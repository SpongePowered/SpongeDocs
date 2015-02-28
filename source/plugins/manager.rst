===============================
Working with the Plugin Manager
===============================

The Plugin Manager is what your plugin gets sent to after being loaded by the server at startup. It will keep track of plugin instances including your own, and will allow you to easily interact with another plugin if you so desire.

The ``PluginManager`` Class
===========================

.. _documentation for ``PluginManager``: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/plugin/PluginManager.html

.. tip::

  See the `documentation for ``PluginManager```_ for full information about the class, as well as its methods and their usage.

Public methods inside the ``PluginManager`` are used to grab information about the current collection of loaded plugins, alongside their instances. Example usages would be to communicate with another plugin, grabbing its instance and using the methods it offers to provide compability or extended features by means of the calling plugin.

TODO: Example - Grabbing Another Plugin

The ``PluginContainer`` Class
=============================

.. _documentation for ``PluginContainer``: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/plugin/PluginContainer.html

.. tip::

  See the `documentation for ``PluginContainer```_ for full information about the class, as well as its methods and their usage.

When grabbing a plugin from the ``PluginManager``, you'll notice very quickly that you are not given an immediate instance of the requested plugin. Instead, you'll be greeted by a ``PluginContainer`` containing information about the plugin attained from its ``@Plugin`` annotation in its main class, as well as the loaded instance.

The ``PluginContainer`` will hold any generic information about the plugin set by its owning developer. You can use information from here instead of hard-coding what you know about it in your supporting plugin. An example scenario would be if the owning developer changes the name of the plugin, references to the latter in the supporting plugin would not become wrong as a result of this change, provided you've used the method ``PluginContainer.getName()`` to get its name.

TODO: Example - Using a Plugin Container
