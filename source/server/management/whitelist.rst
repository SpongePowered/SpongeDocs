======================
Managing the Whitelist
======================

The whitelist allows you to control who can join your server. Be aware that ops will *always* be able to connect to the
server, regardless of whether they're in the whitelist.

When the whitelist function is enabled, only players named on the whitelist will be allowed to login to your server.
Players can be added to the whitelist through the usage of in-game :doc:`../spongineer/commands` or by editing the
``whitelist.json`` file. 

- To enable the whitelist, use ``/whitelist on``
- To disable the whitelist, use ``/whitelist off``
- To add a player to the whitelist, use ``/whitelist add playername``
- To remove a player from the whitelist, use ``/whitelist remove playername``
- To show all players on the whitelist, use ``/whitelist list``
- To reload the whitelist after a manual change to the file, use ``/whitelist reload``

The whitelist can also be enabled or disabled by editing the
`server.properties <https://minecraft.wiki/w/Server.properties>`__ file,
although this will only affect the game after server reload or restart.

Permissions
===========

You can also use these :doc:`/plugin/permissions` to manage the access to your server.

=================================== ====================================================================
Permission                          Description
=================================== ====================================================================
minecraft.login.bypass-whitelist    Treat the user as whitelisted. Defaults to ``Op``-only.
minecraft.login.bypass-player-limit Allow this user to bypass the server's player limit. Defaults to the
                                    ``bypassesPlayerLimit`` option in ``ops.json``.
=================================== ====================================================================
