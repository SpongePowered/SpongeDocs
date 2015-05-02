=================
Creating Commands
=================

There are two different APIs to create commands in Sponge: The ``CommandCallable`` interface and the ``CommandSpec`` builder.

The most comfortable way to create a new command is the ``CommandSpec`` builder. It supports child commands and argument parsing.

Alternatively, you can use ``CommandCallable``, a lower-level interface which provides access to the raw command data. 
It is described on :doc:`this page <../advanced/commands>`.

Building a Command
==================

The first step is to get a new ``CommandSpec`` builder. 
The builder provides methods to modify the command help messages, command arguments and the command logic. 
These methods can be chained. 

To finally build the command, call the ``build()`` method of the builder.

Overview of the ``CommandSpec`` builder methods
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

+----------------------------+---------------------------------------------------------------------------------------------------------+
| Method                     | Description                                                                                             |
+============================+=========================================================================================================+
| ``setExecutor``            | Defines the command logic (See `Writing a Command Executor`_).                                          |
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
| ``build``                  | Builds the command. After that, you have to register the command (See `Registering the Command`_).      |
+----------------------------+---------------------------------------------------------------------------------------------------------+

Example: Building a Simple Command
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.util.command.spec.CommandSpec;
    import org.spongepowered.api.text.Texts;

    CommandSpec myCommandSpec = CommandSpec.builder()
        .setDescription(Texts.of("Hello World Command"))
        .setPermission("myplugin.command.helloworld")
        .setExecutor(new HelloWorldCommand())
        .build();

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

Sometimes it is necessary that only players can execute a command (e.g. a ``/suicide`` command).

Perform an ``instanceof`` check to determine the type of the ``CommandSource``:

.. code-block:: java

    if(src instance Player) {
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

Argument Parsing
================

Child Commands
==============
    
Registering the Command
=======================

Now we can register the class in the ``CommandService``. The ``CommandService`` stands as the manager for watching what commands get typed into chat, and redirecting them to the right command handler.
To register your command, use the method ``CommandService.register()``, passing your plugin, an instance of the command, and any needed aliases as parameters.

.. code-block:: java

    CommandService cmdService = game.getCommandDispatcher();
    cmdService.register(plugin, myCommandSpec, "message", "broadcast");
    
Usually you want to register your commands when the ``PreInitializationEvent`` is called.

.. note::

    The arguments after the new instance of your command are the aliases to register for the command. You can add as many Strings as you want.
    The first alias that isn't used by another command becomes the primary alias. This means aliases used by another command are ignored.
