============
Transactions
============

.. javadoc-import::
    org.spongepowered.api.data.DataTransactionResult
    org.spongepowered.api.data.DataTransactionResult.Type
    org.spongepowered.api.data.manipulator.mutable.entity.HealthData
    org.spongepowered.api.world.Location

Reading the Result
==================

For everything you ``offer`` to a data holder, the ``offer`` method will yield a :javadoc:`DataTransactionResult`. This
object will contain the following:

Type
~~~~

The :javadoc:`DataTransactionResult.Type` indicates whether the transaction was completed
successfully and, if not, how it failed.

+---------------+----------------------------------------------------------------------------+
| ``UNDEFINED`` | No clear result for the transaction - indicates that something went wrong  |
+---------------+----------------------------------------------------------------------------+
| ``SUCCESS``   | Transaction was completed successfully                                     |
+---------------+----------------------------------------------------------------------------+
| ``FAILURE``   | Transaction failed for expected reasons (e.g. incompatible data)           |
+---------------+----------------------------------------------------------------------------+
| ``ERROR``     | Transaction failed for unexpected reasons                                  |
+---------------+----------------------------------------------------------------------------+
| ``CANCELLED`` | An event for this transaction was cancelled                                |
+---------------+----------------------------------------------------------------------------+

The affected Data
~~~~~~~~~~~~~~~~~

The result also provides a couple of immutable lists containing immutable value containers representing
the data that was involved in the transaction.

+-------------------------+---------------------------------------------------------------+
| ``getSuccessfulData()`` | contains all data that was successfully set                   |
+-------------------------+---------------------------------------------------------------+
| ``getReplacedData()``   | contains all data that got replaced by successfully set data  |
+-------------------------+---------------------------------------------------------------+
| ``getRejectedData()``   | contains all data that could not be set                       |
+-------------------------+---------------------------------------------------------------+

Examples
========

Healing a Player
~~~~~~~~~~~~~~~~

Surely you remember the healing example in the :doc:`keys` page. Imagine a player who is down to half a heart
(which equals 1 health) being healed that way. The ``DataTransactionResult`` in that case would look like this:

- ``getType()`` would return ``SUCCESS``
- ``getRejectedData()`` would be an empty list
- ``getReplacedData()`` would contain one value container for the ``Keys.HEALTH`` key with a value of 1.0
- ``getSuccessfulData()`` would contain one value container for the ``Keys.HEALTH`` key with a value of 20.0

Now what would be different if we used the healing example from the :doc:`datamanipulators` page instead? Since the
:javadoc:`HealthData` data manipulator contains values for both the current and the maximum health, in addition to the
above result, both the ``getReplacedData()`` list and the ``getSuccessfulData()`` list would contain one more element:
A value container for the ``Keys.MAX_HEALTH`` key with a value of 20.0.

Offering HealthData to a block of stone
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Now our above-mentioned examples are coded in a such a way that they will fail silently rather than try to offer the
incompatible data. But imagine we took a (fully healed) player's ``HealthData`` and tried to offer it to the
:javadoc:`Location` of the stone block he's currently standing on. We can do this, since ``Location`` is also a data
holder. And if we do, it would reward us with a ``DataTransactionResult`` like this:

- ``getType()`` would return ``FAILURE``
- ``getRejectedData()`` would contain two value containers for the ``HEALTH`` and ``MAX_HEALTH`` keys, each with a value of 20.0
- ``getReplacedData()`` and ``getSuccessfulData()`` would be empty lists

Reverting Transactions
======================

Since everything about a transaction result is immutable, it can serve for documentation of data changes. And it
also allows for those changes it documents to be undone. For that, simply pass a transaction result to the data
holder's ``undo()`` method. This is particularly useful since some data offerings may be partially successful, so
that one or more values are successfully written to the data holder, yet one more value cannot be accepted. Since
you may wish to undo the partial successes.

**Code Example: Reverting a transaction**

.. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.DataTransactionResult;
    import org.spongepowered.api.data.manipulator.DataManipulator;

    public void safeOffer(DataHolder target, DataManipulator data) {
        DataTransactionResult result = target.offer(data);
        if (result.getType() != DataTransactionResult.Type.SUCCESS) {
            target.undo(result);
        }
    }
