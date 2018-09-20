=======================
Serializing Custom Data
=======================

.. javadoc-import::
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.DataQuery
    org.spongepowered.api.data.DataSerializable
    org.spongepowered.api.data.manipulator.DataManipulatorBuilder
    org.spongepowered.api.data.persistence.AbstractDataBuilder
    org.spongepowered.api.data.persistence.DataBuilder
    org.spongepowered.api.data.persistence.DataContentUpdater
    org.spongepowered.api.data.persistence.DataTranslator
    org.spongepowered.api.data.persistence.DataTranslators

Without a method for serializing and deserializing, your data will not persist across restarts. Sponge has a few different 
ways to serialize/deserialize data based on the type of data:

- :javadoc:`DataSerializable`\ s implement an interface to perform serialization, and use :javadoc:`DataBuilder` for 
  deserialization and creation
- :doc:`DataManipulators <../datamanipulators>` also implement ``DataSerializable``, but instead use a 
  :javadoc:`DataManipulatorBuilder` for deserialization and creation
- Objects that do not or cannot implement ``DataSerializable`` use :javadoc:`DataTranslator` for both serialization 
  and deserialization

This means that practically any object in Java can be saved to disk if it has been registered!

Reading DataViews
=================

Whenever you're reading a serialized object, it's tempting to read all the individual values yourself in order to 
manually create all the required objects (and their parameters) for your data. However, depending on the data saved in 
the container there are a few ways that are far more convenient:

- Common java types such as ``int``, ``String``, ``double``, ``List`` and ``Map`` can be retrieved using built-in 
  methods ``getInt(DataQuery)``, ``getString(DataQuery)``, etc. Lists of these types can also be retrieved in a 
  similar fashion, for example ``getStringList(DataQuery)``.
- ``DataSerializable`` objects can be retrieved using ``getSerializable(DataQuery, Class)`` or 
  ``getSerializableList(DataQuery, Class)``. Along with the path, you must also specify the ``Class`` of the 
  serializable type, such as ``Home.class``.
- Objects with a registered ``DataTranslator`` can be retrieved using ``getObject(DataQuery, Class)`` or 
  ``getObjectList(DataQuery, Class)``. A full list of classes that are supported by default can be found in 
  :javadoc:`DataTranslators`.

In all cases you need to specify a path using a :javadoc:`DataQuery`. If your data has a corresponding ``Key`` this is 
as easy as calling ``key.getQuery()``.  Otherwise, the easiest way to do this is with ``DataQuery.of("name")``.

.. tip::

    DataQueries can be used to reference data multiple nodes down a tree by using, for example, 
    ``DataQuery.of("my", "custom", "data")``. 

.. _custom-data-builders:

DataBuilders
============
To make an object serializable, first ensure that it implements :javadoc:`DataSerializable`. You must implement just 
two methods:

- ``getContentVersion()`` - this defined the current version of your data. 
- ``toContainer()`` - this is what your builder will be given when attempting to deserialize and object. You can store 
  whatever you want in the returned ``DataContainer``, so long as it is also serializable using one of the methods 
  above. Just use the ``set(DataQuery, Object)`` method to save your data to the given path. 

.. tip::
    
    It is recommended that you save the version of your data to the container as well using ``Queries.CONTENT_VERSION``
    as the query. This will allow for versioning upgrades with :ref:`content-updaters`.

**Code Example: Implementing toContainer**

.. code-block:: java
    
    import org.spongepowered.api.data.DataContainer;
    import org.spongepowered.api.data.DataQuery;
    import org.spongepowered.api.data.Queries;
    import org.spongepowered.api.data.MemoryDataContainer;

    private String name = "Spongie";

    @Override
    public DataContainer toContainer() {
        return DataContainer.createNew()
                .set(DataQuery.of("Name"), this.name)
                .set(Queries.CONTENT_VERSION, getContentVersion());
    }

The next part is to implement a :javadoc:`DataBuilder`. It's recommended to extend :javadoc:`AbstractDataBuilder` as 
it will try to upgrade your data if the version is less than the current version. There's only one method you need to 
implement - ``build(DataView)``, or ``buildContent(DataView)`` if you're using ``AbstractDataBuilder``.

You'll want to check that all the queries you want to retrieve are present using ``DataView.contains(Key...)``. If not,
the data is likely incomplete and you should return ``Optional.empty()``.

If everything seems to be there, use the ``getX`` methods to construct the values and return a newly created object as 
an ``Optional``.

Finally, you need to register this builder so that it can be found by plugins. To do this, simply call 
``DataManager#registerDataBuilder(Class, DataBuilder)`` referencing the data class and an instance of the builder.

.. _content-updaters:

DataContentUpdaters
===================

What happens if you change the layout of data in a new version release? :javadoc:`DataContentUpdater`\ s solve that 
problem. If the serialized object is less than the current version, an ``AbstractDataBuilder`` will try and update the 
data before passing it to the builder.

Each updater has an input version and an output version. You should take in the old data and change whatever is needed 
to upgrade it to a newer layout. If it's impossible to convert due to missing data, it may be possible instead to 
provide a default value which is interpreted elsewhere - such as by the main builder or the object itself.

Finally, you must ensure that all ``DataContentUpdater``\ s are registerered with 
``DataManager#registerContentUpdater()`` referencing the main data class - this will allow them to be discovered by 
the builder.

**Code Example: Implenting a DataContentUpdater**

.. code-block:: java

    import org.spongepowered.api.data.persistence.DataContentUpdater
    import org.spongepowered.api.text.Text

    public class NameUpdater implements DataContentUpdater {

        @Override
        public int getInputVersion() {
            return 1;
        }

        @Override
        public int getOutputVersion() {
            return 2;
        }

        @Override
        public DataView update(DataView content) {
            String name = content.getString(DataQuery.of("Name")).get();
            
            // For example, version 2 uses a text for the name
            return content.set(DataQuery.of("Name"), Text.of(name));
        }
    }

DataManipulatorBuilders
=======================

A ``DataManipualatorBuilder`` is very similar to ``DataBuilder``, however it adds a few methods directly related to 
deserializing manipulators:

- ``create()`` should return a new manipulator with default values
- ``createFrom(DataHolder)`` is similar to the build method, but instead the values should be taken from the 
  :javadoc:`DataHolder`. If there is no data to be taken from the holder, just return the output of ``create()``. If 
  the data is incompatible with the ``DataHolder``, you should instead return ``Optional.empty()``.

Just like ``DataBuilder``, you should read and return your manipulator in the relevant ``build`` method.

``DataManipulatorBuilder``\ s can make use of :ref:`content-updaters` as well, as long as you implement 
``AbstractDataBuilder``.

Registering a ``DataManipulatorBuilder`` is also similar to ``DataBuilder`` but uses the ``register()`` method. You 
must reference both your mutable and immutable classes in the method, in addition to an instance of your builder. 

.. note::

    You **must** reference the implementation classes if you have split the API from the implementaton.

DataTranslators
===============

Often the objects you want to serialize are not objects that implement ``DataSerializable``, such as ``Vector3d`` or 
``Date``. To allow these objects you implelement a :javadoc:`DataTranslator` which handles *both* the serialization 
and deserialization of the object.

The implementation of ``translate`` is identical to ``toContainer()`` and ``build(DataView)`` for a 
``DataSerializable`` as shown above, except that an ``InvalidDataException`` is thrown if data is missing in place of 
returning an ``Optional``.

As with other data, ensure that you register the translator with 
``DataManager#registerTranslator(Class, DataTranslator)``.