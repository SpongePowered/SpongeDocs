========================
Installing SpongeVanilla
========================

SpongeVanilla is a vanilla implementation of the Sponge API as a stand-alone server.


Overview
========

SpongeVanilla is an implementation of the Sponge API that is created by patching the vanilla Minecraft server. This
means it is a stand-alone server, and does not utilise nor require Minecraft Forge or Forge mod loader (FML).
SpongeVanilla is being developed in parallel to the Forge version of Sponge, as an alternative platform for users who
do not want to run a Forge server. Originally started as an independent project and named Granite, by developers
**AzureusNation** and **VoltaSalt**, the SpongeVanilla team officially joined the Sponge development team in March 2015.

Download
========

Grab your copy of `Sponge Vanilla here <https://www.spongepowered.org/downloads>`_.

Installing SpongeVanilla
========================

.. note::

    If you use (or are planning to use) a game server host, they may have a control panel that can install Sponge for
    you.


.. note::

  When using the Mojang installer, Mojang makes use of their own Java version and not the one you installed on your
  system. The installer currently ships with Java ``1.8.0_25`` for Windows and ``1.8.0_60`` for macOS. Note that Sponge
  requires **at least** ``1.8.0_20`` or above to run properly.

SpongeVanilla only works as a dedicated server.

1. Download the SpongeVanilla .jar from the Sponge website.
#. Run it via command line: ``java -jar spongevanilla-whatever.jar``
#. Set up :doc:`../port-forward` to ensure others can connect.

.. warning::

    Don't double-click the ``.jar`` file, as it will start the server without a window to control it!


Links
=====

* `Homepage <https://www.spongepowered.org/>`__
* `GitHub <https://github.com/SpongePowered/SpongeVanilla>`__
