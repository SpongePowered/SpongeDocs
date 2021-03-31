================
Text Serializers
================

.. javadoc-import::
    java.lang.String

``ComponentSerializer``\ s provide a convenient way to serialize and de-serialize ``Component`` instances.
There are four applicable formats:

* Unformatted string - ``PlainComponentSerializer``
* Legacy ``&`` or ``§`` formatting - ``LegacyComponentSerializer``
* Configurate nodes - ``ConfigurateComponentSerializer``
* Minecraft JSON -  ``GsonComponentSerializer``

For a full documentation of each serializer, see the Adventure docs' `Text Serializers <https://docs.adventure.kyori.net/serializer/index.html>`__ section.

Serializing Text
~~~~~~~~~~~~~~~~

To serialize a ``Component`` object, simply use the ``ComponentSerializer#serialize(Component)``
method, providing the text object as the only argument.
The method will return a serializer-specific object representing the ``Component``.
For example, `ConfigurateComponentSerializer` will return a ``ConfigurationNode`` object.

Deserializing to Text
~~~~~~~~~~~~~~~~~~~~~

To deserialize a ``String`` or other serialized object into its corresponding ``Component``, simply use the
``ComponentSerializer#deserialize(R)`` method, specifying the serialized object from above as the only argument. If the
input is incorrectly formatted, an exception will be thrown. Alternatively, use the
``ComponentSerializer#deserializeOr(R, Component)`` method to deserialize without any exceptions. If there is an
error, the second argument will be returned as a fallback.
