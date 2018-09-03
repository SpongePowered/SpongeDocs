========================
Commands and Permissions
========================

Commands are one method in which server operators can administer their server, and in which players can interact with
the server.

In Sponge, commands follow a system of :doc:`/plugin/permissions`. Permissions allow server operators to control
who can access what commands. By default, all commands are granted to players with OP status. Players without operator
status do not have access to administrative commands or commands that require an assigned permission node. A server
operator can fine-tune who can access what commands by adding/negating permission nodes through a permissions plugin.

.. note::

    Sponge is not a permissions-management plugin. To add and negate permissions for individual players or groups, you
    will need to find a permissions-management plugin.

Operator Commands
=================

These commands, in addition to regular player commands, are available to server operators.

Sponge
~~~~~~

The following commands are available to players with operator status (or the correct permission node) on servers powered
by Sponge.

======================  =========================================  =============================
Command                 Description                                Permission
======================  =========================================  =============================
/sponge audit           Forces loading of unloaded classes to      sponge.command.audit
                        enable mixin debugging.
/sponge blockinfo       Shows the type and some additional         sponge.command.blockinfo
                        information about the block you are
                        looking at.
/sponge chunks          Prints out the chunk data for a world, a   sponge.command.chunks
                        dimension, or globally.
/sponge config          Alters a global, world, or a dimension     sponge.command.config
                        config.
/sponge entityinfo      Shows the type and some additional         sponge.command.entityinfo
                        information about the entity you are
                        looking at.
/sponge heap            Dumps the JVM heap.                        sponge.command.heap
/sponge mods            Lists currently installed forge mods.      sponge.command.mods
                        (SpongeForge only)
/sponge plugins         Lists currently installed Sponge plugins.  sponge.command.plugins
/sponge plugins reload  Asks plugins to perform their own reload   sponge.command.plugins.reload
                        procedures.
/sponge reload          Reloads the global, world, or dimension    sponge.command.reload
                        config.
/sponge save            Saves the global, world, or dimension      sponge.command.save
                        config.
/sponge timings         The main command for the timings module.   sponge.command.timings
/sponge tps             Display ticks per second for each world.   sponge.command.tps
/sponge version         Prints the Sponge/SpongeAPI versions to    sponge.command.version
                        the console.
/sponge which           Prints which plugin provided the command,  sponge.command.which
                        it's aliases and alternatives.
/sponge:callback        Internally used for callback actions on 
                        ``Text``\s (such as pagination). Not
                        intended to be invoked by hand.
/sponge:help            View information on commands used on the   sponge.command.help
                        server.
======================  =========================================  =============================

|

**Sponge Command Parameters**

* /sponge chunks [-g] [-d dim] [-w world]
* /sponge config [-g] [-d dim] [-w world] key value
* /sponge save [-g] [-d dim|*] [-w world|*]
* /sponge reload [-g] [-d dim|*] [-w world|*]

**Command Conflicts**

In cases of command conflict, Sponge provides a primary alias mechanism to specify which command is to be used.  For 
example, Minecraft provides the `reload <https://minecraft.gamepedia.com/Commands#Summary_of_commands>`__ command and 
Sponge provides the `reload <https://docs.spongepowered.org/stable/en/server/spongineer/commands.html>`__ command. To 
specify which command to use, prefix it with ``minecraft`` or ``sponge`` and a ``:``. So, to use Sponge's reload command 
above, type in ``/sponge:reload``. This approach can also be used to handle conflicts between mods and/or plugins. Do
the same thing, just use the mod-id or the plugin-id and a ``:``.  An example is ``/examplemodid:tp``.

Furthermore, the primary alias mechanism can be used to overcome incompatibilities. Let's say a plugin registers a 
command, but the command is incompatible with your mod. If you can configure your mod to use a Minecraft native 
command or another plugin's command, you can restore the expected behavior or prevent unexpected behaviors.

