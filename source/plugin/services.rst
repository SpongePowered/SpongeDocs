========
Services
========

.. javadoc-import::
    org.spongepowered.api.service.permission.PermissionService

Pretty much everything (events, permissions, etc.) is handled through services. All services are accessed through the
service manager:

.. code-block:: java

    import org.spongepowered.api.Sponge;

    Sponge.getServiceManager().provide(EventManager.class);

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
Your plugin can provide the implementation for a core interface like :javadoc:`PermissionService`, or for a custom
interface that is not part of SpongeAPI (e.g. economy, web server):

.. code-block:: java

    Sponge.getServiceManager().setProvider(Object plugin, Class<T> service, T provider);

The ``provider`` object has to implement the ``service`` interface or class.

Designing the API this way makes Sponge extremely modular.

.. note::

    Plugins should provide options to not install their providers if the plugin is not dedicated to a single function.

Example: Providing a simple warp service
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The first step is optional, but recommended. You specify the public methods of your service class in an interface:

.. code-block:: java

    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;
    import java.util.Optional;

    public interface WarpService {
        void setWarp(String name, Location<World> location);
        Optional<Location<World>> getWarp(String name);
    }

Now you can write the class that implements your interface:

.. code-block:: java

    import java.util.HashMap;

    public class SimpleWarpService implements WarpService {
        HashMap<String, Location<World>> warpMap = new HashMap<String, Location<World>>();

        @Override
        public Optional<Location<World>> getWarp(String name) {
            if(!warpMap.containsKey(name)) {
                return Optional.empty();
            } else {
                return Optional.of(warpMap.get(name));
            }
        }

        @Override
        public void setWarp(String name, Location<World> location) {
            warpMap.put(name, location);
        }
    }

Now we can register a new instance of the class in the service manager. We are using the interface
``WarpService.class`` as the ``service`` key.

This makes it possible for other plugin developers to write their own implementation of your service (that implements
the interface) and replace your version.

.. code-block:: java

    Sponge.getServiceManager().setProvider(plugin, WarpService.class, new SimpleWarpService());

Other plugins can now access your service through the service manager:

.. code-block:: java

    Sponge.getServiceManager().provide(WarpService.class);

.. tip::
    If you don't want to use interfaces,
    just replace the ``service`` key with your class (``SimpleWarpService.class`` in the example).
