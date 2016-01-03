=====================
Using the Economy API
=====================

The Economy API unifies all economy plugins under one API. This means any plugin using the Economy API
will be compatible with all economy plugins that implement the API. This page guides you through the steps of using 
the Economy API in your own plugin.

Loading the EconomyService
==========================

In order to utilize the Economy API, you must first load the EconomyService class:

1. Listen to the ``ChangeServiceProviderEvent`` in order to grab an instance of the EconomyService when it is 
registered.

2. When the event is fired, check if the service added was the ``EconomyService``. If the it was the 
``EconomyService``, assign it to a variable that can be used to later access the Economy API.

**Example: Loading the EconomyService**

.. code-block:: java

	import org.spongepowered.api.event.Listener;
	import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
	import org.spongepowered.api.service.economy.EconomyService;

	private EconomyService economyService;
	
	@Listener
	public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
		if (event.getService().equals(EconomyService.class)) {
			Object rawProvider = event.getNewProviderRegistration().getProvider();
			if (rawProvider instanceof EconomyService) {
				economyService = (EconomyService) rawProvider;
			}
		}
	}
	
Using the EconomyService
========================

After loading the ``EconomyService`` and assigning it to a variable, you are ready to access all of the features the 
Economy API has to offer. You can view said functions 
`here <https://jd.spongepowered.org/3.0.0/?org/spongepowered/api/service/economy/EconomyService.html>`_.

**Example: Getting a player's balance**

.. code-block:: java
	
	import org.spongepowered.api.entity.living.player.Player;
	import org.spongepowered.api.service.economy.EconomyService;
	import org.spongepowered.api.service.economy.account.UniqueAccount;
	import java.math.BigDecimal;
	import java.util.Optional;

	Optional<UniqueAccount> uOpt = economyService.getAccount(player.getUniqueId());
	if (uOpt.isPresent()) {
		UniqueAccount acc = uOpt.get();
		BigDecimal balance = acc.getBalance(economyService.getDefaultCurrency());
	}
	
Some ``Account`` methods require variables such as:

- Currency: The currency involved in the exchange
- Cause: What caused the change to the account
- Context: The context that the change occurred in

These are for more advanced uses, but still must be filled in. Below is a list of acceptable default values:

- Currency: ``economyService.getDefaultCurrency()``
- Cause: ``Cause.of("MyPluginName")``
- Context: ``new HashSet<Context>()``