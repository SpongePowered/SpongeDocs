=============
Configuration
=============

You can find all configuration files inside the "config" folder.

Config Syntax
=============

Most configuration files will make use of the HOCON format.

.. toctree::
    :maxdepth: 3
    :titlesonly:

    hocon

What You Can Configure
======================

.. toctree::
    :maxdepth: 3
    :titlesonly:

    sponge-conf
    server-properties

Plugins will also have their own configuration files in the "config" folder.

World Configs
======================

There are three types of world configs:

* Global
* Dimension
* World

Global configuration files can affect all of a server's worlds and dimensions. This is the default level for configs.
Dimension configuration files are used to affect a certain dimension or group of worlds. These types of configs will override the global config files.
World configuration files are used to modify individual worlds only. World configs override dimension and global configs.

It is possible to modiy these configs through the in-game command ``/sponge``.
This can be done through the ``/sponge conf`` command.

To use this command, you would type something like this:

``/sponge conf flag <name> key <value>``

replacing each value with what you desire. The flag is the group you are targeting, such as global, dimension, or world.
``-g`` is the flag for global, ``-d dim`` for dimension (replacing dim with the dimension you are targeting), and ``-w world`` for world (again replacing world with your targeting world).
The key is the value you are targeting to change. The value would be filled in with whatever you want to change the value of the key to.

An example of this in action would be:
``/sponge conf -d nether logging.chunk-load true``

A list of subcommands can be found at the :doc:`commands` page.

.. tip::
    Changing a configuration file will not necessarily take effect right away if your game or server is currently running. Use the ``/sponge reloadconfig`` command to send a message to all plugins and Sponge to reload their configuration. However, you may need to restart your game or server for some changes to take effect.
