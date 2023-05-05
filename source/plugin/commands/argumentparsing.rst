================
Argument Parsing
================

.. javadoc-import::
    org.spongepowered.api.command.parameter.CommandContext
    org.spongepowered.api.command.parameter.Parameter
    org.spongepowered.api.command.parameter.Parameter.Key
    org.spongepowered.api.command.parameter.Parameter.Value.Builder
    org.spongepowered.api.command.Command.Builder
    java.lang.Class
    java.lang.String

Minecraft's Brigadier system includes a powerful argument parser that Sponge can take advantage of. 
It converts the string input to java base types (integer, booleans, string) or game objects 
(players, worlds, block types, ...). Sponge's system also supports optional arguments and flags. It also handles 
suggestions of arguments.

The parsed arguments are stored in the :javadoc:`CommandContext` object. If the parser returns a single object, 
obtain it with :javadoc:`CommandContext#one(Key)`. Optional and weak arguments may return ``Optional.empty()``

Many of the parsers may return more than one object; for example, multiple players with a matching username. In that 
case, you must use the :javadoc:`CommandContext#all(Key)` method to get the ``Collection`` of possible matches. 
*Otherwise, the context object will throw an exception!*

When creating a command, consider whether the argument could return multiple values, for example whether a player 
argument could support multiple players when using a selector. If you support multiple values, the users need to type 
only one command, and can use an easier command sytax, e.g ``/tell @a Who took the cookies?``

To create a new :javadoc:`Parameter` (argument), use the :javadoc:`Parameter` class that will give you many 
:javadoc:`Builder` options. Each parameter will need its :javadoc:`Builder#key(String)` 
filled out before being built. 

Apply the ``Parameter`` to the command builder with the :javadoc:`Command.Builder#addParameter(Parameter)` method. 
It is possible to pass more than one ``Parameter`` to the method, thus chaining multiple arguments. 
Example ``/msg <player> <msg>``. This has the same effect as wrapping the ``Parameter`` objects in a 
:javadoc:`Parameter#seq(Iterable<Parameter>)` element.

Example: Building a Command with Multiple Arguments
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.command.parameter.CommandContext;
    import org.spongepowered.api.command.parameter.Parameter;
    import org.spongepowered.api.command.Command;
    import org.spongepowered.api.command.CommandResult;
    import org.spongepowered.api.entity.living.player.server.ServerPlayer;

    public Command.Parameterized createMessageCommand(){
        Parameter.Value<ServerPlayer> playerParameter = Parameter.player().key("player").build();
        Parameter.Value<String> messageParameter = Parameter.remainingJoinedStrings().key("message").build();

        return Command
            .builder()
            .executor((CommandContext context) -> {
                ServerPlayer player = context.one(playerParameter);
                String message = context.one(messageParameter);

                player.sendMessage(Component.text(message));
                return CommandResult.success();
            })
            .addParameter(playerParameter, messageParameter)
            .build();
    }

Overview of the ``Parameter`` Command Elements
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. list-table::
    :widths: 25 50 25
    :header-rows: 1

    * - Parameter
      - Description
      - Value Type & Amount
    * - ``string``
      - Require the argument to be a string.
      - one ``String``
    * - ``remainingJoinedStrings``
      - Concatenates all remaining arguments separated by spaces (useful for message commands).
      - one ``String``
    * - ``bool``
      - Require the argument to be a boolean.
      - one ``Boolean``
    * - ``integer``
      - Require the argument to be a integer.
      - one ``Integer``
    * - ``rangeInteger``
      - Require the argument to be a integer between two values.
      - one ``Integer``
    * - ``doubleNumber``
      - Require the argument to be a double.
      - one ``Double``
    * - ``rangeDouble``
      - Require the argument to be a double between two values
      - one ``Double``
    * - ``player``
      - Require the argument to be a player. *May return multiple players!*
      - multiple ``ServerPlayer`` instances
    * - ``playerOrTarget``
      - Like ``player``, but returns the sender of the command if no matching player was found.
      - mutiple ``ServerPlayer`` instances
    * - ``user``
      - Require the argument to be a user. *May return multiple users!*
      - multiple ``User`` instances
    * - ``world``
      - Require the argument to be a world (only loaded worlds)
      - multiple ``ServerWorld`` instances
    * - ``location``
      - Require the argument to be a location
      - one ``ServerLocation``
    * - ``vector3d``
      - Require the argument to be a vector
      - one ``Vector3d``
    * - ``rotation``
      - Require the argument to be a vector, but returns the senders rotation if no value was specified
      - one ``Vector3d``
    * - ``registryElement``
      - Require the argument to be the resoure key of one of the specified elements
      - multiple matching elements of the specified registry type
    * - ``choices``
      - Returns an argument that allows selecting from a limited set of values
      - one specified value
    * - ``literal``
      - Require the argument to match one of the specified literals
      - one specified literal
    * - ``enumValue``
      - Require the argument to be a enum
      - one ``Enum`` 
    * - ``seq``
      - Builds a sequence of commands
      - inherited

.. tip::
    See the Javadocs for :javadoc:`Parameter` for more information

.. warning::

    Don't expect that a ``Parameter`` will only ever return a single value, 
    a lot of them support multiple return values; some might support regular expressions or use command selector. 
    This is intentional as it makes commands easier to use, e.g ``/tell @a BanditPlayer has cookies!``.
    
Custom Parameter
~~~~~~~~~~~~~~~~

It is possible to create custom command elements. Example Vector2i. This is done though the 
:javadoc:`Parameter#builder(Class)` method, which returns a :javadoc:`Parameter` where all data of the parameter
is needed to be provided. Once done call the ``build`` method to build the parameter.

When building a new parameter, only the ``parser`` and ``key`` are required for build. The ``parser`` is where the logic
of mapping the ``String`` input to the desired value is.

Example: Creating a Custom Parameter
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

In this example we will make a Vector2i parameter. This requires reading two Integers and converting them to a Vector2i.

.. code-block:: java

    Parameter.Value<Vector2i> vectorParameter = Parameter
      .builder(Vector2i.class)
      .addParser((
        Parameter.Key<Vector2i> parameterKey, 
        ArgumentReader.Mutable reader, 
        CommandContext.Builder context) -> {
          int x = reader.parseInt();
          int y = reader.parseInt();
          return new Vector2i(x, y);
      })
      .key("vector")
      .build();

.. tip::

    When building a new parameter, you can base your parameter from a exsiting parameter. Example: taking 
    :javadoc:`Parameter#string()` and giving it client suggestions


