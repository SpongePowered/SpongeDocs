==================
Setting Up Eclipse
==================

This article describes how to configure your **Eclipse** workspace for plugin development with SpongeAPI and :doc:`a
build system such as Maven or Gradle <../buildsystem/>`.

.. note::

    A tutorial by Mumfrey showing the setup of a Sponge workspace in Eclipse, using the new features in Buildship and ForgeGradle,
    can be viewed `here <https://www.youtube.com/watch?v=R8NcakQtHVI>`_.
    
Gradle
======

Creating your project
~~~~~~~~~~~~~~~~~~~~~

You must first install the **Gradle Integration Plugin** before using Gradle in Eclipse. This only needs to be done
upon the creation of your first project.

.. note::

    Typically, you do not need the optional Spring modules distributed with this plugin, so you can uncheck them during
    installation.

* Open **Eclipse**.
* Click ``Help > Eclipse Marketplace``.
* Search for ``Gradle Integration Plugin``.
* Install the **Gradle Integration Plugin**.

You may then proceed to create your project.

* Click ``File > New > Other``.
* Select ``Gradle > Gradle Project``.
* Click ``Next``.
* Enter a project name, then click ``Finish``.

Editing the build script
~~~~~~~~~~~~~~~~~~~~~~~~

* Open ``build.gradle`` in the navigator.
* Edit the build script according to the instructions at :doc:`../project/gradle`.
* Right-click your project, and select ``Gradle > Refresh Dependencies``.

Importing your project
~~~~~~~~~~~~~~~~~~~~~~

If you've already started with your project and want to import it again at a later point you need to import it instead
of re-creating it inside your IDE:

* Click ``File > Import``
* Select ``Gradle > Gradle Project``
* Navigate to the root folder of the project
* Click Finish

Maven
=====

Creating your project
~~~~~~~~~~~~~~~~~~~~~

* Open **Eclipse**.
* Click ``File > New > Other``.
* Select ``Maven`` on the left side of the popup, and select ``Maven Project`` on the right side.
* Click ``Next``.
* Select ``Create a simple project``, unless you require a more advanced setup.
* Enter your **Group ID**, **Artifact ID**, **Version**, project name, and project description.

  * Your **Group ID** should usually correspond to your Java package name. See :doc:`../plugin-class` for details.
  * Your **Artifact ID** should usually correspond to your **plugin ID** you chose earlier, e.g. ``myplugin``.
  * Your **Version** is up to you.

* Click ``Finish``.

.. tip::

    Eclipse often does not open new projects after creating them. If this happens, try closing the Eclipse welcome
    screen; your project should be open behind it.

Editing the project configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

* Open ``pom.xml`` in the navigator.
* Edit the build configuration according to the instructions at :doc:`../project/maven`.
* Refresh your Maven project.

Importing your project
~~~~~~~~~~~~~~~~~~~~~~

If you've already started with your project and want to import it again at a later point you need to import it instead
of re-creating it inside your IDE:

* Click ``File > Import``
* Select ``Maven > Existing Maven Projects``
* Navigate to the root folder of the project
* Click Finish

Git Integration
===============

The Eclipse Foundation offers documentation on using Eclipse's EGit plugin:

http://www.eclipse.org/egit/documentation/
