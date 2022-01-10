=======================
Setting Up Your Project
=======================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

Before you can start developing your Sponge plugin, you need to set up the SpongeAPI dependency in your project. If
you're already experienced with your build system, below is the Maven dependency you need to add. Otherwise, there is a
more detailed explanation at the :doc:`Gradle <gradle>` and :doc:`Maven <maven>` page.

SpongeAPI dependency
====================

Maven repository
~~~~~~~~~~~~~~~~

==== =======================================
Name ``sponge``
URL  ``https://repo.spongepowered.org/repository/maven-public/``
==== =======================================

Maven dependency
~~~~~~~~~~~~~~~~

============= ============================================================================
Group ID      ``org.spongepowered``
Artifact ID   ``spongeapi``
Version       For example: ``8.0.0`` (stable), or ``8.1.0-SNAPSHOT`` (dev build, unstable)
============= ============================================================================

Setting Up Your Project
=======================

.. toctree::
    :maxdepth: 2
    :titlesonly:

    gradle
    maven
    version-numbers
