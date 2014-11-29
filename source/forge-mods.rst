Forge Mods
==========

Forge Mods in a Sponge server
-----------------------------

At present, the only implementation of Sponge *in development* is as a
Forge CoreMod. Therefore, you must be running a Forge server to use the
Sponge API (via Sponge CoreMod). Forge-compatible mods should be fully
functional in this environment, provided they are updated to work with
the recommended build of Forge.

Sponge will initially do very little to your server other than implement
a few Bukkit-style commands, unless there are also Sponge Plugins
present. Note that Plugins for Sponge should be placed the *mods*
folder, much the same as Forge mods. There will be no separate *plugins*
folder in a Sponge server setup.

What if Sponge breaks?
----------------------

Unexpected bugs may occur as always, please report them on the Sponge
issue tracker so we can fix them. Please make sure that the error isn't
caused by an incompatibility between the Forge version and another mod.
This can usually be accomplished by removing Sponge and testing again,
or by trying out the suspect plugin on its' own in a test-server
environment. For more help in finding the source of a server problem,
visit [[Trouble-Shooting\|Troubleshooting]]
