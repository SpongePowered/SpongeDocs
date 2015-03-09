================
Using Forge Mods
================

Forge Mods in a Sponge Server
-----------------------------

At present, the only official implementation of Sponge *in development* is as a
Forge coremod. Therefore, you must be running a Forge server to use the
Sponge API (via Sponge coremod). Forge-compatible mods should be fully
functional in this environment, provided they are updated to work with
the recommended build of Forge.


More information on Forge can be found at the `Minecraft Forge Wiki <http://www.minecraftforge.net/wiki/>`__.


Sponge will initially do very little to your server other than implement
a few Bukkit-style commands, unless there are also Sponge Plugins
present. Note that Plugins for Sponge should be placed in the *mods*
folder, much the same as Forge mods. There will be no separate *plugins*
folder in a Sponge server setup.

What If Sponge Breaks?
----------------------

Unexpected bugs may occur, so as always, please report them on the Sponge
issue tracker so we can fix them. Please make sure that the error isn't
caused by an incompatibility between the Forge version and another mod.
This can usually be accomplished by removing Sponge and testing again,
or by trying out the suspect plugin on its own in a test-server
environment. For more help in finding the source of a server problem,
visit :doc:`troubleshooting`
