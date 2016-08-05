================
Message Channels
================

.. javadoc-import::
    org.spongepowered.api.Server
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.event.message.MessageChannelEvent
    org.spongepowered.api.event.message.MessageChannelEvent.Chat
    org.spongepowered.api.service.permission.PermissionService
    org.spongepowered.api.text.Text
    org.spongepowered.api.text.channel.AbstractMutableMessageChannel
    org.spongepowered.api.text.channel.MessageChannel
    org.spongepowered.api.text.channel.MessageReceiver
    org.spongepowered.api.text.channel.MutableMessageChannel
    org.spongepowered.api.text.chat.ChatType
    java.lang.Object
    java.lang.String

In Sponge, every object that messages may be sent to implements the :javadoc:`MessageReceiver` interface. A
:javadoc:`MessageChannel` is a device that may send messages to an arbitrary number of ``MessageReceiver``\ s and even
perform actions like recipient-specific formatting of the message.

Selecting Message Recipients
============================

Within the ``MessageChannel`` interface there are constants and static methods that can be used to obtain or create
commonly used channels. There are also other classes and interfaces that can be used to create a ``MessageChannel``,
such as :javadoc:`AbstractMutableMessageChannel` and :javadoc:`MutableMessageChannel`. A complete list of these can be
found at :javadoc:`org.spongepowered.api.text.channel` and its sub-packages.

Broadcasting
~~~~~~~~~~~~

The most common channel will be the broadcasting channel. It can be obtained either from the
:javadoc:`Server` via the :javadoc:`Server#getBroadcastChannel()` method or from either the
:javadoc:`MessageChannel#TO_PLAYERS` or :javadoc:`MessageChannel#TO_ALL` constant. The only difference is that
``TO_ALL`` includes not only every player connected to the server but also the server ``Console``.

The channel returned by ``getBroadcastChannel()`` will generally be the ``MessageChannel.TO_ALL`` channel, however a
different channel may be set using the :javadoc:`Server#setBroadcastChannel(MessageChannel)` method.


Sending to a Fixed List of MessageReceivers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It is also possible to send a message not to all players connected, but to a number of hand-selected receivers. This
can be done by passing the list of the intended recipients to the :javadoc:`MessageChannel#fixed(MessageReceiver...)`
method. Unlike most other channels, the list of recipients will not be generated dynamically every time something is
sent through the channel but remains static for the rest of its existence. However, the references kept are *weak*.
This means that if for example a player disconnects and the corresponding :javadoc:`Player` object is removed by Java's
*garbage collector*, that player will also vanish from the channels recipient list.

Permission-based Selection
~~~~~~~~~~~~~~~~~~~~~~~~~~

It is also possible to create a channel sending to all recipients that hold a specific permission. The channel is
obtained from the :javadoc:`MessageChannel#permission(String)` method with the permission to check for as an argument.
When a message is then sent through this channel, it will obtain all subjects from the :javadoc:`PermissionService`
that have the given permission.

Combining Channels
~~~~~~~~~~~~~~~~~~

It is also possible to combine multiple channels into one. This can be done by passing all channels into the
:javadoc:`MessageChannel#combined(MessageChannel...)` method. The resulting channel will relay messages to every
recipient that is targeted by at least one of the combined channels.

Sending Messages
================

Once you have obtained a ``MessageChannel`` you can send a message through it via the
:javadoc:`MessageChannel#send(Object, Text)` method. This method is preferred over the
:javadoc:`MessageChannel#send(Text)` method, as the ``Object`` can be used for identification or for performing other
various actions. Alternatively, you can use a :javadoc:`ChatType` to specify where the message will be sent to. Using
the :javadoc:`MessageChannel#send(Object, Text, ChatType)` method will allow you to accomplish this. The channel will
then transform the message for every recipient and send the transformed message.

Extended Application: Chat Channels
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Message channels have a very useful application as they can be used to establish chat channels. For example, you could
establish a message channel for every chat channel you wish to have. Then, when a ``MessageReceiver`` joins a channel,
such as with ``/join <channel name>``, simply set the ``MessageReceiver``'s ``MessageChannel`` to the appropriate
channel using :javadoc:`MessageReceiver#setMessageChannel(MessageChannel)`. This will cause every message sent *from*
the ``MessageReceiver`` to be passed to the given ``MessageChannel`` instead of the default one. Alternatively,
you could listen to :javadoc:`MessageChannelEvent`, and set the appropriate ``MessageChannel`` using
:javadoc:`MessageChannelEvent#setChannel(MessageChannel)`. Passing ``null`` to that method will unset any custom
channel, causing the message to be sent to the original MessageChannel instead.

Transforming Messages
=====================

You can apply a filter to all ``Text``\ s that pass through a ``MessageChannel`` to change the message however you
like. This is possible by extending ``MessageChannel`` and overriding the
:javadoc:`MessageChannel#transformMessage(Object, MessageReceiver, Text, ChatType)` method as demonstrated below.

**Example: Transforming Messages**

The following code excerpt defines an ``AdminMessageChannel`` class which overrides the default ``transformMessage``
method. The new ``transformMessage`` method will take the original message and append a red ``[Admin]`` tag to the
front of the message.

.. TODO: UPDATE FOR CHATTYPE

