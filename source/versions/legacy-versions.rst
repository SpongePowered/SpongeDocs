=============================
Older Versions of SpongeForge
=============================

.. note::

    Our versioning policy was updated in October 2018. **Prior to** SpongeAPI 8.0, the following information applies. 
    See :doc:`filenames` for information on newer versions.

When you download SpongeForge, the name of the file includes a Forge build number which this version of SpongeForge is 
built with and guaranteed to be compatible. We do not support newer versions of Forge; however, we try to get an update 
out when a Forge release breaks SpongeForge. You can try the Forge version needed as it will work fine most of the 
time. If it doesn't, we hope to release a SpongeForge compatible with that Forge version soon. 


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
