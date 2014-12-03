Frequently Asked Questions
==========================

What is Sponge?
---------------

Sponge is a combination of a new API (likely based off of Spout/Flow’s
APIs) implemented on top of Forge, with assistance from other parts of
Minecraft’s modding community (Glowstone, etc.). It will be both a
server and client API, and its target user base is pretty much anyone
that wants to mod their game, including server owners. However, we may
focus on the server-side portion first. We invite any developer to help
out.

When will the first downloads be available?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The release date is still to be announced. However, the API portion is
set to be release in November.

Will there be an official place to download plugins?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Yes there will be. The complete details are still being discussed. You
can take part of the discussion here
https://forums.spongepowered.org/t/plugin-hosting/1150.

What does this mean for the players?
------------------------------------

Players should not notice anything different about the servers you know
and love. Sponge is just an API that allows developers to create plugins
just like before when using bukkit.

Will I need Forge to connect to a Sponge server?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

No modification to your client is needed, just launch and play like
normal!

What are Sponge mods
~~~~~~~~~~~~~~~~~~~~

Sponge mod is not currently avalible but is still be planned for the
future. These mods will preform a variety of functions related to the
client.

What does this mean for the server owners?
------------------------------------------

Server owners will have to download Sponge and start them like any other
Minecraft server.

What Java version is Sponge using?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Sponge will be using Java 1.6, which is the minimum version supported by
vanilla Minecraft and Forge.

Will players need the Forge client to connect?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If Sponge is used on the server, players who join will not need Sponge
or Forge installed on their game. You can use Sponge to make management
of your server easier, by allowing you to protect areas, log what
players (or even friends) do, add minigames, and so on.

What Minecraft version is Sponge be first available for?
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Sponge will be first available for Minecraft 1.8, or whatever 1.8.x
version exists at the time.

Will Bukkit Plugins work with Sponge
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

While Sponge will not be directly supporting Bukkit, community projects
have been started that aim to provide complete support for existing
Bukkit plugins on top of Sponge.

But Forge doesn’t support unmodified vanilla clients?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

At this time, yes, but LexManos has expressed plans in adding support
for vanilla clients.

But Forge takes too long to update?
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

With a large portion of the Minecraft community working together, we are
sure we can help speed up things.

I haven't kept up, what happened to Bukkit?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

One of the contributors to Bukkit sent a DMCA take down notice to have
Bukkit removed. He was within his legal right. Downloads, as well as
source code, for Bukkit and its derivatives (Spigot, Cauldron) are no
longer available.

Who?
----

Who is involved with this effort?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

-  sk89q (of WorldEdit/WorldGuard) - project lead
-  blood (of MCPC+/Cauldron) - project lead
-  LexManos (of Forge/FML/MCP)
- Portions of the Spout team: Zidane, Raphfrk, DDoS, Sleaker, Owexz, Wulfspider
- Portions of the Flow team: kitskub
- Portions from ForgeEssentials: AbrarSyed
- Other Bukkit Plugin developers: KHobbits, Elgarl, zml
- Portions of the FTB team: progwml6
- Glowstone: SpaceManiac
- Some previous contributors to Bukkit
- Other people we have likely failed to mention

However, we are interested in talking with anyone who is able to help.

How are decisions made?
~~~~~~~~~~~~~~~~~~~~~~~

The project owners, **blood** and **sk89q**, after consulting with the
community and other members when appropriate, will make the decisions.

Have you consulted the community?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Yes! While things have been moving pretty quickly, we’re very open to
input. Many of our decisions are based on discussion in the #nextstep
IRC channel (on EsperNet) as well as the results of a survey. We have
been collecting meeting notes and consensus on a Google document.

Technical Questions
-------------------

Why not use a "Wrapper"-styled API?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

A wrapper that merely works on network packets and reuses command blocks
is extremely limited in function, so plugins would only be able to do a
fraction of what they are able to do now.

