================   
Argument Parsing
================

The Command Builder API comes with a powerful argument parser. 
It converts the string input to java base types (integers, booleans, strings) or game objects (players, worlds, block types , ...). 
The parser supports optional arguments and flags. It also handles TAB completion of arguments.

The parsed arguments are stored in the ``CommandContext`` object. 
If the parser returns a single object, obtain it with ``args.<T>getOne(String key)`` (``T`` is the value type). 
Optional and weak arguments may return ``Optional.absent()``.

Many of the parsers may return more than one object (e.g. multiple players with a matching username).
In that case, you must use the ``args.<T>getAll(String key)`` method to get the ``Collection`` of possible matches. 
**Otherwise, the context object will throw an exception!**

.. tip::

   You can use the ``onlyOne`` element to limit the amount of returned values to a single one, so you can safely use ``args.<T>getOne(String key)``.

To create a new ``CommandElement`` (argument), use the ``GenericArguments`` factory class. 
Many command elements require a short text key, which is displayed in error and help messages.

Apply the ``CommandElement`` to the command builder with the ``setArguments()`` method.
It is possible to pass more than one ``CommandElement`` to the method, thus chaining multiple arguments (e.g ``/msg <player> <msg>``). This has the same effect as wrapping the ``CommandElement`` objects in a ``GenericArguments.seq()`` element.

Example: Building a Command with Multiple Arguments
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.entity.player.Player;
    import org.spongepowered.api.util.command.CommandException;
    import org.spongepowered.api.util.command.CommandResult;
    import org.spongepowered.api.util.command.CommandSource;
    import org.spongepowered.api.util.command.args.CommandContext;
    import org.spongepowered.api.util.command.args.GenericArguments;
    import org.spongepowered.api.util.command.spec.CommandExecutor;
    import org.spongepowered.api.util.command.spec.CommandSpec;

    CommandSpec myCommandSpec = CommandSpec.builder()
            .setDescription(Texts.of("Send a message to a player"))
            .setPermission("myplugin.command.message")

            .setArguments(
                    GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), this.game)),
                    GenericArguments.remainingJoinedStrings(Texts.of("message")))

            .setExecutor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    Player player = args.<Player>getOne("player").get();
                    String message = args.<String>getOne("message").get();

                    player.sendMessage(Texts.of(message));

                    return CommandResult.success();
                }
            })
            .build();
            
    game.getCommandDispatcher().register(plugin, myCommandSpec, "message", "msg", "m");
    

Overview of the ``GenericArguments`` command elements
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. _catalog type: spongepowered.github.io/SpongeAPI/org/spongepowered/api/CatalogTypes.html

+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| Command Element            | Description                                                                             | Value Type & Amount           |
+============================+=========================================================================================+===============================+
| ``none``                   | Expects no arguments. This is the default behavior of a ``CommandSpec``.                |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| **Java Base Types**                                                                                                                                  |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``string``                 | Require an argument to be a string.                                                     | one ``String``                |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``remainingJoinedStrings`` | Concatenates all remaining arguments separated by spaces (useful for message commands). | one ``String``                |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``bool``                   | Require an argument to be a boolean.                                                    | one ``Boolean``               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``integer``                | Require an argument to be an integer.                                                   | one ``Integer``               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| **Game Objects**                                                                                                                                     |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``player``                 | Expect an argument to represent an online player. **May return multiple players!**      | multiple ``Player`` instances |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``playerOrSource``         | Like ``player``, but returns the sender of the command if no matching player was found. | multiple ``Player`` instances |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``world``                  | Expect an argument to represent a world (also includes unloaded worlds).                | multiple ``WorldProperties``  |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``dimension``              | Expect an argument to represent a dimension (``END``, ``NETHER``, ``OVERWORLD``).       | multiple ``DimensionType``    |
|                            |                                                                                         | instances                     |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``location``               | Expect an argument to represent a ``Location``.                                         | one ``Location``              |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``vector3d``               | Expect an argument to represent a ``Vector3d``.                                         | one ``Vector3d``              |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``catalogedElement``       | Expect an argument that is a member of the specified `catalog type`_.                   | multiple matching elements    |
|                            |                                                                                         | of the specified catalog type |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| **Matchers**                                                                                                                                         |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``choices``                | Return an argument that allows selecting from a limited set of values.                  | one specified value           |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``literal``                | Expect a literal sequence of arguments (e.g. ``"i", "luv", "u"``: ``/cmd i luv u``).    | one specified value           |
|                            | Throws an error if the arguments do not match.                                          |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``enumValue``              | Require the argument to be a key under the provided enum.                               | multiple matching elements    |
|                            |                                                                                         | of the specified enum         |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| **Utilities**                                                                                                                                        |
|                                                                                                                                                      |
| Can be wrapped around command elements. The value type is inherited from the wrapped element.                                                        |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``seq``                    | Builds a sequence of command elements (e.g. ``/cmd <arg1> <arg2> <arg3>``).             | inherited                     |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``repeated``               | Require a given command element to be provided a certain number of times.               | multiple inherited            |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``allOf``                  | Require all remaining args to match the provided command element.                       | multiple inherited            |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``optional``               | Make the provided command element optional. Throws an error if the argument             | inherited                     |
|                            | is of invalid format and there are no more args.                                        |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``optionalWeak``           | Make the provided command element optional. Does not throw an error if the argument     | inherited                     |
|                            | is of invalid format and there are no more args.                                        |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``firstParsing``           | Returns a command element that matches the first of the provided elements that parses   | inherited                     |
|                            | (useful for command overloading, e.g. ``/settime <day|night|<number>>``).               |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``onlyOne``                | Restricts the given command element to only insert one value into the context at the    | inherited                     |
|                            | provided key.                                                                           |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``flags``                  | Returns a builder for command flags (e.g. ``/cmd [-a] [-b <value>]``).                  | Short Flag: one ``Boolean``   |
|                            |                                                                                         |                               |
|                            | See :doc:`Advanced Command Arguments <../../advanced/commands/arguments>`               | Long Flag: one ``String``     |
|                            |                                                                                         |                               |
|                            |                                                                                         | Value Flag: inherited         |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+

.. tip::

    See the `documentation for GenericArguments <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/util/command/args/GenericArguments.html>`_ 
    for more information.

.. tip::

    It is possible to create custom command elements (e.g. an URL parser or a ``Vector2i`` element). The procedure is described on
    :doc:`this page <../../advanced/commands/arguments>` 
