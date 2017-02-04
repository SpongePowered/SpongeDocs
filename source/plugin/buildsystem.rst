=============
Build Systems
=============

Build systems such as Gradle_ or Maven_ can manage the build process of your projects. As an independent tool from your
IDE, you can use them to manage your dependency on SpongeAPI or other plugins and give other people an easy way to
build your plugin from the source.

.. note::
    SpongeAPI does **not** require using a build system for creating plugins, however we strongly recommend using one.
    Except the short explanation at `Creating a plugin without a build system`_, the following parts will assume you're
    using a build system, which can manage the dependencies for you.

Generally, you can use any build system which supports *Maven dependencies*, which is a standard supported by the
majority of build systems for Java projects. The following sections will focus on Gradle_ and Maven_, which are the two
most common choices as build systems. If you're unsure which one to use we recommend using Gradle_ as it is also used
for the Sponge projects and provides the best integration for Sponge plugins.

.. _gradle-setup:

Gradle
======
Gradle_ uses Groovy_-based scripts for configuring projects. A Gradle_ project typically consists of a ``build.gradle``
file in your project's root directory, which tells Gradle_ how to build the project.

.. tip::
    Refer to the `Gradle User Guide`_ for the installation and a general introduction of concepts used in Gradle_. If
    you're only interested in how to use Gradle_ for a simple Java project, a good place to start would be the `Gradle
    Java Quickstart`_.

Setup your workspace as explained in :doc:`workspace/index` then follow :doc:`project/gradle`.

Maven
=====
Maven_ uses a XML-based configuration called `Project Object Model`_ (or *POM*) for configuring projects.
A Maven_ project typically contains a ``pom.xml`` file in the project root directory which tells Maven_ how to
build the project.

.. tip::
    Refer to the `Maven Users Centre`_ for the installation and a general introduction of concepts used in Maven_. If
    you're only interested in how to use Maven_ for a simple Java project, a good place to start would be `Maven in 5
    Minutes`_.

Setup your workspace as explained in :doc:`workspace/index` then follow :doc:`project/maven`.

Creating a plugin without a build system
========================================

It is also possible to create Sponge plugins without the use of a build system, only with the included tools in your
IDE.

.. warning::
    **We strongly suggest against using SpongeAPI without a build system.** In the long term using a build system will
    simplify the development process for you and other people wanting to contribute to your project. This method of
    developing plugins does not receive active testing by the Sponge team.

For developing plugins without a build system you need to download the SpongeAPI dependency manually from the
`SpongeAPI Download Page`_. For developing without a build system, we provide the ``shaded`` artifact which bundles all
dependencies that would normally be automatically downloaded by the build system.

After you have downloaded the ``shaded`` artifact and have added it to a project in your IDE, you can start developing
your plugin. Continue at :doc:`plugin-identifier` for choosing an identifier for your project, then continue at :doc:`plugin-class`.

.. _Gradle: https://gradle.org/
.. _Maven: https://maven.apache.org/
.. _Groovy: http://www.groovy-lang.org/
.. _`Gradle User Guide`: https://docs.gradle.org/current/userguide/userguide.html
.. _`Gradle Java Quickstart`: https://docs.gradle.org/current/userguide/tutorial_java_projects.html
.. _`Project Object Model`: https://maven.apache.org/guides/introduction/introduction-to-the-pom.html
.. _`Maven Users Centre`: https://maven.apache.org/users/index.html
.. _`Maven in 5 Minutes`: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
.. _`Maven Getting Started Guide`: https://maven.apache.org/guides/getting-started/index.html
.. _`SpongeAPI Download Page`: https://repo.spongepowered.org/maven/org/spongepowered/spongeapi/
