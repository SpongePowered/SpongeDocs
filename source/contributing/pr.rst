===============
Submitting a PR
===============

Overview
========

Major API Addition
~~~~~~~~~~~~~~~~~~

So, you've developed a pretty large API addition that you want to now submit as a PR. Good! Constructive PR's are what make this project keep getting better. Which brings us to writing the glorious PR description.

There have been a few in the past that go above and beyond the standards, examples include:
`Inventory API PR <https://github.com/SpongePowered/SpongeAPI/pull/443>`_
`Data API PR <https://github.com/SpongePowered/SpongeAPI/pull/542>`_

Of course, those examples are the extreme, but PR's that are accepted and provide as a good standard of what should be included in a PR description are:
`Event Filtering PR <https://github.com/SpongePowered/SpongeAPI/pull/927>`_
`Bans improvement API PR <https://github.com/SpongePowered/SpongeAPI/pull/954>`_

A few things that can be taken from this:
- Links to any implementation PRs in clear view at the top of the PR, this can be achieved with GitHub Markdown

.. codeblock:: markdown

   *SpongeAPI*|[SpongeCommon](html link)|[SpongeForge](html link)|[SpongeVanilla](html link)

- Clear description of what the API PR is aiming to do, this can be a brief summary as if writing an essay, at most 4 sentences long, depending on what the functionality is.

- Clear code examples of how plugins can use the new feature (and if there are old features existing, why they needed to be changed).

