==============
Basic Concepts
==============

The Sponge Economy API has a few basic components that developers should be familiar with:

* EconomyService
* Currency
* Accounts
* Transactions

The EconomyService
==================

The ``EconomyService`` is the basis of the Economy API. It is used to interact via the Economy API, stores an
economy's currencies and provides methods for account management. The ``EconomyService`` JavaDocs can be found
`here <https://jd.spongepowered.org/index.html?org/spongepowered/api/service/economy/EconomyService.html>`__.

.. warning::

	Sponge does not provide a default implementation for the ``EconomyService``. It's completely up to plugins to
	implement the Economy API. This means that you have to rely on another plugin which implements the service or you'll
	have to implement it yourself, if you want to make use of it.

Currency
========

The `org.spongepowered.api.service.economy.Currency <https://jd.spongepowered.org/org/spongepowered/api/service/economy/Currency.html>`_
object represents a form of Currency. ``Currency`` stores a display name (plural and singular), a symbol, the amount of
fractional digits, and whether the currency is the default currency for the economy. If the economy plugin chooses, it
can support multiple currencies.

Accounts
========

Accounts are used to store economy information about a specific player or other object (i.e. bank, business, entity).
There are two account types in the Sponge Economy API:

Virtual Accounts
~~~~~~~~~~~~~~~~

Virtual accounts are tied to an identifier, which is stored as a string. Virtual accounts can be tied to almost
anything, but are commonly used for things such as banks, or non-players. To get the id of a virtual account, use
``getIdentifier()``.

Unique Accounts
~~~~~~~~~~~~~~~

Unique accounts are tied to a UUID, usually a player. To get the UUID of an unique account, use ``getUUID()``.

Transactions
============

Transactions represent an account's change in balance. There are currently three types of transactions:

- Deposit: Occurs when an account has funds added to it
- Withdraw: Occurs when an account has funds removed from it
- Transfer: Occurs when an account exchanges funds with another account

When a transfer occurs, the ``EconomyTransactionEvent`` is fired. Using this event, you can get the ``TransactionResult``.
The ``TransactionResult`` stores data about the transaction that occurred, including:

- Account involved
- Currency involved
- Amount of currency involved
- Transaction type
- Result of the transaction

You can view all possible transaction results at the
`Sponge JavaDocs <https://jd.spongepowered.org/index.html?org/spongepowered/api/service/economy/transaction/ResultType.html>`_.
