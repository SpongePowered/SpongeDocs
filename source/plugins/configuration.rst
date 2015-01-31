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

**Example - Field using** ``@DefaultConfig``

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.service.config.DefaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File defaultConfig;

.. warning::

    When your plugin is running for the first time, the returned pathname may refer to a configuration file that does not yet exist.

Creating a Getter Method
~~~~~~~~~~~~~~~~~~~~~~~~

Creating a getter method for your plugin's default configuration file may come in useful, although doing so is optional. The remainder of this guide assumes that you create a getter method similar to the one illustrated below.

.. code-block:: java

    public File getDefaultConfig() {
        return defaultConfig;
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

Checking whether your plugin's configuration file exists where it is expected to is an effective method of determining if default configuration values need to be set. If your plugin's configuration file does not exist where it is expected to, then the file likely needs to be created. This is shown in the example below; the example checks whether the pathname defined by the previously-defined ``getDefaultConfig()`` exists. If it does not exist, a default configuration file is created, and values are written to it using ``withValue(String path, ConfigValue value)``.

.. code-block:: java

     import com.typesafe.config.ConfigValueFactory;
     import org.spongepowered.api.util.config.ConfigFile;
     import java.io.File;

     if (!getDefaultConfig().exists()) {
        File newConfig;
        
        try {
            getDefaultConfig().createNewFile();
        } catch (IOException exception) {
            getLogger().error("The default configuration could not be created!");
        }
        ConfigFile config = ConfigFile.parseFile(getDefaultConfig())
            .withValue("plugin.version", ConfigValueFactory.fromAnyRef(1))
            .withValue("plugin.doStuff", ConfigValueFactory.fromAnyRef(true))
            .withValue("plugin.doMoreStuff", ConfigValueFactory.fromAnyRef(false));
        
        config.save(true);
      }

After setting the default configuration values, the ``save(boolean onlyIfChanged)`` method must be called. If you set the ``onlyIfChanged`` boolean to ``true``, the configuration will only be saved if changes are detected. This applies more to later edits of your configuration.

.. note::

    Keep in mind that the ``ConfigFile`` object is immutable. In other words, each invocation of ``withValue()`` produces a new ``ConfigFile`` object rather than modifying the original.

If all goes well, your default configuration file will end up looking similar to this:

.. code-block:: none

    plugin = {
        version = 1,
        doStuff = true,
        doMoreStuff = false
    }

.. note::

    The Sponge API uses HOCON, a superset of JSON, as the default format for saving configuration files. See :doc:`../users/hocon` more for information regarding the HOCON format.

Edits
~~~~~

Editing default configuration files is similar to creating them. After defining the ``ConfigFile`` object, values can be edited as necessary with the ``withValue(String path, ConfigValue value)`` method, as exemplified below.

.. code-block:: java

    import org.spongepowered.api.util.config.ConfigFile;

    ConfigFile config = ConfigFile.parseFile(getDefaultConfig())
        .withValue("plugin.version", ConfigValueFactory.fromAnyRef(2));
    
    config.save(true);
    
The ``path`` is the path to the value in your configuration. The ``path`` is dependent on what you set it to be. Paths are illustrated in :doc:`../users/hocon`.

Retrieving Default Configuration Values
=======================================

.. note::

    The following example assumes that the getter method for your default configuration is named ``getDefaultConfig()``, as shown in :ref:`getting-default-config`. This may differ for you depending on what you named your getter method.

After defining the ``ConfigFile`` object, a method such as ``getInt(String path)`` or ``getBoolean(String path)`` may be invoked to retrieve default configuration values. The path to the configuration value must be passed as an argument to the method.

.. code-block:: java

    import org.spongepowered.api.util.config.ConfigFile;

    ConfigFile config = ConfigFile.parseFile(getDefaultConfig());
    int version = config.getInt("plugin.version");

In this example, "plugin.version" is the path. Assuming that the configuration exists and is valid, an integer will be returned as the value.
