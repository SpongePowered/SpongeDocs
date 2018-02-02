==========================
Choosing an Implementation
==========================

Something that runs Sponge plugins is called an *implementation*. As long as a plugin is correctly made using the
SpongeAPI, it should run correctly on any sufficiently-complete implementation.

Minecraft can't run Sponge plugins out of the box, but you can modify it to do so.

The Sponge API itself is an `open standard <https://github.com/SpongePowered/SpongeAPI>`__.

Available Implementations
=========================

There are currently two implementations:

+--------------------------------------+----------------------------------------------------------------+
| Name                                 | Based on                                                       |
+======================================+================================================================+
| :doc:`SpongeForge <spongeforge>`     | Mojang's "vanilla" Minecraft: Java Edition and Minecraft Forge |
+--------------------------------------+----------------------------------------------------------------+
| :doc:`SpongeVanilla <spongevanilla>` | Mojang's "vanilla" Minecraft: Java Edition                     |
+--------------------------------------+----------------------------------------------------------------+


Which do I choose?
~~~~~~~~~~~~~~~~~~

If you want to run MinecraftForge mods or you prefer to use Sponge in singleplayer, then choose :doc:`SpongeForge <spongeforge>`.

If you only want to run a Mincraft server with plugins on it (but no mods), then you can choose :doc:`SpongeForge <spongeforge>` or
:doc:`SpongeVanilla <spongevanilla>`. SpongeForge supports vanilla clients, as long as you don't install Forge mods which require
clientside mods. If you prefer to run a server without Forge, then SpongeVanilla is your preferred option.

SpongeVanilla and SpongeForge (without mods) behave the same, so the decision between the two is a matter of preference,
not a choice of functionality or features.

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    spongeforge
    spongevanilla
