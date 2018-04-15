=====================
List Project Versions
=====================

**GET /api/v1/projects/:pluginId/versions**

Returns a list of versions for a project of a given plugin ID based on given criteria.

**Query parameters:**

+----------+-----------------------------+-------------------------------------------------------------+
| Name     | Data Type                   | Description                                                 |
+==========+=============================+=============================================================+
| channels | Comma Separated String List | Filters versions by channels (inclusive).                   |
+----------+-----------------------------+-------------------------------------------------------------+
| limit    | Integer                     | Limits the amount of versions returned (max / default: 10). |
+----------+-----------------------------+-------------------------------------------------------------+
| offset   | Integer                     | Drops the first *n* versions from the result list.          |
+----------+-----------------------------+-------------------------------------------------------------+

**Example output:**

.. code-block:: json

    [{
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
        "downloads": 25,
        "author": "ewoutvs_"
    },
    {
        "id": 1139,
        "createdAt": "2018-01-23 11:08:55.798",
        "name": "1.0",
        "dependencies": [{
            "pluginId": "spongeapi",
            "version": "7.0.0"
        }],
        "pluginId": "auction",
        "channel": {
            "name": "Release",
            "color": "#009600"
        },
        "fileSize": 11266,
        "md5": "2d9896413912a0ba719a9fb624b9ff8e",
        "staffApproved": false,
        "href": "/ewoutvs_/Auction/versions/1.0",
        "tags": [{
            "id": 13,
            "name": "Sponge",
            "data": "7.0.0",
            "backgroundColor": "#F7Cf0D",
            "foregroundColor": "#000000"
        }],
        "downloads": 27
    }]
