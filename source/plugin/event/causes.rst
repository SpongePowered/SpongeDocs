============
Event Causes
============

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs> __`

.. javadoc-import::
    org.spongepowered.api.Sponge
    org.spongepowered.api.event.CauseStackManager
    org.spongepowered.api.event.Event
    org.spongepowered.api.event.block.ChangeBlockEvent
    org.spongepowered.api.event.block.ChangeBlockEvent.Grow
    org.spongepowered.api.event.cause.Cause
    org.spongepowered.api.event.cause.Cause.Builder
    org.spongepowered.api.event.cause.EventContext
    org.spongepowered.api.event.cause.EventContext.Builder
    org.spongepowered.api.event.cause.EventContextKey
    org.spongepowered.api.event.cause.EventContextKeys
    org.spongepowered.api.event.entity.DamageEntityEvent
    org.spongepowered.api.plugin.PluginContainer
    org.spongepowered.api.profile.GameProfile
    java.lang.Class
    java.lang.Object
    java.util.Stack

Events are great for attaching additional logic to game actions, but they have the drawback of providing next to no
context as to what has **caused** that event to occur. The :javadoc:`Cause` object allows providing and receiving
additional contextual information about the event. This contextual information can then be used to modify the behavior
of your event listener.

For example, a world protection plugin needs information on what player has caused a :javadoc:`ChangeBlockEvent` to
occur before they can decide if the event should be cancelled or not. Rather than go with the traditional route of
creating a multitude of subevents for the different source conditions this information is instead provided in the
``Cause`` of the event.

Every event provides a ``Cause`` object which can be interrogated for the information pertaining to why the event was
fired. The Cause object can be retrieved from an event by simply calling :javadoc:`Event#getCause()`.

Cause and Context
~~~~~~~~~~~~~~~~~

The ``Cause`` object contains two distinct sets of information, the cause stack and the :javadoc:`EventContext`.

* The cause stack of the event are the direct causes captured in order of importance. There are no names attached
  to the objects in the cause stack.
* The event context contains extra information surrounding the event. Contexts are attached to keys but have no
  order, they are all equally important.

As an example, if a sheep owned by a player eats some grass, the most direct cause of this is the sheep. The
player would be in the ``EventContext`` as the :javadoc:`EventContextKeys#OWNER`, giving event consumers
that additional information about how the event has come about, but would not necessarily be within the
direct cause itself.

Another example that you may need to watch out for is if you simulate a player. The simulated player may not be
in the direct cause, as the player being simulated may not have been involved in the action, however, the player
would be in the ``EventContext`` under the :javadoc:`EventContextKeys#PLAYER_SIMULATED`

Retrieving Objects from the Direct Cause
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Structurally, a ``Cause`` object contains a sequential list of objects. There are several methods of
retrieving information from a Cause object which we will discuss here, for a more complete
listing please see the `Javadocs <https://jd.spongepowered.org>`_.

.. note::

    The objects within a cause are ordered such that the first object is the most immediate
    cause of the event, and subsequent objects are of decreasing importance and/or may only
    provide contextual information.

:javadoc:`Cause#root()` returns the first object within the cause. This object is the most immediate or direct cause of
the event. Since a ``Cause`` may not be empty, it is guaranteed to have a ``root``.

:javadoc:`Cause#first(Class)` returns the first object in the cause chain whose type is either the same as or is a
subtype of the given class. For example, given a cause which contained a player followed by an entity
``[Player, Entity, ...]``

.. code-block:: java

    @Listener
    public void onEvent(ExampleCauseEvent event) {
        Cause cause = event.getCause(); // [Player, Entity]
        Optional<Player> firstPlayer = cause.first(Player.class); // 1
        Optional<Entity> firstEntity = cause.first(Entity.class); // 2
    }

Both optionals would contain the player object as its type directly matched request for a
Player type and it matched the request for an Entity type as Player is a subtype of Entity.

:javadoc:`Cause#last(Class)` is similar to ``Cause#first(Class)`` except it returns the last value in the cause chain
matching the type.

Continuing from the example above, if we instead changed it to call ``Cause#last(Class)`` the first
optional would contain the player object still, but the second optional would now contain
the entity that we passed in the second position of the cause.

:javadoc:`Cause#containsType(Class)` returns a boolean value and can be used to check if a cause chain contains any
object matching the provided type.

:javadoc:`Cause#all()` simply returns all objects within the cause allowing more advanced handling.

Event Context
~~~~~~~~~~~~~

Sometimes the ordering of objects within the cause isn't enough to get the proper idea of what an object represents in
relation to the event. This is where :javadoc:`EventContext` comes in. The event context allows objects to be
associated with unique names, in the form of :javadoc:`EventContextKeys`, allowing them to be easily identified and
requested. Some examples of use cases for named causes is the `Notifier` of a :javadoc:`ChangeBlockEvent.Grow` or the
``Source`` of a :javadoc:`DamageEntityEvent`.

Unlike the cause stack, which makes no guarantees as to the objects contained witin it, an object associated with a
:javadoc:`EventContextKey` is guaranteed to be of the type specified by the key.

**Retrieving a entry from the context of a cause**

.. code-block:: java

    @Listener
    public void onGrow(ChangeBlockEvent.Grow event) {
        Optional<User> notifier = event.getCause().getContext().get(EventContextKeys.NOTIFIER);
    }

