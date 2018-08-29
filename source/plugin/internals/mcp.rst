====================
MCP (Mod Coder Pack)
====================

The `Mod Coder Pack`_ (short MCP) was originally created as a collection of scripts, tools and mappings to make the
development of mods for Minecraft easier. Since Minecraft is not open-source and for the most part obfuscated,
development against it was painful since the original code was almost unreadable. MCP was designed as a workspace in
which developers can create mods using decompiled, deobfuscated (human-readable) Minecraft code.

Workflow
========
Using MCP adds additional steps to the development workflow of plugins, simplified below:

- Set up the MCP workspace
    - Download the Minecraft client/server files
    - Deobfuscate the code (changing obfuscated names into human-readable ones)
    - Decompile the code (generating source files from the binary classes)
- Create a plugin using the deobfuscated Minecraft source
- Re-obfuscate the plugin code so it can be used with the obfuscated code at runtime

Mappings
========
MCP uses two different sets of mappings which are applied separately during the workspace setup. The difference between
*Notch*, *Searge* and *MCP* mappings can be seen in the example below:

::

    // Notch
    boolean a(rw ☃);

    // Searge
    boolean func_72838_d(Entity p_72838_1_);

    // MCP
    boolean spawnEntityInWorld(Entity entityIn);

- **Notch mappings** are the original names in the obfuscated Minecraft binary. They change regularly with new
  Minecraft versions.
- **Searge mappings** contain unique names for all obfuscated methods, fields and parameters, as well as human-readable
  names for the classes. Unlike Notch mappings they usually stay the same across Minecraft updates unless the method
  signatures change. For SpongeVanilla and SpongeForge, they are also used in production (outside of your IDE).
- **MCP mappings** contain human-readable names largely contributed by the community. They are typically only used in
  the development environment, and then re-obfuscated to Searge or Notch mappings.

.. note::
    When you create a plugin, you work with *MCP mappings* in your development environment. To run the plugin in
    production (outside of your IDE) you need to re-obfuscate it to *Searge mappings*.

Using the MCPBot
----------------
The MCPBot_ is available in the Sponge and MCP IRC channels and allows you to lookup MCP mappings or contribute new
names. You can send commands to the bot by sending messages in one of the supported channels (e.g. ``#spongedev``).

.. tip::
    Check out the `MCPBot help page`_ for a list of all available commands.

Contributing new names
``````````````````````
You can also contribute names for class members which are still unnamed. Check out the
`MCPBot help page`_ for more instructions.

.. note::
    You cannot change existing names. If you would like to suggest changing an existing mapping, create
    a new issue on the `MCPBot issue tracker on GitHub <https://github.com/ModCoderPack/MCPBot-Issues/issues>`_.

.. seealso::

    `Mod Coder Pack`_
        Official website of the Mod Coder Pack.
    `MCPBot help page`_
        More information about using the MCPBot_.

.. _`Mod Coder Pack`: http://www.modcoderpack.com
.. _MCPBot: http://mcpbot.bspk.rs/
.. _`MCPBot help page`: http://mcpbot.bspk.rs/help
