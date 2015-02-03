========================
Setting Up IntelliJ IDEA
========================

Download
========

.. note::

    If you choose to use **IntelliJ IDEA** when developing plugins with the Sponge API, we recommend using **Community Edition 14** or later.

**IntelliJ IDEA** can be downloaded from https://www.jetbrains.com/idea/download/.

After downloading the appropriate version of **IntelliJ IDEA** for your platform, you may proceed to install it.

Starting Your Project
=====================

.. note::

    You may skip this section if you only plan on developing plugins with the Sponge API.

We recommend using Gradle or Maven when developing plugins with the Sponge API. Gradle and Maven help significantly when managing dependencies, such as the Sponge API, for your project. This is preferable to including the ``.jar`` file in your project.

Gradle
~~~~~~

**To create your project:**

* Open **IntelliJ IDEA**.
* Click ``Create New Project``.
* Select ``Gradle`` in the popup, and click ``Next``.
* Name your project, and click ``Finish``.

**To import the Sponge API as a dependency:**

* Open ``build.gradle`` in the navigator.
* With the editor tab in focus, choose ``Code > Generate``.
* Click ``Add Maven artifact dependency`` in the popup.
* Search for the :doc:`official Sponge API Maven artifact <../../workspace/artifact>` under **Search by artifact**, and click ``Add``.

Maven
~~~~~

**To create your project:**

* Open **IntelliJ IDEA**.
* Click ``Create New Project``.
* Select ``Maven`` in the popup, and click ``Next``.
* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should correspond to your organization name, or something similar.
  * Your **Artifact ID** should correspond to your project name.
  * Your **Version** is up to you.

* Click ``Next``.
* Enter your project's name, and click ``Finish``.

**To import the Sponge API as a dependency:**

* Open ``pom.xml`` in the navigator.
* With the editor tab in focus, choose ``Code > Generate``.
* Click ``Dependency`` in the popup.
* Search for the :doc:`official Sponge API Maven artifact <../../workspace/artifact>` under **Search by artifact**, and click ``Add``.
* Import the Maven changes, if prompted.

Git Integration
===============

JetBrains offers documentation on Git integration:

https://www.jetbrains.com/idea/help/using-git-integration.html

Track Continuation
==================

If you are following a track in **Preparing for Development**, your next step is:

* **Sponge (coremod):** :doc:`../../devs/forge/contributing`
* **Sponge API:** :doc:`../../devs/api/contributing`
* **SpongeDocs:** This document is not included in the SpongeDocs track.
* **Plugin Development:** :doc:`../../plugins/quick-start`
