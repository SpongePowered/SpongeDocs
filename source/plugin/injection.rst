====================
Dependency Injection
====================

.. javadoc-import::
    org.spongepowered.api.Game
    org.spongepowered.api.GameRegistry
    org.spongepowered.api.Server
    org.spongepowered.api.config.ConfigDir
    org.spongepowered.api.config.DefaultConfig
    org.spongepowered.api.event.EventManager
    org.spongepowered.api.plugin.Plugin
    org.spongepowered.api.plugin.PluginContainer
    org.spongepowered.api.plugin.PluginManager
    org.spongepowered.api.scheduler.Scheduler
    ninja.leaping.configurate.commented.CommentedConfigurationNode
    ninja.leaping.configurate.loader.ConfigurationLoader
    ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory

Sponge uses dependency injection to provide instances of the API to plugins.
Dependency injection allows plugins to designate a few API types that will be injected after construction.

Temporary List of Injected Types
================================

:javadoc:`ConfigDir` (annotation on Path or File)
  Used to inject the plugin's configuration directory:
  ``./config/`` OR ``./config/<Plugin#id>/`` depending on :javadoc:`ConfigDir#sharedRoot()`

:javadoc:`ConfigurationLoader<CommentedConfigurationNode>`
  Must be annotated with ``@DefaultConfig``.
  Used to inject a pre-generated ``ConfigurationLoader`` for the ``File`` of the same annotation.

:javadoc:`DefaultConfig` (annotation on Path, ConfigurationLoader or File)
  Used to inject the plugin's specific configuration file: ``<Plugin#id>.conf``

:javadoc:`EventManager`
  Manages the registration of event handlers and the dispatching of events.

File
  Must be annotated with either ``@DefaultConfig`` or ``@ConfigDir``.
  Depending on the annotation given this will contain a file reference to the plugins default config file or the
  directory used for storing configuration files. However, Path (see below) should be preferred.

:javadoc:`Game`
  The ``Game`` object is the core accessor of SpongeAPI.

:javadoc:`GameRegistry`
  Provides an easy way to retrieve types from a ``Game``.

:javadoc:`GuiceObjectMapperFactory`
  A tool provided by Configurate to allow easier mapping of objects to configuration nodes.
  See :doc:`configuration/serialization` for usage.

Injector
  ``com.google.inject.Injector`` is available from Guice, it is the injector that was used to inject your plugin's
  dependencies. You can use it to create a child injector with your own module in order to inject your own classes
  with either the Sponge provided dependencies listed on this page, or configure your own classes

Logger
  Used to identify the plugin from which logged messages are sent.

Path
  Must be annotated with either ``@DefaultConfig`` or ``@ConfigDir``.
  Depending on the annotation given this will contain a path reference to the plugins default config file or the
  directory used for storing configuration files.

:javadoc:`PluginContainer`
  A :javadoc:`Plugin` class wrapper, used to retrieve information from the annotation for easier use.

:javadoc:`PluginManager`
  Manages the plugins loaded by the implementation.
  Can retrieve another plugin's ``PluginContainer``.


Injection Examples
==================

There are a few references which are difficult to get - or, in some cases, impossible - without injection. While these
may not be absolutely vital to every plugin, they're quite frequently used.

.. note::

    Remember that it's *almost always* best practice to inject your objects within the main class, as it's
    instantiated with the Guice injector when the plugin is loaded.

Logger
~~~~~~

.. tip::

    View :doc:`logging` for a complete guide, specifically for the Logger.

Game
~~~~

The ``Game`` object is the opening for many of the internal functions of SpongeAPI, from the ``EventManager`` to the
:javadoc:`Server` and even the Sync/Async :javadoc:`Scheduler`.

While it is entirely possible to retrieve the ``Game`` object through ``Sponge.getGame()``, it is commonly obtained
through an injection.

**Example - Field**

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.Game;

    @Inject
    private Game game;

**Example - Method**

.. code-block:: java

    private Game game;

    @Inject
    private void setGame(Game game) {
        this.game = game;
    }

**Example - Constructor**

    *For the purpose of this tutorial, "Apple" is the class name.*

.. code-block:: java

    private Game game;

    @Inject
    public Apple(Game game) {
        this.game = game;
    }

Config Directory
~~~~~~~~~~~~~~~~

The recommended way to obtain your config file is through Guice, along with the :javadoc:`ConfigDir` annotation.

.. tip::

    If you set ``sharedRoot`` to ``true``, your ``ConfigDir`` will be the same directory which - potentially - houses
    the configuration for other plugins. In most cases where grabbing the ``ConfigDir`` is required, this should be
    ``false``.

**Example - Field**

.. code-block:: java

    import org.spongepowered.api.config.ConfigDir;

    import java.nio.file.Path;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

**Example - Method**

.. code-block:: java

    private Path configDir;

    @Inject
    private void setConfigDir(@ConfigDir(sharedRoot = false) Path configDir) {
        this.configDir = configDir;
    }

**Example - Constructor**

  *For the purposes of this tutorial, "Orange" is the class name.*

.. code-block:: java

    private Path configDir;

    @Inject
    public Orange(@ConfigDir(sharedRoot = false) Path configDir) {
        this.configDir = configDir;
    }

DefaultConfig
~~~~~~~~~~~~~

The way that ``@DefaultConfig`` works is very similar to ``@ConfigDir``. The biggest difference is that
``@DefaultConfig`` refers to a specific file, whereas ``@ConfigDir`` refers to a directory.

.. tip::

    View :doc:`configuration/index` for a complete guide, specifically for ``@DefaultConfig``.
