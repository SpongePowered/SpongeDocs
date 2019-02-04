=============================
Older Versions of SpongeForge
=============================

.. note::

    Sponge changed our versioning policy in October 2018. **Prior to** SpongeAPI 8.0, the following information 
    applies. See :doc:`filenames` for information on newer versions.

When you download SpongeForge, the name of the file will provide some important version information. It includes a
Forge build number which this version of SpongeForge is compatible with. Other builds, even ones differing by only a
few build numbers are not officially supported.

However, SpongeForge usually updates to a new Forge build fairly soon after it's released, so you needn't
worry about always having to run an outdated Forge version in order to use SpongeForge.


The format of the filename is ``spongeforge-<MCVersion>-<ForgeBuildId>-<SpongeAPIVersion>-BETA-<SpongeBuildId>.jar``

+----------------------+-----------------------------------------------------------------------------------------------+
| ``MCVersion``        | The Minecraft version. Only clients compatible with this version can connect.                 |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``ForgeBuildId``     | Preferably your server should run this exact version of Forge (which can be identified in the |
|                      | last part of Forge's version string).                                                         |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``SpongeAPIVersion`` | The version of SpongeAPI implemented by this file. This is what Sponge plugins depend on.     |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``SpongeBuildId``    | The build number of Sponge. This is what you should supply when reporting bugs or seeking     |
|                      | support.                                                                                      |
+----------------------+-----------------------------------------------------------------------------------------------+

Example
~~~~~~~

SpongeForge Jar files will always follow this naming scheme, to allow you to easily identify compatibility.

For example, the file name ``spongeforge-1.12.2-2705-7.1.0-BETA-3442.jar`` is compatible with Minecraft version
``1.12.2``, was built with Forge ``14.23.4.2705`` (Build ``2705``), provides SpongeAPI ``7.1.0`` and was build number
``3442`` of SpongeForge.
