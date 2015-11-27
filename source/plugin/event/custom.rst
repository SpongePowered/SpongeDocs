======================
Creating Custom Events
======================

You can write your own event classes and dispatch those events using the method described above.

An event class must either implement the ``Event`` interface or extend the ``AbstractEvent`` class.

If you want your event to be cancellable, the class must also implement ``Cancellable``.

Example: Custom Event Class
~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.entity.player.Player;
    import org.spongepowered.api.event.impl.AbstractEvent;
    import org.spongepowered.api.event.Cancellable;

    public class PrivateMessageEvent extends AbstractEvent implements Cancellable {

        private boolean cancelled = false;

        private Player sender;
        private Player recipient;

        private String message;

        public Player getSender() {
            return sender;
        }

        public Player getRecipient() {
           return recipient;
        }

        public String getMessage() {
           return message;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            cancelled = cancel;
        }

        public PrivateMessageEvent(Player sender, Player recipient, String message) {
            this.sender = sender;
            this.recipient = recipient;
            this.message = message;
        }
    }


Example: Fire Custom Event
~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    game.getEventManager().post(new PrivateMessageEvent(playerA, playerB, "Hello World!"));


Example: Listen for Custom Event
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.text.chat.ChatTypes;

    @Listener
    public void onPrivateMessage(PrivateMessageEvent event) {
        if(event.getMessage().equals("hi i am from planetminecraft")) {
            event.setCancelled(true);
            return;
        }

        String senderName = event.getSender().getName();
        event.getRecipient().sendMessage(ChatTypes.CHAT, Texts.of("PM from " + senderName + ": " + event.getMessage()));
    }
