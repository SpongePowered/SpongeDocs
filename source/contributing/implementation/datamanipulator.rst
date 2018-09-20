=============================
Implementing DataManipulators
=============================

.. javadoc-import::

    java.util.function.Supplier
    java.util.function.Consumer
    org.spongepowered.api.data.DataContainer
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.DataSerializable
    org.spongepowered.api.data.DataTransactionResult
    org.spongepowered.api.data.DataTransactionResult.Builder
    org.spongepowered.api.data.DataQuery
    org.spongepowered.api.data.key.Key
    org.spongepowered.api.data.key.Key.Builder
    org.spongepowered.api.data.key.Keys
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.data.manipulator.ImmutableDataManipulator
    org.spongepowered.api.data.value.mutable.Value
    org.spongepowered.api.data.value.BaseValue
    org.spongepowered.api.data.manipulator.mutable.entity.HealthData
    org.spongepowered.api.item.inventory.ItemStack

This is a guide for contributors who want to help with Data API implementation by creating DataManipulators.
An updated list of DataManipulators to be implemented can be found at
`SpongeCommon Issue #8 <https://github.com/SpongePowered/SpongeCommon/issues/8>`_.

To fully implement a :javadoc:`DataManipulator` these steps must be followed:

1. Implement the ``DataManipulator`` itself
#. Implement the :javadoc:`ImmutableDataManipulator`

When these steps are complete, the following must also be done:

3. Register the :javadoc:`Key` in the ``KeyRegistryModule``
#. Implement the ``DataProcessor``
#. Implement the ``ValueProcessor`` for each :javadoc:`Value` being represented by the ``DataManipulator``

If the data applies to a block, several methods must also be mixed in to the block.

.. note::
    Make sure you follow our :doc:`../guidelines`.

The following snippet shows the imports/paths for some classes in SpongeCommon that you will need:

.. code-block:: java

    import org.spongepowered.common.data.DataProcessor;
    import org.spongepowered.common.data.ValueProcessor;
    import org.spongepowered.common.data.manipulator.immutable.entity.ImmutableSpongeHealthData;
    import org.spongepowered.common.data.manipulator.mutable.common.AbstractData;
    import org.spongepowered.common.data.manipulator.mutable.entity.SpongeHealthData;
    import org.spongepowered.common.data.processor.common.AbstractEntityDataProcessor;
    import org.spongepowered.common.data.util.DataConstants;
    import org.spongepowered.common.data.util.NbtDataUtil;
    import org.spongepowered.common.registry.type.data.KeyRegistryModule;
    

1. Implement the DataManipulator
================================

The naming convention for ``DataManipulator`` implementations is the name of the interface prefixed with "Sponge".
So to implement the :javadoc:`HealthData` interface, we create a class named ``SpongeHealthData`` in the appropriate package.
For implementing the ``DataManipulator`` first have it extend an appropriate abstract class from the
``org.spongepowered.common.data.manipulator.mutable.common`` package. The most generic there is ``AbstractData``
but there are also abstractions that reduce boilerplate code even more for some special cases like
``DataManipulator``\ s only containing a single value.

.. code-block:: java

    public class SpongeHealthData extends AbstractData<HealthData, ImmutableHealthData> implements HealthData {
        [...]
    }

There are two type arguments to the AbstractData class. The first is the interface implemented by this class, the
second is the interface implemented by the corresponding ``ImmutableDataManipulator``.

The Constructor
~~~~~~~~~~~~~~~

In most cases while implementing an abstract ``DataManipulator`` you need to have two constructors:

* One without arguments (no-args) which calls the second constructor with "default" values
* The second constructor that takes all the values it supports.

The second constructor must

* make a call to the ``AbstractData`` constructor, passing the class reference for the implemented interface.
* make sure the values passed are valid
* call the ``registerGettersAndSetters()`` method