.. code-block:: java

    import java.util.Collection;
    import java.util.Collections;
    import java.util.Optional;

    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.channel.MessageChannel;
    import org.spongepowered.api.text.channel.MessageReceiver;
    import org.spongepowered.api.text.format.TextColors;

    public class AdminMessageChannel implements MessageChannel {

        @Override
        public Optional<Text> transformMessage(Object sender, MessageReceiver recipient,
                                                                    Text original) {
            Text text = original;
            text = Text.of(TextColors.RED, "[Admin]", TextColors.RESET, text);
            return Optional.of(text);
        }

        @Override
        public Collection<MessageReceiver> getMembers() {
            return Collections.emptyList();
        }
    }


Note that we do not wish to define any additional recipients, so we return an empty collection in the
:javadoc:`MessageChannel#getMembers()` method.

Thanks to the capabilities of combined ``MessageChannel``\ s, we can combine our newly created ``AdminMessageChannel``
with any other ``MessageChannel``. Now if a player joins with the ``myplugin.admin`` permission, we will obtain the
``MessageChannel`` his messages are sent to, combine it with an ``AdminMessageChannel`` and set the combined channel
back onto the player. That way all his messages are sent to everyone specified in the original channel, but due to the
addition of the ``AdminMessageChannel``, a red ``[Admin]`` tag will be prefixed.

.. code-block:: java

    import org.spongepowered.api.entity.living.player.Player;
    import org.spongepowered.api.event.Listener;
    import org.spongepowered.api.event.network.ClientConnectionEvent;

    private AdminMessageChannel adminChannel = new AdminMessageChannel();

    @Listener
    public void onClientConnectionJoin(ClientConnectionEvent.Join event) {
        Player player = event.getTargetEntity();
        if(player.hasPermission("myplugin.admin")) {
            MessageChannel originalChannel = event.getOriginalChannel();
            MessageChannel newChannel = MessageChannel.combined(originalChannel,
                adminChannel);
            player.setMessageChannel(newChannel);
        }
    }

Note that this will prefix `all` messages pertaining to a player. This includes death messages, leave messages, etc. If
you only want to prefix all `chat` messages, you would need to listen to :javadoc:`MessageChannelEvent.Chat` and set
the channel onto the event rather than the player. This would be done using ``event.setChannel(newChannel)`` onto the
``MessageChannelEvent.Chat`` event. To get the player from the event to check for permissions, you would need to get a
``Player`` from the ``Cause`` of the event. This is demonstrated below:

.. code-block:: java

    Optional<Player> playerOptional = event.getCause().<Player>first(Player.class);

More on causes can be found on the :doc:`causes page <../event/causes>`.

.. note::

    When combining multiple ``MessageChannel``\ s defining different message transformations, the ``Text`` will be
    transformed in the order that the ``MessageChannel``\ s are passed in to the
    ``MessageChannel#combined(MessageChannel... channels)`` method. Note that any transformations resulting in an
    empty ``Optional`` will be ignored unless performed by the last channel in the chain.

Mutable Message Channels
========================

A :javadoc:`MutableMessageChannel` contains methods for changing who may receive the messages sent through our channel.
As such, we must implement methods for performing actions that modify our members. To do this, we simply will create a
class named ``MutableAdminMessageChannel`` that will implement a ``MutableMessageChannel``.

.. code-block:: java

    import java.util.Set;
    import java.util.WeakHashMap;

    import org.spongepowered.api.text.channel.MutableMessageChannel;

    public class MutableAdminMessageChannel implements MutableMessageChannel {

        private Set<MessageReceiver> members;

        public MutableAdminMessageChannel() {
            this(Collections.emptySet());
        }

        public MutableAdminMessageChannel(Collection<MessageReceiver> members) {
            this.members = Collections.newSetFromMap(new WeakHashMap<>());
            this.members.addAll(members);
        }

        @Override
        public Collection<MessageReceiver> getMembers() {
            return Collections.unmodifiableSet(this.members);
        }

        @Override
        public boolean addMember(MessageReceiver member) {
            return this.members.add(member);
        }

        @Override
        public boolean removeMember(MessageReceiver member) {
            return this.members.remove(member);
        }

        @Override
        public void clearMembers() {
            this.members.clear();
        }

        @Override
        public Optional<Text> transformMessage(Object sender, MessageReceiver recipient,
                                                                    Text original) {
            Text text = original;
            if(this.members.contains(recipient)) {
                text = Text.of(TextColors.RED, "[Admin]", TextColors.RESET, text);
            }
            return Optional.of(text);
        }
    }

The main difference between our ``AdminMessageChannel`` and our new ``MutableAdminMessageChannel`` is that we check if
the recipient is in the member list before transforming the message. If it is, then we may alter the message that is
sent, appending the red ``[Admin]`` prefix. In our ``getMembers()`` method we return an immutable set, so that the set
can only be modified by the appropriate methods in our ``MutableAdminMessageChannel``.

Note that an abstract implementation for ``MutableMessageChannel``\ s exists in the Sponge API as
``AbstractMutableMessageChannel``. Also note that our members do not persist. If a player were to leave the server,
they would be removed from the set.

Modifying the Members
~~~~~~~~~~~~~~~~~~~~~

To make full use of our ``MutableAdminMessageChannel``, we need to be able to add and remove members from the channel.
To do this, we can simply call our :javadoc:`MutableMessageChannel#addMember(MessageReceiver)`
and :javadoc:`MutableMessageChannel#removeMember(MessageReceiver)` methods we implemented previously whenever we need
to add or remove a member from the member set.
