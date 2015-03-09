======================
Managing the Whitelist
======================

The whitelist allows you to control who can join your server. Be aware that ops will *always* be able to connect to the server, regardless of whether they're in the whitelist.


When the whitelist function is enabled, only players named on the whitelist will be allowed to login to your server. Players can be added to the whitelist through the usage of in-game commands or by editing the ``whitelist.json`` file. Beware, however: if you manually change the file, you will have to reload the whitelist or restart the server for the changes to go into effect. Additionally, pay special heed to the syntax, as the whitelist won't work if it is wrong. An example of a correctly formatted whitelist file can be found at :doc:`json-formatting`.

- To enable the whitelist, use ``/whitelist on``
- To disable the whitelist, use ``/whitelist off``
- To add a player to the whitelist, use ``/whitelist add playername``
- To remove a player from the whitelist, use ``/whitelist remove playername``
- To show all players on the whitelist, use ``/whitelist list``
- To reload the whitelist after a manual change to the file, use ``/whitelist reload``

The whitelist can also be enabled or disabled by editing the :doc:`server-properties` file, although this will only affect the game after server reload or restart.
