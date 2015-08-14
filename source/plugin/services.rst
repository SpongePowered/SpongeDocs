=====================
Working with Services
=====================

Pretty much everything (events, permissions, etc.) is handled through services. All services are accessed through the
service manager:

.. code-block:: java

    game.getServiceManager().provide(EventManager.class);

If you need to get an object reference to something, just get it off the service manager.

Providing your own service
--------------------------
Your plugin can provide the implementation for a core interface like ``PermissionService``, or for a custom interface
that is not part of the Sponge API (e.g. economy, web server):

.. code-block:: java

    game.getServiceManager().setProvider(Object plugin, Class<T> service, T provider);

The ``provider`` object has to implement the ``service`` interface or class.

Designing the API this way makes Sponge extremely modular.

.. note::

    Plugins should provide options to not install their providers if the plugin is not dedicated to a single function.

Example: Providing a simple economy service
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The first step is optional, but recommended. You specify the public methods of your service class in an interface:

.. code-block:: java

    package test.service.economy;

    import org.spongepowered.api.entity.player.Player;

    public interface EconomyService
    {
    	public int getBalance(Player player);
    	public void setBalance(Player player, int amount);
    }

Now you can write the class that implements your interface:

.. code-block:: java

    package test.service.economy;

    import java.util.HashMap;
    import org.spongepowered.api.entity.player.Player;

    public class SimpleEconomyService implements EconomyService
    {
    	HashMap<Player, Integer> balanceMap = new HashMap<Player, Integer>();

    	@Override
    	public int getBalance(Player player)
    	{
    		if(!balanceMap.containsKey(player)) return 0; //no stored balance for player

    		else return balanceMap.get(player);
    	}

    	@Override
    	public void setBalance(Player player, int amount)
    	{
    		balanceMap.put(player, amount);
    	}
    }

Now we can register a new instance of the class in the service manager. We are using the interface
``EconomyService.class`` as the ``service`` key.

This makes it possible for other plugin developers to write their own implementation of your service (that implements
the interface) and replace your version.

.. code-block:: java

    game.getServiceManager().setProvider(yourPluginInstance, EconomyService.class, new SimpleEconomyService());

Other plugins can now access your service through the service manager:

.. code-block:: java

    game.getServiceManager().provide(EconomyService.class)

.. tip::
    If you don't want to use interfaces,
    just replace the ``service`` key with your class (``SimpleEconomyService.class`` in the example).
