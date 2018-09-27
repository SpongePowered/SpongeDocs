=======================
Custom DataManipulators
=======================

.. javadoc-import::
    org.spongepowered.api.CatalogType
    org.spongepowered.api.data.DataManager
    org.spongepowered.api.data.DataRegistration
    org.spongepowered.api.data.DataSerializable
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.DataQuery
    org.spongepowered.api.data.key.Key
    org.spongepowered.api.data.key.Keys
    org.spongepowered.api.data.key.KeyFactory
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.data.manipulator.ImmutableDataManipulator
    org.spongepowered.api.data.manipulator.mutable.common.AbstractBoundedComparableData
    org.spongepowered.api.data.manipulator.mutable.common.AbstractData
    org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData
    org.spongepowered.api.data.manipulator.mutable.tileentity.FurnaceData
    org.spongepowered.api.data.merge.MergeFunction
    org.spongepowered.api.data.value.ValueFactory
    org.spongepowered.api.data.value.mutable.MapValue
    org.spongepowered.api.data.value.mutable.MutableBoundedValue
    org.spongepowered.api.data.value.mutable.Value
    org.spongepowered.api.entity.Transform
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.event.game.GameRegistryEvent.Register
    org.spongepowered.api.event.game.state.GameInitializationEvent
    org.spongepowered.api.item.inventory.ItemStack
    org.spongepowered.api.util.TypeTokens
    java.lang.Comparable
    java.lang.String
    java.util.Comparator
    com.google.common.reflect.TypeToken

On the :doc:`../datamanipulators` page we learned how we use the existing :javadoc:`DataManipulator`\s. This chapter
explains how you can provide your own data manipulators. There are a few steps required for this:

* Making a design decision
* Preparing the Keys and TypeTokens
* Writing the data implementation
* Register your DataManipulators

Making a design decision
========================

To API or Not to API
--------------------

Before you can start with the actual implementation of your ``DataManipulator`` you first have to make a decision on
whether you want to provide an API for your plugin or not.

If other plugins should use your ``DataManipulator``, then the answer should always be yes, because the API abstraction
hides the implementation details that would otherwise distract the users.

We recommend using the API approach even if you are the sole user of those ``DataManipulator``\s for the following
reasons:

* It is easier to spot the values that your ``DataManipulator``\s expose
* It teaches the concept of separation of specification and implementation (and thus makes it easier to expose a
  separate API artifact later on)
* Your ``DataManipulator``\s would work exactly like Sponge's

If you decide to to make an API you would need the following additional things:

* An interface that extends ``DataManipulator`` and defines the methods for the data you want to expose
* An interface that extends ``ImmutableDataManipulator`` and defines the methods for the data you want to expose  

.. tip::

    We recommend creating constant classes for some of the values that you will need for your ``DataManipulator``\s.
    These classes would usually also be part of the API but they don't count, since you would create them anyway.

.. note::

    The following tutorial assumes that you will use the API approach. If you don't want to use it you just have to
    
    * convert the interfaces to actual classes
    * move all code from the DataManipulatorImpl classes to those classes
    * replace all references of the DataManipulatorImpl with the non-suffixed ones

Which Data to Expose
--------------------

For the examples on this page we assume, that we want to create a ``DataManipulator`` for a ``Player``\s homes.
A ``Home`` consist of a ``name`` and :javadoc:`Transform`, which defines a target location and rotation for the
teleport. A player can have multiple homes, but ones of those should be the default.

First lets create the ``Home`` definition:

.. code-block:: java

    import org.spongepowered.api.entity.Transform;
    import org.spongepowered.api.world.World;
    
    public class Home {
    
        private final String name;
        private final Transform<World> transform;
    
        public Home(Transform<World> transform, String name) {
            this.transform = transform;
            this.name = name;
        }
    
        public String getName() {
            return name;
        }
    
        public Transform<World> getTransform() {
            return this.transform;
        }
    
    }

Now we use this data class in our ``HomeData`` specification:

.. code-block:: java

    import org.spongepowered.api.data.manipulator.DataManipulator;
    import org.spongepowered.api.data.value.mutable.MapValue;
    import org.spongepowered.api.data.value.mutable.Value;
    
    public interface HomeData extends DataManipulator<HomeData, ImmutableHomeData> {
    
        Value<Home> defaultHome();
    
        MapValue<String, Home> homes();
    
    }

