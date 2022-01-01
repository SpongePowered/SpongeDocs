=============================
Migrating from API 7 to API 8
=============================

.. javadoc-import::
    java.util.concurrent.ExecutorService
    net.kyori.adventure.text.Component
    net.kyori.adventure.text.LinearComponents
    org.spongepowered.api.Client
    org.spongepowered.api.Engine
    org.spongepowered.api.Game
    org.spongepowered.api.Server
    org.spongepowered.api.adventure.SpongeComponents
    org.spongepowered.api.command.Command.Builder
    org.spongepowered.api.command.Command.Parameterized
    org.spongepowered.api.command.registrar.CommandRegistrar
    org.spongepowered.api.data.DataProvider
    org.spongepowered.api.data.DataRegistration
    org.spongepowered.api.data.DataRegistration.Builder
    org.spongepowered.api.data.Key
    org.spongepowered.api.data.Keys
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.entity.living.player.client.ClientPlayer
    org.spongepowered.api.entity.living.player.server.ServerPlayer
    org.spongepowered.api.event.lifecycle.LoadedGameEvent
    org.spongepowered.api.event.lifecycle.ProvideServiceEvent
    org.spongepowered.api.event.lifecycle.RegisterCommandEvent
    org.spongepowered.api.event.lifecycle.RegisterDataEvent
    org.spongepowered.api.event.lifecycle.RegisterRegistryEvent
    org.spongepowered.api.event.lifecycle.RegisterRegistryEvent.EngineScoped
    org.spongepowered.api.event.lifecycle.RegisterRegistryEvent.GameScoped
    org.spongepowered.api.event.lifecycle.RegisterRegistryEvent.WorldScoped
    org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent
    org.spongepowered.api.event.lifecycle.StartedEngineEvent
    org.spongepowered.api.event.lifecycle.StartingEngineEvent
    org.spongepowered.api.event.lifecycle.StoppingEngineEvent
    org.spongepowered.api.registry.RegistryType
    org.spongepowered.api.registry.RegistryTypes
    org.spongepowered.api.scheduler.Scheduler
    org.spongepowered.api.scheduler.Task
    org.spongepowered.api.scheduler.TaskExecutorService
    org.spongepowered.api.world.Location
    org.spongepowered.api.world.World
    org.spongepowered.api.world.client.ClientLocation
    org.spongepowered.api.world.client.ClientWorld
    org.spongepowered.api.world.server.ServerLocation
    org.spongepowered.api.world.server.ServerWorld
    org.spongepowered.plugin.builtin.jvm.Plugin

SpongeAPI 8 is a significally upgraded API compared to SpongeAPI 7, such that every plugin will need to be updated to
be compatible. While we cannot list every little thing that you will need to change here, this page contains some of
the more common migrations that will be required.

.. note::
    `We provide a plugin template that you can clone to create your own plugins <https://github.com/SpongePowered/sponge-plugin-template>`__.
    While this guide is primarily intend for migrating plugins, you may find it useful to investigate this template to
    help your migration, particularly with the plugin metadata changes.

``@Plugin`` annotation and migration from ``mcmod.info`` to ``sponge_plugins.json``
===================================================================================

SpongeAPI 8 introduces a new :javadoc:`Plugin` annotation that only contains the plugin ID. As a result, the SpongeAPI
build no longer contains an annotation processor that will generate the metadata file for you.

To generate the metadata file, you can either:

- Create the file yourself by creating ``sponge_plugins.json`` in your resources root and filling it out with the required
  information
- Use SpongeGradle 2 and define the metadata in the buildscript `as in this example 
  <https://github.com/SpongePowered/sponge-plugin-template/blob/88d3c35853a687a7dc1540db43a9f9a135c03819/build.gradle.kts#L16-L40>`__

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
the client as a first class citizen. The :javadoc:`Server` and :javadoc:`Client` are both :javadoc:`Engine`.

In general plugin development, it is likely that you will only really consider the server - this is true even in 
singleplayer environments as the game client starts a singleplayer server. However, be aware that there are times when
running on the client where the server engine does not exist.

