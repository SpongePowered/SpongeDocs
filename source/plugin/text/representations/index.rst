================
Text Serializers
================

:javadoc:`org.spongepowered.api.text.serializer.TextSerializer`\ s provide a convenient way to serialize and
de-serialize :javadoc:`org.spongepowered.api.text.Text` instances. There are three applicable formats:

.. toctree::
    :maxdepth: 2
    :titlesonly:

    formatting-code-legacy
    xml
    json
    configurate

The :javadoc:`org.spongepowered.api.text.serializer.TextSerializers` class provides three ``TextSerializer``\ s for
serializing to a representative ``String`` or deserializing to a ``Text`` instance:

* ``LEGACY_FORMATTING_CODE``
* ``FORMATTING_CODE``
* ``JSON``
* ``TEXT_XML``

Serializing Text
~~~~~~~~~~~~~~~~

To serialize a ``Text`` object, simply use the
:javadoc:`org.spongepowered.api.text.serializer.TextSerializer#serialize(org.spongepowered.api.text.Text)` method,
specifying the appropriate ``Text`` object as the only argument. The method will return a ``String`` representing the
``Text`` object.

Deserializing to Text
~~~~~~~~~~~~~~~~~~~~~

To deserialize a ``String`` into its corresponding ``Text`` object, simply use the
:javadoc:`org.spongepowered.api.text.serializer.TextSerializer#deserialize(java.lang.String)` method, specififying the
input ``String`` as the only argument. If the input is incorrectly formatted, a
:javadoc:`org.spongepowered.api.text.serializer.TextParseException` will be thrown. Alternatively, use the
:javadoc:`org.spongepowered.api.text.serializer.TextSerializer#deserializeUnchecked(java.lang.String)` method to
deserialize without any exceptions. If there is an error, the raw input will be returned in the form of a ``Text``
object.
