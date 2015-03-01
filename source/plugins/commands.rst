=================
Creating Commands
=================

Writing a command
=================

The first step is to create a class for the command. The class has to implement the interface ``CommandCallable``:

.. code-block:: java

    package example.command;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Collections;
    import java.util.List;

    import org.spongepowered.api.Server;
    import org.spongepowered.api.text.message.Message;
    import org.spongepowered.api.text.message.Messages;
    import org.spongepowered.api.util.command.CommandCallable;
    import org.spongepowered.api.util.command.CommandException;
    import org.spongepowered.api.util.command.CommandSource;

    import com.google.common.base.Optional;

    // Simple command that sends a message to all players: /broadcast <message>
    public class MyBroadcastCommand implements CommandCallable {

        private final Server server;

        private final Optional<String> desc = Optional.of("Displays a message to all players");
        private final Optional<String> help = Optional.of("Displays a message to all players. It has no color support!");

        public MyBroadcastCommand(Server server) {
            this.server = server;
        }

        // Called when a CommandSource (player, console, ...) executes the command
        public boolean call(CommandSource source, String arguments, List<String> parents) throws CommandException {

            // Creates a message from the command arguments
            Message message = Messages.of(arguments);

            // Sends the message to all players
            this.server.broadcastMessage(message);

            // Indicates that the command was successful
            return true;
        }

        // Tests if the CommandSource is permitted to run this command
        public boolean testPermission(CommandSource source) {
            return source.hasPermission("example.broadcast");
        }

        // Displayed when a player uses /help, if the help system supports it
        public Optional<String> getShortDescription() {
            return desc;
        }

        // A longer help text
        public Optional<String> getHelp() {
            return help;
        }

        // A string explaining the arguments of the command
        public String getUsage() {
            return "<message>";
        }

        // A list of suggestions when a player presses TAB to complete the command
        public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {

            // Checks if a player entered "h" or "H"
            if (arguments.equalsIgnoreCase("H")) {

                // Create a list of suggestions starting with "H"
                ArrayList<String> suggestions = new ArrayList<String>();
                suggestions.add("Hi!");
                suggestions.add("Hello World!");

                // Return the list
                return suggestions;

            } else {
                // Otherwise return no suggestions
                return Collections.emptyList();
            }
        }
    }

.. _documentation for CommandCallable: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/service/command/CommandService.html
.. tip::

    See the `documentation for CommandCallable`_ for the purposes of each method in this example.

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
