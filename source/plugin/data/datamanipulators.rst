=================
Data Manipulators
=================

.. javadoc-import::
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.DataTransactionResult
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.data.manipulator.ImmutableDataManipulator
    org.spongepowered.api.data.manipulator.immutable.entity.ImmutableHealthData
    org.spongepowered.api.data.manipulator.mutable.DisplayNameData
    org.spongepowered.api.data.manipulator.mutable.entity.HealthData
    org.spongepowered.api.data.manipulator.mutable.entity.InvisibilityData
    org.spongepowered.api.data.value.mutable.MutableBoundedValue
    org.spongepowered.api.entity.living.player.Player

Accessing and modifying data
============================

A data manipulator represents a certain component and all of its data. It stores a representation of that data and can
be offered to or created from data holders which possess a matching component. Again, let's use an example. And again
try to heal someone (or something).

**Code Example: Healing with data manipulators**

.. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.DataTransactionResult;
    import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
    import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

    import java.util.Optional;

    public static DataTransactionResult heal(DataHolder target) {
        Optional<HealthData> healthOptional = target.get(HealthData.class);
        if (healthOptional.isPresent()) {
            HealthData healthData = healthOptional.get();

            double maxHealth = healthData.maxHealth().get();
            MutableBoundedValue<Double> currentHealth = healthData.health();
            currentHealth.set(maxHealth);
            healthData.set(currentHealth);

            target.offer(healthData);
        }
    }

First we need to check if our target has health data. We do so by first asking it to provide us with its health
data by passing its class to the ``get()`` method. We get an ``Optional`` which we can use for our check.
This ``Optional`` will be absent if either our target does not support :javadoc:`HealthData` or if it supports it but
at the present moment does not hold any health data.

If the health data is present, it now contains a mutable copy of the data present on the data holder. We make
our alterations and finally offer the changed data back to our target, where it is accepted (again, ``offer()``
will return a :javadoc:`DataTransactionResult` which we will disregard in this example and get back to
:doc:`at a later point <transactions>`).

As you can see, the results for ``health()`` and ``maxHealth()`` are again keyed values we obtain from the
:javadoc:`DataHolder`. As the :javadoc:`MutableBoundedValue` we receive from calling ``health()`` again just contains a
copy of the data, we first need to apply our changes back to the :javadoc:`DataManipulator` before we can offer the
``healthData`` back to our target.

.. tip::

    Rule #1 of the Data API: Everything you receive is a copy. So whenever you change something, make sure that
    your change is propagated back to where the original value came from.

Another possible modification is fully removing a ``DataManipulator``. This is done via the ``remove()`` method which
accepts a class reference for the type of ``DataManipulator`` to remove. Some data cannot be removed and attempts to
do so will always return a ``DataTransactionResult`` indicating failure. The following code attempts to remove a
custom name from a given ``DataHolder``. Again, the result of the transaction is discarded.

**Code Example: Removing a custom display name**

.. code-block:: java

    import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;

    public void removeName(DataHolder target) {
        dataHolder.remove(DisplayNameData.class);
    }

DataManipulator vs. Keys
========================

If you compared both of our healing examples, you may wonder 'Why bother with data manipulators anyway, keys are
so much easier' and be right - for getting and setting single values. But the true merit of a data manipulator is
that it contains *all* data pertaining to a certain component. Let us take a look at another example.

**Code Example: Swapping two data holders' health**

.. code-block:: java

    public void swapHealth(DataHolder targetA, DataHolder targetB) {
        if (targetA.supports(HealthData.class) && targetB.supports(HealthData.class)) {
            HealthData healthA = targetA.getOrCreate(HealthData.class).get();
            HealthData healthB = targetB.getOrCreate(HealthData.class).get();
            targetA.offer(healthB);
            targetB.offer(healthA);
        }
    }

First we check if both targets support HealthData. If they do, we save the health of both in one variable each. We
don't need to bother with ``Optional`` this time since we verified that ``HealthData`` is supported and the
``getOrCreate()`` method ensures that even if no data is present, default values are generated.

Then we just offer the saved health data to the *other* target, thus switching their health status with each other.

This example done with ``Keys`` would be a bit longer and more complicated since we'd have to take care of each
individual key by ourself. And if instead of health we swapped another data manipulator containing even more data
(maybe :javadoc:`InvisibilityData` which even contains a list), we'd have a lot more work to do. But since the data
holder itself takes care of all data pertaining to it, we could even modify the above function to swap arbitrary data
between two holders.

**Code Example: Swapping any data manipulator**

.. code-block:: java

    import org.spongepowered.api.data.manipulator.DataManipulator;

    public  <T extends DataManipulator<?,?>> void swapData(DataHolder targetA, DataHolder targetB, Class<T> dataClass) {
       if (targetA.supports(dataClass) && targetB.supports(dataClass)) {
           T dataA = targetA.getOrCreate(dataClass).get();
           T dataB = targetB.getOrCreate(dataClass).get();
           targetA.offer(dataB);
           targetB.offer(dataA);
       }
    }

The ability to write a function that can just swap any data on a data holder with the same data on another data
holder demonstrates the core design goal of the Data API: Maximum compatibility across the API.

Mutable vs. Immutable Data Manipulators
=======================================

To every data manipulator, there is a matching :javadoc:`ImmutableDataManipulator`. For instance, both ``HealthData``
and :javadoc:`ImmutableHealthData` contain the same data, only the latter returns new instances when requesting modified
data.

Conversion between mutable and immutable data manipulators is done via the ``asImmutable()`` and ``asMutable()``
methods, which each will return a copy of the data. The only way to obtain an immutable data manipulator
from a data holder is obtaining a mutable one and then using ``asImmutable()``.

A possible use case for this would be a custom event fired when someone is healed. It should provide copies of
the health data before and after, but event listeners should not be able to change them. Therefore we can write
our event to only provide ``ImmutableHealthData`` instances. That way, even if third party code gets to interact
with our data, we can rest assured that it will not be changed.

Absent Data
===========

As mentioned above, the ``get()`` method may return an empty ``Optional`` if one of the following is true:

* The ``DataHolder`` does not support the given ``DataManipulator``
* The ``DataHolder`` does support the ``DataManipulator``, but currently holds no data of that type

There is a big semantic difference between data not being present and the data consisting of default values. While the
latter is always possible, there are cases where it is impossible for a ``DataHolder`` to support a type of data and
then not hold it. Examples of those include:

* ``HealthData`` is always present on every (vanilla) ``DataHolder`` that supports it
* :javadoc:`DisplayNameData` is always present on a :javadoc:`Player`, but may be absent on other entities.
