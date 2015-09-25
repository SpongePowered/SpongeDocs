===================
Serializing Objects
===================

The configurate library also provides some means to tweak automatic serialization and deserialization of objects.
Per default, a set of data types can be (de)serialized: among others Strings, ints, doubles, UUIDs and also Lists
(of serializable values) and Maps (where both keys and values are serializable). But if you want to write your
custom data structures to a config file, this will not be enough.

Imagine a data structure tracking how many diamonds a player has mined. It might look a little like this:

.. code-block:: java

    public class DiamondCounter {
        private UUID playerUUID;
        private int diamonds;

        ...
    }

And also some methods to access those fields, a nice constructor setting both of those etc.

Creating a custom TypeSerializer
================================

A very straightforward way of writing and loading such a data structure is providing a custom ``TypeSerializer``.
The ``TypeSerializer`` interface provides two methods, one to write the data from an object to a configuration node
and one to create an object from a given configuration node.

.. code-block:: java

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

This ``TypeSerializer`` then must be registered with configurate.

.. code-block:: java

    import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

    TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(DiamondCounter.class), new DiamondCounterSerializer());


Using ObjectMappers
===================

Since in many cases the (de)serialization boils down to mapping fields to configuration nodes, writing such a
``TypeSerializer`` is a rather dull affair and something we'd like configurate to do on its own. So let's
annotate our class with the ``@ConfigSerializable`` and ``@Setting`` annotations.

.. code-block:: java

    @ConfigSerializable
    public class DiamondCounter {

        @Setting(value="player", comment="Player UUID")
        private UUID playerUUID;
        @Setting(comment="Number of diamonds mined")
        private int diamonds;

        ...
    }

The above example can now be serialized and deserialized from config nodes without further registration. The
``@Setting`` annotations map a configuration node to the field annotated. It accepts two optional parameters,
``value`` and ``comment``. If the ``value`` parameter exists, it defines the name of the node the field will be
saved in. If it is not present, the name of the field will be used instead. So in our above example, the
annotation ensures that the contents of the field ``playerUUID`` are saved to the node "player", commented with
"Player UUID". The ``diamonds`` field however will be saved under that exact name since its annotation only
specifies a comment. That comment will be written to the config if the implementation supports commented
configuration nodes, else it will be discarded.

.. tip::

    You may also use the shorthand ``@Setting("someNode")`` instead of ``@Setting(value="someNode")``


The ``@ConfigSerializable`` annotation eliminates the need for any registration since it allows configurate to
just generate an ``ObjectMapper`` for the class. The only limitation is that configurate needs an empty
constructor to instantiate a new object before filling in the annotated fields.

Providing a custom ObjectMapperFactory
======================================

That restriction, however, can be lifted if we use a different ``ObjectMapperFactory``, for example a
``GuiceObjectMapperFactory``. Instead of requiring an empty constructor, it will work on any class that guice
can create via dependency injection. This also allows for a mixture of ``@Inject`` and ``@Setting`` annotated fields.

Your plugin can just acquire a ``GuiceObjectMapperFactory`` simply by dependency injection
(see :doc:`../injection`) and then pass it to the ``ConfigurationOptions``.

.. code-block:: java

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
