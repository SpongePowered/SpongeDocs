=========================
Working on the Sponge API
=========================

Overview
========

The Sponge API is a community-developed API that allows developers to create plugins for Minecraft servers that run Sponge or an implementation of the Sponge API.

Prerequisites
=============

* :doc:`Java Developer Kit <../../workspace/jdk>`
* :doc:`An IDE of your choice <../../workspace/ide/index>`
* :doc:`Git client <../../workspace/git>`

Prior to working on the Sponge API, please review the :doc:`contributor guidelines <../../devs/guidelines>` and become familiar with them.

Clone
=====

The following commands will clone Sponge API from its Git repository.

.. code-block:: none

    git clone git@github.com:SpongePowered/SpongeAPI.git
    cd Sponge
    cp scripts/pre-commit .git/hooks

Building
========

The only step required to build Sponge API is running ``gradle`` from the Terminal or Command Prompt - whichever one applies to your operating system.

.. note::

    If you do not have Gradle installed, use ``./gradlew`` on Unix systems and ``gradlew.bat`` on Windows systems in lieu of any ``gradle`` command.

You can find the compiled ``.jar`` file in ``./build/libs``. It will be labeled similarly to ``spongeapi-x.x.x-SNAPSHOT.jar``.

Contributing
============

After reviewing the :doc:`contributor guidelines <../../devs/guidelines>`, sign up for a GitHub account and fork the **SpongePowered/SpongeAPI** repository to get started.
