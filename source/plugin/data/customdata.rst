===========
Custom Data
===========

.. javadoc-import::
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.DataManager
    org.spongepowered.api.data.DataRegistration
    org.spongepowered.api.data.Key
    org.spongepowered.api.data.persistence.DataContentUpdater
    org.spongepowered.api.data.persistence.DataContainer
    org.spongepowered.api.data.persistence.DataBuilder
    org.spongepowered.api.data.persistence.DataQuery
    org.spongepowered.api.data.persistence.DataSerializable
    org.spongepowered.api.data.persistence.DataStore
    org.spongepowered.api.data.value.MapValue
    org.spongepowered.api.data.value.Value
    org.spongepowered.api.entity.Entity
    org.spongepowered.api.event.lifecycle.RegisterDataEvent

Object Custom Data
==================

The core part of custom data is the :javadoc:`DataSerializable`. To implement it, you must first decide if you want to 
create a separate API for your custom data. Generally speaking it's best to separate the API from the implementation 
(as SpongeAPI does), but if it won't be seen by other developers then you can just put both in the same class.

First, create a class and define the data you wish to store. In the following example we will use the idea of storing a
players last attacker, therefore we will have only the data of the last attackers UUID.

.. code-block:: java

    import java.util.UUID;

    public class LastAttackerDataSerilizable {
    
        private UUID lastAttackerId;
    
    }

.. note::

    Any data you wish to store must be able to be serialized into java primitives and/or strings


From here you will want to implement the javadoc:`DataSerializable` which will give you two methods to implement. The
first is ``contentVersion`` which is for the version of your data manipulator. The other method (``toContainer``) is 
used for serializing your data to the dataholder it belongs to. To do this you need to create a new :javadoc:`DataContainer`
then set your value(s) to the newly created ``DataContainer``


.. code-block:: java

    import org.spongepowered.api.data.persistence.DataSerializable;
    import org.spongepowered.api.data.persistence.DataContainer;
    import org.spongepowered.api.data.persistence.DataQuery;

    public class LastAttackerDataSerilizable implements DataSerializable {
    
        private UUID lastAttackerId;

        public static final DataQuery UUID_PATH = DataQuery.of("attack", "last");

        @Override
        public int contentVersion(){
            return 1;
        }

        @Override
        public DataContainer toContainer(){
            return DataContainer.createNew()
                .set(LastAttackerDataSerilizable.UUID_PATH, lastAttackerId.toString());
        }
    
    }    

After that you will want to create a class that can build the data from a ``DataContainer`` this is known as 
the :javadoc:`DataBuilder` which can be implemented as follows:

.. code-block:: java

    import org.spongepowered.api.data.persistence.InvalidDataException;

    public class LastAttackerDataBuilder implements DataBuilder<LastAttackerDataSerilizable> {
    
        @Override
        public Optional<LastAttackerDataSerilizable> build(DataView container) throws InvalidDataException {
            Optional<String> lastAttackerAsStringId container.getString(LastAttackerDataSerilizable.UUID_PATH);
            if(lastAttackerAsStringId.isPresent()){
                UUID lastAttacker = UUID.fromString(lastAttackerAsStringId.get());
                return Optional.of(new LastAttackerDataSerilizable(lastAttacker));
            }
            return Optional.empty();
        }
    
    }


Registration
============

Registering your ``DataSerializable`` allows it to be accessible by Sponge and by other plugins in a generic way. The
game/plugin can create copies of your data and serialize/deserialize your data without referencing any of your classes
directly.

To register a ``DataSerializable`` Sponge has the :javadoc:`RegisterDataEvent` event. This will allow you to register
your data with the appropriate ``DataHolder``

Simple Custom Data
==================

All of above is a lot of work if you just wanting to register a java primitive or ``String`` to
a ``DataHolder``. Thankfully there is a much shorter way to do all of that. 

.. code-block:: java

    Key<? extends Value<String>> key = Key.from(pluginContainer, "my_simple_data", String.class);
    DataRegistration myData = DataRegistration.of(key, ServerPlayer.class);
    event.register(myData);

Registration Key
================

When it comes to registering your data, you are required to register it with a :javadoc:`Key` which will allow you and
other developers access to your data manipulator.


