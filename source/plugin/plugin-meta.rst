===============
Plugin Metadata
===============

Adding plugin metadata helps users to identify your plugin more easily by giving them the ability to check the name,
version, description, or even the authors of a plugin at runtime. It will also be used when publishing plugins on a plugin
portal like Ore_.

Currently, Sponge supports the following types of plugin metadata:

* Plugin ID, Plugin Name, Version
* Description
* URL (e.g. Website)
* Authors
* Plugin dependencies

Plugin annotation
=================

You can define the additional (optional) plugin metadata on your ``@Plugin`` annotation:

.. code-block:: java

    import org.spongepowered.api.plugin.Plugin;

    @Plugin(id = "myplugin", name = "My Plugin", version = "1.0",
        description = "This is a very cool plugin I made for me",
        authors = {"Spongie", "FLARD"},
        url = "http://example.com")


File metadata
=============

Additionally to plugin metadata defined in the plugin annotation we also recommend plugins to include this metadata in
an extra file in the JAR, which has several advantages:

* Easier integration for build systems (e.g. contributing the version from the build system into plugin metadata)
* Easier for plugin portals to obtain the plugin metadata (no parsing of class files necessary)

.. note::
    **We strongly recommend public plugins to include file metadata.** Plugin portals such as Ore_ may require
    file metadata. See `Using the Annotation Processor`_ for a simple way to generate it. The
    implementation may print a warning if a plugin is missing file metadata.

The mcmod.info format
~~~~~~~~~~~~~~~~~~~~~

For Sponge plugins, we use a file called ``mcmod.info``, which is included in the root of your plugin JAR. The format
originates from Forge, and has been used by several projects in the Minecraft community, so we have chosen to use the same.

``mcmod.info`` is basically a simple JSON_ file which defines the plugin metadata as simple fields. Here is an example
file that could be used by a Sponge plugin:

.. code-block:: json

    [{
        "modid": "myplugin",
        "name": "My Plugin",
        "version": "1.0",
        "description": "This is a very cool plugin I made for me",
        "authorList": [
            "Spongie",
            "FLARD"
        ]
    }]

Using the Annotation Processor
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Writing an extra file is quite annoying. Fortunately, usually there is nothing extra you need to do. When compiling
your plugin, SpongeAPI is able to generate this file automatically based on the information provided in your
``@Plugin`` annotation.

Enabling
--------

If you're using a build system such as Gradle or Maven and have not explicitly disabled annotation processing there is
nothing extra you need to do. By default the annotation processor will automatically run and generate a ``mcmod.info``
file based on the contents of your ``@Plugin`` annotation.

If you're not using a build system you need to manually enable annotation processing.

Build system integration
------------------------

If you're using Gradle, `SpongeGradle <start/project/gradle>`_ will provide additional integration for Gradle and
plugin metadata. For example, it will automatically include the project version defined in the Gradle build script in
your plugin metadata. See :doc:`project/gradle` for details.

.. _Ore: https://github.com/SpongePowered/Ore
.. _JSON: https://en.wikipedia.org/wiki/JSON
