=====================
Low-Level Command API
=====================

.. javadoc-import::
    org.spongepowered.api.command.CommandCallable
    org.spongepowered.api.command.CommandManager
    org.spongepowered.api.command.dispatcher.Dispatcher
    org.spongepowered.api.command.dispatcher.SimpleDispatcher
    java.lang.Object
    java.lang.String

The :javadoc:`CommandCallable` and :javadoc:`Dispatcher` interfaces can be used to define commands. The interfaces can
be used as a base for custom command APIs.

It is recommended to use the :doc:`Command Builder API <creating>` for simple command definitions.

Writing a command
=================

The first step is to create a class for the command. The class has to implement the interface ``CommandCallable``:

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.command.CommandCallable;
    import org.spongepowered.api.command.CommandException;
    import org.spongepowered.api.command.CommandResult;
    import org.spongepowered.api.command.CommandSource;

    import java.util.Collections;
    import java.util.List;
    import java.util.Optional;

    public class MyBroadcastCommand implements CommandCallable {

        private final Optional<Text> desc = Optional.of(Text.of("Displays a message to all players"));
        private final Optional<Text> help = Optional.of(Text.of("Displays a message to all players. It has no color support!"));
        private final Text usage = Text.of("<message>");

        public CommandResult process(CommandSource source, String arguments) throws CommandException {
            Sponge.getServer().getBroadcastChannel().send(Text.of(arguments));
            return CommandResult.success();
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

    See the JavaDoc for :javadoc:`CommandCallable` for the purposes of each method in this example.

Registering the command
=======================

Now we can register the class in the :javadoc:`CommandManager`. The ``CommandManager`` stands as the manager for
watching what commands get typed into chat, and redirecting them to the right command handler.
To register your command, use the method :javadoc:`CommandManager#register(Object, CommandCallable, String...)`,
passing your plugin, an instance of the command, and any needed aliases as parameters.

.. code-block:: java

    import org.spongepowered.api.command.CommandManager;

    CommandManager cmdService = Sponge.getCommandManager();
    cmdService.register(plugin, new MyBroadcastCommand(), "message", "broadcast");

.. note::

    The arguments after the new instance of your command are the aliases to register for the command. You can add as many
    Strings as you want. The first alias that isn't used by another command becomes the primary alias. This means aliases
    used by another command are ignored.

Command Dispatchers
===================

Command dispatchers can be used to create hierarchical command structures (subcommands).

The default implementation of the ``Dispatcher`` interface is the :javadoc:`SimpleDispatcher` class.

A ``Dispatcher`` is also a ``CommandCallable``, so it can be registered like any other command.

.. code-block:: java

     import org.spongepowered.api.command.dispatcher.SimpleDispatcher;

     CommandCallable subCommand1 = ...;
     CommandCallable subCommand2 = ...;

     SimpleDispatcher rootCommand = new SimpleDispatcher();

     rootCommand.register(subCommand1, "subcommand1", "sub1");
     rootCommand.register(subCommand2, "subcommand2", "sub2");

     Sponge.getCommandManager().register(this, rootCommand, "root");
