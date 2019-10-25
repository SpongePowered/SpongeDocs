=====================
Configuration Loaders
=====================

.. javadoc-import::
    ninja.leaping.configurate.ConfigurationNode
    ninja.leaping.configurate.ConfigurationOptions
    ninja.leaping.configurate.hocon.HoconConfigurationLoader
    ninja.leaping.configurate.hocon.HoconConfigurationLoader.Builder
    ninja.leaping.configurate.gson.GsonConfigurationLoader
    ninja.leaping.configurate.yaml.YAMLConfigurationLoader
    ninja.leaping.configurate.loader.AbstractConfigurationLoader.Builder
    ninja.leaping.configurate.loader.ConfigurationLoader
    org.spongepowered.api.asset.AssetManager
    org.spongepowered.api.asset.Asset
    java.lang.String
    java.net.URL
    java.nio.file.Path

Let's break down how Configurate works, beginning with the loading process. Configurate provides
:javadoc:`ConfigurationLoader`\s for common configuration formats, standing as the manager of the physical
configuration file, allowing you to save and load data from the given resource. They also allow you to load empty
configurations, giving you the option of hard-coding default values or loading from a pre-written file.

.. tip::

    The default and recommended config format for Sponge plugins is HOCON.

Getting your Loader
~~~~~~~~~~~~~~~~~~~

.. note::
    The default :javadoc:`ConfigurationLoader` can be used instead if you're using only a single HOCON config file;
    see the :doc:`main configuration page <index>`.

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

``ConfigurationLoader``\s usually hold a builder for you to statically access and create a new instance of the loader of
your desired type. For a basic configuration, we don't really need to specify anything other than the file we want to
load from and/or save to, so all we'll do is tell it exactly that, using
:javadoc:`AbstractConfigurationLoader.Builder#setPath(Path) {HoconConfigurationLoader.builder().setPath(path)}`.
We then tell the builder to build the instance (:javadoc:`HoconConfigurationLoader.Builder#build() {build()}`) for it
and store it in a variable.

Of course, this isn't the only way to load a file. The builder also has the method
:javadoc:`AbstractConfigurationLoader.Builder#setURL(URL) {setURL(url)}`, in case you want
to load a resource without using a :javadoc:`Path` object. Bear in mind that configuration loaders created from a
:javadoc:`URL` are read-only as they have no way of writing back data to the URL.

This functionality may be used to bundle default configurations with your plugin jar file and load them as initial
configuration to be edited by the server administrator (or your plugin itself).

.. note::

    This example uses a ``HoconConfigurationLoader``, which is the recommended approach for Sponge plugins, but
    you can also use a :javadoc:`YAMLConfigurationLoader` or :javadoc:`GsonConfigurationLoader` for loading legacy
    configs.

Loading and Saving
~~~~~~~~~~~~~~~~~~

Once you obtained your ``ConfigurationLoader`` you can use it to obtain an empty :javadoc:`ConfigurationNode` using the
:javadoc:`ConfigurationLoader#createEmptyNode() {createEmptyNode()}` method.

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.ConfigurationOptions;

    Path potentialFile = getConfigPath();
    ConfigurationLoader<CommentedConfigurationNode> loader =
            HoconConfigurationLoader.builder().setPath(potentialFile).build();
    ConfigurationNode rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());

This method expects the :javadoc:`ConfigurationOptions` to use as a parameter. Unless you want to use
features like custom type serialization, you can just use :javadoc:`ConfigurationOptions#defaults()` to create an
options object with default values.

Using the :javadoc:`ConfigurationLoader#load() {load()}` method you can attempt to load the configuration contents from
the source specified upon creation of the ``ConfigurationLoader``. It also expects a ``ConfigurationOptions`` instance,
but also provides a no-args form that is shorthand for
:javadoc:`ConfigurationLoader#load(ConfigurationOptions) {load(ConfigurationOptions.defaults())}`.

.. code-block:: java

    import java.io.IOException;

    Path potentialFile = getConfigPath();
    ConfigurationLoader<CommentedConfigurationNode> loader =
            HoconConfigurationLoader.builder().setPath(potentialFile).build();
    ConfigurationNode rootNode;
    try {
        rootNode = loader.load();
    } catch(IOException e) {
        // handle error
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
        // handle error
    }

Again, errors will be propagated as an ``IOException`` and must be handled.

.. tip::

    We recommend saving the config after loading it (for the first time after an update) to ensure that newly
    added or migrated configuration options are written to disk. If you need to save the config afterwards it is
    strongly recommended to do this outside of the main thread. See also common :doc:`/plugin/practices/bad` you should
    avoid.

Loading a Default Config from the Plugin Jar File
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

A popular way to provide a default configuration file with your plugin is to include a copy of it in your plugin jar,
copying it to the config directory when the config file has yet to be created.
You can use :doc:`the Asset API <../assets>` to do this, as shown in the example below:

.. code-block:: java

    PluginContainer plugin = ...;
    Path path = ...;

    Sponge.getAssetManager().getAsset(plugin, "default.conf").get().copyToFile(path, false, true);
    loader = HoconConfigurationLoader.builder().setPath(path).build();
    rootNode = loader.load();

For this example it is important to note that the :javadoc:`AssetManager#getAsset(String)` method works relative to the
plugin's asset folder. So, if in the above example the plugin ID is ``myplugin``, the ``default.conf`` file
must not lie in the jar file root, but instead in the directory ``assets/myplugin``. This example also uses 
:javadoc:`Asset#copyToFile(Path, boolean, boolean)` which allows the file creation to override existing
files only if specified. 

.. note::
    
    If the config file cannot be found inside your plugin jar, then you will get a ``NoSuchElementException`` from the
    ``Optional<Asset>.get()`` method. Please make sure that you configure your :doc:`build system </plugin/buildsystem>`
    to include it in the jar.

If you have an extra configuration class, you can use a much easier approach that also works if the only a part of your
config is missing. See also the examples on the :doc:`serialization` page.

Updating Configuration Files from the Default Configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If you would like to merge new nodes and their values to your existing configuration file you can use your
``CommentedConfigurationNode`` and load values from a given asset explained above. This will take each node in 
your asset file and attempt to place it into the new root node if it does not exist. This method is different to simply
copying to a file as this will automatically place values that were absent while just copying to file will not.

.. code-block:: java

    PluginContainer plugin = ...;

    node.mergeValuesFrom(HoconConfigurationLoader.builder()
                        .setURL(plugin.getAsset("default.conf").get().getUrl())
                        .build()
                        .load(ConfigurationOptions.defaults()));

.. note::
    
    This will not change the values of preexisting configuration nodes if they are already present, so this method can 
    be called regardless of whether or not the server already has a previous version of your configuration. 