There are generic lifecylce events that fire when each engine starts. You can use the :javadoc:`StartingEngineEvent`,
:javadoc:`StartedEngineEvent` and :javadoc:`StoppingEngineEvent` (if the engine hasn't crashed) events if you need to
be aware of when each engine starts.

As before, engines can restart multiple times within a game instance (generally, this will happen in clients where the
server is started multiple times - a new server is started when a singleplayer game is started.)

Migrating to Engine Scoped Objects
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

With the introduction of ``Engines``, some objects have now got engine specific versions. In particular:

- :javadoc:`Player` now has :javadoc:`ClientPlayer` and :javadoc:`ServerPlayer` subinterfaces
- :javadoc:`World` now has :javadoc:`ClientWorld` and :javadoc:`ServerWorld` subinterfaces
- :javadoc:`Location` now has :javadoc:`ClientLocation` and :javadoc:`ServerLocation` subinterfaces

When migrating your plugin, you will generally want to use the server variants of these interfaces. Be careful to check
the return type of various methods that return these object to make sure you're getting the correct type.

CatalogTypes and Registries
===========================

The Sponge registry has been overhauled and ``CatalogTypes`` no longer exist.

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

- Data is now primarily driven by the :javadoc:`Key` system, rather than ``DataManipulators``. Keys can be created at
  any time and do not need to be registered.
- Any data supplied to data holders using an unregistered key are transient - for example, if data is supplied to a
  player using an unregistered key and the player dies (so their player object is recreated) that data is lost. To
  persist custom data, plugins must register their keys during the :javadoc:`RegisterDataEvent` and supply a
  :javadoc:`DataRegistration` (via the :javadoc:`DataRegistration.Builder`) that tells Sponge how to persist the data.

In addition, SpongeAPI 8 allows for custom keys to point to external data stores. This can be done by providing a
:javadoc:`DataProvider` to the ``DataRegistration``.

More information about data can be found at :doc:`data/index`

Command Creation and Registration
=================================

Commands have been completely overhauled in SpongeAPI 8 in order to support Minecraft's command completions, as well as
to resolve long standing issues with the previous system. Most developers will want to use the structured command builder
via :javadoc:`Command#builder()`.  Additionally, commands should now be registered during the ``RegisterCommandEvent``,
those who use the command builder should register commands for the generic event 
``RegisterCommandEvent<Command.Parameterized>``.

SpongeAPI 8 also provides for ways to allow alternate frameworks to integrate at a low-level using 
:javadoc:`CommandRegistrar`.

More information about commands can be found at :doc:`commands/index`.

Migration of Text to Adventure
==============================

SpongeAPI 8 uses the `Adventure <https://docs.adventure.kyori.net/>`__ library to provide text manipulation. In general,
``Text`` objects have become :javadoc:`Component`s, most components will be created via builder methods on that 
interface. For those who wish to emulate a ``Text.of(...)`` like behaviour, use the ``linear`` method in 
:javadoc:`LinearComponents`.

There are additional Sponge specific helper operations in the ``org.spongepowered.api.adventure`` package, specifically
:javadoc:`SpongeComponents` for those who used the ``executeCallback`` function in SpongeAPI 7.

More information about text within Sponge can be found at :doc:`text/index`.

Scheduler 
=========

The scheduler has been updated to better reflect the scope in which a scheduler resides:

- The asynchronus :javadoc:`Scheduler` is game scoped and remains on the :javadoc:`Game` object (and the ``Sponge``
  object)
- Each ``Engine`` now has its own synchronus scheduler, and is available via the engine's instance.

The :javadoc:`Task` object is no longer responsible for determining whether it is asynchronus or not, as such, the 
``Task.Builder#async`` method has been removed. Additionally, building a ``Task`` no longer submits it, instead, you must
submit the task to the relavant ``Scheduler`` via the ``submit(Task)`` method.

Sponge also provides a :javadoc:`TaskExecutorService` for each scheduler, should users prefer to the the Java 
:javadoc:`ExecutorService` for their tasks.

More information about the scheduler can be found at :doc:`scheduler`.


Plugin Services
===============

SpongeAPI 8 no longer supports custom plugin services, only supporting its own. If you want to provide an implementation
for a Sponge service, you must now listen to the :javadoc:`ProvideServiceEvent` for the service interface you wish to
provide the implementation for. Within this method, you may suggest a supplier that will create the service in the event
your plugin is selected to provide the service. Note that most services are server scoped, meaning that it is possible
for there to be multiple requests to provide some services during a game's lifetime.

There is no guarantee that the event will get called for your plugin if another plugin has provided the service first or
if Sponge is configured to only look for a specific service.

Plugins that wish to provide their own service interfaces should provide their own service management, or direct plugins
to register a factory that implements that interface.

More information about services can be found at :doc:`services`

Configurate
===========

Configurate has been updated from version 3 to version 4, and along with it, its package name has changed from
``ninja.leaping.configurate`` to ``org.spongepowered.configurate``, and has mostly dropped the use of ``get`` and ``set``
prefixes.

For more information on Configurate, `you can view its manual here <https://github.com/SpongePowered/Configurate/wiki>`__.