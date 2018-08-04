========================
Download Project Version
========================

.. note::

    Please note that this endpoint does **not** use an ore web api version infix such as ``/api/v1/``.

**GET /api/projects/:pluginId/versions/:version/download**

Returns a file stream for the specified version within the specified project. The ``:version`` parameter may be replaced
with ``recommended`` to download the project's current recommended version.

**GET /api/projects/:pluginId/versions/:version/signature**

Returns the pgp signature file stream for the above file. This signature file can be used to verify that the download
was not modified/corrupted in any way and was uploaded by the correct author.
