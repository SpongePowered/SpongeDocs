================
Argument Parsing
================

.. javadoc-import::
    org.spongepowered.api.CatalogType
    org.spongepowered.api.command.CommandSource
    org.spongepowered.api.command.args.ArgumentParseException
    org.spongepowered.api.command.args.CommandArgs
    org.spongepowered.api.command.args.CommandContext
    org.spongepowered.api.command.args.CommandElement
    org.spongepowered.api.command.args.GenericArguments
    org.spongepowered.api.command.spec.CommandSpec.Builder
    org.spongepowered.api.entity.Entity
    org.spongepowered.api.text.selector.Selector
    java.lang.IllegalArgumentException
    java.lang.String

The Command Builder API comes with a powerful argument parser. It converts the string input to java base types
(integers, booleans, strings) or game objects (players, worlds, block types, ...). The parser supports optional
arguments and flags. It also handles TAB completion of arguments.

The parsed arguments are stored in the :javadoc:`CommandContext` object. If the parser returns a single object, obtain
it with :javadoc:`CommandContext#getOne(String)`. Optional and weak arguments may return ``Optional.empty()``.

Many of the parsers may return more than one object (e.g. multiple players with a matching username). In that case, you
must use the :javadoc:`CommandContext#getAll(String)` method to get the ``Collection`` of possible matches.
**Otherwise, the context object will throw an exception!**

When creating a command, consider whether the argument could return multiple values, for example, whether a player
argument could support multiple players when using a selector. If you support multiple values the users need to type
only one command and can use an easier command syntax. Example: ``/tell @a Who took the cookies?``

.. tip::

   You can use the :javadoc:`GenericArguments#onlyOne(CommandElement)` element to restrict the amount of returned values
   to a single one, so you can safely use ``args.<T>getOne(String)``. However the user will still get a message, if they
   try to select more than one value.

To create a new :javadoc:`CommandElement` (argument), use the :javadoc:`GenericArguments` factory class. Many command
elements require a short text key, which is displayed in error and help messages.

Apply the ``CommandElement`` to the command builder with the :javadoc:`CommandSpec.Builder#arguments(CommandElement...)`
method. It is possible to pass more than one ``CommandElement`` to the method, thus chaining multiple arguments (e.g.
``/msg <player> <msg>``). This has the same effect as wrapping the ``CommandElement`` objects in a
:javadoc:`GenericArguments#seq(CommandElement...)` element.

Example: Building a Command with Multiple Arguments
===================================================

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.entity.living.player.Player;
    import org.spongepowered.api.command.CommandException;
    import org.spongepowered.api.command.CommandResult;
    import org.spongepowered.api.command.CommandSource;
    import org.spongepowered.api.command.args.CommandContext;
    import org.spongepowered.api.command.args.GenericArguments;
    import org.spongepowered.api.command.spec.CommandExecutor;
    import org.spongepowered.api.command.spec.CommandSpec;

    PluginContainer plugin = ...;

    CommandSpec myCommandSpec = CommandSpec.builder()
            .description(Text.of("Send a message to a player"))
            .permission("myplugin.command.message")

            .arguments(
                    GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                    GenericArguments.remainingJoinedStrings(Text.of("message")))

            .executor((CommandSource src, CommandContext args) -> {

                Player player = args.<Player>getOne("player").get();
                String message = args.<String>getOne("message").get();

                player.sendMessage(Text.of(message));

                return CommandResult.success();
            })
            .build();

    Sponge.getCommandManager().register(plugin, myCommandSpec, "message", "msg", "m");

Overview of the ``GenericArguments`` command elements
=====================================================

