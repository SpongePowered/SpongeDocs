=======================================
Creating a Default Plugin Configuration
=======================================

Plugins using the Sponge API have the option to use one or more configuration files. Configuration files allow plugins
to store data, and they allow server administrators to customize plugin options (if applicable).


.. _getting-default-config:

Getting your Default Plugin Configuration
=========================================

The Sponge API offers the use of the ``@DefaultConfig`` annotation on a field or method with the type ``File`` to get
the default configuration file for your plugin.

The ``@DefaultConfig`` annotation requires a ``sharedRoot`` boolean. If you set ``sharedRoot`` to ``true``, then the
returned pathname will be in a shared configuration directory. In that case, the configuration file for your plugin
will be ``your_plugin_id.conf`` (with "your_plugin_id" replaced with your plugin's specified ID).

.. tip::

    See :doc:`main-class` for information on configuring your plugin ID.

.. note::

    The Sponge API uses HOCON, a superset of JSON, as the default format for saving configuration files. See
    :doc:`../../server/getting-started/configuration/hocon` more for information regarding the HOCON format.

If you set ``sharedRoot`` to ``false``, the returned pathname will refer to a file named ``{pluginname}.conf`` in a
directory specific to your plugin.

If you are unsure of what to set the value of ``sharedRoot`` to, consider the following:

* If you plan on having multiple configuration files (complex plugins) in the future, set the value to ``false``.
* If you plan on having a single configuration file (less-complex plugins), set the value to ``true``.


**Example - Field using** ``@DefaultConfig``

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.service.config.DefaultConfig;

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
            ConfigurationLoader<ConfigurationNode> yamlReader = YAMLConfigurationLoader.builder().setFile(bukkitConfigFile).build();
            ConfigurationNode bukkitConfig = yamlReader.load();
            configLoader.save(bukkitConfig);
            if (!bukkitConfigFile.renameTo(new File(configDir, "config.yml.bukkit"))) {
                logger.warn(lf(_("Could not rename old Bukkit configuration file to old name")));
            }
        }
    }

Working with the Configuration
==============================

See :doc:`../advanced/config-in-depth/index` to learn how to utilize the config file and loader.
