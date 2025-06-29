================
Modifying Blocks
================

.. javadoc-import::
    org.spongepowered.api.block.BlockSnapshot
    org.spongepowered.api.block.BlockState
    org.spongepowered.api.block.BlockType
    org.spongepowered.api.data.type.DirtTypes
    org.spongepowered.api.world.Location
    org.spongepowered.api.world.server.ServerLocation
    org.spongepowered.api.data.DataManipulator
    org.spongepowered.api.data.DataManipulator.Immutable
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.DataHolder.Immutable
    org.spongepowered.api.util.Direction
    org.spongepowered.api.data.Keys

Changing a Block's Type
~~~~~~~~~~~~~~~~~~~~~~~

Changing the Type of a Block is as simple as calling the :javadoc:`Location#blockType(BlockType)` method with
the new :javadoc:`BlockType`. The following code turns the block at the given :javadoc:`Location` into a
sponge:

 .. code-block:: java

    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.world.server.ServerLocation;
    import org.spongepowered.api.world.server.ServerWorld;

    public void setToSponge(ServerLocation blockLoc) {
        blockLoc.setBlockType(BlockTypes.SPONGE);
    }

It's as simple as that. If you just want to 'delete' a block (which is done by replacing it with air), you may just
use the :javadoc:`Location#removeBlock()` method provided by ``Location``.

Altering Block States
~~~~~~~~~~~~~~~~~~~~~

Similar to the above example, the ``Location`` class provides a :javadoc:`Location#setBlock(BlockState)` method
accepting a new :javadoc:`BlockState`. To make use of it, you first must acquire a ``BlockState`` you can modify. You
can do so either by getting the block's current state via the :javadoc:`Location#block()` method or by using a
``BlockType``\ 's default state. The latter is demonstrated below. The default state for a Sponge block is retrieved
and then modified to directly create a wet sponge block:

 .. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.block.BlockState;
    import org.spongepowered.api.data.Keys;

    public void setToWetSponge(ServerLocation blockLoc) {
        BlockState state = BlockTypes.SPONGE.get().getDefaultState();
        BlockState newState = state.with(Keys.IS_WET, true).get();
        blockLoc.setBlock(newState);
    }

Since a ``BlockState`` is an :javadoc:`DataHolder.Immutable`, you may use the provided methods ``with()`` and
``without()``, both of which will return a new altered ``BlockState`` or ``Optional.empty()`` if the given
:javadoc:`DataManipulator.Immutable` is not applicable to the kind of block represented by the ``BlockState``.

The ``with()`` method accepts an ``DataManipulator.Immutable`` and will try to create a new ``BlockState`` with the
given data set, overwriting existing values. The following example will change any stairs block to face east.

 .. code-block:: java

    public void faceEast(ServerLocation blockLoc) {
        BlockState state = blockLoc.block();
        Optional<BlockState> withEastState = state.with(Keys.DIRECTION, Direction.EAST);
        if (withEastState.isPresent()) {
            blockLoc.setBlock(dirtState);
        }
    }

The ``without()`` method accepts a class reference and will create a new ``BlockState`` without the data
represented by the given class. If the block state would not be valid without that data, a default value will be used.
So if the ``Keys.DIRECTION`` from a block's state is removed, it will fall back to :javadoc:`Direction#NORTH`, 
the default value. 
The following example will dry the block at a given ``Location``, if possible.

 .. code-block:: java

    public void dry(ServerLocation blockLoc) {
        BlockState wetState = blockLoc.block();
        Optional<BlockState> dryState = wetState.without(Keys.IS_WET);
        if (dryState.isPresent()) {
            blockLoc.setBlock(dryState.get());
        }
    }

Since the :javadoc:`Keys#IS_WET` data manipulator represents boolean data, by removing it we set the wetness of the block
(if it has any) to false. The ``dryState.isPresent()`` check will fail on block states that cannot be wet since
``Keys.IS_WET`` will be ``Optional.empty()`` in that case.

Copying Blocks
~~~~~~~~~~~~~~

If you want to copy all of a block's data, the :javadoc:`BlockSnapshot` class is your best friend. While it doesn't
expose all the data, it stores a ``BlockType``, its ``BlockState`` and, if necessary, all additional Tile Entity Data
(for example chest inventories). Conveniently, the ``Location`` class provides a :javadoc:`Location#createSnapshot()`
method to create a snapshot of the block at that point in time. That makes copying blocks from one location to another
very simple:

 .. code-block:: java

    import org.spongepowered.api.block.BlockSnapshot;
    import org.spongepowered.api.world.BlockChangeFlags;

    public void copyBlock(ServerLocation from, ServerLocation to) {
        BlockSnapshot snapshot = from.createSnapshot();
        to.restoreSnapshot(snapshot, false, BlockChangeFlags.ALL);
    }