.. code-block:: java

    import static com.google.common.base.Preconditions.checkArgument;
    
    import org.spongepowered.common.data.util.DataConstants;

    public class SpongeHealthData extends AbstractData<HealthData, ImmutableHealthData> implements HealthData {

        private double health;
        private double maxHealth;

        public SpongeHealthData() {
            this(DataConstants.DEFAULT_HEALTH, DataConstants.DEFAULT_HEALTH);
        }

        public SpongeHealthData(double health, double maxHealth) {
            super(HealthData.class);
            checkArgument(maxHealth > DataConstants.MINIMUM_HEALTH);
            this.health = health;
            this.maxHealth = maxHealth;
            registerGettersAndSetters();
        }

        [...]

    }

Since we know that both current health and maximum health are bounded values, we need to make sure no values
outside of these bounds can be passed. To achieve this, we use guava's ``Preconditions`` of which we import the
required methods statically.

.. note::

    Never use so-called magic values (arbitrary numbers, booleans etc.) in your code. Instead, locate the
    ``DataConstants`` class and use a fitting constant - or create one, if necessary.

Accessors defined by the Interface
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The interface we implement specifies some methods to access :javadoc:`Value` objects. For ``HealthData``, those are
:javadoc:`HealthData#health()` and :javadoc:`HealthData#maxHealth()`. Every call to those should yield a new ``Value``.

.. code-block:: java

    public MutableBoundedValue<Double> health() {
        return SpongeValueFactory.boundedBuilder(Keys.HEALTH)
            .minimum(DataConstants.MINIMUM_HEALTH)
            .maximum(this.maximumHealth)
            .defaultValue(this.maximumHealth)
            .actualValue(this.currentHealth)
            .build();
    }

.. tip::

    Since ``Double`` is a ``Comparable``, we do not need to explicitly specify a comparator.

If no current value is specified, calling :javadoc:`BaseValue#get()` on the ``Value`` returns the default value.

Copying and Serialization
~~~~~~~~~~~~~~~~~~~~~~~~~

The two methods :javadoc:`DataManipulator#copy()` and :javadoc:`DataManipulator#asImmutable()` are not much work to
implement. For both you just need to return a mutable or an immutable data manipulator respectively, containing the same
data as the current instance.

The method :javadoc:`DataSerializable#toContainer()` is used for serialization purposes. Use
:javadoc:`DataContainer#createNew()` as the result and apply to it the values stored within this instance.
A :javadoc:`DataContainer` is basically a map mapping :javadoc:`DataQuery`\s to values. Since a :javadoc:`Key` always
contains a corresponding ``DataQuery``, just use those by passing the ``Key`` directly.

.. code-block:: java

    public DataContainer toContainer() {
        return DataContainer.createNew()
            .set(Keys.HEALTH, this.currentHealth)
            .set(Keys.MAX_HEALTH, this.maximumHealth);
    }

registerGettersAndSetters()
~~~~~~~~~~~~~~~~~~~~~~~~~~~

A ``DataManipulator`` also provides methods to get and set data using keys. The implementation for this is handled
by ``AbstractData``, but we must tell it which data it can access and how. Therefore, in the
``registerGettersAndSetters()`` method we need to do the following for each value:

* register a :javadoc:`Supplier` to directly get the value
* register a :javadoc:`Consumer` to directly set the value
* register a ``Supplier<Value>`` to get the mutable ``Value``

``Supplier`` and ``Consumer`` are functional interfaces, so Java 8 Lambdas can be used.

