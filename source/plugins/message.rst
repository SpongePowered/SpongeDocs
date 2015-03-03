.. External references.

.. _documentation for the MessageBuilder: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/text/message/MessageBuilder.html
.. _documentation for Messages: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/text/message/Messages.html
.. _documentation for TextActions: http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/text/action/TextActions.html

.. _Wikipedia article about the builder pattern: http://en.wikipedia.org/wiki/Builder_pattern
.. _Minecraft Wiki article about selectors: http://minecraft.gamepedia.com/Commands#Target_selectors

=====================
Using the Message API
=====================

Messages are used to create formatted text. These messages can be sent to players in chat messages, and can also be used in places such as books and signs. Sponge provides a message builder through the ``org.spongepowered.api.text.message.MessageBuilder`` interface to assist in creating and formatting messages.

.. tip::

    See the `documentation for the MessageBuilder`_ for full information about the class, as well as its methods and their usage.


Creating a Message
==================

Oftentimes, all you need is an unformatted message. Unformatted messages do not require the use of the message builder interface, and are the simplest form of messages to create.

Example: Unformatted Message
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. tip::

    See the `documentation for Messages`_ for full information about the class, as well as its methods and their usage.

.. code-block:: java

    import org.spongepowered.api.text.message.Message;
    import org.spongepowered.api.text.message.Messages;

    public Message createSomeMessage() {
        return Messages.of("Hey! This is an unformatted message!");
    }

The code excerpt illustrated above will return an uncolored message, with no events configured.

Working with the Message Builder
================================

The message builder interface allows for the creation of formatted messages in a "building-block" style.

.. tip ::

    Read this `Wikipedia article about the builder pattern`_ for help understanding its purpose in software design.

Colors
~~~~~~

One usage of the message builder is the addition of colors to messages, as illustrated below.

Example: Colored Message
~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.message.Message;
    import org.spongepowered.api.text.message.Messages;

    public Message createColoredMessage() {
        return Messages.builder("Woot! Golden text is golden.").color(TextColors.GOLD).build();
    }

Any color specified within the ``org.spongepowered.api.text.format.TextColors`` class can be used when coloring messages. Multiple colors can be used in a message by appending additional messages with different colors:

Example: Multi-colored Message
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.message.Message;
    import org.spongepowered.api.text.message.Messages;

    public Message createMultiColoredMessage() {
        return Messages.builder("Sponges are ").color(TextColors.YELLOW).append(
                Messages.builder("invincible!").color(TextColors.RED).build()
        ).build();
    }

Text Actions
~~~~~~~~~~~~

The message builder also offers the ability to create actions for messages. Any action specified within the ``org.spongepowered.api.text.action.TextActions`` class can be used when creating text actions for messages. The method below is a small example of what text actions can do.

.. tip::

    See the `documentation for TextActions`_ for a full list of supported TextActions in Sponge.

Example: Message with a Text Action
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.action.TextActions;
    import org.spongepowered.api.text.message.Message;
    import org.spongepowered.api.text.message.Messages;

    public Message createClickableMessage() {
        return Messages.builder("Click here!").onClick(TextActions.runCommand("tell Spongesquad I'm ready!")).build();
    }

In the method above, players can click the "Click here!" text to run the specified command.

.. note ::

    Some text actions, such as ``ChangePage``, can only be used with book items.

.. tip ::

    Just like with colors, multiple text actions can be appended to a message. Text actions can even be used in tandem with text colors because of the builder pattern interface.

Selectors
~~~~~~~~~

Target selectors are used to target players or entities that meet a specific criteria. Target selectors are particularly useful when creating minigame plugins, but have a broad range of applications.

.. tip ::

    Read this `Minecraft Wiki article about selectors`_ for help understanding what target selectors are in Minecraft, and how to use them.

To use selectors in messages, you must use the ``org.spongepowered.api.text.selector.SelectorBuilder`` interface. This is illustrated in the example below.

Example: Selector-generated Message
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.message.Message;
    import org.spongepowered.api.text.message.Messages;

    public Message displayAdventurers() {
        return Messages.builder("These players are in adventure mode: ").append(
                Messages.selector("@a[m=2]").build()
        ).build();
    }

In this example, the target selector ``@a[m=2]`` is targeting every online player who is in adventure mode. When the method is called, a Message will be returned containing the usernames of every online player who is in adventure mode.
