=============
Command Flags
=============

.. javadoc-import::
    org.spongepowered.api.command.args.CommandContext
    org.spongepowered.api.command.args.CommandElement
    org.spongepowered.api.command.args.CommandFlags.Builder
    org.spongepowered.api.command.args.GenericArguments
    org.spongepowered.api.text.Text
    java.lang.String

Command flags are useful for specifying extra parameters to be used for the processing of a command that doesn't belong
as a command argument.

To create a flag, we first need a builder for flags. We can simply use the :javadoc:`GenericArguments#flags()` method
provided by :javadoc:`GenericArguments` to obtain the builder we need. From there, we can specify what type of flag we
would like to create. Note that flags are specified as an argument.

.. code-block:: java
    
    import org.spongepowered.api.command.args.GenericArguments;
    import org.spongepowered.api.command.spec.CommandSpec;
    
    CommandSpec myCommand = CommandSpec.builder()
        .executor(new MyCommand())
        .arguments(GenericArguments.flags().flag("s").buildWith(GenericArguments.none()))
        .build();

This will create a command flag, so that when the player performs ``/our-command -s``, the flag for ``s`` will be true.
Note that building with :javadoc:`GenericArguments#none()` will prevent the command from having any arguments. If you
wish for the command to have arguments and flags, you will need to specify your arguments within the
:javadoc:`CommandFlags.Builder#buildWith(CommandElement)` method.

Now that we have specified that our command may be run with the flag, we can now get the value of the flag. For a
simple boolean flag like the one we have specified above, we can simply just check if it exists. In the example below,
we are checking if the :javadoc:`CommandContext` for the command has a value for ``s``.

.. code-block:: java
    
    import org.spongepowered.api.text.Text;
    
    if (args.hasAny("s")) {
        src.sendMessage(Text.of("The command flag s was specified!"));
    }

Permission Flags
================

Our flags so far are great, but what if we wanted to have it so that a player needs a permission to use a flag? We can
instead use the :javadoc:`CommandFlags.Builder#permissionFlag(String, String...)` method on our flag builder. This will
require the player to have a specific permission to run the command with the flag. An example of this is below:

.. code-block:: java
    
    CommandSpec myCommand = CommandSpec.builder()
            .executor(new MyCommand())
            .arguments(GenericArguments.flags().permissionFlag("myplugin.command.flag",
                "s").buildWith(GenericArguments.none()))
            .build();

If a player does not have the permission ``myplugin.command.flag``, then they will not be allowed to execute our
command with the command flag ``s``.

Value Flags
===========

Booleans can be great, but what if we wanted flags for things such as strings or integers? This is where value flags
come into play. We simply need to use the :javadoc:`CommandFlags.Builder#valueFlag(CommandElement, String...)` method
on our flag builder. Using the ``valueFlag()`` method, we can specify the type of flag we want to create, such as an
integer or string. Creating an integer value flag can be done like so:

.. code-block:: java
    
    CommandSpec myCommand = CommandSpec.builder()
            .executor(new MyCommand())
            .arguments(GenericArguments.flags().valueFlag(GenericArguments
                    .integer(Text.of("value")), "s").buildWith(GenericArguments.none()))
            .build();

You may replace :javadoc:`GenericArguments#integer(Text)` with any other flag type you would like to specify, such as
:javadoc:`GenericArguments#string(Text)`.

Now to retrieve the flag value from our command, we can simply treat it like any other command argument. We simply need
to check if it exists before retrieving it:

.. code-block:: java
    
    import java.util.Optional;
    
    Optional<Integer> optional = args.<Integer>getOne("value");
    if (optional.isPresent()) {
        int value = optional.get().intValue();
    } else {
        src.sendMessage(Text.of("The value flag was not specified."));
    }

Long Flags
==========

As an alternative to short flags like the ones we have been using above, we can also use long flags. Using a long flag,
you can specify a value along with the flag using equals in the command. To create a long flag, simply prefix your
normal flag with a dash ``-``, like so:
    
.. code-block:: java

    CommandSpec myCommand = CommandSpec.builder()
            .executor(new MyCommand())
            .arguments(GenericArguments.flags().flag("-myflag")
                .buildWith(GenericArguments.none()))
            .build();
    
We can retrieve the value that was specified with our flag similarly to value flags:

.. code-block:: java
    
    Optional<String> optional = args.<String>getOne("myflag");
    if (optional.isPresent()) {
        String value = optional.get();
    }

So if a user runs ``/our-command --myflag=Flag_Value``, the ``Flag_Value`` will be stored in the string ``value``.

Unknown Flag Behavior
=====================

Now what if we didn't specify a specific flag to go along with our command, but still wanted to accept unknown flags?
We can set the unknown flag behavior of our command to achieve this:

.. code-block:: java
    
    import org.spongepowered.api.command.args.CommandFlags;
    
    CommandSpec myCommand = CommandSpec.builder()
                .executor(new MyCommand())
                .arguments(GenericArguments.flags()
                        .setUnknownShortFlagBehavior(
                            CommandFlags.UnknownFlagBehavior.ACCEPT_VALUE)
                        .buildWith(GenericArguments.none()))
                .build();

Using this, we can specify that any short flag with a specified value will be accepted. Without this, attempting to use
an unknown flag will throw an exception. Some of the possible unknown flag behaviors are ``ERROR``,
``ACCEPT_NONVALUE``, ``ACCEPT_VALUE``, and ``IGNORE``. Note that the default behavior for unknown flags is ``ERROR``.
