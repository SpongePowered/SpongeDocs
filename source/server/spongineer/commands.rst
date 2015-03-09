========
Commands
========

Commands are one method in which server operators can administer their server, and in which players can interact with the server.

In Sponge, commands follow a system of permissions. Permissions allow server operators to control who can access what commands. By default, all commands are granted to players with OP status. Players without operator status do not have access to administrative commands or commands that require an assigned permission node. A server operator can fine-tune who can access what commands by adding/negating permission nodes through a permissions plugin.

.. note::

    Sponge is not a permissions-management plugin. To add and negate permissions for individual players or groups, you will need to find a permissions-management plugin.

Operator Commands
=================

These commands, in addition to regular player commands, are available to server operators.

Sponge
~~~~~~

The following commands are available to players with operator status (or the correct permission node) on servers powered by Sponge.

====================  ========================================  ====================
Command               Description                               Permission
====================  ========================================  ====================
/sponge reloadconfig  Send a message to all plugins and Sponge  Not yet available.
                      to reload their configuration.
/sponge chunks        Prints out the chunk data for a world, a
                      dimension, or globally.
/sponge conf          Alters a global, world, or a dimension
                      config.
/sponge heap          Dumps the JVM heap.
/sponge version       Prints the Sponge/SpongeAPI versions to
                      the console.
/sponge save          Saves the global, world, or dimension
                      config.
/sponge reload        Reloads the global, world, or dimension
                      config.
====================  ========================================  ====================

Forge
~~~~~~

The following commands are available only when using the Sponge coremod on Forge. Other implementations of the Sponge API, such as Granite, do not include these commands.

====================  ========================================  ====================
Command               Description                               Permission
====================  ========================================  ====================
/forge tps            Display ticks per second for each world.  Not yet available.
/forge track          Enable tile entity tracking.
====================  ========================================  ====================

Vanilla
~~~~~~~

There are several commands built-in to vanilla Minecraft that are also available on servers powered by Sponge. The list below is not comprehensive, but it includes the most commonly used commands. These commands are available to players with operator status (or the correct permission node).

====================  ========================================  ====================
Command               Description                               Permission
====================  ========================================  ====================
/ban                  Ban a player.                             Not yet available.
/ban-ip               Ban a player's IP address.
/banlist              View all banned players.
/clear                Clear an inventory.
/deop                 Remove OP from a player.
/difficulty           Set the game difficulty.
/gamemode             Set the gamemode of a player.
/gamerule             Set a gamerule.
/give                 Give an item to a player.
/kill                 Kill a player or entity.
/op                   Give Operator status to a player.
/pardon               Remove a player from the ban list.
/save-all             Save the server.
/save-off             Disable automatic server saving.
/save-on              Enable automatic server saving.
/setidletimeout       Define how long players can be idle
                      before getting kicked.
/setworldspawn        Set the spawnpoint for the world.
/stop                 Stop the server.
/toggledownfall       Toggle between sunny and rainy weather.
/tp                   Teleport players and entities.
/weather              Set the weather to a defined condition.
/whitelist            Manage the server whitelist.
/worldborder          Manage the world border.
====================  ========================================  ====================

Player Commands
===============

The following commands are available as part of vanilla Minecraft to players without operator status.

====================  ========================================  ====================
Command               Description                               Permission
====================  ========================================  ====================
/help                 View information on commands used on the  Not yet available.
                      server
/me                   Tell everyone what you are doing.
/say                  Display a message to everyone (or, if
                      using selectors, specific players).
/tell                 Privately message another player.
====================  ========================================  ====================


A full list of vanilla commands can be found at: http://minecraft.gamepedia.com/Commands#List_of_commands
