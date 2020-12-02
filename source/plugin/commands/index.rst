========
Commands
========

.. javadoc-import::
    org.spongepowered.api.Cause
    org.spongepowered.api.command.Command
    org.spongepowered.api.command.Command.Parameterized
    org.spongepowered.api.command.Command.Raw
    org.spongepowered.api.command.CommandCause
    org.spongepowered.api.command.CommandManager
    org.spongepowered.api.command.registrar.CommandRegistrar
    org.spongepowered.api.event.lifecycle.RegisterCommandEvent

The Command API in Sponge is a powerful system that supports the client completion GUI in modern versions of Minecraft,
while providing a flexible, easy to use API.

.. note::

    SpongeAPI does not have a dependency on Brigadier, Mojang's command library.

Creating a Command
~~~~~~~~~~~~~~~~~~

Sponge API natively provides two ways to write commands:

- The preferred way is to use :javadoc:`Command#builder()` to create :javadoc:`Command.Parameterized` s. These take full
  advantage of the rich API, including first class support for client completions. This method is broadly similar to 
  ``CommandSpecs`` in SpongeAPI 7. :doc:`Learn more about parameterized commands here.<parameterized/index>`
- You can also implement :javadoc:`Command.Raw` directly, similar to ``CommandCallable`` in SpongeAPI 7, though we do not
  recommend this over parameterized commands. :doc:`Learn more about raw commands here.<parameterized/index>`

Commands are registered at the appropriate point during the Sponge lifecycle event :javadoc:`RegisterCommandEvent`,
where the generic represents the type of command to register. For each command you wish to register, simply call
:javadoc:`RegisterCommandEvent#register(PluginContainer, T, String...)`, where ``T`` is the type of command that you
are registering. For a ``Command.Parameterized``, a command registration may look like this:

.. code-block:: java

    private final PluginContainer pluginContainer = ...;
    private final Command.Parameterized yourCommand = Command.builder()....build();
    
	@Listener
    public void registerCommands(final RegisterCommandEvent<Command.Parameterized> event) {
    	event.register(this.pluginContainer, this.yourCommand, "commandAlias1", "commandAlias2");
    }

Sponge commands may not be unregistered right now, however work is ongoing to re-enable this in the future.

Providing Your Own Command Framework
====================================

If you wish to provide your own framework for other plugins to use, you may do so by implementing 
:javadoc:`CommandRegistrar` . 
:doc:`Learn more about how to implement and register your own CommandRegistrar here<commandregistrars>`.

Invoking a Command
~~~~~~~~~~~~~~~~~~

.. warning::
    
    While there are legitimate reasons as to why you may invoke a command, think carefully about whether you need to do
    so. If you are executing a command to perform a specific task that you could do via using the APIs, you should use
    the APIs instead.
    
    If you are calling a command because you cannot find the API you need, or it hasn't been implemented yet, talk to the
    Sponge team - it is generally better to ask for the APIs you need than try to work around it by calling other 
    commands.

Commands in Sponge can be called by calling :javadoc:`CommandManager#process(String)`, supplying the command that you want
to call **without** the first forward slash (``/``). The :javadoc:`CommandCause` of the command will be generated from the
:javadoc:`Cause` on the current thread's cause stack, it does not exist as a separate parameter. 

:doc:`Read more about how the CommandCause should be used by commands.<commandcause>`

Contents
========

.. toctree::
	:maxdepth: 2
	:titlesonly:

	commandcause
	parameterized/index
	parameterized/valueparameters
	rawcommands
	commandregistrars
