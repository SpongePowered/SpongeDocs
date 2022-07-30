========
Services
========

.. javadoc-import::
    java.util.function.Supplier
    org.spongepowered.api.Game
    org.spongepowered.api.Server
    org.spongepowered.api.event.lifecycle.ProvideServiceEvent
    org.spongepowered.api.service.ServiceProvider
    org.spongepowered.api.service.ServiceProvider.GameScoped
    org.spongepowered.api.service.ServiceProvider.ServerScoped
    org.spongepowered.api.service.permission.PermissionService
    org.spongepowered.api.service.permission.Subject


Services provide common server functionality that is intended to be provided by plugins so that plugins that wish to use
this functionality do not need to depend on any third-party plugins. Some services, such as the 
:javadoc:`PermissionService`, are tightly integrated into the API itself, providing the core functionality for some of
the API (for example, the ``PermissionService`` supports various methods on the :javadoc:`Subject` interface).

Services themselves can be :javadoc:`Server` scoped or :javadoc:`Game` scoped. Due to the potential for servers restarting
multiple times within a game instance, server scoped services should at least change instance across restarts, and may 
be provided by different plugins.

.. note::
    In previous versions of SpongeAPI, plugins could register their own arbitary services to arbitary interfaces. This
    is no longer possible using the Sponge service provider in SpongeAPI 8 and later.

Obtaining a Service
===================

Services can be retrieved from the :javadoc:`ServiceProvider` via the ``serviceProvider`` method on either 
``Game`` or ``Server``. Inspect :javadoc:`ServiceProvider.GameScoped` and 
:javadoc:`ServiceProvider.ServerScoped` for details of what services are provided in these scopes.

Providing a Service
===================

Service implementations are provided during the generic :javadoc:`ProvideServiceEvent`. For example, to supply a
``PermissionService``, you would listen to the event like so (swapping out the "new" call for your service
construction code):

.. code-block:: java

    import org.spongepowered.api.event.Listener
    import org.spongepowered.api.event.lifecycle.ProvideServiceEvent
    import org.spongepowered.api.service.permission.PermissionService

    @Listener
    public void providePermissionService(final ProvideServiceEvent.EngineScoped<PermissionService> event) {
        event.suggest(() -> new ConcretePermissionService());
    }

It is important to note that it is possible that your listener will never get called. It is left up to the 
API implementation to decide which plugin to pick to provide a given service. With this in mind, it is highly
recommended that you do not construct your service implementation yourself, instead doing so within the 
:javadoc:`Supplier` supplied to the ``suggest`` method.
