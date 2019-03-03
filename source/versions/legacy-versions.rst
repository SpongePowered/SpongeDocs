=============================
Older Versions of SpongeForge
=============================

.. note::

    Our versioning policy was updated in October 2018. **Prior to** SpongeAPI 7.1, the following information applies. 
    See :doc:`filenames` for information on newer versions.

The format of the SpongeForge download filename is:
``spongeforge-<MCVersion>-<ForgeBuildId>-<SpongeAPIVersion>-BETA-<SpongeBuildId>.jar``

+----------------------+-----------------------------------------------------------------------------------------------+
| ``MCVersion``        | The Minecraft version. Only clients compatible with this version can connect.                 |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``ForgeBuild``       | The Forge build that SpongeForge is built against and is guaranteed to be compatible          |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``SpongeAPIVersion`` | The version of SpongeAPI implemented by this file. This is what Sponge plugins depend on.     |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``SpongeBuildId``    | The build number of Sponge. This is what you should supply when reporting bugs or seeking     |
|                      | support.                                                                                      |
+----------------------+-----------------------------------------------------------------------------------------------+

The Forge Build in the filename specifies the version of Forge this version of SpongeForge used during development and 
testing. The two versions are guaranteed to work without any issues. We **tend** to use the latest *Recommended Build* 
of Forge for this purpose.

.. note::

    Normal Forge mods can usually run on any build of Forge for a given Minecraft version (e.g. 1.12.2) without any 
    problem. However, SpongeForge needs to access, among other things, internal parts of Forge, which most mods
    shouldn’t be touching, let alone modifying as Sponge does. Since Forge is free to change their internal code
    whenever they want to, its normal guarantee of backwards-compatibility doesn’t apply to SpongeForge. Feel free to
    use more recent versions of Forge than the one used for SpongeForge, but we can't always guarantee compatibility.

Example
-------

SpongeForge Jar files will always follow this naming scheme, to allow you to easily identify compatibility.

For example, the file name ``spongeforge-1.12.2-2705-7.1.0-BETA-3442.jar`` is compatible with Minecraft version
``1.12.2``, was built with Forge ``14.23.4.2705`` (Build ``2705``), provides SpongeAPI ``7.1.0`` and was build number
``3442`` of SpongeForge.
