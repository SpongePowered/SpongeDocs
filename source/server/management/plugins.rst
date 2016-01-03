==================
Installing Plugins
==================

What Are Plugins
================

Plugins are files written in Java that change the way the server works, generally adding features. They allow server
owners to do a lot of modifications, such as adding an economy system, managing teleports and permissions, etc.

Finding Plugins
===============

.. warning::
    Do not download plugins from untrustworthy sources! Malicious plugins can be used to give others unauthorized access
    to your server or computer.

Installation
============

SpongeForge
~~~~~~~~~~~

In order to install plugins, place them into the "mods" folder of your game or server directory.

If your download came in a .zip file, then you may need to extract it to find a .jar file inside.

SpongeVanilla
~~~~~~~~~~~~~

.. note:: For consistency between SpongeForge and SpongeVanilla, plugins are stored in the mods directory on SpongeVanilla.

In order to install plugins, place them into the "mods" folder of your game or server directory.

If your download came in a .zip file, then you may need to extract it to find a .jar file inside.

Common Problems
===============

If you are having problems with a plugin, consider the following things:

* Is the plugin compatible with your Minecraft version? While Sponge tries to keep old plugins working, this is sometimes
  not possible. In most cases, plugins based on a stable release should continue functioning without being updated.
* Do you run a current Java version? Sponge is built for Java 8 and is known to trigger a bug in the JRE 1.8.0_40, so
  make sure you use a version newer than that.
* The plugin may be outdated. Is there a newer version of the plugin?
* Does the plugin need a specific Implementation and/or Build of Sponge?  Some plugins may bypass the Sponge API, or
  otherwise rely on details that change between versions or platforms. Check at the site you downloaded it from.
