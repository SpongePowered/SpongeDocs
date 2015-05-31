=============
Message Sinks
=============

Message Sinks enable you to easily control the recipients of a message. The ``org.spongepowered.api.text.sink.MessageSinks`` utility class provides methods for targeting
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

    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink permissionSink = MessageSinks.toPermission("com.example.myplugin");

Broadcasting
~~~~~~~~~~~~

In a broadcast, every player on the server will be sent the same message. This can be achievd with a ``MessageSink`` by targeting every player on the server:

Example: Broadcasting
~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

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

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink sink1 = MessageSinks.toPermission("com.example.myplugin.permission1");
    MessageSink sink2 = MessageSinks.toPermission("com.example.myplugin.permission2");

    MessageSink combinedSink = MessageSinks.combined(sink1, sink2);

Targeting CommandSources
~~~~~~~~~~~~~~~~~~~~~~~~

Lastly, a ``MessageSink`` can be used to target any number of CommandSources. Remember, ``Player`` indirectly extends ``CommandSource``, so you can target a
``Set<Player>`` as well.

Example: Targeting CommandSources
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.text.sink.MessageSinks;

    MessageSink sink1 = MessageSinks.toPermission("com.example.myplugin.permission1");
    MessageSink sink2 = MessageSinks.toPermission("com.example.myplugin.permission2");

    MessageSink combinedSink = MessageSinks.combined(sink1, sink2);

Chat Channels
~~~~~~~~~~~~~
