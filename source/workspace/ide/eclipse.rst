==================
Setting Up Eclipse
==================

Download
========

.. note::

    If you choose to use **Eclipse** when developing plugins with the Sponge API, we recommend using **Eclipse Luna** or later.

**Eclipse** can be downloaded for free from http://www.eclipse.org/downloads/.

The download will come in the form of a compressed file, such as a ``.zip`` or ``.tar.gz``. Decompress this file into the directory of your choice; your **Program Files** (on Windows) or **Applications** (on Mac OS X) folder is recommended.

You may then proceed to run **Eclipse** from the directory it was downloaded into. **Eclipse** does not have an installer.

Starting Your Project
=====================

.. note::

    You may skip this section if you only plan on developing plugins with the Sponge API.

We recommend using Gradle or Maven when developing plugins with the Sponge API. Gradle and Maven help significantly when managing dependencies, such as the Sponge API, for your project. This is preferable to including the ``.jar`` file in your project.

Gradle
~~~~~~

At the time of writing, the **Gradle Integration for Eclipse** plugin is unavailable. Use Maven instead.

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

    Eclipse often does not open new projects after creating them. If this happens, try closing the Eclipse welcome screen; your project should be open behind it.

**To import the Sponge API as a dependency:**

* Open ``pom.xml`` in the navigator.
* Click the ``Dependencies`` tab on the bottom, and click ``Add``.
* Enter the **Group ID**, **Artifact ID**, and **Version** of the :doc:`official Sponge API Maven artifact <../../workspace/artifact>`.
* Click ``OK``.

Git Integration
===============

The Eclipse Foundation offers documentation on using Eclipse's EGit plugin:

http://www.eclipse.org/egit/documentation/

Track Continuation
==================

If you are following a track in **Preparing for Development**, your next step is:

* **Sponge (coremod):** :doc:`../../devs/forge/contributing`
* **Sponge API:** This is the end of the Sponge API track.
* **SpongeDocs:** This document is not included in the SpongeDocs track.
* **Plugin Development:** :doc:`../../plugins/quick-start`
