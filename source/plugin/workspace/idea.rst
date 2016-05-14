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
* Click ``Create New Project``.
* Select ``Gradle`` in the popup, and click ``Next``.
* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should usually correspond to your Java package name. See :doc:`../plugin-class` for details.
  * Your **Artifact ID** should usually correspond to your **plugin ID** you chose earlier, e.g. ``myplugin``.
  * Your **Version** is up to you.

* Click ``Next`` twice, name your project, and click ``Finish``.

Editing the build script
~~~~~~~~~~~~~~~~~~~~~~~~

* Open ``build.gradle`` in the navigator and add the dependencies.
* Edit the build script according to the instructions at :doc:`../project/gradle`.
* Open the **Gradle tab** on the right of the IntelliJ window and hit the refresh button.
* Gradle setup is done! Now you can start coding your plugin.

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
* Click ``Create New Project``.
* Select ``Maven`` in the popup, and click ``Next``.
* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should usually correspond to your Java package name. See :doc:`../plugin-class` for details.
  * Your **Artifact ID** should usually correspond to your **plugin ID** you chose earlier, e.g. ``myplugin``.
  * Your **Version** is up to you.

* Click ``Next``.
* Enter your project's name, and click ``Finish``.

Editing the project configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

* Open ``pom.xml`` in the navigator.
* Edit the build configuration according to the instructions at :doc:`../project/maven`.
* Refresh your Maven project.
* Import the Maven changes, if prompted.

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
