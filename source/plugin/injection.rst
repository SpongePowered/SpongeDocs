====================
Dependency Injection
====================

.. warning::

    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__


.. javadoc-import::
    java.nio.file.Path
    org.spongepowered.api.Game
    org.spongepowered.api.MinecraftVersion
    org.spongepowered.api.Sponge
    org.spongepowered.api.config.ConfigDir
    org.spongepowered.api.config.ConfigManager
    org.spongepowered.api.config.DefaultConfig
    org.spongepowered.api.data.DataManager
    org.spongepowered.api.network.channel.ChannelManager
    org.spongepowered.api.plugin.PluginManager
    org.spongepowered.api.registry.BuilderProvider
    org.spongepowered.api.registry.FactoryProvider
    org.spongepowered.api.service.ServiceProvider
    org.spongepowered.api.sql.SqlManager
    org.spongepowered.api.util.metric.MetricsConfigManager
    org.spongepowered.configurate.commented.CommentedConfigurationNode
    org.spongepowered.configurate.loader.ConfigurationLoader
    org.spongepowered.configurate.reference.ConfigurationReference
    org.spongepowered.configurate.serialize.TypesSerializerCollection
    org.spongepowered.plugin.PluginContainer


When creating your plugin class, Sponge uses a technique called Dependency Injection to supply API objects to your
plugin's main class. Some of these objects, such as loggers and configuration loaders, are specific to your plugin 
and reduce the code you have to write to perform some of these tasks.

.. warning::

    Sponge only performs injection on your main plugin class. Using ``@Inject`` on other classes will not work unless
    you inject an ``Injector`` into your main class and then use that to create other classes.


Simple Injections
=================

Fields or constructor parameters of the following types can be annotated with ``@com.google.inject.Inject`` to ask Sponge
to provide simple objects.

The following objects are the same, no matter which plugin requests the injection:

- :javadoc:`Game`
- :javadoc:`MinecraftVersion`
- :javadoc:`ChannelManager`
- :javadoc:`PluginManager`
- :javadoc:`DataManager`
- :javadoc:`ConfigManager`
- :javadoc:`MetricsConfigManager`
- :javadoc:`SqlManager`
- :javadoc:`ServiceProvider`
- :javadoc:`FactoryProvider`
- :javadoc:`BuilderProvider`

The following types return an appropriate instance for the plugin:

- :javadoc:`PluginContainer` - returns the plugin container associated with the plugin it is being injected into
- ``org.apache.logging.log4j.Logger`` - returns the logger associated with the plugin it is being injected into

Example: Injecting the Plugin Specific Logger and PluginContainer
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

We can signal to Sponge that you want to inject the logger in one of two ways, field or constructor injection. All simple
injections work the same way in Sponge.

For **field injection**, you must annotate non-final fields with the ``@Inject`` annotation, as in the example below:

.. code-block:: java

    import com.google.inject.Inject;
    import org.apache.logging.log4j.Logger;

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer pluginContainer;

For **constructor injection**, you must create a constructor, annotate it with ``@Inject``, and add the objects you
want injecting as parameters, as in the example below:

.. code-block:: java

    import com.google.inject.Inject;
    import org.apache.logging.log4j.Logger;

    // For the purpose of this example, "Banana" is the class name

    private final Logger logger;
    private final PluginContainer pluginContainer;

    @Inject
    public Banana(Logger logger, PluginContainer pluginContainer) {
        this.logger = logger;
        this.pluginContainer = pluginContainer;
    }

In both of these examples, the ``logger`` field will contain a Sponge provided logger and the ``pluginContainer`` field
will contain the plugin's ``PluginContainer`` after the object is constructed.


Configurate Injections
======================

.. tip::

    View :doc:`configuration/index` for a guide to configuration, specifically using the ``@DefaultConfig`` annotation.


Sponge is also able to inject Configurate specific objects into your plugin class, set up with suggested locations for
your plugin configuration. These injections require an additional annotation on your injected type, which will be
either :javadoc:`ConfigDir` or  :javadoc:`DefaultConfig`, dependent on your use case.

The ``DefaultConfig`` Annotation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The :javadoc:`DefaultConfig` annotation is used to resolve a **file location**. ``DefaultConfig`` has a parameter
``sharedRoot``, which alters the file that it points to (where ``<pluginid>`` is your plugin's ID):

- If ``sharedRoot`` is ``false``, the annotation will point to the file ``config/<pluginid>/<pluginid>.conf``.
- If ``sharedRoot`` is ``true``, the annotation will point to the file ``config/<pluginid>.conf``.

The ``DefaultConfig`` annotation can be applied on the following types:

- ``ConfigurationLoader<CommentedConfigurationNode>`` - provides a configuration loader that will load and save a HOCON
  file from the resolved file location
- ``ConfigurationReference<CommentedConfigurationNode>`` - provides a :javadoc:`ConfigurationReference` that will
  load and save a HOCON file from the resolved file location
- ``Path`` - stores the path to the file location, useful if you wish to use a different file format for your 
  configuration (such as YAML).

**Example Injection**

The following example injects the HOCON configuration loader and the path it is pointing to via field injection.

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.config.DefaultConfig
    import org.spongepowered.configurate.CommentedConfigurationNode;
    import org.spongepowered.configurate.loader.ConfigurationLoader;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configFilePath;

Most users will only require the provided ``loader``, which can then be interacted with in the normal way.

The ``ConfigDir`` annotation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The :javadoc:`ConfigDir` annotation is used to resolve a **folder**. The ``sharedRoot`` parameter works as follows
(where ``<pluginid>`` is your plugin's ID):

- If ``sharedRoot`` is ``false``, the annotation will point to the file ``config/<pluginid>/``.
- If ``sharedRoot`` is ``true``, the annotation will point to the file ``config/``.

The ``ConfigDir`` annotation can only be applied on the ``Path`` type to retrive this directory. It is generally most
useful for plugins that require multiple configuration files, providing the directory to place them rather than a
single file.

