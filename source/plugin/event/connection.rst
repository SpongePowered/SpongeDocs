======
Connection Events
======

.. javadoc-import::
    org.spongepowered.api.network.ServerSideConnection
    org.spongepowered.api.event.network.ServerSideConnectionEvent
    org.spongepowered.api.event.network.ServerSideConnectionEvent.Auth
    org.spongepowered.api.event.network.ServerSideConnectionEvent.Disconnect
    org.spongepowered.api.event.network.ServerSideConnectionEvent.Intent

.. note::
    The :javadoc:`ServerSideConnectionEvent` events were reworked in API 11 to address common usability pitfalls
    and provide extra guarantees regarding threading and event ordering.

Connection events are crucial part of plugins and their incorrect usage might be lead to
data corruption, race conditions or memory leaks. This documentation goes over the common
best practices that should be followed to ensure good experience.

Async, Threading And Event Order
================================
Incoming connections can be fragile and their connection state can suddenly change. Connections are also
handled asynchronously up to the point the connections authenticity has been verified. This allows plugins to
load related user data from a database during :javadoc:`ServerSideConnectionEvent.Auth` event. Correctly handling
these edge cases can prove to be difficult. To help with this, Sponge provides extra guarantees.

After a plugin has observed connection event, Sponge ensures that events are ordered correctly and no more than
single event is in-flight for that particular :javadoc:`ServerSideConnection`. You are also guaranteed to
receive :javadoc:`ServerSideConnectionEvent.Disconnect` for all incoming connections that has got past
:javadoc:`ServerSideConnectionEvent.Intent`.

.. warning::
    The ordering guarantees covered in this section does not apply to API versions prior to 11. You are also not
    guaranteed to receive ``ServerSideConnectionEvent.Disconnect`` before the player has successfully joined
    the world. Caches and proper housekeeping is recommended to handle any potential edge cases.

.. note::
    Sponge does not provide guarantees that only single authentication request is processed for one
    particular user. This may happen due to a race conditions and slow connectivity. The plugin developer
    must be aware of this caveat and potentially provide safety-checks and syncronization primitives.

Example A
~~~~~~~~~
- Server processes incoming connection.
- Event ``ServerSideConnectionEvent.Intent`` is fired.
- Connection drops.
- Event ``ServerSideConnectionEvent.Disconnect`` is fired.

Example B
~~~~~~~~~
- Server processes incoming connection.
- Event ``ServerSideConnectionEvent.Intent`` is fired.
- Event ``ServerSideConnectionEvent.Auth`` is fired.
    - Plugin A starts to load user data.
    - Connection drops. Auth event is still being processed, disconnect event is queued.
    - Plugin A finishes loading user data.
- Event ``ServerSideConnectionEvent.Disconnect`` is fired.

Best Practices
==============
Due to the nature of connection events being asynchronously, plugin developers **must** use thread-safe
collections when storing, accessing and mutating related data.

Loading User Data From A Database
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
The following example shows how to correctly load user data from a database and associate it with
the users unique id. This example is simplistic and assumes the data is immutable or that it may be stale.

.. code-block:: java

    public class ConnectionListener {

        private final ConcurrentMap<ServerSideConnection, UserData> connections = new ConcurrentHashMap<>();
        private final ConcurrentMap<UUID, UserData> users = new ConcurrentHashMap<>();

        @Listener
        private void onAuth(ServerSideConnectionEvent.Auth event) {
            UserData userData = //Load from database
            this.connections.put(event.connection(), userData);
        }

        @Listener
        private void onLogin(ServerSideConnectionEvent.Login event) {
            UserData userData = this.connections.get(event.connection());
            this.users.put(event.profile().uniqueId(), userData);
        }

        @Listener
        private void onDisconnect(ServerSideConnectionEvent.Disconnect event) {
            UserData userData = this.connections.remove(event.connection());
            event.profile().ifPresent(profile -> this.users.remove(profile.uniqueId(), userData));
        }
    }

This approach solves the following problems:

- Each connection has its own unique ``UserData``. This is used to identify which connection owns it.
    - Multiple overlapping authentication requests do not fight each other.
- After the user has been successfully authenticated, it is promoted to the ``users`` map.
    - While multiple authentication requests are possible, while highly unlikely, there may only be a single authenicated connection at most.
- On disconnection the ``UserData`` from the owning connection must match the one in the ``users`` map.
    - This ensures that failed authentications do not clear the successfully authenicated connections state erroneously.

Saving User Data To A Database
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Saving user data and making sure that you do not receive stale data on authentication can prove to be challanging.
This is especially true on proxied environments where the user can quickly switch between servers. While we are not going
to discuss more complex setup than single server instance here, you need to be aware that the following example is not suitable
in these environments.

.. code-block:: java

    public class ConnectionListener {

        private final ConcurrentMap<ServerSideConnection, UserData> connections = new ConcurrentHashMap<>();
        private final ConcurrentMap<UUID, UserData> users = new ConcurrentHashMap<>();

        @Listener
        private void onAuth(ServerSideConnectionEvent.Auth event) {
            UserData userData = new UserData();
            this.connections.put(event.connection(), userData);
            UserData oldUserData = this.users.get(event.profile().uniqueId());
            if (oldUserData != null) {
                userData.copyFrom(oldUserData);
                oldUserData.waitForSave();
            } else {
                userData.loadFromDatabase();
            }
        }

        @Listener
        private void onLogin(ServerSideConnectionEvent.Login event) {
            UserData userData = this.connections.get(event.connection());
            userData.activate();
            this.users.put(event.profile().uniqueId(), userData);
        }

        @Listener
        private void onDisconnect(ServerSideConnectionEvent.Disconnect event) {
            UserData userData = this.connections.remove(event.connection());
            if (!userData.active()) {
                return;
            }
            event.profile().ifPresent(profile -> {
                saveAsync(() -> {
                    //Save user data
                    userData.confirmSave();
                    this.users.remove(profile.uniqueId(), userData);
                });
            });
        }

This approach builds upon the previous example and solves the following problems:

- User data is marked as active in `ServerSideConnectionEvent.Login`.
    - This allows for easy identification for whatever we need to potentially perform a save.
- User data is saved upon disconnection. This is done asynchronously, and only AFTER the user has been successfully saved, may it be removed.
    - This informs the load process that there is a pending save and performing fresh load could potentially result in stale data.
- On authentication, it's crucial we always instantiate a new unique object for user data that is not equal to the old one.
    - This prevents any pending loads from erroneously "cancelling" any saves.
- On authentication, we cooperate with pending saves.
    - It's important to WAIT here for the save to finish before processing.
        - With the current design, if we would not wait for the save to finish, the user data object would be overridden prematurely.
        - If you were to fetch the user object from the database before waiting, you could receive stale data.
        - It's possible to not wait here if chaining is implemented, where new saves are queued after the previous ones.
    - We can potentially copy the data from the old ``UserData`` to avoid the extra database roundtrip.
    - To signal that the save has finished, you can use ``CompletableFuture`` or other related synchronization primitives.