.. code-block:: java

    private void setCurrentHealthIfValid(double value) {
        if (value >= DataConstants.MINIMUM_HEALTH && value <= (double) Float.MAX_VALUE) {
            this.currentHealth = value;
        } else {
            throw new IllegalArgumentException("Invalid value for current health");
        }
    }

    private void setMaximumHealthIfValid(double value) {
        if (value >= DataConstants.MINIMUM_HEALTH && value <= (double) Float.MAX_VALUE) {
            this.maximumHealth = value;
        } else {
            throw new IllegalArgumentException("Invalid value for maximum health");
        }

    }

    private void registerGettersAndSetters() {
        registerFieldGetter(Keys.HEALTH, () -> this.currentHealth);
        registerFieldSetter(Keys.HEALTH, this::setCurrentHealthIfValid);
        registerKeyValue(Keys.HEALTH, this::health);

        registerFieldGetter(Keys.MAX_HEALTH, () -> this.maximumHealth);
        registerFieldSetter(Keys.MAX_HEALTH, this::setMaximumHealthIfValid);
        registerKeyValue(Keys.MAX_HEALTH, this::maxHealth);
    }

The ``Consumer`` registered as field setter must perform the adequate checks to make sure the supplied value is valid.
This applies especially for :javadoc:`DataHolder`\s which won't accept negative values. If a value is invalid, an
``IllegalArgumentException`` should be thrown.

.. tip::

    The validity criteria for those setters are the same as for the respective ``Value`` object, so you might delegate
    the validity check to a call of ``this.health().set()`` and just set ``this.currentHealth = value`` if the first
    line has not thrown an exception yet.

That's it. The ``DataManipulator`` should be done now.

2. Implement the ImmutableDataManipulator
=========================================

Implementing the :javadoc:`ImmutableDataManipulator` is similar to implementing the mutable one.

The only differences are:

* The class name is formed by prefixing the mutable ``DataManipulator``\ s name with ``ImmutableSponge``
* Inherit from ``ImmutableAbstractData`` instead
* Instead of ``registerGettersAndSetters()``, the method is called ``registerGetters()``

When creating ``ImmutableDataHolder``\ s or ``ImmutableValue``\ s, check if it makes sense to use the
``ImmutableDataCachingUtil``. For example, if you have ``WetData`` which contains nothing more than a boolean, it
is more feasible to retain only two cached instances of ``ImmutableWetData`` - one for each possible value. For
manipulators and values with many possible values (like ``SignData``) however, caching is proven to be too expensive.

.. tip::

    You should declare the fields of an ``ImmutableDataManipulator`` as ``final`` in order to
    prevent accidental changes.

3. Register the Key in the KeyRegistryModule
============================================

The next step is to register your :javadoc:`Key`\s to the :javadoc:`Keys`. To do so, locate the
``KeyRegistryModule`` class and find the ``registerDefaults()`` method.
There add a line to register (and create) your used keys.

.. code-block:: java

    this.register(Key.builder()
            .type(TypeTokens.BOUNDED_DOUBLE_VALUE_TOKEN)
            .id("health")
            .name("Health")
            .query(of("Health"))
            .build());
    this.register(Key.builder()
            .type(TypeTokens.BOUNDED_DOUBLE_VALUE_TOKEN)
            .id("max_health")
            .name("Max Health")
            .query(of("MaxHealth"))
            .build());


The ``register(Key)`` method registers your ``Key``\s for later use. The string used for the id should be the
corresponding constant name from the ``Keys`` utility class in lowercase. The ``Key`` itself is created by using the
:javadoc:`Key.Builder` provided by the :javadoc:`Key#builder()` method. You have to set a ``TypeToken``, an ``id``,
human readable ``name`` and a ``DataQuery``.
The ``DataQuery`` is used for serialization. It is created from the statically imported ``DataQuery.of()`` method
accepting a string. This string should also be the constant name, stripped of underscores and capitalization changed to
upper camel case.


4. Implement the DataProcessors
===============================

Next up is the ``DataProcessor``. A ``DataProcessor`` serves as a bridge between our ``DataManipulator`` and
Minecraft's objects. Whenever any data is requested from or offered to ``DataHolders`` that exist in Vanilla
Minecraft, those calls end up being delegated to a ``DataProcessor`` or a ``ValueProcessor``.

