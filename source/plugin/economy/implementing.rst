============================
Implementing the Economy API
============================

.. javadoc-import::
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

Things to consider when implementing the Economy API
====================================================

* Do I want to include support for multiple currency types?
* Do I want to my plugin to perform different actions when given different contexts?

Implementing the Economy API is fairly straightforward, and the `JavaDocs <http://jd.spongepowered.org>`_ can be a
great tool if you are unsure what a method is supposed to do.
