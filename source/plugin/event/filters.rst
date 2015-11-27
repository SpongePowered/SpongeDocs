=============
Event Filters
=============

Now that you've spent a bit of time working with events you've probably noticed that there are several preconditions that you
very commonly check while writing an event listener. Event filters are a group of annotations that assist you by allowing you
to automatically validate aspects of the event, and if the validation fails then your listener will not be called. This allows
your listener to be dedicated to the logic of your handler, rather than the preconditions, resulting in cleaner code.

Event filters come in two varieties, event type and parameter filters.

Event type filters are method annotations that are applied to your listener method along with the ``@Listener`` annotation and
provide several filters based on the type or state of the event.

Parameter filters validate objects contained within the event such as the cause. They come in a further two varieties
parameter sources and parameter filters. Each additional parameter must have one source annotation, and optionally may include
any number of filter annotations.

Event Type Filters
==================

**@Include/@Exclude**
These two parameters are used in conjunction with listening for supertype events such as ``EntityEvent`` while narrowing the
events that you receive to just a subset of the events extending the event you're listening for.

For example:

.. code-block:: java

    @Listener
    @Exclude(InteractBlockEvent.Primary.class)
    public void onInterace(InteractBlockEvent event) {
        // do something
    }

This listener will be called for all events extending InteractBlockEvent **except** for the ``InteractBlockEvent.Primary``
event (leaving just the ``InteractBlockEvent.Secondary`` event).

An example with ``@Include`` could be:

.. code-block:: java

    @Listener
    @Include({InteractBlockEvent.Primary.class, InteractBlockEvent.Secondary.class})
    public void onInterace(InteractBlockEvent event) {
        // do something
    }

This listener will be called for all events extending InteractBlockEvent which are also in the list of events within the
``@Include`` annotation (which is all of them as it happens).

**@IsCancelled**
This annotation allows filtering events by their cancellation state at the time that your event listener would normally be
called. By default your event listener will not be called if the event has been cancelled by a previous event listener.
However you can change this behavior to one of three states depending on the Cancellation state value in the ``@IsCancelled``
annotation.

- ``CancellationState.FALSE`` is the default behavior and will not call your listener if the event has been cancelled.
- ``CancellationState.IGNORE`` will cause your listener to be called regardless of the cancellation state of the event.
- ``CancellationState.TRUE`` will cause your listener to be called only if the event has been cancelled by a previous event listener.

Parameter Filters
=================

Parameter filters allow you to filter based on objects within the event. These annotations come in two types, sources and
filters. Each additional parameter for your listener method, beyond the normal event parameter, requires exactly one source
annotation and may optionally have any number of filter annotations.

Parameter Source Annotations
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Parameter source annotations tell the event system where it should look for this parameter's value. This may be in the events
cause or in a member of the event object itself.

**@First** This parameter source annotation tells the event system to find the first object in the event's cause which matches
the type of your parameter (This is equivalent to ``Cause#first(Class<?>)``). If no object is found matching this parameter
then your listener is not called.

**In this example your listener will only be called if there is a player in the event's cause, and the ``player`` parameter
will be set to the first player present the cause.**

.. code-block:: java

    @Listener
    public void onInteract(InteractBlockEvent.Secondary event, @First Player player) {
        // do something
    }

**@Last** This is similar to ``@First`` however it instead makes a call to ``Cause#last(Class<?>)``.

.. code-block:: java

    @Listener
    public void onInteract(InteractBlockEvent.Secondary event, @Last Player player) {
        // do something
    }

**@All** This parameter source annotation is a little special in that it requires that the annotated parameter be an array
type. The returned array will be equivalent to the result of ``Cause#all(Class<?>)``. By default if the returned array would
be empty then the validation fails however this can be disabled by setting ``ignoreEmpty=false``.

**In this example your listener will always be called, although the players array may be empty if the event's cause contained
no players.**

.. code-block:: java

    @Listener
    public void onInteract(InteractBlockEvent.Secondary event, @All(ignoreEmpty=false) Player[] players) {
        // do something
    }

Parameter Filter Annotations
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Parameter filter annotations add additional validation to objects returned from parameter source annotations. As with all
event filters if any of these validations fail then your listener will not be called.

**@Supports**
This parameter filter may be applied to any type which is a ``DataHolder``. It takes a class extending ``DataManipulator`` as
its parameter and validates that the annotated DataHolder supports the given DataManipulator type. This validation is
equivalent to ``DataHolder#supports(Class<? extends DataManipulator>)``.

**In this example the listener will be called only if there is an entity in the event's cause, and if that entity supports
the data manipulator ``FlyingData``.**

.. code-block:: java

    @Listener
    public void onInteract(InteractBlockEvent.Secondary event, @First @Supports(FlyingData.class) Entity entity) {
        // do something
    }

**@Has**
This parameter filter is similar to the ``@Supports`` parameter filter except that it additionally validates that the
``DataHolder`` contains an instance of the given ``DataManipulator``. This validation is equivalent to
``DataHolder#hasData(Class<? extends DataManipulator>)``.

**In this example the listener will be called only if there is an entity in the event's cause, and if that entity has an
instance of ``FlyingData`` available.**

.. code-block:: java

    @Listener
    public void onInteract(InteractBlockEvent.Secondary event, @First @Has(FlyingData.class) Entity entity) {
        // do something
    }