For your name, you should use the name of the ``DataManipulator`` interface and append ``Processor``. Thus, for
``HealthData`` we create a ``HealthDataProcessor``.

In order to reduce boilerplate code, the ``DataProcessor`` should inherit from the appropriate abstract class in
the ``org.spongepowered.common.data.processor.common`` package. Since health can only be present on certain
entities, we can make use of the ``AbstractEntityDataProcessor`` which is specifically targeted at ``Entities``
based on ``net.minecraft.entity.Entity``. ``AbstractEntitySingleDataProcessor`` would require less
implementation work, but cannot be used as ``HealthData`` contains more than just one value.

.. code-block:: java

    public class HealthDataProcessor
            extends AbstractEntityDataProcessor<EntityLivingBase, HealthData, ImmutableHealthData> {
    
        public HealthDataProcessor() {
            super(EntityLivingBase.class);
        }

        [...]

    }

Depending on which abstraction you use, the methods you have to implement may differ greatly, depending on how
much implementation work already could be done in the abstract class. Generally, the methods can be categorized.

.. tip::

    It is possible to create multiple ``DataProcessor``\s for the same data. If vastly different ``DataHolder``\s
    should be supported (for example both a ``TileEntity`` and a matching ``ItemStack``), it may be beneficial to
    create one processor for each type of ``DataHolder`` in order to make full use of the provided abstractions.
    Make sure you follow the package structure for items, tileentities and entities.

Validation Methods
~~~~~~~~~~~~~~~~~~

Always return a boolean value. If any of the ``supports(target)`` methods is called it should perform a general check if
the supplied target generally supports the kind of data handled by our ``DataProcessor``. Based on your level of
abstraction you might not have to implement it at all, if you have to just implement the most specific one, as the more
generic ones usually delegate to them.

For our ``HealthDataProcessor`` ``supports()`` is implemented by the ``AbstractEntityDataProcessor``. Per
default, it will return true if the supplied argument is an instance of the class specified when calling the
``super()`` constructor.

Instead, we are required to provide a ``doesDataExist()`` method. Since the abstraction does not know how to
obtain the data, it leaves this function to be implemented. As the name says, the method should check if the data
already exists on the supported target. For the ``HealthDataProcessor``, this always returns true, since every
living entity always has health.

.. code-block:: java

    @Override
    protected boolean doesDataExist(EntityLivingBase entity) {
        return true;
    }

Setter Methods
~~~~~~~~~~~~~~

A setter method receives a ``DataHolder`` of some sort and some data that should be applied to it, if possible.

The ``DataProcessor`` interface defines a ``set()`` method accepting a ``DataHolder`` and a ``DataManipulator``
which returns a ``DataTransactionResult``. Depending on the abstraction class used, some of the necessary
functionality might already be implemented.

In this case, the ``AbstractEntityDataProcessor`` takes care of most of it and just requires a method to set
some values to return ``true`` if it was successful and ``false`` if it was not. All checks if the
``DataHolder`` supports the ``Data`` is taken care of, the abstract class will just pass a Map mapping each
``Key`` from the ``DataManipulator`` to its value and then construct a ``DataTransactionResult`` depending on
whether the operation was successful or not.

.. code-block:: java

    @Override
    protected boolean set(EntityLivingBase entity, Map<Key<?>, Object> keyValues) {
        entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                .setBaseValue(((Double) keyValues.get(Keys.MAX_HEALTH)).floatValue());
        float health = ((Double) keyValues.get(Keys.HEALTH)).floatValue();
        entity.setHealth(health);
        return true;
    }

.. tip::

    To understand :javadoc:`DataTransactionResult`\s, check the :doc:`corresponding docs page
    </plugin/data/transactions>` and refer to the :javadoc:`DataTransactionResult.Builder` docs to create one.

