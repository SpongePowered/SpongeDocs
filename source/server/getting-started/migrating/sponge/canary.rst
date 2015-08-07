=====================
Migrating from Canary
=====================

Overview
========

The intent of this article is to help people who currently run Canary servers to migrate to Sponge.

Getting Ready
=============

Worlds
~~~~~~

Forge, and thus Sponge, uses the same world structure as vanilla Minecraft. Vanilla Minecraft places the nether
(typically ``world_nether``) and the end (typically ``world_the_end``) dimensions within the ``world`` folder.

Canary relocates the nether and end dimensions outside of the ``world`` folder, which must be remedied if it is desired
to retain the nether and end dimensions when running Sponge. However, Canary provides an easy method to convert Canary
worlds to a structure usable by Sponge with the ``/makevanilla`` command. If the world conversion is successful, the
output will be placed in the ``vanilla`` folder.

Server and World Configuration Files
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
~~~~~~~

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

Installing Sponge
=================

The guide at :doc:`../../../../server/getting-started/installation` provides instructions for installing Sponge, once
the above steps have been completed.
