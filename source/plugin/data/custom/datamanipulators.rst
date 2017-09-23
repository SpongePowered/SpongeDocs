=======================
Custom DataManipulators
=======================

.. javadoc-import::
    org.spongepowered.api.data.DataSerializable
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.DataQuery
    org.spongepowered.api.data.key.Key
    org.spongepowered.api.data.key.KeyFactory
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.data.manipulator.ImmutableDataManipulator
    org.spongepowered.api.data.manipulator.mutable.common.AbstractBoundedComparableData
    org.spongepowered.api.data.manipulator.mutable.common.AbstractData
    org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData
    org.spongepowered.api.data.manipulator.mutable.tileentity.FurnaceData
    org.spongepowered.api.data.value.ValueFactory
    org.spongepowered.api.data.value.mutable.Value
    org.spongepowered.api.data.value.mutable.MapValue
    org.spongepowered.api.data.value.mutable.BoundedComparableValue
    org.spongepowered.api.data.value.ValueContainer
    org.spongepowered.api.inventory.ItemStack
    java.util.Comparable
    java.util.Comparator
    com.google.common.reflect.TypeToken


The core part of custom data is the :javadoc:`DataManipulator`. To implement it, you must first decide if you want to 
create a separate API for your custom data. Generally speaking it's best to separate the API from the implementation 
(as SpongeAPI does), but if it won't be seen by other developers then you can just put both in the same class.

You'll want to define an API method for each "unit" your data, such as a ``String``, ``int``, :javadoc:`ItemStack` or 
a custom type like ``Home``. These units will be wrapped in a :javadoc:`Value`, which will allow it to be accessed
with :javadoc:`Key`\ s. There are various extensions of ``Value`` depending on which object will be represented, such
as :javadoc:`MapValue` which provides the standard map operations, or :javadoc:`BoundedComparableValue` which can set
limits on the upper and lower bound of an :javadoc:`Comparable` objects like integers.

Now, pick which of the :javadoc:`AbstractData` types you'll extend from. While you could implement from scratch, these
abstract types remove a *lot* of the work that needs to be done implementing the required methods. A full list can be 
found in :javadoc:`org.spongepowered.api.data.manipulator.mutable.common`. See either :ref:`single-data-types` or 
:ref:`compound-data-types` below for implementation details each type.

You need to create two different classes - one which is mutable and implements :javadoc:`DataManipulator` and your
abstract type, and an immutable version which implements :javadoc:`ImmutableDataManipulator` and your *immutable* 
abstract type.

.. note::
    
    **All** data must have mutable and immutable versions, you must implement both.

For all types, you'll need to define the :javadoc:`DataManipulator#asImmutable()`/
:javadoc:`ImmutableDataManipulator#asMutable() {asMutable()}` methods - this is as simple as copying the existing
objects into a constructor for the alternate version.

Values
======

Your value getter(s) need to return a value. In the example below, we get the :javadoc:`ValueFactory`. This saves us a
lot of type by using Sponge's already implemented ``Value`` objects. Depending on what value you're creating there a
different methods to call such as ``createMapValue``, ``createBoundedComparableValue``, etc.

**Code Example: Implementing a Value Getter**

.. code-block:: java
    
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.data.value.ValueFactory;
    import org.spongepowered.api.data.value.mutable.Value;

    import org.spongepowered.cookbook.myhomes.data.home.Home;
    import org.spongepowered.cookbook.myhomes.data.Keys;

    @Override
    protected Value<Home> defaultHome() {
        return Sponge.getRegistry().getValueFactory()
                .createValue(Keys.DEFAULT_HOME, getValue(), null);
    }

Note that an ``ImmutableDataManipulator`` would instead return an ``ImmutableValue``, by calling ``asImmutable()`` on
the returned ``Value``. We recommended that you cache this (such as with a class field) in the immutable version.

Each ``Value`` also needs a :javadoc:`Key` to identify it, seen in the example as ``Keys.DEFAULT_HOME``. Similar
to values, you use one of the ``makeXKey()`` methods in :javadoc:`KeyFactory` to create a ``Key`` for your value.

You need to pass one ``TypeToken`` representing the *raw* type of your value, and one ``TypeToken`` representing the
``Value``. You also need to provide a :javadoc:`DataQuery` path - this is most commonly used to serialize the
``Value``. As with any catalog type you must also provide a unique ID and a name. Put this all together and you have a
``Key`` you can use in your ``Value``\ s.

**Code Example: Creating a Key**

.. code-block:: java

    import org.spongepowered.api.data.DataQuery;
    import org.spongepowered.api.data.key.Key;
    import org.spongepowered.api.data.key.KeyFactory;
    import org.spongepowered.api.data.value.mutable.Value;
    import org.spongepowered.api.data.value.mutable.Value;

    import com.google.common.reflect.TypeToken;

    import org.spongepowered.cookbook.myhomes.data.home.Home;

    public static final Key<Value<Home>> DEFAULT_HOME = KeyFactory.makeSingleKey(
            TypeToken.of(Home.class),
            new TypeToken<Value<Home>>() {},
            DataQuery.of("DefaultHome"), "myhomes:default_home", "Default Home");

.. note::

    :javadoc:`TypeToken`\ s are used by the server implementation to preserve the generic type of your
    values. They are created in one of two ways:

    - For non-generic types, use ``TypeToken.of(MyType.class)``
    - For generic types, create an anonymous class with ``TypeToken<MyGenericType<String>>() {}``

Serialization
=============

