===================
The Command Manager
===================

.. javadoc-import::
    org.spongepowered.api.command.CommandCallable
    org.spongepowered.api.command.CommandManager
    org.spongepowered.api.event.game.state.GameInitializationEvent
    java.lang.Object
    java.lang.String

The :javadoc:`CommandManager` stands as the manager for watching what commands get typed into chat, and redirecting
them to the right command handler. To register your command, use the method
:javadoc:`CommandManager#register(Object, CommandCallable, String...)` passing your plugin, an instance of the command,
and any needed aliases as parameters.

Usually you want to register your commands when the :javadoc:`GameInitializationEvent` is called. If you are registering
the commands from the main plugin class, use ``this`` as the ``plugin`` parameter.

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.command.CommandManager;

    CommandManager cmdManager = Sponge.getCommandManager();
    cmdManager.register(this, myCommandSpec, "alias1", "alias2", "alias3");

.. note::

    The arguments after the new instance of your command are the aliases to register for the command. You can add as
    many Strings as you want. The first alias that isn't used by another command becomes the primary alias. This means
    aliases used by another command are ignored.

The ``CommandManager`` can also be used to call a command programatically:

.. code-block:: java

    cmdManager.process(player, "msg Notch hi notch!");

You can also send a command from the server console:

.. code-block:: java

    cmdManager.process(Sponge.getServer().getConsole(), "kill Notch");
