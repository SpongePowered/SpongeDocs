=============================
Implementing DataManipulators
=============================

This is a guide for contributors who want to help with Data API implementation by creating DataManipulators.
An updated list of DataManipulators to be implemented can be found at
`SpongeCommon Issue #8 <https://github.com/SpongePowered/SpongeCommon/issues/8>`_.

To fully implement a ``DataManipulator`` these steps must be followed:

1. Implement the ``DataManipulator`` itself
#. Implement the ``ImmutableDataManipulator``

When these steps are complete, the following must also be done:

3. Register the ``Key`` in the ``KeyRegistry``
#. Implement the ``DataProcessor``
#. Implement the ``ValueProcessor`` for each value being represented by the ``DataManipulator``
#. Register everything in the ``SpongeSerializationRegistry``

.. note::
    Make sure you follow our :doc:`../guidelines`.

1. Implement the DataManipulator
================================

The naming convention for ``DataManipulator`` implementations is the name of the interface prefixed with "Sponge".
So to implement the ``HealthData`` interface, we create a class named ``SpongeHealthData`` in the appropriate package.
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

In most cases while implementing an abstract Manipulator you want to have two constructors:

* One without arguments (no-args) which calls the second constructor with "default" values
* The second constructor that takes all the values it supports.

The second constructor must

* make a call to the ``AbstractData`` constructor, passing the class reference for the implemented interface.
* make sure the values passed are valid
* call the ``registerGettersAndSetters()`` method

.. code-block:: java

    import static com.google.common.base.Preconditions.checkArgument;

    public class SpongeHealthData {

        public SpongeHealthData() {
            this(DataConstants.DEFAULT_HEALTH, DataConstants.DEFAULT_HEALTH);
        }

        public SpongeHealthData(double currentHealth, double maxHealth) {
            super(HealthData.class);
            checkArgument(currentHealth >= DataConstants.MINIMUM_HEALTH && currentHealth <= (double) Float.MAX_VALUE);
            checkArgument(maxHealth >= DataConstants.MINIMUM_HEALTH && maxHealth <= (double) Float.MAX_VALUE);
            this.currentHealth = currentHealth;
            this.maximumHealth = maxHealth;
            this.registerGettersAndSetters();
        }

        ...

    }

Since we know that both current health and maximum health are bounded values, we need to make sure no values
outside of these bounds can be passed. To achieve this we use guava's ``Preconditions`` of which we import the
required methods statically.

.. note::

    Never use so-called magic values (arbitrary numbers, booleans etc) in your code. Instead, locate the
    ``org.spongepowered.common.data.util.DataConstants`` class and use a fitting constant - or create one, if
    necessary.

Accessors defined by the Interface
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The interface we implement specifies some methods to access ``Value`` objects. For ``HealthData``, those are
``health()`` and ``maxHealth()``. Every call to those should yield a new ``Value``.

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

If no current value is specified, calling ``get()`` on the ``Value`` returns the default value.

Copying and Serialization
~~~~~~~~~~~~~~~~~~~~~~~~~

The two methods ``copy()`` and ``asImmutable()`` are not much work to implement. For both you just need to return
a mutable or an immutable data manipulator respectively, containing the same data as the current instance.

The method ``toContainer()`` is used for serialization purposes. Use a ``MemoryDataContainer`` as the result
and apply to it the values stored within this instance. A ``DataContainer`` is basically a map mapping ``DataQuery``\ s
to values. Since a ``Key`` always contains a corresponding ``DataQuery``, just use those by passing the ``Key`` directly.

.. code-block:: java

    public DataContainer toContainer() {
        return new MemoryDataContainer()
            .set(Keys.HEALTH, this.currentHealth)
            .set(Keys.MAX_HEALTH, this.maximumHealth);
    }

registerGettersAndSetters()
~~~~~~~~~~~~~~~~~~~~~~~~~~~

A ``DataManipulator`` also provides methods to get and set data using keys. The implementation for this is handled
by ``AbstractData``, but we must tell it which data it can access and how. Therefore, in the
``registerGettersAndSetters()`` method we need to do the following for each value:

