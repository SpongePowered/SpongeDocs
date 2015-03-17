================
Accessing Blocks
================
Basic Information
~~~~~~~~~~~~~~~~~
The ``BlockLoc`` class represents a block at a specific location. It will usually be accessed by calling ``getBlock(posX, posY, posZ)`` on a ``World`` object.
Assuming a ``getServer()`` method exists, this would be a way to access any block by the name of its ``World`` and coordinates.

.. code-block:: java

	import org.spongepowered.api.Server;
	import org.spongepowered.api.block.BlockLoc;
	import org.spongepowered.api.world.World;

	public BlockLoc getBlockAt(String worldName, int posX, int posY, int posZ) {
		World world = getServer().getWorld(worldName).get();
		BlockLoc block = world.getBlock(posX, posY, posZ);
		return block;
	}

.. tip:: 

	Note that the above example does not check if the world exists. ``getWorld(worldName).get()`` will fail if there is no world of that name loaded.

	
With this ``BlockLoc`` object you can then obtain further information about the block. The following code checks if a referenced block is any kind of banner by checking the blocks type

.. code-block:: java

    import org.spongepowered.api.block.BlockLoc;
    import org.spongepowered.api.block.BlockType;
    import org.spongepowered.api.block.BlockTypes;
    
    public boolean isBanner(BlockLoc block) {
        BlockType type = block.getType();
        return type == BlockTypes.STANDING_BANNER
                || type == BlockTypes.WALL_BANNER;
    }
   
.. tip ::

	For every possible type of block, there is only one ``BlockType`` instance. Therefore, simple comparison with ``==`` can be used instead of using the ``equals()`` function

The ``BlockLoc`` class also provides means to check for environmental influences on the block (for example lighting and redstone power). The following example checks if a block is a regular chest directly or indirectly powered with redstone.

.. code-block:: java

    import org.spongepowered.api.block.BlockLoc;
    
    public boolean isPoweredChest(BlockLoc block) {
        if (block.getType() == BlockTypes.CHEST) {
            return block.isPowered() || block.isIndirectlyPowered();
        } else {
            return false;
        }
    }
        
BlockStates
~~~~~~~~~~~
A ``BlockState`` contains additional data which may be present depending on the type of a block. In this example, it is checked if a Sponge block is wet or not.

.. code-block:: java

    import org.spongepowered.api.block.BlockLoc;
    import org.spongepowered.api.block.BlockState;
    import com.google.common.base.Optional;
    
    public boolean isWet(BlockLoc sponge) {
        BlockState state = block.getState();
        Optional<Boolean> wetness = (Optional<Boolean>) state.getPropertyValue("wet");
        return wetness.isPresent() && wetness.get().booleanValue();
    }    
    
The returned ``Optional`` must be cast to the appropriate type. 
Beware that if it is cast to the wrong type (for example if ``wetness`` were cast to ``Optional<String>``) it will throw a ``ClassCastException`` as soon as the ``get()`` method is called.

It will be ``Optional.absent()`` if the ``Blockstate`` contains no property of the specified Name. A list of block states and their properties can be found `here <http://minecraft.gamepedia.com/Block_state>`_. 
