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
#. Register everything in ``SpongeGameRegistry``

.. note::
  Don't forget to add an empty line at the end of every file you create.

1. Implementing the DataManipulator
===================================

Implementing the ``DataManipulator`` is done by extending ``AbstractData`` in the easiest case. Let's assume we have a
single value based manipulator, eg. the ``flying`` state on an entity, which can be ``true`` or ``false``.
We decide to abstract things, so now we're able to just extend ``AbstractBooleanData``.
Here is a short example:

.. code-block:: java

  public class SpongeFlyingData extends AbstractBooleanData<FlyingData, ImmutableFlyingData> implements FlyingData {
 /* your code here */
  }

The next step is to provide the generic ``DataManipulator`` that follows the generic of ``FlyingData``. In this case
we use ``FlyingData.class`` in our ``super`` constructor, followed by the ``Key`` which identifies the recommended
``value`` of the flying state and last but not least the ``Key`` itself: ``Keys.IS_FLYING``.

.. code-block:: java

 public SpongeFlyingData(boolean flying) {
          super(FlyingData.class, flying, Keys.IS_FLYING);
 }

In most cases while implementing an abstract Manipulator you want to have two constructors:

* One without arguments (no-args) which calls the second
* and one single-argument constructor which is actually used for the provided ``value``

Now, after the Manipulator is done you have to check if you are extending ``AbstractData`` or not.
Remember that we decided to extend ``AbstractBooleanData`` above, hence now must call ``registerFieldGetter()`` and
``registerFieldSetter()`` in your constructor. Don't forget the ``Field`` registration!

.. code-block:: java

 private void registerStuff() {
      registerFieldGetter(Keys.IS_FLYING, new GetterFunction<Object>() {
             @Override
             public Value get() {
                 return getflying();
             }
         });
      registerFieldSetter(Keys.IS_FLYING, new SetterFunction<Object>() {
              @Override
              public void set(Object value) {
                   setFlying(((Boolean) value).booleanValue());
              }
          });
  }

That's it. The ``DataManipulator`` should be done now.

2. ImmutableDataManipulator
===========================

Just repeat the above mentioned steps for the ``ImmutableDataManipulator``.

Now, after the ``Manipulators`` are done we have to register the ``KEY`` and finally implement the
``DataProcessor`` and ``ValueProcessor``.

3. Register the Key in the KeyRegistry
======================================

The next step is to register your ``KEYS`` in the ``KeyRegistry``:

.. code-block:: java

 public static void registerKeys() {
      keyMap.put("is_flying", makeSingleKey(Boolean.class, Value.class, of("IsFlying")));
 }


4. Implement the DataProcessor
==============================

Next up is the ``DataProcessor``. A ``DataProcessor`` implements your ``DataManipulator`` to work semi-natively with
Minecraft's objects. Unfortunately, since it's always easier to just directly implement all of the processing of a
particular set of data in one place, we have ``DataProcessor``\s to do just that. To simplify the implementation of
``DataProcessor``\s, ``AbstractDataProcessor`` was developed to reduce boilerplate code. It brought up two additional
``DataProcessor``\s, ``AbstractEntitySingleDataProcessor`` and ``AbstractEntityDataProcessor`` which are specifically
targeted at ``Entities`` based on ``net.minecraft.entity.entity``. These two ``processor``\s reduce the needed code and
leave us with this:


.. code-block:: java

 protected abstract M createManipulator();

  protected boolean supports(E entity) {
      return true;
  }

  protected abstract boolean set(E entity, T value);

  protected abstract Optional<T> getVal(E entity);

  protected abstract ImmutableValue<T> constructImmutableValue(T value);


createManipulator() method
--------------------------

.. code-block:: java

 @Override
 protected HealthData createManipulator() {
    return new SpongeHealthData(20, 20);
 }

doesDataExist() method
----------------------

.. code-block:: java

 @Override
 protected boolean doesDataExist(EntityLivingBase entity) {
    return true;
 }

set() method
------------

.. code-block:: java

 @Override
 protected boolean set(EntityLivingBase entity, Map<Key<?>, Object> keyValues) {
    entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(((Double) keyValues.get(Keys.MAX_HEALTH)).floatValue());
    entity.setHealth(((Double) keyValues.get(Keys.HEALTH)).floatValue());
    return true;
 }

getValues(DataContainer)
------------------------

.. code-block:: java

 @Override
 protected Map<Key<?>, ?> getValues(EntityLivingBase entity) {
    final double health = entity.getHealth();
    final double maxHealth = entity.getMaxHealth();
    return ImmutableMap.<Key<?>, Object>of(Keys.HEALTH, health, Keys.MAX_HEALTH, maxHealth);
 }


fill(DataContainer)
-------------------

.. code-block:: java

 @Override
 public Optional<HealthData> fill(DataContainer container, HealthData healthData) {
    healthData.set(Keys.MAX_HEALTH, getData(container, Keys.MAX_HEALTH));
    healthData.set(Keys.HEALTH, getData(container, Keys.HEALTH));
    return Optional.of(healthData);
 }

remove(DataHolder)
------------------

.. code-block:: java

 @Override
 public DataTransactionResult remove(DataHolder dataHolder) {
    return DataTransactionBuilder.builder().result(DataTransactionResult.Type.FAILURE).build();
 }

5. Implement the ValueProcessor
===============================

6. Register everything in SpongeGameRegistry
============================================

When finally done, you have to register everything in ``SpongeGameRegistry``. As always, add all necessary imports, then the ``DataProcessor`` and
``Databuilder``:

.. code-block:: java

 private void setupSerialization() {
  final FlyingDataProcessor flyingDataProcessor = new FlyingDataProcessor();
        final FlyingDataBuilder flyingDataBuilder = new FlyingDataBuilder();
        service.registerBuilder(FlyingData.class, flyingDataBuilder);
         dataRegistry.registerDataProcessorAndImpl(FlyingData.class, SpongeFlyingData.class, ImmutableFlyingData.class, ImmutableSpongeFlyingData.class, flyingDataProcessor, flyingDataBuilder);
 }

And finally the ``ValueProcessor``:

.. code-block:: java

 private void setupSerialization() {
  dataRegistry.registerValueProcessor(Keys.IS_FLYING, new IsFlyingValueProcessor());
 }

7. Examples
===========

Several ``DataManipulators`` have already been implemented. Have a look at them to get a quick overview on how to implement your own ``Manipulator``.
Here are some examples which might help you understanding the concept of ``DataManipulator``:

* `VelocityData <https://github.com/SpongePowered/SpongeCommon/commit/ab47f2681dd382a44f1d32d92858bd29c2910ff3>`_
* `BreathingData <https://github.com/SpongePowered/SpongeCommon/commit/f461697e0a6de7840e7cdb6e739d97cb176d7617>`_
* `FoodData <https://github.com/SpongePowered/SpongeCommon/commit/19c13cb71ea3e1d8cd67372b7f272fe298c21902>`_
