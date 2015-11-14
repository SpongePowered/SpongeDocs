=====================
Configuration Loaders
=====================

Let's break down how Configurate works, beginning from our loading process. Configurate provides ``ConfigurationLoaders``
for common configuration formats, standing as the manager of the physical file of your configuration, allowing you to
save and load data from the given resource. They also allow you to load empty configurations, giving you the option of
hard-coding default values or loading from a pre-written file.

Getting your Loader
~~~~~~~~~~~~~~~~~~~

First, let's grab a new ``HoconConfigurationLoader`` that points to our configuration file.

.. code-block:: java

    import ninja.leaping.configurate.commented.CommentedConfigurationNode;
    import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
    import ninja.leaping.configurate.loader.ConfigurationLoader;

    import java.io.File;

    File potentialFile = new File("config.conf");
    ConfigurationLoader<CommentedConfigurationNode> loader =
      HoconConfigurationLoader.builder().setFile(potentialFile).build();

The loader will also hold a generic type denoting what kind of node it'll build for you. These nodes will be discussed
in a later section.

``ConfigurationLoader``\ s usually hold a builder for you to statically access and create a new instance of the loader of
your desired type. For a basic configuration, we don't really need to specify anything other than the file we want to
load from and/or save to, so all we'll do is tell it exactly that, using ``HoconConfigurationLoader.builder().setFile(File)``.
We then tell the builder to build the instance (``build()``) for it to give it to our variable.

Of course, this isn't the only way to load a file. The builder also has the method ``setURL(URL)``, in case you might want to load a resource without using a ``File`` object. Bear in mind that configuration loaders created from an ``URL`` are read-only as they have no way of writing back data to the URL.

This functionality may be used to bundle default configurations with your plugin jar file and load them as initial configuration to be edited by the server administrator (or your plugin itself).

Example: Loading a default config from the plugin jar file
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import java.net.URL;

    URL jarConfigFile = this.getClass().getResource("defaultConfig.conf");
    ConfigurationLoader<CommentedConfigurationNode> loader =
      HoconConfigurationLoader.builder().setURL(jarConfigFile).build();

For this example it is important to note that the ``getResource(...)`` method works relative to the location of the
class it is called on. So if in the above example the class lies in the package ``me.username.myplugin``, the
``defaultConfig.conf`` file must not lie in the jar file root, but instead in the directory ``me/username/myplugin``.

.. warning::

    Since all Sponge plugins share a single namespace, all resources available to the ``getResource()`` method are
    visible to all other plugins as well. Therefore, resources placed in the root of a jar may overwrite (or be
    overwritten by) equally named resources in another jar. Placing those resources in unique folder structures
    similar to your java packages will mitigate the danger of accidentally having a resource file overwritten by
    another plugin.
