==================
Configuring Sponge
==================

.. toctree::
    :maxdepth: 3
    :titlesonly:

    sponge-conf
    hocon

.. _configuration_sponge:

This article is about configuring Sponge itself; plugins will usually create their own configuration files inside the 
server "config" directory.
For Vanilla configuration check out the `Minecraft Wiki <https://minecraft.gamepedia.com/Server.properties>`__.

Sponge Configuration Files
==========================

Sponge has multiple configuration files, which all can be found in the ``config/sponge/`` directory of your server:

* ``tracker.conf``
* ``custom_data.conf``
* ``global.conf``
  and the corresponding dimension and world subconfigs in the ``worlds`` folder

The ``global.conf`` file contains the global configuration settings for Sponge. Its properties can be also overridden
for each world and dimension type by using the config files in the subfolders of ``config/worlds``.

The ``tracker.conf`` can be used to configure the Phase Tracker, and the ``custom_data.conf`` is used to manage custom
data added by plugins.

Modifying the Configuration Using a Text Editor
===============================================

Open the desired file using your preferred editor. Sponge configuration files use HOCON format, which you can read
more about in the :doc:`"Introduction to HOCON" <hocon>`.

After making your desired changes, save your file. If your server was running while you made changes, you must reload
the config file using the reload commands explained :ref:`below <configuration_sponge_reload>`. For example, after
making changes to your ``global.conf``, run ``/sponge reload -g`` to reload the global configurations from file.

Overriding the Global Configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If you would like to override the global configs in just a single world or dimension type, for example raise the
spawn cap of monsters in the Vanilla Nether alone, subconfigs allow you to do just that. You can find configs for
every world and dimension on your server within the ``worlds`` folder, inside the main Sponge config folder. These
are sorted first by mods, and then dimension type. Simply modify those configs to your needs, and the changes will
be applied to all the related worlds, overriding broader configs.
This follows a simple order: World > Dimension > Global

Modifying the Configuration Using Commands
==========================================

It is possible to modify most configs through the in-game command ``/sponge config``. The syntax for the config
command looks like this:

.. code-block:: none

    /sponge config <flag> <key> <value>

There are the following **flags** for specifying the target that you would wish to change.

* ``-g`` is the flag for global (``global.conf``)
* ``-d <dim>`` targets a dimension (``dimension.conf``)
* ``-w <world>`` targets one world (``world.conf``)
* ``-t`` is the flag for tracker (``tracker.conf``)

``<name>`` being a placeholder, you need replace with the name of your target, e.g. ``-d minecraft:nether``.

The **key** is the value you want to change. The **value** is whatever you want to change the value of the key to.
For example, if you want to change ``chunk-load`` to ``true``
in the file ``config/sponge/minecraft/nether/dimension.conf``:

.. code-block:: none

    sponge {
        logging {
            # Log when chunks are loaded
            chunk-load=false
        }
    }

Here is the command you should use:

.. code-block:: none

    /sponge config -d minecraft:nether logging.chunk-load true

If you need to check the value of a key, simply omit the value.
Checking the value of a key such as ``logging.chunk-load`` in the nether can be done like so:

.. code-block:: none

    /sponge config -d minecraft:nether logging.chunk-load

Saving Config
~~~~~~~~~~~~~

Saving a world config to the file may be desired after making modifications. This can be useful in the event of an
unexpected server crash. This is done by using the ``/sponge save`` command on the server. The syntax for this
command is similar to the config command:

.. code-block:: none

    /sponge save <flag>

Here is an example for saving the global config:

.. code-block:: none

    /sponge save -g

.. _configuration_sponge_reload:

Reloading a Config
~~~~~~~~~~~~~~~~~~

Sometimes you need to reload a world config while the server is still running. This is useful when you have
made changes to the local config file, and want to reload it for use on the live server. This is made
possible by the command ``/sponge reload``. The syntax for the command is as follows:

.. code-block:: none

    /sponge reload <flag>

Here is an example of reloading the end world config file:

.. code-block:: none

    /sponge reload -d minecraft:the_end
