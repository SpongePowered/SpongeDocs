====================
Dependency Injection
====================

Sponge uses dependency injection to provide instances of the API to plugins.

Overview
========

Dependency injection allows plugins to designate a few API types that will be injected after construction.

Temporary List of Injected Types
================================

- Game
- PluginManager
- EventManager
- GameRegistry
- PluginContainer
- Logger
- ConfigFile
- File (Has to have an additional annotation specifying specific file)

.. note::

    This is an incomplete doc, but offers a temporary quick reference. More information can be found in the source of SpongeGuiceModule which is part of Sponge.
