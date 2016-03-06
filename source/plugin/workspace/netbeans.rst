===================
Setting Up NetBeans
===================

This article describes how to configure your **NetBeans** workspace for plugin development with SpongeAPI and :doc:`a
build system such as Maven or Gradle <../buildsystem/>`.

Gradle
======

Creating your project
~~~~~~~~~~~~~~~~~~~~~

You must first install the **Gradle Support** plugin before using Gradle in NetBeans. This only needs to be done upon
the creation of your first project.

* Open **NetBeans**.
* Click ``Tools > Plugins``.
* Select ``Available Plugins`` in the popup, and search for ``Gradle Support``.
* Install the **Gradle Support** plugin.

You may then proceed to create your project.

* Click ``File > New Project``.
* Select ``Gradle`` on the left side of the popup, and select ``Gradle Root Project`` on the right side.
* Click ``Next``.
* Name your project.
* Enter your **Group ID** and **Version**.

  * Your **Group ID** should usually correspond to your the **plugin group ID** you chose earlier, e.g.
    ``io.github.username``.
  * Your **Artifact ID** should usually correspond to your **unqualified plugin ID** you chose earlier, e.g.
    ``myplugin``.
  * Your **Version** is up to you.

* Click ``Finish``.

Editing the build script
~~~~~~~~~~~~~~~~~~~~~~~~

.. TODO: Wtf is this ``common.gradle`` stuff? Is Netbeans literally messing with the standard Gradle project layout?

* Open ``common.gradle`` under the ``Projects`` tab.
* Edit the build script according to the instructions at TODO.
* Right-click your primary project node under the **Projects** tab, and click ``Reload Project``.

Importing your project
~~~~~~~~~~~~~~~~~~~~~~

If you've already started with your project and want to import it again at a later point you need to import it instead
of re-creating it inside your IDE:

.. TODO

Maven
=====

Creating your project
~~~~~~~~~~~~~~~~~~~~~

* Open **NetBeans**.
* Click ``File > New Project``.
* Scroll to ``Maven`` in the popup window. Select ``Maven`` on the left and ``Java Application`` on the right. Click
  ``Next``.
* Enter your project's **name**.

* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should usually correspond to your the **plugin group ID** you chose earlier, e.g.
    ``io.github.username``.
  * Your **Artifact ID** should usually correspond to your **unqualified plugin ID** you chose earlier, e.g.
    ``myplugin``.
  * Your **Version** is up to you.

* Enter a **package**, if you please. This is optional.
* Click ``Finish`` upon completion of the text fields.

Editing the project configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. TODO: How does this work? That wouldn't add Sponge's Maven repository

* Right-click on the ``Dependencies`` node under the **Projects** tab, which is on the left by default.
* Click ``Add Dependency``.
* Enter the **Group ID**, **Artifact ID**, and **Version** of the Sponge API Maven artifact. See TODO for details.
* Click ``Add``.

Importing your project
~~~~~~~~~~~~~~~~~~~~~~

If you've already started with your project and want to import it again at a later point you need to import it instead
of re-creating it inside your IDE:

.. TODO

Git Integration
===============

NetBeans offers documentation on Git integration:

https://netbeans.org/kb/docs/ide/git.html
