===========
Get Project
===========

**GET /api/v1/projects/:pluginId**

Returns a single project with a given plugin ID. Note that there are no two plugins on Ore with the same plugin ID.

**Sample output:**

.. code-block:: json

    {
        "pluginId": "auction",
        "createdAt": "2018-01-23 11:08:55.456",
        "name": "Auction",
        "owner": "ewoutvs_",
        "description": "Auction for players",
        "href": "/ewoutvs_/Auction",
        "members": [{
            "userId": 21366,
            "name": "ewoutvs_",
            "roles": ["Owner"],
            "headRole": "Owner"
        }],
        "channels": [{
            "name": "Release",
            "color": "#009600"
        }],
        "recommended": {
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
        },
        "category": {
            "title": "Gameplay",
            "icon": "fa-puzzle-piece"
        },
        "views": 287,
        "downloads": 53,
        "stars": 4
    }
