=================
Creating Commands
=================

There are two different APIs to create commands in Sponge: The ``CommandCallable`` interface and the ``CommandSpec`` builder.

The most comfortable way to create a new command is the ``CommandSpec`` builder. It supports child commands and argument parsing.

Alternatively, you can use ``CommandCallable``, a lower-level interface which provides access to the raw command data. 
It is described on :doc:`this page <../advanced/commands/callable>`.

Building a Command
==================

The first step is to get a new ``CommandSpec`` builder. 
The builder provides methods to modify the command help messages, command arguments and the command logic. 
These methods can be chained. 

To finally build the command, call the ``build()`` method of the builder.

After that, you have to register the command.

Example: Building a Simple Command
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.util.command.spec.CommandSpec;

    CommandSpec myCommandSpec = CommandSpec.builder()
        .setDescription(Texts.of("Hello World Command"))
        .setPermission("myplugin.command.helloworld")
        .setExecutor(new HelloWorldCommand())
        .build();
        
    game.getCommandDispatcher().register(plugin, myCommandSpec, "helloworld", "hello", "test");

Overview of the ``CommandSpec`` builder methods
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

+----------------------------+---------------------------------------------------------------------------------------------------------+
| Method                     | Description                                                                                             |
+============================+=========================================================================================================+
| ``setExecutor``            | Defines the command logic (See `Writing a Command Executor`_).                                          |
|                            |                                                                                                         |
|                            | **Setting the executor is required** if no child commands are set.                                      |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setArguments``           | Sets the argument specification for this command (See `Argument Parsing`_).                             |                              
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setPermission``          | Set the permission that will be checked before using this command.                                      |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setDescription``         | A short, one-line description of this command's purpose that will be displayed by the help system.      |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setExtendedDescription`` | Sets an extended description to use in longer help listings. Will be appended to the short description. |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setChildren``            | Sets the child commands of this command with their aliases (See `Child Commands`_).                     |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setInputTokenizer``      | Defines how the arguments will be parsed. By default, the parser splits the command input by spaces.    |
|                            | Quotations count as a single argument.                                                                  |
|                            |                                                                                                         |
|                            | Example: ``/tpworld Notch "My World"`` would result in two arguments: ``Notch`` and ``My World``.       |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``build``                  | Builds the command. After that, you have to register the command.                                       |
+----------------------------+---------------------------------------------------------------------------------------------------------+

Writing a Command Executor
==========================

The only required component to build a simple command is the command executor class, which contains the logic of the command.

The class has to implement the ``CommandExecutor`` interface, which defines the ``execute`` method. 
The method is called on command execution and has two arguments:

* The source of the command call (e.g. the console, a command block or a player)
* The command context object, which contains the parsed arguments (See `Argument Parsing`_)

Example: Simple Command Executor
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.util.command.CommandException;
    import org.spongepowered.api.util.command.CommandResult;
    import org.spongepowered.api.util.command.CommandSource;
    import org.spongepowered.api.util.command.args.CommandContext;
    import org.spongepowered.api.util.command.spec.CommandExecutor;
    
    public class HelloWorldCommand implements CommandExecutor {
   
        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            src.sendMessage(Texts.of("Hello World!"));
            return CommandResult.success();
        }
    }
    
Player-Only Commands
~~~~~~~~~~~~~~~~~~~~

Sometimes it is neccessary that only players can execute a command (e.g. a ``/suicide`` command).

Perform an ``instanceof`` check to determine the type of the ``CommandSource``:

.. code-block:: java

    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.entity.player.Player;
    import org.spongepowered.api.util.command.source.ConsoleSource;
    import org.spongepowered.api.util.command.source.CommandBlockSource;

    if(src instanceof Player) {
        Player player = (Player) src; 
        player.sendMessage(Texts.of("Hello " + player.getName() + "!"));
    }
    else if(src instanceof ConsoleSource) {
        src.sendMessage(Texts.of("Hello GLaDOS!"));
        // The Cake Is a Lie
    }
    else if(src instanceof CommandBlockSource) {
        src.sendMessage(Texts.of("Hello Companion Cube!"));
        // <3
    }
    
