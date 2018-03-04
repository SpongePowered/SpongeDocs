==========
List Users
==========

**GET /api/v1/users**

Returns a list of users based on given criteria.

**Query parameters:**

+--------+-----------+-------------------------------------------------------------+
| Name   | Data Type | Description                                                 |
+========+===========+=============================================================+
| limit  | Integer   | Limits the amount of projects returned ( default: all ).    |
+--------+-----------+-------------------------------------------------------------+
| offset | Integer   | Drops the first *n* projects from the result list.          |
+--------+-----------+-------------------------------------------------------------+

**Sample output:**

.. code-block:: json

    [{
        "id": 26141,
        "createdAt": "2016-12-31 02:20:59.63",
        "username": "blood",
        "roles": [
            "Sponge Staff",
            "Sponge Developer",
            "Sponge Leader"
        ],
        "starred": [],
        "avatarUrl": "https://www.gravatar.com/avatar/9d9369d67c6f5d2c5f9d3b0d195a55f9",
        "projects": [{
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
        }]
    },
    {
        "id": 26082,
        "createdAt": "2016-12-28 01:04:04.849",
        "username": "Zidane",
        "roles": [
            "Sponge Staff",
            "Sponge Developer",
            "Sponge Leader",
            "Ore Moderator"
        ],
        "starred": [],
        "avatarUrl": "https://www.gravatar.com/avatar/3b8d6171c3f15daf35328a4f04c83de9?s=400",
        "projects": []
    },
    {
        "id": 25951,
        "createdAt": "2016-12-28 01:10:05.588",
        "username": "Inscrutable",
        "roles": [
            "Sponge Staff",
            "Sponge Documenter",
            "Team Leader"
        ],
        "starred": [
            "advancedserverlisticons",
            "economylite",
            "graveyards",
            "nucleus",
            "nucleus-mixin",
            "permissionsex",
            "playershopsrpg",
            "protectionperms",
            "serverlistplus",
            "slotmachines",
            "spectastic",
            "voxelsniper"
        ],
        "avatarUrl": "https://forums-cdn.spongepowered.org/uploads/default/original/2X/4/4d2d123de67afac9ca5b81eb89ad221c058dcb6a.png",
        "projects": []
    }]