Why not build a standalone server?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Reusing existing efforts in the community will allow us to have a
working version much quicker. Glowstone, unfortunately, is not free from
the EULA as it was not written in a “clean-room” fashion (in the
strictest sense of the term). Other implementations are less far along
or they do not support Java, which appears to be a major point of
contention for a lot of users and developers. None of us have the time
to write a new server from scratch, and most of us have already seen
Minecraft’s code in some form.

Doesn’t that mean that we will end up in this same situation again if we use proprietary code?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It is to our knowledge and our understanding that Mojang does not wish
to stop Minecraft modding, and the recent events have not been directly
caused by them. Rather, a contributor (a major one) objected to the use
of his code, licensed under GPL, in combination with proprietary code.
If we avoid GPL, we will not have this problem. While this does not free
us from Mojang’s control, it is to our belief that they support modding
and will continue to do so.

Why not wait for the Official Minecraft API?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

We are not sure when it will come out. Many people are not in a position
to wait. We can get started sooner. Those last three bullet points may
have said the same thing. As this new project is community-run, we may
be able to push updates quicker than Mojang is able to and react to the
needs of the community better. The API may be implemented on other
server implementations and we encourage it.

What license will Sponge be placed under?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

MIT, without a Contributor License Agreement. A Contributor License
Agreement is not necessary and it may be a turn off to contributors.

Why not use Bukkit’s API?
~~~~~~~~~~~~~~~~~~~~~~~~~

It contains GPL licensed code, which is the reason why we are in this
situation. Recently, at least in the United States, the federal courts
found that APIs could be copyrighted, although the case has not been
fully resolved. Will the new API be similar to the Bukkit API in how it
is used? Events, etc.? Yes. It should be fairly similar, and still
afford you more power because you will have access to Forge.

Why SpoutAPI (+ Flow libs) versus Canary or other APIs?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

We chose SpoutAPI purely based on the result of the survey (which is
mentioned previously). Note, however, we are not implementing SpoutAPI
as-is. It will serve as inspiration, which will reduce the amount of
time spent on API design. We will also use portions of flow’s libraries
from http://github.com/flow.

What about plugins that access ‘NMS’?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You will instead be accessing interfaces through Forge, which has a much
greater number of names de-obfuscated. However, accessing “NMS” raises
the risk of your plugin breaking as is the case here, but that is your
prerogative.

Will the new server be multi-threaded?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It will be multi-threaded in the same fashion that is Minecraft is (and
also Bukkit and Spigot was), but we are not writing a server from
scratch, so we are not able to make substantial changes.

How will the new server perform in comparison with Spigot and Bukkit?
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

We are currently investigating this, but we plan to reach or exceed
performance of the other implementations given time.

Will you be able to send mods from the server to the client?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The general consensus is against this due to security concerns.
Minecraft’s API does not plan to send mods (with executable code) to the
client either.

Will Bukkit plugins be supported, without modification?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

No, not natively, however members of the community have begun work on a
Sponge plugin named Pore that acts as a bridge between the two APIs.

For those unable to use it, we will be providing documentation and
support for people looking to transition from Bukkit to Sponge entirely.

Will I be able to keep my Bukkit worlds and data?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The plan is to create a conversion process which will convert or import
as much data as possible. Plugins will likely create their own
conversion process allowing you to keep homes, warps and other data.

What about support for Scala, Groovy, and other JVM-based languages?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

We encourage other languages but Java will be our main priority. Will
other programming languages (such as Lua) be supported? We will
encourage other implementations but it will not be a high priority.

Will Glowstone be a part of this?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

We hope we can help SpaceManiac and the team implement the API. We also
invite others to collaborate with us if they wish to.

I wish to help out.
~~~~~~~~~~~~~~~~~~~

We appreciate all offers of assistance. Please visit our volunteers portal:

-  :doc:`helping-out`

We apologise to anyone who did not receive a reply to earlier rounds of
applications. The number of sumbissions was overwhelming, thank you all!
