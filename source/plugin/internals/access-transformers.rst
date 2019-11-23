===================
Access Transformers
===================

Since some parts of the Minecraft code were not designed to be used from the outside, you may find yourself in a
situation in which you need to access a field or method that is not public. While you would normally use reflection to
access the field or method, MCP will make this more difficult since you have two different names - the MCP names in the
development environment and the Searge names in production.

For example to access the method ``tick()`` using reflection you would need to use ``tick`` in the development
environment, but ``func_71217_p`` in production. The re-obfuscation step only handles direct references to methods
and fields, not the string parameter passed to the reflection call.

As a solution, ForgeGradle supports using access transformers (or AT) that automatically make the specified
methods/fields public so you can reference them directly (without reflection). While they are primarily intended for
usage with the Minecraft code base, they can be also applied to classes from other projects. If configured in the JAR
manifest of the plugin, SpongeVanilla and Forge will also apply them in production.

Setup
-----
ForgeGradle will automatically scan for access transformer files with the file name suffix ``_at.cfg`` in your resource
folders. To be able to use the access transformer at runtime, you need to add them to a ``META-INF`` folder in your
resource directory, for example ``META-INF/myplugin_at.cfg``.

There are 3 different types of access transformers: you can change the modifiers of classes, fields and methods.
An access transformer line is defined by 2 parts (for classes) or 3 parts (for fields and methods), each separated by a
space.

- The access type you want to change the method/field to, e.g. ``public`` or ``protected``. To remove ``final`` from a
  field, append ``-f`` after the access type, e.g. ``public-f``.
- Full qualified class name, e.g. ``net.minecraft.server.MinecraftServer``
- For fields and methods: Searge field name or method name and method signature, e.g. ``field_54654_a`` or ``func_4444_a()V``

.. tip::
    You can add comments by prefixing them with ``#``. A good convention is to add the MCP name after each access
    transformer line so you know which field/method the line is referring to.

Here are two examples for access transformer lines:

::

    public-f net.minecraft.server.MinecraftServer field_71308_o # anvilFile
    public net.minecraft.server.MinecraftServer func_71260_j()V # stopServer
    public-f net.minecraft.item.ItemStack

To apply the access transformers to your development environment, run the Gradle ``setupDecompWorkspace`` task again and
refresh your Gradle project:

.. code-block:: bash

    gradle setupDecompWorkspace

.. tip::
    You can use the `MCP bot <http://mcpbot.bspk.rs/help>`_ which is present in the MCP and Sponge IRC channels,
    or in the ``#bot-spam`` channel on Discord, to quickly get the access transformer line for a field or method.
    After looking up a method using ``!gm <mcp method name>`` or a field using ``!gf <mcp field name>``,
    simply copy the listed ``AT`` line to your access transformer file.

.. note::
    Making a field/method less accessible (e.g. ``public`` -> ``private``) is not supported.

Production
``````````
To apply the access transformers in production, you need to add a ``FMLAT`` manifest entry to your plugin with the file
name of your access transformer in the ``META-INF`` directory.

.. code-block:: groovy

    jar {
        manifest.attributes('FMLAT': 'myplugin_at.cfg')
    }
