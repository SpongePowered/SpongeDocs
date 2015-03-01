=================
Creating Commands
=================

The Sponge API contains a robust set of command interfaces in order to allow developers to specify command handling.

Writing a Command
=================

The first step is to create a class for the command. The class has to implement the interface ``CommandCallable``:

.. code-block:: java

    package example.command;

    import org.spongepowered.api.util.command.CommandCallable;

    public class MyBroadcastCommand implements CommandCallable {

        // Body of the command goes here

    }

Your IDE should be throwing a couple of errors at you, so let's keep going.

Describing Your Command
~~~~~~~~~~~~~~~~~~~~~~~

Now that we have our command class, let's start describing our command.
This is one of the most important functions of a command (other than its actual function), because
Commands are associated with three strings describing their usage:

The Usage String
----------------

The usage string is an extremely short description of the command, describing sub-commands, arguments, and other properties.
It is displayed when a command is run incorrectly, or when asked for explicitly.

It typically looks something like this:

.. code-block:: none

  /sponge conf [-g] [-d dim] [-w world] key value

The usage string is the only required property, and is specified by implementing the :code:`getUsage()` method.

The Short Description
---------------------

The short description is a description of the command that is typically one sentence long.
It is usually displayed next to the command in a help listing.
Unlike the usage string, it is written in English. Also unlike the usage string, it is optional.

The short description is specified by implementing the :code:`getShortDescription()` method.
If your command does not provide a description, use :code:`Optional.absent()`:

.. code-block:: java

  package example.command;

  import com.google.common.base.Optional;

  public Optional<String> getShortDescription() {
      return Optional.absent();
  }

And if it provides a description, create an Optional using :code:`Optional.of()`:

.. code-block:: java

  package example.command;

  import com.google.common.base.Optional;

  public Optional<String> getShortDescription() {
      return Optional.of("Description goes here");
  }

The Help Text
-------------

The help text is a full description of your command and how to use it.
It is shown when :code:`/help yourcommand` is run. Like the short description, it is optional.

Specifying the help text is similar to how the short description is specified:

.. code-block:: java

    package example.command;

    import com.google.common.base.Optional;

    public Optional<String> getHelp() {
        return Optional.of(
            "My very long help text which is extremely informative and allows
            people to use the command effectively goes here");
    }

Command Permissions
~~~~~~~~~~~~~~~~~~~

Almost every useful command should be restricted by permissions somehow -- a server owner doesn't want
the average Bob or Alice to run the `/fireball` or `/broadcast` commands, for obvious reasons.

Testing for permissions is required by the CommandCallable interface via the `testPermission` method:

.. code-block:: java

    package example.command;

    import org.spongepowered.api.util.command.CommandSource;

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("example.mycommand");
    }

.. tip::

    To learn how to use the Permissions API with more of its features, please see the :doc:`permissions` guide.

Command Example
===============

The following is a full-featured command example:

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

        private final Optional<String> desc = Optional.of(
            "Displays a message to all players");

        private final Optional<String> help = Optional.of(
            "Displays a message to all players. It has no color support!");

        public MyBroadcastCommand(Server server) {
            this.server = server;
        }

        // Called when a CommandSource (player, console, ...) executes the command
        public boolean call(CommandSource source, String arguments,
            List<String> parents) throws CommandException {

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
        public List<String> getSuggestions(CommandSource source, String arguments)
            throws CommandException {

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

Registering the Command
=======================

Now we can register the class in the ``CommandService``. The ``CommandService`` stands as the manager for watching what commands get typed into chat, and redirecting them to the right command handler.
To register your command, use the method ``CommandService.register()``, passing your plugin, an instance of the command, and any needed aliases as parameters.

.. code-block:: java

    CommandService cmdService = game.getCommandDispatcher();
    cmdService.register(plugin, new MyBroadcastCommand(server), "message", "broadcast");

.. note::

    The arguments after the new instance of your command are the aliases to register for the command. You can add as many Strings as you want.
    The first alias that isn't used by another command becomes the primary alias. This means aliases used by another command are ignored.
