========
Concepts
========

Blocks in Minecraft are stored in a peculiar way.

Properties
~~~~~~~~~~

All blocks are of a *base type*. Examples of base types include dirt, stairs, and leaves. However, to further
differentiate these base types, each block has set of different properties, of which each can take a limited set of
values (i.e. *podzol* dirt, *brick* stairs, *oak* leaves). A block can have multiple properties (such as *east-facing*,
*brick* stairs).

.. topic:: Examples of block properties

    .. code::

        minecraft:dirt[snowy=false,variant=default]
        minecraft:dirt[snowy=true,variant=default]
        minecraft:dirt[snowy=false,variant=grassless]
        minecraft:dirt[snowy=true,variant=grassless]
        minecraft:planks[variant=oak]
        minecraft:planks[variant=spruce]
        minecraft:planks[variant=birch]
        minecraft:redstone_wire[east=up,north=up,power=0,south=up,west=up]
        minecraft:redstone_wire[east=side,north=up,power=0,south=up,west=up]
        minecraft:redstone_wire[east=none,north=up,power=0,south=up,west=up]
        minecraft:redstone_wire[east=up,north=side,power=0,south=up,west=up]

Some properties, however, are *ephemeral* -- they exist only when the game is running. Their values are not written to
the save file because their values can be detected automatically. For example, with Redstone wire, whether they are
powered or not can be detected based on the environment (is there a lever that is on?). In this case, the ``power``
property of ``minecraft:redstone_wire`` as illustrated above is an ephemeral property.

As of writing, Minecraft still stores block data to an old format with 12 bits for a base type (4096 possible base types)
and 4 bits for "metadata" (16 possible values per base type). However, properties do not map directly to metadata due to
legacy reasons: for example, the furnace block consists of two base types (currently smelting versus not smelting), each
not utilizing their metadata at all. On the other hand, logs do use their metadata fully, but because the combination of
properties exceeds 16 possible values (think tree type and direction), logs must be split over two base types.

In the future, there will only be one 16 bit number (65536 possible combinations of base type + properties). Blocks will
be assigned an ID automatically and this assignment will be stored in the world save file. This is illustrated below::

    0 => minecraft:dirt[snowy=false,variant=default]
    1 => minecraft:dirt[snowy=true,variant=default]
    2 => minecraft:dirt[snowy=false,variant=grassless]
    3 => minecraft:dirt[snowy=true,variant=grassless]
    4 => minecraft:planks[variant=oak]
    5 => minecraft:planks[variant=spruce]
    etc.

Tile Entity Data
~~~~~~~~~~~~~~~~

With 65536 possible combinations, it is not possible to store a lot of information like inventory, so there's an
additional way that *some* blocks have data: tile entities.

Tile entities themselves are Java objects (like a `Chest` class). Normally, Minecraft code would access data in a tile
entity by getting its instance and then calling it methods or fields, like a regular object
(``world.getTileEntity(position).getInventory()``). When tile entities need to be written to the save file, they are
stored in the `NBT format <http://minecraft.gamepedia.com/NBT_format>`_.

.. tip::

    Some things, like paintings, are actually entities.

    However, tile entities can also override rendering so they don't look like a regular block, although this is
    generally inefficient and causes a client framerate drop.
