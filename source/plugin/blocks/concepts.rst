========
Concepts
========

.. javadoc-import::
    org.spongepowered.api.block.entity.carrier.chest.Chest

Properties
~~~~~~~~~~

All blocks are of a *base type*. Examples of base types include redstone dust, brick stairs, and chest. 
However, to further differentiate these base types, each block has set of different properties, 
of which each can take a limited set of values (i.e. *activated* redstone, *corner* brick stairs, *double* chest). 
A block can have multiple properties (such as *corner*,*upsidedown* stairs).

**Examples of block properties**

    .. code::

        minecraft:dirt[snowy=false,variant=default]
        minecraft:dirt[snowy=true,variant=default]
        minecraft:dirt[snowy=false,variant=grassless]
        minecraft:dirt[snowy=true,variant=grassless]
        minecraft:redstone_wire[east=up,north=up,power=0,south=up,west=up]
        minecraft:redstone_wire[east=side,north=up,power=0,south=up,west=up]
        minecraft:redstone_wire[east=none,north=up,power=0,south=up,west=up]
        minecraft:redstone_wire[east=up,north=side,power=0,south=up,west=up]

Some properties, however, are *ephemeral* -- they exist only when the game is running. Their values are not written to
the save file because their values can be detected automatically. For example, with Redstone wire, whether they are
powered or not can be detected based on the environment (is there a lever that is on?). In this case, the ``power``
property of ``minecraft:redstone_wire`` as illustrated above is an ephemeral property.

Each property will have a finite number of valid values that can be assigned to the property. 
Exampeles would be that redstone power can only be a value between 0 and 15. This means that Minecraft knows each 
possible state of a block prior to fully booting the server. This is required knowledge as sending blocks over the 
network are compressed to a number.

Block Entity Data
~~~~~~~~~~~~~~~~

With block properties having a known preset of possible values, these cannot be used when attempting to store data 
whereby there is a infinite amount of possibilities. Examples include sign text and block inventories. 

Minecraft's solution to this is block entities, which are Java objects that store additional data on a `Location`
the block is at in `NBT format <https://minecraft.gamepedia.com/NBT_format>`_. Examples of block entities are 
:javadoc:`Chest`

Example use would be to receive the inventory of a chest. This can be done as follows

.. code-block:: java

    import org.spongepowered.api.world.server.ServerLocation;
    import org.spongepowered.api.block.entity.BlockEntity;
    import org.spongepowered.api.block.entity.carrier.chest.Chest;
    import org.spongepowered.api.item.inventory.type.BlockEntityInventory;

    public Optional<BlockEntityInventory<Chest>> getChestInventory(ServerLocation location){
        Optional<BlockEntity> optionalBlockEntity = location.blockEntity();
        if(optionalBlockEntity.isPresent()){
            BlockEntity blockEntity = optionalBlockEntity.get();
            if(blockEntity instanceof Chest){
                Chest chest = (Chest) blockEntity;
                return Optional.of(chest.inventory());
            }
        }
        return Optional.empty();
    }

.. tip::

    Some things, like paintings, are actually entities.

    However, tile entities can also override rendering so they don't look like a regular block, although this is
    generally inefficient and causes a client framerate drop.

Block Type Tags
~~~~~~~~~~~~~~~

Prior to Minecraft 1.13, blocks had a base id with all variants of that block being set using block properties. 
In Minecraft 1.13 and onwards, this has changed so now all variants have a different base id. This change can be
great as it allows plugin developers to be more specific, however what do you do when all you want to know is if
the block is wool of any kind? 

The answer is to use a tagging system to group similar blocks together under a category. An example of this would
be the tag of `Wool` that can be used to determine if a block is any kind of wool.

An example of Block tags being used can be found in the :doc:`accessing <./accessing#basic-information>`
