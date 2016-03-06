=========================
Setting Up Your Workspace
=========================

This section shows the basic setup for your IDE, to get yourself ready to develop Sponge plugins.

Choosing a Plugin Identifier
=============================

Sponge plugins are identified using an unique **plugin ID**, and a human-readable **plugin name**. It is important that
you choose a proper name for your project now, because later plugins will interact with your plugin under your chosen
plugin ID (e.g. when defining plugin dependencies). The plugin ID is also used for creating the default configuration
folders for your plugin.

.. note::
    Plugin IDs must be lowercase and only contain characters from a-z, dashes, underscores or dots. It cannot end with a
    dash, underscore or dot. The plugin name does **not** have such a limitation and can even contain spaces or special
    characters.

While it is valid to use a plugin ID like ``exampleplugin`` we recommend **qualified plugin IDs** for public plugins.
Qualifying your plugin ID with a **group ID** ensures there are no conflicts between plugins, even if they would
normally have the same plugin ID.

The recommended format for **group IDs** follows the `naming conventions of Java packages
<http://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html>`_. If you own a domain (for example
``example.com``), the group ID you would use would be ``com.example``. However, in the event that you do not own a
domain, a common practice is to use an email address (such as ``com.gmail.username``) or an open-source repository (such
as ``io.github.username``).

The resulting **plugin ID** should be prefixed with the group ID separated by a **dot**. A good example for a qualified
plugin ID would be ``io.github.username.myplugin`` or ``com.example.myplugin`` if you own the ``example.com`` domain.

Setting Up Your IDE
===================

.. toctree::
    :maxdepth: 2
    :titlesonly:

    idea
    eclipse
    netbeans
