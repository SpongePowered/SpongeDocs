=====================
Configuration Loaders
=====================

Let's break down how Configurate works, beginning from our loading process. Configurate provides ``ConfigurationLoaders`` for common configuration formats, standing as the manager of the physical file of your configuration, allowing you to save and load data from the given resource. They also allow you to load empty configurations, giving you the option of hard-coding default values or loading from a pre-written file.

Getting your Loader
~~~~~~~~~~~~~~~~~~~

First, let's grab a new ``HoconConfigurationLoader`` that points to our configuration file.

.. code-block:: java

    File potentialFile = new File("config.conf");
    ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setFile(potentialFile).build();

The loader will also hold a generic type denoting what kind of node it'll build for you. These nodes will be discussed in a later section.

``ConfigurationLoaders`` usually hold a builder for you to statically access and create a new instance of the loader of your desired type. For a basic configuration, we don't really need to specify anything other than the file we want to load from and/or save to, so all we'll do is tell it exactly that, using ``HoconConfigurationLoader.builder().setFile(File)``. We then tell the builder to build the instance (``build()``) for it to give it to our variable.

Of course, this isn't the only way to load a file. The builder also has the method ``setURL(URL)``, in case you might want to load a resource without using a ``File`` object. The following example represents loading a config from the plugin jar file.

.. code-block:: java

    URL jarConfigFile = this.getClass().getResource("defaultConfig.conf");
    ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setURL(jarConfigFile).build();
