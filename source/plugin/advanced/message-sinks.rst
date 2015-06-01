=============
Message Sinks
=============

A ``MessageSink`` enables you to easily control the recipients of a message. The ``org.spongepowered.api.text.sink.MessageSinks`` utility class provides methods for targeting
appropriate recipients, as described below.

Selecting MessageSink Recipients
================================

Permission-based Selection
~~~~~~~~~~~~~~~~~~~~~~~~~~

The ``MessageSinks`` class can be employed to target any player that holds the specified permission.

**Example: Permission-based Targeting**

For example, the code below will define a ``MessageSink`` targeting all players with the `com.example.myplugin` permission

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink permissionSink = MessageSinks.toPermission("com.example.myplugin");

Broadcasting
~~~~~~~~~~~~

In a broadcast, every ``CommandSource`` connected to the server will be sent the same message. This can be achieved with a ``MessageSink``, as shown below:

**Example: Broadcasting**

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink broadcastSink = MessageSinks.toAll();

Alternatively, you could retrieve the broadcast ``MessageSink`` provided by Sponge:

.. code-block:: java

    MessageSink broadcastSink = server.getBroadcastSink();

Combining Message Sinks
~~~~~~~~~~~~~~~~~~~~~~~

The ``MessageSinkFactory`` can also be used to combine the recipients of any arbitrary number of other ``MessageSink``\ s:

**Example: Combined Message Sinks**

In the following block of code, any ``CommandSource`` that holds either the `com.example.myplugin.permission1` or the `com.example.myplugin.permission2` permissions will be targeted
by the ``MessageSink``.

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink sink1 = MessageSinks.toPermission("com.example.myplugin.permission1");
    MessageSink sink2 = MessageSinks.toPermission("com.example.myplugin.permission2");

    MessageSink combinedSink = MessageSinks.combined(sink1, sink2);

Targeting CommandSources
~~~~~~~~~~~~~~~~~~~~~~~~

Lastly, a ``MessageSink`` can be used to target any number of CommandSources. This allows a finer control over who you would like to send the message to.

**Example: Targeting CommandSources**

In the following example, any ``Player`` whose display name contains the prefix "[Donor]" will be added as a recipient to a new ``MessageSink``, which could then be used to send a
message thanking the players for their donations.

.. code-block:: java

    import java.util.HashSet;
    import java.util.Set;
    import org.spongepowered.api.data.manipulator.DisplayNameData;
    import org.spongepowered.api.entity.player.Player;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;
    import org.spongepowered.api.util.command.CommandSource;

    Set<CommandSource> donors = new HashSet<CommandSource>();
    for(Player player: event.getGame().getServer().getOnlinePlayers()) {
        DisplayNameData displayNameData = player.getDisplayNameData();
        if(Texts.toPlain(displayNameData.getDisplayName()).contains("[Donor]")) {
            donors.add(player);
        }
    }

    MessageSink sink = MessageSinks.to(donors);

Sending Messages with MessageSinks
===================================

Once you have defined an appropriate ``MessageSink``, you can use ``MessageSink#sendMessage(Text text)`` to send the message.

**Example: Sending Messages with MessageSinks**

.. code-block:: java

    messageSink.sendMessage(Texts.of("Yay! Message sinks!"));

Extended Application: Chat Channels
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Message sinks have a very useful application that they can be used to establish chat channels. For example, you could establish a message sink for every chat channel you wish to have.
Then, when a ``CommandSource`` joins a channel, such as with ``/join <channel name>``, simply set the ``CommandSource``'s ``MessageSink`` to the appropriate channel using
``CommandSource#setMessageSink(MessageSink sink)``. Alternatively, you could subscribe to ``MessageEvent``, and set the appropriate ``MessageSink`` using ``MessageEvent#setSink(MessageSink sink)``.
