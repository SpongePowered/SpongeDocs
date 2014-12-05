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

.. tip::
    Changing a configuration file will not necessarily take effect right away if your game or server is currently running. Use the ``/sponge reloadconfig`` command to send a message to all plugins and Sponge to reload their configuration. However, you may need to restart your game or server for some changes to take effect.