=====================
Using the Economy API
=====================

.. javadoc-import::
    org.spongepowered.api.service.economy.EconomyService
    org.spongepowered.api.service.economy.account.Account
    org.spongepowered.api.service.ServiceManager
    org.spongepowered.api.event.service.ChangeServiceProviderEvent

The Economy API unifies all economy plugins under one API. This means any plugin using the Economy API
will be compatible with all economy plugins that implement said API. This page guides you through the steps of using
the Economy API in your own plugin.

Loading the EconomyService
==========================

In order to utilize the Economy API, you must first load the :javadoc:`EconomyService` class using the :javadoc:`ServiceManager`. 

.. warning::
  Please note that you need to pay attention to different ``game states`` while the server is starting, stopping or
  running when using services like the Economy API. Take a look at the :doc:`/plugin/services` page for further
  information.



Example: Loading the EconomyService
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

	import org.spongepowered.api.service.economy.EconomyService;
	import org.spongepowered.api.Sponge;

	Optional<EconomyService> serviceOpt = Sponge.getServiceManager().provide(EconomyService.class);
	if (!serviceOpt.isPresent()) {
	    // handle there not being an economy implementation
	}
	EconomyService economyService = serviceOpt.get();
	
.. warning:: 
  Keep this service in a local variable instead of a member variable, since the provider (implementation) 
  could change at any point. If you need to place it in a member variable, for whatever reason, use 
  :javadoc:`ChangeServiceProviderEvent` to keep the implementation updated.

.. note::
  Unlike other services, you should try to use :javadoc:`ServiceManager#provide(java.lang.Class)` instead of
  :javadoc:`ServiceManager#provideUnchecked(java.lang.Class)` because Sponge does not provide a default implementation 
  of the :javadoc:`EconomyService`, and therefore it is not guaranteed that it exists.

Using the EconomyService
========================

After loading the ``EconomyService`` and assigning it to a variable, you are ready to access all of the features the
Economy API has to offer.

Example: Getting a player's balance
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.entity.living.player.Player;
    import org.spongepowered.api.service.economy.EconomyService;
    import org.spongepowered.api.service.economy.account.UniqueAccount;
    import java.math.BigDecimal;
    import java.util.Optional;
    
    Optional<UniqueAccount> uOpt = economyService.getOrCreateAccount(player.getUniqueId());
    if (uOpt.isPresent()) {
        UniqueAccount acc = uOpt.get();
    	BigDecimal balance = acc.getBalance(economyService.getDefaultCurrency());
    }

Some :javadoc:`Account` methods require variables such as:

* Currency: The currency involved in the exchange
* Cause: What caused the change to the account
* Context: The context that the change occurred in

These are for more advanced uses, but still must be filled in. Below is a list of acceptable default values:

* Currency: :javadoc:`EconomyService#getDefaultCurrency()`
* Cause: ``Cause.of(EventContext.builder().add(EventContextKeys.PLUGIN, plugin).build(), plugin)``
* Context: ``new HashSet<Context>()``