* register a ``Supplier`` to directly get the value
* register a ``Consumer`` to directly set the value
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
        registerFieldGetter(Keys.HEALTH, () -> SpongeHealthData.this.currentHealth);
        registerFieldSetter(Keys.HEALTH, SpongeHealthData.this::setCurrentHealthIfValid);
        registerKeyValue(Keys.HEALTH, SpongeHealthData.this::health);

        registerFieldGetter(Keys.MAX_HEALTH, () -> SpongeHealthData.this.maximumHealth);
        registerFieldSetter(Keys.MAX_HEALTH, SpongeHealthData.this::setMaximumHealthIfValid);
        registerKeyValue(Keys.MAX_HEALTH, SpongeHealthData.this::maxHealth);
    }

The ``Consumer`` registered as field setter must perform the adequate checks to make sure the supplied value is valid.
This applies especially for ``DataHolder``s which won't accept negative values. If a value is invalid, an
``IllegalArgumentException`` should be thrown.

.. tip::

    The validity criteria for those setters are the same as for the respective ``Value`` object, so you might delegate
    the validity check to a call of ``this.health().set()`` and just set ``this.currentHealth = value`` if the first
    line has no thrown an exception yet.

That's it. The ``DataManipulator`` should be done now.

2. Implement the ImmutableDataManipulator
=========================================

Implementing the ``ImmutableDataManipulator`` is similar to implementing the mutable one.

The only differences are:

* The class name is formed by prefixing the mutable ``DataManipulator``\ s name with ``ImmutableSponge``
* Inherit from ``ImmutableAbstractData`` instead
* Instead of ``registerGettersAndSetters()``, the method is called ``registerGetters()``

When creating ``ImmutableDataHolder``\ s or ``ImmutableValue``\ s, check if it makes sense to use the
``ImmutableDataCachingUtil``. For example if you have ``WetData`` which contains nothing more than a boolean, it
is more feasible to retain only two cached instances of ``ImmutableWetData`` - one for each possible value. For
manipulators and values with many possible values (like ``SignData``) however, caching is proven to be too expensive.

.. tip::

    You should declare the fields of an ``ImmutableDataManipulator`` as ``final`` in order to
    prevent accidental changes.

3. Register the Key in the KeyRegistry
======================================

The next step is to register your ``Key``\ s to the ``KeyRegistry``. To do so, locate the
``org.spongepowered.common.data.key.KeyRegistry`` class and find the static ``generateKeyMap()`` function.
There add a line to register (and create) your used keys.

.. code-block:: java

    keyMap.put("health"), makeSingleKey(Double.class, MutableBoundedValue.class, of("Health")));
    keyMap.put("max_health", makeSingleKey(Double.class, MutableBoundedValue.class, of("MaxHealth")));


The ``keyMap`` maps strings to ``Key``\ s. The string used should be the corresponding constant name from
the ``Keys`` utility class in lowercase. The ``Key`` itself is created by one of the static methods
provided by ``KeyFactory``, in most cases ``makeSingleKey``. ``makeSingleKey`` requires first a class reference
for the underlying data, which in our case is a "Double", then a class reference for the ``Value`` type used.
The third argument is the ``DataQuery`` used for serialization. It is created from the statically imported
``DataQuery.of()`` method accepting a string. This string should also be the constant name, stripped of
underscores and capitalization changed to upper camel case.


4. Implement the DataProcessors
===============================

Next up is the ``DataProcessor``. A ``DataProcessor`` serves as a bridge between our ``DataManipulator`` and
Minecraft's objects. Whenever any data is requested from or offered to ``DataHolders`` that exist in Vanilla
Minecraft, those calls end up being delegated to a ``DataProcessor`` or a ``ValueProcessor``.

For your name, you should use the name of the ``DataManipulator`` interface and append ``Processor``. Thus for ``HealthData`` we create a ``HealthDataProcessor``.