.. note::

    The ``/sponge audit`` command forces loading of any classes which have not yet been loaded, allowing the full output
    from all mixin debugging environment variables to be captured. This also requires the mixins.checks variable, see
    the `Mixin wiki <https://github.com/SpongePowered/Mixin/wiki/Mixin-Java-System-Properties>`__ for more information.

.. tip::

    Here are a few simple examples of the sponge config command in action. Please see
    :doc:`../getting-started/configuration/index` for a more detailed explanation.

    a. ``/sponge config logging.chunk-load true``

      Since no dimension was specified, the dimension would default to the sender(player) dimension. So, if you were in
      a mystcraft dimension, this would alter the mystcraft dimension config.

    b. ``/sponge config -d minecraft:nether logging.chunk-load true``

    Since a dimension type was specified, this would alter the nether dimension config (and hence all nether worlds).

    c. ``/sponge config -w DIM1 logging.chunk-load true``

    This would alter the config of world named DIM1.

Sponge provides two permissions for debugging purposes:

* Allow using Sponge's block and entity tracking message channel: ``sponge.debug.block-tracking``
  This message channel is used by the client (if the SpongeForge mod is installed) to display the block and entity
  owner and notifier in the debug view.
* Allow hovering of command execution errors to get the stacktrace: ``sponge.debug.hover-stacktrace``

Timings
~~~~~~~

Timings are a tool built into Sponge that allows server administrators to monitor the performance of their server.
Timings will collect information about a server so that a report may later be generated on the data. Information that
is recorded by timings include the server motd, version, uptime, memory, installed plugins, tps, percent of tps loss,
number of players, tile entities, entities, and chunks.
Below is a list of sub-commands to ``/sponge timings``:

========================  ========================================
Command                   Description
========================  ========================================
/sponge timings on        Enables timings. Note that this will
                          also reset timings data.
/sponge timings off       Disables timings. Note that most timings
                          commands will not function and timings
                          will not be recorded if timings are
                          disabled.
/sponge timings reset     Resets all timing data and begins
                          recording timing data after the time
                          this command was done.
/sponge timings report    Generates the timings report on your
                          server performance at
                          https://timings.aikar.co
/sponge timings verbon    Enables timings monitoring at the
                          verbose level.
/sponge timings verboff   Disables timings monitoring at the
                          verbose level. Note that high-frequency
                          timings will not be available.
/sponge timings cost      Gets the cost of using timings.
========================  ========================================

Forge
~~~~~

The following commands are available only when using the SpongeForge coremod on Forge. Other implementations of the
SpongeAPI, such as SpongeVanilla, do not include these commands.

====================  ========================================  ====================
Command               Description                               Permission
====================  ========================================  ====================
/forge tps            Display ticks per second for each world.  forge.command.forge
/forge track          Enable tile entity tracking.              forge.command.forge
====================  ========================================  ====================

|

For any Forge mods that use the vanilla command API, command permissions are provided in the form
``<modid>.command.<commandname>``.


Vanilla
~~~~~~~

There are several commands built-in to vanilla Minecraft that are also available on servers powered by Sponge. The list
below is not comprehensive, but it includes the most commonly used commands. These commands are available to players
with operator status (or the correct permission node). In general, permissions for vanilla Minecraft commands on a
Sponge server are of the structure ``minecraft.command.<command>``, as shown below.

====================  ========================================  ================================
Command               Description                               Permission
====================  ========================================  ================================
/ban                  Ban a player.                             minecraft.command.ban
/ban-ip               Ban a player's IP address.                minecraft.command.ban-ip
/banlist              View all banned players.                  minecraft.command.banlist
/clear                Clear an inventory.                       minecraft.command.clear
/deop                 Remove OP from a player.                  minecraft.command.deop
/difficulty           Set the game difficulty.                  minecraft.command.difficulty
/gamemode             Set the gamemode of a player.             minecraft.command.gamemode
/gamerule             Set a gamerule.                           minecraft.command.gamerule
/give                 Give an item to a player.                 minecraft.command.give
/kill                 Kill a player or entity.                  minecraft.command.kill
/op                   Give Operator status to a player.         minecraft.command.op
/pardon               Remove a player from the ban list.        minecraft.command.pardon
/save-all             Save the server.                          minecraft.command.save-all
/save-off             Disable automatic server saving.          minecraft.command.save-off
/save-on              Enable automatic server saving.           minecraft.command.save-on
/setidletimeout       Define how long players can be idle       minecraft.command.setidletimeout
                      before getting kicked.
