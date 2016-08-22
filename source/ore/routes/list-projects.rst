=============
List projects
=============

**GET /api/projects**

Returns a list of projects based on given criteria.

**Query parameters:**

+------------+------------------------------+-------------------------------------------------------------+
| Name       | Data Type                    | Description                                                 |
+============+==============================+=============================================================+
| categories | Comma Separated Integer List | Filters projects by categories (inclusive).                 |
+------------+------------------------------+-------------------------------------------------------------+
| sort       | Integer                      | Sorts projects by a given method.                           |
+------------+------------------------------+-------------------------------------------------------------+
| q          | String                       | Search query. Checks against name, author, and description. |
+------------+------------------------------+-------------------------------------------------------------+
| limit      | Integer                      | Limits the amount of projects returned (max / default: 25). |
+------------+------------------------------+-------------------------------------------------------------+
| offset     | Integer                      | Drops the first *n* projects from the result list.          |
+------------+------------------------------+-------------------------------------------------------------+

**Sample output:**

.. code-block:: json

    [{
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
    }, {
        "pluginId": "serverlistplus",
        "createdAt": "2016-11-07 12:26:35.672",
        "name": "ServerListPlus",
        "owner": "Minecrell",
        "description": "An extremely customizable server status ping plugin for Minecraft",
        "href": "/Minecrell/ServerListPlus",
        "members": [{
            "userId": 1875,
            "name": "Minecrell",
            "roles": ["Owner"],
            "headRole": "Owner"
        }],
        "channels": [{
            "name": "Release",
            "color": "#009600"
        }],
        "recommended": {
            "id": 231,
            "createdAt": "2016-11-07 12:26:35.672",
            "name": "3.4.7",
            "dependencies": [],
            "pluginId": "serverlistplus",
            "channel": {
                "name": "Release",
                "color": "#009600"
            },
            "fileSize": 397480
        },
        "category": {
            "title": "Admin Tools",
            "icon": "fa-server"
        },
        "views": 60,
        "downloads": 9,
        "stars": 3
    }]
