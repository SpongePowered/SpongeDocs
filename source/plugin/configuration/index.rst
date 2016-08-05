===================
Configuring Plugins
===================

.. javadoc-import::
    ninja.leaping.configurate.hocon.HoconConfigurationLoader
    ninja.leaping.configurate.yaml.YAMLConfigurationLoader
    org.spongepowered.api.config.ConfigDir
    org.spongepowered.api.config.DefaultConfig

Configuration files allow plugins to store data, as well as allow server administrators to easily take control over
specific portions of a plugin, if you so choose to let them. Sponge uses Configurate to allow you to easily
manipulate configuration files. These pages will explain how to utilize Configurate in order to use configuration
files to full advantage.

.. tip::

    See the official `Configurate wiki <https://github.com/zml2008/configurate/wiki>`_ to gain more in-depth information
    about working with its components.


.. note::
    Sponge makes use of the HOCON configuration format, a superset of JSON, as the default format for saving
    configuration files. The rest of this guide will assume you are using HOCON as well. See
    :doc:`../../server/getting-started/configuration/hocon` more for information regarding the HOCON format.
    Working with different formats is made relatively similar by the Configurate system, so it should not
    pose too much of an issue if you use an alternate format instead.

.. toctree::
    :maxdepth: 2
    :titlesonly:

    loaders
    nodes
    serialization

Quick Start
===========

Creating a Default Plugin Configuration
---------------------------------------

Plugins using the Sponge API have the option to use one or more configuration files. Configuration files allow plugins
to store data, and they allow server administrators to customize plugin options (if applicable).


.. _getting-default-config:

Getting your Default Plugin Configuration
-----------------------------------------

The Sponge API offers the use of the :javadoc:`DefaultConfig` annotation on a field or setter method with the type
``Path`` to get the default configuration file for your plugin.

The ``@DefaultConfig`` annotation requires a ``sharedRoot`` boolean. If you set ``sharedRoot`` to ``true``, then the
returned pathname will be in a shared configuration directory. In that case, the configuration file for your plugin
will be ``your_plugin_id.conf`` (with "your_plugin_id" replaced with your plugin's specified ID).

.. tip::

    See :doc:`../plugin-class` for information on configuring your plugin ID.

If you set ``sharedRoot`` to ``false``, the returned pathname will refer to a file named ``{pluginname}.conf`` in a
directory specific to your plugin.

If you are unsure of what to set the value of ``sharedRoot`` to, consider the following:

* If you plan on having multiple configuration files (complex plugins) in the future, set the value to ``false``.
* If you plan on having a single configuration file (less-complex plugins), set the value to ``true``.

You can also obtain a ``Path`` instance pointing to the config directory instead of a particular file. Just
have it injected using the :javadoc:`ConfigDir` annotation, either with ``sharedRoot`` set to ``false`` for a plugin
specific directory or to ``true`` to get the shared configuration directory.

.. note::

    While it may be possible to get a ``File`` instead of a ``Path``, Configurate (and Sponge) recommend using ``Path``.

**Example - Field using** ``@DefaultConfig``

.. code-block:: java

    import java.nio.file.Path;
    import com.google.inject.Inject;
    import org.spongepowered.api.config.ConfigDir;
    import org.spongepowered.api.config.DefaultConfig;
    import ninja.leaping.configurate.commented.CommentedConfigurationNode;
    import ninja.leaping.configurate.loader.ConfigurationLoader;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path privateConfigDir;

.. warning::

    When your plugin is running for the first time, returned pathnames for configuration files and directories may not
    yet exist. If you delegate all reading / writing of files to Configurate, you do not need to worry about
    non-existant paths as the library will handle them appropriately.

.. note::

    The use of YAML format (http://yaml.org/spec/1.1/) is also supported, but the preferred config format for Sponge
    plugins is HOCON. Conversion from YAML to HOCON can be automated by using a :javadoc:`YAMLConfigurationLoader` to
    load the old config and then saving it using a :javadoc:`HoconConfigurationLoader`.