This example makes use of :javadoc:`EventContext#get(EventContextKey)` which can be used to retrieve the expected object
associated with a name if it is present within the context. Additionally :javadoc:`EventContext#asMap()` provides
a ``Map<EventContextKey<?>, Object>`` which can be used to find all present ``EventContextKey``\s and their associated
objects.

.. note::

    Some common identifying names for ``EventContextKey``\s are present as static fields in the
    ``EventContextKeys`` class.

Creating Custom Causes
~~~~~~~~~~~~~~~~~~~~~~

Creating a cause is easy, but depends on whether you are creating your cause on the main server
thread or async.

.. note::

    Cause objects are immutable therefore cannot be modified once created.

Using the CauseStackManager
===========================

.. warning::

    The ``CauseStackManager`` only works on the main server thread. If you call it from a
    different thread, an ``IllegalStateException`` will be thrown. Ensure you are on the main
    server thread **before** calling methods on the ``CauseStackManager``.

If you are creating your event on the main thread, then use the :javadoc:`CauseStackManager`, which can
be found at :javadoc:`Sponge#getCauseStackManager()`. The ``CauseStackManager`` tracks the potential
causes of events as the game runs, allowing for easy retrieval of the current ``Cause`` without effort.
To see the current cause, call :javadoc:`CauseStackManager#getCurrentCause()`. You may notice that your
plugin's :javadoc:`PluginContainer` is already in the returned ``Cause``, as plugins are one of the
objects tracked by the manager. Using the ``CauseStackManager`` for creating causes removes the
need for boilerplate-like code where you supply objects like your plugin container, so that you can
concentrate on adding your own causes.

Before adding your own causes, you should push a cause stack frame to the manager. Adding a frame acts
as a saved state, when you are done with your causes, the removal of the frame returns the manager to
its original state.

.. tip::

    Adding a frame to the CauseStackManager does not remove what is already in the manager, so anything
    that is in the cause stack and contexts before a stack frame is added will be there afterwards. You
    can verify this by calling ``Sponge.getCauseStackManager().getCurrentCause()`` before and after the
    frame is pushed.

    For example, if the cause stack contains a ``PluginContainer`` and a ``CommandSource`` when a frame
    is pushed, they will remain on the stack and will form part of the ``Cause`` if one is obtained from
    the frame.

For example, if you were to fire an event that was simulating another player in a sudo like command,
you may want to add the player you are acting as in the cause and the ``GameProfile`` of the player that you are
simulating in the context (as the simulated player is not directly responsible for the event being fired.)

**Creating a custom Cause with the CauseStackManager**

In this example, the variables would be populated, the cause would contain the ``playerToSimulate`` as
the root cause, the ``sourceRunningSudo`` as the second object in the cause and the :javadoc:`GameProfile`
as the :javadoc:`EventContextKeys#PLAYER_SIMULATED` context, in addition to anything already in the
``CauseStackManager``. Your event code would be at the bottom of the method.

.. code-block:: java

    CommandSource sourceRunningSudo = ...;
    Player playerToSimulate = ...;
    try (CauseStackManager.StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {

      frame.pushCause(sourceRunningSudo);
      frame.pushCause(playerToSimulate);

      frame.addContext(EventContextKeys.PLAYER_SIMULATED, playerToSimulate.getProfile());

      Cause cause = frame.getCurrentCause();
    }

Note that the last item you push to the cause stack will be the root of the ``Cause`` as
stacks are "last in, first out" (LIFO) structures.

.. tip::

  For more information about the stack data type and why the order matters, see the
  :javadoc:`Stack` javadocs or `this Wikipedia article <https://en.wikipedia.org/wiki/Stack_(abstract_data_type)>`_.

Using the Cause Builder
=======================

If you are creating an event that does not fire on the main thread, you cannot use the
``CauseStackManager``. Instead, you will need to create a ``Cause`` object manually.

Creating a cause object is easy using the :javadoc:`Cause.Builder`. You can obtain a
builder by calling ``Cause.builder()``. To add a cause to the builder, use the
:javadoc:`Cause.Builder#append(Object)` method, but note that unlike the ``CauseStackManager``,
the first element you add will be the root, not the last.

If you wish to add contexts, there is a separate builder for those, the
:javadoc:`EventContext.Builder`, accessed by calling ``EventContext#builder()``.
The ``EventContext`` can then be added using the ``Cause.Builder#build(EventContext)`` when
you have finished building the ``Cause`` up.

Taking the previous example, this is how we would build it using the cause builder.

**Creating a custom Cause with the Cause and EventContext builders**

Note that in this example, the variables would be populated, and that the first entry appended
to the cause would be the root cause.

.. code-block:: java

    CommandSource sourceRunningSudo = ...;
    Player playerToSimulate = ...;
    PluginContainer plugin = ...;

    EventContext context = EventContext.builder()
      .add(EventContextKeys.PLAYER_SIMULATED, playerToSimulate.getProfile())
      .add(EventContextKeys.PLUGIN, plugin)
      .build();

    Cause cause = Cause.builder()
      .append(playerToSimulate)
      .append(sourceRunningSudo)
      .append(plugin)
      .build(context);

Think carefully about what information to include in your cause.
If you're firing an event from your plugin which is usually triggered through other means,
it is a good idea to include your ``PluginContainer`` in the cause so other plugins know
that the event comes from your plugin. If you are firing the event on behalf of a player
due to some action it's usually a good idea to include that player in the cause.
