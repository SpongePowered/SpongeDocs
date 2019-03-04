==========
Versioning
==========

.. _sem-ver:

.. tip::
    Sponge follows a format of the SemVer specification in its projects. You can read about general SemVer usage at 
    http://semver.org.

The SpongeAPI and implementations (SpongeForge/SpongeVanilla) follow two different policies. Understanding our versions 
is a matter of interpreting the SemVer version string. The SpongeAPI version utilizes the *Major* and *Minor* parts in 
the SemVer version string while the implementations use the *Major*, *Minor*, and *Patch* parts.

API
===

Plugin developers create plugins for a particular SpongeAPI version, such as ``7.0``, ``7.3``, or ``7.9``, so the 
plugin works with the features provided in that specific version. Versions are incremented to reflect added, removed, 
or changed features. How the version number changes convey how plugins might be affected.

Major
-----

A change in the Major number (**X**.Y.Z) indicates that changes broke some APIs that were guaranteed to work in the 
previous version. Plugins *might* break with this kind of version change. For example, a plugin for version 6.9 *might* 
not run with version 7.0.

Minor
-----

A change in the Minor number (X. **Y**.Z) indicates that changes added some APIs, but plugins for previous minor 
versions in the same major version are still guaranteed to work. For example, a plugin for version ``7.3`` will run 
with version ``7.4`` or ``7.5``. However, the opposite is not *necessarily* the same. A plugin for version ``7.4`` is 
not guaranteed to run with version ``7.3``. 

.. note::

    The *Minor* number is always reset to zero when the *Major* number changes.

Examples of SpongeAPI version strings are ``spongeapi-7.1.0`` and ``spongeapi-7.2.0``. 

Implementations
===============

The version string for the Sponge implementations includes the target Minecraft version as well as the SpongeAPI 
version. The SpongeForge implementation also specifies the Forge **recommended version** with which it is guaranteed to 
be compatible.

Patch
-----

A change in the patch number (X.Y. **Z**) occurs with implementation builds. These builds contain bug fixes, 
performance improvements, configuration changes, and other changes not related to the API. When a patch number is only 
a number, this build is a **recommended version**. Any plugin developed for the SpongeAPI version in the string, or is  
compatible with that version, is guaranteed to work.

.. note::

    The *Patch* number is always reset to zero when the *Minor* number changes.

Examples of implementation version strings are ``spongevanilla-1.12.2-7.1.5`` and ``spongeforge-1.12.2-2768-7.1.5``. 

.. warning::

    Plugin developers may choose to develop their plugin for a particular implementation. The plugin's Ore page should 
    note such decision with a tag by the version. Sponge makes no guarantee of a plugin's compatibility when marked as 
    such.

Recommended Version
===================

Recommended Versions are also known as Recommended Builds or Releases. They are versions of reasonable quality of which 
the implementations can make full use of the functionality available in SpongeAPI, and plugins compatible with the API 
are guaranteed to work as designed.

.. warning::

    The following information is for Sponge Developers. Plugin Developers and Server Administrators should only use 
    **Recommended Versions** for development and server installations. Using a version that is not a recommended build 
    introduces instability and nearly guarantees problems. We strongly discourage the use of non-recommended builds by 
    Plugin Developers and Server Administrators.

SNAPSHOT
========

A release which has the ``-SNAPSHOT`` label represents the next API version in development. For example, 
``7.2.0-SNAPSHOT`` means ``7.2.0`` is in development. Another example is ``8.0.0-SNAPSHOT``, which means the next 
*major* release (``8.0.0``) is in development. New features added in snapshots may break at any time. 

.. note::

    If the minor version is zero (e.g., ``8.0-SNAPSHOT``), **anything** may break before it is released and plugins 
    cannot expect stability. If the Minor version is greater than zero (e.g., ``7.2-SNAPSHOT``), anything up to the 
    previous minor version is still guaranteed to work (in this case, ``7.1``). However, anything added since it **may 
    break** and instability is likely.

    When the minor and patch numbers are zero, and the ``-SNAPSHOT`` label is attached, versions are ``bleeding`` 
    builds for testing the next version of SpongeAPI only and may break at any time.

    The ``-SNAPSHOT`` label on a SpongeCommon file means the implementation is nearly complete but is still not a 
    stable release. More importantly, Gradle determines whether or not to append the *-RC{build number}* to the version 
    string by the presence of the label.

Release Candidate
=================

Any push made to GitHub which is not a *Recommended Build* is a *Release Candidate*. Any release candidate can become a 
recommended build. Furthermore, the code may or may not change; the change might occur in a settings file. The 
resulting build may run as well as a recommended build, or it may not run at all. In the end, a change occurred in the 
repository, it triggered a build, and the resulting build is not a recommended build. Therefore, it is a release 
candidate.

.. tip::

    *SNAPSHOT* appears in SpongeAPI and SpongeCommon version strings while *RC* appears in implementation version 
    strings.
