================
Serializing Data
================

.. javadoc-import::
    ninja.leaping.configurate.ConfigurationNode
    org.spongepowered.api.Game
    org.spongepowered.api.Sponge
    org.spongepowered.api.data.DataContainer
    org.spongepowered.api.data.DataManager
    org.spongepowered.api.data.DataQuery
    org.spongepowered.api.data.DataSerializable
    org.spongepowered.api.data.DataView
    org.spongepowered.api.data.MemoryDataContainer
    org.spongepowered.api.data.MemoryDataView
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.data.manipulator.ImmutableDataManipulator
    org.spongepowered.api.data.manipulator.mutable.entity.HealthData
    org.spongepowered.api.data.persistence.DataBuilder
    org.spongepowered.api.data.persistence.InvalidDataException
    org.spongepowered.api.data.translator.ConfigurateTranslator
    org.spongepowered.api.data.translator.DataTranslator

While an :javadoc:`ImmutableDataManipulator` is a good way to store data while the server is running, it will not
persist over a restart. However, every :javadoc:`DataManipulator` implements the :javadoc:`DataSerializable` interface
and thus can be serialized to a :javadoc:`DataContainer` and deserialized by a :javadoc:`DataBuilder`.

After this initial conversion from the specialized ``DataManipulator`` to a more general structure, the ``DataContainer``
can be further processed.

DataContainer and DataView
==========================

A :javadoc:`DataView` is a general-purpose structure for holding any kind of data. It supports multiple values and even
nested ``DataView``\ s as a value, thus allowing for a tree-like structure. Every value is identified by a
:javadoc:`DataQuery`. A ``DataContainer`` is a root ``DataView``.

Every ``DataSerializable`` provides a ``toContainer()`` method which will create and return a ``DataContainer``.
As an example, calling ``toContainer()`` on a :javadoc:`HealthData` instance will yield a ``DataContainer`` containing
two values, one for the current and one for the maximum health, each identified by the ``DataQuery`` of the respective
``Key``.

.. code-block:: java

    import org.spongepowered.api.data.DataContainer;
    import org.spongepowered.api.data.key.Keys;

    DataContainer serializedHealth = healthData.toContainer();
    double currentHealth = serializedHealth.getDouble(Keys.HEALTH.getQuery()).get();
    currentHealth == healthData.health().get();  // true

Converting this container back into a ``HealthData`` instance is done by the corresponding ``DataBuilder``. Those are
registered and managed by the :javadoc:`DataManager`. It can either be obtained from a valid :javadoc:`Game` instance
or using the :javadoc:`Sponge` utility class. The ``DataManager`` provides a method to get the appropriate
``DataBuilder`` to deserialize a given class and additionally a shorthand method to get the ``DataBuilder`` and have it
do the deserialization in one step. Both of the following code examples are functionally equivalent.

**Code Example: Deserialization, the long way**

.. code-block:: java

    import org.spongepowered.api.data.DataView;
    import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
    import org.spongepowered.api.util.persistence.DataBuilder;

    import java.util.Optional;

    public Optional<HealthData> deserializeHealth(DataView container) {
        final Optional<DataBuilder<HealthData>> builder = Sponge.getDataManager().getBuilder(HealthData.class);
        if (builder.isPresent()) {
            return builder.get().build(container);
        }
        return Optional.empty();
    }

**Code Example: Deserialization, the short way**

.. code-block:: java

    import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;

    public Optional<HealthData> deserializeHealth(DataView container) {
        return Sponge.getDataManager().deserialize(HealthData.class, container);
    }

The ``deserializeHealth`` function will return ``Optional.empty()`` if there is no ``DataBuilder`` registered for
``HealthData`` or the supplied ``DataContainer`` is empty. If invalid data is present in the ``DataContainer``, an
:javadoc:`InvalidDataException` will be thrown.

DataTranslator
==============

In Sponge, generally the implementations :javadoc:`MemoryDataView` and :javadoc:`MemoryDataContainer` are used, which
reside in memory only and thus will not persist over a server restart. In order to persistently store a
``DataContainer``, it first has to be converted into a storable representation. This can be done by using an
implementation of the :javadoc:`DataTranslator` interface, for example the :javadoc:`ConfigurateTranslator`, which can
convert a ``DataView`` to a :javadoc:`ConfigurationNode` and vice versa. ``ConfigurationNode``\ s can then be written
to and read from persistent files using the :doc:`Configurate Library <../configuration/index>`.

**Code Example: Serializing a HealthData instance to Configurate**

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import org.spongepowered.api.data.translator.ConfigurateTranslator;

    public void writeToConfig(HealthData data, ConfigurationNode config) {
        final ConfigurateTranslator translator = ConfigurateTranslator.instance();
        final DataView container = data.toContainer();
        translator.translateContainerToData(config, container);
    }

**Code Example: Deserializing a HealthData instance from Configurate**

.. code-block:: java

    public Optional<HealthData> readHealthFromConfig(ConfigurationNode config) {
        final ConfigurateTranslator translator = ConfigurateTranslator.instance();
        final DataView container = translator.translateFrom(config);
        return deserializeHealth(container);
    }
