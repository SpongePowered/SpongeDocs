===============
Plugin Metadata
===============

.. javadoc-import::
    org.spongepowered.api.plugin.Dependency

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

You can define the additional (optional) plugin metadata on your ``@Plugin`` annotation. You can provide all meta
information in the annotation, despite only the id being required. A ``@Plugin`` annotation which uses all fields
available looks like this:

.. code-block:: java

    import org.spongepowered.api.plugin.Plugin;

    @Plugin(id = "myplugin", name = "My Plugin", version = "1.0",
        description = "This is a very cool plugin I made for me",
        url = "http://example.com",
        authors = {"Spongie", "FLARD"},
        dependencies = {@Dependency(id = "otherplugin", optional = true)})

For every ``@Dependency`` you provide, you may also give a ``version`` attribute specifying a version range according to
:javadoc:`Dependency#version()`. The ``optional`` attribute specifies that your plugin may be loaded even if the
dependency is not available. Unless explicitly specified as optional, the absence of any dependency will prevent your
plugin from being loaded.

.. note::
    For both ``authors`` and ``dependencies``, the curly brackets may be left out if only one element is supplied, e.g.
    ``dependencies = @Dependency(id = "otherplugin", optional = true)`` in the above example.


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
        ],
        "dependencies": [
            "otherplugin"
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

If you're using Gradle, `SpongeGradle <https://github.com/SpongePowered/SpongeGradle>`_ will provide additional integration for Gradle and
plugin metadata. For example, it will automatically include the project version defined in the Gradle build script in
your plugin metadata. See :doc:`project/gradle` for details.

.. _Ore: https://github.com/SpongePowered/Ore
.. _JSON: https://en.wikipedia.org/wiki/JSON

.. note::
    If you're using the NetBeans IDE make sure you've unchecked the _Compile_ _On_ _Save_ option under
    _Project_ _Properties_ > _Build_ > _Compile_ to make sure the mcmod.info file will be generated correctly. 
