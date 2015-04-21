===================
Using SpongeVanilla
===================

Forge Mods in a Sponge Server
-----------------------------

At present, one of two official implementations of Sponge *in development* is based on a
plain vanilla server. Forge-compatible mods are not compatible with this implementation.
If you wish to use forge mods, usage of the Sponge Coremod is recommended.

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
visit :doc:`../../../server/spongineer/troubleshooting`
