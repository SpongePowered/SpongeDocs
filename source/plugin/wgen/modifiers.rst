=======================
WorldGeneratorModifiers
=======================

.. javadoc-import::
    org.spongepowered.api.CatalogType
    org.spongepowered.api.GameRegistry
    org.spongepowered.api.data.DataContainer
    org.spongepowered.api.event.game.GameRegistryEvent.Register
    org.spongepowered.api.world.gen.WorldGeneratorModifier
    org.spongepowered.api.world.storage.WorldProperties

For a brief overview of the World Generation process in Sponge, please read :doc:`index`.
Now, let's show how you can begin making your mark on world generation.

All plugins wishing to make changes to a worlds generator must register a :javadoc:`WorldGeneratorModifier`. These
modifiers are registered globally with a unique id, which must be added to the config of a world by a server admin.
With the id specified in the world config, a ``WorldGeneratorModifier`` will be automatically called when the generator
for that world is set up, allowing it to make changes to the generator.

Creating a WorldGeneratorModifier
=================================

Let's start with the format of a ``WorldGeneratorModifier``. First, you need a class which implements
the ``WorldGeneratorModifier`` interface:

.. code-block:: java

    import org.spongepowered.api.world.storage.WorldProperties;

    public class MyModifier implements WorldGeneratorModifier {

        @Override
        public String getId() {
            return "pluginid:mymodifier";
        }

        @Override
        public String getName() {
            return "My Modifier";
        }

        @Override
        public void modifyWorldGenerator(WorldProperties world, DataContainer settings, WorldGenerator worldGenerator) {

        }

    }

As you can see, ``WorldGeneratorModifier`` has three methods which we override. :javadoc:`CatalogType#getId()` must be
overridden to return a constant and unique identifier for your ``WorldGeneratorModifier``, this is the identifier which
will be used in the world configuration to specify which worlds your modifier should be applied to.
:javadoc:`CatalogType#getName()` must be overridden with a constant and simple human-readable name for your modifier.

The third overridden method is where you make your changes to the world generator. This method is called by
the implementation when it is creating the world generator for a world which has specified that your
``WorldGeneratorModifier`` should be applied.

The :javadoc:`WorldProperties` and a :javadoc:`DataContainer` of additional properties for the world are passed
to this method in order to give context for your changes. For instance, you can use the ``WorldProperties`` to only
apply your generator changes to nether worlds.

Registering a WorldGeneratorModifier
====================================

Now that you have created the modifier, all you have to do is register it in the :javadoc:`GameRegistry`. To do this
you need to create a :doc:`listener </plugin/event/listeners>` for the 
:javadoc:`GameRegistryEvent.Register<WorldGeneratorModifier>` event and register your instance there, as shown in the
following example:

.. code-block:: java

    @Listener
    public void onWorldGeneratorModifierRegistration(GameRegistryEvent.Register<WorldGeneratorModifier> event) {
        event.register(new MyModifier());
    }

To apply your WorldGeneratorModifier to a world you must add it to the ``world-generation-modifiers`` array within
the world config file found at ``config/sponge/worlds/[dimension]/[worldName]/world.conf``. For example, to apply
the skylands WorldGeneratorModifier to a world you would add the skylands modifier's id to the modifiers list.

.. code-block:: text

    # WorldGenerationModifiers to apply to the world
    world-generation-modifiers=[
        "sponge:skylands"
    ]

Note that the ``world-generation-modifiers`` list may not be there, as by default there are no modifiers applied to
a world and therefore the value is not created when the configuration file is created.

In the next articles we will look deeper at the changes we can make from our ``WorldGeneratorModifier``.
