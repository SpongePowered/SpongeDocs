=============================
Working on the Sponge Coremod
=============================

Overview
========

The term "Sponge" refers to the Sponge coremod, which runs on the Forge platform. Sponge implements the Sponge API. Think of it as the half of the Sponge project that does the work.

Prerequisites
=============

* :doc:`Java Developer Kit <../../workspace/jdk>`
* :doc:`An IDE of your choice <../../workspace/ide/index>`
* :doc:`Git client <../../workspace/git>`
* `Gradle <http://gradle.org/downloads>`_ (optional, but recommended)

Prior to working on the Sponge coremod, please review the :doc:`contributor guidelines <../../devs/guidelines>` and become familiar with them.

PATH Environment Variable
=========================

The PATH environment variable essentially tells your operating system where to look for executable programs. Ensure your PATH environment variable is set correctly prior to working on Sponge. Oracle provides `instructions <https://www.java.com/en/download/help/path.xml>`_ on setting and changing the PATH environment variable.

On Unix-based systems, the environment variable is ``$PATH``. On Windows, the environment variable is ``%PATH%``.

It is recommended to construct your system profile in such a way that the PATH environment variable to set up correctly when you log in, rather than having to manually update it each time you want to work on Sponge.

JAVA_HOME Environment Variable
==============================

The JAVA_HOME environment variable is an environment that is set in the shell, and is the directory in which the Java Developer Kit is installed. It is recommended to construct your system profile in such a way that JAVA_HOME is set up correctly each time you log in.

Windows
~~~~~~~

#. Launch the Command Prompt.
#. To find the path, run ``dir C:\Program Files\Java``
#. To set JAVA_HOME, run ``set JAVA_HOME=C:\Program Files\Java\<your jdk version>``

Alternatively, if you are uncomfortable using the Command Prompt, you can use a GUI:

#. Right-click ``My Computer`` and click ``Properties``.
#. Click ``Advanced``, then ``Environment Variables``.
#. Under ``System Variables``, click ``New``.
#. The name is JAVA_HOME, and the value is the installation path for the Java Development Kit.
#. Click ``OK``.
#. Click ``Apply Changes``.

Mac OS X
~~~~~~~~

#. Launch the Terminal.
#. To find the current path, run ``echo $JAVA_HOME``.
#. Run ``vim .bash_profile``.
#. Enter ``export JAVA_HOME=$(/usr/libexec/java_home)``.
#. Close the editor by pressing Esc, then type ``:x``.
#. Run ``source .bash_profile``.
#. Verify you have set the variable correctly by typing ``echo $JAVA_HOME``.

Tips Before You Start
=====================

* Spaces/whitespace in file names are not ideal.
* Store the Git repository somewhere other than your desktop.

  * Some people prefer to keep it in their user folder.
  * It is recommended to keep file paths short; however, this is up to you.
* Be aware of the file path length limits of your operating system.

Cloning
=======

The following commands will clone Sponge from its Git repository.

.. code-block:: none

    git clone git@github.com:SpongePowered/Sponge.git
    cd Sponge
    git submodule update --init --recursive
    cp scripts/pre-commit .git/hooks

Running
=======

The following command will set up the workspace.

.. code-block:: none

    gradle setupDecompWorkspace --refresh-dependencies

This process does a couple things:

* It downloads the ``.jar`` files that Sponge requires in order to build.
* It downloads Forge, which Sponge also requires in order to build.

The process may take a while, depending on the quality of your network connection.

.. note::

    Remember, we are building Sponge itself, not plugins for Sponge. Plugins do not need Forge to build.

After the Gradle process is complete, follow the steps on the SpongePowered/Sponge `README.md <https://github.com/SpongePowered/Sponge/blob/master/README.md>`_ file on GitHub to import Sponge into your IDE and run the Sponge artifact.

Building
========

The only step required to build Sponge is running ``gradle`` from the Terminal or Command Prompt - whichever one applies to your operating system.

.. note::

    If you do not have Gradle installed, use ``./gradlew`` on Unix systems and ``gradlew.bat`` on Windows systems in lieu of any ``gradle`` command.

You can find the compiled ``.jar`` file in ``./build/libs``. It will be labeled similarly to ``sponge-x.x.x-SNAPSHOT.jar``.

Contributing
============

After reviewing the :doc:`contributor guidelines <../../devs/guidelines>`, sign up for a GitHub account and fork the **SpongePowered/Sponge** repository to get started.
