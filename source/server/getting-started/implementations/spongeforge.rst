===========
SpongeForge
===========

SpongeForge integrates `Minecraft Forge <http://www.minecraftforge.net/>`__ so you can also run Minecraft Forge mods.
In fact, it's more like Sponge itself is a Forge mod that then loads Sponge plugins, but this is a technical detail.

Users who do not want to use Minecraft Forge can consider :doc:`spongevanilla`.

Download
========

Check :doc:`../../../downloads` for further information.

Reading the Download Filename
=============================

When you download SpongeForge, the name of the file will provide some important version information. It includes a
Forge build number which this version of SpongeForge is compatible with. Other builds, even ones differing by only a
few build numbers are not supported.

However, SpongeForge usually updates to a new Forge build fairly soon after it's released, so you needn't
worry about always having to run an outdated Forge version in order to use SpongeForge.


The format of the filename is ``spongeforge-{MCVersion}-{ForgeVersion}-{SpongeAPIVersion}-{SpongeBuildId}``

+----------------------+----------------------------------------------------------------------------------------------+
| ``MCVersion``        | The minecraft version. Only clients compatible with this version can connect.                |
+----------------------+----------------------------------------------------------------------------------------------+
| ``ForgeVersion``     | The version of Forge this file is built for. Preferably your server should run this exact    |
|                      | version of Forge.                                                                            |
+----------------------+----------------------------------------------------------------------------------------------+
| ``SpongeAPIVersion`` | The version of the SpongeAPI implemented by this file. This is what Sponge plugins depend on.|
+----------------------+----------------------------------------------------------------------------------------------+
| ``SpongeBuildId``    | The build number of Sponge. This is what you should supply when reporting bugs or seeking    |
|                      | support.                                                                                     |
+----------------------+----------------------------------------------------------------------------------------------+

Example
~~~~~~~

The file name ``spongeforge-1.8-1577-3.0.0-BETA-1000.jar`` is compatible with minecraft version ``1.8``, requires build
``1.8-11.14.4.1577`` of Forge, provides SpongeAPI ``3.0.0`` and was build ``1000`` of SpongeForge.

.. note::

    Normal Forge mods can usually run on any build of Forge for a given minecraft version (e.g. 1.8.0) without
    any problems. However, SpongeForge needs to access, among other things, internal parts of Forge, which
    most mods shouldn't be touching, let alone modifying as Sponge does. Since Forge is free to change internal
    code whenever they want to, its normal guarantee of backwards-compatibility doesn't apply to SpongeForge.

Links
=====

* `Homepage <http://spongepowered.org/>`__
* `GitHub <https://github.com/SpongePowered/SpongeForge>`__
