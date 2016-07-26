===================
Offline Player Data
===================

It may be necessary for plugins to access player data even when the player is offline.
One could guess that ``Sponge.getServer().getPlayer()`` returning a ``Player`` can be used for this.
But since ``Player`` objects only exist for online players, another solution has to be used.

Some plugins store the relevant data themselves and associate the user by using the ``GameProfileManager``.
But writing different code for offline and online users is not necessary:
The ``ServiceManager`` natively provides a service known as the ``UserStorageService`` which is capable of returning ``User`` instances for ``Player``\s who are currently offline.
Since the ``Player`` interface extends ``User`` most methods you call on a ``Player`` are also available. 

For example:

* ``#hasPermission(String permission)`` is available from both instances.

Code Example
----------------------------------

Here's an example for a utility method that can be used to get a User:

.. code-block:: java

    //(Only sponge imports listed)
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.entity.living.player.User;
    import org.spongepowered.api.service.user.UserStorageService;
    
    /**
     * A method that can return offline and online users
     * @return Optional<User> User reference. Non present means unknown
     */
    public Optional<User> getUser(UUID uuid) {
        
        Optional<Player> onlinePlayer = Sponge.getServer().getPlayer(uuid);
    
        //if the user is online, all work is done
        if (onlinePlayer.isPresent())
            return onlinePlayer;
            
        //Player is not online, use UserStorageService
        //See "Services" for detailed info on this:
        Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
        
        if (!userStorage.isPresent()) {
            //UserStorageService wasn't found
            //Although this is not supposed to happen, include some warning here.
            //You could send an error to your log for example
        }
        
        Optional<User> user = userStorage.get().get(uuid);
        
        //Since we return an optional there's nothing more to do
        return user;
        
    }


This solution can be used to get online and offline Users which makes it dynamically usable within your plugin.
Now you only need to differenciate online and offline when it's really necessary.
    
    
UUID or UserName ?  
~~~~~~~~~~~~~~~~~~

Most guides here give another example about how to use usernames instead of UUIDs.  
If you want to use usernames instead of UUIDs, apply these replacements:  

* The name of the variable ``uuid`` with ``username`` or better ``lastKnownUsername``  
* The type ``UUID`` with ``String``  
