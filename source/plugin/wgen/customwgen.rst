==========================
Modifying World Generation
==========================

- Creating a WorldGeneratorModifer
- Registering a WorldGeneratorModifer
- Modifying Vanilla Generation
- Creating Custom Base Terrain
- Creating Custom GenerationPopulators
- Creating Custom Populators
- Creating Custom Biomes

For a brief overview of the World Generation process in Sponge, please read :doc:`index`.
Now, let's show how you can begin making your mark on world generation.
All plugins wishing to make changes to a worlds generator must register a ``WorldGeneratorModifier``.
These modifiers are registered globally with a unique name, which must be added to the config of a world
by a server admin. With the name specified in the world config, a ``WorldGeneratorModifer`` will be
automatically called when the generator for that world is set up, allowing it to make changes to the generator.

Creating a WorldGeneratorModifer
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Let's start with the format of a ``WorldGeneratorModifier``. First, you need a class which implements
the WorldGeneratorModifier interface:

.. code-block:: java

    import org.spongepowered.api.data.DataContainer;
    import org.spongepowered.api.world.WorldCreationSettings;
    import org.spongepowered.api.world.gen.WorldGenerator;
    import org.spongepowered.api.world.gen.WorldGeneratorModifier;

    public class MyModifier implements WorldGeneratorModifier {

        @Override
        public String getId() {
            return "myplugin:mymodifier";
        }

        @Override
        public String getName() {
            return "MyAwesomeModifier";
        }

        @Override
        public void modifyWorldGenerator(WorldCreationSettings world, DataContainer settings, WorldGenerator worldGenerator) {
            //Make changes here
        }
    }

As you can see, ``WorldGeneratorModifier`` has three methods which we override. The first method is ``getId()``,
which must be overridden to return a constant and unique identifier for your ``WorldGeneratorModifier``.
This value will be used to refer to your ``WorldGeneratorModifier`` from the world configuration.

The second one returns a name, which is a more human-readable form of identification. It does not need to be unique but
nonetheless should be descriptive.

The third overridden method is where you make your changes to the world generator. This method is called by
the implementation when it is creating the world generator for a world which has specified that your
``WorldGeneratorModifier`` should be applied.

The ``WorldGenerationSettings`` and a ``DataContainer`` of additional properties for the world are passed to this method
in order to give context for your changes. For instance, you can use the ``WorldCreationSettings`` to only apply your
generator changes to nether worlds.


Registering a WorldGeneratorModifer
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Now that you have created our modifier, you need to register it. A good place to do this is in an
``GamePreInitializationEvent`` {Citation needed}. To register it, simply call ``GameRegistry.registerWorldGeneratorModifier``.

.. code-block:: java

    import org.spongepowered.api.GameRegistry;
    import org.spongepowered.api.event.Listener;
    import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
    import com.google.inject.Inject;

    @Inject GameRegistry registry;

    @Listener
    public void initialize(GamePreInitializationEvent event) {
        registry.registerWorldGeneratorModifier(new MyModifier());
    }

[TODO something about how to specify to use the modifier in the world configuration.]

In the next articles we will look deeper at the changes we can make from our ``WorldGeneratorModifer``.


Modifying Vanilla Generation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. note::

    This page assumes that you are familiar with setting up your ``WorldGeneratorModifier``.
    If not then please see the article on setting up your plugin. {Citation needed}

Sponge exposes a great deal of vanilla world generation, which can be manipulated through the various interfaces.
Currently, the only elements of the generation process that are *easily* exposed to manipulation are the populators.

For a quick example, let's look at how we would change the cactii that spawn in deserts to be taller.

.. code-block:: java

    import org.spongepowered.api.world.biome.BiomeGenerationSettings;
    import org.spongepowered.api.world.biome.BiomeTypes;
    import org.spongepowered.api.world.gen.Populator;
    import org.spongepowered.api.world.gen.populator.Cactus;

    @Override
    public void modifyWorldGenerator(WorldCreationSettings world, DataContainer settings, WorldGenerator worldGenerator) {
         BiomeGenerationSettings desertSettings = worldGenerator.getBiomeSettings(BiomeTypes.DESERT);
         for(Populator populator: desertSettings.getPopulators()) {
            if(populator instanceof Cactus) {
                ((Cactus) populator).setHeight(5);
            }
        }
    }

Start by getting the ``BiomeGenerationSettings`` for the desert biome. This object is a container for all generation
settings relating to that biome. Next, iterate through the list of populators and check for the Cactus populator.
Every cactus populator found is set to height 5, which means it can only generate cactii which are 5 blocks tall.

However there is a problem with the code example above. It calls ``BiomeTypes.DESERT``, and that means modifying *every*
desert, in *every* world. If you only want to modify the deserts in a specific world, you must use *Biome Overrides*.

Within the ``WorldGenerator`` interface there are a number of methods that deal with Biome Overrides.
These overrides are alternate instances of ``BiomeGenerationSettings`` that are used in place of the default
generation settings specified on the ``BiomeType``.

Start by creating a new ``BiomeGenerationSettings`` instance which is a perfect copy of the settings specified
by the desert biome. Then apply your changes to this instance, and finally apply this new instance to the world
generator as an override for the desert biome.

