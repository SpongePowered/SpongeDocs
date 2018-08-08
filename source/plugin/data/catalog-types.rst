=============
Catalog Types
=============

.. javadoc-import::

    org.spongepowered.api.CatalogType
    org.spongepowered.api.CatalogTypes
    org.spongepowered.api.GameRegistry
    org.spongepowered.api.text.translation.Translatable
    org.spongepowered.api.util.annotation.CatalogedBy

A big part of the data API consists of special values with a specific meaning. In previous versions of Minecraft these
have been numbers such as ``1`` that meant something specific in a given context, for example a simple stone in
the ``/give`` command. From version to version Minecraft moved away from these magics numbers and moved to a more
declarative way using (namespaced) identifiers such as ``minecraft:stone``. This however brought up two big issues with
these values:

1. How do I persist them and reload them?
2. Which values do exist?

Because of this, Sponge introduced a new class of data: :javadoc:`CatalogType`\s.

``CatalogType``\s provide two methods that make them very versatile.

First, the :javadoc:`CatalogType#getId()` method that provides an easy access to a unique identifier (within that type
of data) that can be used to save it to a config file and retrieve it back using the 
:javadoc:`GameRegistry#getType(Class, String)` method. In fact you don't have to do this yourself, you can rely on
Sponge's :doc:`config serialization <../configuration/serialization>` to do this for you instead. 

Some ids are not really human readable, so there is another method for ``CatalogType``\s that tries to return a human
readable name. You can also check whether your type is :javadoc:`Translatable`, which gives you a good name to show to
your players using their locale.

GameRegistry
============

The :javadoc:`GameRegistry` gives you access to all variants of a ``CatalogType``\s using the
:javadoc:`GameRegistry#getAllOf(Class)` method. It also offers various other ways of retrieving a ``CatalogType`` and
related classes such as builders for most classes including data classes. You can also get more specific registries from
there such as the ``VillagerRegistry`` and the recipe registries.

Usage in Commands
=================

``CatalogType``\s can be easily used in :doc:`../commands/index` using the ``catalogedElement`` which will automatically
support auto completion for all possible variants of the given type. Read more about it in the
:doc:`../commands/arguments` section.

Catalog Classes
===============

Sometimes we just want to reference a ``CatalogType`` entry in our code without actually getting it from the registry;
this is usually the case for default values. To simplify these use-cases there are additional catalog classes which
contain static references to types that are either provided by Minecraft itself or Sponge. You can locate these classes
by following the :javadoc:`CatalogedBy` annotation on your ``CatalogType`` class. If the annotation is missing on that
class please also check the parent and child classes. If the class has too many variants or variants can be added and
removed at runtime then there won't be a catalog class. A list of all native catalog types can be found in
:javadoc:`CatalogTypes`.

.. warning::

    Do not use those static references inside your own static fields, because the references are initialized only during
    game startup and thus might still contain their dummy values. Using these dummy values will cause exceptions that
    might be hard to trace back to their origin.