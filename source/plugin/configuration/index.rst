=======================
Configuring Your Plugin
=======================

Configuration files allow your plugins to store data, as well as allow server administrators to easily take control over
specific portions of your plugin, if you so choose to let them. Sponge uses Configurate to allow you to easily
manipulate your configuration files. These pages will explain how to utilize Configurate in order to use configuration
files to your full advantage.

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
    paths
    values
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

The Sponge API offers the use of the ``@DefaultConfig`` annotation on a field or method with the type ``File`` to get
the default configuration file for your plugin.

The ``@DefaultConfig`` annotation requires a ``sharedRoot`` boolean. If you set ``sharedRoot`` to ``true``, then the
returned pathname will be in a shared configuration directory. In that case, the configuration file for your plugin
will be ``your_plugin_id.conf`` (with "your_plugin_id" replaced with your plugin's specified ID).

.. tip::

    See :doc:`../main-class` for information on configuring your plugin ID.

If you set ``sharedRoot`` to ``false``, the returned pathname will refer to a file named ``{pluginname}.conf`` in a
directory specific to your plugin.

If you are unsure of what to set the value of ``sharedRoot`` to, consider the following:

* If you plan on having multiple configuration files (complex plugins) in the future, set the value to ``false``.
* If you plan on having a single configuration file (less-complex plugins), set the value to ``true``.


**Example - Field using** ``@DefaultConfig``

.. code-block:: java

    import com.google.inject.Inject;
    import ninja.leaping.configurate.commented.CommentedConfigurationNode;
    import ninja.leaping.configurate.loader.ConfigurationLoader;
    import org.spongepowered.api.service.config.DefaultConfig;

    import java.io.File;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

.. warning::

    When your plugin is running for the first time, the returned pathname may refer to a configuration file that does
    not yet exist.

.. note::

    The use of YAML format (http://yaml.org/spec/1.1/) is also supported, but the preferred config format for Sponge
    plugins is HOCON. Conversion from YAML to HOCON can be automated; a simple code snippet for converting a Bukkit
    plugin configuration folder with a dedicated data folder (sharedRoot = false) is provided below.

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

    import java.io.IOException;

    private void convertFromBukkit() throws IOException {
        File bukkitConfigDir = new File("plugins/" + PomData.NAME);
        if (bukkitConfigDir.isDirectory() && !configDir.isDirectory()) {
        logger.info(lf(_("Migrating configuration data from Bukkit")));
            if (!bukkitConfigDir.renameTo(configDir)) {
                throw new IOException(lf(_("Unable to move Bukkit configuration directory to location for Sponge!")));
            }
        }
        File bukkitConfigFile = new File(configDir, "config.yml");
        if (bukkitConfigFile.isFile()) {
            ConfigurationLoader<ConfigurationNode> yamlReader =
              YAMLConfigurationLoader.builder().setFile(bukkitConfigFile).build();
            ConfigurationNode bukkitConfig = yamlReader.load();
            configLoader.save(bukkitConfig);
            if (!bukkitConfigFile.renameTo(new File(configDir, "config.yml.bukkit"))) {
                logger.warn(lf(_("Could not rename old Bukkit configuration file to old name")));
            }
        }
    }