.. code-block:: java

    @Override
    public void modifyWorldGenerator(WorldCreationSettings world, DataContainer settings, WorldGenerator worldGenerator) {
        BiomeGenerationSettings desertSettings;
        if(worldGenerator.isBiomeOverriden(BiomeTypes.DESERT)) {
            desertSettings = worldGenerator.getBiomeOverride(BiomeTypes.DESERT);
        } else {
            desertSettings = BiomeTypes.DESERT.getGenerationSettings().clone();
        }
        for(Populator populator: desertSettings.getPopulators()) {
            if(populator instanceof Cactus) {
                ((Cactus) populator).setHeight(5);
            }
        }
        worldGenerator.addBiomeOverride(BiomeType.DESERT, desertSettings);
    }

Observer that the loop for changing the Cactus populator has stayed the same, but it has changed how we
get the desertSettings. First it checks if the biome has already been overridden for this world generator
(probably by another plugin), if so then it get the settings from the world generator. If it has not been
overriden then it gets a clone of the desert biome's settings. Then after changes have been made, the
settings are applied back to the world generator as an override for the desert biome.

This has been a simple example of how to modify an existing populator. Let's look at how we can add a new
instance of a vanilla populator. This time the populator will be added globally, which means it will be
applied to all chunks regardless of the biome. Let's add a Pumpkin populator globally, causing pumpkins to be
scattered everywhere throughout the world.

.. code-block:: java

    import org.spongepowered.api.world.gen.populator.Pumpkin;

    @Override
    public void modifyWorldGenerator(WorldCreationSettings world, DataContainer settings, WorldGenerator worldGenerator) {
        Pumpkin.Builder builder = Pumpkin.builder();
        builder.perChunk(12);
        Pumpkin pumpkinPopulator = builder.build();
        worldGenerator.getPopulators().add(pumpkinPopulator);
    }

Contrary to the previous example, this time you are creating an entirely new populator. To do this, first you need to
get a builder for that populator from the ``PopulatorFactory`` (which can be fetched from the game registry).
Then set your desired settings for the populator into it - in this case, we want a dozen pumpkins to spawn per patch.
Finally, add your new populator to the list of populators that are applied globally to the world.

Voila, now we have pumpkins everywhere.

These two examples should serve to help you get familiar with the realm of working with vanilla populators.
This only touches the surface of what is possible. For a complete listing of available populators, and their
properties, see the javadocs for a complete listing of available populators and their properties.


Creating Custom Base Terrain
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Changing the base ``GenerationPopulator`` of a world generator allows you to change the base terrain shape
generation of the world. A generator populator will roughly follow the procedure of using the seed and biome
information to seed and modify a series of noise maps, from which the terrain is formed. The terrain created
in a modified base generator populator should only consist of stone blocks, to allow the biomes to properly replace
blocks for biome-specific ground cover.

.. code-block:: java

    public class SinusoidalGenerator implements GenerationPopulator {

        @Override
            public void populate(World world, MutableBlockVolume buffer, ImmutableBiomeArea biomes) {
                for(int x = buffer.getBlockMin().getX(); x < buffer.getBlockMax().getX(); x++) {
                    for(int z = buffer.getBlockMin().getZ(); z < buffer.getBlockMax().getZ(); z++) {
                        BiomeType biome = biomes.getBiome(x,z);
                        int height = getHeight(x, z, biome);
                        for(int y = 0; y < height || y < 64; y++) {
                            if(y < height) {
                                buffer.setBlockType(x, y, z, BlockTypes.STONE);
                            } else {
                                buffer.setBlockType(x, y, z, BlockTypes.WATER);
                            }
                        }
                    }
                }
            }

        private int getHeight(int x, int z, BiomeType biome) {
            double sx = Math.sin(x / 64d) + 1;
            double sz = Math.sin(z / 64d) + 1;
            double value = (sx + sz) / 4d;
            BiomeGenerationSettings settings = null; // No object of that type obtainable
            double heightRange = settings.getMaxHeight() - settings.getMinHeight();
            double height = heightRange * value / settings.getMinHeight();
            return GenericMath.floor(height * 256);
        }
    }

This is a fairly simple example of a base terrain generation populator (at least, if you look past the math to
calculate the height). For each column in the buffered area we want to calculate a height value, and then fill
in everything below that with stone and leave everything above it as air (or water if we're still below sea-level).

[You can of course ... TBA]

[TBA Add some simple example of creating noise with flow-noise and creating terrain from it.]


Creating Custom GenerationPopulators
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

[TBA]


Creating Custom Populators
~~~~~~~~~~~~~~~~~~~~~~~~~~

Custom populators can be used to add a great variety of custom features. To create a custom populator you need
only create a class implementing the Populator interface and add it to the list of populators attached to a
biome, or to a world generator if you want it applied globally.

The key thing to remember when creating a populator is that the area affected by the populator is a 16x16 area
offset by 8 in both the x and z axes

[see image]

[Insert an example or two.]

Creating Custom Biomes
~~~~~~~~~~~~~~~~~~~~~~

*At present it is not possible to create entirely new biomes from purely within the SpongeAPI.*
