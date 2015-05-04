=================
Creating Messages
=================

The Text API is used to create formatted messages. These messages can be sent to players in chat messages, and can also be used in places such as books and signs. 
Sponge provides the ``org.spongepowered.api.text.Texts`` utility class to assist in creating and formatting messages.

Unformatted Messages
====================

Oftentimes, all you need is an unformatted message. Unformatted messages do not require the use of a text builder, 
and are the simplest form of messages to create.

Example:
~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.Texts;

    public Text createSomeMessage() {
        return Texts.of("Hey! This is an unformatted message!");
    }

The code excerpt illustrated above will return an uncolored message, with no events configured.

Working with the Message Builder
================================

The message builder interface allows for the creation of formatted messages in a "building-block" style.

.. tip ::

    Read this `Wikipedia article <http://en.wikipedia.org/wiki/Builder_pattern>`__ for help understanding the purpose of the builder pattern in software design.

Colors
~~~~~~

One usage of the message builder is the addition of colors to messages, as illustrated below.

Example: Colored Message
~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.Texts;

    public Text createColoredMessage() {
        return Texts.builder("Woot! Golden text is golden.").color(TextColors.GOLD).build();
    }

Any color specified within the ``org.spongepowered.api.text.format.TextColors`` class can be used when coloring messages. 
Multiple colors can be used in a message by appending additional messages with different colors:

Example: Multi-colored Message
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.Texts;

    public Text createMultiColoredMessage() {
        return Texts.builder("Sponges are ").color(TextColors.YELLOW).append(
                Texts.builder("invincible!").color(TextColors.RED).build()
        ).build();
    }

Text Actions
~~~~~~~~~~~~

The message builder also offers the ability to create actions for messages. 
Any action specified within the ``org.spongepowered.api.text.action.TextActions`` class can be used when creating text actions for messages. 
The method below is a small example of what text actions can do.

Example: Message with a Text Action
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.action.TextActions;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.Texts;

    public Text createClickableMessage() {
        return Texts.builder("Click here!").onClick(TextActions.runCommand("tell Spongesquad I'm ready!")).build();
    }

In the method above, players can click the "Click here!" text to run the specified command.

.. note ::

    Some text actions, such as ``ChangePage``, can only be used with book items.

.. tip ::

    Just like with colors, multiple text actions can be appended to a message. Text actions can even be used in tandem with text colors 
    because of the builder pattern interface.

Selectors
~~~~~~~~~

Target selectors are used to target players or entities that meet a specific criteria. Target selectors are particularly useful 
when creating minigame plugins, but have a broad range of applications.

.. tip ::

    Read this `Minecraft wiki article <http://minecraft.gamepedia.com/Commands#Target_selectors>`__ for help understanding 
    what target selectors are in Minecraft, and how to use them.

To use selectors in messages, you must use the ``org.spongepowered.api.text.selector.SelectorBuilder`` interface. This is illustrated in the example below.

Example: Selector-generated Message
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.text.selector.Selectors;

    public Text displayAdventurers() {
        return Texts.builder("These players are in adventure mode: ").append(
                Texts.of(Selectors.parse("@a[m=2]"))
        ).build();
    }

In this example, the target selector ``@a[m=2]`` is targeting every online player who is in adventure mode. When the method is called, 
a Message will be returned containing the usernames of every online player who is in adventure mode.
