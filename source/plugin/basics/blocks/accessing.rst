================
Accessing Blocks
================
Basic Information
~~~~~~~~~~~~~~~~~
Blocks are most commonly identified and accessed by their ``Location``. This location points to a certain coordinate within an ``Extent``. In most cases a ``World`` will be used as the Extent.

.. code-block:: java

	import org.spongepowered.api.Game;
	import org.spongepowered.api.world.Location;
	import org.spongepowered.api.world.World;

	public BlockLoc getBlockAt(String worldName, int posX, int posY, int posZ) {
		World world = game.getServer().getWorld(worldName).get();
		Location block = new Location(world, posX, posY, posZ);
		return block;
	}

.. warning:: 

	Note that the above example does not check if the world exists. ``getWorld(worldName).get()`` will fail if there is no world of that name loaded.

	
With this ``Location`` object you can then obtain further information about the block. The following code checks if a referenced block is any kind of banner by checking the blocks type

.. code-block:: java

    import org.spongepowered.api.block.BlockType;
    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.world.Location;
    
    public boolean isBanner(Location block) {
        BlockType type = block.getType();
        return type == BlockTypes.STANDING_BANNER
                || type == BlockTypes.WALL_BANNER;
    }
   
.. tip ::

	For every possible type of block, there is only one ``BlockType`` instance. Therefore, simple comparison with ``==`` can be used instead of using the ``equals()`` function

The ``Location`` class also provides means to check for environmental influences on the block (for example lighting and redstone power). The following example checks if a block is a regular chest directly or indirectly powered with redstone.

.. code-block:: java

    import org.spongepowered.api.world.Location;
    
    public boolean isPoweredChest(Location block) {
        if (block.getType() == BlockTypes.CHEST) {
            return block.isPowered() || block.isIndirectlyPowered();
        } else {
            return false;
        }
    }
        
BlockStates
~~~~~~~~~~~
Additional Data (like orientation, subtypes like granite vs regular stone, farm land moisture ... ) can be obtained from a ``BlockState``. A ``BlockState`` contains a ``BlockType`` and further data that can be accessed via a set of data manipulators that all extend the ``DataManipulator`` interface. The following example will return ``true`` if a the block at a given ``Location`` is a) a Sponge and b) wet, false otherwise.

.. code-block:: java

    import org.spongepowered.api.block.BlockState;
    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.data.manipulators.WetData;
    import org.spongepowered.api.world.Location;
    
    public boolean isWet(Location block) {
        if (block.getType() != BlockTypes.SPONGE) {
            return false;
        }
        BlockState sponge = block.getState();
        Optional<WetData> wetness = sponge.getManipulator(WetData.class);
        return wetness.isPresent();
    }   

First, we need to know which ``DataManipulator`` subinterface we need. Those that are applicable to blocks are found in the ``org.spongepowered.api.data.manipulators`` and ``org.spongepowered.api.data.manipulators.block`` packages. Then we can just pass that class to the ``getManipulator()`` method and get an ``Optional`` in return which will be ``absent()`` if the ``BlockState`` does not contain data of that type.
Since ``WetData`` represents a boolean value, its presence equates to ``true``. Its absence (if ``Optional.absent()`` was returned) either signifies ``false``.

.. tip::
	For a more detailed take on DataManipulators and how to use them, take a look at ToDo:HERE BE LINK