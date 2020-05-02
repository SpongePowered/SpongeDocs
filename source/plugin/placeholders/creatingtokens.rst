===========================
Creating Placeholder Tokens
===========================

.. javadoc-import::
    org.spongepowered.api.CatalogType
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.event.game.GameRegistryEvent.Register
    org.spongepowered.api.service.placeholder.PlaceholderService
    org.spongepowered.api.service.placeholder.PlaceholderParser
    org.spongepowered.api.service.placeholder.PlaceholderText
    org.spongepowered.api.text.Text
    org.spongepowered.api.text.channel.MessageReceiver

At the heart of the Sponge Placeholder API is the ability to create your own tokens and have them accessible to all 
plugins. To create your own placeholder, you must create an object that implements :javadoc:`PlaceholderParser` and
register it in the Sponge Registry.

:javadoc:`PlaceholderParser {PlaceholderParsers}` are independent of a plugin's :javadoc:`PlaceholderService`.

Implementing PlaceholderParser
==============================

.. note::
  :javadoc:`PlaceholderParser {PlaceholderParsers}` are :doc:`Catalog Types<../data/catalog-types>`. As with all
  Catalog Types, the ID of the parser should be plugin namespaced, and therefore be of the form  
  ``[pluginid]:[placeholderid]``, and must be unique.

:javadoc:`PlaceholderParser` objects take a :javadoc:`PlaceholderText` object which contains the context of the
request and returns a :javadoc:`Text` based on that context. Information that the :javadoc:`PlaceholderText` may 
contain includes:

* An associated :javadoc:`MessageReceiver`, such as a :javadoc:`Player`
* An argument string which has been provided by a templating engine

This is the minimum as specified by Sponge. If another plugin replaces the :javadoc:`PlaceholderService`, there may a
:javadoc:`PlaceholderText` that provides more information.

If your placeholder is unable to provide text because there is insufficient information, the placeholder should return
and empty :javadoc:`Text` and not throw an exception.

Registering Your PlaceholderParser
==================================

For your parser to be easily accessible to other plugins, it must be registered in the registry. This should be done
by listening to the :javadoc:`GameRegistryEvent.Register<PlaceholderParser>` event and registering your parsers using 
the :javadoc:`GameRegistryEvent.Register<PlaceholderParser>#register(T) {register}` method.
