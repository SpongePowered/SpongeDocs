================
Plugin Lifecycle
================

.. javadoc-import::
    org.spongepowered.api.event.game.state.GameConstructionEvent
    org.spongepowered.api.event.game.state.GamePreInitializationEvent
    org.spongepowered.api.event.game.state.GameInitializationEvent
    org.spongepowered.api.event.game.state.GamePostInitializationEvent
    org.spongepowered.api.event.game.state.GameLoadCompleteEvent
    org.spongepowered.api.event.game.state.GameAboutToStartServerEvent
    org.spongepowered.api.event.game.state.GameStartingServerEvent
    org.spongepowered.api.event.game.state.GameStartedServerEvent
    org.spongepowered.api.event.game.state.GameStoppingServerEvent
    org.spongepowered.api.event.game.state.GameStoppedServerEvent

Prior to any states that make the plugin visible, the plugin loader first sorts through the available plugins, determines
if all dependencies are present, and sorts plugins by dependency order. Lifecycle events are given to plugins in this
order. For example, plugin A containing "[required-]after:B" will get each event after plugin B has completed work for
the given state. Additionally, lifecycle states are global. This means that all plugins visible to each other must be
transitioned through all states at once.

State Events
============

There are three categories of state events:

1. **Initialization:** When Sponge and plugins are loading, before the actual game has started. Initialization states
   only occur once.
2. **Running:** When the game and world are loading. Running states may occur multiple times.
3. **Stopping:** When the game is shutting down. Stopping states, like initialization states, only occur once.

Initialization States
=====================

Initialization states only occur once during a single run.

**CONSTRUCTION**

The :javadoc:`GameConstructionEvent` is triggered.
During this state, the ``@Plugin`` class instance for each plugin is triggered.

**PRE_INITIALIZATION**

The :javadoc:`GamePreInitializationEvent` is triggered.
During this state, the plugin gets ready for initialization. Access to a default logger instance and access to
information regarding preferred configuration file locations is available.

**INITIALIZATION**

The :javadoc:`GameInitializationEvent` is triggered.
During this state, the plugin should finish any work needed in order to be functional. Global event handlers should get
registered in this stage.

**POST_INITIALIZATION**

The :javadoc:`GamePostInitializationEvent` is triggered.
By this state, inter-plugin communication should be ready to occur. Plugins providing an API should be ready to accept
basic requests.

**LOAD_COMPLETE**

The :javadoc:`GameLoadCompleteEvent` is triggered.
By this state, all plugin initialization should be completed.

Running States
==============

Running States can occur multiple times during a single run. ``SERVER_ABOUT_TO_START`` may follow ``SERVER_STOPPED``,
and ``SERVER_STOPPED`` may occur at any point during the process if there is an error.

**SERVER_ABOUT_TO_START**

The :javadoc:`GameAboutToStartServerEvent` event is triggered.
The server instance exists, but worlds are not yet loaded.

**SERVER_STARTING**

The :javadoc:`GameStartingServerEvent` is triggered.
The server instance exists, and worlds are loaded. Command registration is handled during this state.

**SERVER_STARTED**

The :javadoc:`GameStartedServerEvent` event is triggered.
The server instance exists, and worlds are loaded.

**SERVER_STOPPING**

The :javadoc:`GameStoppingServerEvent` is triggered.
This state occurs immediately before the final tick, before the worlds are saved.

**SERVER_STOPPED**

The :javadoc:`GameStoppedServerEvent` is triggered.
During this state, no players are connected and no changes to worlds are saved.

Stopping States
===============

Stopping states never occur more than once during a single run. They occur when the game stops normally. (On Servers:
the ``/stop`` command is typed. On Clients: The "Close" button or the "Quit Game" button are clicked)

.. warning::
    Stopping states are not guaranteed to be run during shutdown. They may **not** fire if the game is force-stopped via
    Ctrl-C, Task Manager, a computer crash, or similar situations.

**GAME_STOPPING**

The :javadoc:`GameStoppingServerEvent` is triggered.
This state occurs immediately before ``GAME_STOPPED``. Plugins providing an API should still be capable of accepting
basic requests.

**GAME_STOPPED**

The :javadoc:`GameStoppedServerEvent` is triggered.
Once this event has finished executing, Minecraft will shut down. No further interaction with the game or other plugins
should be attempted at this point.
