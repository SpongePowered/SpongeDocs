==============
Best Practices
==============

There are many ways to create a plugin, and many pitfalls for an unwary developer. Here we describe the plugin
development practices that will make the most of the Sponge API, setting sensible boundaries for the benefit of
compatibility. This information may change and expand as the Sponge project matures.


Plugin Development Guidelines
=============================

The following guidelines have been prepared to aid Sponge plugin developers. It is not a definitive or comprehensive
list, merely an attempt to detail some issues that are likely to arise during plugin development, and propose our best
solutions.


Economy API
~~~~~~~~~~~

The Economy API is used to link economy plugins with other plugins that use the economy (i.e. shops). You can read
about :doc:`economy/index` on the docs page, which details everything you need to know about the API.

Mixins
~~~~~~

Mixins are specifically for transforming classes before other mods/plugins start. ForgeModLoader calls these mods
“Coremods”. SpongeForge is a Coremod, and deploys Mixins on startup. Mixins cannot be used by plugins.


Packets
~~~~~~~

Anything to do with intercepting packets, or introducing custom items/blocks/entities/etc, is *not* planned to be part
of the Sponge API. Note that using packets may be looking at the problem the wrong way, as there may be a solution
achievable with the existing Sponge API. In some cases it may be possible to add whatever is needed to the Sponge API;
otherwise, the alternative is to use the Forge API and create a Mod instead.


Using Forge or NMS Classes
~~~~~~~~~~~~~~~~~~~~~~~~~~

We do not recommend working with Forge or Minecraft base classes at all, unless it is to provide compatibility with a
mod for Sponge API. Most uses of NMS (net.minecraft.server) code in plugins do not fail gracefully, making
troubleshooting very difficult. Maintaining NMS modifications is also more difficult than using the Sponge API. Mods that
add to the Sponge API using code internals will have to specifically write an API, which does not rely on underlying
Minecraft code, to be usable by Sponge plugins. However, plugins can be created that load separate “compatibility”
modules to interact with the underlying implementation (SpongeForge or SpongeVanilla).

Plugins using implementation-specific code are very likely to break between versions, and should be clearly labelled
as such wherever they are hosted. These may more appropriately labelled as "Mods".


Plugin Interoperability
=======================

An explanation of how to communicate with other plugins, *coming soon*.