Here we created a method for each "unit" your data, such as default home and a map of the other homes. In this case
we used our custom data type, but you can also use many others such as a ``String``\s, ``int``\s or
:javadoc:`ItemStack`\s.

.. warning::

    Do **NOT** directly reference ``Entity``\s or other instances that might get unloaded as that would cause memory
    leaks.

.. note::

    All custom methods in your data must return :javadoc:`Value` wrappers for your type. That will allow them to be
    accessed with :javadoc:`Key`\s. There are various extensions of ``Value`` depending on which object will be
    represented, such as :javadoc:`MapValue` which provides the standard map operations, or
    :javadoc:`MutableBoundedValue` which can set limits on the upper and lower bound of the value (like integers).
    The bounds of the values are verified using a :javadoc:`Comparator`. See :javadoc:`Value {Value's javadocs}` for
    all sub-interfaces provided by the SpongeAPI.

Writing the ``ImmutableHomeData`` specification works similar to the ``HomeData`` one, just prefixed with ``Immutable``:

.. code-block:: java

    import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
    import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
    import org.spongepowered.api.data.value.immutable.ImmutableValue;
    
    public interface ImmutableHomeData extends ImmutableDataManipulator<ImmutableHomeData, HomeData> {
    
        ImmutableValue<Home> defaultHome();
    
        ImmutableMapValue<String, Home> homes();
    
    }

.. tip::

    In this examples we omitted the javadocs, but we recommend writing some; especially if you want others to use your
    classes. 

Preparing the Keys and TypeTokens
=================================

Now that we have specified which values we are going to use in our data classes, we need to create some :javadoc:`Key`\s
and :javadoc:`TypeToken`\s. You need one key for each method in your ``DataManipulator`` and one ``TypeToken`` per
return type of your methods.

The TypeTokens class
--------------------

Lets start with the ``TypeToken``\s. ``TypeToken`` store the information which type was referenced at runtime and thus
avoid the runtime type erasure.

.. code-block:: java

    import org.spongepowered.api.data.value.mutable.Value;
    
    import com.google.common.reflect.TypeToken;
    
    public final class MyTypeTokens {
    
        public static final TypeToken<Value<Home>> HOME_VALUE_TOKEN = new TypeToken<Value<Home>>() {
            public static final long serialVersionUID = 1L;
        };
        
        public static final TypeToken<MapValue<String, Home>> HOME_MAP_VALUE_TOKEN = new TypeToken<MapValue<String, Home>>() {
            public static final long serialVersionUID = 1L;
        };
    
    }

.. tip::

    Sponge also has a class with many :javadoc:`TypeTokens`, so you might be able to use existing ``TypeToken``\s.
    In this example we named it ``MyTypeTokens`` to avoid import clashes with Sponge's class.

.. note::

    TypeTokens can be created in one of two ways:

    - For non-generic types, use ``TypeToken.of(MyType.class)``
    - For generic types, create an anonymous class with ``TypeToken<MyGenericType<String>>() {}``

Registering the Keys
--------------------

After we have created the ``TypeToken``\s we can now create and register the ``Key``\s.

The keys should be created and registered during the
:javadoc:`GameRegistryEvent.Register {GameRegistryEvent.Register<Key<?>>}` event.

You start with by calling the :javadoc:`Key#builder()` method and then supplementing the required values to the returned
builder. You need to pass one ``TypeToken`` representing the ``Value`` type for your key. You also need to provide a
:javadoc:`DataQuery` path - this is most commonly used to serialize the ``Value``. As with any catalog type you must
also provide a unique ID and a name. Put this all together and you have a ``Key`` you can use in your ``Value``\s.

.. code-block:: java

    import org.spongepowered.api.data.DataQuery;
    import org.spongepowered.api.data.key.Key;
    import org.spongepowered.api.event.Listener;
    import org.spongepowered.api.event.game.GameRegistryEvent;

    @Listener
    public void onKeyRegistration(GameRegistryEvent.Register<Key<?>> event) {
        MyKeys.DEFAULT_HOME = Key.builder()
                .id("default_home")
                .name("Default Home")
                .type(MyTypeTokens.HOME_VALUE_TOKEN)
                .query(DataQuery.of("DefaultHome"))
                .build();
        event.register(MyKeys.DEFAULT_HOME);

        MyKeys.HOMES = Key.builder()
                .id("homes")
                .name("Homes")
                .type(MyTypeTokens.HOME_MAP_VALUE_TOKEN)
                .query(DataQuery.of("Homes"))
                .build();
        event.register(MyKeys.HOMES);
    }