.. warning::

    Especially when working with :javadoc:`ItemStack`\s it is likely that you will need to deal with
    ``NBTTagCompound``\s directly. Many NBT keys are already defined as constants in the ``NbtDataUtil`` class.
    If your required key is not there, you need to add it in order to avoid 'magic values' in the code.

Removal Method
~~~~~~~~~~~~~~

The ``remove()`` method attempts to remove data from the ``DataHolder`` and returns a ``DataTransactionResult``.

Removal is not abstracted in any abstract ``DataProcessor`` as the abstractions have no way of knowing if the data
is always present on a compatible ``DataHolder`` (like ``WetData`` or ``HealthData``) or if it may or may not be present
(like ``LoreData``). If the data is always present, ``remove()`` must always fail. If it may or may not be present,
``remove()`` should remove it.

Since a living entity *always* has health, ``HealthData`` is always present and removal therefore not supported.
Therefore we just return :javadoc:`DataTransactionResult#failNoData()`.

.. code-block:: java

    @Override
    public DataTransactionResult remove(DataHolder dataHolder) {
        return DataTransactionResult.failNoData();
    }


Getter Methods
~~~~~~~~~~~~~~

Getter methods obtain data from a ``DataHolder`` and return an optional ``DataManipulator``. The
``DataProcessor`` interface specifies the methods ``from()`` and ``createFrom()``, the difference being that
``from()`` will return ``Optional.empty()`` if the data holder is compatible, but currently does not contain the
data, while ``createFrom()`` will provide a ``DataManipulator`` holding default values in that case.

Again, ``AbstractEntityDataProcessor`` will provide most of the implementation for this and only requires a
method to get the actual values present on the ``DataHolder``. This method is only called after ``supports()``
and ``doesDataExist()`` both returned true, which means it is run under the assumption that the data is present.

.. warning::

    If the data may not always exist on the target ``DataHolder``, e.g. if the ``remove()`` function may be successful
    (see above), it is imperative that you implement the ``doesDataExist()`` method so that it returns ``true``
    if the data is present and ``false`` if it is not.

.. code-block:: java

    @Override
    protected Map<Key<?>, ?> getValues(EntityLivingBase entity) {
        final double health = entity.getHealth();
        final double maxHealth = entity.getMaxHealth();
        return ImmutableMap.of(Keys.HEALTH, health, Keys.MAX_HEALTH, maxHealth);
    }

Filler Methods
~~~~~~~~~~~~~~

A filler method is different from a getter method in that it receives a ``DataManipulator`` to fill with values.
These values either come from a ``DataHolder`` or have to be deserialized from a ``DataContainer``. The method
returns ``Optional.empty()`` if the ``DataHolder`` is incompatible.

``AbstractEntityDataProcessor`` already handles filling from ``DataHolders`` by creating a ``DataManipulator``
from the holder and then merging it with the supplied manipulator, but the ``DataContainer`` deserialization it
cannot provide.

.. code-block:: java

    @Override
    public Optional<HealthData> fill(DataContainer container, HealthData healthData) {
        if (!container.contains(Keys.MAX_HEALTH.getQuery()) || !container.contains(Keys.HEALTH.getQuery())) {
            return Optional.empty();
        }
        healthData.set(Keys.MAX_HEALTH, getData(container, Keys.MAX_HEALTH));
        healthData.set(Keys.HEALTH, getData(container, Keys.HEALTH));
        return Optional.of(healthData);
    }

The ``fill()`` method is to return an ``Optional`` of the altered ``healthData``, if and only if all required data could
be obtained from the ``DataContainer``.

Other Methods
~~~~~~~~~~~~~

Depending on the abstract superclass used, some other methods may be required. For instance,
``AbstractEntityDataProcessor`` needs to create ``DataManipulator`` instances in various points. It can't do this
since it knows neither the implementation class nor the constructor to use. Therefore it utilizes an abstract
function that has to be provided by the final implementation. This does nothing more than create a
``DataManipulator`` with default data.

