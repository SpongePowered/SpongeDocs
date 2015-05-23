=================
Installing Sponge
=================

Overview
========

Recall that there are two official variants of Sponge (coremod and SpongeVanilla) — they both run Sponge plugins identically (that's the goal), but how either work behind the scenes (the code) differs. This is better explained in the :doc:`implementations/index` page.

Instructions for both are listed on this page.

You will also need to :doc:`install Java <jre>`. To recap, Java 8 is recommended, but Java 7 should work too.

Installing Sponge (coremod)
===========================

.. note::

    If you use (or are planning to use) a game server host, they may have a control panel that can install Sponge for you.

Single Player / In-Game LAN Servers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

1. Download the Minecraft Forge installer from the `Minecraft Forge website <http://files.minecraftforge.net/>`_. The latest version, or any version that's newer than the one listed :doc:`in the filename of the Sponge download <implementations/sponge>`, should work.
#. Run the provided Forge installer. A new Forge profile will be created in the Minecraft launcher.
#. Open the Minecraft launcher, and select the new Forge profile.
#. Click "Options" and click "Open Game Dir".
#. Download the Sponge coremod from the Sponge website and put it into the ``mods`` folder. Create the folder if it does not yet exist.
#. Sponge should work in both in single player and if you open your world to LAN.

Next, learn :doc:`how you can configure Sponge <configuration/index>` and how to :doc:`how to manage your server <../management/index>` (including installing plugins).

Dedicated Servers
~~~~~~~~~~~~~~~~~

.. note::
    
    If you already have a Forge server, just put the Sponge mod into your ``mods`` folder. Remember to update your Forge version if it's older than the one that :doc:`sponge requires <implementations/sponge>`.

Via GUI
--------

1. Download the Minecraft Forge installer from the `Minecraft Forge website <http://files.minecraftforge.net/>`_. The latest version, or any version that's newer than the one listed :doc:`in the filename of the Sponge download <implementations/sponge>`, should work.
#. Run the provided Forge installer, select "Install Server", choose an empty folder to place the server's files, and then click OK.
#. Download the Sponge coremod from the Sponge website and put it into the ``mods`` folder in your server directory. Create the folder if it does not yet exist.
#. You may now launch the server via command or launch script ``java -jar forge-version-XYZ.jar``.
#. Set up :doc:`port-forward` to ensure others can connect.

Next, learn :doc:`how you can configure Sponge <configuration/index>` and how to :doc:`how to manage your server <../management/index>` (including installing plugins).

Headless (Command-line)
-----------------------

If you are installing the server on your personal computer or via remote deskop, we recommend using the steps listed above instead.

However, if you are working via command line or SSH, run the following commands where you want to put the server's files (after downloading the Sponge coremod):

1. ``java -jar sponge.jar install``
#. ``java -jar sponge.jar run``

Or you can do both in one go:

* ``java -jar sponge.jar go``

Next, learn :doc:`how you can configure Sponge <configuration/index>` and how to :doc:`how to manage your server <../management/index>` (including installing plugins).

.. tip::

    Developer Me4502 has provided a `tutorial video for installing Sponge (coremod). <https://www.youtube.com/watch?v=UTIyjjO6lxY>`__

Installing SpongeVanilla
========================

SpongeVanilla only works as a dedicated server.

1. Download the SpongeVanilla .jar from the Sponge website.
#. Run it via command line: ``java -jar spongevanilla-whatever.jar``
#. Set up :doc:`port-forward` to ensure others can connect.

.. warning::

    Don't double-click the ``.jar`` file!
