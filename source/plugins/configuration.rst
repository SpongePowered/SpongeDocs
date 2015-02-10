==========================
Configuration and Defaults
==========================

Plugins using the Sponge API have the option to use one or more configuration files. Configuration files allow plugins to store data, and they allow server administrators to customize plugin options (if applicable).


.. _getting-default-config:

Getting your Default Plugin Configuration
=========================================

The Sponge API offers the use of the ``@DefaultConfig`` annotation on a field or method with the type ``File`` to get the default configuration file for your plugin.

The ``@DefaultConfig`` annotation requires a ``sharedRoot`` boolean. If you set ``sharedRoot`` to ``true``, then the returned pathname will be in a shared configuration directory. In that case, the configuration file for your plugin will be ``your_plugin_id.conf`` (with "your_plugin_id" replaced with your plugin's specified ID).

.. tip::

    See :doc:`quick-start` for information on configuring your plugin ID.

If you set ``sharedRoot`` to ``false``, the returned pathname will refer to a file named ``config.conf`` in a directory specific to your plugin.

If you are unsure of what to set the value of ``sharedRoot`` to, consider the following:

* If you plan on having multiple configuration files (complex plugins) in the future, set the value to ``false``.
* If you plan on having a single configuration file (less-complex plugins), set the value to ``true``.

.. warning::

    Setting sharedRoot to false currently crashes the plugin as it has not been implemented yet. Please set sharedRoot to true until that is fixed.


**Example - Field using** ``@DefaultConfig``

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.service.config.DefaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File defaultConfig;
    
    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader configManager;

.. warning::

    When your plugin is running for the first time, the returned pathname may refer to a configuration file that does not yet exist.

Creating a Getter Method
~~~~~~~~~~~~~~~~~~~~~~~~

Creating a getter method for your plugin's default configuration file may come in useful, although doing so is optional. The remainder of this guide assumes that you create a getter method similar to the one illustrated below.

.. code-block:: java

    public File getDefaultConfig() {
        return defaultConfig;
    }
    
    public ConfigurationLoader getConfigManager() {
        return configManager;
    }

Setting Configuration Values
============================

.. note::

    The following examples assume that the getter method for your default configuration is named ``getDefaultConfig()``, as shown in :ref:`getting-default-config`. This may differ for you depending on what you named your getter method.

Defaults
~~~~~~~~

Default configuration values are a necessity for a couple of reasons:

- They are the first thing server administrators will see when first opening the configuration for your plugin.
- The lack of default configuration values may throw exceptions, depending on how you handle things.

Checking whether your plugin's configuration file exists where it is expected to is an effective method of determining if default configuration values need to be set. If your plugin's configuration file does not exist where it is expected to, then the file likely needs to be created. This is shown in the example below; the example checks whether the pathname defined by the previously-defined ``getDefaultConfig()`` exists. If it does not exist, a default configuration file is created, and values are written to it using ``config.getNode(String path).setValue(value)``. The value can be a String, Int, Boolean, Double, Long, or Float.

.. code-block:: java

     import java.io.File;
     import ninja.leaping.configurate.ConfigurationNode;
     import ninja.leaping.configurate.loader.ConfigurationLoader;
      
      ConfigurationNode config = null;

      try {
          if (!defaultConfig.exists()) {
              defaultConfig.createNewFile();
              config = configManager.load();
              
              config.getNode("version").setValue(1);
              config.getNode("doStuff").setValue(true);
              config.getNode("doMoreStuff").setValue(false);
              configManager.save(config);
          }
          config = configManager.load();

      } catch (IOException exception) {
          getLogger().error("The default configuration could not be loaded or created!");
      }

After setting the default configuration values, the ``save()`` method must be called. The configuration will only be saved if changes are detected. This applies more to later edits of your configuration.


If all goes well, your configuration file will end up looking similar to this:

.. code-block:: none

    version=1
    doStuff=true
    doMoreStuff=false

.. note::

    The Sponge API uses HOCON, a superset of JSON, as the default format for saving configuration files. See :doc:`../users/hocon` more for information regarding the HOCON format.

Edits
~~~~~

Editing configuration files is similar to creating them. After defining the ``config = configManager.load()``, values can be edited as necessary with the ``config.getNode(String path).setValue(value)`` method, as exemplified below.

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.loader.ConfigurationLoader;
    
    config = configManager.load();   
    config.getNode("version").setValue(2);
    configManager.save(config);

    
The ``path`` is the path to the value in your configuration. The ``path`` is dependent on what you set it to be. Paths are illustrated in :doc:`../users/hocon`.

Retrieving Configuration Values
=======================================


After defining the ``config = configManager.load()`` ,  a method such as the following may be invoked to retrieve configuration values.

* ``getNode(String path).getInt()``
* ``getNode(String path).getBoolean()`` 
* ``getNode(String path).getDouble()``
* ``getNode(String path).getString()``
* ``getNode(String path).getLong()``
* ``getNode(String path).getFloat()``

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.loader.ConfigurationLoader;

    config = configManager.load();
    int version = config.getNode("version").getInt();

In this example, "version" is the path. Assuming that the configuration exists and is valid, an integer will be returned as the value. 

Config getter methods can be passed a default value as an argument. This default value will be returned if the config object contains no appropriate value on the given path.

.. code-block:: java

    config.getNode("doesntexist").getString("foo"); // will return "foo" since there is no path "doesntexist" in our config file
