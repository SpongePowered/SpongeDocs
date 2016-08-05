===================
Offline Player Data
===================

.. javadoc-import::
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.entity.living.player.User
    org.spongepowered.api.profile.GameProfileManager
    org.spongepowered.api.service.ServiceManager
    org.spongepowered.api.service.user.UserStorageService

It may be necessary for plugins to access player data even when the player is offline.
You might think that ``Sponge.getServer().getPlayer()`` returning a :javadoc:`Player` can be used for this.
But since ``Player`` objects only exist for online players, another solution must be used.

Some plugins store the relevant data themselves and associate the user by using the :javadoc:`GameProfileManager`.
But writing different code for offline and online users is not necessary.
The :javadoc:`ServiceManager` natively provides a service known as the :javadoc:`UserStorageService` which is capable
of returning :javadoc:`User` instances for ``Player``\s who are currently offline.
Since the ``Player`` interface extends ``User`` most methods you call on a ``Player`` are also available. 

For example:

* ``#hasPermission(String permission)`` is available from both instances.

Code Example
------------

Here's an example for a utility method that can be used to get a ``User``:

.. code-block:: java
    
    import java.util.Optional;
    import java.util.UUID;
    
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.entity.living.player.User;
    import org.spongepowered.api.service.user.UserStorageService;
    
    public Optional<User> getUser(UUID uuid) {
        Optional<Player> onlinePlayer = Sponge.getServer().getPlayer(uuid);
    
        if (onlinePlayer.isPresent()) {
            return onlinePlayer;
        }
        
        Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
        
        return userStorage.get().get(uuid);
    }

First check if a ``Player`` instance exists meaning that the user is online.
If so, the object is returned. 
If not get the ``UserStorageService`` from the ``ServiceManager`` and then retrieve the ``User`` from there.

.. note::

    The ``UserStorageService`` can only return ``User``\s who previously were connected.

.. tip::

    This solution can be used to get online and offline ``User``\s which makes it dynamically usable within your plugin.    