.. note::

    We recommend you to add an optional ``[player]`` argument to make the command **console-friendly** (e.g. ``/suicide [player]``).
    
    The easiest solution for this is to append a ``playerOrSource`` command element as the last argument (See `Argument Parsing`_).
    
    
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
Use the ``GenericArguments.seq()`` element to chain multiple arguments (e.g ``/msg <player> <msg>``).

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

            .setArguments(GenericArguments.seq(
                    GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), this.game)),
                    GenericArguments.remainingJoinedStrings(Texts.of("message"))))

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
| ``player``                 | Expect an argument to represent an online player. **May return multiple players!**      | multiple ``Player``s          |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``playerOrSource``         | Like ``player``, but returns the sender of the command if no matching player was found. | multiple ``Player``s          |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``world``                  | Expect an argument to represent a world (also includes unloaded worlds).                | multiple ``WorldProperties``  |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``dimension``              | Expect an argument to represent a dimension (``END``, ``NETHER``, ``OVERWORLD``).       | multiple ``DimensionType``s   |
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
| Can be wrapped arround command elements. The value type is inherited from the wrapped element.                                                       |
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
|                            | See :doc:`Advanced Command Arguments <../advanced/commands/arguments>`                  | Long Flag: one ``String``     |
|                            |                                                                                         |                               |
|                            |                                                                                         | Value Flag: inherited         |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+

.. tip::

    See the `documentation for GenericArguments <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/util/command/args/GenericArguments.html>`_ 
    for more information.

.. tip::

    It is possible to create custom command elements (e.g. an URL parser or a ``Vector2i`` element). The procedure is described on
    :doc:`this page <../advanced/commands/arguments>` 

Child Commands
==============

The ``CommandSpec`` builder supports hierarchical command structures like this:

* ``/mail`` (parent command)
  * ``/mail send`` (child command)
  * ``/mail read`` (child command)

Every child command is a separate ``CommandSpec`` with a list of aliases. 
The specification of the child commands must be stored in a ``Map``:

.. code-block:: java

    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.util.command.spec.CommandSpec;

    HashMap<List<String>, CommandSpec> subcommands = new HashMap<>();

    // /mail read
    subcommands.put(Arrays.asList("read", "r", "inbox"), CommandSpec.builder()
            .setPermission("myplugin.mail.read")
            .setDescription(Texts.of("Read your inbox"))
            .setExecutor(...)
            .build());

    // /mail send
    subcommands.put(Arrays.asList("send", "s", "write"), CommandSpec.builder()
            .setPermission("myplugin.mail.send")
            .setDescription(Texts.of("Send a mail"))
            .setArguments(...)
            .setExecutor(...)
            .build());

Use the ``setChildren()`` method of the parent command builder to apply the child command map: 

.. code-block:: java 
    
    CommandSpec mailCommandSpec = CommandSpec.builder()
            .setPermission("myplugin.mail")
            .setDescription(Texts.of("Send and receive mails"))
            .setChildren(subcommands)
            .build();
            
    game.getCommandDispatcher().register(plugin, mailCommandSpec, "mail", "email");
            
.. note::

    If a ``CommandExecutor`` was set for the parent command, it is used as a fallback if the arguments do not match one of the child command aliases.
    Setting an executor is not required.
    
The Command Service
===================

The ``CommandService`` stands as the manager for watching what commands get typed into chat, and redirecting them to the right command handler.
To register your command, use the method ``CommandService.register()``, passing your plugin, an instance of the command, and any needed aliases as parameters.

Usually you want to register your commands when the ``PreInitializationEvent`` is called. 
If you are registering the commands from the main plugin class, use ``this`` as the ``plugin`` parameter.

.. code-block:: java

    import org.spongepowered.api.service.command.CommandService;
    
    CommandService cmdService = game.getCommandDispatcher();
    cmdService.register(this, myCommandSpec, "alias1", "alias2", "alias3");
    
.. note::

    The arguments after the new instance of your command are the aliases to register for the command. You can add as many Strings as you want.
    The first alias that isn't used by another command becomes the primary alias. This means aliases used by another command are ignored.
    
The ``CommandService`` can also be used to call a command programatically:

.. code-block:: java

    cmdService.process(player, "msg Notch hi notch!");
    
    
