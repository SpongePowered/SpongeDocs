===========================
Creating Placeholder Tokens
===========================

.. javadoc-import::
    org.spongepowered.api.CatalogType
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.event.game.GameRegistryEvent.Register
    org.spongepowered.api.service.economy.Currency
    org.spongepowered.api.service.economy.EconomyService
    org.spongepowered.api.service.economy.account.Account
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

Example: Default World Name PlaceholderParser
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This :javadoc:`PlaceholderParser` attempts to get the default world's name, returning an empty :javadoc:`Text` if it
cannot be found.

.. code-block:: java

    public class DefaultWorldNameParser implements PlaceholderParser {
        
        @Override
        public String getId() {
            return "spongedocs:defaultworld"
        }

        @Override
        public String getName() {
            return "Default World Placeholder"
        }

        @Override
        public Text parse(PlaceholderText placeholderText) {
            return Sponge.getServer()
                .getDefaultWorld()
                .map(x -> x.getWorldName())
                .orElse(Text.EMPTY);
        }
    }

Example: Player Location PlaceholderParser
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This :javadoc:`PlaceholderParser` attempts to get the player's location in the world. If used without a 
:javadoc:`Player` as the associated receiver, returns an empty :javadoc:`Text`.

.. code-block:: java

    public class PlayerBalancePlaceholder implements PlaceholderParser {
        
        @Override
        public String getId() {
            return "spongedocs:balance"
        }

        @Override
        public String getName() {
            return "Balance Placeholder"
        }

        @Override
        public Text parse(PlaceholderText placeholderText) {
            placeholderText.getAssociatedReceiver()
                .filter(x -> x instanceof Player)
                .map(player -> ((Player) player).getLocation())
                .map(location -> Text.of("World: ", location.getExtent().getName(), " - ", location.getPosition()))
                .orElse(Text.EMPTY);
        }
    }


Example: Player Balance PlaceholderParser
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This :javadoc:`PlaceholderParser` attempts to get the player's balance, with an optional argument that may contain the 
ID of a currency to check, else checking the default currency. It does the following:

* Checks if an :javadoc:`EconomyService` exists
* Checks if the associated receiver in the :javadoc:`PlaceholderText` is a :javadoc:`Player` 
* Gets the string returned by :javadoc:`PlaceholderText#getArgumentString()`

  * If the string exists and matches a valid :javadoc:`Currency` and the player has an :javadoc:`Account` in that 
    currency, returns the balance of that account.
  * If the string exists and does not match a valid :javadoc:`Currency`, or the player does not have an 
    :javadoc:`Account` in that currency, returns an empty :javadoc:`Text`.
  * If the string does not exist, if the player has an :javadoc:`Account` in the default :javadoc:`Currency`, return
    that balance, else return an empty :javadoc:`Text`.

.. code-block:: java

    public class PlayerBalancePlaceholder implements PlaceholderParser {
        
        @Override
        public String getId() {
            return "spongedocs:balance"
        }

        @Override
        public String getName() {
            return "Balance Placeholder"
        }

        @Override
        public Text parse(PlaceholderText placeholderText) {
            // First: check the EconomyService is available
            Optional<EconomyService> optionalEconService = Sponge.getServiceManager()
                .provide(EconomyService.class);
            if (optionalEconService.isPresent()) {
                EconomyService econService = optionalEconService.get();

                // This placeholder only applies to players, so check that
                Optional<Player> player = placeholderText.getAssociatedReceiver()
                    .map(x -> x instanceof Player ? (Player) x : null);
                if (player.isPresent()) {

                    // Check the argument string, try to get a valid currency out of it
                    Currency currency;
                    if (placeholderText.getArgumentString().isPresent()) {
                        // If we have an argument string, for this placeholder, we assume the 
                        // entire string is the argument - so is it a valid Currency ID?
                        Optional<Currency> co = Sponge.getRegistry()
                            .getType(Currency.class, placeholderText.getArgumentString().get());
                        if (!co.isPresent()) {
                            // If not, then the placeholder is invalid and should return an empty text.
                            return Text.EMPTY;
                        }
                        // It is, so this is what we use
                        currency = co.get();
                    } else {
                        currency = econService.getDefaultCurrency();
                    }

                    // Finally, check the account exists, and if so, return the value
                    Optional<UniqueAccount> account = econService
                        .getOrCreateAccount(player.get().getUniqueId());
                    return account.map(x -> Text.of(x.getBalance(currency))).orElse(Text.EMPTY);
                }
            }

            return Text.EMPTY;
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
