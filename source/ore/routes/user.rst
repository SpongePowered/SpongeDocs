========
Get User
========

**GET /api/users/:username**

Returns a single user.

**Sample output:**

.. code-block:: json

    {
        "id": 6602,
        "createdAt": "2016-11-07 12:26:35.672",
        "username": "windy",
        "roles": ["Ore Admin", "Ore Moderator", "Sponge Developer", "Sponge Staff"],
        "starred": [],
        "avatarTemplate": "https://forums.spongepowered.org/user_avatar/forums.spongepowered.org/windy/{size}/8440_1.png",
        "projects": [{
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
        }]
    }
