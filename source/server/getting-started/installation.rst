=================
Installing Sponge
=================

Recall that there are two official variants of Sponge (SpongeForge and SpongeVanilla) — they both run Sponge plugins
identically (that's the goal), but how either work behind the scenes (the code) differs. This is better explained in
the :doc:`implementations/index` page.

Instructions for both are listed on this page.

You will also need to :doc:`install Java <jre>`. To recap, Java 8 is the minimum version required; older versions (ie Java 6 and 7) are no longer supported.

Installing SpongeForge
======================

.. note::

    If you use (or are planning to use) a game server host, they may have a control panel that can install Sponge for you.

Single Player / In-Game LAN Servers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. warning::
  When using the Mojang Minecraft installer for Windows, Mojang makes use of their own Java version and not the one one
  you installed on your system. The installer currently ships with Java ``1.8.0_25``, note that Sponge requires
  **at least** ``1.8.0_40`` or above to run properly. You can grab the Launcher without included Java here:
  `official Minecraft Launcher <https://s3.amazonaws.com/Minecraft.Download/launcher/Minecraft.exe>`_

1. Download the Minecraft Forge installer from the `Minecraft Forge website <http://files.minecraftforge.net/>`_. The
   latest version, or any version that's newer than the one listed :doc:`in the filename of the Sponge download
   <implementations/spongeforge>`, should work.
#. Run the provided Forge installer. A new Forge profile will be created in the Minecraft launcher.
#. Open the Minecraft launcher, and select the new Forge profile.
#. Click "Options" and click "Open Game Dir".
#. Download SpongeForge from the Sponge website and put it into the ``mods`` folder. Create the folder if it does
   not yet exist.
#. Sponge should work in both in single player and if you open your world to LAN.

Next, learn how you can create and use a :doc:`launch-script <launch-script>`, how to
:doc:`configure Sponge <configuration/index>` and how to :doc:`manage your server <../management/index>`
(including installing plugins).

Dedicated Servers
~~~~~~~~~~~~~~~~~

.. note::

    If you already have a Forge server, just put the Sponge mod into your ``mods`` folder. Remember to update your Forge
    version to match the one that :doc:`sponge requires <implementations/spongeforge>`.

Installing Forge via Commandline
--------------------------------

1. Visit the `Minecraft Forge website <http://files.minecraftforge.net/>`_ and click "Show all downloads" to view the full
   set of available options. Identify the version matching the one listed :doc:`in the filename of the SpongeForge download
   <implementations/spongeforge>`, and hover over the (i) next to "Installer" to get the direct download link.
#. Use your favorite download method to download the jar to its destination.
   Example: ``wget http://url.to/forge-version-installer.jar``
#. From the folder in which you wish to install Forge, execute the jar with the ``--installServer`` option. Example:
   ``java -jar forge-version-installer.jar --installServer``
#.  Continue to Adding SpongeForge to Forge below.


Installing Forge via GUI
------------------------

1. Download the Minecraft Forge installer from the `Minecraft Forge website <http://files.minecraftforge.net/>`_ for the version
   matching the one listed :doc:`in the filename of the SpongeForge download <implementations/spongeforge>`.
#. Run the provided Forge installer, select "Install Server", choose an empty folder to place the server's files,
   and then click OK.
#. Continue to Adding SpongeForge to Forge below.


Adding SpongeForge to Forge
---------------------------

1. Download SpongeForge from the Sponge website and put it into the ``mods`` folder in your server directory.
   Create the folder if it does not yet exist.
#. You may now launch the server via command or launch script ``java -jar forge-version-XYZ.jar``.
#. If operating from home, set up :doc:`port-forward` to ensure others can connect.

Next, learn :doc:`how you can configure Sponge <configuration/index>` and how to :doc:`how to manage your server
<../management/index>` (including installing plugins).


Installing SpongeVanilla
========================

SpongeVanilla only works as a dedicated server.

1. Download the SpongeVanilla .jar from the Sponge website.
#. Run it via command line: ``java -jar spongevanilla-whatever.jar``
#. Set up :doc:`port-forward` to ensure others can connect.

.. warning::

    Don't double-click the ``.jar`` file!
