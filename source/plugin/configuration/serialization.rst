===================
Serializing Objects
===================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

.. javadoc-import::

    java.util.List
    java.util.Map
    java.util.Set
    java.util.UUID
    java.net.URL
    java.net.URI
    java.util.regex.Pattern
    net.kyori.adventure.text.Component
    org.spongepowered.configurate.ConfigurationOptions
    org.spongepowered.configurate.objectmapping.GuiceObjectMapperFactory
    org.spongepowered.configurate.objectmapping.ObjectMapper
    org.spongepowered.configurate.objectmapping.ObjectMapperFactory
    org.spongepowered.configurate.objectmapping.Setting
    org.spongepowered.configurate.objectmapping.serialize.ConfigSerializable
    org.spongepowered.configurate.objectmapping.serialize.TypeSerializer
    org.spongepowered.configurate.objectmapping.serialize.TypeSerializerCollection
    org.spongepowered.api.util.TypeTokens

The Configurate library also provides the means to tweak automatic serialization and deserialization of objects.
Per default, a set of data types can be (de)serialized: 

* ``String``\s, the most commonly used primitive types and their wrappers
* :javadoc:`List`\s and :javadoc:`Set`\s of serializable values (not including specific implementations)
* :javadoc:`Map`\s where both keys and values are serializable (not including specific implementations)
* The types :javadoc:`UUID`, :javadoc:`URL`, :javadoc:`URI` and :javadoc:`Pattern {(regex) Pattern}`
* Any enum or :doc:`CatalogType </plugin/data/catalog-types>`
* The text type :doc:`Component </plugin/text/index>` (See also :doc:`here </plugin/text/representations/index>`)

.. note::

    If you need special constraints or rules for your serialization (such as sorting the elements in a ``Set``),
    then you should consider using your own ``TypeSerializer`` implementations.

But if you want to write your custom data structures to a config file, this will not be enough.

Imagine a data structure tracking how many diamonds a player has mined. It might look a little like this:

.. code-block:: java

    public class DiamondCounter {
        private UUID playerUUID;
        private int diamonds;

        [...]
    }

Also assume some methods to access those fields, a nice constructor setting both of those etc.

Creating a Custom TypeSerializer
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

.. note::

    ``ConfigurationOptions`` are immutable. Every time you try to modify the original instance a new instance is
    created; so you either have to use the (chained) result directly or update your variable accordingly.

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

.. tip::

    If you need the ``TypeToken.of(DiamondCounter.class)`` in multiple places, then you should consider creating a
    constant for it. You can do it in a similar fashion as Sponge does in the :javadoc:`TypeTokens` class, or just
    define the constant inside of your data or serializer class.

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

        [...]
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

.. note::

    You can also have fields that are not are not annotated with ``@Setting`` in your ``@ConfigSerializable`` classes.
    These fields won't be persisted to config files and can be used to store temporary references for your plugin.

Using Default Values in ConfigSerializable Types
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It is also possible to use default values inside of ``@ConfigSerializable`` types. You just have to use Java's field
initializers (or getters) to set some default values. As long as the entry is not present in the config file the value
won't be overwritten.

.. code-block:: java

    @ConfigSerializable
    public class DiamondCounter {

        @Setting(value="player", comment="Player UUID")
        private UUID playerUUID;

        @Setting(comment="Number of diamonds mined")
        private int diamonds = 0;
        
        @Setting(comment="The time the player found a diamond last.")
        private LocalDateTime diamonds = LocalDateTime.now();

        [...]
    }

Example: Loading a ConfigSerializable Config with Default Values
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Instead of loading a default config from the plugin jar itself, it is also possible to just ask Configurate to create
it if it is missing.

.. code-block:: java

    try {
        this.config = this.configManager.load().<Configuration>getValue(Configuration.TYPE, Configuration::generateDefault);
    } catch (ObjectMappingException | IOException e) {
        this.logger.error("Failed to load the config - Using a default", e);
        this.config = Configuration.generateErrorDefault();
    }

In this case you load the entire configuration into a ``Configuration`` object that contains all of your plugins
configuration. Using such a class has the following benefits:

* Type safety is guaranteed
* No need to update the configuration file shipped in your plugin
* You don't need to store lots of references for each of your configuration options
* You can pass this config (or its parts) into methods or reference it from other classes
* It is easy to write comments for each attribute in a place that also helps you during development

.. note::

    In this case ``Configuration.generateDefault()`` is called when the config file is missing or empty.
    If you still want to load the shipped default config asset you can load it inside of that method.
    ``Configuration.generateErrorDefault()`` is called when there is an error reading or parsing the config.
    It is not necessary to use separate methods for those cases; you can also use the no-arg constructor,
    or use an entirely custom solution.

Example: Saving a ConfigSerializable Config
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Saving a ``@ConfigSerializable`` config is also very simple, as shown by the following example:

.. code-block:: java

    try {
        this.configManager.save(this.configManager.createEmptyNode().setValue(Configuration.TYPE, this.config));
    } catch (IOException | ObjectMappingException e) {
        this.logger.error("Failed to save the config", e);
    }

Providing a custom ObjectMapperFactory
======================================

That restriction, however, can be lifted if we use a different :javadoc:`ObjectMapperFactory`, for example a
:javadoc:`GuiceObjectMapperFactory`. Instead of requiring an empty constructor, it will work on any class that guice
can create via dependency injection. This also allows for a mixture of ``@Inject`` and ``@Setting`` annotated fields.

Your plugin can just acquire a ``GuiceObjectMapperFactory`` simply by dependency injection
(see :doc:`../injection`) and then pass it to the ``ConfigurationOptions``.

.. code-block:: java

    import org.spongepowered.api.event.Listener;
    import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
    import org.spongepowered.api.plugin.Plugin;
    import com.google.inject.Inject;
    import ninja.leaping.configurate.commented.CommentedConfigurationNode;
    import ninja.leaping.configurate.loader.ConfigurationLoader;
    import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;

    @Plugin(name="IStoleThisFromZml", id="shamelesslystolen", version="0.8.15", description = "Stolen")
    public class StolenCodeExample {

        @Inject private GuiceObjectMapperFactory factory;
        @Inject private ConfigurationLoader<CommentedConfigurationNode> loader;

        @Listener
        public void enable(GamePreInitializationEvent event) throws IOException, ObjectMappingException {
            CommentedConfigurationNode node =
              loader.load(ConfigurationOptions.defaults().setObjectMapperFactory(factory));
            DiamondCounter myDiamonds = node.getValue(TypeToken.of(DiamondCounter.class));
        }
    }

.. note::

    The above code is an example and, for brevity, lacks proper exception handling.
