=============================
Implementing DataManipulators
=============================

This is a guide for contributors who want to help with Data API implementation by creating DataManipulators.
An updated list of DataManipulators to be implemented can be found at 
https://github.com/SpongePowered/SpongeCommon/issues/8.

To fully implement a ``DataManipulator`` these steps must be followed:

1. Implement the ``DataManipulator`` itself
#. Implement the ``ImmutableDataManipulator``

When these steps are complete, the following must also be done:

3. Register the ``Key`` in the ``KeyRegistry``
#. Implement the ``DataProcessor``
#. Implement the ``ValueProcessor`` for each value being represented by the ``DataManipulator``

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
If you do, you must call ``registerFieldGetter()`` and ``registerFieldSetter`` in your constructor.
Don't forget the ``Field`` registration!

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
particular set of data in one place, we have ``DataProcessors`` to do just that.
Currently, a ``DataProcessor`` has the following methods:

.. code-block:: java

 public interface DataProcessor<M extends DataManipulator<M, I>, I extends ImmutableDataManipulator<I, M>> {

    int getPriority();

    boolean supports(DataHolder dataHolder);

    Optional<M> from(DataHolder dataHolder);

    Optional<M> createFrom(DataHolder dataHolder);

    Optional<M> fill(DataHolder dataHolder, M manipulator, MergeFunction overlap);

    Optional<M> fill(DataContainer container, M m);

    DataTransactionResult set(DataHolder dataHolder, M manipulator, MergeFunction function);

    Optional<I> with(Key<? extends BaseValue<?>> key, Object value, I immutable);

    DataTransactionResult remove(DataHolder dataHolder);
 }

The first thing that we notice is a ``getPriority()`` method. This is used for multi-processor registration, or in more
plain terms: A multi-processor registration system for mods to provide their own compatibility processors for their
custom entity/itemstack/tileentity/blockstate types. The higher the priority return, the earlier the processor is used
in what's called the ``DataProcessorDelegate``. The delegate is a collection of registered ``DataProcessor`` of a
particular ``DataManipulator`` such that all registered ``DataProcessor`` can be used.

Next up, we have ``supports(DataHolder)``. This is really simple, if your ``DataManipulator`` is supposed to be
"supported" by the provided ``DataHolder`` (which can be an ItemStack, Entity, TileEntity, etc.) you can define this
"support" in this method. So, if I have a ``VelocityDataProcessor``, my ``support(DataHolder)`` would look something like so:

.. code-block:: java

 @Override
 public boolean supports(DataHolder dataHolder) {
  return dataHolder instanceof Entity;
 }


5. Implement the ValueProcessor
===============================

6. Examples
===========

Several ``DataManipulators`` have already been implemented. Have a look at them to get a quick overview on how to implement your own ``Manipulator``.
Here are some examples which might help you understanding the concept of ``DataManipulator``:

* `VelocityData <https://github.com/SpongePowered/SpongeCommon/commit/ab47f2681dd382a44f1d32d92858bd29c2910ff3>`_
* `BreathingData <https://github.com/SpongePowered/SpongeCommon/commit/f461697e0a6de7840e7cdb6e739d97cb176d7617>`_
* `FoodData <https://github.com/SpongePowered/SpongeCommon/commit/19c13cb71ea3e1d8cd67372b7f272fe298c21902>`_
