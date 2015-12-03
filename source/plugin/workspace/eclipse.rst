==================
Setting Up Eclipse
==================

This article describes how to configure your **Eclipse** workspace for plugin development with the Sponge API.

Starting Your Project
=====================

We recommend using Gradle or Maven when developing plugins with the Sponge API. Gradle and Maven help significantly
when managing dependencies, such as the Sponge API, for your project. This is preferable to including the ``.jar``
file in your project.

Gradle
~~~~~~

**To create your project:**

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

**To import the Sponge API as a dependency:**

* Open ``build.gradle`` in the navigator.
* Manually add the :doc:`official Sponge API Maven artifact <dependencies>` as a dependency. An example can be found
  on the aforementioned Artifacts article.
* Right-click your project, and select ``Gradle > Refresh Dependencies``.

Maven
~~~~~

**To create your project:**

* Open **Eclipse**.
* Click ``File > New > Other``.
* Select ``Maven`` on the left side of the popup, and select ``Maven Project`` on the right side.
* Click ``Next``.
* Select ``Create a simple project``, unless you require a more advanced setup.
* Enter your **Group ID**, **Artifact ID**, **Version**, project name, and project description.

  * Your **Group ID** should correspond to your organization name, or something similar.
  * Your **Artifact ID** should correspond to your project name.
  * Your **Version** is up to you.
* Click ``Finish``.

.. tip::

    Eclipse often does not open new projects after creating them. If this happens, try closing the Eclipse welcome screen;
    your project should be open behind it.

**To import the Sponge API as a dependency:**

* Open ``pom.xml`` in the navigator.
* Click the ``Dependencies`` tab on the bottom, and click ``Add``.
* Enter the **Group ID**, **Artifact ID**, and **Version** of the :doc:`official Sponge API Maven artifact <dependencies>`.
* Click ``OK``.

Git Integration
===============

The Eclipse Foundation offers documentation on using Eclipse's EGit plugin:

http://www.eclipse.org/egit/documentation/
