================
Writing a Plugin
================

.. toctree::
   :maxdepth: 2

Your Plugin Class
=================

In order to make a plugin, you need to create a new class with a name of your choosing and in any package (that does **not** begin with org.spongepowered).

`Oracle Recommends <http://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html>`_  that if you own a domain to use the domain as your package name. However if you do not own a domain, a common practice is using an email address com.gmail.exampleuser.project or if open source and hosted on a repository such as github io.github.username.project


However, in order to make Sponge recognize your plugin, you need to affix a ``@Plugin`` annotation to the class as illustrated below.

.. code-block:: java

    package example;
    
    import org.spongepowered.api.plugin.Plugin;
    
    @Plugin(id = "example", name = "Example Plugin", version = "1.0")
    public class ExamplePlugin {
    
    }

When loading your plugin .jar file, Sponge will find your plugin class automatically and load it.

Be sure to also change the ID and name of your plugin, as well its version. The ID should be a simple identifier without special characters or spaces, as it is used for generating configuration files and letting users refer to your plugin by its ID.

Initializing Your Plugin
========================

Plugins are loaded before the game and worlds are loaded, so there is a specific time when you should start interacting with the game, registering commands, and performing other startup functions.

To be notified when the time is right, your plugin needs to listen to a particular *event*. Sponge comes with many events, but a few of these are called *state events* because they indicate changes in the state of the game. When you listen to an event, you create an *event handler*. This is illustrated below.

.. code-block:: java

    package example;
    
    import org.spongepowered.api.event.state.ServerStartedEvent;
    import org.spongepowered.api.plugin.Plugin;
    import org.spongepowered.api.util.event.Subscribe;
    
    @Plugin(id = "example", name = "Example Plugin", version = "1.0")
    public class ExamplePlugin {
    
        @Subscribe
        public void doThisOnServerStart(ServerStartedEvent event) {
        }
    
    }

The ``@Subscribe`` annotation tells Sponge that whenever the ``ServerStartedEvent`` event occurs, the method *doThisOnServerStart* should be called (you are free to name the method however you like).

.. tip::
    Normally, in addition to marking your event handlers with ``@Subscribe``, you must also register your object with Sponge's event bus; however, your main plugin class is automatically registered.
    
    If you need to register event handlers on another object, see :doc:`the event bus documentation <event-bus>`.

State Events
------------

There are two categories of state events:

1. **Initialization:** When Sponge and plugins are loading, before the actual game has started.
2. **Running:** When the game and world are loading.

The initialization events occur once each and occur in the following order:

1. **ConstructionEvent**: Fired when your plugin is constructed.
2. **PreInitializationEvent:** Fired when your plugin should be doing anything that should occur before the next step (initialization).
3. **InitializationEvent:** Fired when event handlers, services, commands, and other things should be registered with Sponge.
4. **PostInitializationEvent:** Fired when plugins should start asking each other for resources, including querying for services.
5. **LoadCompleteEvent:** Fired when everything should be loaded already and the game / server is ready to start.

The running events may occur more than once:

6. **ServerAboutToStartEvent:** Fired when the game / server is about to start and worlds have not yet been loaded.
7. **ServerStartingEvent:** Fired when worlds have been loaded.
8. **ServerStartedEvent:** Fired after the previous event.
9. **ServerStoppingEvent:** Fired right before the final tick and before a world save has occurred.
10. **ServerStoppedEvent:** Fired when the game / server has shutdown and no changes to the world will be committed to disk.

It is possible for Sponge to skip directly to ServerStoppedEvent if a critical error occurs during loading.

For more information, see the :doc:`plugin lifecycle documentation <plugin-lifecycle>`.
