===============
Command Manager
===============

.. javadoc-import::
    org.spongepowered.api.command.manager.CommandManager
    org.spongepowered.api.event.lifecycle.RegisterCommandEvent


The :javadoc:`CommandManager` stands as the manager for watching what commands get typed into chat, and redirecting them
to the right command handler. To register your commands, use the :javadoc:`RegisterCommandEvent` as shown on 
:doc:`./commandbuilding`.

Command Execution
~~~~~~~~~~~~~~~~~

When executing a command Sponge splits the sender into three categories.

The first category is the ``permissions subject`` which is the target to
check permissions for. The second cateogory is the ``audience`` which is 
the target for all messages to be sent to. With the last category being 
the actual ``sender`` who is the object that sent the command.

.. warning::

    When executing a command programmatically, you need to specifiy the permissions
    subject and the audience but not the sender as by default the sender will be
    the permissions manager. 

    The command sender can be changed by pushing the new sender to the root of the
    cause stack.


Sending a Command As a Player
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    ServerPlayer player;

    Sponge.server().causeStackManager().pushCause(player);
    CommandManager cmdManager = Sponge.server().commandManager();
    cmdManager.process(player, "msg Notch hi notch!");

Sending a Command As a Console
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Sending a Command As a Console
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    SystemSubject console = Sponge.systemSubject();

    Sponge.server().causeStackManager().pushCause(console);
    CommandManager cmdManager = Sponge.server().commandManager();
    cmdManager.process(console, "kill Notch");

Sending a Command As a Player With Console Permissions
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    ServerPlayer player;
    SystemSubject console = Sponge.systemSubject();

    Sponge.server().causeStackManager().pushCause(player);
    CommandManager cmdManager = Sponge.server().commandManager();
    cmdManager.process(console, player, "kill Notch");