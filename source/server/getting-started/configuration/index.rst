==================
Configuring Sponge
==================

You can find all configuration files inside the "config" folder.

Config Syntax
=============

Most configuration files will make use of the :doc:`HOCON <hocon>` format.

.. toctree::
    :maxdepth: 3
    :titlesonly:

    hocon
    json

What You Can Configure
======================

.. toctree::
    :maxdepth: 3
    :titlesonly:

    sponge-conf
    server-properties

Plugins will also have their own configuration files in the "config" folder.

World Configs
=============

There are three types of world configs:

* Global
* Dimension
* World

Global configuration files can affect all of a server's worlds and dimensions. This is the default level for configs.
Dimension configuration files are used to affect a certain dimension or group of worlds. These types of configs will
override the global config files. World configuration files are used to modify individual worlds only. World configs
override dimension and global configs.

It is possible to modify these configs through the in-game command ``/sponge conf``.

The sponge conf command syntax looks like this:

``/sponge conf <flag> <name> <key> <value>``

The *flag* is the group you are targeting, such as global, dimension, or world.

* ``-g`` is the flag for global
* ``-d dim`` targets a dimension (replacing *dim* with the dimension you want to configure)
* ``-w world`` targets one world (replacing *world* with your chosen world).

The *key* is the value you want to change. The *value* is whatever you want to change the value of the key to.

An example of this command in action:

``/sponge conf -d nether logging.chunk-load true``

.. tip::
    Changing a configuration file will not necessarily take effect right away if your game or server is currently
    running. Use the ``/sponge reloadconfig`` command to send a message to all plugins and Sponge to reload their
    configuration. However, you may need to restart your game or server for some changes to take effect.
