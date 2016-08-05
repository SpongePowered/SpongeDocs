======
Events
======

.. javadoc-import::
	org.spongepowered.api.event.Cancellable

Events are used to inform plugins of certain happenings. Many events can also be *cancelled* -- that is, the action that
the event refers to can be prevented from occurring. Cancellable events implement the :javadoc:`Cancellable` interface.

Sponge itself contains many events; however, plugins can create their own events which other plugins can listen to.

Event listeners are assigned a priority that determines the order in which the event listener is run in context of other
event listeners (such as those from other plugins). For example, an event listener with ``EARLY`` priority will return
before most other event listeners. See :ref:`about_listener` for more information.

Events cannot be sent to a specific set of plugins. All plugins that listen to an event will be notified of the event.

The event bus or event manager is the class that keeps track of which plugins are listening to which event,
and is also responsible for distributing events to event listeners.

Sponge provides a built in callback for plugin reloading. See the :ref:`game-reload` section for more information.


Contents
========

.. toctree::
	:maxdepth: 2
	:titlesonly:

	listeners
	causes
	filters
	custom
