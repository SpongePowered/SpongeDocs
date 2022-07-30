=================
Main Plugin Class
=================

.. javadoc-import::
    org.spongepowered.api.Server
    org.spongepowered.api.event.Listener
    org.spongepowered.api.event.lifecycle.StartedEngineEvent
    org.spongepowered.plugin.builtin.jvm.Plugin

.. note::

    The instructions within the Sponge Documentation assume that you have prior knowledge of Java. SpongeAPI
    provides the foundation for you to begin creating plugins for Minecraft servers powered by Sponge; however, it is
    up to you to be creative and make your code work! There are several free Java courses online if you have had little
    experience with Java.

Starting Your Class
===================

The next step after adding SpongeAPI as a dependency is creating a new class. The class can be named however you
like, and can be in any package that does **not** begin with ``org.spongepowered``. By convention, class names should be
in title case.

Oracle `recommends <https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html>`_ to use your domain as your
package name, if you own a domain. However, in the event that you do not own a domain, a common practice is to use an
email address (such as ``com.gmail.username.project``) or an open-source repository
(such as ``io.github.username.project``).

After creating your main class, the :javadoc:`Plugin` annotation must be affixed to it. This annotation allows Sponge
to easily find your main plugin class when your plugin is loaded. An example usage is illustrated below, more detailed
usage is explained on :doc:`plugin-meta`.

.. code-block:: java

    package io.github.username.project;

    import org.spongepowered.plugin.builtin.jvm.Plugin;

    @Plugin("exampleplugin")
    public class ExamplePlugin {

    }

.. note::
    Refer to :doc:`plugin-identifier` if you've not chosen your plugin ID yet.


Initializing Your Plugin
========================

Your plugin can listen for particular events, called **lifecycle events**, to be notified about changes in the state of 
the game or be to prompted to peform a specific task, such as registering a command. In the example below, 
``onServerStart(StartedEngineEvent<Server>)`` is called when the :javadoc:`StartedEngineEvent` occurs for the 
:javadoc:`Server`. Note that the method is annotated with the :javadoc:`Listener` annotation.

The example below will log a message upon starting the server. If your plugin is correctly loaded, you should see this 
message as part of the server's initialization output.

.. code-block:: java

    import org.spongepowered.api.event.Listener;
    import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
    import org.spongepowered.plugin.builtin.jvm.Plugin;

    // Imports for logger
    import com.google.inject.Inject;
    import org.apache.logging.log4j.Logger;

    @Plugin("exampleplugin")
    public class ExamplePlugin {

        @Inject
        private Logger logger;

        @Listener
        public void onServerStart(final StartedEngineEvent<Server> event) {
            logger.info("Successfully running ExamplePlugin!!!");
        }

    }

.. tip::

    The Sponge documentation provides a guide with more information on events (see :doc:`event/index`). Normally, in
    addition to prefixing event-handler methods with ``@Listener``, you must also register your object with Sponge's
    event bus, which can be done at any time. However, your main plugin class is registered automatically.

Lifecycle Events
~~~~~~~~~~~~~~~~

It may also be desirable to listen for other lifecycle events in your plugin, such that you can react to re-registration
requests or engine/game state changes. See the :doc:`plugin lifecycle documentation <lifecycle>` for more information on
the lifecycle events available for plugins to listen to.
