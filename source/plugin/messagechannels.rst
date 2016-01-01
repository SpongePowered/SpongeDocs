================
Message Channels
================

In Sponge, every object that messages may be sent to implements the ``MessageReceiver`` interface. A ``MessageChannel``
is a device that may send messages to an arbitrary number of ``MessageReceiver``\ s and even perform actions like
recipient-specific formatting of the message.

Selecting Message Recipients
============================

Within the ``MessageChannel`` interface there are constants and static methods that can be used to obtain or create
commonly used channels.

Broadcasting
~~~~~~~~~~~~

The most common channel will be the broadcasting channel. It can be obtained either from the ``Server`` via the
``getBroadcastChannel()`` method or from either the ``MessageChannel.TO_PLAYERS`` or ``MessageChannel.TO_ALL`` constant.
The only difference is that ``TO_ALL`` includes not only every player connected to the server but also the server
``Console``.

The channel returned by ``getBroadcastChannel()`` will generally be the ``MessageChannel.TO_ALL`` channel, however a
different channel may be set using the ``setBroadcastChannel()`` method.


Sending to a Fixed List of MessageReceivers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It is also possible to send a message not to all players connected, but to a number of hand-selected receivers. This
can be done by passing the list of the intended recipients to the ``MessageChannel.fixed()`` method. Unlike most other
channels, the list of recipients will not be generated dynamically every time something is sent through the channel but
remains static for the rest of its existance. However, the references kept are *weak*. This means that if for example
a player disconnects and the corresponding ``Player`` object is removed by Javas *garbage collector*, that player will
also vanish from the channels recipient list.

Permission-based Selection
~~~~~~~~~~~~~~~~~~~~~~~~~~

It is also possible to create a channel sending to all recipients that hold a specific permission. The channel is
obtained from the ``MessageChannel.permission()`` method with the permission to check for as an argument. When a
message is then sent through this channel, it will obtain all subjects from the ``PermissionManager`` that have the
given permission.

Combining Channels
~~~~~~~~~~~~~~~~~~

It is also possible to combine multiple channels into one. This can be done by passing all channels into the
``MessageChannel.combined()`` method. The resulting channel will relay messages to every recipient that is targeted by
at least one of the combined channels.

Sending Messages
================

Once you have obtained a ``MessageChannel`` you can send a message through it via the ``send(Text message)`` method.
It is also possible specify a sender (which can be any ``Object``). However, that sender is only used for message
transforming. The channel will then transform the message for every recipient and send the transformed message.

Extended Application: Chat Channels
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Message channels have a very useful application as they can be used to establish chat channels. For example, you could
establish a message channel for every chat channel you wish to have. Then, when a ``MessageReceiver`` joins a channel,
such as with ``/join <channel name>``, simply set the ``MessageReceiver``'s ``MessageChannel`` to the appropriate c
hannel using the ``MessageReceiver#setMessageChannel(MessageChannel channel)``. This will cause every message sent
*from* the ``MessageReceiver`` to be passed to the given ``MessageChannel`` instead of the default one. Alternatively,
you could listen to ``MessageChannelEvent``, and set the appropriate ``MessageChannel`` using
``MessageChannelEvent#setChannel(MessageChannel channel)``. Passing ``null`` to that method will unset any custom
channel, causing the message to be sent to the originalMessageChannel instead.

Transforming Messages
=====================

You can apply a filter to all ``Text``\ s that pass through a ``MessageSink`` to change the message however you like.
This is possible by extending ``MessageChannel`` and overriding the ``transformMessage()`` method as demonstrated
below.

**Example: Transforming Messages**

The following code excerpt defines a ``AdminMessageSink`` class which overrides the default ``transformMessage`` method.
The new transformation method will first check if a source exists and, if it exists and is a permission ``Subject``,
has the ``myplugin.admin`` permission. If that is the case, the text will be prefixed with a red ``[Admin]``.

.. code-block:: java

    // TODO Update code example


Note that we do not wish to define any additional recipients, so we return an empty collection in the ``getMembers``
method.

Thanks to the capabilities of combined ``MessageChannel``\ s, we can combine our newly created ``AdminMessageChannel``
with any other ``MessageChannel``. Now if a player joins that has the ```myplugin.admin`` permission, we will obtain
the ``MessageChannel`` his messages are sent to, combine it with an ``AdminMessageChannel`` and set the combined
channel back on the player. That way all his messages are sent to everyone specified in the original channel, but
due to the addition of the ``AdminMessageChannel`` will be prefixed.

.. code-block:: java

    import org.spongepowered.api.event.Listener;
    import org.spongepowered.api.event.network.ClientConnectionEvent;

    @Listener
    public void onClientConnectionJoin(ClientConnectionEvent.Join event) {
        Player player = event.getEntity();

        MessageChannel originalChannel = player.getChannel();
        MessageChannel newChannel = MessageChannel.combined(originalChannel, new AdminMessageChannel());

        player.setChannel(newChannel);
    }

.. note::

    When combining multiple ``MessageChannel``\ s defining different message transformations, the ``Text`` will be
    transformed in the order that the ``MessageChannel``\ s are passed in to the
    ``MessageChannel#combined(MessageChannel... channels)`` method. Note that any transformations resulting in an
    empty ``Optional`` will be ignored unless performed by the last channel in the chain.
