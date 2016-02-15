==========================
Frequently Asked Questions
==========================

.. contents::
   :depth: 2
   :local:

For Everyone
============

What is Sponge?
---------------

Sponge is a new versatile Minecraft API. It was made to enrich your Minecraft experience by allowing plugins to add
functionality to Minecraft. Read more about Sponge here: :doc:`introduction` and about the history of Sponge here:
:doc:`history`

What is required to run Sponge?
-------------------------------

Sponge (and Minecraft) needs the Java Runtime Environment to run properly. You will obviously need a computer to run
the server on too, besides that nothing is required. Learn more about choosing the correct Java version here:
:doc:`../server/getting-started/jre/`

Where do I get Sponge?
----------------------

Sponge currently isn't finished, but there are development builds of SpongeForge and SpongeVanilla available:
:doc:`/downloads`

What Sponge implementations are available?
------------------------------------------

The Sponge Project currently develops two implementations which both use the SpongeAPI:

(1) **SpongeForge**, a coremod for Minecraft Forge, which is an existing Minecraft modding framework famous for spurring
    the Minecraft modding scene. Forge lacks a cross-version API, and this is where Sponge steps in. Sponge allows
    server owners to deploy Sponge plugins with ease, making server management easier.
    (SpongeForge was formerly known as Sponge, until it was renamed to avoid confusion).

(2) **SpongeVanilla**, a stand-alone implementation of the Sponge API, running on top of the vanilla Minecraft server.
    (SpongeVanilla was formerly known as Granite, until the development teams merged).

Where do I get Plugins for Sponge?
----------------------------------

On our official plugin repository, which is called Ore. Development has already started on GitHub. Temporarily, plugins
are hosted on the `SpongeForums <https://forums.spongepowered.org/c/plugins/plugin-releases>`_.

What happened to Bukkit?
------------------------

The Bukkit project halted further development of their API and server modification. Shortly thereafter, one of the
contributors to Bukkit sent a DMCA takedown notice to stop further distribution of CraftBukkit. He was within his legal
right. Downloads, as well as source code, for CraftBukkit and its derivatives (such as Spigot and Cauldron) are no
longer publicly available. If you want to know the reasons why this affected Sponge development, have a look at our
history page: :doc:`history`

Can I run Bukkit Plugins with this?
-----------------------------------

Sadly no, not natively. Sponge is using its own API (Sponge API), while Bukkit is using the Bukkit API. However there is the
possibility that a third-party plugin for Sponge implements the Bukkit API on top of Sponge API.

For Server Owners
=================

I'm a Server Owner! How Will Switching to Sponge Affect My Server?
------------------------------------------------------------------

For an existing Forge server, you will need to download Sponge and place it into the mods folder. The server can then
be started like any other Forge server.

Non-Forge servers may elect to use SpongeVanilla instead, an implementation that does not rely on Forge. There are
guides for migrating from Bukkit and/or Canary elsewhere on SpongeDocs. It is worth noting that many plugin developers
from the Bukkit community have thrown their weight behind Sponge, and are planning to make their plugins available for
Sponge-powered servers.

Worlds will be able to be ported over. It is up to plugin developers to create conversion processes that will allow you
to keep plugin data, if any exists. Some plugin developers may not do this.

On a related note, we will not be providing support for Bukkit plugins on Sponge. However, it may be possible for a
third-party to create a way for Bukkit plugins to work on Sponge.

How Will Switching to Sponge Affect Players on My Server?
---------------------------------------------------------

Switching to Sponge should not affect players on your server. If you (as a server owner) migrate correctly, players will
be able to connect to your server the same way as they did before you migrated to Sponge. They will not need to have
Forge installed - unless your server runs Forge mods, of course.

For Developers
==============

What can I do with Sponge?
--------------------------

Sponge provides a Plugin API. This means that you can create new content and gamemodes on the go.
Have a look at our plugin pages to get a quick-start: :doc:`../plugin/index`

What can't I do with Sponge? / Limitations of Sponge?
-----------------------------------------------------

Sponge can't be used to create new blocks, textures, mobs on the clientside or any other content which would need
clientside modifications. The Sponge API wont support sending mods or plugins to the client for now due to security
concerns. However you can make use of the ForgeAPI for clients and create Sponge plugins for the serverside.
It is even possible to use Sponge on the client-side, but for several tasks mods are still required.

I'm a Bukkit Plugin Developer! Why Can't Sponge Use Bukkit's API?
-----------------------------------------------------------------

Bukkit’s API contains code licensed under the GPL. This is a large reason why Bukkit met its demise in September; by
moving forward with a new API licensed under the MIT license, we can avoid some of the problems that fell upon Bukkit.
While this does not free us from Mojang’s control, as their code is proprietary, it is our belief that Mojang supports
modding and will continue to do so.

Will I Be Able to Access the Server Internals In My Plugins?
------------------------------------------------------------

Accessing the server internals (known as "NMS" or "net.minecraft.server" in CraftBukkit) can be done through Forge,
which has a large number of names de-obfuscated. However, be aware that accessing the server internals raises the risk
of your plugin breaking - this is your prerogative.
