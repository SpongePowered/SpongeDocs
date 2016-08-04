=============
Creating Text
=============

.. javadoc-import::
    org.spongepowered.api.text.Text
    org.spongepowered.api.text.action.TextActions
    org.spongepowered.api.text.format.TextColors
    org.spongepowered.api.text.selector.Selector.Builder
    java.lang.Object

The Text API is used to create formatted text, which can be sent to players in chat messages, and can also be used in
places such as books and signs.

Unformatted Text
================

Oftentimes, all you need is unformatted text. Unformatted text does not require the use of a text builder, and is the
simplest form of text to create.

Example:
~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.Text;

    Text unformattedText = Text.of("Hey! This is unformatted text!");

The code excerpt illustrated above will return uncolored, unformatted text with no :ref:`text actions <text-actions>`
configured.

Text Builder
============

The text builder interface allows for the creation of formatted text in a "building-block" style.

.. tip::

    Read this `Wikipedia article <http://en.wikipedia.org/wiki/Builder_pattern>`__ for help understanding the purpose
    of the builder pattern in software design.

Colors
~~~~~~

One usage of the text builder is the addition of colors to text, as illustrated below.

Example: Colored Text
~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.format.TextColors;

    Text coloredText = Text.builder("Woot! Golden text is golden.").color(TextColors.GOLD).build();

Any color specified within the :javadoc:`TextColors` class can be used when coloring text. Multiple colors can be used
in text by appending additional texts with different colors:

Example: Multi-colored Text
~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    Text multiColoredText = Text.builder("Sponges are ").color(TextColors.YELLOW).append(
            Text.builder("invincible!").color(TextColors.RED).build()).build();

Styling
~~~~~~~

The builder can also be used to style text, including underlining, italicizing, etc.

Example: Styled Text
~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.format.TextStyles;

    Text styledText = Text.builder("Yay! Styled text!").style(TextStyles.ITALIC).build();

Just like with colors, multiple styles can be used by chaining together separately styled texts.

Example: Multi-styled Text
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    Text multiStyledText = Text.builder("I'm italicized! ").style(TextStyles.ITALIC)
            .append(Text.builder("I'm bold!").style(TextStyles.BOLD).build()).build();

Coloring & Styling Shortcut
~~~~~~~~~~~~~~~~~~~~~~~~~~~

The :javadoc:`Text#of(Object...)` method provides a simple way to add color and styling to your text in a much more
concise way.

Example: Color & Style Shortcut
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    Text colorAndStyleText = Text.of(TextColors.RED, TextStyles.ITALIC, "Shortcuts for the win!");

.. _text-actions:

Text Actions
~~~~~~~~~~~~

The text builder also offers the ability to create actions for text. Any action specified within the
:javadoc:`TextActions` class can be used when creating text actions for text. The method below is a small example of
what text actions can do.

Example: Text with an Action
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.action.TextActions;

    Text clickableText = Text.builder("Click here!").onClick(TextActions.runCommand("tell Spongesquad I'm ready!")).build();

In the method above, players can click the "Click here!" text to run the specified command.

.. note::

    Some text actions, such as :javadoc:`TextActions#changePage(int)`, can only be used with book items.

.. tip::

    Just like with colors, multiple actions can be appended to text. Text actions can even be used in tandem with colors
    because of the builder pattern interface.

Selectors
~~~~~~~~~

Target selectors are used to target players or entities that meet a specific criteria. Target selectors are particularly
useful when creating minigame plugins, but have a broad range of applications.

.. tip::

    Read this `Minecraft wiki article <http://minecraft.gamepedia.com/Commands#Target_selectors>`__ for help understanding
    what target selectors are in Minecraft, and how to use them.

To use selectors in text, you must use the :javadoc:`Selector.Builder` interface. This is illustrated in the example
below.

Example: Selector-generated Text
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.selector.Selector;

    Text adventurers = Text.builder("These players are in adventure mode: ").append(
            Text.of(Selector.parse("@a[m=2]"))
    ).build();

In this example, the target selector ``@a[m=2]`` is targeting every online player who is in adventure mode. When the
method is called, a Text will be returned containing the usernames of every online player who is in adventure mode.
