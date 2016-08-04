=============
Custom Events
=============

.. javadoc-import::
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.event.Cancellable
    org.spongepowered.api.event.Event
    org.spongepowered.api.event.cause.Cause
    org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent

You can write your own event classes and dispatch those events using the method described above. An event class must
either implement the :javadoc:`Event` interface and, if it should be possible to cancel the event,
:javadoc:`Cancellable`.

.. tip::

    Depending on what type of event you want to create, there might be another interface to implement instead of
    ``Event``. For example if your event describes something that happens to a :javadoc:`Player`, you should have your
    custom event class implement :javadoc:`TargetPlayerEvent`.

Example: Custom Event Class
~~~~~~~~~~~~~~~~~~~~~~~~~~~

The following class describes an event indicating a ``Player`` has come in contact with FLARD and is now about to
mutate in a way specified by the event. Since the event targets a player and can be cancelled by listeners, it
implements both the ``TargetPlayerEvent`` and ``Cancellable`` interfaces.

Since generally custom events are intended to be listened to by other plugins, it is in your best interest to document
them appropriately. This includes a list of objects typically found in the :javadoc:`Cause`. In the below example, it
would probably be mentioned that the root cause is generally an object of the fictitious ``FLARDSource`` class.

.. code-block:: java

    import org.spongepowered.api.entity.living.player.Player;
    import org.spongepowered.api.event.Cancellable;
    import org.spongepowered.api.event.cause.Cause;
    import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;

    public class PlayerMutationEvent implements TargetPlayerEvent, Cancellable {

        public static enum Mutation {
            COMPULSIVE_POETRY,
            ROTTED_SOCKS,
            SPONTANEOUS_COMBUSTION;
        };

        private final Cause cause;
        private final Player victim;
        private final Mutation mutation;
        private boolean cancelled = false;

        public PlayerMutationEvent(Player victim, Mutation mutation, Cause cause) {
            this.victim = victim;
            this.mutation = mutation;
            this.cause = cause;
        }

        public Mutation getMutation() {
            return this.mutation;
        }

        @Override
        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            this.cancelled = cancel;
        }

        @Override
        public Cause getCause() {
            return this.cause;
        }

        @Override
        public Player getTargetEntity() {
            return this.victim;
        }

    }

Example: Fire Custom Event
~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.Sponge;

    PlayerMutationEvent event = new PlayerMutationEvent(victim, PlayerMutationEvent.Mutation.ROTTED_SOCKS,
            Cause.source(flardSource).build());
    Sponge.getEventManager().post(event);
    if (!event.isCancelled()) {
        // Mutation code
    }

Bear in mind that you need to supply a non-empty cause. If your event was ``Cancellable``, make sure that it was not
cancelled before performing the action described by the event.

Example: Listen for Custom Event
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.event.Listener;
    import org.spongepowered.api.text.Text;

    @Listener
    public void onPrivateMessage(PlayerMutationEvent event) {
        if(event.getMutation() == PlayerMutationEvent.Mutation.SPONTANEOUS_COMBUSTION) {
            event.setCancelled(true);
            event.getTargetEntity().sendMessage(Text.of("You can not combust here, this is a non-smoking area!"));
        }
    }
