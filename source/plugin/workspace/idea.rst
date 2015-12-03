========================
Setting Up IntelliJ IDEA
========================

This article describes how to configure your **IntelliJ IDEA** workspace for plugin development with the Sponge API.

Starting Your Project
=====================

We recommend using Gradle or Maven when developing plugins with the Sponge API. Gradle and Maven help significantly when
managing dependencies, such as the Sponge API, for your project. This is preferable to including the ``.jar`` file in
your project.

Gradle
~~~~~~

**To create your project:**

* Open **IntelliJ IDEA**.
* Click ``Create New Project``.
* Select ``Gradle`` in the popup, and click ``Next``.
* Enter your **Group ID**, **Artifact ID**, and **Version**.

  * Your **Group ID** should correspond to your organization name, or something similar.
  * Your **Artifact ID** should correspond to your project name.
  * Your **Version** is up to you.

* Click ``Next`` twice, name your project, and click ``Finish``.

**To import the Sponge API as a dependency:**

* Open ``build.gradle`` in the navigator and add the dependencies.
* Have a look at the :doc:`dependencies page <dependencies>` for an example Gradle file.
* Open the **Gradle tab** on the right of the IntelliJ window and hit the refresh button.
* Gradle setup is done! Now you can start coding your plugin.

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
* Search for the :doc:`official Sponge API Maven artifact <dependencies>` under **Search by artifact**, and click ``Add``.
* Import the Maven changes, if prompted.

Git Integration
===============

JetBrains offers documentation on Git integration:

https://www.jetbrains.com/idea/help/using-git-integration.html
