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

Modifying the Config In-Game
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It is possible to modify these configs through the in-game command ``/sponge config``. The syntax for the config
command looks like this:

.. code-block:: java

    /sponge config <flag> <key> <value>

There are *flags* for specifying the target that you would wish to change. These flags are global, dimension, and world.

* ``-g`` is the flag for global
* ``-d <dim>`` targets a dimension (replacing *<dim>* with the dimension you want to configure)
* ``-w <world>`` targets one world (replacing *<world>* with your chosen world).

The *key* is the value you want to change. The *value* is whatever you want to change the value of the key to.

Here is an example of this command in action:

.. code-block:: java

    /sponge config -d minecraft:nether logging.chunk-load true

This will set the config to log when chunks are loaded for the nether.

If you need to check the value of a key, you would need to omit the *value*. Checking the value of a key such as
``logging.chunk-load`` in the nether would be done like so:

.. code-block:: java

    /sponge config -d minecraft:nether logging.chunk-load

Saving a World Config
~~~~~~~~~~~~~~~~~~~~~

Saving a world config to the file may be desired after making modifications. This would be useful in the event of an
unexpected server crash. This would be done by using the ``/sponge save`` command on the sponge server. The syntax for
this command is similar to the config command:

.. code-block:: java

    /sponge save <flag>

Here is an example for saving the global config:

.. code-block:: java

    /sponge save -g

Reloading a World Config
~~~~~~~~~~~~~~~~~~~~~~~~

Sometimes it may be desired that a world config is reloaded while the server is still running. This would be useful if
you have made changes to the local config file and would like to reload it for use on the live server. This is made
possible by the command ``/sponge reload``. The syntax for the command is as follows:

.. code-block:: java

    /sponge reload <flag>

Here is an example of reloading the end world config file:

.. code-block:: java

    /sponge reload -d minecraft:the_end
