========
Services
========

.. javadoc-import::
    org.spongepowered.api.service.permission.PermissionService


If you need to get an object reference to something, just get it off the service manager.

Service Guidelines
==================

* Services should be registered during the ``POST_INITIALIZATION`` game state at the latest.
* Services should be fully operational by the ``SERVER_ABOUT_TO_START`` game state.

You can read more about game states on the :doc:`lifecycle` page.

.. note::

    It is a good practice to register services as soon as possible so that other plugins can note that the service will
    be provided.

Providing your own service
==========================
Your plugin can provide the implementation for a core interface like :javadoc:`PermissionService`

.. code-block:: java

    Sponge.getServiceManager().setProvider(Object plugin, Class<T> service, T provider);

The ``provider`` object has to implement the ``service`` interface or class.

Designing the API this way makes Sponge extremely modular.

.. note::

    Plugins should provide options to not install their providers if the plugin is not dedicated to a single function.
