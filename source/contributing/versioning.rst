========================
Repository Branch Layout
========================

The layout of our branches is designed to assist :ref:`semantic versioning <sem-ver>` by allowing us to make minor 
releases without a breaking change forcing us to make it a major release. This branch layout applies to SpongeAPI, 
SpongeCommon, SpongeForge, and SpongeVanilla repositories but not to the SpongeDocs.

SpongeAPI, SpongeCommon, SpongeForge and SpongeVanilla
======================================================

The Bleeding Branch
~~~~~~~~~~~~~~~~~~~

The core of our repositories is the ``bleeding`` branch. Almost all changes will be added to ``bleeding``, including
new features, changes, and bugfixes. The version of ``bleeding`` will always be the next major release version 
appended with ``-SNAPSHOT`` (e.g. ``8.0.0-SNAPSHOT``) to denote that it is not yet a final build and subject to change.

The primary reason for having the ``bleeding`` branch is to have a testing ground for changes. Even experienced
members of the Sponge team can accidentally cause a build to fail or miss a bug. The ``bleeding`` branch will be
tested by people in the community that want the very latest, and it means that we can fix bugs that arise far more
readily.

Stable Branches
~~~~~~~~~~~~~~~

Stable branches represent a much more stable platform which plugins and server implementations can be built upon. There
will be no breakages to API, only non-breaking additions. There is a branch named after each major API release, which
contains the latest API/implementation for that release including any minor or patch releases.

When the time comes to release a major version, a new ``stable-x`` branch will be created from ``bleeding``, where
``x`` is the new major version - for example, ``stable-7``. ``bleeding`` will be appropriately updated to be the next
major release as described above.

Changes that have been in ``bleeding`` for a while, which have no known bugs, and that can be applied to a previous
major release will be cherry-picked to the relevant ``stable`` branch for future release. Changes will be grouped into
a new minor version, unless an immediate fix is preferred in which case a bugfix version will be created instead. When
a version is released, the API repository will have a tag created pointing to that release's commit.

Feature Branches
~~~~~~~~~~~~~~~~

New features or changes should be created in a ``feature/foo`` or ``fix/bar`` branch. This should be based on the most
recent commit to ``bleeding``. The only exception to this is if the changes are incompatible with the breaking changes
in ``bleeding``, in which case you should base against the relevant ``stable-x``. You should state in your pull
request why your change cannot be included in ``bleeding`` - such as fixing a bug in a feature that was removed by
Mojang in a later release.

If the changes made are not breaking for a previous release, the Sponge team may also cherry-pick the changes to one
or more ``stable`` branches assuming that no problems arise after the change is merged into ``bleeding``.

SpongeDocs
==========

The SpongeDocs themselves are unversioned following our philosophy that they will never be finished, but instead in a
constant flux of ever increasing usability. However, they *target* a specific version of the API, generally the most
recent *release* of SpongeAPI.

Core Branch
~~~~~~~~~~~

The core branch for the SpongeDocs is ``stable``. Each new commit to ``stable`` triggers a rebuild of the `docs website
<https://docs.spongepowered.org/>`_. Commits to ``stable`` are generally made to document the most current SpongeAPI
release or to fix minor mistakes on the Docs.

Feature Branches
~~~~~~~~~~~~~~~~

Whenever a new feature is described, older texts are updated or reworded or the documents are restructured, it is done
in a ``feature/foo`` or ``fix/bar`` branch. Those branches will then be reviewed and, once they are deemed complete,
may be merged.

A feature branch may only be merged into master if the changes / additions made in it are correct regarding the
**SpongeAPI release currently targeted by the SpongeDocs**. Any feature branches that describe features not yet included
in a release stay unmerged until the corresponding API version is released and becomes the new targeted version for the
SpongeDocs. However, the Docs team might collect additions for a specific version on a single branch.

.. image:: /images/contributing/versioning-release-branch.svg
    :alt: release branch example

Release Branches
~~~~~~~~~~~~~~~~

SpongeDocs uses ``release/x.y.z`` branches to publish Docs for older API versions like API ``3.1.0``. Older API releases
are available on their respective branches. Whenever a new API version is released, the Docs Staff will create a new
``release/x.y.z`` branch and bump master to the new API version afterwards. A commit to a ``release`` branch also
triggers a rebuild of the older Docs release, just like on the core branch.
