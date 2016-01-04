=========================================================
The Sponge Versioning System and Repository Branch Layout
=========================================================

With the release for beta we've moved the SpongeAPI versioning to semantic versioning (see http://semver.org/).
This change means that every time that we make a release we have to increment the version according to the rules
of semver.

SemVer
======

SemVer uses the scheme ``X.Y.Z``, where ``X`` is a *major* version, ``Y`` is a *minor* one and ``Z`` finally is a
*patch* version.
A release containing changes which are not backwards-compatible must be one *major* version ahead of the previous
release. If there are only new features that are still backwards compatible, then the new release will be one *minor*
version ahead of the previous release, and if the release strictly contains bugfixes then only the *patch* version will
be incremented.

This means that for example ``3.2.0`` is fully compatible to ``3.0.0`` while ``4.0.0`` isn't binary compatible to
``3.0.0``. ``3.1.0`` and ``3.1.2`` are fully interchangeable besides the bugs that were fixed.

The layout of our branches (described below) is designed to assist this process by allowing us to make minor releases
without a breaking change forcing us to make it a major release. This branch layout applies to the SpongeAPI,
SpongeCommon, SpongeForge, and SpongeVanilla repositories but not to the SpongeDocs.

SpongeAPI, SpongeCommon, SpongeForge and SpongeVanilla
======================================================

The Core Branches
~~~~~~~~~~~~~~~~~

There are three branches which form the core of our repositories; they are ``master``, ``bleeding``, and
``release``. The ``master`` and ``bleeding`` branches are for active development and the ``release`` branch tracks
the commit as of the most recent release build.

The key differences between the ``master`` and ``bleeding`` branches is that any ``feature`` branches which are
breaking *must* be merged into the bleeding branch. This allows the ``master`` branch to only contain
backwards-compatible changes allowing minor versions to be released based on it if necessary.

Release Branches
~~~~~~~~~~~~~~~~

Prior to releasing builds the content of the release should be first moved to a release prep branch.
This branch allows dedicated testing to be performed for a release without forcing a code freeze on
the development branches. Any bugfixes applied to the ``release`` branch are merged back to the ``master``
branch when the release is finalized. Once a release is made the version of the ``master`` and ``bleeding``
branches are both updated: the ``master`` branch to the next minor version and the ``bleeding`` branch to
the next major version (assuming it was not already on the next major version).

Hotfix Branches
~~~~~~~~~~~~~~~

If after a release is made, and a significant bug is found, a ``hotfix/foo`` branch can be created based on the
last release version and a new release can be made from this hotfix branch with the patch version
incremented by one. The hotfix branch can then be merged back into ``master`` for inclusion into future
versions.

Feature Branches
~~~~~~~~~~~~~~~~

New features or changes will continue to be done in a ``feature/foo`` or ``fix/bar`` branch. When merging
back into a development branch (``master`` or ``bleeding``) you should consider whether the changes are
breaking or are strictly backwards-compatible. If the changes are purely new features, or
binary-compatible bugfixes, then the feature branch can be merged into the ``master`` branch. If the
changes include any breaking changes however, then the feature branch must be merged into the
``bleeding`` branch to be included in the next major release.

SpongeDocs
==========

The SpongeDocs follow their own versioning scheme which takes the other projcts parts versioning scheme (see above) into
account.

.. todo WE NEED OUR OWN VERSIONING SCHEME ON THE DOCS