The ``MyKeys`` class contains static non-final fields that will be updated during the register event.

.. code-block:: java

    import org.spongepowered.api.data.key.Key;
    import org.spongepowered.api.data.value.mutable.MapValue;
    import org.spongepowered.api.data.value.mutable.Value;
    import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;
    
    public final class MyKeys {
    
        public static Key<Value<Home>> DEFAULT_HOME = DummyObjectProvider.createExtendedFor(Key.class, "DEFAULT_HOME");
    
        public static Key<MapValue<String, Home>> HOMES = DummyObjectProvider.createExtendedFor(Key.class, "HOMES");
    
    }

The ``DummyObjectProvider`` create dummy instances that prevent null warning from static code analysis tools, and causes
warnings if someone tries to use the values before they have been initialized.

.. warning::

    Do **NOT** move the creation of the ``Key``\s to the constant class as the ``Key``\'s id is generated based on the
    event's context.

Writing the Data Implementation
===============================

While there are quite a few abstract methods in the ``DataManipulator`` interface, you don't have to implement them all
by yourself, because the SpongeAPI already provides a few, which cover a lot of those methods. Have a look at
:javadoc:`AbstractData` for a list of existing implementations.

.. _single-data-types:

Single Types
------------

Single types are ``DataManipulator``\s with only a single value. In our example this would be the case if we only had a
single (default) home, or if we just had a map of homes without a default one. Single types require little
implementation because much of the work has already been done in the :javadoc:`AbstractSingleData` type you extend from. 

The "simple" abstract types are the easiest to implement, but are restricted to only the types below:

- ``Boolean``
- :javadoc:`Comparable`
- ``Integer``
- ``List``
- ``Map``
- :javadoc:`CatalogType`
- ``Enum``

For all other types you must implement a custom single type by extending ``AbstractSingleData``. This allows you to 
define your own single data with whatever type you want, while still doing most of the work for you.

.. tip::

    The abstract implementations save the object for you in the constructor. You can access it in your implementation 
    by calling the ``getValue()`` and ``getValueGetter()`` methods.

Simple Single Types
^^^^^^^^^^^^^^^^^^^

Almost all the work is done for you with simple abstract types. All you need to do is:

- Extend the relevant abstract type
- pass the `Key` for your data, the object itself, and the default object (if the object is null) in the constructor

:javadoc:`AbstractBoundedComparableData` (and the immutable equivalent) additionally require minimum and maximum 
values that will be checked, as well as a :javadoc:`Comparator`.

.. note::

    ``List`` and ``Mapped`` single types must instead implement ``ListData`` / ``MappedData`` (or the immutable 
    equivalent). This adds additional methods to allow Map-like/List-like behavior directly on the ``DataManipulator``.

