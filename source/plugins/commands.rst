====================
Command Registration
====================

Create a command
================

The first step is to create a class for the command. The class has to implement ``CommandCallable``:

.. code-block:: java

    package com.github.maenni025.spongeExample;

    import java.util.Collections;
    import java.util.List;

    import org.spongepowered.api.Server;
    import org.spongepowered.api.text.message.Messages;
    import org.spongepowered.api.util.command.CommandCallable;
    import org.spongepowered.api.util.command.CommandException;
    import org.spongepowered.api.util.command.CommandSource;

    import com.google.common.base.Optional;

    public class MyBroadcastCommand implements CommandCallable {

        private final Server server;
        private final Optional<String> desc = Optional.of("Displays a message to all players");
        private final Optional<String> help = Optional.of("Displays a message to all players. It has no color support!");

        public MyBroadcastCommand(Server server) {
            this.server = server;
        }

        public boolean call(CommandSource source, String arguments, List<String> parents) throws CommandException {
            server.broadcastMessage(Messages.of(arguments));
            return true;
        }

        public boolean testPermission(CommandSource source) {
            return source.hasPermission("example.exampleCommand");
        }

        public Optional<String> getShortDescription() {
            return desc;
        }

        public Optional<String> getHelp() {
            return help;
        }

        public String getUsage() {
            return "/<command> <message>";
        }

        public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
            return Collections.emptyList();
        }

    }


Register the Command
====================

Now we can register the class in the CommandService

.. code-block:: java

    CommandService cmdService = game.getCommandDispatcher();
    cmdService.register(plugin, new MyBroadcastCommand(server), "message", "broadcast");

The command is now registered.
The last argument are the aliases for the command. You can add as many Strings as you want.
If an alias is used by another command it is ignored. The first alias, which isn't used by another command, becomes the primary alias.
