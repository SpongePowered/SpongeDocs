===============
Plugin Metadata
===============

.. note::
    The metadata described here is for plugins for SpongeAPI 8 or later. Plugins for version 7 should define their
    metadata in the ``@Plugin`` annotation

.. _Ore: https://github.com/SpongePowered/Ore

Every Sponge plugin must have an entry in a metadata file known as ``sponge-plugins.json``, which is found in the 
``META-INF`` directory. In most cases, a plugin JAR will contain this file, and the file will refer to one plugin.
However, it is possible for there to be multiple plugins in a JAR, and the metadata file can represent multiple plugins.

If you are using Gradle, SpongeGradle 2 makes it easy to define the metadata for your plugin. If you are creating a new
plugin, or you want a reference on how to use SpongeGradle 2, `take a look at our plugin template repository
<https://github.com/SpongePowered/sponge-plugin-template/blob/88d3c35853a687a7dc1540db43a9f9a135c03819/build.gradle.kts#L16-L40>`__
which provides an example configuration to use.

While much of the metadata is optional, adding it helps users to identify your plugin more easily by giving them the 
ability to check the name, version, description, or even the authors of a plugin at runtime. It will also be used when
publishing plugins on a plugin portal like Ore_.

Plugin annotation
~~~~~~~~~~~~~~~~~

You should define the ``@Plugin`` annotation on your plugin's main class, and supply its ID.

.. code-block:: java

    import org.spongepowered.api.plugin.Plugin;

    @Plugin(id = "myplugin")

Additonal metadata must be defined in the ``sponge_plugins.json`` file.


The sponge_plugins.json format
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. note::
    If you're using the SpongeGradle plugin, we strongly recommend that you use the metadata builder provided there.

The ``META-INF/sponge_plugins.json`` file, with one plugin, looks like this:

.. code-block:: json

    {
        "loader": {
            "name": "java_plain",
            "version": "1.0"
        },
        "license": "MIT",
        "global": {
            "version": "8.0.0",
            "links": {
                "homepage": "https://www.spongepowered.org",
                "source": "https://www.spongepowered.org/source",
                "issues": "https://www.spongepowered.org/issues"
            },
            "contributors": [
                {
                    "name": "SpongePowered",
                    "description": "Lead Developer"
                }
            ],
            "dependencies": [
                {
                    "id": "spongeapi",
                    "version": "8.0.0"
                }
            ]
        },
        "plugins": [
            {
                "id": "test",
                "name": "Test Plugin",
                "entrypoint": "org.spongepowered.test.TestPlugin",
                "description": "Manages the test plugins"
            }
        ]
    }

In general, your plugin json file won't change from this much. Anything in the "global" section can also be placed in
each plugin's metadata, which allows for overriding of values in complex, multi-plugin JAR files.

The following table explains each section of this file:

+-------------+---------------+-----------------------------------------------------------+
| Section     | Required      | Explanation                                               |  
+=============+===============+===========================================================+
| loader      | yes           | The loader that the plugin uses. In most cases, plugins   |
|             |               | will use the "java_plain" loader version "1.0"            |
+-------------+---------------+-----------------------------------------------------------+
| licence     | yes           | The licence that the JAR that contains the plugins falls  |
|             |               | under. Free text, but common choices are MIT and Apache 2 |
+-------------+---------------+-----------------------------------------------------------+
| mappings    | no            | The mappings that the code within the JAR may be written  |
|             |               | in. The format of this string should be in maven          |
|             |               | dependency format (group:artifact:version).               |
|             |               |                                                           |
|             |               | You should consult the mappings vendor to determine the   |
|             |               | correct form of this field if you need it. Standard       |
|             |               | Sponge plugins will not need to use mappings, and so this |
|             |               | field will be omitted in these cases                      |
+-------------+---------------+-----------------------------------------------------------+
| global      | no            | Attributes that apply to all plugins, but can be          |
|             |               | overridden on a per-plugin basis. See "plugin attributes" |
|             |               | below for a list of permissible values.                   |
|             |               |                                                           |
|             |               | If this block is not specified, all required values must  |
|             |               | be in **all** plugin blocks.                              |
+-------------+---------------+-----------------------------------------------------------+
| plugins     | yes, at least | A list of plugin objects with plugin specific metadata.   |
|             | one plugin is | See "plugin attributes" below for a list of permissible   |
|             | required      | values                                                    |
+-------------+---------------+-----------------------------------------------------------+

Plugin Attributes
----------------- 

Plugins may have the following attributes. Any attribute that is marked "global" may appear in the "global" section, and
can be overridden on a per plugin basis.

+--------------+---------------+---------------+-----------------------------------------------------------+
| Attribute    | Required      | Global        | Explanation                                               |  
+==============+===============+===============+===========================================================+
| id           | yes           | no            | The plugin ID.                                            |
+--------------+---------------+---------------+-----------------------------------------------------------+
| name         | no            | no            | The plugin's friendly name.                               |
+--------------+---------------+---------------+-----------------------------------------------------------+
| description  | no            | no            | A description of the plugin.                              |
+--------------+---------------+---------------+-----------------------------------------------------------+
| entrypoint   | yes           | no            | The plugin's entrypoint, which is the fully qualified     |
|              |               |               | name of the class that the plugin loader should try to    |
|              |               |               | instantiate and load (often referred to as the main       |
|              |               |               | class)                                                    |
+--------------+---------------+---------------+-----------------------------------------------------------+
| version      | yes           | yes           | The plugin version. Should be in SemVer format.           |
+--------------+---------------+---------------+-----------------------------------------------------------+
| links        | no            | yes           | Links to your project on the web. Contains up to three    |
|              |               |               | sub properties - all are optional:                        |
|              |               |               |                                                           |
|              |               |               | ``homepage``: the homepage for your plugin                |
|              |               |               |                                                           |
|              |               |               | ``source``: where to find the source code for the plugin  |
|              |               |               |                                                           |
|              |               |               | ``issues``: where users can report issues for the plugin  |
+--------------+---------------+---------------+-----------------------------------------------------------+
| branding     | no            | yes           | How your plugin is branded in the client. Contains two    |
|              |               |               | fields:                                                   |
|              |               |               |                                                           |
|              |               |               | ``logo``: the path to your plugin's logo                  |
|              |               |               |                                                           |
|              |               |               | ``icon``: the path to your plugin's icon                  |
+--------------+---------------+---------------+-----------------------------------------------------------+
| contributors | yes           | yes           | A list of contributors to your plugin. At least one is    |
|              |               |               | required with a name and description.                     |
+--------------+---------------+---------------+-----------------------------------------------------------+
| dependencies | no            | yes           | A list of dependencies that your plugin has. Contains the |
|              |               |               | following fields:                                         |
|              |               |               |                                                           |
|              |               |               | ``id``: the ID of the plugin to depend on. Required.      |
|              |               |               |                                                           |
|              |               |               | ``version``: The version, or a maven version range, that  |
|              |               |               | represents the versions this plugin depends on. Required. |
|              |               |               |                                                           |
|              |               |               | ``loadOrder``: "before" or "after", whether this plugin   |
|              |               |               | must load before or after the specified the plugin.       |
|              |               |               | Optional - if not defined, the loader will try to load    |
|              |               |               | the plugin at an implementation defined point.            |
+--------------+---------------+---------------+-----------------------------------------------------------+
