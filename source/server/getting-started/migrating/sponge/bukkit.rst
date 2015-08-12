====================================
Migrating from CraftBukkit or Spigot
====================================

Overview
========

The intent of this article is to help people who currently run CraftBukkit or Spigot servers to migrate to Sponge.

.. note::

    Spigot is a modified fork of CraftBukkit.

Getting Ready
=============

Worlds
~~~~~~

Forge, and thus Sponge, uses the same world structure as vanilla Minecraft. Vanilla Minecraft places the nether
(typically ``world_nether``) and the end (typically ``world_the_end``) dimensions within the ``world`` folder.

CraftBukkit relocates the nether and end dimensions outside of the ``world`` folder, which must be remedied if it is
desired to retain the nether and end dimensions when running Sponge. To relocate the nether and end dimensions, follow
the instructions below.

.. tip::

    It is a good idea to make a backup of your worlds in case you make a mistake.

1. Stop your CraftBukkit server if it is running.
#. Copy ``world_the_end/DIM1`` to ``world/DIM1``.
#. Copy ``world_nether/DIM-1`` to ``world/DIM-1``.

Server and World Configuration Files
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

CraftBukkit and Sponge both share files that are made available by vanilla Minecraft. These files can thus be reused on
Sponge, if they are already present in your CraftBukkit installation:

* ``server.properties``
* ``banned-ips.json``
* ``banned-players.json``
* ``ops.json``
* ``usercache.json``
* ``whitelist.json``

The following files are used by CraftBukkit only, and can be removed because Sponge does not use them:

* ``bukkit.yml``
* ``commands.yml``
* ``help.yml``
* ``permissions.yml``

Users who are migrating from Spigot may wish to compare ``spigot.yml`` to ``global.conf`` in Sponge. Some keys in
``spigot.yml`` have counterparts in ``global.conf``, and it may be desirable to copy over the values of any keys that
are present in both files.

Plugins
~~~~~~~

Sponge has no native support for Bukkit plugins. However, some members of the community are re-implementing the Bukkit
API with a special Sponge plugin, which may allow Bukkit plugins to function on a Sponge server. This plugin has not yet
been slated for release.

Ore is Sponge's official repository for finding plugins, and it is recommended to download all Sponge plugins from Ore.
When finding replacements for your Bukkit plugins, there are a few points to keep in mind:

* Not all Bukkit developers have chosen to port their plugins to Sponge. Over time, however, someone else may create a
  suitable replacement.
* Not all Sponge plugins that are ported from Bukkit will automatically convert configuration files. Individual plugin
  developers make the decision on whether or not to automatically convert configuration files.
* Some Sponge plugins that are ported from Bukkit may change in functionality, or may not even use the same
  configuration structure.

Installing Sponge
=================

The guide at :doc:`../../../../server/getting-started/installation` provides instructions for installing Sponge, once
the above steps have been completed.
