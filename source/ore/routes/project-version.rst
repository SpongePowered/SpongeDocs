===================
Get Project Version
===================

**GET /api/v1/projects/:pluginId/versions/:version**

Returns a single project version for the project with the given plugin ID and the given version string. Note that there
are no two versions in a project with the same version string.

**Example output:**

.. code-block:: json

    {
        "id": 1226,
        "createdAt": "2018-02-08 18:41:36.917",
        "name": "1.1",
        "dependencies": [{
            "pluginId": "spongeapi",
            "version": "7.0.0"
        }],
        "pluginId": "auction",
        "channel": {
            "name": "Release",
            "color": "#009600"
        },
        "fileSize": 11441,
        "md5": "d169809b0eda0e5d49bc60d5f69f097b",
        "staffApproved": false,
        "href": "/ewoutvs_/Auction/versions/1.1",
        "tags": [{
            "id": 13,
            "name": "Sponge",
            "data": "7.0.0",
            "backgroundColor": "#F7Cf0D",
            "foregroundColor": "#000000"
        }],
        "downloads": 26,
        "author": "ewoutvs_"
    }
