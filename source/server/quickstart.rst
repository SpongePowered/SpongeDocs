=================
Quick Start Guide
=================

This is a step by step reference on how to create a Minecraft server with Sponge.

.. note::

    Are you migrating from a Bukkit or Canary based server? Please read
    :doc:`getting-started/migrating` as it explains the differences between these platforms and Sponge,
    and explains the migration process.

.. warning::

    If you're running behind a server proxy such as BungeeCord, Waterfall or Velocity, there are additional
    steps you must follow once Sponge is installed, which are documented in
    :doc:`getting-started/bungeecord`.

System Requirements
===================

Sponge requires Java 8 update 20 or higher to run. Sponge **does not** work with Java 9 or above.
We also recommend a CPU with a large single core speed and at least 2GB of RAM (higher if you
plan to run with mods). We highly recommend using the 64bit Java runtime.

See :doc:`getting-started/jre` for more help with how to install Java 8.

SpongeForge or SpongeVanilla?
=============================

There are two official Sponge implementations on offer that integrate directly with the official
Minecraft server jar:

* **SpongeForge** - a mod for Forge that allows you to use Sponge plugins with Forge mods;
* **SpongeVanilla** - a server jar that adds Sponge support directly into vanilla Minecraft.

When choosing, there are a few things to keep in mind:

* If you want to run Forge mods alongside Sponge, you **must** use **SpongeForge**.
* If you want to run Sponge in single player, you **must** use **SpongeForge**.
* If you want a vanilla server with Sponge support, you can use either **SpongeForge** or **SpongeVanilla**.

While this suggests that you should use SpongeForge in all cases, SpongeVanilla can actually be easier to
update and setup than Minecraft Forge and SpongeForge.

.. note::

    Other implementations of the SpongeAPI exist that do not use Mojang's Minecraft code, such as Lantern.
    This guide will only focus on the implementations that the Sponge Project provides.

SpongeForge
~~~~~~~~~~~

SpongeForge is a mod for Minecraft Forge and therefore requires a Minecraft Forge server. Set up your
Minecraft Forge server first. Once you have your server set up, you can get SpongeForge from our
`downloads page <https://www.spongepowered.org/downloads/spongeforge/stable/1.12.2>`__ and place it in
the "mods" directory alongside any other mods you may wish to install. You can then start your Forge
server and Sponge should be loaded.

Note that SpongeForge requires a compatible build of Minecraft Forge to be installed on your server. The
version we build against is indicated on the downloads page, as well as the filename. Updating SpongeForge
may also require an update to Minecraft Forge.

.. warning::

    Some mods may clash with SpongeForge's changes causing errors to appear on the console.
    Before reporting this to Sponge, please refer to :ref:`quickstart-when-things-go-wrong`.
    You can also ask us for help on our support channels.

For more information on how to create a Minecraft Forge server and install SpongeForge, see
:doc:`getting-started/implementations/spongeforge`.

SpongeVanilla
~~~~~~~~~~~~~

SpongeVanilla is a server wrapper for vanilla Minecraft that adds the Sponge code to Minecraft when run.
Unlike SpongeForge, you do not need to have created a Minecraft server before running SpongeVanilla. It is
therefore simpler to setup and update, making it a good option for those that simply want the Vanilla
experience.

You can get SpongeVanilla from our
`downloads page <https://www.spongepowered.org/downloads/spongevanilla/stable/1.12.2>`__. Once downloaded,
ensure that you run SpongeVanilla using the command line, as Sponge disables the server console GUI to
increase performance. SpongeVanilla will download the appropriate version of Minecraft and setup the server
structure for you.

For more information on creating a SpongeVanilla server, see
:doc:`getting-started/implementations/spongevanilla`.

Configuring Sponge
==================

Sponge is a highly configurable product. While the core is designed to provide the SpongeAPI for servers,
Sponge also adds optional :doc:`performance optimizations <management/performance-tweaks>` and
:doc:`exploit patches <management/exploit-patches>` to the vanilla game, as well as fine tuning some of
Sponge's advanced systems. The configuration file can be found in the ``config/sponge`` directory.

For more information about how to configure Sponge, see :doc:`getting-started/configuration/index`.

Installing Plugins
==================

By default, Sponge plugins are installed in the `mods` directory, the same directory as Forge mods. Simply
add the plugins to this directory and start (or restart) your server. Your plugins should then be available,
which you can verify by running the ``/sponge plugins`` command.

.. note::

    Sponge does not have an equivalent to Bukkit's ``/reload`` command and so plugins cannot be unloaded
    and reloaded during the server lifetime. The server must be restarted to change plugins.

Once plugins are installed, they may generate configuration files. The Sponge convention is that these files
are placed in the `config` directory, but this may vary. Read the plugin documentation for any specific
guidance.

For more information about plugins, please see our :doc:`page on installing plugins <management/plugins>`.

.. note::

    You can find community supplied Sponge plugins on our
    `hosted plugin repository Ore <https://ore.spongepowered.org>`__.

Next Steps
==========

Now you have created your server, we recommend you familiarize yourself with the following so that you can
manage your server effectively:

* :doc:`management/whitelist`
* :doc:`management/bans`
* :doc:`management/permissions`
* :doc:`management/plugins`
* :doc:`management/exploit-patches`
* :doc:`management/performance-tweaks`
* :doc:`spongineer/commands`

.. _quickstart-when-things-go-wrong:

When Things Go Wrong
====================

From time to time, incompatible mods or other strange behavior might cause problems on your server. Before
reaching out for help, following the advice on these pages may help you fix your problems quickly:

* :doc:`spongineer/troubleshooting`
* :doc:`spongineer/debugging`
* :doc:`spongineer/logs`
