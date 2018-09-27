===========
Custom Data
===========

Sponge has a powerful :doc:`data <../index>` system, that can do much more than just vanilla features. It's also 
possible to create your own data objects, allowing you to :doc:`serialize <../serialization>` objects directly to 
players, entities and more!

To start making your own data, we recommend that you read up on each of the components of the ecosystem in the
:doc:`data <../index>` documentation. You should understand how a fully implemented system works before you begin work 
on your own implementation.

There are three main areas of custom data:

- :doc:`DataHolders <dataholders>`, which store data such as items and entities
- :doc:`DataManipulators <datamanipulators>`, which are attached to a ``DataHolder`` and can contain any number of 
  serializable objects. Manipulators will stay attached to their owner, even across reboots
- :doc:`Serialization <serialization>`, which saves/loads data into configs, files, or world files

We will provide and explain snippets of code throughout the tutorials, however we also provide a `full implementation`_
for those that prefer to look through an example implementation.

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    datamanipulators
    datamanipulators-new
    dataholders
    serialization

.. _full implementation: https://github.com/SpongePowered/Cookbook/tree/master/Plugin/MyHomes