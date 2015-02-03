===================
Setting Up NetBeans
===================

Download
========

.. note::

    If you choose to use **NetBeans** when developing plugins with the Sponge API, we recommend using **NetBeans 8** or later. Note that NetBeans has not been thoroughly tested in Sponge development; thus, we cannot provide any significant support for users of NetBeans. We recommend that beginners use Eclipse or IntelliJ IDEA.

**NetBeans** can be downloaded from https://netbeans.org/downloads/index.html.

After downloading the appropriate version of **NetBeans** for your platform, you may proceed to install it.

Starting Your Project
=====================

.. note::

    You may skip this section if you only plan on developing plugins with the Sponge API.

We recommend using Gradle or Maven when developing plugins with the Sponge API. Gradle and Maven help significantly when managing dependencies, such as the Sponge API, for your project. This is preferable to including the ``.jar`` file in your project.

Gradle
~~~~~~

**To create your project:**

You must first install the **Gradle Support** plugin before using Gradle in NetBeans. This only needs to be done upon the creation of your first project.

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

  * Your **Group ID** should correspond to your organization name, or something similar.
  * Your **Artifact ID** will be automatically generated based on your project name.
  * Your **Version** is up to you.

* Click ``Finish``.

**To import the Sponge API as a dependency:**

* Open ``common.gradle`` under the ``Projects`` tab.
* Manually add the :doc:`official Sponge API Maven artifact <../../workspace/artifact>` as a dependency. An example can be found on the aforementioned Artifacts article.
* Right-click your primary project node under the **Projects** tab, and click ``Reload Project``.

Maven
~~~~~

**To create your project:**

* Open **NetBeans**.
* Click ``File > New Project``.
* Scroll to ``Maven`` in the popup window. Select ``Maven`` on the left and ``Java Application`` on the right. Click ``Next``.
* Enter your project's **name**.

* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should correspond to your organization name, or something similar.
  * Your **Artifact ID** should correspond to the name of your project.
  * Your **Version** is up to you.

* Enter a **package**, if you please. This is optional.
* Click ``Finish`` upon completion of the text fields.

**To import the Sponge API as a dependency:**

* Right-click on the ``Dependencies`` node under the **Projects** tab, which is on the left by default.
* Click ``Add Dependency``.
* Enter the **Group ID**, **Artifact ID**, and **Version** of the :doc:`official Sponge API Maven artifact <../../workspace/artifact>`.
* Click ``Add``.

Git Integration
===============

NetBeans offers documentation on Git integration:

https://netbeans.org/kb/docs/ide/git.html

Track Continuation
==================

If you are following a track in **Preparing for Development**, your next step is:

* **Sponge (coremod):** :doc:`../../devs/forge/contributing`
* **Sponge API:** :doc:`../../devs/api/contributing`
* **SpongeDocs:** This document is not included in the SpongeDocs track.
* **Plugin Development:** :doc:`../../plugins/quick-start`
