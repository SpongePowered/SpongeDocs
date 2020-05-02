===================================
Retrieving and Parsing Placeholders
===================================

.. javadoc-import::
    org.spongepowered.api.service.placeholder.PlaceholderService
    org.spongepowered.api.service.placeholder.PlaceholderParser
    org.spongepowered.api.service.placeholder.PlaceholderText
    org.spongepowered.api.service.placeholder.PlaceholderText.Builder
    org.spongepowered.api.text.Text
    org.spongepowered.api.text.TextRepresentable
    org.spongepowered.api.text.channel.MessageReceiver


Obtaining A PlaceholderParser
=============================

There are two ways to obtain a registered :javadoc:`PlaceholderParser`:

* Directly obtain the parser from the Sponge Registry by calling 
  ``Sponge.getRegistry().getType(PlaceholderParser.class, id);``; or
* Obtain the parser via :javadoc:`PlaceholderService#getParser(String)`.

In general, we recommend that the second option is used, as plugin-provided implementations may allow for shorthand 
tokens to resolve to a parser, such as accepting ``name`` as a tokne, retrieving the parser for ``sponge:name``, 
thereby allowing for a consistent experience across entire servers.

Creating Text From A PlaceholderParser
======================================

A :javadoc:`PlaceholderParser` requires a :javadoc:`PlaceholderText` in order to generate an appropriate :javadoc:`Text`
object. :javadoc:`PlaceholderText {PlaceholderTexts}` can be created by using a :javadoc:`PlaceholderText.Builder` 
obtained from :javadoc:`PlaceholderService#placeholderBuilder()`.

The builder, as a minimum, allows for the following context to be provided:

* The :javadoc:`PlaceholderParser` to use to generate the :javadoc:`Text` (this must be provided)
* An associated :javadoc:`MessageReceiver`, allowing for the placeholder to modify its output
* An argument string that the :javadoc:`PlaceholderParser` can parse, often provided by templating engines

If the service is not the Sponge provided service, the builder may be able to provide more context, though this will 
require depending on the service providing plugin in question.

A built :javadoc:`PlaceholderText` is a :javadoc:`TextRepresentable`, meaning it can be used in :javadoc:`Text` objects
like other text-based objects. This will generate the appropriate text.

Examples
========

If you wish to include a player's name using the ``sponge:name`` parser, you could do the following:

.. code-block:: java
  
  Player player = ...;
  PlaceholderService placeholderService = Sponge.getServiceManager()
      .provideUnchecked(PlaceholderService.class);

  // We know this exists
  PlaceholderParser nameParser = placeholderService.getParser("sponge:name").get();
  PlaceholderText placeholderText = placeholderService.placeholderBuilder()
      .setParser(nameParser)
      .setAssociatedSource(player)
      .build();
  
  // Use in text like this
  player.sendMessage(Text.of("Hello! Your name is ", placeholderText, "!"));

You can also use the shorthand methods like so:

.. code-block:: java
  
  Player player = ...;
  PlaceholderService placeholderService = Sponge.getServiceManager()
      .provideUnchecked(PlaceholderService.class);

  // We know this exists
  PlaceholderText placeholderText = placeholderService.parse("sponge:name", player).get();
  
  // Use in text like this
  player.sendMessage(Text.of("Hello! Your name is ", placeholderText, "!"));


If the player name is "SpongePlayer", either example will send the player the message 
``Hello! Your name is SpongePlayer!``
