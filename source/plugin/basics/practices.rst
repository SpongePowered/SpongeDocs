==============
Best Practices
==============

There are many ways to create a plugin, and many pitfalls for an unwary developer. Here we describe the plugin development practices that will make the most of the Sponge API, setting sensible boundaries for the benefit of compatibility. This information may change and expand as the Sponge project matures.


Plugin Development Guidelines
=============================

The following guidelines have been prepared to aid Sponge plugin developers. It is not a definitive or comprehensive list, merely an attempt to detail some issues that are likely to arise during plugin development, and propose our best solutions.


Economy API
~~~~~~~~~~~

An Economy API is currently in development, using the Services API. It is anticipated that plugins wishing to use economy features will utilise this service once available.


Mixins
~~~~~~

Mixins can not be used by Plugins. Mixins are specifically for transforming classes before other mods/plugins start. ForgeModLoader calls these mods “CoreMods”. Sponge is a CoreMod, and deploys Mixins on startup.  


Packets
~~~~~~~

Anything to do with intercepting packets, or introducing custom items/blocks/entities/etc, is *not* planned to be part of the Sponge API. Note that using packets may be looking at the problem the wrong way, as there may be a solution achievable with the existing Sponge API. In some cases it may be possible to add whatever is needed to the Sponge API; otherwise, the alternative is to use the Forge API and create a Mod instead. 


Using Forge or NMS Classes
~~~~~~~~~~~~~~~~~~~~~~~~~~

Mods which add to the Sponge API using code internals will have to specifically write an API, that does not rely on underlying Minecraft code, to be usable by Sponge plugins.
However, plugins can be created that load separate “compatibility” modules to interact with the underlying
implementation (Sponge Coremod or SpongeVanilla).

Plugins using implementation-specific code are very likely to break between versions, and should be clearly labelled as such wherever they are hosted. These may more appropriately labelled as "Mods".


Plugin Interoperability
=======================

An explanation of how to communicate with other plugins, *coming soon*.
