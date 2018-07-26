========================
Setting Up IntelliJ IDEA
========================

This article describes how to configure your **IntelliJ IDEA** workspace for plugin development with SpongeAPI and
:doc:`a build system such as Maven or Gradle <../buildsystem/>` or the `Minecraft Development plugin <https://minecraftdev.org/>`_.

If you want to create your project completely from scratch, please skip ahead to the Gradle or Maven sections.  Using the Minecraft Dev plugin sets up a working starting point and eliminates some of the guesswork in getting your project off the ground.

Using IDEA Minecraft Dev Plugin to Create a Working Starting Point
=======================================

The `Minecraft Development plugin <https://minecraftdev.org/>`_ for IntelliJ is a great plugin by a community member which makes plugin project creation much easier while also providing some neat and useful features for development.  By default it will create a project which uses Gradle as the build tool.

Installing the Minecraft Dev Plugin
~~~~~~~~~~~~~~~~~~~~~

This plugin is available on the JetBrains IntelliJ plugin repository.

Because of this, you can install the plugin through IntelliJ's internal plugin browser. Navigate to ``File -> Settings -> Plugins`` and click the ``Browse Repositories...`` button at the bottom of the window. In the search box, simply search for ``Minecraft``. You can install it from there and restart IntelliJ to activate the plugin.

Creating Your Project from a Template
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
* The plugin will create a main java file as a starting point, with the logger already injected.  Try adding a ``logger.info`` statement in the ``onServerStart`` function to verify that the plugin is working when you run it.

Editing the Project Configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Refer to the Gradle_ or Maven_ configuration sections, depending on what you chose during project creation.



Creating a Plugin from Scratch -- Gradle
======

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
* The project will be created without a ``src`` directory.  If you add java files to the incorrect location underneath the project, they will be ignored and not compiled, so it is a good idea to enable the checkbox "Create directories for empty content roots", found in:

  * **Windows**: ``File`` -> ``Settings`` -> ``Build, Execution, Deployment`` -> ``Gradle``
  * **Mac**: ``Intellij IDEA`` -> ``Preferences`` -> ``Build, Execution, Deployment`` -> ``Build Tools`` -> ``Gradle``
* Enabling ``Use Auto-import`` in the same location will allow change to the gradle configuration to automatically reload without IDEA prompting you each time.  
* Upon enabling those settings, a ``/src/main/java`` directory should be created, where you can start creating your main plugin code files.  


.. _Gradle:

Editing the Build Script
~~~~~~~~~~~~~~~~~~~~~~~~

* Open ``build.gradle`` in the navigator and add the dependencies.
* Edit the build script according to the instructions at :doc:`../project/gradle`.
* Open the **Gradle tab** on the right of the IntelliJ window and hit the refresh button.
* Gradle setup is done! Now you can start coding your plugin.



Creating a Plugin from Scratch -- Maven
=====

Creating Your Project
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


Testing your plugin
====================

The following instructions are a quick way to test your plugin, but won't be the most efficient way to iteratively develop.

* Go to ``View`` -> ``Tool Windows`` -> ``Gradle``
* Under ``Tasks`` -> ``Build``, click on ``jar``
* The build process should create the jar underneath ``build\libs``
* Copy your jar file to the ``mods`` directory of a working Sponge server.

For a more efficient development process, see :doc:`../debugging` for instructions on running both the Sponge server and your plugin from within IDEA.  This process allows for hot-swapping, allowing you to change plugin code without restarting the server.


.. _Maven:

Editing the Project Configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

* Open ``pom.xml`` in the navigator.
* Edit the build configuration according to the instructions at :doc:`../project/maven`.
* Refresh your Maven project.
* Import the Maven changes, if prompted.

Importing An Existing Project (Gradle or Maven)
===============================================

If you've already started with your project and want to import it again at a later point you need to import it instead
of re-creating it inside your IDE:

* Click ``File > Open`` or ``Import Project``.
* **Gradle**: Navigate to the project's ``build.gradle`` file and select it.
* **Maven**: Navigate to the project's ``pom.xml`` file and select it.
* Make sure the settings are as you desire and click ``Ok``.


Git Integration
===============

JetBrains offers in-depth documentation on using their Git integration:

https://www.jetbrains.com/help/idea/using-git-integration.html
