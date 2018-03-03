=============
List projects
=============

**GET /api/v1/projects**

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
        "pluginId": "griefprevention",
        "createdAt": "2017-01-29 03:28:16.978",
        "name": "GriefPrevention",
        "owner": "blood",
        "description": "This plugin is designed to prevent all forms of grief.",
        "href": "/blood/GriefPrevention",
        "members": [{
            "userId": 26141,
            "name": "blood",
            "roles": ["Owner"],
            "headRole": "Owner"
        }],
        "channels": [{
            "name": "Release",
            "color": "#009600"
        }],
        "recommended": {
            "id": 1277,
            "createdAt": "2018-02-25 22:17:57.018",
            "name": "1.12.2-4.3.0.555",
            "dependencies": [{
                "pluginId": "spongeapi",
                "version": "7.1.0-SNAPSHOT-6aa4e438"
            }],
            "pluginId": "griefprevention",
            "channel": {
                "name": "Release",
                "color": "#009600"
            },
            "fileSize": 919256,
            "md5": "e1a1603f59f6a74edf5ea3ef8183361a",
            "staffApproved": true,
            "href": "/blood/GriefPrevention/versions/1.12.2-4.3.0.555",
            "tags": [{
                "id": 42,
                "name": "Sponge",
                "data": "7.1.0-SNAPSHOT-6aa4e438",
                "backgroundColor": "#F7Cf0D",
                "foregroundColor": "#000000"
            }],
            "downloads": 24,
            "author": "blood"
        },
        "category": {
            "title": "Protection",
            "icon": "fa-lock"
        },
        "views": 4208,
        "downloads": 2727,
        "stars": 30
    }, {
        "pluginId": "luckperms",
        "createdAt": "2017-02-01 18:58:17.19",
        "name": "LuckPerms",
        "owner": "Luck",
        "description": "An advanced permissions plugin",
        "href": "/Luck/LuckPerms",
        "members": [{
            "userId": 25904,
            "name": "Luck",
            "roles": ["Owner"],
            "headRole": "Owner"
        }],
        "channels": [{
            "name": "Release",
            "color": "#009600"
        }],
        "recommended": {
            "id": 1275,
            "createdAt": "2018-02-25 14:52:26.889",
            "name": "4.1.7",
            "dependencies": [{
                "pluginId": "spongeapi",
                "version": "6.0.0"
            }],
            "pluginId": "luckperms",
            "channel": {
                "name": "Release",
                "color": "#009600"
            },
            "fileSize": 1319619,
            "md5": "66119f637f0327821169ad2c2545f43d",
            "staffApproved": false,
            "href": "/Luck/LuckPerms/versions/4.1.7",
            "tags": [{
                "id": 10,
                "name": "Sponge",
                "data": "6.0.0",
                "backgroundColor": "#F7Cf0D",
                "foregroundColor": "#000000"
            }],
            "downloads": 35,
            "author": "Luck"
        },
        "category": {
            "title": "Admin Tools",
            "icon": "fa-server"
        },
        "views": 5575,
        "downloads": 1867,
        "stars": 35
    }]