In order to reduce boilerplate code, the ``DataProcessor`` should inherit from the appropriate abstract class in
the ``org.spongepowered.common.data.processor.common`` package. Since health can only be present on certain
entities, we can make use of the ``AbstractEntityDataProcessor`` which is specifically targeted at ``Entities``
based on ``net.minecraft.entity.Entity``. ``AbstractEntitySingleDataProcessor`` would require less
implementation work, but cannot be used as ``HealthData`` contains more than just one value.

.. code-block:: java

    public class HealthDataProcessor extends AbstractEntityDataProcessor<EntityLivingBase, HealthData, ImmutableHealthData> {
        public HealthDataProcessor() {
            super(EntityLivingBase.class);
        }
        [...]
    }

Depending on which abstraction you use, the methods you have to implement may differ greatly, depending on how
much implementation work already could be done in the abstract class. Generally, the methods can be categorized.

.. tip::

    It is possible to create multiple ``DataProcessor``\ s for the same data. If vastly different ``DataHolder``\ s
    should be supported (for example both a ``TileEntity`` and a matching ``ItemStack``), it may be beneficial to
    create one processor for each type of ``DataHolder`` in order to make full use of the provided abstractions.
    Make sure you follow the package structure for items, tileentities and entities.

Validation Methods
~~~~~~~~~~~~~~~~~~

Always return a boolean value. If the method is called ``supports()`` it should perform a general check if the supplied target generally supports the kind of data handled by our ``DataProcessor``.

For our ``HealthDataProcessor`` ``supports()`` is implemented by the ``AbstractEntityDataProcessor``. Per
default, it will return true if the supplied argument is an instance of the class specified when calling the
``super()`` constructor.

Instead, we are required to provide a ``doesDataExist()`` method. Since the abstraction does not know how to
obtain the data, it leaves this function to be implemented. As the name says, the method should check if the data
already exists on the supported target. For the ``HealthDataProcessor``, this always returns true, since every
living entity always has health.

.. code-block:: java

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

    protected boolean set(EntityLivingBase entity, Map<Key<?>, Object> keyValues) {
        entity.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(((Double) keyValues.get(Keys.MAX_HEALTH)).floatValue());
        entity.setHealth(((Double) keyValues.get(Keys.HEALTH)).floatValue());
        return true;
    }

.. tip::

    To understand ``DataTransactionResult`` \ s, check the :doc:`corresponding docs page
    <../../plugin/data/transactions>` and refer to the
    :javadoc:`org.spongepowered.api.data.DataTransactionResult.Builder` docs to create one.

.. warning::

    Especially when working with ``ItemStack``\ s it is likely that you will need to deal with ``NBTTagCompound``\ s
    directly. Many NBT keys are already defined as constants in the ``org.spongepowered.common.data.util.NbtDataUtil``
    class. If your required key is not there, you need to add it in order to avoid 'magic values' in the code.

Removal Method
~~~~~~~~~~~~~~

The ``remove()`` method attempts to remove data from the ``DataHolder`` and returns a ``DataTransactionResult``.

Removal is not abstracted in any abstract ``DataProcessor`` as the abstractions have no way of knowing if the data
is always present on a compatible ``DataHolder`` (like ``WetData`` or ``HealthData``) or if it may or may not be present
(like ``LoreData``). If the data is always present, ``remove()`` must always fail. If it may or may not be present,
``remove()`` should remove it. In such cases the ``doesDataExist()`` method should be overridden.

Since a living entity *always* has health, ``HealthData`` is always present and removal therefore not supported.
Therefore we just return ``failNoData()`` and do not override the ``doesDataExist()`` method.

