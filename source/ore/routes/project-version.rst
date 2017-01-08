===================
Get Project Version
===================

**GET /api/projects/:pluginId/versions/:version**

Returns a single project version for the project with the given plugin ID and the given version string. Note that there
are no two versions in a project with the same version string.

**Example output:**

.. code-block:: json

    {
        "id": 221,
        "createdAt": "2016-11-07 12:26:35.672",
        "name": "1.0.0",
        "dependencies": [],
        "pluginId": "ore",
        "channel": {
            "name": "Beta",
            "color": "#B400FF"
        },
        "fileSize": 52807
    }
