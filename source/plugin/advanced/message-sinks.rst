=============
Message Sinks
=============

``MessageSinks enable you to easily control the recipients of a message. The ``org.spongepowered.api.text.sink.MessageSinks`` utility class provides methods for targeting
appropriate recipients, as described below.

Working with the MessageSinkFactory
===================================

Permissions
~~~~~~~~~~~

Message sinks can be employed to target any player that holds the specified permission.

Example: Permission-based Targeting
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

For example, the code below will define a ``MessageSink`` targeting all players with the `com.example.myplugin` permission

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink permissionSink = MessageSinks.toPermission("com.example.myplugin");

Broadcasting
~~~~~~~~~~~~

In a broadcast, every ``CommandSource`` connected to the server will be sent the same message. This can be achievd with a ``MessageSink``, as shown below:

Example: Broadcasting
~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink broadcastSink = MessageSinks.toAll();

Alternatively, you could retrieve the broadcast message sink provided by Sponge:

.. code-block:: java

    MessageSink broadcastSink = server.getBroadcastSink();

Combining Message Sinks
~~~~~~~~~~~~~~~~~~~~~~~

The ``MessageSinkFactory`` can also be used to combine the recipients any arbitrary number of other message sinks:

Example: Combined Message Sinks
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

In the following block of code, any ``CommandSource`` that holds either the `com.example.myplugin.permission1` or the `com.example.myplugin.permission2` permissions will be targeted
by the ``MessageSink``

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink sink1 = MessageSinks.toPermission("com.example.myplugin.permission1");
    MessageSink sink2 = MessageSinks.toPermission("com.example.myplugin.permission2");

    MessageSink combinedSink = MessageSinks.combined(sink1, sink2);

Targeting CommandSourcess
~~~~~~~~~~~~~~~~~~~~~~~~

Lastly, a ``MessageSink`` can be used to target any number of CommandSources. In the following example, any ``Player`` whose name contains the letter `a` will be added as a recipient to a new
``MessageSink``

.. code-block:: java

    import java.util.HashSet;
    import java.util.Set;
    import org.spongepowered.api.entity.player.Player;
    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;
    import org.spongepowered.api.util.command.CommandSource;

    Set<CommandSource> playersWithA = new HashSet<CommandSource>();
    for(Player p: event.getGame().getServer().getOnlinePlayers()) {
        if(p.getName().contains("a")) {
            playersWithA.add(p);
        }
    }

    MessageSink sink = MessageSinks.to(playersWithA);

Extended Application: Chat Channels
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Message sinks have a very useful application that they can be used to establish chat channels. For example, you could establish a message sink for every chat channel you wish to have.
Then, when a ``CommandSource`` joins a channel, such as with ``/join <channel name>``, simply set the ``CommandSource``'s ``MessageSink`` to the appropriate channel using ``CommandSource#setMessageSink(MessageSink sink)``.
Alternatively, you could subscribe to ``MessageEvent``, and set the appropriate ``MessageSink`` using ``MessageEvent#setSink(MessageSink sink)``.
