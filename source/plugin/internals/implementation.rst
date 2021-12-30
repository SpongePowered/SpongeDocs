=======================
Internal Sponge Classes
=======================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs> __`

You can add SpongeCommon, SpongeVanilla or SpongeForge as a dependency to your plugin project if you need to access
internal Sponge classes.

.. warning::
    You should only add a specific implementation dependency when really necessary. If possible, use SpongeAPI or
    request feature additions on the `SpongeAPI issue tracker <https://github.com/SpongePowered/SpongeAPI/issues>`_.

In addition to the normal artifacts, the implementation modules provide a ``dev`` artifact which can be easily used in
the IDE, since it is not re-obfuscated. All implementation modules have the API module already included, so you do not
need an extra dependency on SpongeAPI.

SpongeCommon
------------

- **Group ID**: ``org.spongepowered``
- **Artifact ID**: ``spongecommon``
- **Version**: Same as SpongeAPI + current recommended version (see :doc:`/versions/versioning` for details)
- **Classifier**: ``dev``

Example Using Gradle
````````````````````

.. code-block:: groovy

    dependencies {
        compile 'org.spongepowered:spongecommon:7.1.0:dev'
    }

SpongeVanilla
-------------

Choose a build from the downloads page and copy the full version string to your dependency definition.

- **Group ID**: ``org.spongepowered``
- **Artifact ID**: ``spongevanilla``
- **Version**: Use a build version from the downloads page
- **Classifier**: ``dev``

Example Using Gradle
````````````````````

.. code-block:: groovy

    dependencies {
        compile 'org.spongepowered:spongevanilla:1.12.2-7.1.1-RC123:dev'
    }

SpongeForge
-----------

Choose a build from the downloads page and copy the full version string to your dependency definition.

- **Group ID**: ``org.spongepowered``
- **Artifact ID**: ``spongeforge``
- **Version**: Use a build version from the downloads page
- **Classifier**: ``dev``

Example Using Gradle
````````````````````

.. code-block:: groovy

    dependencies {
        compile 'org.spongepowered:spongeforge:1.12.2-2768-7.1.1-RC3484:dev'
    }
