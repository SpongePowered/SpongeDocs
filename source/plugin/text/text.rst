=============
Creating Text
=============

.. javadoc-import::
    java.lang.Object
    net.kyori.adventure.text.Component
    net.kyori.adventure.text.event.ClickEvent
    net.kyori.adventure.text.event.HoverEvent
    net.kyori.adventure.text.format.NamedTextColor
    net.kyori.adventure.text.format.TextColor
    org.spongepowered.api.adventure.SpongeComponents

Formatted text can be created using :javadoc:`Component` factories as described in this section.
The robust text API can be used in a variety of ways to combine styling, coloring, and actions.

Unformatted Text
================

Oftentimes, all you need is unformatted text. Unformatted text is the simplest form of text to create.

Example:
~~~~~~~~

.. code-block:: java

    import net.kyori.adventure.text.Component;

    Component unformattedText = Component.text("Hey! This is unformatted text!");

The code excerpt illustrated above will return uncolored, unformatted text with no :ref:`text actions <text-actions>`
configured.

Formatted Text
==============

The Component interface allows for the creation of formatted text in a "building-block" style.
Components in Adventure act like builders where any Component can be amended with colors, decorations, etc.
Components are immutable, so using these builder-like methods will create a copy each time.
There is no need to call ``.build()`` at the end, unlike with Sponge's previous Text API from API 7 and below.

.. tip::

    Read this `Wikipedia article <https://en.wikipedia.org/wiki/Builder_pattern>`__ for help understanding the purpose
    of the builder pattern in software design.

Colors
======

One usage of the text API is the addition of colors to text, as illustrated below.

Example: Colored Text
~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import net.kyori.adventure.text.format.NamedTextColor;

    Component coloredText = Component.text("Woot! Golden text is golden.", NamedTextColor.GOLD);

Any Minecraft color specified within the :javadoc:`NamedTextColor` class can be used when coloring text,
as well as full-RGB colors using :javadoc:`TextColor`.
Multiple colors can be used in text by appending additional texts with different colors:

Example: Multi-colored Text
~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import net.kyori.adventure.text.format.TextColor;

    Component multiColoredText = Component.text("Sponges are ", NamedTextColor.YELLOW).append(
            Component.text("invincible!", TextColor.color(0x5fb0ff)));

Styling
=======

The API can also be used to style text, including underlining, italicizing, etc.
This is called "decorating" in the Adventure library.

Example: Styled Text
~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import net.kyori.adventure.text.format.TextDecoration;

    Component styledText = Component.text("Yay! Styled text!").decorate(TextDecoration.ITALIC);
    Component shortcutText = Component.text("Shortcut for both!", NamedTextColor.GRAY, TextDecoration.UNDERLINE);

Just like with colors, multiple styles can be used by chaining together separately styled texts.

Example: Multi-styled Text
~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    Component multiStyledText = Component.text("I'm italicized! ").decorate(TextDecoration.ITALIC)
            .append(Component.text("I'm bold!").decorate(TextDecoration.BOLD));

.. _text-actions:

Text Events
===========

Components also offer the ability to create actions for text. Any action within the
:javadoc:`HoverEvent` or :javadoc:`ClickEvent` classes can be used when creating text actions for text.
Sponge provides additional actions in :javadoc:`SpongeComponents`.
The method below is a small example of what text actions can do.

Example: Text with an Event
~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import net.kyori.adventure.text.event.ClickEvent;

    Component clickableText = Component.text("Click here!")
        .clickEvent(ClickEvent.runCommand("tell Spongesquad I'm ready!"));

    Component callbackText = Component.text("Click here too!")
        .clickEvent(SpongeComponents.executeCallback(
            cause -> cause.audience().sendMessage(Component.text("You clicked!"))));

In the method above, players can click the "Click here!" text to run the specified command.

.. note::

    Some text actions, such as :javadoc:`ClickEvent#changePage(int)`, can only be used with book items.

.. tip::

    Just like with colors, multiple actions can be appended to text. Text actions can even be used in tandem with colors
    because of the builder pattern interface.

Selectors
=========

Target selectors are used to target players or entities that meet a specific criteria. Target selectors are particularly
useful when creating minigame plugins, but have a broad range of applications.

.. tip::

    Read this `Minecraft wiki article <https://minecraft.wiki/w/Target_selectors>`__ for help understanding
    what target selectors are in Minecraft, and how to use them.

To use selectors in text, there is a component factory for Selectors. This is illustrated in the example
below.

Example: Selector-generated Text
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    Component adventurers = Component.text("These players are in adventure mode: ")
            .append(Component.selector("@a[m=2]"));

In this example, the target selector ``@a[m=2]`` is targeting every online player who is in adventure mode. When the
method is called, a Component will be returned with a selector of all the usernames of every online player who is in adventure mode.
To send this selector to players, the selector will have to be expanded to the real usernames using ``SpongeComponents#resolve``.

Text Builders
=============

Mutable builder classes exist for every kind of ``Component``.
These are available should you need a mutable representation, and they are also used in a few convenience methods
throughout the library to expose parts of an existing Component for editing.

Example: Text Component builder
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    Component weBuiltThisText = Component.text()
            .content("with Sponge and Flard")
            .color(NamedTextColor.YELLOW)
            .decorate(TextDecoration.ITALIC, TextDecoration.BOLD)
            .build()

Example: Editing text with a StyleBuilder
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    Component limitedEdition = weBuiltThisText.style(
            styleBuilder -> styleBuilder.decorate(TextDecoration.UNDERLINE));
