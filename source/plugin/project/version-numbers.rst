Version Numbers
===============

Your plugin needs a unique version number in order to allow humans to find out which version of a plugin is the
successor of the other. There are plenty of versioning schemes that you could use for your own plugin. In the following
part, we explain a few concepts with their pros and cons.

Sponge recommends using a version identifier that consists of either two or three numeric version parts separated with
a ``.`` and additional label appended with a hyphen ``-`` such as ``3.1`` or ``1.0.3-API7`` similar to SemVer.
Start with ``1.x`` if your plugin is ready. Start with ``0.x`` if your plugin is not yet ready or unstable.

.. note::

    While Sponge recommends using the above versioning scheme, plugin authors are free to choose their own. Please keep in mind that your users have to deal with a multitude of versioning schemes, so keep it simple.

SemVer
------

`SemVer <https://semver.org/>`_ is a commonly used semantic versioning scheme. It consists of three numeric blocks that
are separated by dots and additional labels which are appended using hyphens. The three numeric blocks make up
the ``MAJOR``, ``MINOR`` and ``PATCH`` version parts. 

* A change in the **MAJOR** version indicates that you made incompatible API changes. Rewriting large parts of your
  plugin or the entire plugin would also count as incompatible changes.
* A change in **MINOR** version indicates that a feature has been added without breaking backward compatibility.
* A change in the **PATCH** version indicates that some bugs have been fixed.

The labels can be used to mark builds as pre-releases or contain build metadata such as the supported Minecraft version.
If you increase one part of the version, then you will set the following parts back to zero. Using this strategy you
can find the latest version by comparing the version blocks with each other, however, this scheme does not contain any
information about the release order for versions which differ in two version number blocks. Example: Version ``1.0.9``
would be released after ``1.0.8``, but version ``1.1.0`` could be released before, after or even at the same time as
either of them. 

.. note::

    The SpongeAPI uses this versioning scheme.

**Examples**

* ``0.1.0``
* ``1.0.0``
* ``1.1.0-SNAPSHOT``
* ``3.12.21-API7``

**Pros**

* Easy to read and understand

**Cons**

* Mostly API oriented so the terms don't really apply if you don't provide an API in your plugin.

Timestamp
---------

This is a less commonly used versioning scheme, but it is also a very simple one, as it does not contain any additional
information in the version numbers. It does not matter which separators you use (if any) or which date and time parts
you include. However, we recommend you to order date and time parts in the descending order of duration like
``YYYY-MM-DD``. If you sort your files alphabetically, please make sure that the order would be the same as the version
order. It is also possible to add a build qualifier or a build number.

**Examples**

* ``18.01.31``
* ``v2018-05-29.1``
* ``2018.01.13-SNAPHOT``

**Pros**

* Easy to read and understand

**Cons**

* Does not contain any information about compatibility.

Other
-----

You can read more about Software versioning and versioning schemes
`online <https://en.wikipedia.org/wiki/Software_versioning>`_.
