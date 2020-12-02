==============
Command Causes
==============

.. javadoc-import::
    net.kyori.adventure.audience.Audience
    net.kyori.adventure.identity.Identified
    net.kyori.adventure.text.Component
	org.spongepowered.api.Cause
	org.spongepowered.api.command.Command
	org.spongepowered.api.command.CommandCause
    org.spongepowered.api.command.CommandManager
    org.spongepowered.api.event.cause.Cause
    org.spongepowered.api.event.cause.EventContext
    org.spongepowered.api.event.cause.EventContextKeys
    org.spongepowered.api.service.permission.Subject

In SpongeAPI 8 and later, the :javadoc:`CommandCause` represents what invoked a command. It is essentially a wrapper 
around the :javadoc:`Cause` object that provides additional methods to make inspecting the cause for command related
information easier.

.. note::
    For more information about the ``Cause`` object, what it is, how it works and how to interact with it, 
    :doc:`see this page on Causes<../event/causes>`.

Commands and Causes
===================

In previous versions of SpongeAPI, as well as in other well known APIs, a command could only be invoked by certain
users, such as a player, the console, or a RCON user. In the case of SpongeAPI 7 and earlier, the ``CommandSource``
interface was used to indicate who could invoke a command.

In SpongeAPI 8 and later, no such restrictions are placed on who can invoke a command. By bringing our cause system
to commands, developers can find out much more about who invoked a command, and why. For example, if a plugin,
reacting to an event where a sheep stood on a pressure plate, forces a player to run a command, the Cause might
contain a cause the contains the following items (from root to last)

* Player
* PluginContainer
* Pressure plate
* Sheep

In this scenario, while the player is indeed the direct invoker of the command, it quickly becomes clear that the 
command is being run because of a plugin! If a player directly runs a command, inspecting the cause will reveal
very few indirect causes. Plugins can make use of this information to alter their behaviour should it be needed.

The cause also contains :javadoc:`EventContext`. Any context that would ordinarily available on the cause will
now also be available for commands to consume. Some contexts provide additional information about the invocation
of a command, such as :javadoc:`EventContextKeys#COMMAND` that contains the raw string that was provided to the
command processor. The :javadoc:`EventContextKeys#SUBJECT` and :javadoc:`EventContextKeys#AUDIENCE` keys provide
ways to change how Sponge performs permission checks and sends messages about the command - allowing a level of
control not possible previously.

The CommandCause
----------------

The ``CommandCause`` object wraps around the ``Cause`` object and provides additional utilities useful for plugins
that have commands. As well as the standard ``Cause`` methods, the ``CommandCause`` also provides methods to
determine the :javadoc:`Subject` that permission checks should be made against, and the :javadoc:`Audience` that
messages should ultimately be sent to. The ``CommandCause`` is there to simplify development for plugin developers,
allowing for quick access to common actions.

Determining the Direct Invoker of a Command
===========================================

In previous versions of SpongeAPI, only the ``CommandSource`` was provided, and it was common practice to check
the type of this source. In SpongeAPI 8, plugins should instead inspect the root of the command cause using 
:javadoc:`CommandCause#root()` and perform a type check on the returned object, like so:

.. code-block:: java
    
    org.spongepowered.api.command.CommandCause
    org.spongepowered.api.entity.living.player.server.ServerPlayer

    final CommandCause commandCause = ...
    final Object root = commandCause.root();
    if (root instanceof ServerPlayer) {
        ...
    }

.. warning::
    When checking the cause of a player, you should generally prefer using ``root`` over ``first``. Using ``first``
    may not yield expected results, for example, if the console executes a command in respose to a player doing 
    something, you may miss that information as the player is not the root.

    The direct invoker of a command will **always** be the root.

Permission Checks and Sending Messages
======================================

While the ``CommandCause`` provides more flexibility about what caused a command to be invoked, the loss of a specific
``CommandSource`` interface does raise the following questions:

* Which :javadoc:`Subject` should plugins perform permission checks against?
* Which :javadoc:`Audience` should messages from the command be sent?

The ``CommandCause`` itself solves these problems by providing the following:

* The ``CommandCause`` is itself a ``Subject``, permission checks should be peformed against the ``CommandCause`` 
  directly. Such checks will be passed through to the appropriate ``Subject`` found at :javadoc:`CommandCause#subject()`
* The ``CommandCause`` provides a :javadoc:`CommandCause#sendMessage(Identified, Component)` method to easily send a
  message to the intended recipient found at :javadoc:`CommandCause#audience()`

Outside of plugin owned command executors, where Sponge needs to perform a permission check or send a message as part
of a command invocation, it will use the ``Subject`` and ``Audience`` as stated above. 

.. note::
    It is **strongly** advised that you read the Javadocs for the various helper methods of the :javadoc:`CommandCause`
    so that you understand how subjects, audiences etc. are selected from the cause and context.

Summary
=======

When interacting with the ``CommandCause`` as part of a command, your plugin should:

* Use the root of the ``CommandCause`` as the target of a command;
* Perform permission checks on the ``CommandCause`` directly; and
* Send messages to the ``Audience`` on the ``CommandCause``, usually via the ``sendMessage`` method.