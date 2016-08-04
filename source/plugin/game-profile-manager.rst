====================
Game Profile Manager
====================

.. javadoc-import::
    org.spongepowered.api.profile.GameProfile
    org.spongepowered.api.profile.GameProfileCache
    org.spongepowered.api.profile.GameProfileManager
    org.spongepowered.api.profile.property.ProfileProperty
    java.lang.Iterable
    java.lang.String
    java.util.UUID

A :javadoc:`GameProfile` represents the profile of a player, including such data as a name, ``UUID``, and other
arbitrary data known as properties. SpongeAPI provides the :javadoc:`GameProfileManager` class to get, create, and fill
``GameProfile``\ s. You may obtain an instance of the ``GameProfileManager`` using the following code.

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.profile.GameProfileManager;

    GameProfileManager profileManager = Sponge.getServer().getGameProfileManager();


Retrieving GameProfiles
=======================

It is important to note that Sponge maintains a cache of ``GameProfile``\ s to be used as a substitute to making a
request to the Mojang API every time a ``GameProfile`` is requested. The methods for retrieving ``GameProfile``\ s offer
a ``boolean`` second argument determining whether the cache will be used. By default, the cache will be used when
possible.

A ``GameProfile`` can be looked up using either a ``UUID`` or username. Note that the same profile will always be
returned when looking up by ``UUID``, but as usernames can be changed, this may not necessarily be the case when looking
up by username.

Retrieving by username
~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.profile.GameProfile;

    import java.util.concurrent.CompletableFuture;

    CompletableFuture<GameProfile> futureGameProfile = profileManager.get("Notch");

Retrieving by UUID
~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import java.util.UUID;

    CompletableFuture<GameProfile> futureGameProfile =
        profileManager.get(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5"));

.. tip::
    You can also retrieve many ``GameProfile``\ s at once using :javadoc:`GameProfileManager#getAllById(Iterable<UUID>,
    boolean)` or :javadoc:`GameProfileManager#getAllByName(Iterable<String>, boolean)`. Both of these methods return
    ``CompletableFuture<Collection<GameProfile>>``.

Note that each of these methods return some sort of ``CompletableFuture``. This indicates that the ``GameProfile``
(or ``Collection<GameProfile>``) may not be immediately available because of pending requests to the Mojang API. The
`Javadocs for CompletableFuture <https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html>`_
describe the full capabilities of the class, but we will focus on the ``get`` method for the purpose of this article.

To retrieve a ``GameProfile`` from a ``CompletableFuture<GameProfile``, you can simply call the ``CompletableFuture#get``
method.

.. code-block:: java

    GameProfile gameProfile = futureGameProfile.get();

.. warning::
    If the ``GameProfile`` is not immediately available (such as if the cache is not being used or does not contain the
    ``GameProfile``), then ``get`` will wait for the future to complete. For that reason, it is not advisable to use
    this on the main thread as it will halt the server. Alternatively, you can use the
    ``CompletableFuture#thenAccept(Consumer<? super T>)`` method to specify a ``Consumer`` to be run upon completion.

Creating GameProfiles
=====================

You can generate a new ``GameProfile`` using :javadoc:`GameProfile#of(UUID, String)`. Note that the username does not
necessarily need to correspond to the ``UUID`` of that player. Likewise, the ``UUID`` does not need to belong to a
valid player.

.. code-block:: java

    GameProfile gameProfile = GameProfile.of(
            UUID.fromString("00000000-0000-0000-0000-000000000000"),
            "Herobrine");

.. note::
    It is not mandatory to specify the name of the ``GameProfile`` (``null`` is a valid argument).

Filling GameProfiles
====================

Filling a ``GameProfile`` completes the profile by fetching information like the player's skin from the Mojang API.
Note that if faked data like username is associated with a certain UUID, it will be replaced by the actual data from
the Mojang API.

.. code-block:: java

    GameProfile filledProfile = profileManager.fill(gameProfile).get();

ProfileProperties
=================

``GameProfile``\ s can be used to store arbitrary data about a player using :javadoc:`ProfileProperty`\ s. However,
this cannot not be used as a permanent data store, as the data does not persist across server restarts. We can retrieve
the properties of a ``GameProfile`` using the :javadoc:`GameProfile#getPropertyMap()` method, which returns a
``Multimap``. From there, you can retrieve existing or store new ``ProfileProperty``\ s, which are represented as a key
value pair. To generate a new ``ProfileProperty``, simply call the :javadoc:`ProfileProperty#of(String, String)`
method. The third argument (signature) is optional. However, a valid signature from Mojang must be specified for
certain properties.

.. code-block:: java

    import org.spongepowered.api.profile.property.ProfileProperty;

    import java.util.Collection;

    profile.getPropertyMap().put(
        "key", ProfileProperty.of("foo", "bar", null));
    Collection<ProfileProperty> customProperties = profile.getPropertyMap().get("key");

GameProfileCache
================

You can also directly access the :javadoc:`GameProfileCache` used by Sponge to store ``GameProfile``\ s. To do so,
simply call the :javadoc:`GameProfileManager#getCache()` method. Using the ``GameProfileCache``, you can look up
``GameProfile``\ s, add newly constructed ``GameProfile``\ s, and fill profiles with data stored in the cache.

.. code-block:: java

    import org.spongepowered.api.profile.GameProfileCache;

    GameProfile fakeProfile =
        GameProfile.of(UUID.fromString("00000000-0000-0000-0000-000000000000"),
        "Herobrine");
    GameProfileCache cache = profileManager.getCache();
    cache.add(profile);

.. tip::
    ``GameProfileCache#add`` also accepts a ``boolean`` second argument determining whether existing cache entries
    should be overwritten, and a ``Date`` third argument setting the expiry of the ``GameProfile``.

The ``GameProfileCache`` may also be set by plugins with the :javadoc:`GameProfileManager#setCache(GameProfileCache)`
method. To restore the original cache, use the same method, passing in the result of
:javadoc:`GameProfileManager#getDefaultCache()`.
