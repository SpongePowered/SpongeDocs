==============
Block Entities
==============

.. javadoc-import::
    org.spongepowered.api.block.BlockType
    org.spongepowered.api.block.entity.Jukebox
    org.spongepowered.api.block.entity.BlockEntity
    org.spongepowered.api.block.entity.BlockEntityType
    org.spongepowered.api.block.entity.carrier.CarrierBlockEntity
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.world.Location

Block entities are blocks that are capable of additional operations like autonomically crafting (furnaces and brewing
stands) or provide effects (like a beacon or a note block). They also contain additional data like an inventory or
text (like chests, signs or command blocks).

Identifying Block Entities and their Type
=========================================

Again, it all starts with a :javadoc:`Location`. The :javadoc:`Location#blockEntity()` function will return the tile
entity corresponding to the block or ``Optional.empty()`` if the block is not a tile entity.

 .. code-block:: java

    import org.spongepowered.api.world.server.ServerLocation;

    public boolean isTileEntity(ServerLocation blockLoc) {
        return blockLoc.blockEntity().isPresent();
    }

The type of a block entity can then be obtained by the :javadoc:`BlockEntity#type()` function which returns a
:javadoc:`BlockEntityType`. Which can then be compared similar to a :javadoc:`BlockType`. After performing this check
the :javadoc:`BlockEntity` variable can safely be cast to the according subtype.

 .. code-block:: java

    import org.spongepowered.api.block.Jukebox;
    import org.spongepowered.api.block.TileEntity;
    import org.spongepowered.api.block.TileEntityTypes;

    public boolean isJukebox(BlockEntity entity) {
        return entity.getType().equals(BlockEntityTypes.JUKEBOX.get());
    }

    public void ejectDiscFromJukebox(BlockEntity entity) {
        if (isJukebox(entity)) {
            Jukebox jukebox = (Jukebox) entity;
            jukebox.eject();
        }
    }

After performing this cast, the methods provided by the particular interface can be accessed (in this example the
:javadoc:`Jukebox#eject()` method). For detailed information about ``BlockEntity`` subtypes and their respective
methods refer to the :javadoc:`org.spongepowered.api.block.entity` package and its subpackages in the API.


Accessing and Modifying a Block Entity's Data
=============================================

Similar to block states, the data stored in a tile entity is accessed using a :javadoc:`DataManipulator`. Since the
kind of a data is fully described by the ``DataManipulator`` used, all data manipulation can be done with the
``BlockEntity`` interface itself and does not require a cast.

The following example contains two methods to alter the data of a sign. The first method reads (if possible) the first
line, the second attempts to set it and returns the boolean value indicating its success.

 .. code-block:: java

    import net.kyori.adventure.text.Component;
    import org.spongepowered.api.block.entity.Sign;

    import java.util.Optional;
    import java.util.List;

    public Optional<Component> getFirstLine(BlockEntity entity) {
        Optional<List<Component>> lines = entity.get(Keys.SIGN_LINES)
        if (data.isPresent()) {
            return Optional.of(data.get().get(0));
        }
        return Optional.empty();
    }

    public boolean setFirstLine(BlockEntity entity, Component line) {
        if (entity.supports(Keys.SIGN_LINES)) {
            List<Component> lines = Arrays.asList(line);
            entity.offer(Keys.SIGN_LINES, lines);
            return true;
        }
        return false;
    }

The main difference to working with a ``BlockState`` is that a block entity is a mutable :javadoc:`DataHolder` as
opposed to the immutable ``BlockState``.

Accessing Inventories
=====================

Quite a share of block entities come with their own inventory, most notably chests and furnaces. That inventory cannot
be accessed directly from the ``BlockEntity`` interface. So a cast will be necessary. Since all block entities containing
an inventory extend the :javadoc:`CarrierBlockEntity` interface it suffices to cast to that interface as shown below.

 .. code-block:: java

    import org.spongepowered.api.block.entity.carrier.CarrierBlockEntity;
    import org.spongepowered.api.item.inventory.Inventory;

    public void useInventory(TileEntity entity) {
        if (entity instanceof CarrierBlockEntity) {
            CarrierBlockEntity carrier = (CarrierBlockEntity) entity;
            Inventory inventory = carrier.getInventory();
            [...]
        }
    }

Refer to the inventory documentation regarding the manipulation of the inventory.

.. TODO Link to inventory docs
