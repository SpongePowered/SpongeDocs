=========================
Custom Command Registrars
=========================

.. javadoc-import::
    org.spongepowered.api.command.Command
    org.spongepowered.api.command.registrar.CommandRegistrar
    org.spongepowered.api.command.registrar.CommandRegistrarType
    org.spongepowered.api.command.manager.Command.Mutable
    org.spongepowered.api.event.lifecycle.RegisterRegistryValueEvent.GameScoped

.. warning::

    Most plugin developers do not need to create their own :javadoc:`CommandRegistrar`. If you are using a non-Sponge
    native command framework, refer to their documentation on how to build and register your command. If you simply want
    to write commands for Sponge, see :doc:`our pages on parameterized comamnds instead<parameterized/index>`.

While the two command registration options are sufficient for most plugin developers, there are scenarios where these 
options may be too restrictive for you. In SpongeAPI 7 and earlier, those wanting to create their own command framework
would have to provide classes that inherted from ``CommandCallable``, or provide some abstraction that ultimately
creates ``CommandCallables``.

In SpongeAPI 8, while you can do this using ``Command.Raw``, ``CommandRegistrars`` are an alternative way to allow 
framework developers the flexibility to write their code with minimal reference to the Sponge provided interfaces, only
needing to write their own registrar that they can register their commands to, rather than make their commands conform
to the Sponge interfaces.

You could look into writing your own ``CommandRegistrar`` if:

* You are writing a command framework in a language other than Java and don't want to use any of the Sponge interfaces; or
* You are writing a cross-platform command framework and don't want to leak Sponge details to your consumer.

Creating your Registrar
=======================

.. warning::

    Registrars **must not be singletons**. They may be recreated at any time -- when recreated Sponge will not retain
    any command mappings.

To create and register your registrar, you must create the following:

* A base interface or class that your framework users must implement to create a command. This can be anything you like,
  it does not (and should not) extend the Sponge :javadoc:`Command` interface
* A class that implements :javadoc:`CommandRegistrar`, with the generic type being your command type defined above
* A singleton object that implements :javadoc:`CommandRegistrarType` that contains a factory method to create your 
  registrar, and has a generic type of your command interface (not your registrar)

You may implement your interfaces as you please, but in order to allow Sponge to use your framework, ensure that you:

* store a reference to the :javadoc:`CommandManager.Mutable` in your ``CommandRegistar`` (this is supplied via the 
  ``CommandRegistrarType`` ``create`` method);
* register your singleton ``CommandRegistrarType`` during the :javadoc:`RegisterRegistryValueEvent.GameScoped` event; and
* register commands back to the ``CommandManager.Mutable`` supplied to your registar

Once this is set up, Sponge will pass through requests to your registrar when appropriate.

Using your Registrar
====================

Once set up, your registrar should be totally transparent to your consumers. Sponge will automatically fire a
:javadoc:`RegisterCommandEvent` of your command type that they can listen to - or you can write your own event/callback
that reacts to this event to provide a more platform agnostic experience should you wish.