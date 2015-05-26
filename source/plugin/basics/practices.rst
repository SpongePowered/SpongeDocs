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

Anything to do with intercepting packets, adding NPCs, or introducing custom items/blocks/entities/etc, is *not* planned to be part of the Sponge API. Plugins/mods that require these will need to invoke Forge/NMS code, or develop some form of compatibility module to perform a similar task.


Plugin Interoperability
~~~~~~~~~~~~~~~~~~~~~~~

An explanation of how to communicate with other plugins, *coming soon*.


Using Forge or NMS Classes
~~~~~~~~~~~~~~~~~~~~~~~~~~

Mods that add to the SpongeAPI using code internals will have to specifically write an API, that does not rely on underlying Minecraft code, to be usable by Sponge plugins.
However, plugins can be created that load separate “compatibility” modules to interact with the underlying
implementation (Sponge Coremod or SpongeVanilla).

Plugins that use implementation specific code are very likely to break between versions, and should be clearly labelled as such wherever they are hosted.