If you implemented your ``DataManipulator`` as recommended, you can just use the no-args constructor.

.. code-block:: java

    @Override
    protected HealthData createManipulator() {
        return new SpongeHealthData();
    }


5. Implement the ValueProcessors
================================

Not only a ``DataManipulator`` may be offered to a ``DataHolder``, but also a keyed ``Value`` on its own.
Therefore, you need to provide at least one ``ValueProcessor`` for every ``Key`` present in your
``DataManipulator``. A ``ValueProcessor`` is named after the constant name of its ``Key`` in the ``Keys`` class
in a fashion similar to its ``DataQuery``. The constant name is stripped of underscores, used in upper camel case
and then suffixed with ``ValueProcessor``.

A ``ValueProcessor`` should always inherit from ``AbstractSpongeValueProcessor``, which already will handle a
portion of the ``supports()`` checks based on the type of the ``DataHolder``. For ``Keys.HEALTH``, we'll create
and construct ``HealthValueProcessor`` as follows.

.. code-block:: java

    public class HealthValueProcessor
            extends AbstractSpongeValueProcessor<EntityLivingBase, Double, MutableBoundedValue<Double>> {
    
        public HealthValueProcessor() {
            super(EntityLivingBase.class, Keys.HEALTH);
        }

        [...]

    }

Now the ``AbstractSpongeValueProcessor`` will relieve us of the necessity to check if the value is supported.
It is assumed to be supported if the target ``ValueContainer`` is of the type ``EntityLivingBase``.

.. tip::

    For a more fine-grained control over what ``EntityLivingBase`` objects are supported, the
    ``supports(EntityLivingBase)`` method can be overridden.

Again, most work is done by the abstraction class. We just need to implement two helper methods for creating
a ``Value`` and its immutable counterpart and three methods to get, set and remove data.

.. code-block:: java

    @Override
    protected MutableBoundedValue<Double> constructValue(Double health) {
        return SpongeValueFactory.boundedBuilder(Keys.HEALTH)
            .minimum(DataConstants.MINIMUM_HEALTH)
            .maximum(((Float) Float.MAX_VALUE).doubleValue())
            .defaultValue(DataConstants.DEFAULT_HEALTH)
            .actualValue(health)
            .build();
    }

    @Override
    protected ImmutableBoundedValue<Double> constructImmutableValue(Double value) {
        return constructValue(value).asImmutable();
    }



.. code-block:: java

    @Override
    protected Optional<Double> getVal(EntityLivingBase container) {
        return Optional.of((double) container.getHealth());
    }

Since it is impossible for an ``EntityLivingBase`` to not have health, this method will never return
``Optional.empty()``.

.. code-block:: java

    @Override
    protected boolean set(EntityLivingBase container, Double value) {
        if (value >= DataConstants.MINIMUM_HEALTH && value <= (double) Float.MAX_VALUE) {
            container.setHealth(value.floatValue());
            return true;
        }
        return false;
    }

The ``set()`` method will return a boolean value indicating whether the value could successfully be set.
This implementation will reject values outside of the bounds used in our value construction methods above.

.. code-block:: java

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }

Since the data is guaranteed to be always present, attempts to remove it will just fail.

6. Register Processors
======================

In order for Sponge to be able to use our manipulators and processors, we need to register them. This is done in the 
``DataRegistrar`` class. In the ``setupSerialization()`` method there are two large blocks of registrations to which we
add our processors.

DataProcessors
~~~~~~~~~~~~~~

A ``DataProcessor`` is registered alongside the interface and implementation classes of the ``DataManipulator`` it
handles. For every pair of mutable / immutable ``DataManipulator``\ s at least one ``DataProcessor`` must be registered.

.. code-block:: java

    DataUtil.registerDataProcessorAndImpl(HealthData.class, SpongeHealthData.class,
            ImmutableHealthData.class, ImmutableSpongeHealthData.class,
            new HealthDataProcessor());


