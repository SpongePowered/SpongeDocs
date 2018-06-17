========================
Setting Up IntelliJ IDEA
========================

This article describes how to configure your **IntelliJ IDEA** workspace for plugin development with SpongeAPI and
:doc:`a build system such as Maven or Gradle <../buildsystem/>` or the `Minecraft Development plugin <https://minecraftdev.org/>`_.

Gradle
======

Creating your project
~~~~~~~~~~~~~~~~~~~~~

* Open **IntelliJ IDEA**.
* Click ``Create New Project``.
* Select ``Gradle`` in the popup.
* If you want, select any additional libraries and frameworks you desire, for example Kotlin.
* Make sure your **Project SDK** is set to some version of Java 8/1.8.
* Click ``Next`` to move on.
* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should usually correspond to your Java package name. See :doc:`../plugin-class` for details.
  * Your **Artifact ID** should usually correspond to your **plugin ID** you chose earlier, e.g. ``myplugin``.
  * Your **Version** is up to you.

* Click ``Next`` twice, name your project, and click ``Finish``.

.. _Gradle:

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

* Click ``File > Open`` or ``Import Project``.
* Navigate to the project's ``build.gradle`` file and select it.
* Make sure the settings are as you desire and click ``Ok``.

Maven
=====

Creating your project
~~~~~~~~~~~~~~~~~~~~~

* Open **IntelliJ IDEA**.
* Click ``Create New Project``.
* Select ``Maven`` in the popup.
* Make sure your **Project SDK** is set to some version of Java 8/1.8.
* Click ``Next`` to move on.
* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should usually correspond to your Java package name. See :doc:`../plugin-class` for details.
  * Your **Artifact ID** should usually correspond to your **plugin ID** you chose earlier, e.g. ``myplugin``.
  * Your **Version** is up to you.

* Click ``Next``.
* Enter your project's name, and click ``Finish``.

.. _Maven:

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

* Click ``File > Open`` or ``Import Project``.
* Navigate to the project's ``pom.xml`` file and select it.
* Make sure the settings are as you desire and click ``Ok``.

Minecraft Dev Plugin
====================

The Minecraft Development plugin for IntelliJ is a great plugin by a community member which makes plugin project
creation much easier while also providing some neat and useful features for development.

Creating your project
~~~~~~~~~~~~~~~~~~~~~

* Open **IntelliJ IDEA**.
* Click ``Create New Project``.
* Select ``Minecraft`` in the popup.
* Make sure your **Project SDK** is set to some version of Java 8/1.8.
* Select ``Sponge plugin`` for your project type, then click ``Next``.
* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should usually correspond to your Java package name. See :doc:`../plugin-class` for details.
  * Your **Artifact ID** should usually correspond to your **plugin ID** you chose earlier, e.g. ``myplugin``.
  * Your **Version** is up to you.

* Select your desired build tool, either Gradle or Maven, and click ``Next``.
* Check your **Plugin Name** and **Main Class Name** to make sure they are what you want.
* Specify your desired plugin **description**, **authors**, **website**, and **dependencies** if you want.
* Click ``Next`` to move on.
* Verify your project name, location, and module information, then click ``Finish``.

Editing your project configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Refer to the Gradle_ or Maven_ configuration sections, depending on what you chose during project creation.

Git Integration
===============

JetBrains offers in-depth documentation on using their Git integration:

https://www.jetbrains.com/help/idea/using-git-integration.html
