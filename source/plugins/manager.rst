.. External references.

.. _documentation for PluginContainer: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/plugin/PluginContainer.html
.. _documentation for PluginManager: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/plugin/PluginManager.html
.. _documentation for Game: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/Game.html

===============================
Working with the Plugin Manager
===============================

The Plugin Manager is what your plugin gets sent to after being loaded by the server at startup. The server loads your plugin by finding its main class, annotated by the ``@Plugin`` annotation that holds its general information, and sends a new instance of it to the manager. The manager then keeps that instance in its own collection that you can look into and pull from using methods provided by itself, thus allowing you to easily interact with another loaded plugin if you so desire.

The ``PluginManager`` Class
~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. tip::

    See the `documentation for PluginManager`_ for full information about the class, as well as its methods and their usage.

Public methods inside the ``PluginManager`` are used to grab information about the current collection of loaded plugins, alongside their instances. The plugins are stored inside a ``PluginContainer`` (discussed in next section) to allow for an easy center of information about the specific plugin. As an example, you can use the ``PluginManager`` to communicate with another plugin, grabbing its instance and using the methods it offers to provide compability or extended features by means of your calling plugin.

Obtaining the Plugin Manager
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You can get an instance of the server's ``PluginManager`` using a few different ways.

1. Dependency Injection
-----------------------

.. tip::

    See the :doc:`advanced/dependency-injection` guide for help on using dependency injection.

The ``PluginManager`` is one of the few API instances that are injected into the main class upon being loaded. To ask for a reference, create a new variable to hold the ``PluginManager`` instance and simply annotate it with ``@Inject``.

.. code-block:: java

    package examples.plugin;

    import com.google.inject.Inject;
    import org.spongepowered.api.plugin.PluginManager;

    @Inject
    private PluginManager pluginManager;

2. The Service Manager
----------------------

.. tip::

    See :doc:`services` for a full guide about the Service Manager.

The service manager also holds an instance of the server's ``PluginManager``. Simply use the method ``ServiceManager.provide()``, passing the ``PluginManager``'s class (``PluginManager.class``) as a parameter.

.. code-block:: java

    package examples.plugin;

    import org.spongepowered.api.plugin.PluginManager;
    import org.spongepowered.api.service.ServiceManager;

    private PluginManager pluginManager = serviceManager.provide(PluginManager.class);

3. The Game Instance
--------------------

.. tip::

    See the `documentation for Game`_ for full information about the class, as well as its methods and their usage.

A game instance can provide a reference to the server's ``PluginManager`` as well for convenience.

.. code-block:: java

    package examples.plugin;

    import org.spongepowered.api.Game;
    import org.spongepowered.api.plugin.PluginManager;

    private PluginManager pluginManager = game.getPluginManager();

Now that you have an instance to the plugin manager, let's use it.

Using the Plugin Manager
~~~~~~~~~~~~~~~~~~~~~~~~

The plugin manager provides several methods for working with plugins.

A lot of methods return plugin containers, which will be discussed in the next section.
Plugin containers are pretty much self-explanatory "containers" of the actual plugin instance.

With the plugin manager, it is possible to get all plugins currently loaded through the plugin manager:

.. code-block:: java

    package examples.plugin;

    import java.util.Collection;
    import org.spongepowered.api.plugin.PluginContainer;

    private List<PluginContainer> plugins = pluginManager.getPlugins();

Or, it is possible to obtain an instance to a plugin container directly, by the example shown below:

.. code-block:: java

    package examples.plugin;

    import org.spongepowered.api.plugin.PluginContainer;

    private PluginContainer myOtherPlugin = pluginManager.getPlugin("myOtherPluginId").orNull();

The PluginContainer Class
~~~~~~~~~~~~~~~~~~~~~~~~~

.. tip::

    See the `documentation for PluginContainer`_ for full information about the class, as well as its methods and their usage.

When grabbing a plugin from the ``PluginManager``, you'll notice very quickly that you are not given an immediate instance of the requested plugin. Instead, you'll be greeted by a ``PluginContainer`` containing information about the plugin attained from its ``@Plugin`` annotation in its main class, as well as the loaded instance.

The ``PluginContainer`` will hold any generic information about the plugin set by its owning developer. You can use information from here instead of hard-coding what you know about it in your supporting plugin. An example scenario would be if the owning developer changes the name of the plugin, references to the latter in the supporting plugin would not become wrong as a result of this change, provided you've used the method ``PluginContainer.getName()`` to get its name.

.. code-block:: java

    package examples.plugin;

    import org.spongepowered.api.plugin.PluginContainer;

    private PluginContainer myOtherPlugin = pluginManager.getPlugin("myOtherPluginId").orNull();
    private MyOtherPlugin pluginInstance = (MyOtherPlugin) myOtherPlugin.getInstance();

.. note::

    ``PluginContainer.getInstance()`` will return as an ``Object``. You need to cast it as the target plugin after obtaining it from the container.
