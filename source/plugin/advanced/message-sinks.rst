=============
Message Sinks
=============

A ``MessageSink`` enables you to easily control the recipients of a message. The ``org.spongepowered.api.text.sink.MessageSinks`` utility class provides methods for targeting
appropriate recipients, as described below.

Selecting Message Sink Recipients
=================================

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

The ``MessageSinks`` class can also be used to combine the recipients of any arbitrary number of other ``MessageSink``\ s:

**Example: Combined Message Sinks**

In the following block of code, any ``CommandSource`` that holds either the `com.example.myplugin.permission1` or the `com.example.myplugin.permission2` permissions will be targeted
by the ``MessageSink``.

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink sink1 = MessageSinks.toPermission("com.example.myplugin.permission1");
    MessageSink sink2 = MessageSinks.toPermission("com.example.myplugin.permission2");

    MessageSink combinedSink = MessageSinks.combined(sink1, sink2);

.. tip::

    ``MessageSinks#combined(MessageSink... sinks)`` can also be used to apply message transformations, as discussed below.

Targeting Command Sources
~~~~~~~~~~~~~~~~~~~~~~~~~

Lastly, a ``MessageSink`` can be used to target any number of CommandSources. This allows a finer control over who you would like to send the message to.

**Example: Targeting Command Sources**

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

Sending Messages with Message Sinks
===================================

Once you have defined an appropriate ``MessageSink``, you can use ``MessageSink#sendMessage(Text text)`` to send the message.

**Example: Sending Messages with Message Sinks**

.. code-block:: java

    messageSink.sendMessage(Texts.of("Yay! Message sinks!"));

Extended Application: Chat Channels
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Message sinks have a very useful application that they can be used to establish chat channels. For example, you could establish a message sink for every chat channel you wish to have.
Then, when a ``CommandSource`` joins a channel, such as with ``/join <channel name>``, simply set the ``CommandSource``'s ``MessageSink`` to the appropriate channel using
``CommandSource#setMessageSink(MessageSink sink)``. Alternatively, you could subscribe to ``PlayerChatEvent``, and set the appropriate ``MessageSink`` using
``MessageEvent#setSink(MessageSink sink)``.

Transforming Messages with Message Sinks
========================================

You can apply a filter to all ``Text``\ s that pass through a ``MessageSink`` to change the message however you like. This is possible by creating a ``Class`` that extends
``MessageSink`` and defining the appropriate behavior for the ``MessageSink#transformMessage(CommandSource target, Text text)`` method, as shown below.

**Example: Transforming Messages with Message Sinks**

The following code excerpt defines a ``DonorMessageSink`` class which overrides the default ``transformMessage`` method. The new transformation method will first check if the
``CommandSource`` has the ``com.example.myplugin.donor`` permission, and if so, will append a `[Donor]` prefix, such as in a ranking system.

.. code-block:: java

    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.util.command.CommandSource;

    import com.google.common.collect.Lists;

    public class DonorMessageSink extends MessageSink {

        @Override
        public Text transformMessage(CommandSource target, Text input) {
            if(target.hasPermission("com.example.myplugin.donor")) {
                return Texts.of("[Donor]", input);
            }
            return input;
        }

        @Override
        public Iterable<CommandSource> getRecipients() {
            return Lists.newArrayList();
        }

    }

Note that we do not wish to define any additional reecipients, so we return an empty ``List`` in the ``getRecipients`` method.

Now that we have defined our custom ``DonorMessageSink`` that will append a prefix to a player's name, we need to apply a new ``MessageSink`` combining the existing one
and the new one to the ``CommandSource``. We can do this by using the ``MessageSinks#combined(MessageSink... sinks)`` method. In the following code excerpt, the new
``DonorMessageSink`` will be applied to any ``Player`` that joins the server.

.. code-block:: java

    import org.spongepowered.api.entity.player.Player;
    import org.spongepowered.api.event.Subscribe;
    import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
    import org.spongepowered.api.text.sink.MessageSink;
    import org.spongepowered.api.text.sink.MessageSinks;

    @Subscribe
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getEntity();

        MessageSink originalSink = player.getMessageSink();
        MessageSink newSink = MessageSinks.combined(originalSink, new DonorMessageSink());

        player.setMessageSink(newSink);
    }

.. note::

    When combining multiple ``MessageSink``\ s defining different message transformations, the ``Text`` will be transformed in the order that the ``MessageSink``\ s
    are passed in to the ``MessageSinks#combined(MessageSink... sinks)`` method.
