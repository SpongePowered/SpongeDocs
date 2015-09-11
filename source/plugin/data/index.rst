============
The Data API
============

Overview
========

The unified Data API aims to provide a consistent way of accessing and modifying data of any kind. Where other approaches define the available data using interface and inheritance (like a ``LivingEntity`` interface providing getter and setter functions for current and maximum health), in Sponge every entity, block etc. is completely oblivious to what data it holds. While this may appear less straightforward than direct accessor methods, it is foremost far more extensible. And thanks to the addition of ``Key``\ s, simply accessing specific values is no less straightforward.

.. warning::

    As of writing, the Data API is not yet fully implemented. Refer to the `Implementation Tracker <https://github.com/SpongePowered/SpongeCommon/issues/8>`_, ask in the ``#spongedev`` IRC channel or on the `Forums <https://forums.spongepowered.org>`_ to find out if the data you need to work with is available yet.

Concepts
========

On first glance at the API docs, the data API threatens to overwhelm you with lots of interfaces and packages. But to simply use the data API, you will not have to deal with many of them, as most interfaces found there are just specific data manipulators.

DataHolder
~~~~~~~~~~

A data holder is just that - something that holds data. It provides methods to retrieve and offer back data. The interface itself is completely oblivious to the type of data held. Since only the implementations will possess this knowledge, it is possible to ask a ``DataHolder`` to provide data it does not have or to accept data it cannot use. In those cases, the return values of the methods will provide the information that data is not available (via ``Optional.absent()``) or not accepted.

Property
~~~~~~~~

A property is a read-only point of data. It provides information that is directly derived from its holders type, but, unlike other data, unchangeable (except for core mods). Example for this are the harvesting ablities on tools (represented as ``HarvestingProperty``) or the damage absorption of an equippable armor item.

DataManipulator
~~~~~~~~~~~~~~~

A data manipulator represents points of cohesive data that describes a certain component of its holder. For example ``HealthData``, which contains both current and maximum health. If a data holder has ``HealthData``, it has health that can somehow be depleted and replenished and can die if that health is depleted. This allows for the re-use of such components over the API and prevents duplication of accessor methods - as sheep, stained glass blocks and leather armor all can share the ``DyeableData`` holding the color they are dyed in.

Key
~~~

A ``Key`` is a unique identifier for a single point of data and can be used to directly read or set that point of data without worrying about data manipulators. It was designed to provide a convenient way of accessing data similar to direct getter/setter methods. All keys used within Sponge are listed as constants in the ``org.spongepowered.api.data.key.Keys`` utility class.

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    keys
