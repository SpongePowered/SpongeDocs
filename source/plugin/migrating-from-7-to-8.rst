=============================
Migrating from API 7 to API 8
=============================

.. javadoc-import::
    org.spongepowered.plugin.builtin.jvm.Plugin

SpongeAPI 8 is a significally upgraded API compared to SpongeAPI 7, such that every plugin will need to be updated to
be compatible. While we cannot list every little thing that you will need to change here, this page contains some of
the more common migrations that will be required.

.. note::

    `We provide a plugin template that you can clone to create your own plugins 
    <https://github.com/SpongePowered/sponge-plugin-template> __`.
    While this guide is primarily intend for migrating plugins, you may find it useful to investigate this template to
    help your migration, particularly with the plugin metadata changes.


``@Plugin`` annotation and migration from ``mcmod.info`` to ``sponge_plugins.json``
===================================================================================

SpongeAPI 8 introduces a new :javadoc:`Plugin` annotation that only contains the plugin ID. As a result, the SpongeAPI
build no longer contains an annotation processor that will generate the metadata file for you.

To generate the metadata file, you can either:

* Create the file yourself by creating ``sponge_plugins.json`` in your resources root and filling it out with the required
  information
* Use SpongeGradle 2 and define the metadata in the buildscript `as in this example 
  <https://github.com/SpongePowered/sponge-plugin-template/blob/88d3c35853a687a7dc1540db43a9f9a135c03819/build.gradle.kts#L16-L40> __`

More information about the metadata file can be found at :doc:`plugin-meta`.

Game Lifecycle
==============

The previous lifecycle events (such as ``GamePreInitializationEvent``) have been removed. SpongeAPI 8 uses a clearer 
lifecycle so you know exactly when to register various parts of your plugin. Most of your plugin's setup should now be
done in "Register" events, such as :javadoc:`RegisterCommandEvent` or :javadoc:`RegisterDataEvent`, though there are a
few generic events (such as :javadoc:`LoadedGameEvent`) that may also be used.

More information about the game lifecycle can be found at :doc:`lifecycle`.

Engines
=======

SpongeAPI 8 introduces the concept of an engine. While SpongeAPI 7 was mostly designed for servers, SpongeAPI 8 considers
the client as a first class citizen. The :javadoc:`Server` and :javadoc:`Client` are both :javadoc:`Engine`s.

In general plugin development, it is likely that you will only really consider the server. However, be aware that there
are times when running on the client where the server engine does not exist.

There are generic lifecylce events that fire when each engine starts. You can use the :javadoc:`StartingEngineEvent`,
:javadoc:`StartedEngineEvent` and :javadoc:`StoppingEngineEvent` (if the engine hasn't crashed) events if you need to
be aware of when each engine starts.

As before, engines can restart multiple times within a game instance (generally, this will happen in clients where the
server is started multiple times - a new server is started when a singleplayer game is started.)

CatalogTypes and Registries
===========================

The Sponge registry has been overhauled and ``CatalogType``s no longer exist.

In the previous system, objects had an awareness of their own identifier through the ``CatalogType#getId`` method.
This generally restricted these types to only exist in one registry. In SpongeAPI 8, any object can be placed in 
a registry of the correct type without implementing ``CatalogType``, with the key to the object being provided
separately, allowing an object to exist in multiple registries with different keys.

Additionally, unlike in SpongeAPI 7 and earlier where all registries were global to the game instance, in SpongeAPI 8
and later registries can be scoped to the engine

Plugins that wish to add items to the registry must do so during the :javadoc:`RegisterRegistryValueEvent` for the
:javadoc:`RegistryType` they wish to register the object to. The standard registry types can be found at
:javadoc:`RegistryTypes`. Similarly, plugins that wish to create their own registries can do so during the 
:javadoc:`RegisterRegistryEvent.GameScoped`, :javadoc:`RegisterRegistryEvent.EngineScoped` or 
:javadoc:`RegisterRegistryEvent.WorldScoped` event, depending on what scoping is required.

Custom Data
===========

Data gets an overhaul in SpongeAPI 8, but the most impactful change to consider when migrating plugins is that custom
data is now much simpler to use. In particular, there are two large changes as to how you implement custom data:

* Data is now primarily driven by the :javadoc:`Key` system, rather than ``DataManipulator``s. Keys can be created at
  any time and do not need to be registered.
* Any data supplied to data holders using an unregistered key are transient - for example, if data is supplied to a
  player using an unregistered key and the player dies (so their player object is recreated) that data is lost. To
  persist custom data, plugins must register their keys during the :javadoc:`RegisterDataEvent` and supply a
  :javadoc:`DataRegistration` (via the :javadoc:`DataRegistration.Builder`) that tells Sponge how to persist the data.

In addition, SpongeAPI 8 allows for custom keys to point to external data stores. This can be done by providing a
:javadoc:`DataProvider` to the ``DataRegistration``.

More information about data can be found at :doc:`data/index`

Command Creation and Registration
=================================

Commands have been completely overhauled in SpongeAPI 8 in order to support Minecraft's command completions, as well as
to resolve long standing issues with the previous system. Additionally, commands should now be registered during the
``RegisterCommandEvent``.

SpongeAPI 8 also provides for ways to allow alternate frameworks to integrate at a low-level using 
:javadoc:`CommandRegistrar`.

More information about commands can be found at :doc:`commands/index`.