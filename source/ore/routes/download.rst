========================
Download Project Version
========================

**GET /api/v1/projects/:pluginId/versions/:version/download**

Returns a file stream for the specified version within the specified project. The ``:version`` parameter may be replaced
with ``recommended`` to download the project's current recommended version.
