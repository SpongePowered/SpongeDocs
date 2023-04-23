===============
Command Manager
===============

.. javadoc-import::
    org.spongepowered.api.command.CommandManager
    org.spongepowered.api.event.lifecycle.RegisterCommandEvent


The :javadoc:`CommandManager` stands as the manager for watching what commands get typed into chat, and redirecting them
to the right command handler. To register your commands, use the :javadoc:`RegisterCommandEvent` as shown on 
:doc:`./commandbuilding`.

The ``CommandManager`` can also be used to call a command programmatically.

.. code-block:: java

    cmdManager.process(player, "msg Notch hi notch!");

You can also send a command from the server console

.. code-block:: java

    cmdManager.process(Sponge.systemSubject(), "kill Notch");

You can also send a command as a player, but with server console permissions.

.. code-block:: java

    cmdManager.process(Sponge.systemSubject(), player, "kill Nitch");