+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| Command Element            | Description                                                                             | Value Type & Amount           |
+============================+=========================================================================================+===============================+
| ``none``                   | Expects no arguments. This is the default behavior of a ``CommandSpec``.                |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| **Java Base Types**                                                                                                                                  |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``string``                 | Require an argument to be a string.                                                     | one ``String``                |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``remainingJoinedStrings`` | Concatenates all remaining arguments separated by spaces (useful for message commands). | one ``String``                |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``bool``                   | Require an argument to be a boolean.                                                    | one ``Boolean``               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``integer``                | Require an argument to be an integer.                                                   | one ``Integer``               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``doubleNum``              | Require an argument to be a double.                                                     | one ``Double``                |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| **Game Objects**                                                                                                                                     |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``player``                 | Expect an argument to represent an online player. **May return multiple players!**      | multiple ``Player`` instances |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``playerOrSource``         | Like ``player``, but returns the sender of the command if no matching player was found. | multiple ``Player`` instances |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``userOrSource``           | Like ``playerOrSource``, but returns a user instead of a player.                        | multiple ``User`` instances   |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``world``                  | Expect an argument to represent a world (also includes unloaded worlds).                | multiple ``WorldProperties``  |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``dimension``              | Expect an argument to represent a dimension (``END``, ``NETHER``, ``OVERWORLD``).       | multiple ``DimensionType``    |
|                            |                                                                                         | instances                     |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``location``               | Expect an argument to represent a ``Location``.                                         | one ``Location``              |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``vector3d``               | Expect an argument to represent a ``Vector3d``.                                         | one ``Vector3d``              |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``catalogedElement``       | Expect an argument that is a member of the specified                                    | multiple matching elements    |
|                            | :javadoc:`CatalogType`.                                                                 | of the specified catalog type |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| **Matchers**                                                                                                                                         |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``choices``                | Return an argument that allows selecting from a limited set of values.                  | one specified value           |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``literal``                | Expect a literal sequence of arguments (e.g. ``"i", "luv", "u"``: ``/cmd i luv u``).    | one specified value           |
|                            | Throws an error if the arguments do not match.                                          |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``enumValue``              | Require the argument to be a key under the provided enum.                               | multiple matching elements    |
|                            |                                                                                         | of the specified enum         |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| **Utilities**                                                                                                                                        |
|                                                                                                                                                      |
| Can be wrapped around command elements. The value type is inherited from the wrapped element.                                                        |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``seq``                    | Builds a sequence of command elements (e.g. ``/cmd <arg1> <arg2> <arg3>``).             | inherited                     |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``repeated``               | Require a given command element to be provided a certain number of times.               | multiple inherited            |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``allOf``                  | Require all remaining args to match the provided command element.                       | multiple inherited            |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``optional``               | Make the provided command element optional. Throws an error if the argument             | inherited                     |
|                            | is of invalid format and there are no more args.                                        |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``optionalWeak``           | Make the provided command element optional. Does not throw an error if the argument     | inherited                     |
|                            | is of invalid format and there are no more args.                                        |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``firstParsing``           | Returns a command element that matches the first of the provided elements that parses   | inherited                     |
|                            | (useful for command overloading, e.g. ``/settime <day|night|<number>``).                |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``onlyOne``                | Restricts the given command element to only insert one value into the context at the    | inherited                     |
|                            | provided key.                                                                           |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``flags``                  | Returns a builder for command flags (e.g. ``/cmd [-a] [-b <value>]``).                  | Short Flag: one ``Boolean``   |
|                            |                                                                                         |                               |
|                            | See :doc:`flags`                                                                        | Long Flag: one ``String``     |
|                            |                                                                                         |                               |
|                            |                                                                                         | Value Flag: inherited         |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``requiringPermission``    | Requires the command sender to have the specified permission in order to use the given  | inherited                     |
|                            | command argument                                                                        |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+
| ``requiringPermissionWeak``| Requires the command sender to have the specified permission in order to use the given  | inherited                     |
|                            | command argument. Does not throw an error if the user does not have the permission.     |                               |
+----------------------------+-----------------------------------------------------------------------------------------+-------------------------------+

.. tip::

    See the Javadocs for :javadoc:`GenericArguments` for more information.

.. warning::

    Don't expect that a ``CommandElement``\s will only ever return a single value, a lot of them support multiple return
    values; some might even support regular expressions or use a command selector. This is intentional as it makes
    commands easier to use. Example: ``/tell @a BanditPlayer has the cookies!``. If you want to make sure to only get a
    single value use ``GenericArguments#onlyOne(CommandElement)``.

Custom Command Elements
=======================

It is possible to create custom command elements (e.g. a URL parser or a ``Vector2i`` element) by extending the abstract
``CommandElement`` class.

