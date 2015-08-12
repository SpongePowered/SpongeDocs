======================
Contributing to Sponge
======================

Overview
========

What is this about?
~~~~~~~~~~~~~~~~~~~

The articles in this section explain how to get involved with the Sponge Forge coremod, SpongeVanilla, SpongeCommon,
the Sponge API, or SpongeDocs. These articles are essentially required reading for anyone who wishes to begin developing
or writing for Sponge. We also recommend developers become familiar with the Sponge API `JavaDocs
<https://spongepowered.github.io/SpongeAPI/>`__.

Who should read this?
~~~~~~~~~~~~~~~~~~~~~

This articles listed in the contents below are intended for people who wish to:

* Volunteer to help with the Sponge documentation with top-notch articles and edits.
* Volunteer to help with Sponge API or Sponge implementation development by planning, developing, and submitting a
  quality PR on GitHub.
* Make Spongie, our squishy yellow mascot, proud.

There are, of course, other ways to help if you'd like:

* Join the Sponge forums and the #sponge IRC channel to help others and hang out.
* Spread the word about Sponge!

Sponge, SpongeVanilla and SpongeCommon
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

There are two official implementations of the Sponge API: Sponge (coremod) and SpongeVanilla. Despite their differences,
much of their code is shared, and this is now contained in the SpongeCommon repository. This diagram shows an overview
of the Sponge codebase and its dependencies:

**SpongeCommon**  is the core of both official implementations of the Sponge API. Within it resides all core services
and the common layer of implementation that is used in both Sponge and SpongeVanilla. Almost all implementation should
reside in SpongeCommon, with separation of interfaces as necessary for special handling per implementation.

**Sponge** is the forge implementation extending SpongeCommon, with special handling for maximizing compatibility with
Forge, and therefore numerous Forge mods.

**SpongeVanilla** is the vanilla implementation of the Sponge API. Acting as a standalone implementation having complete
control over the Minecraft Server, this implementation does not support mods wishing to add new things like blocks and items.



Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    guidelines
    api/index
    implementation/index
    spongedocs
    porting
