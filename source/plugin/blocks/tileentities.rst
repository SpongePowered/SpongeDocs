=============
Tile Entities
=============

.. javadoc-import::
    org.spongepowered.api.block.BlockType
    org.spongepowered.api.block.tileentity.Jukebox
    org.spongepowered.api.block.tileentity.TileEntity
    org.spongepowered.api.block.tileentity.TileEntityType
    org.spongepowered.api.block.tileentity.carrier.TileEntityCarrier
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.world.Location

Tile entities are blocks that are capable of additional operations like autonomically crafting (furnaces and brewing
stands) or provide effects (like a beacon or a note block). They also contain additional data like an inventory or
text (like chests, signs or command blocks).

Identifying Tile Entities and their Type
========================================

Again, it all starts with a :javadoc:`Location`. The :javadoc:`Location#getTileEntity()` function will return the tile
entity corresponding to the block or ``Optional.empty()`` if the block is not a tile entity.

 .. code-block:: java

    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;

    public boolean isTileEntity(Location<World> blockLoc) {
        return blockLoc.getTileEntity().isPresent();
    }

The type of a tile entity can then be obtained by the :javadoc:`TileEntity#getType()` function which returns a
:javadoc:`TileEntityType`. Which can then be compared similar to a :javadoc:`BlockType`. After performing this check
the :javadoc:`TileEntity` variable can safely be cast to the according subtype.

 .. code-block:: java

    import org.spongepowered.api.block.tileentity.Jukebox;
    import org.spongepowered.api.block.tileentity.TileEntity;
    import org.spongepowered.api.block.tileentity.TileEntityTypes;

    public boolean isJukebox(TileEntity entity) {
        return entity.getType().equals(TileEntityTypes.JUKEBOX);
    }

    public void ejectDiscFromJukebox(TileEntity entity) {
        if (isJukebox(entity)) {
            Jukebox jukebox = (Jukebox) entity;
            jukebox.ejectRecord();
        }
    }

After performing this cast, the methods provided by the particular interface can be accessed (in this example the
:javadoc:`Jukebox#ejectRecord()` method). For detailed information about ``TileEntity`` subtypes and their respective
methods refer to the :javadoc:`org.spongepowered.api.block.tileentity` package and its subpackages in the API.


Accessing and Modifying a Tile Entity's Data
============================================

Similar to block states, the data stored in a tile entity is accessed using a :javadoc:`DataManipulator`. Since the
kind of a data is fully described by the ``DataManipulator`` used, all data manipulation can be done with the
``TileEntity`` interface itself and does not require a cast.

The following example contains two methods to alter the data of a sign. The first method reads (if possible) the first
line, the second attempts to set it and returns the boolean value indicating its success.

 .. code-block:: java

    import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
    import org.spongepowered.api.text.Text;

    import java.util.Optional;

    public Optional<Text> getFirstLine(TileEntity entity) {
        Optional<SignData> data = entity.getOrCreate(SignData.class);
        if (data.isPresent()) {
            return Optional.of(data.get().lines().get(0));
        }
        return Optional.empty();
    }

    public boolean setFirstLine(TileEntity entity, Text line) {
        if (entity.supports(SignData.class)) {
            SignData sign = entity.getOrCreate(SignData.class).get();
            sign.set(sign.lines().set(0, line));
            entity.offer(sign);
            return true;
        }
        return false;
    }

The main difference to working with a ``BlockState`` is that a tile entity is a mutable :javadoc:`DataHolder` as
opposed to the immutable ``BlockState``.

Accessing Inventories
=====================

Quite a share of tile entities come with their own inventory, most notably chests and furnaces. That inventory can not
be accessed directly from the ``TileEntity`` interface. So a cast will be necessary. Since all tile entities containing
an inventory extend the :javadoc:`TileEntityCarrier` interface it suffices to cast to that interface as shown below.

 .. code-block:: java

    import org.spongepowered.api.block.tileentity.carrier.TileEntityCarrier;
    import org.spongepowered.api.item.inventory.Inventory;

    public void useInventory(TileEntity entity) {
        if (entity instanceof TileEntityCarrier) {
            TileEntityCarrier carrier = (TileEntityCarrier) entity;
            Inventory inventory = carrier.getInventory();
            ...
        }
    }

Refer to the inventory documentation regarding the manipulation of the inventory.

.. TODO Link to inventory docs
