===============================
Working with the Plugin Manager
===============================

The Plugin Manager is what your plugin gets sent to after being loaded by the server at startup. The server loads your plugin by finding its main class, annotated by the ``@Plugin`` annotation that holds its general information, and sends a new instance of it to the manager. The manager then keeps that instance in its own collection that you can look into and pull from using methods provided by itself, thus allowing you to easily interact with another loaded plugin if you so desire.

The ``PluginManager`` Class
===========================

.. _documentation for PluginManager: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/plugin/PluginManager.html

.. tip::

  See the `documentation for PluginManager`_ for full information about the class, as well as its methods and their usage.

Public methods inside the ``PluginManager`` are used to grab information about the current collection of loaded plugins, alongside their instances. The plugins are stored inside a ``PluginContainer`` (discussed in next section) to allow for an easy center of information about the specific plugin. As an example, you can use the ``PluginManager`` to communicate with another plugin, grabbing its instance and using the methods it offers to provide compability or extended features by means of your calling plugin.

Obtaining the ``PluginManager``
===============================

You can get an instance of the server's ``PluginManager`` using a few different ways.

1. Dependency Injection
~~~~~~~~~~~~~~~~~~~~~~~

.. tip::

  See the :doc:`advanced/dependency-injection` guide for help on using dependency injection.

The ``PluginManager`` is one of the few API instances that are injected into the main class upon being loaded. To ask for a reference, create a new variable to hold the ``PluginManager`` instance and simply annotate it with ``@Inject``.

Example - Grabbing the Plugin Manager (Dependency Injection)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

  package examples.plugin;

  import com.google.inject.Inject;
  import org.spongepowered.api.plugin.PluginManager;

  @Inject
  private PluginManager pluginManager;

2. The Service Manager
~~~~~~~~~~~~~~~~~~~~~~

.. tip::

  See :doc:`services` for a full guide about the Service Manager.

The service manager also holds an instance of the server's ``PluginManager``. Simply use the method ``ServiceManager.provide()``, passing the ``PluginManager``'s class (``PluginManager.class``) as a parameter.

Example - Grabbing the Plugin Manager (Service Manager)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

  package examples.plugin;

  import org.spongepowered.api.plugin.PluginManager;
  import org.spongepowered.api.service.ServiceManager;

  private PluginManager pluginManager = serviceManager.provide(PluginManager.class);

3. The Game Instance
~~~~~~~~~~~~~~~~~~~~

.. _documentation for Game: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/Game.html

.. tip::

  See the `documentation for Game`_ for full information about the class, as well as its methods and their usage.

A game instance can provide a reference to the server's ``PluginManager`` as well for convenience.

Example - Grabbing the Plugin Manager (Game Instance)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

  package examples.plugin;

  import org.spongepowered.api.Game;
  import org.spongepowered.api.plugin.PluginManager;

  private PluginManager pluginManager = game.getPluginManager();


And now that you have the ``PluginManager``...

Example - Grabbing Another ``PluginContainer``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

  package examples.plugin;

  import org.spongepowered.api.plugin.PluginContainer;
  import org.spongepowered.api.plugin.PluginManager;

  import com.google.inject.Inject;

  private PluginManager manager = ;

  //Grab our plugin's container using the plugin manager.
  private PluginContainer myOtherPlugin = manager.getPlugin("myOtherPluginId").get();


The ``PluginContainer`` Class
=============================

.. _documentation for PluginContainer: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/plugin/PluginContainer.html

.. tip::

  See the `documentation for PluginContainer`_ for full information about the class, as well as its methods and their usage.

When grabbing a plugin from the ``PluginManager``, you'll notice very quickly that you are not given an immediate instance of the requested plugin. Instead, you'll be greeted by a ``PluginContainer`` containing information about the plugin attained from its ``@Plugin`` annotation in its main class, as well as the loaded instance.

The ``PluginContainer`` will hold any generic information about the plugin set by its owning developer. You can use information from here instead of hard-coding what you know about it in your supporting plugin. An example scenario would be if the owning developer changes the name of the plugin, references to the latter in the supporting plugin would not become wrong as a result of this change, provided you've used the method ``PluginContainer.getName()`` to get its name.

Example - Using a ``PluginContainer`` to get a Plugin's Instance
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
.. code-block:: java

  package examples.plugin;

  import org.spongepowered.api.plugin.PluginContainer;
  import org.spongepowered.api.plugin.PluginManager;

  import com.google.inject.Inject;

  /* Things like PluginManager are injected into the plugin's main class upon being
   * loaded.
   *
   * See https://docs.spongepowered.org/en/plugins/advanced/dependency-injection.html
   * for a full list of these injected API instances.
   */
  @Inject private PluginManager manager;
  private PluginContainer myOtherPlugin = manager.getPlugin("myOtherPluginId").get();

  //The plugin is returned as an object. We need to cast it as our other plugin,
  //since we know it really is our other plugin.
  private MyOtherPlugin pluginInstance = (MyOtherPlugin) myOtherPlugin.getInstance();
