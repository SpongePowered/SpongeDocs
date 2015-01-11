==================
Accessing Services
==================
Pretty much everything (events, permissions, etc.) is handled through services. 
All services are accessed through the service manager:

.. code-block:: java

    game.getServiceManager().provide(PermissionsService.class);
    
If you need to get an object reference to something, just get it off the service manager.

Providing your own service
~~~~~~~~~~~~~~~~~~~~~~~~~~
Your plugin can provide the implementation for a core interface like ``PermissionService``,
or for a custom interface that is not part of the Sponge API (e.g. economy, web server):

.. code-block:: java

    game.getServiceManager().setProvider(Object plugin, Class<T> service, T provider);

The ``provider`` object has to implement the ``service`` interface or class.

Designing the API this way makes Sponge extremely modular.

.. note::

    Plugins should provide options to not install their providers if the plugin is not dedicated to a single function.

