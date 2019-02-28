=============================
Reading the Download Filename
=============================

When you download files, the name of the file will provide some important version information. The following sections 
describe the information provided.

.. _sponge-forge-file-name:

SpongeForge
===========

.. note::

    Our versioning policy was updated in October 2018. **Beginning with** SpongeAPI 8.0, the following information 
    applies. See :doc:`legacy-versions` for information on older versions.

When you download SpongeForge, the name of the file includes a Forge build number which this version of SpongeForge is 
built with and guaranteed to be compatible. We do not support newer versions of Forge; however, we try to get an update 
out when a Forge release breaks SpongeForge. You can try the Forge version needed as it will work fine most of the 
time. If it doesn't, we hope to release a SpongeForge compatible with that Forge version soon. 

The format of the filename is:

``spongeforge-<MCVersion>-<ForgeBuild>-<APIMajor>.<LatestAPIMinorRelease>.<RecommendedVersion(-RC<BuildNumber>)>.jar``

+----------------------+-----------------------------------------------------------------------------------------------+
| ``MCVersion``        | The Minecraft version. Only clients compatible with this version can connect.                 |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``ForgeBuild``       | Preferably your server should run this exact version of Forge (which can be identified in the |
|                      | last part of Forge's version string).                                                         |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``APIMajor``         | The major version of SpongeAPI implemented by this file (the ``X`` in                         |
|                      | :ref:`semantic versioning<sem-ver>`).                                                         |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``LatestAPI``        | The minor version of SpongeAPI implemented by this file (the ``Y`` in                         |
| ``MinorRelease``     | :ref:`semantic versioning<sem-ver>`).                                                         |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``Recommended``      | The released version of the implementation when **not** followed by ``-RC<BuildNumber>.``     |
| ``Version``          |                                                                                               |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``-RC``              | The build number in development for the next recommended release. When a build number is      |
| ``<BuildNumber>``    | present, the ``RecommendedVersion`` has **not** been released yet.                            |
+----------------------+-----------------------------------------------------------------------------------------------+

Examples
~~~~~~~~

SpongeForge Jar files will always follow this naming scheme to allow you to easily identify compatibility.

For example, the file name ``spongeforge-1.12.2-2705-7.1.4.jar`` is compatible with Minecraft version ``1.12.2``, was 
built with Forge ``14.23.4.2705`` (Build ``2705``), guarantees compatibility with SpongeAPI ``7.1.0``, is a recommended 
version, and is the ``4`` th release of SpongeForge with this API.

Another example is the file name ``spongeforge-1.12.2-2705-7.1.5-RC3449.jar``. This file is compatible with Minecraft 
version ``1.12.2``, was built with Forge ``14.23.4.2705`` (Build ``2705``), guarantees compatibility with SpongeAPI 
``7.1.0``, is not a recommended version, and is build number ``3449`` in development, which will be the ``5`` th 
release of SpongeForge once this version is released. 

.. note::

    Normal Forge mods can usually run on any build of Forge for a given Minecraft version (e.g. 1.8.0) without any
    problem. However, SpongeForge needs to access, among other things, internal parts of Forge, which most mods
    shouldn’t be touching, let alone modifying as Sponge does. Since Forge is free to change their internal code
    whenever they want to, its normal guarantee of backwards-compatibility doesn’t apply to SpongeForge. Feel free to
    use more recent versions of Forge, than the one used for SpongeForge, but we can't always guarantee compatibility.

.. warning::

    When investigating crash issues, you can freely try newer versions of Forge than listed on the SpongeForge Jar.
    However, it is recommended to also check with the matching version, to make sure your issue is not related to a
    version mismatch.
    Even though there will be no guarantee of compatibility, please report any breakage to the issue tracker, so that
    SpongeForge can be updated.

SpongeVanilla
=============

The information for SpongeVanilla is identical to SpongeForge except that SpongeVanilla does not contain a Forge build 
number. So, the above examples of SpongeForge jar files will look like the following for SpongeVanilla:

``spongevanilla-1.12.2-7.1.4.jar`` and ``spongevanilla-1.12.2-7.1.5-RC3449.jar``

SpongeAPI
=========

The format of the filename is:

``spongeapi-<APIMajor>.<LatestAPIMinorRelease>.0-shaded.jar``

**OR**

``spongeapi-<APIMajor>.<LatestAPIMinorRelease>.0-<Date>.<Time>-<BuildNumber>-shaded.jar``

+----------------------+-----------------------------------------------------------------------------------------------+
| ``APIMajor``         | The major version of SpongeAPI implemented by this file (the ``X`` in                         |
|                      | :ref:`semantic versioning<sem-ver>`).                                                         |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``LatestAPI``        | The minor version of SpongeAPI implemented by this file (the ``Y`` in                         |
| ``MinorRelease``     | :ref:`semantic versioning<sem-ver>`).                                                         |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``Date``             | The date when the build job ran.                                                              |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``Time``             | The time when the build job ran.                                                              |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``<BuildNumber>``    | The build number of Sponge. Supply this number when reporting bugs or seeking support.        |
+----------------------+-----------------------------------------------------------------------------------------------+

.. note::

    The first format without the build information is the release and recommended build format. The second format with 
    build information is the *SNAPSHOT* format. This version is still in development.
    
    See our `build system 
    <https://docs.spongepowered.org/stable/en/plugin/buildsystem.html#creating-a-plugin-without-a-build-system>`_ page 
    for an explanation of the ``-shaded`` label.