.. code-block:: java

    import org.spongepowered.api.ResourceKey;
    import org.spongepowered.api.data.Key;
    import org.spongepowered.api.data.value.Value;

    ResourceKey resourceKey = ResourceKey(pluginContainer, "last_attacker_manipulator");
    Key<? extends Value<LastAttackerDataSerilizable>> key = Key
        .builder()
        .key(resourceKey)
        .elementType(LastAttackerDataSerilizable.class)
        .build();

.. warning::

    Be sure to store your ``Key`` somewhere global so you can access it later.

.. tip::

    You can register a key for a specific element within a DataSerializable

Data Store
==========

The :javadoc:`DataStore` is used to register your ``Key`` with the appropriate ``DataHolder`` and also register
any other keys you may have accessing your ``DataSerializable``. In the example below, it creates a ``DataStore``
and makes it appliciable to only the :javadoc:`Entity` ``DataHolder``.

.. code-block:: java

    import org.spongepowered.api.data.persistence.DataStore;

    DataStore datastore = DataStore
        .builder()
        .pluginData(resourceKey)
        .holder(Entity.class)
        .key(key)
        .build();

Simple Data Store
=================

The above code is a lot for such a simple DataStore, so thankfully Sponge allows a quick way to create a ``DataStore``
for a single key. The following example does the same as the above example.

.. code-block:: java

    DataStore datastore = DataStore.of(key, DataQuery.of(), Entity.class);

Multi-Key Data Store
====================

If you are registering multiple keys onto a single ``DataStore`` then the first approach should be used, however the
other keys should be specified with the original key, such as the following example.

.. code-block:: java

    import org.spongepowered.api.entity.Entity;

    DataStore datastore = DataStore
        .builder()
        .pluginData(resourceKey)
        .holder(Entity.class)
        .key(key)
        .key(innerKey, DataQuery.of("inner_data"))
        .build();

Data Provider
=============

For data that requires more code to be used whenever the getter, setter, deleter are used will require the use of
a ``DataProvider``. With a ``dataProvider`` a plugin is able to manipulate how its data should be received, set, and
deleted automatically. 

In the following example, we will be getting the UUID from the last attacker but if there is no last attacker, then
return the player's UUID instead.

.. code-block:: java

    import org.spongepowered.api.data.DataProvider;

    DataProvider<Value<UUID>, UUID> provider = DataProvider.mutableBuilder()
        .dataKey(innerKey)
        .dataHolder(ServerPlayer.class)
        .get(this::myCustomGetter)
        .build();

    public UUID myCustomGetter(ServerPlayer player){
        return player.get(key).orElse(player.uniqueId());
    }

.. note::

    Data Providers are completely optional, if your data does not require one then don't use one

.. tip::

    Data Providers are great if you wish to have your data be synced with a database


Data Registration
=================

The final object you will need to register your data is the :javadoc:`DataRegistration` which combines 
your ``Key``, ``DataStore`` and ``DataProvider`` together into a single package that you can register.

.. code-block:: java

    import org.spongepowered.api.data.DataRegistration;

    DataRegistration myData = DataRegistration.builder()
        .key(key)
        .store(datastore)
        .provider(provider)
        .build();

    event.register(myData);

Data Builder Register
=====================

The final part of your custom data registration is registering the data builder so your data can be
constructed upon reboot. This is registered though the :javadoc:`DataManager`, although it is recommended
that you register it within the ``RegisterDataEvent``.

.. code-block:: java

    Sponge.dataManager().registerBuilder(LastAttackerDataSerilizable.class, new LastAttackerDataBuilder());

Updating Data Containers
========================

You may wish to update the data found within a DataHolder to a new and improved ``DataSerializable``. 
This can be done with the use of the :javadoc:`DataContentUpdater` interface. In the example below
we will be adding a field of the nanosecond the attack occurred, with the update value being ``LocalDateTime.MIN``. 

.. code-block:: java

    import org.spongepowered.api.data.persistence.DataContentUpdater;

    public class LastAttackerUpdater implements DataContentUpdater {
    
        @Override
        public int inputVersion(){
            return 1;
        }

        @Override
        public int outputVersion(){
            return 2;
        }

        @Override
        public DataView update(DataView view){
            view.set(DataQuery.of("attack", "occurred"), LocalDateTime.MIN.getNano());
            return view;
        }
    
    }

This can then be registered with your ``DataStore``, whereby specifying a version number
on the ``pluginData`` function will allow you to register your ``DataContentUpdater``.

.. code-block:: java

    DataStore.builder()
        .pluginData(resourceKey, 1)
        .updater(new LastAttackerUpdater())
        //continue with the normal registeration

