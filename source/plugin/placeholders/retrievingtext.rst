===================================
Retrieving and Parsing Placeholders
===================================

.. javadoc-import::
    org.spongepowered.api.CatalogType
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.placeholder.PlaceholderContext
    org.spongepowered.api.placeholder.PlaceholderContext.Builder
    org.spongepowered.api.placeholder.PlaceholderParser
    org.spongepowered.api.placeholder.PlaceholderText
    org.spongepowered.api.placeholder.PlaceholderText.Builder
    org.spongepowered.api.text.Text
    org.spongepowered.api.text.Text.Builder
    org.spongepowered.api.text.TextRepresentable
    org.spongepowered.api.text.channel.MessageReceiver


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

A ``PlaceholderParser`` requires a :javadoc:`PlaceholderContext` in order to generate an appropriate :javadoc:`Text`
object. ``PlaceholderContexts`` can be created by using a :javadoc:`PlaceholderContext.Builder` obtained from the 
:javadoc:`PlaceholderContext#builder()` method.

The builder allows for the following optional context to be provided:

* An associated object, allowing for the placeholder to modify its output (this will usually be a :javadoc:`Player` or 
  other :javadoc:`MessageReceiver`)
* An argument string that a ``PlaceholderParser`` can parse

A built ``PlaceholderContext`` can then be supplied to the ``PlaceholderParser`` by using 
:javadoc:`PlaceholderParser#parse(PlaceholderContext)`.

For example, if you wish to include a player's name using the ``sponge:name`` parser, you could do the following:

.. code-block:: java
  
  Player player = ...;

  // We know this exists
  PlaceholderParser parser = Sponge.getRegistry().getType(PlaceholderParser.class, "sponge:name").get();
  PlaceholderContext context = PlaceholderContext.builder()
      .setAssociatedObject(player)
      .build();
  Text text = parser.parse(context);
  

If the player name is "SpongePlayer", the returned text will say ``SpongePlayer``

Including Placeholders in Text
==============================

Placeholders can also be used in ``Text.of(...)`` and :javadoc:`Text.Builder` objects without parsing them
first. Sponge provides a :javadoc:`PlaceholderText` object that bundles a ``PlaceholderParser`` and 
``PlaceholderContext`` together into a :javadoc:`TextRepresentable`.

To create a ``PlaceholderText``, use :javadoc:`PlaceholderText#builder()` and add the ``PlaceholderParser`` and 
``PlaceholderContext`` objects as appropriate. You can then use the built ``PlaceholderText`` in the ``Text`` objects.

If you wished to use the parser and context from the previous example in ``Text.of()``, you could write the following:

.. code-block:: java
    
    PlaceholderText placeholderText = PlaceholderText.builder().setContext(context).setParser(parser).build();
    Text result = Text.of("Hello! Your name is ", placeholderText, "!");

The text will say "Hello! Your name is SpongePlayer!"

.. note::
    
    A ``PlaceholderText`` will be parsed when the ``Text`` it is placed in is built, that is, either when placed in
    ``Text.of(...)```, or when added to a :javadoc:`Text.Builder` and :javadoc:`Text.Builder#build()` is called.
