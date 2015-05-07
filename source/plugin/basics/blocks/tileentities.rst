==========================
Working with Tile Entities
==========================

Tile entities are blocks that are capable of additional operations like autonomically crafting (furnaces and brewing stands) or provide effects (like a beacon or a note block). They also contain additional data like an inventory or text (like chests, signs or command blocks).

Identifying Tile Entities and their Type
========================================

Again, it all starts with a ``Location``. The ``getTileEntity()`` function will return the tile entity corresponding to the block or ``Optional.absent()`` if the block is not a tile entity.

 .. code-block:: java
 
	public boolean isTileEntity(Location block) {
		return block.getTileEntity().isPresent();
	}
	
The type of a tile entity can then be obtained by the ``getType()`` function which returns a ``TileEntityType``. Which can then be compared similar to a ``BlockType``. After performing this check the ``TileEntity`` variable can safely be cast to the according subtype.

 .. code-block:: java
 
	public boolean isJukebox(TileEntity entity) {
		return entity.getType() == TileEntityTypes.JUKEBOX;
	}
	
	public Optional<Jukebox> getJukebox(TileEntity entity) {
		if (isJukebox(entity)) {
			return Optional.of((Jukebox) entity);
		} else {
			return Optional.absent();
		}
	}
	
After this performing this cast, the methods provided by the particular interface can be accessed. In our example we could now easily obtain a ``Jukebox`` object and make use of its methods. For detailed information about ``TileEntity`` subtypes and their respective methods refer to the ``org.spongepowered.api.block.tile`` package and its subpackages in the API docs.


Accessing and Modifying a Tile Entity's Data
============================================

Similar to block states, the data stored in a tile entity is accessed via ``DataManipulator``s. Since the kind of a data is fully described by the ``DataManipulator`` used, all data manipulation can be done with the ``TileEntity`` interface itself and does not require a cast.

The following example contains two methods to alter the data of a sign. The first method reads (if possible) the first line, the second attempts to set it and returns the boolean value indicating its success.

 .. code-block:: java

    public Optional<Text> getFirstLine(TileEntity entity) {
        Optional<SignData> data = entity.getOrCreate(SignData.class);
        if (data.isPresent()) {
            return Optional.of(data.get().getLine(0));
        } else {
            return Optional.absent();
        }
    }
    
    public boolean setFirstLine(TileEntity entity, Text line) {
        if (entity.isCompatible(SignData.class)) {
            SignData sign = entity.getOrCreate(SignData.class).get();
            sign.setLine(0, line);
            entity.offer(sign);
            return true;
        } else {
            return false;
        }
    }

The main difference to working with ``BlockState``s is that a tile entity is a mutable ``DataHolder`` as opposed to the immutable ``BlockState``.	

Accessing Inventories
=====================

Quite a share of tile entities come with their own inventory, most notably chests and furnaces. That inventory can not be accessed directly from the ``TileEntity`` interface. So a cast will be necessary. Since all tile entities containing an inventory extend the ``TileEntityCarrier`` interface it suffices to cast to that interface as shown below.

 .. code-block:: java
 
	public Optional<Inventory> getInventory(TileEntity entity) {
        if (entity instanceof TileEntityCarrier) {
            TileEntityCarrier carrier = (TileEntityCarrier) entity;
            Inventory inventory = carrier.getInventory();
            return Optional.of(inventory);
        } else {
            return Optional.absent();
        }

On how to manipulate the returned inventory please refer to the according documentation.
