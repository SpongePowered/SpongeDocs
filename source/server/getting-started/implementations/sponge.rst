==============
Sponge Coremod
==============

Sponge Coremod integrates `Minecraft Forge <http://www.minecraftforge.net/>`__ so you can also run Minecraft Forge mods. In actually, it's more like Sponge itself is a Forge mod that then loads Sponge plugins, but this is a technical detail.

Users who do not want to use Minecraft Forge can consider :doc:`spongevanilla`.

Download
========

Check :doc:`../../../downloads` for further information.

Reading the Download Filename
=============================

When you download Sponge Coremod, the name of the file will provide some important version information.

The format of the filename is ``{MCVersion}-{ForgeVersion}-{SpongeAPIVersion}{SpongeBuildId}``	
	
Note that there is no separator between the Sponge API Version and the Sponge Build ID.
	
+----------------------+-------------------------------------------------------------------------------------------------------------+
| ``MCVersion``        | The minecraft version. Only clients compatible with this version can connect.                               |
+----------------------+-------------------------------------------------------------------------------------------------------------+
| ``ForgeVersion``     | The version of Forge this file is built for. Preferably your server should run this exact version of Forge. |
+----------------------+-------------------------------------------------------------------------------------------------------------+
| ``SpongeAPIVersion`` | The version of the SpongeAPI implemented by this file. This is what Sponge plugins depend on.               |
+----------------------+-------------------------------------------------------------------------------------------------------------+
| ``SpongeBuildId``    | The build number of Sponge. This is what you should supply when reporting bugs or seeking support.          |
+----------------------+-------------------------------------------------------------------------------------------------------------+

Example
~~~~~~~
		
For example the file name ``1.8-1371-2.1DEV-439`` tells us that this Sponge coremod is compatible to Minecraft ``1.8``, best used with Forge version ``1371``, provides the API Version ``2.1`` and has the build id ``Dev-439``

Links
=====

* `Homepage <http://spongepowered.org/>`__
* `GitHub <https://github.com/SpongePowered/Sponge>`__
