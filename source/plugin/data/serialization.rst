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
    org.spongepowered.api.data.persistence.DataTranslator
    org.spongepowered.api.data.persistence.DataTranslators
    org.spongepowered.api.data.persistence.InvalidDataException
    org.spongepowered.api.data.persistence.DataFormat
    org.spongepowered.api.data.persistence.DataFormats

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
``DataContainer``, it first has to be converted into a storable representation.

Using the :javadoc:`DataTranslators#CONFIGURATION_NODE` implementation of :javadoc:`DataTranslator`, we can convert a
``DataView`` to a :javadoc:`ConfigurationNode` and vice versa. ``ConfigurationNode``\ s can then be written to and read
from persistent files using the :doc:`Configurate Library <../configuration/index>`.

**Code Example: Serializing a HealthData instance to Configurate**

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import org.spongepowered.api.data.persistence.DataTranslator;
    import org.spongepowered.api.data.persistence.DataTranslators;

    public ConfigurationNode translateToConfig(HealthData data) {
        final DataTranslator<ConfigurationNode> translator = DataTranslators.CONFIGURATION_NODE;
        final DataView container = data.toContainer();
        return translator.translate(container);
    }

**Code Example: Deserializing a HealthData instance from Configurate**

.. code-block:: java

    import java.util.Optional;

    public Optional<HealthData> translateFromConfig(ConfigurationNode node) {
        final DataTranslator<ConfigurationNode> translator = DataTranslators.CONFIGURATION_NODE;
        final DataView container = translator.translate(node);
        return deserializeHealth(container);
    }


DataFormat
==========

An alternative to using a ``DataTranslator`` is to use :javadoc:`DataFormat`, which allows you to store a 
``DataContainer`` in HOCON, JSON or NBT format. You can also recreate DataContainers using ``DataFormats``. Sponge 
provided ``DataFormat`` implementations are available in the :javadoc:`DataFormats` class.

For example, we can use the :javadoc:`DataFormats#JSON` ``DataFormat`` which allows us to create a JSON representation
of a ``DataContainer``. The output JSON could then easily be stored in a database. We can then use the same 
``DataFormat`` to recreate the original ``DataContainer`` from this JSON when required.

**Imports for code examples**

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.data.DataContainer;
    import org.spongepowered.api.data.persistence.DataFormats;
    import org.spongepowered.api.item.inventory.ItemStackSnapshot;

    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.util.Optional;

**Code Example: Serializing an ItemStackSnapshot to JSON format**

.. code-block:: java

    String json = DataFormats.JSON.write(itemStack.toContainer());

**Code Example: Deserializing an ItemStackSnapshot from JSON format**

.. code-block:: java

    DataContainer container = DataFormats.JSON.read(json);

**Code Example: Writing an ItemStackSnapshot to a file using NBT**

.. code-block:: java

    public void writeItemStackSnapshotToFile(ItemStackSnapshot itemStackSnapshot, File file) {
        DataContainer dataContainer = itemStackSnapshot.toContainer();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            DataFormats.NBT.writeTo(outputStream, dataContainer);
        } catch (IOException e) {
            // For the purposes of this example, we just print the error to the console. However,
            // as this exception indicates the file didn't save, you should handle this in a way 
            // more suitable for your plugin.
            e.printStackTrace();
        }
    }

**Code Example: Reading an ItemStackSnapshot from a file using NBT**

.. code-block:: java

    public Optional<ItemStackSnapshot> readItemStackSnapshotFromFile(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            DataContainer dataContainer = DataFormats.NBT.readFrom(inputStream);
            return Sponge.getDataManager().deserialize(ItemStackSnapshot.class, dataContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
