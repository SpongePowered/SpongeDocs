==================
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
| ``setArguments``           | Sets the argument specification for this command (See :doc:`arguments`).                                |                              
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setPermission``          | Set the permission that will be checked before using this command.                                      |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setDescription``         | A short, one-line description of this command's purpose that will be displayed by the help system.      |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setExtendedDescription`` | Sets an extended description to use in longer help listings. Will be appended to the short description. |
+----------------------------+---------------------------------------------------------------------------------------------------------+
| ``setChildren``            | Sets the child commands of this command with their aliases (See :doc:`childcommands`).                  |
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
* The command context object, which contains the parsed arguments (See :doc:`arguments`)

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
    
.. tip::

    You can use `anonymous classes <https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html>`_ to define the command executor in the command build process (See the example in the :doc:`arguments` page).

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
    
    The easiest solution for this is to append a ``playerOrSource`` command element (See :doc:`arguments`).


Command Results
===============

The ``CommandExecutor::execute()`` method must always return a ``CommandResult``. In most cases it is sufficient to return ``CommandResult.success()`` if the command was successful or ``CommandResult.empty()`` if it wasn't.
In cases where more information needs to be conveyed, a ``CommandResult.builder()`` should be used. It provides the methods ``affectedBlocks()``, ``affectedEntities()``, ``affectedItems()``, ``queryResult()`` and ``successCount()`` methods, each accepting an integer and setting the attribute of the same name. All attributes that are not set by the builder will be absent.

Command blocks can use those values to modify scoreboard stats, which then can be used for elaborate constructions consisting of multiple command blocks. A tutorial how the data is accessed can be found `here <https://minecraft.gamepedia.com/Tutorials/Command_stats>`_.

Example: Building a CommandResult
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java
	
	CommandResult result = CommandResult.builder().affectedEntities(42).successCount(1).build();
	
This example uses a builder to create a ``CommandResult`` for a command which affected 42 entities and was successful.

Error Handling
==============

The ``execute()`` method may also throw a ``CommandException``, signaling that an error occured while trying to execute the command. If such an Exception is thrown, its message will be displayed to the command source, formatted as an error. Also, the commands usage message will be displayed.
An ``ArgumentParseException``, a subtype of ``CommandException`` is automatically thrown if the commands arguments could not be parsed.