/setworldspawn        Set the spawnpoint for the world.         minecraft.command.setworldspawn
/stop                 Stop the server.                          minecraft.command.stop
/toggledownfall       Toggle between sunny and rainy weather.   minecraft.command.toggledownfall
/tp                   Teleport players and entities.            minecraft.command.tp
/weather              Set the weather to a defined condition.   minecraft.command.weather
/whitelist            Manage the server whitelist.              minecraft.command.whitelist
/worldborder          Manage the world border.                  minecraft.command.worldborder
====================  ========================================  ================================

|

Sponge also creates permissions for these Minecraft features:

* Allow player to use the entity-selector in commands: ``minecraft.selector``
* Allow player to bypass spawn-protection in all worlds: ``minecraft.spawn-protection.override``
* Allow player to bypass force-gamemode in all worlds: ``minecraft.force-gamemode.override``
* Allow editing an ordinary commandblock of the given name: ``minecraft.commandblock.edit.block.<name>``
* Allow editing a minecart commandblock of the given name: ``minecraft.commandblock.edit.minecart.<name>``

.. note::

    These permissions use the actual *name* of the commandblock, which is normally ``@`` by default. 

There are also extra permissions managing the access to the server:

* Treat the user as whitelisted: ``minecraft.login.bypass-whitelist``
* Allow this user to bypass the server's player limit: ``minecraft.login.bypass-player-limit``

.. note::

    Sponge offers improved multi-world support, such as per-world world borders. By default, Sponge only changes the
    world border (or other world options) of the world the player is currently in. The vanilla behavior of setting it
    for all worlds can be restored using the global configuration and setting
    ``sponge.commands.multi-world-patches.worldborder`` (or the corresponding entry) to ``false``. See 
    :doc:`/server/getting-started/configuration/sponge-conf` for details. Sponge assumes that multi-world plugins also
    provide optimized configuration commands for those options and thus does not provide its own variants.

Player Commands
===============

The following commands are available as part of vanilla Minecraft to players without operator status.

====================  ========================================  ======================
Command               Description                               Permission
====================  ========================================  ======================
/help                 View information on commands used on the  minecraft.command.help
                      server, by default the Sponge command
                      will be used instead!
/me                   Tell everyone what you are doing.         minecraft.command.me
/say                  Display a message to everyone (or, if     minecraft.command.say
                      using selectors, specific players).
/tell                 Privately message another player.         minecraft.command.tell
====================  ========================================  ======================

|

A full list of vanilla commands can be found at: https://minecraft.gamepedia.com/Commands#List_of_commands. Permissions
for vanilla Minecraft commands on a Sponge server are of the structure ``minecraft.command.<command>``.

Command Features
================

Sponge and most Sponge plugins support additional command features such as auto completion and hoverable text. The image
below shows the output using the ``/sponge plugins`` command (yellow box). The elements in that list can be hovered over
to get addition information such as the current version number (red box). Some elements in the example below also have
additional actions bound to them. For example, the plugin entries in that list can be clicked to show more detailed
information (purple box) about that plugin. This is equivalent to sending the ``/sponge plugins <pluginId>`` command.
The auto completion can be triggered by pressing tab. Entering ``/sponge plugins ﻿`` (with a trailing space) and then
pressing tab will show a list of possible values (turquoise box) that can be used in that context. Pressing tab again
will iterate the possibilities, if any. Theoretically it is possible to write most commands by pressing only tab and
space (and maybe the first letter of each segment).

.. image:: /images/command-features.png
    :align: center
    :alt: command features

.. tip::

    If you are a plugin author and you want to use hoverable text and text actions in your plugin, then head over to our
    :doc:`Text documentation </plugin/text/text>`.
