============
API-Versions
============

This page explains which API versions exists and to which Minecraft version their implementations belong to.

+-------------+--------------+----------------+-------------------------------------------+
| API-Version | Release Date | End of Support | Known Implementations (Minecraft Version) |
+=============+==============+================+===========================================+
| *Planned*   | ---          | --             | * *SpongeForge (1.13.x)*                  |
| *8.0.0*     |              |                | * *SpongeVanilla (1.13.x)*                |
+-------------+--------------+----------------+-------------------------------------------+
| 7.1.0       | 2018-09-06   | Late 2019      | * SpongeForge (1.12.2)                    |
|             |              |                | * SpongeVanilla (1.12.2)                  |
+-------------+--------------+----------------+-------------------------------------------+
| 7.0.0       | 2018-01-01   | ^              | * SpongeForge (1.12.2)                    |
|             |              |                | * SpongeVanilla (1.12.2)                  |
+-------------+--------------+----------------+-------------------------------------------+
| 6.0.0       | 2017-05-02   | 2017-12-31     | * SpongeForge (1.11.2)                    |
|             |              |                | * SpongeVanilla (1.11.2)                  |
+-------------+--------------+----------------+-------------------------------------------+
| 5.1.0       | 2017-02-25   | 2017-12-31     | * SpongeForge (1.10.2)                    |
|             |              |                | * SpongeVanilla (1.10.2)                  |
+-------------+--------------+----------------+-------------------------------------------+

It is recommended to always update to the latest stable version of the API or to an LTS version.

.. note::

    You can still release your plugin with an older major API version, but we don't officially support these versions.


LTS-Versions
============

The following versions are LTS (long time support) versions.

* SpongeAPI 7.x until late 2019


Getting the API Version from Implementations
============================================

Getting it from the Jar
~~~~~~~~~~~~~~~~~~~~~~~

* For `SpongeForge <https://www.spongepowered.org/downloads/spongeforge/>`__: 

  **Example:** ``spongeforge-1.12.2-2705-7.1.0-BETA-3361.jar``
  
  => API-Version ``7.1.0`` (might reference a preview though)
  
  See also :ref:`sponge-forge-file-name`.

* For `SpongeVanilla <https://www.spongepowered.org/downloads/spongevanilla/>`__:

  **Example:** ``spongevanilla-1.12.2-7.1.0-BETA-59.jar``
  
  => API-Version ``7.1.0`` (might reference a preview though)

.. _associated-minecraft-version:

Getting the Minecraft Version from Implementations
==================================================

Getting it from the Sources
~~~~~~~~~~~~~~~~~~~~~~~~~~~

* For `SpongeForge <https://github.com/SpongePowered/SpongeForge>`__ or
  `SpongeVanilla <https://github.com/SpongePowered/SpongeVanilla>`__:

  Go to the referenced SpongeCommon repository and check the ``gradle.properties`` in the project root. It contains an
  entry with the ``minecraftVersion``.
  
  .. note::
  
      The same works for the ``mcpMappings`` version.

* For `SpongeVanilla <https://github.com/SpongePowered/SpongeVanilla>`__ (only):

  Check the ``gradle.properties`` in the project root. It contains an entry with the ``minecraftVersion``.
