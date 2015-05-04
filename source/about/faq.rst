==========================
Frequently Asked Questions
==========================

For Everyone
============

Can I use SpongeAPI to create client side plugins?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Can I create (client side mods with sponge) / (new GUI to a players screen) / (new blocks or entities)?

**Not yet.** 

The Sponge team do not want to get overcommitted with client side API until Sponge is competitive with other Minecraft modding API's.
No one is currently working to create client side API, but it's not been forgotten; it should be possible in the long term.
This is a separate issue from single player! 
Single player support using Sponge (coremod / forge) should work fine, any issues should be reported to the bug tracker.

Are There Downloads Available Yet?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Yes, see :doc:`/downloads`.

Is the Official Sponge Implementation Built on Minecraft Forge?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

There are now TWO official implementations, Sponge (coremod) and SpongeVanilla. Sponge is a coremod for Minecraft Forge, and SpongeVanilla is a stand-alone server that does not use Forge. Take your pick!

What Makes Sponge Different from Other Projects, Such as Bukkit?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Sponge is using the MIT license, which is an extremely permissive open-source license. This means that, should our server implementation (the Sponge coremod) encounter any legal issues similar to those that fell upon Bukkit, it will be possible to reuse the Sponge API and start a new project without being further encumbered by legal issues.

Is There an Official Plugin Repository?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Yes, and it is called Ore. Development has already started on GitHub.

What Version of Java Is Sponge Using?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Sponge will be using Java 1.6, which is the minimum version supported by vanilla Minecraft and Forge.

I Haven't Kept Up. What Happened to Bukkit?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The Bukkit project halted further development of their API and server modification. Shortly thereafter, one of the contributors to Bukkit sent a DMCA takedown notice to stop further distribution of CraftBukkit. He was within his legal right. Downloads, as well as source code, for CraftBukkit and its derivatives (such as Spigot and Cauldron) are no longer publicly available.

What Happened to the Granite implementation?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The developers of Granite have joined the Sponge project, and Granite was renamed SpongeVanilla. It is now an officially supported Sponge API implementation, developed in parallel with Sponge coremod.


For Server Owners
=================

I Am a Server Owner! How Will Switching to Sponge Affect My Server?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

For an existing Forge server, you will need to download Sponge and place it into the mods folder. The server can then be started like any other Forge server.

Non-Forge servers may elect to use SpongeVanilla instead, an implementation that does not rely on Forge.
There are guides for migrating from Bukkit and/or Canary elsewhere on SpongeDocs. It is worth noting that many plugin developers from the Bukkit community have thrown their weight behind Sponge, and are planning to make their plugins available for Sponge-powered servers.

Worlds will be able to be ported over. It is up to plugin developers to create conversion processes that will allow you to keep plugin data, if any exists. Some plugin developers may not do this.

On a related note, we will not be providing support for Bukkit plugins on Sponge. However, it may be possible for a third-party to create a way for Bukkit plugins to work on Sponge.

How Will Switching to Sponge Affect Players on My Server?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Switching to Sponge should not affect players on your server. If you (as a server owner) migrate correctly, players will be able to connect to your server the same way as they did before you migrated to Sponge. They will not need to have Forge installed - unless your server runs Forge mods, of course.

For Developers
==============

I'm a Bukkit Plugin Developer! Why Can't Sponge Use Bukkit's API?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Bukkit’s API contains code licensed under the GPL. This is a large reason why Bukkit met its demise in September; by moving forward with a new API licensed under the MIT license, we can avoid some of the problems that fell upon Bukkit. While this does not free us from Mojang’s control, as their code is proprietary, it is our belief that Mojang supports modding and will continue to do so.

Will I be able to access the server internals in my plugins?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Accessing the server internals (known as "NMS" or "net.minecraft.server" in CraftBukkit) can be done through Forge, which has a large number of names de-obfuscated. However, be aware that accessing the server internals raises the risk of your plugin breaking - this is your prerogative.

Will the Sponge API Be Similar to the Bukkit API in Terms of Usability?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Yes. It will also afford you more power because you will have access to Forge.

It is worth noting that the Spout API is serving as inspiration for the development of the Sponge API, which reduces the amount of time spent on API design. We are also using portions of Flow's libraries from http://github.com/flow.
