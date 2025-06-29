===================
Offline Player Data
===================

.. javadoc-import::
    org.spongepowered.api.entity.living.player.server.ServerPlayer
    org.spongepowered.api.entity.living.player.User
    org.spongepowered.api.profile.GameProfileManager
    org.spongepowered.api.user.UserManager

It may be necessary for plugins to access player data even when the player is offline.
You might think that ``Sponge.server().player()`` returning a :javadoc:`ServerPlayer` can be used for this.
But since all ``Player`` objects only exist for online players, 
another solution must be used.

Some plugins store the relevant data themselves and associate the user by using the :javadoc:`GameProfileManager`.
But writing different code for offline and online users is not necessary.
The :javadoc:`UserManager` is capable of returning :javadoc:`User` instances for ``Player``\s who are currently 
offline. 

Code Example
------------

Here's an example for a utility method that can be used to get a ``User``:

.. code-block:: java
    
    import java.util.Optional;
    import java.util.UUID;
    import java.util.concurrent;
    
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.entity.living.player.User;
    import org.spongepowered.api.user.UserManager;
    
    public CompletableFuture<Optional<User>> getUser(UUID uuid) {
        UserManager userManager = Sponge.server().userManager();
        return userManager.load(uuid);
    }

This code will get the ``UserManager`` and then retrieve the ``User`` from there. 

.. note::

    The ``UserManager`` will use local cached versions of the user, however will contact Mojang servers if
    the user has not previously joined, hence why the load returns a ``CompletableFuture``. If the user 
    cannot be found in either cache or Mojang then the ``CompletableFuture`` will return ``Optional.empty`` 

.. note::

    You can check if the player is loaded in cache with the ``exists(UUID playerUuid)`` method found in
    ``UserManager``
