=====================
List Project Versions
=====================

**GET /projects/:pluginId/versions**

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
    }]
