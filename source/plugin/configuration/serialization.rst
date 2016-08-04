===================
Serializing Objects
===================

.. javadoc-import::
    ninja.leaping.configurate.ConfigurationOptions
    ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory
    ninja.leaping.configurate.objectmapping.ObjectMapper
    ninja.leaping.configurate.objectmapping.ObjectMapperFactory
    ninja.leaping.configurate.objectmapping.Setting
    ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable
    ninja.leaping.configurate.objectmapping.serialize.TypeSerializer
    ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection

The Configurate library also provides the means to tweak automatic serialization and deserialization of objects.
Per default, a set of data types can be (de)serialized: among others Strings, ints, doubles, UUIDs, Lists
(of serializable values) and Maps (where both keys and values are serializable). But if you want to write your
custom data structures to a config file, this will not be enough.

Imagine a data structure tracking how many diamonds a player has mined. It might look a little like this:

.. code-block:: java

    public class DiamondCounter {
        private UUID playerUUID;
        private int diamonds;

        ...
    }

Also assume some methods to access those fields, a nice constructor setting both of those etc.

Creating a custom TypeSerializer
================================

A very straightforward way of writing and loading such a data structure is providing a custom :javadoc:`TypeSerializer`.
The ``TypeSerializer`` interface provides two methods, one to write the data from an object to a configuration node and
one to create an object from a given configuration node.

.. code-block:: java

    import com.google.common.reflect.TypeToken;
    import ninja.leaping.configurate.objectmapping.ObjectMappingException;
    import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

    public class DiamondCounterSerializer implements TypeSerializer<DiamondCounter> {

        @Override
        public DiamondCounter deserialize(TypeToken<?> type, ConfigurationNode value)
          throws ObjectMappingException {
            UUID player = value.getNode("player").getValue(TypeToken.of(UUID.class));
            int diamonds = value.getNode("diamonds").getInt();
            return new DiamondCounter(player, diamonds);
        }

        @Override
        public void serialize(TypeToken<?> type, DiamondCounter obj, ConfigurationNode value)
          throws ObjectMappingException {
            value.getNode("player").setValue(obj.getPlayerUUID());
            value.getNode("diamonds").setValue(obj.getDiamonds());
        }
    }

This ``TypeSerializer`` must then be registered with Configurate. This can be done either globally, by registering to
the default :javadoc:`TypeSerializerCollection` or locally, by specifying it in the :javadoc:`ConfigurationOptions`
when loading your config.

**Code Example: Registering a TypeSerializer globally**

.. code-block:: java

    import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

    TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(DiamondCounter.class), new DiamondCounterSerializer());


**Code Example: Registering a TypeSerializer locally**

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.ConfigurationOptions;
    import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
    import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

    TypeSerializerCollection serializers = TypeSerializers.getDefaultSerializers().newChild();
    serializers.registerType(TypeToken.of(DiamondCounter.class), new DiamondCounterSerializer());
    ConfigurationOptions options = ConfigurationOptions.defaults().setSerializers(serializers);
    ConfigurationNode rootNode = someConfigurationLoader.load(options);

.. warning::

    If you provide a custom ``TypeSerializer`` for types that are not introduced by your own plugin, you should only
    ever register them locally in order to avoid conflicts with other plugins or Sponge, caused by a ``TypeSerializer``
    being overwritten.

Using ObjectMappers
===================

Since in many cases the (de)serialization boils down to mapping fields to configuration nodes, writing such a
``TypeSerializer`` is a rather dull affair and something we'd like Configurate to do on its own. So let's annotate our
class with the :javadoc:`ConfigSerializable` and :javadoc:`Setting` annotations.

.. code-block:: java

    import ninja.leaping.configurate.objectmapping.Setting;
    import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

    @ConfigSerializable
    public class DiamondCounter {

        @Setting(value="player", comment="Player UUID")
        private UUID playerUUID;
        @Setting(comment="Number of diamonds mined")
        private int diamonds;

        ...
    }

The above example can now be serialized and deserialized from config nodes without further registration. The
``@Setting`` annotations map a configuration node to the field that was annotated. It accepts two optional parameters,
``value`` and ``comment``. If the ``value`` parameter exists, it defines the name of the node the field will be
saved in. If it is not present, the name of the field will be used instead. So in our above example, the
annotation ensures that the contents of the field ``playerUUID`` are saved to the node "player", commented with
"Player UUID". The ``diamonds`` field however will be saved under that exact name since its annotation only
specifies a comment. That comment will be written to the config if the implementation supports commented
configuration nodes, otherwise it will be discarded.

.. tip::

    You may also use the shorthand ``@Setting("someNode")`` instead of ``@Setting(value="someNode")``


The ``@ConfigSerializable`` annotation eliminates the need for any registration since it allows Configurate to
just generate an :javadoc:`ObjectMapper` for the class. The only limitation is that Configurate needs an empty
constructor to instantiate a new object before filling in the annotated fields.

Providing a custom ObjectMapperFactory
======================================

That restriction, however, can be lifted if we use a different :javadoc:`ObjectMapperFactory`, for example a
:javadoc:`GuiceObjectMapperFactory`. Instead of requiring an empty constructor, it will work on any class that guice
can create via dependency injection. This also allows for a mixture of ``@Inject`` and ``@Setting`` annotated fields.

Your plugin can just acquire a ``GuiceObjectMapperFactory`` simply by dependency injection
(see :doc:`../injection`) and then pass it to the ``ConfigurationOptions``.

.. code-block:: java

    import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
    import org.spongepowered.api.plugin.Plugin;
    import com.google.common.eventbus.Subscribe;
    import com.google.inject.Inject;
    import ninja.leaping.configurate.commented.CommentedConfigurationNode;
    import ninja.leaping.configurate.loader.ConfigurationLoader;
    import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;

    @Plugin(name="IStoleThisFromZml", id="shamelesslystolen", version="0.8.15")
    public class StolenCodeExample {

        @Inject private GuiceObjectMapperFactory factory;
        @Inject private ConfigurationLoader<CommentedConfigurationNode> loader;

        @Subscribe
        public void enable(GamePreInitializationEvent event) {
            CommentedConfigurationNode node =
              loader.load(ConfigurationOptions.defaults().setObjectMapperFactory(factory));
            DiamondCounter myDiamonds = node.getValue(TypeToken.of(DiamondCounter.class));
        }
    }

.. note::

    The above code is an example and, for brevity, lacks proper exception handling.
