=============================
Migrating from API 7 to API 8
=============================

.. javadoc-import::
    org.spongepowered.plugin.builtin.jvm.Plugin

SpongeAPI 8 is a significally upgraded API compared to SpongeAPI 7, such that every plugin will need to be updated to
be compatible. While we cannot list every little thing that you will need to change here, this page contains some of
the more common migrations that will be required.

.. note::

    `We provide a plugin template that you can clone to create your own plugins 
    <https://github.com/SpongePowered/sponge-plugin-template> __`.
    While this guide is primarily intend for migrating plugins, you may find it useful to investigate this template to
    help your migration, particularly with the plugin metadata changes.


``@Plugin`` annotation and migration from ``mcmod.info`` to ``sponge_plugins.json``
===================================================================================

SpongeAPI 8 introduces a new :javadoc:`Plugin` annotation that only contains the plugin ID. As a result, the SpongeAPI
build no longer contains an annotation processor that will generate the metadata file for you.

To generate the metadata file, you can either:

* Create the file yourself by creating ``sponge_plugins.json`` in your resources root and filling it out with the required
  information
* Use SpongeGradle 2 and define the metadata in the buildscript `as in this example 
  <https://github.com/SpongePowered/sponge-plugin-template/blob/88d3c35853a687a7dc1540db43a9f9a135c03819/build.gradle.kts#L16-L40> __`

More information about the metadata file can be found at :doc:`plugin-meta` page