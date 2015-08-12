==========================
Choosing an Implementation
==========================

Something that runs Sponge plugins is called an *implementation*. As long as a plugin is correctly made using the
"Sponge API," it should run correctly on any sufficiently-complete implementation.

Minecraft can't run Sponge plugins out of the box, but you can modify it to do so.

The Sponge API itself is an `open standard <https://github.com/SpongePowered/SpongeAPI>`__.

Available Implementations
=========================

There are currently three implementations:

.. csv-table::
   :header: "Name", "Based on"

   :doc:`Sponge Coremod <sponge>`,"Mojang's Minecraft and Minecraft Forge"
   :doc:`Sponge Vanilla <spongevanilla>`,"Mojang's Minecraft"
   :doc:`Glowstone <glowstone>` (not developed by the Sponge team),"(standalone)"

Which do I choose?
~~~~~~~~~~~~~~~~~~

The first two items listed in the table change Minecraft's existing code to run Sponge plugins and are ideal if you want
something that plays exactly the same as unmodified Minecraft (same world generation, same Redstone mechanics). The first
option also integrates `Minecraft Forge <http://www.minecraftforge.net/forum/>`__, which lets you run Minecraft Forge
mods alongside Sponge plugins.

While it would may appear that the Coremod option may be the best, you may want to consider the other options because of:

* Performance concerns, as other options may use less memory and CPU to do the same task
* Open source concerns, as only Glowstone is fully open source (Minecraft isn't)

The goal is for all listed implementations to run all Sponge plugins correctly and in the same way, so you should be able
to switch between different implementations at a later time (as far as plugins are concerned).

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    sponge
    spongevanilla
    glowstone
