================
Serializing Data
================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

.. javadoc-import::
    org.spongepowered.configurate.ConfigurationNode
    org.spongepowered.api.Game
    org.spongepowered.api.Sponge
    org.spongepowered.api.data.DataContainer
    org.spongepowered.api.data.DataManager
    org.spongepowered.api.data.DataQuery
    org.spongepowered.api.data.DataSerializable
    org.spongepowered.api.data.DataView
    org.spongepowered.api.data.DataManipulator
    org.spongepowered.api.data.DataManipualator.Immutable
    org.spongepowered.api.data.persistence.DataBuilder
    org.spongepowered.api.data.persistence.DataTranslator
    org.spongepowered.api.data.persistence.InvalidDataException
    org.spongepowered.api.data.persistence.DataFormat
    org.spongepowered.api.data.persistence.DataFormats

While an :javadoc:`DataManipulator#Immutable` is a good way to store data while the server is running, it will not
persist over a restart. However, every :javadoc:`DataManipulator` implements the :javadoc:`DataSerializable` interface
and thus can be serialized to a :javadoc:`DataContainer` and deserialized by a :javadoc:`DataBuilder`.

After this initial conversion from the specialized ``DataManipulator`` to a more general structure, the ``DataContainer``
can be further processed.

DataContainer and DataView
==========================

A :javadoc:`DataView` is a general-purpose structure for holding any kind of data. It supports multiple values and even
nested ``DataView``\ s as a value, thus allowing for a tree-like structure. Every value is identified by a
:javadoc:`DataQuery`. A ``DataContainer`` is a root ``DataView``.

DataFormat
==========

:javadoc:`DataFormat` allows you to store a ``DataContainer`` in HOCON, JSON or NBT format. 
You can also recreate DataContainers using ``DataFormats``. Sponge provided ``DataFormat`` 
implementations are available in the :javadoc:`DataFormats` class.

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
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.util.Optional;

**Code Example: Serializing an ItemStackSnapshot to JSON format**

.. code-block:: java

    String json = DataFormats.JSON.write(itemStack.toContainer());

**Code Example: Deserializing an ItemStackSnapshot from JSON format**

.. code-block:: java

    DataContainer container = DataFormats.JSON.read(json);

**Code Example: Writing an ItemStackSnapshot to a file using NBT**

.. code-block:: java

    public void writeItemStackSnapshotToFile(ItemStackSnapshot itemStackSnapshot, Path path) {
        DataContainer dataContainer = itemStackSnapshot.toContainer();
        try (OutputStream outputStream = Files.newOutputStream(path)) {
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

    public Optional<ItemStackSnapshot> readItemStackSnapshotFromFile(Path path) {
        try (InputStream inputStream = Files.newInputStream(path)) {
            DataContainer dataContainer = DataFormats.NBT.readFrom(inputStream);
            return Sponge.getDataManager().deserialize(ItemStackSnapshot.class, dataContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
