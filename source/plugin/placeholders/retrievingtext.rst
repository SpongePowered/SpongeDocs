===================================
Retrieving and Parsing Placeholders
===================================

.. javadoc-import::
    net.kyori.adventure.audience.Audience
    net.kyori.adventure.text.Component
    net.kyori.adventure.text.ComponentBuilder
    org.spongepowered.api.CatalogType
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.service.placeholder.PlaceholderContext
    org.spongepowered.api.service.placeholder.PlaceholderContext.Builder
    org.spongepowered.api.service.placeholder.PlaceholderParser
    org.spongepowered.api.service.placeholder.PlaceholderComponent
    org.spongepowered.api.service.placeholder.PlaceholderComponent.Builder


Obtaining a PlaceholderParser
=============================

:javadoc:`PlaceholderParser {PlaceholderParsers}` are stored in the Sponge registry, meaning they can be obtained the
same way as any other :javadoc:`CatalogType`:

.. code-block:: java

  Sponge.getRegistry().getType(PlaceholderParser.class, id);


.. tip::

  Remember that the ``PlaceholderParser`` ID is of the form ``pluginid:placeholderid``, for example ``sponge:name``.

Creating Text from a PlaceholderParser
======================================

A ``PlaceholderParser`` requires a :javadoc:`PlaceholderContext` in order to generate an appropriate :javadoc:`Component`
object. ``PlaceholderContexts`` can be created by using a :javadoc:`PlaceholderContext.Builder` obtained from the 
:javadoc:`PlaceholderContext#builder()` method.

The builder allows for the following optional context to be provided:

* An associated object, allowing for the placeholder to modify its output (this will usually be a :javadoc:`Player` or 
  other :javadoc:`Audience`)
* An argument string that a ``PlaceholderParser`` can parse

A built ``PlaceholderContext`` can then be supplied to the ``PlaceholderParser`` by using 
:javadoc:`PlaceholderParser#parse(PlaceholderContext)`.

For example, if you wish to include a player's name using the ``sponge:name`` parser, you could do the following:

.. code-block:: java
  
  Player player = ...;

  // We know this exists
  PlaceholderParser parser = PlaceholderParsers.NAME;
  PlaceholderContext context = PlaceholderContext.builder()
      .associatedObject(player)
      .build();
  Component text = parser.parse(context);
  

If the player name is "SpongePlayer", the returned text will say ``SpongePlayer``

Including Placeholders in Text
==============================

Placeholders can also be used in ``Component`` and :javadoc:`ComponentBuilder` objects without parsing them
first. Sponge provides a :javadoc:`PlaceholderComponent` object that bundles a ``PlaceholderParser`` and 
``PlaceholderContext`` together.

To create a ``PlaceholderComponent``, use :javadoc:`PlaceholderComponent#builder()` and add the ``PlaceholderParser`` and 
``PlaceholderContext`` objects as appropriate. You can then use the built ``PlaceholderComponent`` in the ``Component`` objects.

If you wished to use the parser and context from the previous example in a ``Component``, you could write the following:

.. code-block:: java
    
    PlaceholderComponent placeholderText = PlaceholderComponent.builder().context(context).parser(parser).build();
    Component result = Component.text("Hello! Your name is ")
        .append(placeholderText)
        .append(Component.text("!"));

The text will say "Hello! Your name is SpongePlayer!"

.. note::
    
    A ``PlaceholderComponent`` will be parsed as soon as it is added to a ``Component`` or ``ComponentBuilder`` using 
    :javadoc:`Component#append(Component)`.
