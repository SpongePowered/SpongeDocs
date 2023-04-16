================
Accessing Blocks
================

.. javadoc-import::
    org.spongepowered.api.Sponge
    org.spongepowered.api.ResourceKey
    org.spongepowered.api.block.BlockState
    org.spongepowered.api.block.BlockType
    org.spongepowered.api.data.DataManipulator
    org.spongepowered.api.data.Keys
    org.spongepowered.api.world.Location
    org.spongepowered.api.world.World
    org.spongepowered.api.world.server.ServerLocation
    org.spongepowered.api.world.server.ServerWorld
    org.spongepowered.api.state.BooleanStateProperty
    org.spongepowered.api.data.DataManipulator.Immutable

Basic Information
~~~~~~~~~~~~~~~~~

Blocks are most commonly identified and accessed by their :javadoc:`Location`. This location points to a certain
coordinate within an :javadoc:`World`. In most cases a :javadoc:`ServerWorld` will be used as the ``World``.

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.world.server.ServerLocation;
    import org.spongepowered.api.world.server.ServerWorld;
    import org.spongepowered.api.ResourceKey;

    public ServerLocation getBlockAt(ResourceKey worldKey, int posX, int posY, int posZ) {
        ServerWorld world = Sponge.server().worldManager().world(worldKey).get();
        ServerLocation blockLocation = world.location(posX, posY, posZ);
        return blockLoc;
    }

.. warning::

    Note that the above example does not check if the world exists. ``world(worldKey).get()`` will fail if there
    is no world of that name loaded.


With this ``ServerLocation`` object you can then obtain further information about the block. The following code checks if a
referenced block is any kind of banner by checking the blocks type.

.. code-block:: java

    import org.spongepowered.api.block.BlockType;
    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.tag.BlockTypeTag;
    import org.spongepowered.api.world.server.ServerLocation;

    public boolean isBanner(ServerLocation blockLoc) {
        Tag<BlockType> bannerTag = BlockTypeTags.BANNERS.get();
        BlockType blockType = blockLoc.blockType();
        return bannerTag.contains(blockType);
    }

.. tip::
    
    The function ``==`` could be used in place of ``equals()`` when it comes to comparing two :javadoc:`BlockType`'s as there are a fixed number of instance for
    every block, however it is generally recommended to use ``equals()``.

Block Data Manipulators
~~~~~~~~~~~~~~~~~~~~~~~

The data of a block is held as a :javadoc:`DataManipulator`, similar to other parts of the API. This is the container
that holds information on components of our block such as the orientation of a block, the blocks light level, and so on. Checking the values of these manipulators is easy, you just need to check the block's direction
:javadoc:`Key`.

.. code-block:: java

    import org.spongepowered.api.util.Direction;
    import org.spongepowered.api.data.Keys;

    public boolean isFacingNorth(ServerLocation blockLoc) {
        Optional<Direction> optionalBlockDirection = blockLoc.get(Keys.DIRECTION).get();
        if(!optionalBlockDirection.isPresent()){
            return false;
        }
        Direction blockDirection = optionalBlockDirection.get();
        if(blockDirection.equals(Direction.NORTH)){
            return true;
        }
        return false;
    }

First, we need to know which ``DataManipulator`` ``Key`` we need. We can then pass the ``get(Key)`` method of 
``ServerLocation`` which will return an ``Optional``. We then have to check if our ``DataManipulator`` actually
exists for out block by checking ``ifPresent()``. If it exists, then we can use it.

More on ``DataManipulator``\s can be found in the :doc:`data documentation <../data/datamanipulators>`.

.. tip::
    
    If a block will never stop supporting a particular ``DataManipulator``, such as ``DirectionalData`` with stairs,
    then there is no need to check for ``isPresent()``. Just remove the optional around the ``DataManipulator``'s value output and
    fetch the non-optional data by adding ``.get()`` to the end of the statement. Note, that this will cause a
    ``NoSuchElementException`` if a block ever stops supporting a particular ``DataManipulator``.

Block States
~~~~~~~~~~~~

A :javadoc:`BlockState` contains a :javadoc:`BlockType`,  any ``DataManipulator``\ s and properties that are applied to
the block, and any :javadoc:`StateProperty`\ s for a block. It stores all immutable values for a particular block. One
use of this is getting an :javadoc:`DataManipulator.Immutable`, as shown below:

.. code-block:: java

    import org.spongepowered.api.block.BlockState;

    public boolean isWet(ServerLocation blockLoc) {
        BlockState sponge = blockLoc.getBlock();
        if (!sponge.getType().equals(BlockTypes.SPONGE.get())) {
            return false;
        }
        Optional<Boolean> wetness = sponge.get(Keys.IS_WET);
        if (wetness.isPresent()){
            return wetness.get();
        }
        return false;
    }

More information on mutable and immutable ``DataManipulator``\s can be found in the :doc:`data documentation
<../data/datamanipulators>`.

Block State Properties
~~~~~~~~~~~~~~~~~~~~~~

A block state property is a certain value on the current state of a block. A block may or may not contain properties depending
on the type of block. For example, a bed has a :javadoc:`BooleanStateProperty` called
``BED_OCCUPIED``. As a boolean can only have two values, true and false, the ``BED_OCCUPIED`` property can only be true or
false. Checking this value is simple, just call the :javadoc:`BlockState#stateProperty(StateProperty<T>)` method. An example
of this with a bed is shown below:

.. code-block:: java

    import org.spongepowered.api.state.BooleanStateProperties;

    public boolean isBlueBedOccupied(ServerLocation blockLoc) {
        if(blockLoc.state().type().equals(BlockTypes.BLUE_BED.get())) {
            return blockLoc.state().stateProperty(BooleanStateProperties.BLUE_BED_OCCUPIED).get();
        }
        return false;
    }

.. warning::

    If possible, it is recommended to use ``DataManipulator``\s in place of ``StateProperty``\s where possible as they are
    only to be meant as a fallback for modded compatibility.
