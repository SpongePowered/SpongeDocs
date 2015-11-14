===================
Configuration Nodes
===================

Before we discuss the next section, let's learn about what a ``ConfigurationNode`` is. To put it in simplest terms, a
``ConfigurationNode`` holds all the data stored within a config file. From the root node of the config file, you can
read values set by you or the user, or write values to save to a file with simple calls to the value retrieval methods,
and ``setValue(Object[] path, Object value)``.

In previous topics, it was noted that the configuration loader holds a generic type to allow you to choose what type of
node it should generate. Currently, the only extra node is a ``CommentedConfigurationNode``. You're free to use a normal
``ConfigurationNode``, but for configurations to be modified by server administrators, the ``CommentedConfigurationNode``
will help you write comments to values of the file to help them customize the configuration values. Documentation on the
``CommentedConfigurationNode`` is available on the Configurate wiki, linked at the index of these pages.

Getting your Configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~

Now that we have a loader, we can load our configuration from it with a simple call to ``load()``. This will give us a
``ConfigurationNode`` with all values found within the config file. From here, you can start reading the values to
accommodate for features within your plugin, or write new values to store some data. Note the ``IOException`` that's
thrown when loading the node from the file and handle it accordingly.

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.commented.CommentedConfigurationNode;
    import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
    import ninja.leaping.configurate.loader.ConfigurationLoader;

    import java.io.File;
    import java.io.IOException;

    File potentialFile = new File("config.conf");
    ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setFile(potentialFile).build();
    ConfigurationNode rootNode;
    try {
        rootNode = loader.load();
    } catch(IOException e) {
        rootNode = null;
    }

Previously, it was mentioned that loaders can also load blank configuration nodes for you to write default data on. This
can be done with a simple call to ``ConfigurationLoader.createEmptyNode(ConfigurationOptions)``. (and you don't even
have an ``IOException`` to catch!)

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationOptions;

    File potentialFile = new File("config.conf");
    ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setFile(potentialFile).build();
    ConfigurationNode rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());

.. warning::

    Note the parameter passed to satisfy the method creating the empty node. While the value of ``null`` seems to work
    fine upon initializing, this will cause an internal ``NullPointerException`` while attempting to set values on the node. Pass a default ``ConfigurationOptions`` instance instead by using the static method ``ConfigurationOptions.defaults()`` to generate one.
