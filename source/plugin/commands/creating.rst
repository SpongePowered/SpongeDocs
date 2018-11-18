==================
Building a Command
==================

.. javadoc-import::
    org.spongepowered.api.command.CommandException
    org.spongepowered.api.command.CommandResult
    org.spongepowered.api.command.CommandSource
    org.spongepowered.api.command.args.ArgumentParseException
    org.spongepowered.api.command.args.CommandContext
    org.spongepowered.api.command.spec.CommandExecutor
    org.spongepowered.api.command.spec.CommandSpec
    org.spongepowered.api.command.spec.CommandSpec.Builder

The first step is to get a new :javadoc:`CommandSpec` builder. The builder provides methods to modify the command help
messages, command arguments and the command logic. These methods can be chained.

To finally build the command, you'll want to call the :javadoc:`CommandSpec.Builder#build()` method.

After that, you have to register the command.

Example: Building a Simple Command
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.command.spec.CommandSpec;

    CommandSpec myCommandSpec = CommandSpec.builder()
        .description(Text.of("Hello World Command"))
        .permission("myplugin.command.helloworld")
        .executor(new HelloWorldCommand())
        .build();

    Sponge.getCommandManager().register(plugin, myCommandSpec, "helloworld", "hello", "test");

Overview of the CommandSpec builder methods
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

+-------------------------+---------------------------------------------------------------------------------------------------------+
| Method                  | Description                                                                                             |
+=========================+=========================================================================================================+
| ``executor``            | Defines the command logic (See `Writing a Command Executor`_).                                          |
|                         |                                                                                                         |
|                         | **Setting the executor is required** if no child commands are set.                                      |
+-------------------------+---------------------------------------------------------------------------------------------------------+
| ``arguments``           | Sets the argument specification for this command (See :doc:`arguments`).                                |
+-------------------------+---------------------------------------------------------------------------------------------------------+
| ``permission``          | Set the permission that will be checked before using this command.                                      |
+-------------------------+---------------------------------------------------------------------------------------------------------+
| ``description``         | A short, one-line description of this command's purpose that will be displayed by the help system.      |
+-------------------------+---------------------------------------------------------------------------------------------------------+
| ``extendedDescription`` | Sets an extended description to use in longer help listings. Will be appended to the short description. |
+-------------------------+---------------------------------------------------------------------------------------------------------+
| ``child``               | Adds a child command to this command with its aliases (See :doc:`childcommands`).                       |
+-------------------------+---------------------------------------------------------------------------------------------------------+
| ``children``            | Sets the child commands of this command with their aliases (See :doc:`childcommands`).                  |
+-------------------------+---------------------------------------------------------------------------------------------------------+
| ``inputTokenizer``      | Defines how the arguments will be parsed. By default, the parser splits the command input by spaces.    |
|                         | Quotations count as a single argument.                                                                  |
|                         |                                                                                                         |
|                         | Example: ``/tpworld Notch "My World"`` would result in two arguments: ``Notch`` and ``My World``.       |
+-------------------------+---------------------------------------------------------------------------------------------------------+
| ``build``               | Builds the command. After that, you have to register the command.                                       |
+-------------------------+---------------------------------------------------------------------------------------------------------+

Writing a Command Executor
==========================

The only required component to build a simple command is the command executor class, which contains the logic of the
command.

The class has to implement the :javadoc:`CommandExecutor` interface, which defines the
:javadoc:`CommandExecutor#execute(CommandSource, CommandContext)` method. The method is called on command execution and
has two arguments:

* The source of the command call (e.g. the console, a command block or a player)
* The command context object, which contains the parsed arguments (See :doc:`arguments`)

Example: Simple Command Executor
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.command.CommandException;
    import org.spongepowered.api.command.CommandResult;
    import org.spongepowered.api.command.CommandSource;
    import org.spongepowered.api.command.args.CommandContext;
    import org.spongepowered.api.command.spec.CommandExecutor;

    public class HelloWorldCommand implements CommandExecutor {

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            src.sendMessage(Text.of("Hello World!"));
            return CommandResult.success();
        }
    }

.. tip::

    You can use `anonymous classes <https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html>`_ to
    define the command executor in the command build process (See the example in the :doc:`arguments` page).

Player-Only Commands
~~~~~~~~~~~~~~~~~~~~

Sometimes it is necessary that only players can execute a command (e.g. a ``/suicide`` command).

Perform an ``instanceof`` check to determine the type of the :javadoc:`CommandSource`:

.. code-block:: java

    import org.spongepowered.api.entity.living.player.Player;
    import org.spongepowered.api.command.source.CommandBlockSource;
    import org.spongepowered.api.command.source.ConsoleSource;

    if (src instanceof Player) {
        Player player = (Player) src;
        player.sendMessage(Text.of("Hello " + player.getName() + "!"));
    }
    else if(src instanceof ConsoleSource) {
        src.sendMessage(Text.of("Hello GLaDOS!"));
        // The Cake Is a Lie
    }
    else if(src instanceof CommandBlockSource) {
        src.sendMessage(Text.of("Hello Companion Cube!"));
        // <3
    }

.. note::

    We recommend you to add an optional ``[player]`` argument to make the command **console-friendly** (e.g. ``/suicide
    [player]``).

    The easiest solution for this is to append a ``playerOrSource`` command element (See :doc:`arguments`).


Command Results
===============

The ``CommandExecutor#execute()`` method must always return a :javadoc:`CommandResult`. In most cases it is sufficient
to return :javadoc:`CommandResult#success()` if the command was successful or :javadoc:`CommandResult#empty()` if it
wasn't. In cases where more information needs to be conveyed, a :javadoc:`CommandResult#builder()` should be used. The
builder provides the several various methods that accepts an integer and will set the attribute of the same name. All
attributes that are not set by the builder will be empty.

Command blocks can use those values to modify scoreboard stats, which then can be used for elaborate constructions
consisting of multiple command blocks. A tutorial how the data is accessed can be found
`here <https://minecraft.gamepedia.com/Tutorials/Command_stats>`_.

Example: Building a CommandResult
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    CommandResult result = CommandResult.builder()
        .affectedEntities(42)
        .successCount(1)
        .build();

This example uses a builder to create a ``CommandResult`` for a command which affected 42 entities and was successful.

Error Handling
==============

The ``execute()`` method may also throw a :javadoc:`CommandException`, signaling that an error occurred while trying to
execute the command. If such an Exception is thrown, its message will be displayed to the command source, formatted as
an error. Also, the commands usage message will be displayed. An :javadoc:`ArgumentParseException`, a subtype of
``CommandException`` is automatically thrown if the commands arguments could not be parsed.
