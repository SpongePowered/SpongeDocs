===========
Get Project
===========

**GET /projects/:pluginId**

Returns a single project with a given plugin ID. Note that there are no two plugins on Ore with the same plugin ID.

**Sample output:**

.. code-block:: json

    {
        "pluginId": "ore",
        "createdAt": "2016-11-07 12:26:35.672",
        "name": "Ore",
        "owner": "windy",
        "description": "Official package manager for Sponge.",
        "href": "/windy/Ore",
        "members": [{
            "userId": 6602,
            "name": "windy",
            "roles": ["Owner"],
            "headRole": "Owner"
        }],
        "channels": [{
            "name": "Beta",
            "color": "#B400FF"
        }],
        "recommended": {
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
        },
        "category": {
            "title": "Admin Tools",
            "icon": "fa-server"
        },
        "views": 275,
        "downloads": 28,
        "stars": 9
    }
