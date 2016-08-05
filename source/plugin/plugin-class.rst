=================
Main Plugin Class
=================

.. javadoc-import::
    org.spongepowered.api.event.Listener
    org.spongepowered.api.event.game.state.GameConstructionEvent
    org.spongepowered.api.event.game.state.GamePreInitializationEvent
    org.spongepowered.api.event.game.state.GameInitializationEvent
    org.spongepowered.api.event.game.state.GamePostInitializationEvent
    org.spongepowered.api.event.game.state.GameLoadCompleteEvent
    org.spongepowered.api.event.game.state.GameAboutToStartServerEvent
    org.spongepowered.api.event.game.state.GameStartingServerEvent
    org.spongepowered.api.event.game.state.GameStartedServerEvent
    org.spongepowered.api.event.game.state.GameStoppingServerEvent
    org.spongepowered.api.event.game.state.GameStoppedServerEvent
    org.spongepowered.api.plugin.Plugin

.. note::

    The instructions within the Sponge Documentation assume that you have prior knowledge of Java. The Sponge API
    provides the foundation for you to begin creating plugins for Minecraft servers powered by Sponge; however, it is
    up to you to be creative and make your code work! There are several free Java courses online if you have had little
    experience with Java.

Starting Your Class
===================

The next step after adding the Sponge API as a dependency is creating a new class. The class can be named however you
like, and can be in any package that does **not** begin with ``org.spongepowered``. By convention, class names should be
in title case.

Oracle `recommends <http://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html>`_ to use your domain as your
package name, if you own a domain. However, in the event that you do not own a domain, a common practice is to use an
email address (such as ``com.gmail.username.project``) or an open-source repository
(such as ``io.github.username.project``).

After creating your main class, the :javadoc:`Plugin` annotation must be affixed to it. This annotation allows Sponge
to easily find your main plugin class when your plugin is loaded. An example usage is illustrated below.

.. code-block:: java

    package io.github.username.project;

    import org.spongepowered.api.plugin.Plugin;

    @Plugin(id = "exampleplugin", name = "Example Plugin", version = "1.0")
    public class ExamplePlugin {

    }

.. note::
    Refer to :doc:`plugin-identifier` if you've not chosen your plugin ID yet.

Initializing Your Plugin
========================

Plugins are loaded before the game and the world(s). This leaves a specific timeframe when your plugin should begin
interacting with the game, such as registering commands or events.

Your plugin can listen for particular events, called **state events**, to be notified about changes in the state of the
game. In the example below, ``onServerStart()`` is called when the :javadoc:`GameStartedServerEvent` occurs; take note
of the :javadoc:`Listener` annotation before the method.

.. code-block:: java

    import org.spongepowered.api.event.Listener;
    import org.spongepowered.api.event.game.state.GameStartedServerEvent;

    @Plugin(id = "exampleplugin", name = "Example Plugin", version = "1.0")
    public class ExamplePlugin {
        @Listener
        public void onServerStart(GameStartedServerEvent event) {
            // Hey! The server has started!
            // Try instantiating your logger in here.
            // (There's a guide for that)
        }
    }

.. tip::

    The Sponge documentation provides a guide with more information on events (see :doc:`event/index`). Normally, in addition
    to prefixing event-handler methods with ``@Listener``, you must also register your object with Sponge's event bus.
    However, your main plugin class is registered automatically.

State Events
~~~~~~~~~~~~

It may also be desirable to listen for other state events, particularly the ``GameStoppingServerEvent``. There are two
categories of state events:

* **Initialization**: These events occur when Sponge and plugins are loading.

  * :javadoc:`GameConstructionEvent`
  * :javadoc:`GamePreInitializationEvent`
  * :javadoc:`GameInitializationEvent`
  * :javadoc:`GamePostInitializationEvent`
  * :javadoc:`GameLoadCompleteEvent`
* **Running**: These events occur when the game and world are loading.

  * :javadoc:`GameAboutToStartServerEvent`
  * :javadoc:`GameStartingServerEvent`
  * :javadoc:`GameStartedServerEvent`
  * :javadoc:`GameStoppingServerEvent`
  * :javadoc:`GameStoppedServerEvent`

For information regarding when each state event occurs, see the :doc:`plugin lifecycle documentation <lifecycle>`.
