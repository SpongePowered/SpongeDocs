===================
Migrating to Sponge
===================

.. only:: html

.. contents::
   :depth: 2
   :local:


Overview
========

The purpose of the articles within this section is to help current server owners to migrate from other server platforms
to Sponge.

Migrating to Sponge (coremod)
=============================

To migrate to a Forge server running the Sponge coremod, select the platform you are migrating from below.


Migrating from CraftBukkit or Spigot
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. note::

    Spigot is a modified fork of CraftBukkit.

Worlds
------

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
------------------------------------

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
-------

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

Migrating from Canary
~~~~~~~~~~~~~~~~~~~~~

Worlds
------

Forge, and thus Sponge, uses the same world structure as vanilla Minecraft. Vanilla Minecraft places the nether
(typically ``world_nether``) and the end (typically ``world_the_end``) dimensions within the ``world`` folder.

Canary relocates the nether and end dimensions outside of the ``world`` folder, which must be remedied if it is desired
to retain the nether and end dimensions when running Sponge. However, Canary provides an easy method to convert Canary
worlds to a structure usable by Sponge with the ``/makevanilla`` command. If the world conversion is successful, the
output will be placed in the ``vanilla`` folder.

Server and World Configuration Files
------------------------------------

Sponge uses many files that are made available by vanilla Minecraft, such as ``server.properties``. Canary, however,
does not; the only file it has in common with vanilla Minecraft is ``usercache.json``. Thus, ``usercache.json`` is the
only file from Canary that can be reused on Sponge.

Nevertheless, it is possible to manually migrate some Canary configuration files to their Sponge counterparts, which
have been provided below.

+----------------------------+----------------------------+
| Canary file(s)             | Sponge counterpart(s)      |
+============================+============================+
| server.cfg                 | server.properties          |
| <world>_<dimension>.cfg    |                            |
+----------------------------+----------------------------+
| <world>_<dimension>.cfg    | global.conf                |
|                            | <dimension>/dimension.conf |
+----------------------------+----------------------------+
| ops.cfg                    | ops.json                   |
+----------------------------+----------------------------+
| db.cfg                     | No counterpart             |
+----------------------------+----------------------------+
| motd.txt                   | No counterpart             |
+----------------------------+----------------------------+

Plugins
-------

Sponge has no native support for Canary plugins. It may be possible to re-implement the Canary API in a special Sponge
plugin.

Ore is Sponge's official repository for finding plugins, and it is recommended to download all Sponge plugins from Ore.
When finding replacements for your Canary plugins, there are a few points to keep in mind:

* Not all Canary developers have chosen to port their plugins to Sponge. Over time, however, someone else may create a
  suitable replacement.
* Not all Sponge plugins that are ported from Canary will automatically convert configuration files. Individual plugin
  developers make the decision on whether or not to automatically convert configuration files.
* Some Sponge plugins that are ported from Canary may change in functionality, or may not even use the same configuration
  structure.


Migrating from Forge
~~~~~~~~~~~~~~~~~~~~

Instructions
------------

Migrating from a plain Forge server to a Sponge server is a fairly simple process that needs little-to-no preparatory
work.

You must first ensure you are running a version of Forge that is compatible with the version of Sponge your plan to use.
You may find recommended builds of Forge at http://files.minecraftforge.net. If you are using any other mods, they must
also be updated.

When you are ready to install Sponge, you may proceed with the following steps:

1. Stop your Forge server if it is running.
#. Download the Sponge coremod from the Sponge website.
#. Place the Sponge coremod into your ``mods`` folder.
#. Start the server and party!

.. note::

    If Sponge is the only mod on your server, players will be able to log in with a vanilla client. Other mods may
    require players to install Forge on their own computers.

Migrating from Vanilla
~~~~~~~~~~~~~~~~~~~~~~

The intent of this article is to help people who currently run vanilla servers to migrate to Sponge.

Administrators of vanilla Minecraft servers can migrate to Sponge easily because Forge, and thus Sponge, uses the same
world structure as vanilla Minecraft. Sponge also uses the same files used by vanilla Minecraft, such as
``server.properties``.

Migrating to SpongeVanilla
==========================

To migrate to a server running SpongeVanilla select the platform you are migrating from below.

.. note::
    As there currently are no development builds of SpongeVanilla available, there currently are no guides available on
    how to migrate to SpongeVanilla. These will be added when development builds are available.

Installing Sponge
=================

The guide at :doc:`../../../../server/getting-started/installation` provides instructions for installing Sponge, once
the above steps have been completed.
