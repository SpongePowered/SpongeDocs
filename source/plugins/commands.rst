.. External references.

.. _documentation for CommandCallable: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/util/command/CommandCallable.html
.. _documentation for CommandService: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/service/command/CommandService.html

=================
Creating Commands
=================

Writing a command
=================

The first step is to create a class for the command. The class has to implement the interface ``CommandCallable``:

.. tip::

    See the `documentation for CommandCallable`_ for the purposes of each method in this example.

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

Registering the command
=======================

Now we can register the class in the ``CommandService``. The ``CommandService`` stands as the manager for watching what commands get typed into chat, and redirecting them to the right command handler.
To register your command, use the method ``CommandService.register()``, passing your plugin, an instance of the command, and any needed aliases as parameters.

.. tip::

    See the `documentation for CommandService`_ for full information about the class, as well as its methods and their usage.

.. code-block:: java

    CommandService cmdService = game.getCommandDispatcher();
    cmdService.register(plugin, new MyBroadcastCommand(server), "message", "broadcast");

.. note::

    The arguments after the new instance of your command are the aliases to register for the command. You can add as many Strings as you want.
    The first alias that isn't used by another command becomes the primary alias. This means aliases used by another command are ignored.
