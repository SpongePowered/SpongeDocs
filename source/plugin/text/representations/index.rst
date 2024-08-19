================
Text Serializers
================

.. javadoc-import::
    net.kyori.adventure.text.Component
    net.kyori.adventure.text.serializer.ComponentSerializer
    net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
    net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
    net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
    net.kyori.adventure.serializer.configurate4.ConfigurateComponentSerializer
    java.lang.String

..
	Note to editors: There used to be many other files in this folder, which were made irrelevant.
	This file was not moved to keep the URLs to this page alive.

:javadoc:`ComponentSerializer`\ s provide a convenient way to serialize and de-serialize :javadoc:`Component` instances.
There are four applicable formats:

* Unformatted string - :javadoc:`PlainTextComponentSerializer`
* Legacy ``&`` or ``§`` formatting - :javadoc:`LegacyComponentSerializer`
* Configurate nodes - :javadoc:`ConfigurateComponentSerializer`
* Minecraft JSON - :javadoc:`GsonComponentSerializer`

For a full documentation of each serializer, see the Adventure docs' :doc:`Text Serializers <adventure:serializer/index>` section.

Serializing Text
~~~~~~~~~~~~~~~~

To serialize a ``Component`` object, simply use the :javadoc:`ComponentSerializer#serialize(Component)`
method, providing the text object as the only argument.
The method will return a serializer-specific object representing the ``Component``.
For example, `ConfigurateComponentSerializer` will return a ``ConfigurationNode`` object.

Deserializing to Text
~~~~~~~~~~~~~~~~~~~~~

To deserialize a ``String`` or other serialized object into its corresponding ``Component``, simply use the
:javadoc:`ComponentSerializer#deserialize(R)` method, specifying the serialized object from above as the only argument. If the
input is incorrectly formatted, an exception will be thrown. Alternatively, use the
:javadoc:`ComponentSerializer#deserializeOr(R, Component)` method to deserialize without any exceptions. If there is an
error, the second argument will be returned as a fallback.
