=================
Data Manipulators
=================

Accessing and modifying data
============================

A data manipulator represents a certain component and all of its data. It stores a representation of that data and can
be offered to or created from data holders which possess a matching component. Again, let's use an example. And again
try to heal someone (or something).

**Code Example: Healing with data manipulators**

.. code-block:: java

    public static DataTransactionResult heal(DataHolder target) {
        Optional<HealthData> healthOptional = target.getOrCreate(HealthData.class);
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
data by passing its class to the ``getOrCreate()`` method. We get an ``Optional`` which we can use for our check.
If the target does not support health data, it will be absent. But if the health data is present, it now contains
a mutable copy of the data present on the data holder. We make our alterations and finally offer the changed data
back to our target, where it is accepted (again, ``offer()`` will return a ``DataTransactionResult`` which we will
just discard here).

As you can see, the results for ``health()`` and ``maxHealth()`` are again value containers we obtain from the
``DataHolder``. As the ``MutableBoundedValue`` we receive from calling ``health()`` again just contains a copy of
the data, we first need to apply our changes back to the ``DataManipulator`` before we can offer the
``healthData`` back to our target.

.. tip::

    Rule #1 of the Data API: Everything you receive is a copy. So whenever you change something, make sure that
    your change is propagated back to where the original value came from.


DataManipulator vs. Keys
========================

If you compared both of our healing examples, you may wonder 'Why bother with data manipulators anyway, keys are
so much easier' and be right - for getting and setting single values. But the true merit of a data manipulator is
that it contains *all* data pertaining to a certain component. Let us take a look at another example.

**Code Example: Swapping two data holders' health**

.. code-block:: java

    public void swapHealth(DataHolder targetA, DataHolder targetB) {
        if (targetA.supports(HealthData.class) && targetB.supports(HealthData.class)) {
            HealthData healthA = targetA.get(HealthData.class).get();
            HealthData healthB = targetB.get(HealthData.class).get();
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
(maybe ``InvisibilityData`` which even contains a list), we'd have a lot more work to do. But since the data
holder itself takes care of all data pertaining to it, we could even modify the above function to swap arbitrary
data between two holders.

**Code Example: Swapping any data manipulator**

.. code-block:: java

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

To every data manipulator, there is a matching ``ImmutableDataHolder``. For instance, both ``HealthData`` and
``ImmutableHealthData`` contain the same data, only the latter does not provide any means to make alterations to
the data.

Conversion between mutable and immutable data manipulators is done via the ``asImmutable()`` and ``asMutable()``
methods, which each will return a copy of the data. Since the only way to obtain an immutable data manipulator
from a data holder is obtaining a mutable one and then using ``asImmutable()``, in terms of processing power it
might be cheaper to only use immutable data holders if it is to be passed around.

A possible use case for this would be a custom event fired when someone is healed. It should provide copies of
the health data before and after, but event listeners should not be able to change them. Therefore we can write
our event to only provide ``ImmutableHealthData`` instances. That way, even if third party code gets to interact
with our data, we can rest assured that it will not be changed.
