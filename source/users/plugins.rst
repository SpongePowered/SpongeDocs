==================
Installing Plugins
==================

Finding Plugins
===============

.. warning::
    Do not download plugins from untrustworthy sources! Malicious plugins can be used to give others unauthorized access to your server or computer.

Installation
============

In order to install plugins, place them into the "mods" folder of your game or server directory.

If your download came in a .zip file, then you may need to extract it to find a .jar file inside.

Configuration
=============

To configure plugins, find the "config" folder that should be alongside your "mods" folder. Inside, you should find configuration files for some of the plugins that you have installed.

Changing a configuration file will not necessarily take effect right away if your game or server is currently running. Use the ``/sponge reloadconfig`` command to send a message to all plugins to reload their plugins. However, not all plugins may abide this message, so you may need to restart your game or server if your changes do not seem to be taking effect.

Common Problems
===============

If you are having problems with a plugin, consider the following things:

* If the plugin compatible with your Minecraft version? While Sponge tries to keep old plugins working, this is sometimes not possible.
* Do you have a new enough Java version? While Sponge is built for Java 6 minimum, some plugins may require newer versions of Java such as 7 or 8.
* Is there a new version of the plugin?