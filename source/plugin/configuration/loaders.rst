=====================
Configuration Loaders
=====================

.. javadoc-import::
    ninja.leaping.configurate.ConfigurationNode
    ninja.leaping.configurate.ConfigurationOptions
    ninja.leaping.configurate.hocon.HoconConfigurationLoader
    ninja.leaping.configurate.loader.ConfigurationLoader
    org.spongepowered.api.asset.AssetManager

Let's break down how Configurate works, beginning with the loading process. Configurate provides
:javadoc:`ConfigurationLoader`\ s for common configuration formats, standing as the manager of the physical
configuration file, allowing you to save and load data from the given resource. They also allow you to load empty
configurations, giving you the option of hard-coding default values or loading from a pre-written file.

Getting your Loader
~~~~~~~~~~~~~~~~~~~

..note::
    The default :javadoc:`ConfigurationLoader` can be used instead if you're using HOCON; see the 
    :doc:`main configuration page <index>`.

First, let's grab a new :javadoc:`HoconConfigurationLoader` that points to our configuration file.

.. code-block:: java

    import java.nio.file.Path;
    import ninja.leaping.configurate.commented.CommentedConfigurationNode;
    import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
    import ninja.leaping.configurate.loader.ConfigurationLoader;

    Path potentialFile = getConfigPath();
    ConfigurationLoader<CommentedConfigurationNode> loader =
      HoconConfigurationLoader.builder().setPath(potentialFile).build();

The loader will also hold a generic type depending what kind of node it will build. These *Configuration Nodes* will be
discussed in :doc:`a later section <nodes>`.

``ConfigurationLoader``\ s usually hold a builder for you to statically access and create a new instance of the loader of
your desired type. For a basic configuration, we don't really need to specify anything other than the file we want to
load from and/or save to, so all we'll do is tell it exactly that, using
``HoconConfigurationLoader.builder().setPath(path)``. We then tell the builder to build the instance (``build()``) for
it and store it in a variable.

Of course, this isn't the only way to load a file. The builder also has the method ``setURL(URL)``, in case you want
to load a resource without using a ``Path`` object. Bear in mind that configuration loaders created from an ``URL``
are read-only as they have no way of writing back data to the URL.

This functionality may be used to bundle default configurations with your plugin jar file and load them as initial
configuration to be edited by the server administrator (or your plugin itself).

Loading and Saving
~~~~~~~~~~~~~~~~~~

Once you obtained your ``ConfigurationLoader`` you can use it to obtain an empty :javadoc:`ConfigurationNode` using the
``createEmptyNode()`` method.

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.ConfigurationOptions;

    Path potentialFile = getConfigPath();
    ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(potentialFile).build();
    ConfigurationNode rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());

This method expects the `ninja.leaping.configurate.ConfigurationOptions` to use as a parameter. Unless you want to use
features like custom type serialization, you can just use :javadoc:`ConfigurationOptions#defaults()` to create an
options object with default values.

Using the ``load()`` method you can attempt to load the configuration contents from the source specified upon creation
of the ``ConfigurationLoader``. It also expects a ``ConfigurationOptions`` instance, but also provides a no-args form
that is shorthand for ``load(ConfigurationOptions.defaults())``.

.. code-block:: java

    import java.io.IOException;

    Path potentialFile = getConfigPath();
    ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(potentialFile).build();
    ConfigurationNode rootNode;
    try {
        rootNode = loader.load();
    } catch(IOException e) {
        // error
    }

If the ``Path`` given does not exist, the ``load()`` method will create an empty ``ConfigurationNode``. Any other error
will lead to an ``IOException`` being thrown which you will need to handle properly.

If you have injected the default loader, it's a good idea to get its ``ConfigurationOptions``, since they contain the 
ability to serialize and deserialize a large number of Sponge objects.

Once you modified your ``ConfigurationNode`` to hold the data you like to be saved, you can use the
``ConfigurationLoader`` to save the node to the file specified while creating the loader. If that file does not exist,
it will be created. If it does exist, all contents will be overwritten.

.. code-block:: java

    try {
        loader.save(rootNode);
    } catch(IOException e) {
        // error
    }

Again, errors will be propagated as an ``IOException`` and must be handled.

Example: Loading a default config from the plugin jar file
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import java.net.URL;

    URL jarConfigFile = Sponge.getAssetManager().getAsset("defaultConfig.conf").get().getUrl();
    ConfigurationLoader<CommentedConfigurationNode> loader =
      HoconConfigurationLoader.builder().setURL(jarConfigFile).build();

For this example it is important to note that the :javadoc:`AssetManager#getAsset` method works relative to the
plugin's asset folder. So if in the above example the plugin ID is ``myplugin``, the ``defaultConfig.conf`` file
must not lie in the jar file root, but instead in the directory ``assets/myplugin``. For more information, see
:doc:`the Asset API page <assets>`.
