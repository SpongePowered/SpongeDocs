================
Plugin Lifecycle
================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs> __

Prior to any states that make the plugin visible, the plugin loader first sorts through the available plugins, determines
if all dependencies are present, and sorts plugins by dependency order. Lifecycle events are given to plugins in this
order. For example, plugin A containing "[required-]after:B" will get each event after plugin B has completed work for
the given state. Additionally, lifecycle states are global. This means that all plugins visible to each other must be
transitioned through all states at once.

.. warning::
    The Sponge ``Server`` object is not always available. Availability can be checked using the method
    ``Sponge.isServerAvailable()`` or ``Game.isServerAvailable()``.

State Events
============

There are three categories of state events:

1. **Initialization:** When Sponge and plugins are loading, before the actual game has started. Initialization states
   only occur once.
2. **Running:** When the game and world are loading. Running states may occur multiple times.
3. **Stopping:** When the game is shutting down. Stopping states, like initialization states, only occur once.

Initialization States
=====================

To be filled out