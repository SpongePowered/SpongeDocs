====================
Dependency Injection
====================

Sponge uses dependency injection to provide instances of the API to plugins.

Overview
========

Dependency injection allows plugins to designate a few API types that will be injected after construction.

Temporary List of Injected Types
================================

* Game

    * The ``Game`` object is the core accessor of the SpongeAPI.

* PluginManager

    * Manages the plugins loaded by the implementation. Can retrieve another plugin's ``PluginContainer``.

* EventManager

    * Manages the registration of event handlers and the dispatching of events.

* GameRegistry

    * Provices an easy way to retrieve types from a ``Game``.

* PluginContainer

    * A ``@Plugin`` class wrapper, used to retrieve information from the annotation for easier use.

* Logger

    * Used to identify the plugin from which logged messages are sent.

* File (Has to have an additional annotation specifying specific file)

    * Currently, specifications are ``ConfigFile`` and ``ConfigDir``.

* ConfigDir

    * Used to inject the plugin's configuration directory: ``./mods/`` OR ``./mods/<Plugin#id>/`` depending on ``sharedRoot``

* ConfigFile

    * Used to inject the plugin's specific configuration file: ``<Plugin#id>.conf``

Injection Examples
==================

There are a few references which are difficult to get - or, in some cases, impossible - without injection. While these
may not be absolutely vital to every plugin, they're quite frequently used.

.. note::

    Note that it's *almost always* recommended to inject your objects within the main class, as it's instantiated with the Guice injector when the plugin is loaded.

Logger
~~~~~~

.. tip::

    View :doc:`../basics/logging` for a complete guide, specifically for the Logger.

Game
~~~~

The ``Game`` object is the opening for many of the internal functions of the SpongeAPI, from the ``EventManager`` to the
``Server`` and even the Sync/Async ``Scheduler``.

It's entirely possible to recieve the ``Game`` object from within most events, however it is commonly obtained through
an injection.

**Example - Field**

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.Game;

    @Inject
    private Game game;

**Example - Method**

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.Game;

    private Game game;

    @Inject
    private void setGame(Game game) {
        this.game = game;
    }

**Example - Constructor**

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.Game;

    // For the purpose of this tutorial, "Apple" is the class name.

    private Game game;

    @Inject
    public Apple(Game game) {
        this.game = game;
    }

Config Directory
~~~~~~~~~~~~~~~~

The recommended way to obtain your config file is through Guice, along with the @ConfigFile annotation.

.. tip::

    If you set ``sharedRoot`` to ``true``, your ``ConfigDir`` will be the same directory which - potentially - houses the configuration for other plugins. In most cases where grabbing the ``ConfigDir`` is required, this should be ``false``.

**Example - Field**

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.service.config.ConfigDir;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;

**Example - Method**

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.service.config.ConfigDir;

    private File configDir;

    @Inject
    @ConfigDir(sharedRoot = false)
    private void setConfigDir(File configDir) {
        this.configDir = configDir;
    }

**Example - Constructor**

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.service.config.ConfigDir;

    // For the purposes of this tutorial, "Orange" is the class name.

    private File configDir;

    @Inject
    @ConfigDir(sharedRoot = false)
    public Orange(File configDir) {
        this.configDir = configDir;
    }

DefaultConfig
~~~~~~~~~~~~~

The way that ``DefaultConfig`` works is very similar to ``ConfigDir``. The biggest difference obviously being that
``DefaultConfig`` refers to a specific file, where ``ConfigDir`` refers to a directory.

.. tip::

    View :doc:`../basics/configuration` for a complete guide, specifically for ``DefaultConfig``.
