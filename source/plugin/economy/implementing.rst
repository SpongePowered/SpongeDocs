============================
Implementing the Economy API
============================

.. javadoc-import::
    org.spongepowered.api.event.economy.EconomyTransactionEvent
    org.spongepowered.api.service.economy.Currency
    org.spongepowered.api.service.economy.EconomyService
    org.spongepowered.api.service.economy.account.UniqueAccount
    org.spongepowered.api.service.economy.account.VirtualAccount
    org.spongepowered.api.service.economy.transaction.TransactionResult
    org.spongepowered.api.service.economy.transaction.TransferResult

Sponge provides interfaces for economy plugins to implement and create an API. In order to build a complete Economy
API, you must implement six classes:

* :javadoc:`Currency`
* :javadoc:`EconomyService`
* :javadoc:`TransactionResult`
* :javadoc:`TransferResult`
* :javadoc:`UniqueAccount`
* :javadoc:`VirtualAccount`
* :javadoc:`EconomyTransactionEvent`

Registering Your Economy Service
================================

When it comes to registering any service in Sponge, you can provide your service as an option with Sponge deciding 
which service to use if requested. This means that if two Economy Service plugins are used on a server, only one
of the services will be used. 

.. code-block:: java

    @Listener
    public void registerEconomyService(ProvideServiceEvent.EngineScoped<MyCustomEconomyService> event){
        event.suggest(() -> new MyCustomEconomyService());
    }

Things to consider when implementing the Economy API
====================================================

* Do I want to include support for multiple currency types?
* Do I want to my plugin to perform different actions when given different contexts?

Implementing the Economy API is fairly straightforward, and the `JavaDocs <https://jd.spongepowered.org>`_ can be a
great tool if you are unsure what a method is supposed to do.
