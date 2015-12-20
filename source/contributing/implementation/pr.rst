=========================
Submitting a Pull-Request
=========================

The Basics
==========

First you need to setup your machine to be able to develop for and with Sponge:

* read :doc:`/preparing/index` and setup your machine
* get familiar with git and GitHub: :doc:`../howtogit`
* read our :doc:`Code Style page <codestyle>` and :doc:`../guidelines`
* get familiar with our :doc:`git-implementation`

When you're done and feel you're ready for developing Sponge, decide which parts you want to work on.

Writing a PR
============

Fixing Bugs
~~~~~~~~~~~

Explain in a few sentences:

* which bug you encountered, especially

  * how it behaved
  * how you think it should behave

* what you fixed
* how you fixed it

Major API Addition
~~~~~~~~~~~~~~~~~~

So, you've developed a pretty large API addition that you want to now submit as a PR. Good! Constructive PR's are what
make this project keep getting better. Which brings us to writing the glorious PR description.

There have been a few in the past that go above and beyond the standards, examples include:

* `Inventory API PR <https://github.com/SpongePowered/SpongeAPI/pull/443>`_
* `Data API PR <https://github.com/SpongePowered/SpongeAPI/pull/542>`_

Of course, those examples are the extreme, but PR's that are accepted and provide as a good standard of what should be
included in a PR description are:

* `Event Filtering PR <https://github.com/SpongePowered/SpongeAPI/pull/927>`_
* `Bans improvement API PR <https://github.com/SpongePowered/SpongeAPI/pull/954>`_

A few things that can be taken from this:

* Links to any implementation PRs in clear view at the top of the PR, this can be achieved with GitHub Markdown

.. code-block:: none

  *SpongeAPI*|[SpongeCommon](html link)|[SpongeForge](html link)|[SpongeVanilla](html link)


* Clear description of what the API PR is aiming to do, this can be a brief summary as if writing an essay, at most 4
  sentences long, depending on what the functionality is.

* Clear code examples of how plugins can use the new feature (and if there are old features existing, why they needed
  to be changed).
