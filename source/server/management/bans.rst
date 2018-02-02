=============
Managing Bans
=============

Minecraft, and consequently Sponge, has simple ban management to prevent unwanted users from joining your server.


The ``/ban <name> [reason]`` command is a native Minecraft server function that bans player *name*. The complete list
of banned players is available using the command ``/banlist players``


It is also possible to ban any connections from a given IP address using ``/banip <address|name> [reason]``. The complete
list of banned IP addresses is available using the command ``/banlist ips``


A ban can be reversed using the command ``/pardon <name>`` or ``/pardon <ip-address>``


More information on Bans can be found at the `Minecraft Wiki <https://minecraft.gamepedia.com/Commands/ban>`_.
