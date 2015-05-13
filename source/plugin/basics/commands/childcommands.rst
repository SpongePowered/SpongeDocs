==============
Child Commands
==============

The ``CommandSpec`` builder supports hierarchical command structures like this:

* ``/mail`` (parent command)

  * ``/mail send`` (child command)
  * ``/mail read`` (child command)

Every child command is a separate ``CommandSpec`` with a list of aliases. 
The specification of the child commands must be stored in a ``Map``:

.. code-block:: java

    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.util.command.spec.CommandSpec;

    HashMap<List<String>, CommandSpec> subcommands = new HashMap<>();

    // /mail read
    subcommands.put(Arrays.asList("read", "r", "inbox"), CommandSpec.builder()
            .setPermission("myplugin.mail.read")
            .setDescription(Texts.of("Read your inbox"))
            .setExecutor(...)
            .build());

    // /mail send
    subcommands.put(Arrays.asList("send", "s", "write"), CommandSpec.builder()
            .setPermission("myplugin.mail.send")
            .setDescription(Texts.of("Send a mail"))
            .setArguments(...)
            .setExecutor(...)
            .build());

Use the ``setChildren()`` method of the parent command builder to apply the child command map: 

.. code-block:: java 
    
    CommandSpec mailCommandSpec = CommandSpec.builder()
            .setPermission("myplugin.mail")
            .setDescription(Texts.of("Send and receive mails"))
            .setChildren(subcommands)
            .build();
            
    game.getCommandDispatcher().register(plugin, mailCommandSpec, "mail", "email");
            
.. note::

    If a ``CommandExecutor`` was set for the parent command, it is used as a fallback if the arguments do not match one of the child command aliases.
    Setting an executor is not required.
