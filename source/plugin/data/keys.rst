==========
Using Keys
==========

.. javadoc-import::
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.DataTransactionResult
    org.spongepowered.api.data.key.Key
    org.spongepowered.api.data.key.Keys
    org.spongepowered.api.data.value.BaseValue
    org.spongepowered.api.data.value.mutable.MutableBoundedValue

Getting and offering data using a key
=====================================

A data holder provides methods to retrieve or alter a single point of data identified by a :javadoc:`Key`. Let's just
start out with an example:

**Code Example: Healing a data holder, if possible**

.. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.key.Keys;

    public void heal(DataHolder target) {
        if (target.supports(Keys.HEALTH)) {
            double maxHealth = target.get(Keys.MAX_HEALTH).get();
            target.offer(Keys.HEALTH, maxHealth);
        }
    }

Now for the details of the above function.

The first line checks if our given data holder supports a current health value. Only if it does, it can be healed after
all. Since a data holder can not have current health without having a maximum health and vice versa, a check for
one of the keys using the ``supports()`` method suffices.

The second line uses the ``get()`` function to ask the data holder for its maximum health. Besides
``get()``, the methods ``getOrNull()`` and ``getOrElse()`` exist, all of which accept a ``Key`` as their first
parameter. Generally, ``get()`` should be used, which will return an ``Optional`` of the data requested or
``Optional.empty()`` if the data holder does not support the supplied key. Since we already verified that the
``Key`` is supported, we can just call ``get()`` on the Optional without further checks. We could also use
``getOrNull()`` which is basically a shortcut to call ``get(key).orNull()``, thus getting rid of the
``Optional``. The third possibility would be the ``getOrElse()``, which accepts a default value as a second
parameter to be returned if the value is not present on the data holder.

In the third line, we offer data back to the data holder. We provide a ``Key`` denoting the current health and the
before acquired maximum health, thus healing the data holder to full health. There are a variety of ``offer()``
methods accepting different parameter sets, all of which return a :javadoc:`DataTransactionResult` containing
information if the offer was accepted. For now, we'll use the one accepting a ``Key`` and a corresponding value, but we
will encounter more in the next pages. Since we already know that our offer of current health is accepted (as the data
holder supports it), we can silently discard the result.

It is also possible to completely remove data from a :javadoc:`DataHolder` using the ``remove()`` function. Simply
provide a ``Key`` representing the data you want removed. The following example will attempt to remove a custom name
from a given data holder:

.. code-block:: java

    public void removeName(DataHolder target) {
        target.remove(Keys.DISPLAY_NAME);
    }

Transforming Data
=================

Other than getting, modifying and offering a value, there is another way of interacting with data. Using a data
holder's ``transform()`` method we can pass a ``Key`` and a ``Function``. Internally, the value for the key will be
retrieved and the given function applied to it. The result is then stored under the key and the ``transform()``
method will return a ``DataTransactionResult`` accordingly.

Now, as an example, imagine we want to buff a data holder by doubling his maximum health.

.. code-block:: java

    import java.util.function.Function;

    public void buff(DataHolder target) {
        target.transform(Keys.MAX_HEALTH, new Function<Double,Double>() {
            @Override
            public Double apply(Double input) {
                return (input == null) ? 0 : input * 2;
            }
        });
    }

Or, if you use Java 8, you're able to shorten the line with lambda expressions:

.. code-block:: java

    public void buff(DataHolder target) {
        target.transform(Keys.MAX_HEALTH, d -> (d == null) ? 0 : 2*d);
    }

Note that in both cases we need to make sure our passed function can handle ``null``. You will also notice that no
check has been performed if the target actually supports the :javadoc:`Keys#MAX_HEALTH` key. If a target does not
support it, the ``transform()`` function will fail and return a ``DataTransactionResult`` indicating so.

Keyed Values
============

There are cases where you may care about not only the direct value for a Key, but the keyed value
encapsulating it. In that case, use the ``getValue(key)`` method instead of ``get(key)``. You will receive an
object inheriting from :javadoc:`BaseValue` which contains a copy of the original value. Since we know that current
health is a :javadoc:`MutableBoundedValue`, we can find out the minimum possible value and set our target's health just
a tiny bit above that.

**Code Example: Bring a target to the brink of death**

.. code-block:: java

    import org.spongepowered.api.data.value.mutable.MutableBoundedValue;

    public void scare(DataHolder target) {
        if (target.supports(Keys.HEALTH)) {
            MutableBoundedValue<Double> health = target.getValue(Keys.HEALTH).get();
            double nearDeath = health.getMinValue() + 1;
            health.set(nearDeath);
            target.offer(health);
        }
    }

Again, we check if our target support the health key and then obtain the keyed value. A
``MutableBoundedValue`` contains a ``getMinValue()`` method, so we obtain the minimal value, add 1 and then set
it to our data container. Internally, the ``set()`` method performs a check if our supplied value is valid and
silently fails if it is not. Calling ``health.set(-2)`` would not change the value within ``health`` since it
would fail the validity checks. To finally apply our changes to the target, we need to offer the keyed value
back to it. As a keyed value also contains the ``Key`` used to identify it, calling ``target.offer(health)``
is equivalent to ``target.offer(health.getKey(), health.get())``.
