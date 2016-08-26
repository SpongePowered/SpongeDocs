===============
Event Listeners
===============

.. javadoc-import::
    org.spongepowered.api.event.Event
    org.spongepowered.api.event.EventManager
    org.spongepowered.api.event.Listener
    org.spongepowered.api.event.Order
    org.spongepowered.api.event.SpongeEventFactory
    org.spongepowered.api.event.block.ChangeBlockEvent
    org.spongepowered.api.event.block.ChangeBlockEvent.Break
    org.spongepowered.api.event.cause.Cause
    org.spongepowered.api.event.game.GameReloadEvent
    org.spongepowered.api.plugin.Plugin
    java.lang.Object

In order to listen for an event, an event listener must be registered. This is done by making a method with any name,
defining the first parameter to be the desired event type, and then affixing the :javadoc:`Listener` annotation to the
method, as illustrated below.

.. code-block:: java

    import org.spongepowered.api.event.Listener;

    @Listener
    public void onSomeEvent(SomeEvent event) {
        // Do something with the event
    }

In addition, the class containing these methods must be registered with the event manager:

.. tip::

    For event listeners on your main plugin class (annotated by :javadoc:`Plugin`), you do not need to register the
    object for events as Sponge will do it automatically.


.. note::
    The event bus **supports supertypes**. For example, :javadoc:`ChangeBlockEvent.Break` extends
    :javadoc:`ChangeBlockEvent`. Therefore, a plugin could listen to ``ChangeBlockEvent`` and still receive
    ``ChangeBlockEvent.Break``\ s. However, a plugin listening to just ``ChangeBlockEvent.Break`` would not be notified
    of other types of ``ChangeBlockEvent``.


Registering and Unregistering Event Listeners
=============================================

To register event listeners annotated by ``@Listener`` that are not in the main plugin class, you can use
:javadoc:`EventManager#registerListeners(Object, Object)`, which accepts a reference to the plugin and an instance of
the class containing the event listeners.

**Example: Registering Event Listeners in Other Classes**

.. code-block:: java

    import org.spongepowered.api.Sponge;

    public class ExampleListener {

        @Listener
        public void onSomeEvent(SomeEvent event) {
            // Do something with the event
        }
    }

    Sponge.getEventManager().registerListeners(this, new ExampleListener());



Dynamically Registering Event Listeners
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Some plugins (such as scripting plugins) may wish to dynamically register an event listener. In that case the event
listener is not a method annotated with ``@Listener``, but rather a class implementing the ``EventListener`` interface.
This event listener can then be registered by calling ``EventManager#registerListener``, which accepts a reference to the
plugin as the first argument, the ``Class`` of events handled as the second argument, and the listener itself as the
final argument. Optionally, you can specify an :javadoc:`Order` to run the event listener in as the third argument or a
boolean value as the fourth argument (before the instance of the listener) which determines whether to call the listener
before other server modifications.

**Example: Implementing EventListener**

.. code-block:: java

    import org.spongepowered.api.event.EventListener;
    import org.spongepowered.api.event.block.ChangeBlockEvent;

    public class ExampleListener implements EventListener<ChangeBlockEvent.Break> {

        @Override
        public void handle(ChangeBlockEvent.Break event) throws Exception {
            ...
        }
    }

**Example: Dynamically Registering the Event Listener**

.. code-block:: java

    EventListener<ChangeBlockEvent.Break> listener = new ExampleListener();
    Sponge.getEventManager().registerListener(this, ChangeBlockEvent.Break.class, listener);

.. tip::

        For event listeners created with the ``@Listener`` annotation, the order of the execution can be configured
        (see also `About @Listener`_). For dynamically registered listeners this is possible by passing an ``Order``
        to the third argument the ``EventManager#registerListener`` method.


Unregistering Event Listeners
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To unregister a single event listener, you can use the :javadoc:`EventManager#unregisterListeners(Object)` method,
which accepts an instance of the class containing the event listeners.

.. code-block:: java

    EventListener listener = ...
    Sponge.getEventManager().unregisterListeners(listener);

Alternatively, you can use :javadoc:`EventManager#unregisterPluginListeners(Object)`, passing in a reference to the
plugin, to unregister all event listeners associated with that plugin. Note that this will remove *all* of the plugin's
event listeners, including those registered with ``@Listener`` annotations.

.. code-block:: java

    MyPlugin plugin = ...
    Sponge.getEventManager().unregisterPluginListeners(plugin);

.. _about_listener:

About @Listener
~~~~~~~~~~~~~~~~

The ``@Listener`` annotation has a few configurable fields:

* ``order`` is the priority in which the event listener is to be run. See the :javadoc:`Order` enum in the SpongeAPI to
  see the available options.
* ``beforeModifications`` specifies if the event listener should be called before other server mods, such as Forge
  mods. By default, this is set to false.

By default, ``@Listener`` is configured so that your event listener will *not* be called if the event in question is
cancellable and has been cancelled (such as by another plugin).

.. _game-reload:

GameReloadEvent
~~~~~~~~~~~~~~~

To prevent all plugins providing their own reload commands, Sponge provides a built in callback for plugins to listen
to, and when executed, perform any reloading actions. What constitutes as a 'reloading action' is purely up to the
plugin to decide. The :javadoc:`GameReloadEvent` will fire when a player executes the
``/sponge plugins reload`` command. The event is not necessarily limited to reloading configuration.

.. code-block:: java

    import org.spongepowered.api.event.game.GameReloadEvent;

    @Listener
    public void reload(GameReloadEvent event) {
        // Do reload stuff
    }

Note that this is different for what generally is considered a 'reload', as the event is purely all callback for
plugins and does not do any reloading on its own.

Firing Events
=============

To dispatch an event, you need an object that implements the :javadoc:`Event` interface.

You can fire events using the event bus (:javadoc:`EventManager`):

.. code-block:: java

    boolean cancelled = Sponge.getEventManager().post(theEventObject);

The method returns ``true`` if the event was cancelled, ``false`` if not.

Firing Sponge Events
~~~~~~~~~~~~~~~~~~~~

It is possible to generate instances of built-in events with the static :javadoc:`SpongeEventFactory`. The events
created by the ``SpongeEventFactory`` are then passed to :javadoc:`EventManager#post(Event)`.

Example: Firing LightningEvent
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.event.SpongeEventFactory;
    import org.spongepowered.api.event.action.LightningEvent;
    import org.spongepowered.api.event.cause.Cause;

    LightningEvent lightningEvent = SpongeEventFactory.createLightningEvent(Cause.source(plugin).build());
    Sponge.getEventManager().post(lightningEvent);

.. warning::

    A :javadoc:`Cause` can never be empty. At the very least it should contain your plugin container.
