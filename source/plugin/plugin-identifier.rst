==================
Plugin Identifiers
==================

Sponge plugins are identified using a unique **plugin ID**, and a human-readable **plugin name**. It is important that
you choose a proper name for your project now, because later plugins will interact with your plugin under your chosen
plugin ID (e.g. when defining plugin dependencies). The plugin ID is also used for creating the default configuration
folders for your plugin.

.. note::
    Plugin IDs must be lowercase and may only contain characters from a-z, dashes, underscores or dots. It cannot end
    with a dash, underscore or dot. The plugin name does **not** have such a limitation and can even contain spaces or
    special characters.

Keep in mind your plugin ID will be the main identification of your plugin, used in other plugins as dependencies, for
your configuration files, as well as other properties stored for your plugin. That's why it is important you always
choose a proper plugin ID directly, because changing it again later will be difficult.

Continue at :doc:`plugin-class` for an introduction how to set up your plugin class.
