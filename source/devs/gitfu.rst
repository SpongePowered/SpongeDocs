===============================
How to Work with Git and Sponge
===============================

*(aka Git-Fu: A Guide to Using Git in Development)*

Using git is a vital part of developing for Sponge and SpongeAPI.

Using git well can improve your contributions. This document will help
show you how to make the most of git when contributing to Sponge.


Who Are You?
============

Before you even start to work with git and the repository, make sure your
git configuration has your identity set up.

1.  ``git config --list``
#.  Look for ``user.name`` and ``user.email``
#.  If they are not the same username and email as your Github account
    then set them:
#.  ``git config --global user.name "John Doe"``
#.  ``git config --global user.email johndoe@example.com``


.. tip::
    Do not proceed with any contribution in any Sponge repository until you establish your ``user.name`` and ``user.email``.


Getting an Enlistment to Sponge
===============================

Setup your workspace as described in the main page of the Sponge project.

* git clone git@github.com:SpongePowered/Sponge.git
* cd Sponge
* git submodule update --init --recursive
* cp scripts/pre-commit .git/hooks

Your local copy of Sponge is now setup and your branch is master.

* Working on the SpongeAPI is described here  :doc:`gitfu-api`.

* Working on the Sponge implementation is described here  :doc:`gitfu-impl`.



