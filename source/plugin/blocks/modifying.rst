================
Modifying Blocks
================

.. javadoc-import::
    org.spongepowered.api.block.BlockSnapshot
    org.spongepowered.api.block.BlockState
    org.spongepowered.api.block.BlockType
    org.spongepowered.api.data.ImmutableDataHolder
    org.spongepowered.api.data.manipulator.ImmutableDataManipulator
    org.spongepowered.api.data.manipulator.mutable.WetData
    org.spongepowered.api.data.manipulator.mutable.block.DirtData
    org.spongepowered.api.data.type.DirtTypes
    org.spongepowered.api.world.Location

Changing a Blocks Type
~~~~~~~~~~~~~~~~~~~~~~

Changing the Type of a Block is as simple as calling the :javadoc:`Location#setBlockType(BlockType)` method with the
new :javadoc:`BlockType`. For example the following code turns the block at the given :javadoc:`Location` into a sponge:

 .. code-block:: java

    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;

    public void setToSponge(Location<World> blockLoc) {
        blockLoc.setBlockType(BlockTypes.SPONGE);
    }

It's as simple as that. If you just want to 'delete' a block (which is done by replacing it with air), you may just
use the :javadoc:`Location#removeBlock()` method provided by ``Location``.

Altering Block States
~~~~~~~~~~~~~~~~~~~~~

Similar to the above example, the ``Location`` class provides a :javadoc:`Location#setBlock(BlockState)` method
accepting a new :javadoc:`BlockState`. To make use of it, you first must acquire a ``BlockState`` you can modify. You
can do so either by getting the block's current state via the :javadoc:`Location#getBlock()` method or by using a
``BlockType``\ 's default state. The latter is demonstrated below. The default state for a Sponge block is retrieved
and then modified to directly create a wet sponge block:

 .. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.block.BlockState;
    import org.spongepowered.api.data.manipulator.mutable.WetData;

    public void setToWetSponge(Location<World> blockLoc) {
        BlockState state = BlockTypes.SPONGE.getDefaultState();
        WetData wetness = Sponge.getDataManager().
            getManipulatorBuilder(WetData.class).get().create();
        wetness.set(wetness.wet().set(true));
        BlockState newState = state.with(wetness.asImmutable()).get();
        blockLoc.setBlock(newState);
    }

Since a ``BlockState`` is an :javadoc:`ImmutableDataHolder`, you may use the provided methods ``with()`` and
``without()``, both of which will return a new altered ``BlockState`` or ``Optional.empty()`` if the given
:javadoc:`ImmutableDataManipulator` is not applicable to the kind of block represented by the ``BlockState``.

The ``with()`` method accepts an ``ImmutableDataManipulator`` and will try to create a new ``BlockState`` with the
given data set, overwriting existing values. The following example will change any dirt block to podzol.

 .. code-block:: java

    import org.spongepowered.api.data.key.Keys;
    import
        org.spongepowered.api.data.manipulator.immutable.block.ImmutableDirtData;
    import org.spongepowered.api.data.manipulator.mutable.block.DirtData;
    import org.spongepowered.api.data.type.DirtTypes;

    public void dirtToPodzol(Location<World> blockLoc) {
        BlockState state = blockLoc.getBlock();
        Optional<ImmutableDirtData> dirtDataOpt =
            state.get(ImmutableDirtData.class);

        if (dirtDataOpt.isPresent()) {
            DirtData dirtData = dirtDataOpt.get().asMutable();
            dirtData.set(Keys.DIRT_TYPE, DirtTypes.PODZOL);
            BlockState dirtState = state.with(dirtData.asImmutable()).get();
            blockLoc.setBlock(dirtState);
        }
    }

Note that the :javadoc:`DirtData` is a mutable copy of the data held in the ``BlockState``. It is changed and then
converted back to an immutable and used to create a new ``BlockState`` which then replaces the original block.

The ``without()`` method accepts a class reference and will create a new ``BlockState`` without the data
represented by the given class. If the block state would not be valid without that data, a default value will be used.
So if the ``DirtData`` from a dirt blocks state is removed, it will fall back to :javadoc:`DirtTypes#DIRT`, the default
value. The following example will dry the block at a given ``Location``, if possible.

 .. code-block:: java

    import
        org.spongepowered.api.data.manipulator.immutable.block.ImmutableWetData;

    public void dry(Location<World> blockLoc) {
        BlockState wetState = blockLoc.getBlock();
        Optional<BlockState> dryState = wetState.without(ImmutableWetData.class);
        if (dryState.isPresent()) {
            blockLoc.setBlock(dryState.get());
        }
    }

Since the :javadoc:`WetData` data manipulator represents boolean data, by removing it we set the wetness of the block
(if it has any) to false. The ``dryState.isPresent()`` check will fail on block states that can not be wet since
``dryState`` will be ``Optional.empty()`` in that case.

Copying Blocks
~~~~~~~~~~~~~~

If you want to copy all of a block's data, the :javadoc:`BlockSnapshot` class is your best friend. While it doesn't
expose all the data, it stores a ``BlockType``, its ``BlockState`` and, if necessary, all additional Tile Entity Data
(for example chest inventories). Conveniently, the ``Location`` class provides a :javadoc:`Location#createSnapshot()`
method to create a snapshot of the block at that point in time. That makes copying blocks from one location to another
very simple:

 .. code-block:: java

    import org.spongepowered.api.block.BlockSnapshot;

    public void copyBlock(Location<World> from, Location<World> to) {
        BlockSnapshot snapshot = from.createSnapshot();
        to.setBlock(snapshot.getState());
    }
