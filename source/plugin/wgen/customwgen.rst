==========================
Modifying World Generation
==========================

.. javadoc-import::
    org.spongepowered.api.world.biome.BiomeGenerationSettings
    org.spongepowered.api.util.weighted.VariableAmount
    org.spongepowered.api.world.gen.BiomeGenerator
    org.spongepowered.api.world.gen.GenerationPopulator
    org.spongepowered.api.world.gen.WorldGeneratorModifier
    org.spongepowered.api.world.gen.populator.Cactus

- Modifying Vanilla Generation
- Creating Custom Base Terrain
- Creating Custom GenerationPopulators
- Creating Custom Populators
- Creating Custom Biomes

Modifying Vanilla Generation
============================

.. note::

    This page assumes that you are familiar with setting up your :javadoc:`WorldGeneratorModifier`. If not, then please
    read the article on setting up your modifier at :doc:`modifiers`.

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
        for (Cactus populator : desertSettings.getPopulators(Cactus.class)) {
            populator.setHeight(5);
        }
    }

Start by getting the :javadoc:`BiomeGenerationSettings` for the desert biome. This object is a container for all
generation settings relating to that biome. Next, iterate through the list of all :javadoc:`Cactus` populators and set
the height to 5, which means it can only generate cactii which are 5 blocks tall.

.. note::

    The :javadoc:`Cactus#setHeight(int)`, and many other similar methods on other populators, also takes a
    :javadoc:`VariableAmount` which can be used to specify the height as a range or other custom value.

This has been a simple example of how to modify an existing populator. Let's look at how we can add a new
instance of a vanilla populator. This time the populator will be added globally, which means it will be
applied to all chunks regardless of the biome. Let's add a Pumpkin populator globally, causing pumpkins to be
scattered everywhere throughout the world.

.. code-block:: java

    import org.spongepowered.api.world.gen.populator.Pumpkin;

    @Override
    public void modifyWorldGenerator(WorldCreationSettings world, DataContainer settings, WorldGenerator worldGenerator) {
        Pumpkin pumpkinPopulator = Pumpkin.builder().perChunk(12).build();
        worldGenerator.getPopulators().add(pumpkinPopulator);
    }

Contrary to the previous example, this time you are creating an entirely new populator. To do this, first you need to
get a builder for that populator. Then set your desired settings for the populator into it - in this case, we want a
dozen pumpkins to spawn per patch. Finally, add your new populator to the list of populators that are applied globally
to the world.

Voila, now we have pumpkins everywhere.

.. note::

    In this example we added the pumpkin populator to the end of the populators list, but it should be noted that
    this list is order dependent. So if you would like your populator to be called earlier than other populators,
    as is usually a good idea with Forest populators, then your should add your populator to the start of the list.

These two examples should serve to help you get familiar with the realm of working with vanilla populators.
This only touches the surface of what is possible. See the javadocs for a complete listing of available populators
and their properties.


Creating Custom Base Terrain
============================

Changing the base :javadoc:`GenerationPopulator` of a world generator allows you to change the base terrain shape
generation of the world. A generator populator will roughly follow the procedure of using the seed and biome information
to seed and modify a series of noise maps, from which the terrain is formed. The terrain created in a modified base
generator populator should only consist of stone blocks, to allow the biomes to properly replace blocks for
biome-specific ground cover.

.. code-block:: java

    public class SinusoidalGenerator implements GenerationPopulator {

        @Override
        public void populate(World world, MutableBlockVolume buffer, ImmutableBiomeArea biomes) {
            for(int x = buffer.getBlockMin().getX(); x < buffer.getBlockMax().getX(); x++) {
                for(int z = buffer.getBlockMin().getZ(); z < buffer.getBlockMax().getZ(); z++) {
                    BiomeType biome = biomes.getBiome(x,z);
                    int height = getHeight(x, z, world.getWorldGenerator().getBiomeSettings(biome));
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

        private int getHeight(int x, int z, BiomeGenerationSettings biome) {
            double sx = Math.sin(x / 64d) + 1;
            double sz = Math.sin(z / 64d) + 1;
            double value = (sx + sz) / 4d;
            double heightRange = biome.getMaxHeight() - biome.getMinHeight();
            double height = heightRange * value / biome.getMinHeight();
            return GenericMath.floor(height * 256);
        }
    }

This is a fairly simple example of a base terrain generation populator (at least, if you look past the math to
calculate the height). For each column in the buffered area we want to calculate a height value, and then fill
in everything below that with stone and leave everything above it as air (or water if we're still below sea-level).

Creating Custom GenerationPopulators
====================================

.. note::

  The API for custom GenerationPopulators isn't finished yet. This section will be expanded in the future.

Creating Custom Populators
==========================

Custom populators can be used to add a great variety of custom features. To create a custom populator you need
only create a class implementing the Populator interface and add it to the list of populators attached to a
biome, or to a world generator if you want it applied globally.

The key thing to remember when creating a populator is that the area affected by the populator is a 16x16 area
offset by 8 in both the x and z directions.

Creating Custom Biomes
======================

While it is currently not possible to create entirely new biomes from within sponge, you can replace the system
by which they are arranged in the world be implementing the :javadoc:`BiomeGenerator` interface and setting your custom
biome generator onto a WorldGenerator.

Below is an example of a biome generator which creates one large island centered around (0, 0).

.. code-block:: java

    public class IslandBiomeGen implements BiomeGenerator {

        private static final double ISLAND_SIZE = 200f;
        private static final double BEACH_RADIUS = ISLAND_SIZE * ISLAND_SIZE;
        private static final double FOREST_SIZE = ISLAND_SIZE - 7;
        private static final double FOREST_RADIUS = FOREST_SIZE * FOREST_SIZE;
        private static final double HILLS_SIZE = FOREST_SIZE - 120;
        private static final double HILLS_RADIUS = HILLS_SIZE * HILLS_SIZE;

        @Override
        public void generateBiomes(MutableBiomeArea buffer) {
            Vector2i min = buffer.getBiomeMin();
            Vector2i max = buffer.getBiomeMax();

            for (int x = min.getX(); x <= max.getX(); x++) {
                for (int y = min.getY(); y <= max.getY(); y++) {
                    if (x * x + y * y < HILLS_RADIUS) {
                        buffer.setBiome(x, y, BiomeTypes.EXTREME_HILLS);
                    } else if (x * x + y * y < FOREST_RADIUS) {
                        buffer.setBiome(x, y, BiomeTypes.FOREST);
                    } else if (x * x + y * y < BEACH_RADIUS) {
                        buffer.setBiome(x, y, BiomeTypes.BEACH);
                    } else {
                        buffer.setBiome(x, y, BiomeTypes.OCEAN);
                    }
                }
            }
        }
    }
