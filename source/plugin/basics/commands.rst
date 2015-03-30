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

The CommandCallable Lifecycle
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Unlike what you might expect, CommandCallable *does not* represent a specific invocation of a command.
Instead, it is instantiated once and passed into a command service, which then calls it every time that command is invoked.
(We will get to registering your command towards the end of this tutorial.)

A lot of commands require holding an instance to the server when performing their behavior.
We can do this by adding it as a constructor parameter and private variable:

.. code-block:: java

    private final Server server;

    public MyBroadcastCommand(Server server) {
        this.server = server;
    }

This private server variable will be referenced later on.


Describing Your Command
~~~~~~~~~~~~~~~~~~~~~~~

Now that we have our command class set up, let's start describing our command.
This is one of the most important functions of a command (other than its actual behavior), because it describes the command, what it does,
and how to use it.
Commands are associated with three strings describing their usage:

The Usage String
----------------

The usage string is an extremely short description of the command, describing sub-commands, arguments, and other properties.
It is displayed when a command is run incorrectly, or when asked for explicitly.

It typically looks something like this:

.. code-block:: none

  /sponge conf [-g] [-d dim] [-w world] key value

The usage string is the only required property, and is specified by implementing the :code:`getUsage()` method:

.. code-block:: java

  public Optional<String> getShortDescription() {
      return Optional.absent();
  }

The Short Description
---------------------

The short description is a description of the command that is typically one sentence long.
It is usually displayed next to the command in a help listing.
Unlike the usage string, it is written in English. Also unlike the usage string, it is optional.

The short description is specified by implementing the :code:`getShortDescription()` method.
If your command does not provide a description, use :code:`Optional.absent()`:

.. code-block:: java

  public Optional<String> getUsage() {
      return Optional.absent();
  }

And if it provides a description, create an Optional using :code:`Optional.of()`:

.. code-block:: java

  public Optional<String> getShortDescription() {
      return Optional.of("Description goes here");
  }

The Help Text
-------------

The help text is a full description of your command and how to use it.
It is shown when :code:`/help yourcommand` is run. Like the short description, it is optional.

Specifying the help text is similar to how the short description is specified:

.. code-block:: java

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

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("example.mycommand");
    }

.. tip::

    To learn how to use the Permissions API with more of its features, please see the :doc:`permissions` guide.

Command Behavior
~~~~~~~~~~~~~~~~

Finally, let's get to the meat of our command -- the behavior.
To do this, we implement the `call` method on CommandCallable for the behavior of the command.

Since this command doesn't do much, our implementation is very simple:

.. code-block:: java

    public boolean call(CommandSource source, String arguments,
        List<String> parents) throws CommandException {

        Message message = Messages.of(arguments);
        this.server.broadcastMessage(message);

        return true;
    }

We simply make a message out of the passed-in arguments and broadcast it to the server.

The first parameter given to `call` is the CommandSource -- this can be a player,
the console, a command block, or a remote source (Rcon or some developer-defined source).
The second parameter is a string of the arguments passed into the command.

The final parameter is a list of parents to the command, starting with the root command.
Most commands don't worry about this, so it is out of scope for this tutorial.

.. tip::

    If you wish to learn about command parenting in Sponge, see the guide.

    .. TODO: add the relevant guide.

Command Exceptions
~~~~~~~~~~~~~~~~~~

A command throws CommandException by default. You can use this error to throw messages related to how the
command is handled, for instance, if the console attempts to run a command that only a player can run.

Here's the command exceptions already present in the Sponge API:

- **CommandException**: The base class for all exceptions, represents an exception related to command handling.
- **InvocationCommandException**: 

Be careful about throwing CommandException; if something goes wrong with the actual execution of your command,
you most likely need to throw another exception,
like an InvalidArgumentsException.

Finally, the command returns a boolean indicating whether it passed or failed.
It is not possible to return a boolean if an exception was thrown, Sponge will assume the command failed because of the exception.

Completion Suggestions
~~~~~~~~~~~~~~~~~~~~~~

While a user is typing your command, they may press "tab" in order to try to autocomplete some parts of it, in order to speed up entering the command.
We can customize this by implementing the `getSuggestions` method, like so:

.. code-block:: java

    public List<String> getSuggestions(CommandSource source, String arguments)
        throws CommandException {

        if (arguments.equalsIgnoreCase("H")) {

            ArrayList<String> suggestions = new ArrayList<String>();
            suggestions.add("Hi!");
            suggestions.add("Hello World!");

            return suggestions;

        } else {
            return Collections.emptyList();
        }
    }

Like the `call` method, `getSuggestions` also takes a CommandSource and a string for the arguments.
The CommandSource is exactly the same as `call` -- the source of the command.
The arguments are almost the same, except they aren't the completed arguments like `call`, they are whatever
the CommandSource has currently typed in. Keep this in mind when writing your autocomplete code.

Also like `call`, `getSuggestions` throws a CommandException something related to command handling fails.
Again, be sure to throw this exception only when necessary and use different or more specific classes for other, unrelated exceptions.

Return a list of strings as suggestions, or `Collections.emptyList()`.

Registering the Command
=======================

Now we can register the class in the ``CommandService``.
The ``CommandService`` stands as the manager for watching what commands get typed into chat, and redirecting them to the right command handler.
To register your command, use the method ``CommandService.register()``, passing your plugin, an instance of the command, and any needed aliases as parameters.

.. code-block:: java

    CommandService cmdService = game.getCommandDispatcher();
    cmdService.register(plugin, new MyBroadcastCommand(server), "message", "broadcast");

.. note::

    The arguments after the new instance of your command are the aliases to register for the command. You can add as many Strings as you want.
    The first alias that isn't used by another command becomes the primary alias. This means aliases used by another command are ignored.