ValueProcessors
~~~~~~~~~~~~~~~

Value processors are registered at the bottom of the very same function. For each ``Key`` multiple processors
can be registered by subsequent calls of the ``registerValueProcessor()`` method.

.. code-block:: java

    DataUtil.registerValueProcessor(Keys.HEALTH, new HealthValueProcessor());
    DataUtil.registerValueProcessor(Keys.MAX_HEALTH, new MaxHealthValueProcessor());


Implementing Block Data
=======================

Block data is somewhat different from other types of data in that it is implemented by mixing in to the block itself.
There are several methods in ``org.spongepowered.mixin.core.block.MixinBlock`` that must be overridden to implement
data for blocks.

.. code-block:: java
    
    @Mixin(BlockHorizontal.class)
    public abstract class MixinBlockHorizontal extends MixinBlock {

        [...]

    }

``supports()`` should return ``true`` if either the ``ImmutableDataManipulator`` interface is assignable from the
``Class`` passed in as the argument, or the superclass supports it.

.. code-block:: java

    @Override
    public boolean supports(Class<? extends ImmutableDataManipulator<?, ?>> immutable) {
        return super.supports(immutable) || ImmutableDirectionalData.class.isAssignableFrom(immutable);
    }

``getStateWithData()`` should return a new ``BlockState`` with the data from the ``ImmutableDataManipulator`` applied
to it. If the manipulator is not directly supported, the method should delegate to the superclass.

.. code-block:: java

    @Override
    public Optional<BlockState> getStateWithData(IBlockState blockState, ImmutableDataManipulator<?, ?> manipulator) {
        if (manipulator instanceof ImmutableDirectionalData) {
            final Direction direction = ((ImmutableDirectionalData) manipulator).direction().get();
            final EnumFacing facing = DirectionResolver.getFor(direction);
            return Optional.of((BlockState) blockState.withProperty(BlockHorizontal.FACING, facing));
        }
        return super.getStateWithData(blockState, manipulator);
    }

``getStateWithValue()`` is the equivalent of ``getStateWithData()``, but works with single ``Key``\ s.

.. code-block:: java

    @Override
    public <E> Optional<BlockState> getStateWithValue(IBlockState blockState, Key<? extends BaseValue<E>> key, E value) {
        if (key.equals(Keys.DIRECTION)) {
            final Direction direction = (Direction) value;
            final EnumFacing facing = DirectionResolver.getFor(direction);
            return Optional.of((BlockState) blockState.withProperty(BlockHorizontal.FACING, facing));
        }
        return super.getStateWithValue(blockState, key, value);
    }

Finally, ``getManipulators()`` should return a list of all ``ImmutableDataManipulator``\ s the block supports, along with
the current values for the provided ``IBlockState``. It should include all ``ImmutableDataManipulator``\ s from the
superclass.

.. code-block:: java

    @Override
    public List<ImmutableDataManipulator<?, ?>> getManipulators(IBlockState blockState) {
        return ImmutableList.<ImmutableDataManipulator<?, ?>>builder()
                .addAll(super.getManipulators(blockState))
                .add(new ImmutableSpongeDirectionalData(DirectionResolver.getFor(blockState.getValue(BlockHorizontal.FACING))))
                .build();
    }


Further Information
===================

With ``Data`` being a rather abstract concept in Sponge, it is hard to give general directions on how to
acquire the needed data from the Minecraft classes itself. It may be helpful to take a look at already
implemented processors similar to the one you are working on to get a better understanding of how it should work.

If you are stuck or are unsure about certain aspects, go visit the ``#spongedev`` IRC channel, the forums, or
open up an Issue on GitHub. Be sure to check the `Data Processor Implementation Checklist
<https://github.com/SpongePowered/SpongeCommon/issues/8>`_ for general contribution requirements.
