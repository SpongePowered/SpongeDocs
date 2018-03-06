========================
Deploy Project Version
========================

**POST /api/v1/projects/:pluginId/versions/:version**

Deploys a new version for the specified project. The body should be sent as ``multipart/form-data`` with the fields 
shown below. Returns a JSON representation of the deployed version.

+-------------+------------------------------+----------------------------------------------------------------+
| Name        | Data Type                    | Description                                                    |
+=============+==============================+================================================================+
| apiKey      | String                       | An Ore deployment key obtained through the Ore settings.       |
+-------------+------------------------------+----------------------------------------------------------------+
| channel     | String                       | The channel the version should be in.                          |
+-------------+------------------------------+----------------------------------------------------------------+
| recommended | Boolean                      | If this version should be set as the recommended version.      |
|             |                              | Defaults to true.                                              |
+-------------+------------------------------+----------------------------------------------------------------+
| forumPost   | Boolean                      | Whether a forum post should be created or not. If no value is  |
|             |                              | specified, it will default to the project's setting. Optional. |
+-------------+------------------------------+----------------------------------------------------------------+
| changelog   | String                       | A changelog to include for this version. Optional.             |
+-------------+------------------------------+----------------------------------------------------------------+
| pluginFile  | File                         | The plugin file to upload.                                     |
+-------------+------------------------------+----------------------------------------------------------------+
| pluginSig   | File                         | A signature of the plugin file.                                |
+-------------+------------------------------+----------------------------------------------------------------+

**Example curl usage:**

.. code-block:: bash

    curl -F "apiKey=string" -F "apiKey=string" -F "channel=string" -F "recommended=boolean" \
    -F "forumPost=boolean" -F "changelog=string" -F pluginFile=@localPluginFile \
    -F pluginSig=@localpluginSig https://URL.org/api/v1/projects/:pluginId/versions/:version

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