.. code-block:: java

    public DataTransactionResult remove(DataHolder dataHolder) {
        return DataTransactionBuilder.failNoData();
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
    (see above), it is imperative that you override the ``doesDataExist()`` method so that it returns ``true``
    if the data is present and ``false`` if it is not.

.. code-block:: java

    protected Map<Key<?>, ?> getValues(EntityLivingBase entity) {
        final double health = entity.getHealth();
        final double maxHealth = entity.getMaxHealth();
        return ImmutableMap.<Key<?>, Object>of(Keys.HEALTH, health, Keys.MAX_HEALTH, maxHealth);
    }

Filler Methods
~~~~~~~~~~~~~~

A filler method is different from a getter method in that it receives a ``DataManipulator`` to fill with values.
These values either come from a ``DataHolder`` or have to be deserialized from a ``DataContainer``. The method
returns ``Optional.empty()`` if the ``DataHolder`` is incompatible.

``AbstractEntityDataProcessor`` already handles filling from ``DataHolders`` by creating a ``DataManipulator``
from the holder and then merging it with the supplied manipulator, but the ``DataContainer`` deserialization it
can not provide.

.. code-block:: java

    public Optional<HealthData> fill(DataContainer container, HealthData healthData) {
        final Optional<Double> health = container.getDouble(Keys.HEALTH.getQuery());
        final Optional<Double> maxHealth = container.getDouble(Keys.MAX_HEALTH.getQuery());
        if (health.isPresent() && maxHealth.isPresent()) {
            healthData.set(Keys.HEALTH, health.get());
            healthData.set(Keys.MAX_HEALTH, maxHealth.get());
            return Optional.of(healthData);
        }
        return Optional.empty();
    }

The ``fill()`` method is to return an ``Optional`` of the altered healthData, if and only if all required data could
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

    public class HealthValueProcessor extends AbstractSpongeValueProcessor<EntityLivingBase, Double,
        MutableBoundedValue<Double> {

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

    protected MutableBoundedValue<Double> constructValue(Double value) {
        return SpongeValueFactory.boundedBuilder(Keys.HEALTH)
            .minimum(DataConstants.MINIMUM_HEALTH)
            .maximum((double) Float.MAX_VALUE)
            .defaultValue(DataConstants.DEFAULT_HEALTH)
            .actualValue(value)
            .build();
    }

    protected ImmutableValue<Double> constructImmutableValue(Double value) {
        return constructValue(value).asImmutable();
    }



.. code-block:: java

    protected Optional<Double> getVal(EntityLivingBase container) {
        return Optional.of((double) container.getHealth());
    }

Since it is impossible for an ``EntityLivingBase`` to not have health, this method will never return
``Optional.empty()``.

.. code-block:: java

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

    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        return DataTransactionBuilder.failNoData();
    }

Since the data is guaranteed to be always present, attempts to remove it will just fail.

6. Register Processors
======================

In order for Sponge to be able to use our manipulators and processors, we need to register them. This is done
in the ``org.spongepowered.common.data.SpongeSerializationRegistry`` class. In the ``setupSerialization`` method
there are two large blocks of registrations to which we add our processors.

DataProcessors
~~~~~~~~~~~~~~

A ``DataProcessor`` is registered alongside the interface and implementation classes of the ``DataManipulator`` it
handles. For every pair of mutable / immutable ``DataManipulator``\ s at least one ``DataProcessor`` must be registered.

.. code-block:: java

    dataRegistry.registerDataProcessorAndImpl(HealthData.class, SpongeHealthData.class,
        ImmutableHealthData.class, ImmutableSpongeHealthData.class,
        new HealthDataProcessor());


ValueProcessors
~~~~~~~~~~~~~~~

Value processors are registered at the bottom of the very same function. For each ``Key`` multiple processors
can be registered by subsequent calls of the ``registerValueProcessor()`` method.

.. code-block:: java

    dataRegistry.registerValueProcessor(Keys.HEALTH, new HealthValueProcessor());
    dataRegistry.registerValueProcessor(Keys.MAX_HEALTH, new MaxHealthValueProcessor());


Further Information
===================

With ``Data`` being a rather abstract concept in Sponge, it is hard to give general directions on how to
acquire the needed data from the Minecraft classes itself. It may be helpful to take a look at already
implemented processors similar to the one you are working on to get a better understanding of how it should work.

If you are stuck or are unsure about certain aspects, go visit the ``#spongedev`` IRC channel, the forums, or
open up an Issue on GitHub. Be sure to check the `Data Processor Implementation Checklist
<https://github.com/SpongePowered/SpongeCommon/issues/8>`_ for general
contribution requirements.
