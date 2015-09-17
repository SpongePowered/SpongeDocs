==========
Using Keys
==========

Getting and offering data using a key
=====================================

Since accessing even a single point of data going from ``DataHolder`` to the correct ``DataManipulator`` tended to be quite tedious and bloat both the code and the import list, a more direct way of accessing values via ``Key``\ s was devised. Let's just start out with an example.

**Code Example: Healing a data holder, if possible**

.. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.key.Keys;

    public void heal(DataHolder target) {
        if (target.supports(Keys.HEALTH)) {
            double maxHealth = target.getOrNull(Keys.MAX_HEALTH);
            target.offer(Keys.HEALTH, maxHealth);
        }
    }

Now for the details of the above function.

The first line checks if our given data holder possesses health. Only if he does, he can be healed after all. Since a data holder can not have health without having a maximum health and vice versa, a check for one of the keys using the ``supports()`` method suffices.

The second line uses the ``getOrNull()`` function to ask the data holder for its maximum health. Besides ``getOrNull()``, the methods ``get()`` and ``getOrElse()`` exist, all of which accept a ``Key`` as their first parameter. Generally, ``get()`` should be used. It will return an ``Optional`` of the data requested or ``Optional.absent()`` if the data holder does not support the supplied key. The ``getOrNull()`` method we used is no more than a shortcut for ``get(key).orNull()``. In our example it is safe to use ``getOrNull()`` since we already verified that the value will be present in the first line and therefore ``getOrNull()`` will just relieve us of the ``Optional``. The third possibility would be the ``getOrElse()``, which accepts a default value as a second parameter to be returned if the value is not present on the data holder.

In the third line, we offer data back to the data holder. We provide a ``Key`` denoting the current health and the before acquired maximum health, thus healing the data holder to full health. There are a variety of ``offer()`` methods accepting different parameter sets, all of which return a ``DataTransactionResult`` containing information if the offer was accepted. For now, we'll use the one accepting a ``Key`` and a corresponding value, but we will encounter more in the next pages. Since we already know that our offer of current health is accepted (as the data holder supports it), we can silently discard the result.

Transforming Data
=================

Other than getting a value, modify it and offer it back, there is another way of modifying data. Using a data holders ``transform()`` method we can pass a ``Key`` and a ``Function``. Internally, the value for the key will retrieved and the given function applied to it. The result is then stored under the key and the ``transform()`` method will return a ``DataTransactionResult`` accordingly.

Now, as an example, imagine we want to buff a data holder by doubling his maximum health.

.. code-block:: java

    public void buff(DataHolder target) {
        target.transform(Keys.MAX_HEALTH, new Function<Double,Double>() {
            @Override
            public Double apply(Double input) {
                return (input == null) ? 0 : input * 2;
            }
        });
    }

Or, since we use Java 8 and are able to make use of its lambda expressions:

.. code-block:: java

    public void buff(DataHolder target) {
        target.transform(Keys.MAX_HEALTH, d -> (d == null) ? 0 : 2*d);
    }

Note that in both cases we need to make sure our passed function can handle ``null``. You will also notice that no check has been performed if the target actually supports the ``MAX_HEALTH`` key. If a target does not support it, the ``transform()`` function will fail and return a ``DataTransactionResult`` indicating so.

Value Containers
================

There are cases where you may care about not only the direct value for a Key, but the value container encapsulating it. In that case, use the ``getValue(key)`` method instead of ``get(key)``. You will receive an object inheriting from ``BaseValue`` which contains a copy of the original value. Since we know that current health is a ``MutableBoundedValue``, we can find out what is the minimum possible value and set our target's health just a tiny bit above that.

**Code example: Bring a target to the brink of death**

.. code-block:: java

    public void scare(DataHolder target) {
        if (target.supports(Keys.HEALTH)) {
            MutableBoundedValue<Double> health = target.getValue(Keys.HEALTH).get();
            double nearDeath = health.getMinValue() + 1;
            health.set(nearDeath);
            target.offer(health);
        }
    }

Again, we check if our target support the health key and then obtain the value container. A ``MutableBoundedValue`` contains a ``getMinValue()`` method, so we obtain the minimal value, add 1 and then set it to our data container. Internally, the ``set()`` method performs a check if our supplied value is valid and silently fails if it is not. Calling ``health.set(-2)`` would not change the value within ``health`` since it would fail the validity checks. To finally apply our changes to the target, we need to offer the value container back to it. As a value container also contains the ``Key`` used to identify it, calling ``target.offer(health)`` is equivalent to ``target.offer(health.getKey(), health.get())``.
