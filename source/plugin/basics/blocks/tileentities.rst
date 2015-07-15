==========================
Working with Tile Entities
==========================

Tile entities are blocks that are capable of additional operations like autonomically crafting (furnaces and brewing stands) or provide effects (like a beacon or a note block). They also contain additional data like an inventory or text (like chests, signs or command blocks).

Identifying Tile Entities and their Type
========================================

Again, it all starts with a ``Location``. The ``getTileEntity()`` function will return the tile entity corresponding to the block or ``Optional.absent()`` if the block is not a tile entity.

 .. code-block:: java
    
    import org.spongepowered.api.world.Location;
 
    public boolean isTileEntity(Location block) {
        return block.getTileEntity().isPresent();
    }
    
The type of a tile entity can then be obtained by the ``getType()`` function which returns a ``TileEntityType``. Which can then be compared similar to a ``BlockType``. After performing this check the ``TileEntity`` variable can safely be cast to the according subtype.

 .. code-block:: java
  
    import org.spongepowered.api.block.tileentity.TileEntity;
    import org.spongepowered.api.block.tileentity.TileEntityTypes;
    
    public boolean isJukebox(TileEntity entity) {
        return entity.getType() == TileEntityTypes.JUKEBOX;
    }
    
    public void ejectDiscFromJukebox(TileEntity entity) {
        if (isJukebox(entity)) {
            Jukebox jukebox = (Jukebox) entity);
            jukebox.ejectRecord();
        }
    }
    
After performing this cast, the methods provided by the particular interface can be accessed (in this example the ``ejectRecord()`` method). For detailed information about ``TileEntity`` subtypes and their respective methods refer to the ``org.spongepowered.api.block.tileentity`` package and its subpackages in the API docs.


Accessing and Modifying a Tile Entity's Data
============================================

Similar to block states, the data stored in a tile entity is accessed using a ``DataManipulator``. Since the kind of a data is fully described by the ``DataManipulator`` used, all data manipulation can be done with the ``TileEntity`` interface itself and does not require a cast.

The following example contains two methods to alter the data of a sign. The first method reads (if possible) the first line, the second attempts to set it and returns the boolean value indicating its success.

 .. code-block:: java

    import org.spongepowered.api.block.tileentity.TileEntity;
    import org.spongepowered.api.data.manipulator.tileentities.SignData;
    import org.spongepowered.api.text.Text;
    
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

The main difference to working with a ``BlockState`` is that a tile entity is a mutable ``DataHolder`` as opposed to the immutable ``BlockState``.    

Accessing Inventories
=====================

Quite a share of tile entities come with their own inventory, most notably chests and furnaces. That inventory can not be accessed directly from the ``TileEntity`` interface. So a cast will be necessary. Since all tile entities containing an inventory extend the ``TileEntityCarrier`` interface it suffices to cast to that interface as shown below.

 .. code-block:: java

    import org.spongepowered.api.block.tileentity.TileEntity;
    import org.spongepowered.api.block.tileentity.carrier.TileEntityCarrier;
    import org.spongepowered.api.item.inventory.Inventory;
 
    public void useInventory(TileEntity entity) {
        if (entity instanceof TileEntityCarrier) {
            TileEntityCarrier carrier = (TileEntityCarrier) entity;
            Inventory inventory = carrier.getInventory();
            ...
        }
    }
        
On how to manipulate the inventory please refer to the according documentation.

.. TODO Link to inventory docs
