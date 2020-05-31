============
Introduction
============

What Is Sponge?
~~~~~~~~~~~~~~~

The purpose of the Sponge project is to create a plugin development framework for Minecraft: Java Edition. Sponge is
being created by a global community, and its open-source nature means anyone can participate.

Sponge was created after seeing the failures of other projects in the Minecraft plugin development community. We are
trying to avoid making the same mistakes as other projects; thus:

* Sponge is an extremely open project.
* Sponge is licensed under the MIT license - an extremely permissive open source license.
* Performance is of high priority.

Most plugins developed with SpongeAPI should work across several different versions of Minecraft without needing to be
updated. This means that, for the most part, server owners do not need to worry about plugin incompatibilities.

We have two other projects in addition to SpongeAPI:

(1) **SpongeForge**, a coremod for Minecraft Forge, which is an existing Minecraft: Java Edition modding framework
    famous for spurring the Minecraft modding scene. Forge lacks a cross-version API, and this is where Sponge steps
    in. Sponge allows server owners to deploy Sponge plugins with ease, making server management easier.

(2) **SpongeVanilla**, a stand-alone implementation of SpongeAPI, running on top of the vanilla Minecraft server.
    (SpongeVanilla was formerly known as Granite, until the development teams merged).

Players on servers running SpongeForge or SpongeVanilla do not need to install any client-side mods. They are able to join
servers running Sponge using the vanilla Minecraft client provided by Mojang.

SpongeAPI is not tied to any platform. This means that server owners can run Sponge plugins on any official
implementation of SpongeAPI. Sponge plugins should function identically on either implementation, due to the
common functionality of mixins.

Where Can I Download Sponge?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You can download SpongeForge and SpongeVanilla on our `downloads page <https://www.spongepowered.org/downloads/>`_.

Who Is behind Sponge?
~~~~~~~~~~~~~~~~~~~~~

The project leaders are blood, gabizou and Zidane. We are trying to be very open with the team to ensure the project leaders
do not end up “holding all the keys.” Nonetheless, these three people make the final decisions to ensure the efficient
operation of the project.

A full list of staff members is located at :doc:`staff`.

Our developers are well-versed with Java, and many of them have worked with Minecraft for years and know the ins-and-outs
of its mechanics. There are tons of very good developers working on the Sponge project, and it would be nearly impossible
to list all of them!
