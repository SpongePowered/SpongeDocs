================
Modifying Blocks
================

Changing a Blocks Type
~~~~~~~~~~~~~~~~~~~~~~

Changing the Type of a Block is as simple as calling the ``replaceWith()`` method of a ``Location`` with the new ``BlockType``. For example the following code turns the block at the given ``Location`` into a sponge.

 .. code-block:: java
 
    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.world.Location;
 
    public void setToSponge(Location block) { 
        block.replaceWith(BlockTypes.SPONGE);
    }

It's as simple as that. If you just want to 'delete' a block (which is done by replacing it with air), you may just use the ``remove()`` method provided by ``Location``.
    
Altering Block States
~~~~~~~~~~~~~~~~~~~~~

Similar to the above example, the ``Location`` class provides a ``replaceWith()`` method accepting a new ``BlockState``. To make use of it, you first must acquire a ``BlockState`` you can modify. You can do so either by getting the block's current state via the ``getState()`` method provided by the ``Location`` class or by using a blocktype's default state. The latter is demonstrated below: The default state for a Sponge block is retrieved and then modified to directly create a wet sponge block.

 .. code-block:: java
 
    import org.spongepowered.api.Game;
    import org.spongepowered.api.block.BlockState;
    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.data.manipulators.WetData;
    import org.spongepowered.api.world.Location;
 
    public void setToWetSponge(Location block) {
        BlockState state = BlockTypes.SPONGE.getDefaultState();
        WetData wetness = game.getRegistry().getManipulatorRegistry()
            .getBuilder(WetData.class).get().create();
        BlockState newState = state.withData(wetness).get();
        block.replaceWith(newState);
    }

A ``BlockState`` itself and its data are immutable, but it provides the methods ``withData()`` and ``withoutData()`` both of which will return a new altered ``BlockState`` or ``Optional.absent()`` if the given ``DataManipulator`` is not applicable to the kind of block represented by the ``BlockState``. 

The ``withData()`` method accepts a ``DataManipulator`` and will try to create a new ``BlockState`` with the given data set, overwriting existing values. The following example will change any dirt block to podzol.

 .. code-block:: java
    
    import org.spongepowered.api.block.BlockState;
    import org.spongepowered.api.data.manipulators.blocks.DirtData;
    import org.spongepowered.api.data.types.DirtTypes;
    import org.spongepowered.api.world.Location;
    
    public void dirtToPodzol(Location block) {
        BlockState state = block.getState();
        Optional<DirtData> dirtData = state.getManipulator(DirtData.class);
        if (dirtData.isPresent()) {
            DirtData coarseData = dirtData.get();
            coarseData.setValue(DirtTypes.PODZOL);
            BlockState coarseState = state.withData(coarseData).get();
            block.replaceWith(coarseState);
        }
    }

Note that the ``DirtData`` is a mutable copy of the data held in the ``BlockState``. It is changed and then used to create a new ``BlockState`` which then replaces the original block.


The ``withoutData()`` method accepts a class reference and will create a new ``BlockState`` without the data represented by the given class. If the block state would not be valid without that data, a default value will be used. So if the ``DirtData`` from a dirt blocks state is removed, it will fall back to ``DirtTypes.DIRT``, the default value. 
The following example will dry the block at a given ``Location``, if possible. 

 .. code-block:: java
 
    import org.spongepowered.api.block.BlockState;
    import org.spongepowered.api.data.manipulators.WetData;
    import org.spongepowered.api.world.Location;
 
    public void dry(Location block) {
        BlockState wetState = block.getState();
        Optional<BlockState> dryState = wetState.withoutData(WetData.class);
        if (dryState.isPresent()) {
            block.replaceWith(dryState.get());
        }
    }
 
Since the ``WetData`` data manipulator represents boolean data, by removing it we set the wetness of the block (if it has any) to false. The ``dryState.isPresent()`` check will fail on block states that can not be wet since ``dryState`` will be ``Optional.absent()`` in that case.

Copying Blocks
~~~~~~~~~~~~~~

If you want to copy all of a blocks data, the ``BlockSnapshot`` class is your best friend. While it doesn't expose all the data, it stores a Blocks type, its BlockState and, if necessary, all additional Tile Entity Data (for example chest inventories). Conventiently, the ``Location`` class provides a ``getSnapshot()`` method as well as a ``replaceWith()`` method accepting a ``BlockSnapshot``. That makes copying blocks from one location to another very simple:

 .. code-block:: java
     
    import org.spongepowered.api.block.BlockSnapshot;
    import org.spongepowered.api.world.Location;
 
    public void copyBlock(Location from, Location to) {
        BlockSnapshot snapshot = from.getSnapshot();
        to.replaceWith(snapshot);
    }
