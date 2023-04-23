==============
Child Commands
==============

.. javadoc-import::
    org.spongepowered.api.command.Command.Builder
    org.spongepowered.api.command.Command.CommandExecutor
    org.spongepowered.api.command.exception.ArgumentParseException

The :javadoc:`Command.Builder` supports hierachical command structure like this:

- ``/mail`` (parent command)
    - ``/main send`` (child command)
    - ``/main read`` (child command)

Every child command is a seperate ``Command`` and can be cheated in the same way as a regular command is.

.. code-block:: java

    import org.spongepowered.api.command.Command;

    Command.Paramertized readCmd = Command
        .builder()
        .permission("myplugin.mail.read")
        .shortDescription(Component.text("Read your inbox"))
        .executor(...)
        .build();

    Command.Paramertized sendCmd = Command
        .builder()
        .permission("myplugin.mail.send")
        .shortDescription(Component.text("Send a mail"))
        .addParameter(...)
        .executor(...)
        .build();

Instead of registering them to the event, child commands are registered on their parent command using the 
:javadoc:`Command.Builder#child(Command, String, String...)` method. They are registered with a list of aliases. 
The first alias supplied is the primary one and will appear in the usage message.

.. code-block:: java

    Command.Paramertized mailCommand = Command.builder()
        .permission("myplugin.mail.base")
        .shortDescription(Component.text("Send and receive mails"))
        .child(readCmd, "read", "r", "inbox")
        .child(sendCmd, "send", "s", "write")
        .build();

Fallback Behavior
=================

If a command has child commands, a :javadoc:`CommandExecutor`, set through 
:javadoc:`Command.Builder#executor(CommandExecutor)` and the parameters for the executor are set to optional. 
The behavior of error in selection and argument parsing of child commands is dependent on whether this parent 
executor exists.

If a parent exector has not been set, then if the requested child command could not be found or the arguments cannot 
be parsed, then an :javadoc:`ArgumentParseException` will be thrown.

.. warning::

    If a ``ArgumentParseException`` is unhandled, then the Minecraft brigader system will tell the user that the 
    command doesn't exist.

If the child executor throws an exception, the fallback executor is not executed and the error message from the child 
executor is displayed.
