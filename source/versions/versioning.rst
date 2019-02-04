==========
Versioning
==========

.. _sem-ver:

SemVer
======

Sponge uses the semantic versioning (https://semver.org/) specification. As a result, we increment versions according 
to the rules of SemVer every time that we make a release.

SemVer uses the scheme ``X.Y.Z``, where ``X`` is a *major* version, ``Y`` is a *minor* version and ``Z`` is a *patch* 
version. A release containing changes which are not backwards-compatible must be one *major* version ahead of the 
previous release. If there are only new features that are still backwards compatible, then the new release will be 
one *minor* version ahead of the previous release, and if the release strictly contains bugfixes then only the *patch* 
version will be incremented.

This means that ``3.2.0`` is fully compatible to ``3.0.0`` while ``4.0.0`` is not compatible to ``3.0.0``, ``3.1.0``, 
or ``3.1.2``. These versions are fully compatible also; the difference between them are new features (``3.0.0`` -> 
``3.1.0``) and bug fixes (``3.1.0`` -> ``3.1.1`` -> ``3.1.2``). ``3.2.0`` introduced more new features beyond those 
introduced by ``3.1.0``.

Version String
==============

Applying the versioning policy to Sponge begins with SpongeAPI. It is the core on which the version string is based. 
SpongeAPI is the actual API that allows plugins to interact with the game. However, the API is enabled by the Sponge 
implementation (SpongeForge or SpongeVanilla). Together, these products form the foundation of a Sponge server.

As such, the version string reflects the state of these two products. The major and minor release components of a 
version is based on the API while the patch component of the version is based on the implementation. Let's consider 
this version string as an example: ``8.2.0``. This string conveys the API is Version 8.2 and the implementation is the 
first release using this API version.

The following table provides several examples of a version string and its components:

+-----------+---------+----------------+
|           |         |                |
| Version   | API     | Implementation |
|           |         |                |
+===========+=========+================+
|           |         |                |
| ``8.0.2`` | ``8.0`` | ``2``          |
|           |         |                |
+-----------+---------+----------------+
|           |         |                |
| ``8.1.1`` | ``8.1`` | ``1``          |
|           |         |                |
+-----------+---------+----------------+
|           |         |                |
| ``8.5.0`` | ``8.5`` | ``0``          |
|           |         |                |
+-----------+---------+----------------+

There are two other components of the version string: *SNAPSHOT* and *RC*. Both of these labels indicate a version 
still in development. These files are used for testing new features and bug fixes, so they are generally used by 
developers. *SNAPSHOT* is associated with the API, and *RC*, or Release Candidate, is associated with the 
implementation (SpongeVanilla and SpongeForge).

*SNAPSHOT* and *RC* are **always** associated with a number (e.g., ``1-SNAPSHOT``, ``-RC120``). With *SNAPSHOT*, the 
number identifies the **next** *minor* release for SpongeAPI. The number associated with *RC* identifies the build 
number of the implementation and indicates the build is part of the development towards the next 
implementation/recommended release. 

.. note::

    Any file without the *SNAPSHOT* or *RC* label is a released version. A recommended version is a release of an 
    implementation.

.. tip::

    Keep in mind the diffences between a major release, a minor release, and a patch release while reading the 
    following.

API
---

An important part to grasp in understanding Sponge versioning is how the version string conveys the API information. 
The API version is primarily conveyed by the *major* . *minor* labels. The *major* label is always a number. The 
*minor* label can be just a number, indicating a release, or it can be a number with ``-SNAPSHOT`` appended to it, 
indicating a **minor release in development**. Developers are the most likely to use snapshots, so most Sponge users 
can focus on the *major* . *minor* labels to identify which version is represented.

However, the patch release provides important information for understanding which version of the API is represented as 
well. Even though the API itself does not have patch releases, a Sponge implementation is required to use the 
SpongeAPI. So, the version string has to convey both API and implementation versions. We achieve this by tying them 
together and having the implementation release reflect any patch release for the API. This allows the version string to 
accurately and completely reflect the status of the API *and* the implementation. 

The good news is the policy is straight forward. When the implementation release is ``0``, the API is a release. An 
example of the version string in this case is ``8.1.0``. When the implementation release is **not** ``0``, the API is 
in development. An example of the version string in this case is ``8.1.1``. In this example, the API is actually the 
next version in development (i.e. ``2-SNAPSHOT``). 

Implementations
---------------

The patch version for Sponge is added to the version string with the implementation; however, care must be taken to 
interpret the information correctly. As mentioned above, the patch version provides information about the release 
status of the API as well as the implementation.

First, a **zero** in the patch label indicates a release. A full release occurs when a major release occurs (i.e. 
**zero** in the minor *and* patch components). Examples are ``7.2.0`` and ``8.0.0`` respectively. 

Second, any **non-zero** number in the patch label indicates the next **API minor release in development**. For 
example, ``spongevanilla-1.12.2-7.1.4`` is using ``7.2`` version of the API (which is in development); it does **NOT** 
use the ``7.1`` version (which has been released). However, this version is still a **recommended version** because the 
*RC* label is not used. Plus, it is the ``4`` th recommended version for this implementation.

.. tip::

    ``spongevanilla-1.12.2-7.2.0`` is a released version of the API and a released version of the implementation. It is 
    also a recommended version because the *RC* label is not used.

Last, a *RC* in the patch version indicates an **implementation version in development**. The implementation is still 
having features added, bugs fixed, and testing performed for the **next recommended version**. Using the same example 
above, the version string for this implementation is ``spongevanilla-1.12.2-7.1.5-RC152``.
