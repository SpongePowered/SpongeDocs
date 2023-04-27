===========
Raw Command
===========


.. javadoc-import::
    org.spongepowered.api.event.lifecycle.RegisterCommandEvent
    org.spongepowered.api.command.Command.Raw
    org.spongepowered.api.command.Command
    org.spongepowered.plugin.PluginContainer
    java.lang.String


The raw command interface can be used to define commands. The interface can be used as a base for custom command APIs.

It is recommended to use the :doc:`commandbuilding` for simple command definitions.

Writing a Command
~~~~~~~~~~~~~~~~~

The first step is to create a class for the command. The class has to implement the interface ``Command.Raw``:

.. code-block:: java

    public class MyBroadcastCommand implements Command.Raw {

        private final Component usage = Component.text("<message>");
        private final Component descritption = Component.text("Display a message to all players");

        @Override
        public CommandResult process(CommandCause cause, ArgumentReader.Mutable arguments) throws CommandException {
            String message = arguments.remaining();
            Sponge.server().broadcastAudience().sendMessage(Component.text(message));
            return CommandResult.success();
        }

        @Override
        public List<CommandCompletion> complete(CommandCause cause, ArgumentReader.Mutable arguments) throws CommandException {
            return Collections.emptyList();
        }

        @Override
        public boolean canExecute(CommandCause cause) {
            return cause.hasPermission("myplugin.broadcast");
        }

        @Override
        public Optional<Component> shortDescription(CommandCause cause) {
            return Optional.of(this.descritption);
        }

        @Override
        public Optional<Component> extendedDescription(CommandCause cause) {
            return Optional.empty();
        }

        @Override
        public Component usage(CommandCause cause) {
            return this.usage;
        }
    }

.. tip::

    See the JavaDoc for :javadoc:`Command.Raw` for the purposes of each method in the example

Registering the Command
~~~~~~~~~~~~~~~~~~~~~~~

Now we can register the class in the :javadoc:`RegisterCommandEvent` event. You can register the command using the 
:javadoc:`RegisterCommandEvent#register(PluginContainer, Command, String, String...)` passing your plugin's container, 
an instance of the raw command and the main command as well as any aliases.

.. code-block:: java

    @Inject
    PluginContainer container;

    @Listener
    public void onRegisterRawCommands(final RegisterCommandEvent<Command.Raw> event){
        event.register(this.container, new MyBroadcastCommand(), "broadcast");
    }