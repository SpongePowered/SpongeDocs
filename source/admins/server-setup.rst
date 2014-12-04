How to make a Sponge Server
===========================

The procedure below demonstrates how to set up a Sponge server using
Minecraft Forge.

For information on the various implementations of the Sponge API, see :doc:`implementations`

Installing Sponge on your Forge server
--------------------------------------

-  In order to run a Sponge server, you must first install the
   recommended build of Forge Server. This can easily be accomplished by
   downloading and using the Universal Forge installer from their
   `download site <http://files.minecraftforge.net/>`__. When Forge
   installation is complete, you must place the recommended build of the
   Sponge CoreMod into the server "mods" folder. Starting the Forge
   server will then cause all the relevant server configuration files
   for Sponge to be generated. At this point, stop the server and edit
   these files if required.

-  On server start-up using the Sponge CoreMod, it will attempt to load
   all files in the "mods" folder. These files must be plugins written
   specifically for Sponge, using the Sponge API. Bukkit plugins *will
   not work* unless there as also some kind of functioning third-party
   compatibility-layer plugin, such as *Pore* (still a work in
   progress). For information about Bukkit plugins, see :doc:`../bukkit-compatibility`

-  If you run into problems, visit :doc:`troubleshooting`
