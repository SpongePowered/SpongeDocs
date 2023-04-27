==================
Building a Command
==================

.. javadoc-import::
    org.spongepowered.api.command.Command
    org.spongepowered.api.command.Command.Builder
    org.spongepowered.api.command.CommandExecutor
    org.spongepowered.api.command.parameter.CommandContext
    org.spongepowered.api.command.CommandResult
    org.spongepowered.api.command.exception.CommandException
    org.spongepowered.api.command.exception.ArgumentParseException
    org.spongepowered.api.event.lifecycle.RegisterCommandEvent
    org.spongepowered.api.event.EventContextKeys
    org.spongepowered.plugin.PluginContainer
    net.kyori.adventure.text.Component
    
The first step is to get a new :javadoc:`Command.Builder` builder. 
The builder provides methods to modify the command help messages, command arguments and the command logic. 
These methods can be chained.

To finally build the command, you'll want to call the 
:javadoc:`Command.Builder#build()` method.

Example: Building a Simple Command
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.command.Command;

    public Command.Parameterized buildCommand(){
        return Command
            .builder()
            .executor(new HelloWorldCommand())
            .permission("myplugin.command.helloWorld")
            .shortDescription(Component.text("Hello World Command"))
            .build();
    }

Overview of the Command.Builder methods
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. list-table:: 
    :widths: 25 75
    :header-rows: 1

    * - method
      - Description
    * - executor
      - Defines the command logic. *Setting the executor is required* if no child commands are set.
    * - parameters
      - Sets the parameters for use on the executor
    * - flags
      - Sets the flags for use on the executor
    * - permission
      - Sets the permission that will be checked before using this command
    * - shortDescription
      - A short, one-line description of this command's purpose that will be displayed by the help system
    * - extendedDescription
      - Sets an extended description to use in longer help listings. Will be appended to the short description
    * - children
      - Sets the child commands of this command with their aliases
    * - executionRequirements
      - Sets the execution requirements for the command.
    * - terminal
      - Sets if the command can execute despite the parameters stating that they are mandatory
    * - build
      - Builds the command. After that you need to register the command

Player-Only commands
~~~~~~~~~~~~~~~~~~~~

Sometimes it is necessary that only players can execute a command. Example ``/suicide`` command. 
This is best done though the ``executionRequirements`` on the command's builder where we check for
a target player on the command cause. 

.. code-block:: java

    public Command.Parameterized buildCommand(){
        return Command
            .builder()
            .executor(new HelloWorldCommand())
            .permission("myplugin.command.helloWorld")
            .shortDescription(Component.text("Hello World Command"))
            .executionRequirements(context -> context.cause().context().get(EventContextKeys.PLAYER).isPresent())
            .build();
    }

.. note::

    We recommend you to add an optional ``[player]`` argument to make the command *console-friendly*. 
    Example ``/suicide [player]``

    The easiest solution for this is to append a ``playerOrTarget`` command parameter (see :doc:`argumentparsing`) 
    instead of providing a execution requirement.

.. tip::

    Often times command are put in as player-only as they require the location in the world the command was
    executed from. Best practise would be to check for the target location using :javadoc:`EventContextKeys#LOCATION`
    instead of the player as this would allow command blocks to run the command without specifing a player.

Writing a Command Executor
~~~~~~~~~~~~~~~~~~~~~~~~~~

The only required component to build a simple command is the command executor class, which contains the logic of 
the command.

The class has to implement the :javadoc:`CommandExecutor` interface, which contains a single method that is called on 
command execution. The method contains a single argument of :javadoc:`CommandContext` which contains all data attached
to the command.

Example: Simple Command Executor
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.command.CommandResult;
    import org.spongepowered.api.command.parameter.CommandContext;
    import org.spongepowered.api.command.CommandExecutor;
    import org.spongepowered.api.command.exception.CommandException;

    public class HelloWorldCommand implements CommandExecutor {
    
        @Override
        public CommandResult execute(CommandContext context) throws CommandException{
            context.sendMessage(Component.text("Hello World!"));
            return CommandResult.success();
        }

    }

.. tip::

    You can use `anonymous classes <https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html>`_ to 
    define the command executor in the command's build process (see example in the :doc:`argumentparsing` page).

Command Result
~~~~~~~~~~~~~~

The command result is used to give more information about how the command was executed.
In the example above we used :javadoc:`CommandResult#success()`, however we can give more information than this,
which is then sent back to the client to provide a correctly formatted message.

By going the builder route you gain access to the ``result`` method which is an integer value sent back to the client.
Generally this can be ignored and the static helper methods of :javadoc:`CommandResult#success()` and 
:javadoc:`CommandResult#error(Component)` will be used.

Example: Building a CommandResult
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    CommandResult result = CommandResult.builder()
        .result(0)
        .error(Component.text("Hello world in error form"))
        .build();

The result's input number has three meanings.

.. list-table:: 
    :widths: 25 75
    :header-rows: 1

    * - Value
      - Description
    * - Positive
      - Successful execution
    * - Zero
      - Unsuccessful execution (but not necessarily an error)
    * - Negative
      - Undefined in the Minecraft spec, can result in different effects

Error Handling
~~~~~~~~~~~~~~

The ``execute`` method may also throw a :javadoc:`CommandException`, signaling that an error occured while trying to 
execute the command. If such an Exception is thrown, its message will be displayed to the command source, formatted 
as an error. An :javadoc:`ArgumentParseException`, a subtype of ``CommandExeption`` is automaticlly thrown if the 
commands arguments could not be parsed.

Registering a Command
~~~~~~~~~~~~~~~~~~~~~

Commands are registered on a :javadoc:`RegisterCommandEvent`. The event takes a generic which is the type of command 
that is being registered to it. To register a command, the 
:javadoc:`RegisterCommandEvent#register(PluginContainer, C, String, String...)` method needs to be invoked.

.. code-block:: java

  @Inject
  PluginContainer container;

  @Listener
  public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event){
    event.register(this.container, buildCommand(), "helloworld", "hello", "test");
  }

  @Listener
  public void onRegisterRawCommands(final RegisterCommandEvent<Command.Raw> event){
    event.register(this.container, new MyRawCommand(), "raw");
  }