DataManipulator
"""""""""""""""

The following 5 methods must be defined on mutable manipulators:

* :javadoc:`DataManipulator#fill(DataHolder, MergeFunction) {fill(DataHolder, MergeFunction)}` should replace the data on
  your object with that of the given ``DataHolder``, using the result of
  :javadoc:`MergeFunction#merge(ValueContainer, ValueContainer) {MergeFunction#merge()}`.

  .. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.merge.MergeFunction;

    import java.util.Optional;

    @Override
    public Optional<HomeData> fill(DataHolder dataHolder, MergeFunction overlap) {
        HomeData merged = overlap.merge(this, dataHolder.get(HomeData.class).orElse(null));
        setValue(merged.homes().get());
        return Optional.of(this);
    }

* :javadoc:`DataManipulator#from(DataContainer) {from(DataContainer)}` should overwrite its value with the one in the
  container and return itself, otherwise return ``Optional.empty()``

  .. code-block:: java

    import org.spongepowered.api.data.DataContainer;
    import org.spongepowered.api.data.DataQuery;

    import com.google.common.collect.Maps;

    import java.util.Optional;
    import java.util.UUID;

    @Override
    public Optional<FriendsData> from(DataContainer container) {
        if(container.contains(Keys.FRIENDS)) {
            List<UUID> friends = container.getObjectList(Keys.FRIENDS.getQuery(), UUID.class).get();
            return Optional.of(setValue(friends));
        }
        return Optional.empty();
    }

* :javadoc:`DataManipulator#copy() {copy()}` should, as the name suggests, return a copy of itself with the same data.

  .. code-block:: java

    import org.spongepowered.cookbook.myhomes.data.friends.FriendsData;

    @Override
    public FriendsData copy() {
        return new FriendsDataImpl(getValue());
    }

* :javadoc:`DataManipulator#asImmutable() {asImmutable()}` should, as the name suggests, return a immutable copy of
  itself with the same data.

  .. code-block:: java

    @Override
    public ImmutableFriendsData asImmutable() {
        return new ImmutableFriendsDataImpl(getValue());
    }

* And finally :javadoc:`DataManipulator#getContentVersion() {getContentVersion()}` should return the version number of
  this current data implementation. We recommend starting with the version ``1``.

  .. code-block:: java

    @Override
    public int getContentVersion() {
        return 1;
    }

ImmutableDataManipulator
""""""""""""""""""""""""

The implementation for the ``ImmutableDataManipulator`` is far easier as you only have to implement two trivial methods:

* :javadoc:`ImmutableDataManipulator#asMutable() {asMutable()}` should, as the name suggests, return a immutable copy of
  itself with the same data.

  .. code-block:: java

    @Override
    public HomeData asMutable() {
        return new HomeDataImpl(getValue());
    }

* And finally :javadoc:`ImmutableDataManipulator#getContentVersion() {getContentVersion()}` should return the version
  number of this current data implementation. We recommend starting with the version ``1``.

  .. code-block:: java

    @Override
    public int getContentVersion() {
        return 1;
    }
    
  .. note::
  
    Keep this value in sync with the mutable ``DataManipulator``. We recommend using a constant field for this.

Summary
"""""""

Your implementation for the ``DataManipulator``  should look like this:

.. code-block:: java

    public class CustomDataImpl
            extends AbstractBooleanData<CustomData, ImmutableCustomData>
            implements CustomData {
    
        protected CustomDataImpl() {
            this(false);
        }
        
        protected CustomDataImpl(boolean value) {
            super(value, MyKeys.CUSTOM, false);
        }
        
        @Override
        public Value<Boolean> myCustom() {
            return getValueGetter();
        }
    
        public Optional<CustomData> fill(DataHolder dataHolder, MergeFunction overlap) { ... }
        public Optional<CustomData> from(DataContainer container) { ... }
        public CustomData copy() { ... }
        public ImmutableCustomData asImmutable() { ... }
        public int getContentVersion() { ... }
    
    } 

And your implementation for the ``DataManipulator``  should look like this:

.. code-block:: java

    public class ImmutableCustomDataImpl
            extends AbstractImmutableBooleanData<ImmutableCustomData, CustomData>
            implements ImmutableCustomData {
    
        protected ImmutableCustomDataImpl() {
            this(false);
        }
    
        protected ImmutableCustomDataImpl(boolean value) {
            super(value, MyKeys.CUSTOM, false);
        }
    
        @Override
        public ImmutableValue<Boolean> myCustom() {
            return getValueGetter();
        }
    
        public CustomData asMutable() { ... }
        public int getContentVersion() { ... }
    
    }

Custom Single Types
^^^^^^^^^^^^^^^^^^^

In addition to the methods from the simple single types, you need to override the following methods:

* ``getValueGetter()`` should pass the ``Value`` representing your data (see above).

:javadoc:`DataManipulator#toContainer() {toContainer()}` should return a ``DataContainer`` representing your data (see above).

.. _compound-data-types:

Compound Types
--------------

Whereas single types only support one value, "compound" types support however many values you want. This is useful 
when multiple objects are grouped, such as :javadoc:`FurnaceData`. The downside, however, is that they are more 
complex to implement.

To start with, create all the ``Value`` getters that your data will have. For each value, create a method to get and 
set the *raw* object, which you'll use later. For immutable data, only the getters are necessary.