To make your data :doc:`serializable <../serialization>` to :javadoc:`DataHolder`\ s or config files, you must also
implement :javadoc:`DataSerializable#toContainer()`. We recommend calling ``super.toContainer()`` as this will
include the version from :javadoc:`DataSerializable#getContentVersion()`. You should increase the version each time a
change is made to the format of your serialized data, and use :ref:`content-updaters` to allow backwards compatability.

.. note::

    This is not required for simple single types, as the already implement ``toContainer()``

**Code Example: Implementing toContainer**

.. code-block:: java
    
    import org.spongepowered.api.data.DataContainer;

    import org.spongepowered.cookbook.myhomes.data.Keys;

    @Override
    public DataContainer toContainer() {
        DataContainer container = super.toContainer();
        // This is the simplest, but use whatever structure you want!
        container.set(Keys.DEFAULT_HOME.getQuery(), this.defaultHome);
        container.set(Keys.HOMES, this.homes);

        return container;
    }

.. _single-data-types:

Single Types
============

Single types require little implementation because much of the work has already been done in the ``AbstractSingleData``
type you extend from. 

The "simple" abstract types are the easiest to implement, but are restricted to only the types below:

- ``Boolean``
- ``Comparable``
- ``Integer``
- ``List``
- ``Map``
- ``CatalogType``
- ``Enum``

For all other types you must implement a custom single type by extending ``AbstractSingleData``. This allows you to 
define your own single data with whatever type you want, while still doing most of the work for you.

.. tip::

    The abstract implementations save the object for you in the constructor. You can access it in your implementation 
    by calling the ``getValue()`` and ``getValueGetter()`` methods.

Simple Single Types
-------------------

Almost all the work is done for you with simple abstract types. All you need to do is:

- Extend the relevant abstract type
- pass the `Key` for your data, the object itself, and the default object (if the object is null) in the constructor

:javadoc:`AbstractBoundedComparableData` (and the immutable equivalent) additionally require minimum and maximum 
values that will be checked, as well as a :javadoc:`Comparator`.

.. note::

    ``List`` and ``Mapped`` single types must instead implement ``ListData`` / ``MappedData`` (or the immutable 
    equivalent). This adds additional methods to allow Map-like/List-like behavior directly on the ``DataManipulator``.

The following 3 methods must be defined on mutable manipluators:

``fill(DataHolder, MergeFunction)`` should replace the data on your object with that of the given ``DataHolder``, 
using the result of ``MergeFunction#merge()``.

.. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.merge.MergeFunction;

    import org.spongepowered.cookbook.myhomes.data.friends.FriendsData;

    import java.util.Optional;

    @Override
    public Optional<FriendsData> fill(DataHolder dataHolder, MergeFunction overlap) {
        FriendsData merged = overlap.merge(this, dataHolder.get(FriendsData.class).orElse(null));
        setValue(merged.friends().get());

        return Optional.of(this);
    }

``from(DataContainer)`` should overwrite its value with the one in the container and return itself, otherwise return
``Optional.empty()``

.. code-block:: java

    import org.spongepowered.api.data.DataContainer;
    import org.spongepowered.api.data.DataQuery;

    import org.spongepowered.cookbook.myhomes.data.Keys;
    import org.spongepowered.cookbook.myhomes.data.friends.FriendsData;
    import org.spongepowered.cookbook.myhomes.data.friends.ImmutableFriendsData;

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

``copy()`` should, as the name suggests, return a copy of itself with the same data.

.. code-block:: java

    import org.spongepowered.cookbook.myhomes.data.friends.FriendsData;

    @Override
    public FriendsData copy() {
        return new FriendsDataImpl(getValue());
    }

Custom Single Types
-------------------

In addition to the , you need to override the following methods:

``getValueGetter()`` should pass the ``Value`` representing your data (see above).

``toContainer()`` should return a ``DataContainer`` representing your data (see above).

.. _compound-data-types:

Compound Types
==============

Whereas single types only support one value, "compound" types support however many values you want. This is useful 
when multiple objects are grouped, such as :javadoc:`FurnaceData`. The downside, however, is that they are more 
complex to implement.

To start with, create all the ``Value`` getters that your data will have. For each value, create a method to get and 
set the *raw* object, which you'll use later. For immutable data, only the getters are necessary.

Registering Values
------------------

Next, you'll want to register these so that the :doc:`Keys <../keys>`-based system can reference them. To do this,
implement either :javadoc:`DataManipulator#registerGettersAndSetters()` or 
:javadoc:`ImmutableDataManipulator#registerGetters()` depending on whether the data is mutable or not.

For each value you must call:

- ``registerKeyValue(Key, Supplier)`` referencing the ``Value`` getter for the given key
- ``registerFieldGetter(Key, Supplier)`` referencing the getter method for the *raw* object defined above
- ``registerFieldSetter(Key, Consumer)`` referencing the setter method above if you are implementing the mutable
  version

We recommend using Java 8's ``::`` syntax for easy ``Supplier`` and ``Consumer`` functions.

**Code Example: Implementing Getters and Setters**

.. code-block:: java

    import org.spongepowered.cookbook.myhomes.data.Keys
    
    // registerGetters() for immutable implementation
    @Override
    protected void registerGettersAndSetters() {
        registerKeyValue(Keys.DEFAULT_HOME, this::defaultHome);
        registerKeyValue(Keys.HOMES, this::homes);

        registerFieldGetter(Keys.DEFAULT_HOME, this::getDefaultHome);
        registerFieldGetter(Keys.HOMES, this::getHomes);

        // Only on mutable implementation
        registerFieldSetter(Keys.DEFAULT_HOME, this::setDefaultHome);
        registerFieldSetter(Keys.HOMES, this::setHomes);
    }

``fill(DataHolder, MergeFunction)`` and ``from(DataContainer)`` are similar to the implementations for single data, 
but loading all your values.
