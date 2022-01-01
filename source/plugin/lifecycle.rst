================
Plugin Lifecycle
================

.. javadoc-import::
    org.spongepowered.api.Client
    org.spongepowered.api.Engine
    org.spongepowered.api.Game
    org.spongepowered.api.Server
    org.spongepowered.api.data.DataRegistration
    org.spongepowered.api.event.lifecycle.EngineLifecycleEvent
    org.spongepowered.api.event.lifecycle.LoadedGameEvent
    org.spongepowered.api.event.lifecycle.ProvideServiceEvent.EngineScoped
    org.spongepowered.api.event.lifecycle.ProvideServiceEvent.GameScoped
    org.spongepowered.api.event.lifecycle.RefreshGameEvent
    org.spongepowered.api.event.lifecycle.RegisterCommandEvent
    org.spongepowered.api.event.lifecycle.RegisterDataEvent
    org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent.EngineScoped
    org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent.GameScoped
    org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent.WorldScoped
    org.spongepowered.api.event.lifecycle.StoppedGameEvent

During initialization, refresh and shutdown of the game, server and world, Sponge has a series of lifecycle events that 
plugins can listen to such that they can act accordingly. All lifecycle events are in the 
``org.spongepowered.api.event.lifecycle`` package.

.. warning::
    
    All lifecycle events assume that the game is currently working and no errors have occurred. Some events may
    not fire if the game crashes or otherwise terminates abnormally, particularly any event that concerns itself
    with ending something. Your plugins should therefore not rely on these events to finalize and store any 
    important state.

Game Lifecycle Events
=====================

In Sponge, the :javadoc:`Game` is a representation of the entire Minecraft process -- effectively from the point
Minecraft starts to when the process terminates. As a result, it can only start once and only stop once, and will
start before any :javadoc:`Engine`s start, and will terminate after all ``Engine``s stop. As a result, the following
events will fire at most once during the game's lifetime:

- :javadoc:`LoadedGameEvent` will fire when the game itself has loaded and is ready to start loading engines
  (but importantly, none have yet started to load). All plugins have been loaded and inter-plugin communication should
  be possible, as well as any game-scoped registries.
- :javadoc:`StoppedGameEvent` will fire when the game has shut down all engines and is about to terminate. No
  engines are available. May not fire if the game terminates abnormally.

Engine Lifecycle Events
=======================

Unlike the ``Game``, ``Engines`` may startup and shutdown multiple times during a game's lifetime. There may also be 
multiple engines running at the same time. For example, for a singleplayer world, there will be a :javadoc:`Client` and
a :javadoc:`Server` running concurrently.

The :javadoc:`EngineLifecycleEvent` is the base event for the engine lifecycle and is generic, bound to the type of 
Engine it is acting for. Listeners to any sub event must also specify the engine in the generic when the sub event
requires it, for example, for the ``StartingEngineEvent`` on the ``Server``, you would write your listener like this:

.. code-block:: java

    import org.spongepowered.api.Server
    import org.spongepowered.api.event.Listener
    import org.spongepowered.api.event.lifecycle.StartingEngineEvent

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        // ...
    }

The following events run during the engine lifecycle:

- :javadoc:`StartingEngineEvent` will fire when the specified ``Engine`` is starting. Nothing about this engine has
  initialized at this point, worlds will not exist and the engine scoped registry will not be ready at this point.
- :javadoc:`StartedEngineEvent` will fire when the specified ``Engine`` has completed initialization. Specifically,
  this means that the registry has been populated and in the case of the server engine, worlds have been created.
- :javadoc:`StoppingEngineEvent` will fire when the engine has been told to shutdown and is about to shut down
  everything it is responsible for. May not fire if the game terminates abnormally.

Registration Events
===================

At various points in the lifecycle of the game, Sponge will fire registration events to prompt plugins to perform 
specific tasks. These registration requests may come at any time, even during normal game play if, for example, a
datapack reload is required. It is important that plugins that perform actions prompted by such lifecycle events
listen to these events.

Some of the important registration events for most plugins are:

- :javadoc:`ProvideServiceEvent.GameScoped` and :javadoc:`ProvideServiceEvent.EngineScoped` for plugins that provide
  services (see :doc:`services`).
- :javadoc:`RegisterCommandEvent` for registering commands as they are now engine scoped and are tied to datapacks,
  not listening to this event may result in commands not being re-registered when requested 
  (see :doc:`commands/index`).
- :javadoc:`RegisterDataEvent` for providing :javadoc:`DataRegistration`s, allowing for persistent storage of
  custom data (see :doc:`data/index`).
- :javadoc:`RegisterRegistryValueEvent.GameScoped`, :javadoc:`RegisterRegistryValueEvent.EngineScoped` and
  :javadoc:`RegisterRegistryValueEvent.WorldScoped` for providing additional entries to registries.

There are other registration events that plugins may be interested in, see the ``org.spongepowered.api.event.lifecycle``
package `in the javadocs <https://jd.spongepowered.org/>`__.

Refresh Events
==============

The :javadoc:`RefreshGameEvent` may be fired in response to a user requesting that all configuration be refreshed.
Plugins should listen to this event and reload their configuration in response.