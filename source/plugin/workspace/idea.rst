========================
Setting Up IntelliJ IDEA
========================

This article describes how to configure your **IntelliJ IDEA** workspace for plugin development with SpongeAPI and
:doc:`a build system such as Maven or Gradle <../buildsystem/>`.

Gradle
======

Creating your project
~~~~~~~~~~~~~~~~~~~~~

* Open **IntelliJ IDEA**.
* Click ``Configure``, then click ``plugins``.
* Click ``Browse Repositories``
* Search for ``Minecraft Development``, and install the first result
* Restart **IntelliJ IDEA**
* Click ``Create New Project``.
* Select ``Minecraft Plugin`` in the popup, select ``Sponge Plugin``, and click ``Next``.
* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should usually correspond to your Java package name. See :doc:`../plugin-class` for details.
  * Your **Artifact ID** should usually correspond to your **plugin ID** you chose earlier, e.g. ``myplugin``.
  * Your **Version** is up to you.

* Click ``Next`` twice, name your project, and click ``Finish``.

Importing your project
~~~~~~~~~~~~~~~~~~~~~~

If you've already started with your project and want to import it again at a later point you need to import it instead
of re-creating it inside your IDE:

* Click ``File > Open``
* Navigate to the project's ``build.gradle`` file
* Click Ok

Maven
=====

Creating your project
~~~~~~~~~~~~~~~~~~~~~

* Open **IntelliJ IDEA**.
* Click ``Configure``, then click ``plugins``.
* Click ``Browse Repositories``
* Search for ``Minecraft Development``, and install the first result
* Restart **IntelliJ IDEA**
* Click ``Create New Project``.
* Select ``Minecraft Plugin`` in the popup, select ``Sponge Plugin``, and click ``Next``.
* Enter your **Group ID**, **Artifact ID**, and **Version**, click on the popup menu in the corner, and change the selected option from **Gradle** to **Maven**.

  * Your **Group ID** should usually correspond to your Java package name. See :doc:`../plugin-class` for details.
  * Your **Artifact ID** should usually correspond to your **plugin ID** you chose earlier, e.g. ``myplugin``.
  * Your **Version** is up to you.

* Click ``Next`` twice.
* Enter your project's name, and click ``Finish``.

Importing your project
~~~~~~~~~~~~~~~~~~~~~~

If you've already started with your project and want to import it again at a later point you need to import it instead
of re-creating it inside your IDE:

* Click ``File > Open``
* Navigate to the project's ``pom.xml`` file
* Click Ok

Git Integration
===============

JetBrains offers documentation on Git integration:

https://www.jetbrains.com/idea/help/using-git-integration.html
