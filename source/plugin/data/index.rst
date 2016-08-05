============
The Data API
============

.. javadoc-import::
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.key.Key
    org.spongepowered.api.data.key.Keys
    org.spongepowered.api.data.manipulator.mutable.DyeableData
    org.spongepowered.api.data.manipulator.mutable.entity.HealthData
    org.spongepowered.api.data.property.item.DamageAbsorptionProperty
    org.spongepowered.api.data.property.item.HarvestingProperty

The unified Data API aims to provide a consistent way of accessing and modifying data. 'Data', in this context
means any data that is consistently synchronized between client and server. It can be changed server-side and
then those changes will be synchronized to the connected clients. This includes, among many others, the text on
a sign, the looks of a horse or the health of any living entity.

Where other approaches define the available data using interfaces and inheritance (like a ``LivingEntity``
interface providing getter and setter functions for current and maximum health), in Sponge every entity, block
etc. is completely oblivious to what data it holds. While this may appear less straightforward than direct
accessor methods, it is foremost far more extensible. And thanks to the addition of :javadoc:`Key`\ s, simply accessing
specific values is no less straightforward.

.. warning::

    As of writing, the Data API is not yet fully implemented. Refer to the `Implementation Tracker
    <https://github.com/SpongePowered/SpongeCommon/issues/8>`_, ask in the ``#spongedev`` IRC channel or on the
    `Forums <https://forums.spongepowered.org>`_ to find out if the data you need to work with is available yet.

Concepts
========

On first glance at the API docs, the data API threatens to overwhelm you with lots of interfaces and packages. But
to simply use the data API, you will not have to deal with many of them, as most interfaces found there are just
specific data manipulators.

DataHolder
~~~~~~~~~~

A data holder is just that - something that holds data. It provides methods to retrieve and offer back data. The
interface itself is completely oblivious to the type of data held. Since only the implementations will possess
this knowledge, it is possible to ask a :javadoc:`DataHolder` to provide data it does not have or to accept data it
cannot use. In those cases, the return values of the methods will provide the information that data is not available
(via ``Optional.empty()``) or not accepted (via the :doc:`DataTransactionResult <transactions>`).

Property
~~~~~~~~

A property too is data, but not synchronized between server and clients. Therefore, it can only be
changed by modifications present on both client and server. Since Sponge is not intended to require a
client-side counterpart, properties are not modifiable.
Examples of properties are the harvesting ablities on tools (represented as :javadoc:`HarvestingProperty` or the damage
absorption of an equippable armor item (represented as :javadoc:`DamageAbsorptionProperty`).

DataManipulator
~~~~~~~~~~~~~~~

A data manipulator represents points of cohesive data that describes a certain component of its holder. For
example :javadoc:`HealthData`, which contains both current and maximum health. If a data holder has ``HealthData``, it
has health that can somehow be depleted and replenished and can die if that health is depleted. This allows for the
re-use of such components over the API and prevents duplication of accessor methods. For example, sheep, stained glass
blocks and leather armor all can share the :javadoc:`DyeableData` holding the color they are dyed in.

Key
~~~

A ``Key`` is a unique identifier for a single point of data and can be used to directly read or set that point of
data without worrying about data manipulators. It was designed to provide a convenient way of accessing data
similar to direct getter/setter methods. All keys used within Sponge are listed as constants in the
:javadoc:`Keys` utility class.

Value
~~~~~

Within the Data API, a value referred to by a ``Key`` is encoded in a container object. For this documentation,
it is referred to as 'keyed value' to avoid confusion with the actual value. A keyed value encapsulates the
actual data value (if it is present), a default value (to be used if no direct value is present) and the Key by
which the value is identified.

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    keys
    datamanipulators
    transactions
    serialization
