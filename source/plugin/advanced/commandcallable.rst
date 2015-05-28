=====================
Low-Level Command API
=====================

The ``CommandCallable`` and ``Dispatcher`` interfaces can be used to define commands. 
The interfaces can be used as a base for custom command APIs.

It is recommended to use the `Command Builder API <../../basics/commands>`_ for simple command definitions.

Writing a command
=================

The first step is to create a class for the command. The class has to implement the interface ``CommandCallable``:

.. code-block:: java

    import java.util.Collections;
    import java.util.List;
    
    import org.spongepowered.api.Server;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.util.command.CommandCallable;
    import org.spongepowered.api.util.command.CommandException;
    import org.spongepowered.api.util.command.CommandResult;
    import org.spongepowered.api.util.command.CommandSource;
    
    import com.google.common.base.Optional;
    
    public class MyBroadcastCommand implements CommandCallable {
    
        private final Optional<Text> desc = Optional.of((Text) Texts.of("Displays a message to all players"));
        private final Optional<Text> help = Optional.of((Text) Texts.of("Displays a message to all players. It has no color support!"));
        private final Text usage = (Text) Texts.of("<message>");
        
        private final Server server;
    
        public MyBroadcastCommand(Server server) {
            this.server = server;
        }
    
        public Optional<CommandResult> process(CommandSource source, String arguments) throws CommandException {
            server.broadcastMessage(Texts.of(arguments));
            return Optional.of(CommandResult.success());
        }
    
        public boolean testPermission(CommandSource source) {
            return source.hasPermission("myplugin.broadcast");
        }
    
        public Optional<Text> getShortDescription(CommandSource source) {
            return desc;
        }
    
        public Optional<Text> getHelp(CommandSource source) {
            return help;
        }
    
        public Text getUsage(CommandSource source) {
            return usage;
        }
    
        public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
            return Collections.emptyList();
        }
    }

.. tip::

    See the `documentation for CommandCallable <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/service/command/CommandService.html>`_ for the purposes of each method in this example.

Registering the command
=======================

Now we can register the class in the ``CommandService``. The ``CommandService`` stands as the manager for watching what commands get typed into chat, and redirecting them to the right command handler.
To register your command, use the method ``CommandService.register()``, passing your plugin, an instance of the command, and any needed aliases as parameters.

.. code-block:: java

    CommandService cmdService = game.getCommandDispatcher();
    cmdService.register(plugin, new MyBroadcastCommand(server), "message", "broadcast");

.. note::

    The arguments after the new instance of your command are the aliases to register for the command. You can add as many Strings as you want.
    The first alias that isn't used by another command becomes the primary alias. This means aliases used by another command are ignored.
    
Command Dispatchers
===================

Command dispatchers can be used to create hierarchical command structures (subcommands).

The default implementation of the ``Dispatcher`` interface is the ``SimpleDispatcher`` class.

A ``Dispatcher`` is also a ``CommandCallable``, so it can be registered like any other command.

.. code-block:: java

     CommandCallable subCommand1 = ...;
     CommandCallable subCommand2 = ...;
     
     SimpleDispatcher rootCommand = new SimpleDispatcher();
     
     rootCommand.register(subCommand1, "subcommand1", "sub1");
     rootCommand.register(subCommand2, "subcommand2", "sub2");
     
     game.getCommandDispatcher().register(this, rootCommand, "root");
