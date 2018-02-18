==============
Child Commands
==============

.. javadoc-import::
    org.spongepowered.api.command.CommandCallable
    org.spongepowered.api.command.args.CommandElement
    org.spongepowered.api.command.spec.CommandExecutor
    org.spongepowered.api.command.spec.CommandSpec
    org.spongepowered.api.command.spec.CommandSpec.Builder
    org.spongepowered.api.command.args.ArgumentParseException
    java.lang.String

The :javadoc:`CommandSpec` builder supports hierarchical command structures like this:

* ``/mail`` (parent command)

  * ``/mail send`` (child command)
  * ``/mail read`` (child command)

Every child command is a separate ``CommandSpec`` and can be created in the same way a regular command is.

.. code-block:: java

    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.command.spec.CommandSpec;

    // /mail read
    CommandSpec readCmd = CommandSpec.builder()
        .permission("myplugin.mail.read")
        .description(Text.of("Read your inbox"))
        .executor(...)
        .build();

    // /mail send
    CommandSpec sendCmd = CommandSpec.builder()
        .permission("myplugin.mail.send")
        .description(Text.of("Send a mail"))
        .arguments(...)
        .executor(...)
        .build();

Instead of registering them to the command service, child commands are registered on their parent command using the
:javadoc:`CommandSpec.Builder#child(CommandCallable, String...)` method. They are registered with a list of aliases.
The first alias supplied is the primary one and will appear in the usage message.

.. code-block:: java

    import org.spongepowered.api.Sponge;

    CommandSpec mailCommandSpec = CommandSpec.builder()
        .permission("myplugin.mail")
        .description(Text.of("Send and receive mails"))
        .child(readCmd, "read", "r", "inbox")
        .child(sendCmd, "send", "s", "write")
        .build();

    Sponge.getCommandManager().register(plugin, mailCommandSpec, "mail", "email");

Fallback Behavior
=================

If a command has child commands, a :javadoc:`CommandExecutor`, set through
:javadoc:`CommandSpec.Builder#executor(CommandExecutor)` and their associated
:javadoc:`CommandSpec.Builder#arguments(CommandElement)` are optional. The behavior of error in selection and
argument parsing of child commands is dependent on whether this parent executor exists.

If a parent executor has not been set, then if the requested child command could not be found or the arguments
cannot be parsed, then an :javadoc:`ArgumentParseException` will be thrown.

If a parent executor has been set for the parent command, it is used as a fallback if the first argument does
not match one of the child command aliases. If a child command is selected but the arguments do not parse, one of
the following will happen based on what :javadoc:`CommandSpec.Builder#childArgumentParseExceptionFallback(boolean)`
is set to:

* If `true` (the default), the :javadoc:`ArgumentParseException` is discarded and the arguments from the parent
  commands are parsed. If they fail, the exception for the parent command will be displayed. This is the same
  behavior as previous API revisions, where child command argument parsing exceptions will not be displayed.
* If `false`, the parent executor is not executed and the :javadoc:`ArgumentParseException` is thrown, returning
  the exception from the child command argument that failed to parse, but may prevent some combination of parent
  commands and arguments from being executed (if the first argument of the fallback could be the same as the
  child command).

In all cases, if the arguments parse succesfully but the child executor throws an exception, the fallback
executor (if any) is not executed and the error message from the child executor is displayed.
