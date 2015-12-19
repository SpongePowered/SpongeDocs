==============
Child Commands
==============

The ``CommandSpec`` builder supports hierarchical command structures like this:

* ``/mail`` (parent command)

  * ``/mail send`` (child command)
  * ``/mail read`` (child command)

Every child command is a separate ``CommandSpec`` and can be created in the same way a regular command is.

.. code-block:: java

    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.command.spec.CommandSpec;

    // /mail read
    CommandSpec readCmd = CommandSpec.builder()
        .permission("myplugin.mail.read")
        .description(Texts.of("Read your inbox"))
        .executor(...)
        .build();

    // /mail send
    CommandSpec sendCmd = CommandSpec.builder()
        .permission("myplugin.mail.send")
        .description(Texts.of("Send a mail"))
        .arguments(...)
        .executor(...)
        .build();

Instead of registering them to the command service, child commands are registered on their parent command using the
``child()`` method. They are registered with a list of aliases. The first alias supplied is the primary one and will
appear in the usage message.

.. code-block:: java

    CommandSpec mailCommandSpec = CommandSpec.builder()
        .permission("myplugin.mail")
        .description(Texts.of("Send and receive mails"))
        .child(readCmd, "read", "r", "inbox")
        .child(sendCmd, "send", "s", "write")
        .build();

    game.getCommandManager().register(plugin, mailCommandSpec, "mail", "email");

.. note::

    If a ``CommandExecutor`` was set for the parent command, it is used as a fallback if the arguments do not match
    one of the child command aliases. Setting an executor is not required.
