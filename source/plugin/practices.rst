==============
Best Practices
==============

.. javadoc-import::
    org.spongepowered.api.block.tileentity.TileEntity
    org.spongepowered.api.command.CommandSource
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.service.permission.Subject
    org.spongepowered.api.world.World

There are many ways to create a plugin, and many pitfalls for an unwary developer. Here we describe the plugin
development practices that will make the most of the Sponge API, setting sensible boundaries for the benefit of
compatibility. This information may change and expand as the Sponge project matures.


Plugin Development Guidelines
=============================

The following guidelines have been prepared to aid Sponge plugin developers. It is not a definitive or comprehensive
list, merely an attempt to detail some issues that are likely to arise during plugin development, and propose our best
solutions.

.. note::

   We reserve the use of *Sponge* for official SpongePowered works. Please don't use *Sponge* as part of your plugin
   name, unless (1) your plugin primarily concerns the Minecraft block "Sponge", or (2) your plugin also has versions
   for other APIs (in which case you may append "for Sponge" to the title).


Economy API
~~~~~~~~~~~

The Economy API is used to link economy plugins with other plugins that use the economy (i.e. shops). You can read
about the Economy API :doc:`here <economy/index>`, which details everything you need to know about the API.


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


Mixins
~~~~~~

Mixins are specifically for transforming classes before other mods/plugins start. ForgeModLoader calls these mods
“Coremods”. SpongeForge is a Coremod, and deploys mixins on startup. Mixins can be used by plugins, but be aware of the
additional complexities involved. 

**Hybrid Mods**

Sponge plugins which leverage mixins may also be a considered core mods, based on content.

- To use mixins in FML, it must be a coremod. The jar may also contain a Sponge plugin,
  so most properly the container is a "hybrid mod".
- To use mixins in SpongeVanilla, intentions must be declared in the manifest.
  SpongeVanilla then injects the mixins.
- Having both types in a single jar is entirely possible. (Indeed, a single jar could
  easily contain a tweaker, FML mod, coremod, bukkit plugin, sponge plugin, and/or litemod.)
  
Some definitions may be helpful here. 

Tweak Mod (aka Tweaker)
  a subsystem-level mod which hooks directly into the game using LaunchWrapper, usually used for
  ModSystems (eg. LiteLoader, FML) and stand-alone mods (eg. Optifine). Can interact with any aspect
  of the game environment directly. Generally breaks every version.

Core Mod
  has almost equivalent power of a Tweak Mod but must be bootstrapped by a ModSystem.
  Can interact with any aspect of the game environment directly. Generally breaks on every new Game version.

Mod
  interacts with the game only via a ModSystem, the mod is exposed to game objects directly but will
  generally only hook into the game via hooks provided by the ModSystem. Generally breaks every major
  version of the Game (depending on features used). The term mod is also used as an umbrella term for
  anything which modifies the game, though for the sake of clarity we'll use this definition.

Plugin
  interacts with the game only via an API, does not interact with game objects directly in any way,
  only leverages objects exposed by the API. Generally breaks only when the API is revised
  (and sometimes not even then).

It's also important to distinguish the container from the contents. Issues with terminology tends to
arise because a jar containing a mod tends to get referred to as a "mod".
Any plugin which is not fully decoupled via the API puts itself into the category of Mod.
This type of "plugin" may be prevalent where there are shortcomings in an API.

**Advantages of Hybrid Mods**

A hybrid mod leverages both a plugin component which interacts via the API, and a mod (or even coremod)
in the same package. This has the disadvantages of a mod (breaks every version) but also the power of a
mod (can interact with the game directly) coupled with some of the benefits of a Plugin (high-level
abstract access to the game, and can also interact with other plugins as a peer).

The primary benefit of this system is that the maintenance burden is reduced when updating the mod,
because any features accessed via the API are likely to be much more stable.

This type of mod can be used to implement plugins whose needs overflow the capability of the API (in
the case of a plugin which needs to leverage mixins for a particular feature); but can also be used
for mods which want to leverage services afforded by the API (eg. a mod which wants to provide direct
support for permissions or chat channels).

Unlike NMS-exploiting "plugins", a hybrid mod makes its nature clear.



Plugin Interoperability
=======================

An explanation of how to communicate with other plugins, *TBA*.


Bad Practices
=============

These should practices should be avoided, as they can lead to memory leaks (``OutOfMemoryError``), lag or
inconsistencies.


Storing References
~~~~~~~~~~~~~~~~~~

Some instances such as 

* :javadoc:`DataHolder`
* :javadoc:`TileEntity`
* :javadoc:`CommandSource`
* :javadoc:`Entity`
* :javadoc:`Player`
* :javadoc:`Subject`
* :javadoc:`World`
* and containers that **MIGHT** contain any of the above elements, including
    * ``Collections``
    * ``Maps``

should **NEVER** be stored or cached in plugins.

These are the main reasons for this:

* The references prevent proper garbage collection
* The instances might no longer be valid

This can easily be avoided by using the corresponding snapshots or saving the UUID of the given instances and requesting
a live instance when you need it.


IO on the main thread
~~~~~~~~~~~~~~~~~~~~~

Executing some IO operations such as loading a config/data file or checking for updates/connecting to a website takes
much time and greatly affects the TPS on the server. Such tasks should be done either in their own threads, or using the
inbuilt scheduler's async feature. It is perfecty fine to load required files/config on the main thread during server
startup/plugin initialization though.

.. code-block:: text

    this.game.getScheduler().createAsyncExecutor(this).execute(this::checkForUpdates);

For more details refer to the :doc:`scheduler` docs.

If this is done wrong, you will see clients timing out or the server's watchdog might kill the server.


Accessing game objects outside the main thread
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Accessing game objects outside of the main thread can lead to crashes, inconsistencies and various other problems and
should be avoided. If you have a lengthy operation on a different thread use the :doc:`scheduler` to execute the apply
part on the main thread. If you want to use an game object in a different thread use a snapshot of the instance or a
detached data container.

.. warning::

    If this is done wrong, you can get a ``ConcurrentModificationException`` with or without a server crash at best and
    a corrupted player/world/server at worst.
