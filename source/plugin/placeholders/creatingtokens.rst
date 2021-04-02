===========================
Creating Placeholder Tokens
===========================

.. javadoc-import::
    net.kyori.adventure.text.Component
    org.spongepowered.api.CatalogType
    org.spongepowered.api.plugin.PluginContainer
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.event.game.GameRegistryEvent.Register
    org.spongepowered.api.service.economy.Currency
    org.spongepowered.api.service.economy.EconomyService
    org.spongepowered.api.service.economy.account.Account
    org.spongepowered.api.service.placeholder.PlaceholderService
    org.spongepowered.api.service.placeholder.PlaceholderContext
    org.spongepowered.api.service.placeholder.PlaceholderParser
    org.spongepowered.api.service.placeholder.PlaceholderComponent

At the heart of the Sponge Placeholder API is the ability to create your own tokens and have them accessible to all 
plugins. To create your own placeholder, you must create an object that implements :javadoc:`PlaceholderParser` and
register it in the Sponge Registry.

Creating PlaceholderParsers
===========================

There are two ways you can create a ``PlaceholderParser``:

* Using :javadoc:`PlaceholderParser#builder()`, supplying your :javadoc:`PluginContainer`, un-namespaced ID and 
  a function that takes a ``PlaceholderContext`` and returns a :javadoc:`Component`.
* Directly implement the interface.

.. note::
  ``PlaceholderParsers`` are :doc:`Catalog Types<../data/catalog-types>`. If you implement the interface directly,
  remember that the ID of the parser should be plugin namespaced, of the form  ``[pluginid]:[placeholderid]``. IDs 
  must also be unique.

:javadoc:`PlaceholderParser` objects take a :javadoc:`PlaceholderContext` object which contains the context of the
request and returns a ``Component`` based on that context. Information that the ``PlaceholderContext`` may 
contain includes:

* An associated object, such as a :javadoc:`Player`
* An argument string which generally will be provided by a templating engine

This is the minimum as specified by Sponge. 

If your placeholder is unable to provide text because the context does not provide the context or arguments it requires,
the placeholder should return an empty ``Component`` and not throw an exception.

.. tip::
  If you wish to provide the ability to add multiple arguments to your placeholder, consider specifying a way to split 
  up the argument string.
  
  Remember to tell users of your plugin what you expect your argument string to look like.

Example: Default World Name PlaceholderParser
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This ``PlaceholderParser`` attempts to get the default world's name, returning an empty ``Component`` if it cannot be 
found. It uses the builder to create the parser with ID ``spongedocs:defaultworld``, assuming the plugin has an ID of 
``spongedocs``.

.. code-block:: java

    import net.kyori.adventure.text.Component;
    
    PluginContainer thisPlugin = ...;
    
    PlaceholderParser parser = PlaceholderParser.builder()
        .plugin(this.thisPlugin)
        .id("defaultworld")
        .name("Default World Placeholder")
        .parser(placeholderContext -> {
            return Sponge.getServer()
                .getDefaultWorld()
                .map(x -> x.getWorldName())
                .orElse(Component.empty());
        })
        .build();

Example: Player Location PlaceholderParser
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This ``PlaceholderParser`` attempts to get the player's location in the world. If used without a ``Player`` as the 
associated object, it returns an empty ``Component``. This implements the ``PlaceholderParser`` interface directly.

.. code-block:: java

    import net.kyori.adventure.text.TextComponent;

    public class PlayerLocationPlaceholder implements PlaceholderParser {
        
        @Override
        public String getId() {
            return "spongedocs:location"
        }

        @Override
        public String getName() {
            return "Location Placeholder"
        }

        @Override
        public Component parse(PlaceholderContext placeholderContext) {
            placeholderContext.associatedObject()
                .filter(x -> x instanceof Player)
                .map(player -> ((Player) player).getLocation())
                .map(location -> TextComponent.ofChildren(
                    Component.text("World: "),
                    Component.text(location.getExtent().getName()),
                    Component.text(" - "),
                    Component.text(location.getPosition())))
                .orElse(Component.empty());
        }
    }


Example: Current Time PlaceholderParser
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This ``PlaceholderParser`` returns the current time in the server's local timezone. If the string "UTC" is provided as
the argument string, it returns the current time in the UTC time zone. This implements the ``PlaceholderParser`` 
interface directly.

.. code-block:: java

    public class CurrentTimePlaceholder implements PlaceholderParser {

        @Override
        public String getId() {
            return "spongedocs:currenttime";
        }

        @Override
        public String getName() {
            return "Current Time parser";
        }

        @Override
        public Component parse(PlaceholderContext placeholderContext) {
            if (placeholderContext.argumentString().filter(x -> x.equalsIgnoreCase("UTC")).isPresent()) {
                return Component.text(OffsetDateTime.now(ZoneOffset.UTC).format(FORMATTER));
            }
            return Component.text(OffsetDateTime.now().format(FORMATTER));
        }

    }


Registering Your PlaceholderParser
==================================

For your parser to be easily accessible to other plugins, it must be registered in the registry. This should be done
by listening to the :javadoc:`GameRegistryEvent.Register<PlaceholderParser>` event and registering your parsers using 
the :javadoc:`GameRegistryEvent.Register<PlaceholderParser>#register(T) {register}` method.

Example: Registering a PlaceholderParser
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    PlaceholderParser parser = ...;
    
    @Listener
    public void registerTokensEvent(GameRegistryEvent.Register<PlaceholderParser> event) {
        event.register(this.parser);
    }