The :javadoc:`CommandElement#parseValue(CommandSource, CommandArgs)` method should fetch a raw argument string with
:javadoc:`CommandArgs#next()` and convert it to an object. The method should throw an :javadoc:`ArgumentParseException`
if the parsing fails.

The :javadoc:`CommandElement#complete(CommandSource, CommandArgs, CommandContext)` method should use
:javadoc:`CommandArgs#peek()` to read the next raw argument. It returns a list of suggestions for TAB completion.

Example: ``Vector2i`` command element definition
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The parser in this example reads two input arguments and converts them to a vector.

.. code-block:: java

   import com.flowpowered.math.vector.Vector2i;
   import org.spongepowered.api.command.args.ArgumentParseException;
   import org.spongepowered.api.command.args.CommandArgs;
   import org.spongepowered.api.text.Text;
   import org.spongepowered.api.command.args.CommandElement;

   import java.util.Collections;
   import java.util.List;

   public class Vector2iCommandElement extends CommandElement {

       protected Vector2iCommandElement(Text key) {
           super(key);
       }

       @Override
       protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {

           String xInput = args.next();
           int x = parseInt(xInput, args);

           String yInput = args.next();
           int y = parseInt(yInput, args);

           return new Vector2i(x, y);
       }

       private int parseInt(String input, CommandArgs args) throws ArgumentParseException {
           try {
               return Integer.parseInt(input);
           } catch(NumberFormatException e) {
               throw args.createError(Text.of("'" + input + "' is not a valid number!"));
           }
       }

       @Override
       public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
           return Collections.emptyList();
       }

       @Override
       public Text getUsage(CommandSource src) {
           return Text.of("<x> <y>");
       }
   }

Example: ``Vector2i`` command element usage
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    // /plottp <x> <y>
    CommandSpec myCommandSpec = CommandSpec.builder()
            .description(Text.of("Teleport to a plot"))
            .permission("myplugin.command.plot.tp")

            .arguments(new Vector2iCommandElement(Text.of("coordinates")))

            .executor(new MyCommandExecutor())
            .build();

.. tip::

    Look at the `source code <https://github.com/SpongePowered/SpongeAPI/blob/stable-7/src/main/java/org/spongepowered/api/command/args/GenericArguments.java>`_
    of the ``GenericArguments`` class for more examples.

Using Selectors in Custom Command Elements
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Sponge provides support for parsing selectors, meaning that you can make use of them in your custom elements. There
are two steps in using selectors, **parsing** (getting a :javadoc:`Selector` from the string) and **resolving**
(getting a set of :javadoc:`Entity` objects selected by the selector).

To **parse** a selector string, use the :javadoc:`Selector#parse(String)` method, passing the entire selector,
including the ``@`` symbol. This will turn the string into a ``Selector`` object that can be queried or resolved.
Note that if the string is not a valid selector, an :javadoc:`IllegalArgumentException` will be thrown.

To **resolve** this selector, use :javadoc:`Selector#resolve(CommandSource)`. This will return a set of ``Entity``
objects selected by the selector.

The following ``parseValue`` method from the ``CommandElement`` class attempts to parse a selector and return a set of
entities based on the location of the ``CommandSource``. If the passed string does not start with ``@``, an exception
will be thrown indicating that the passed argument is not a selector.

.. code-block:: java

    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String nextArg = args.next();
        if (nextArg.startsWith("@")) {
            Set<Entity> selectedEntities;
            try {
                selectedEntities = Selector.parse(nextArg).resolve(source);
            } catch (IllegalArgumentException e) {
                throw args.createError(Text.of("Could not parse selector."));
            }

            if (selectedEntities.isEmpty()) {
                throw args.createError(Text.of("No entities selected."));
            }

            return selectedEntities;
        }

        throw args.createError(Text.of("Not a selector."));
    }

.. tip::

  Look at the `SelectorCommandElement source code <https://github.com/SpongePowered/SpongeAPI/blob/stable-7/src/main/java/org/spongepowered/api/command/args/SelectorCommandElement.java#L40>`_
  for an example of how selector parsing is performed in the standard Sponge ``CommandElements``.
