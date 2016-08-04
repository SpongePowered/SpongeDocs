================
Text Serializers
================

.. javadoc-import::
    org.spongepowered.api.text.Text
    org.spongepowered.api.text.serializer.TextParseException
    org.spongepowered.api.text.serializer.TextSerializer
    org.spongepowered.api.text.serializer.TextSerializers
    java.lang.String

:javadoc:`TextSerializer`\ s provide a convenient way to serialize and de-serialize :javadoc:`Text` instances. There
are three applicable formats:

.. toctree::
    :maxdepth: 2
    :titlesonly:

    formatting-code-legacy
    xml
    json
    configurate

The :javadoc:`TextSerializers` class provides three ``TextSerializer``\ s for serializing to a representative
``String`` or deserializing to a ``Text`` instance:

* ``LEGACY_FORMATTING_CODE``
* ``FORMATTING_CODE``
* ``JSON``
* ``TEXT_XML``

Serializing Text
~~~~~~~~~~~~~~~~

To serialize a ``Text`` object, simply use the :javadoc:`TextSerializer#serialize(Text)` method, specifying the
appropriate ``Text`` object as the only argument. The method will return a ``String`` representing the ``Text`` object.

Deserializing to Text
~~~~~~~~~~~~~~~~~~~~~

To deserialize a ``String`` into its corresponding ``Text`` object, simply use the
:javadoc:`TextSerializer#deserialize(String)` method, specififying the input ``String`` as the only argument. If the
input is incorrectly formatted, a :javadoc:`TextParseException` will be thrown. Alternatively, use the
:javadoc:`TextSerializer#deserializeUnchecked(String)` method to deserialize without any exceptions. If there is an
error, the raw input will be returned in the form of a ``Text`` object.
