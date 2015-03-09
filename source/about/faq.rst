==========================
Frequently Asked Questions
==========================

For Everyone
============

Are there downloads available yet?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

No. When we are ready for a release, an announcement will be posted on the Sponge forums. Keep in mind that Sponge will first be available for Minecraft 1.8.

Why is the official Sponge implementation built on Minecraft Forge?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Reusing existing efforts in the community allows us to have a working version much quicker. It would be very time-consuming to write a new server from scratch, and many people are not in a position to wait.

What makes Sponge different from other projects, such as Bukkit?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Sponge is using the MIT license, which is an extremely permissive open-source license. This means that, should our server implementation (the Sponge coremod) encounter any legal issues similar to those that fell upon Bukkit, it will be possible to reuse the Sponge API and start a new project without being further encumbered by legal issues.

Is there an official plugin repository?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Yes, and it is called Ore. Development has already started on GitHub.

What version of Java is Sponge using?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Sponge will be using Java 1.6, which is the minimum version supported by vanilla Minecraft and Forge.

I haven't kept up. What happened to Bukkit?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The Bukkit project halted further development of their API and server modification. Shortly thereafter, one of the contributors to Bukkit sent a DMCA takedown notice to stop further distribution of CraftBukkit. He was within his legal right. Downloads, as well as source code for CraftBukkit and its derivatives (such as Spigot and Cauldron) are no longer publicly available.

For Server Owners
=================

I am a server owner! How will switching to Sponge affect my server?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You will need to download Sponge and place it into the mods folder. The server can then be started like any other Forge server.

There are guides for migrating from Bukkit and/or Canary elsewhere on SpongeDocs. It is worth noting that many plugin developers from the Bukkit community have thrown their weight behind Sponge, and are planning to make their plugins available for Sponge-powered servers.

Worlds will be able to be ported over. It is up to plugin developers to create conversion processes that will allow you to keep plugin data, if any exists. Some plugin developers may not do this.

On a related note, we will not be providing support for Bukkit plugins on Sponge. However, it may be possible for a third-party to create a way for Bukkit plugins to work on Sponge.

How will switching to Sponge affect players on my server?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Switching to Sponge should not affect players on your server. If you (as a server owner) migrate correctly, players will be able to connect to your server the same way as they did before you migrated to Sponge. They will not need to have Forge installed - unless your server runs Forge mods, of course.

For Developers
==============

I’m a Bukkit plugin developer! Why can’t Sponge use Bukkit’s API?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Bukkit’s API contains code licensed under the GPL. This is a large reason why Bukkit met its demise in October; by moving forward with a new API licensed under the MIT license, we can avoid the problems that fell upon Bukkit. While this does not free us from Mojang’s control, as their code is proprietary, it is our belief that Mojang supports modding and will continue to do so.

Will I be able to access the server internals in my plugins?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Accessing the server internals (known as "NMS" or "net.minecraft.server" in CraftBukkit) can be done through Forge, which has a large number of names de-obfuscated. However, be aware that accessing the server internals raises the risk of your plugin breaking - this is your prerogative.

Will the Sponge API be similar to the Bukkit API in terms of usability?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Yes. It will also afford you more power because you will have access to Forge.

It is worth noting that the Spout API is serving as inspiration for the development of the Sponge API, which reduces the amount of time spent on API design. We are also using portions of Flow's libraries from http://github.com/flow.
