==============
Installing Git
==============

Overview
========

Git is an open-source version control system, and helps tremendously with collaborative project development.

The Sponge project, alongside thousands of other open-source projects, hosts its Git repositories on GitHub. Thus,
Git is a crucial tool in the development of Sponge and the Sponge API.

The `Git website <https://www.git-scm.com/>`__ has substantial documentation, and their downloads page offers a range
of options for GUI clients suitable for various operating systems.

Download
========

Windows
~~~~~~~

`GitHub for Windows <https://windows.github.com/>`_ is an easy method of installing Git on Windows, because Git is
included as a part of the software.

Rebooting your computer after installing Git is recommended.

Mac
~~~

There are a couple of ways to install Git on Mac OS X.

The easiest method is to install Xcode Command Line Tools.

.. warning::

    These instructions do not work on Macs running a version older than Mavericks. If you are running a version of OS X
    older than Mavericks, install the GitHub client instead.

#. Launch the Terminal.
#. Run ``xcode-select --install``.
#. Install it, and grab a cookie while you wait.
#. Run ``git`` from the Terminal.

Alternatively, you can install `GitHub for Mac <https://mac.github.com/>`_. Git is available as a part of the GitHub
for Mac installation.

Rebooting your computer after installing Git is recommended.

Linux and Unix
~~~~~~~~~~~~~~

The simplest method of installing Git on Linux is by using the package manager that came with your Linux distribution.

.. note::

    You may need to prefix these commands with ``sudo``.

#. Launch the Terminal.
#. Run ``apt-get install git`` if you are on a Ubuntu or Debian-based distribution. Run ``yum install git`` if you are on Fedora.

There is not a GitHub client available for Linux, unlike Windows and Mac.

Rebooting your computer after installing Git is recommended.
