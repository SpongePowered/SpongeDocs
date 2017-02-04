================================
Implementation-dependent Plugins
================================

There are various reasons for bypassing SpongeAPI and accessing the internal Minecraft implementation directly:

- A part of SpongeAPI is not implemented yet. In this case you can temporarily bypass the API in your plugins. However,
  in most cases the better option is attempting to contribute the missing implementation to Sponge so other plugin
  developers can profit from the implemented API.
- You need access to an implementation-dependent feature which is not supported by SpongeAPI (on purpose).
- You want to optimize or customize the implementation specifically for your server.

.. warning::
    Depending on special implementation features will make your plugin only work on the implementation you build it
    against (and likely also only on a specific version). Unless you are certain that accessing the implementation is
    necessary we highly recommend building plugins only against SpongeAPI.

.. note::
    The following articles assume you build your plugin for SpongeVanilla/SpongeForge. The plugin will not be usable on
    any other implementation.

SpongeVanilla and SpongeForge use MCP as development environment for the internal Minecraft code. Continue at :doc:`mcp`
for a short overview about MCP or continue directly with :doc:`mcp-setup` for an introduction about using MCP in
plugins.

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    mcp
    mcp-setup
    access-transformers
    mixins
    implementation
