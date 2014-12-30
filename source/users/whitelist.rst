======================
Managing the Whitelist
======================

The whitelist allows you to control who can join your server. Be aware that ops will *always* be able to connect to the server, regardless of whether they're in the whitelist.


When the whitelist function is enabled, only players named on the whitelist will be allowed to login to your server. Players can be added to the whitelist through the usage of in-game commands or by editing the ``whitelist.json`` file. Beware, however: if you manually change the file, you will have to reload the whitelist or restart the server for the changes to go into effect. Additionally, pay special heed to the syntax, as the whitelist won't work if it is wrong.

- To enable the whitelist, use ``/whitelist on``
- To disable the whitelist, use ``/whitelist off``
- To add a player to the whitelist, use ``/whitelist add playername``
- To remove a player from the whitelist, use ``/whitelist remove playername``
- To show all players on the whitelist, use ``/whitelist list``
- To reload the whitelist after a manual change to the file, use ``/whitelist reload``

The whitelist can also be enabled or disabled by editing the :doc:`server-properties` file, although this will only affect the game after server reload or restart.

Example Valid json Syntax for the Whitelist
---------------------------------------------

This is an example of a ``whitelist.json`` file with correct formatting (although the UUID-s are fictional). Your file should follow the same syntax.
::

  [
    {
      "uuid": "01234567-89ab-cdef-0123-456789abcdef",
      "name": "Notch"
    },
    {
      "uuid": "a0b1c2d3-e4f5-0617-2839-4a5b6c7d8e9f",
      "name": "sk89q"
    }
  ]

Main points of interest:
^^^^^^^^^^^^^^^^^^^^^^^^^^

- Square braces (``[]``) open and close the file
- Each entry in the file is wrapped with curly braces (``{}``)
- Each key and its corresponding value is typed on its own line
- If more than one exists, both entries and key/value pairs are comma separated
- All strings are in quotation marks
- UUID-s are 32 symbols long, and written in hexadecimal (0-9, a-f).
- the UUID symbols are grouped. First is a group of 8, then three groups of 4, then a group of 12. The groups are separated by dashes (``-``)
