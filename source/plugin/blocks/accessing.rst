================
Accessing Blocks
================

.. javadoc-import::
    org.spongepowered.api.block.BlockState
    org.spongepowered.api.block.BlockType
    org.spongepowered.api.block.trait.BlockTrait
    org.spongepowered.api.block.trait.BooleanTrait
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.data.manipulator.ImmutableDataManipulator
    org.spongepowered.api.data.manipulator.mutable.block.DirectionalData
    org.spongepowered.api.data.property.BooleanProperty
    org.spongepowered.api.data.property.DoubleProperty
    org.spongepowered.api.data.property.block.BlastResistanceProperty
    org.spongepowered.api.data.property.block.PoweredProperty
    org.spongepowered.api.world.Location
    org.spongepowered.api.world.World
    org.spongepowered.api.world.extent.Extent

Basic Information
~~~~~~~~~~~~~~~~~

Blocks are most commonly identified and accessed by their :javadoc:`Location`. This location points to a certain
coordinate within an :javadoc:`Extent`. In most cases a :javadoc:`World` will be used as the ``Extent``.

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;

    public Location<World> getBlockAt(String worldName, int posX, int posY, int posZ) {
        World world = Sponge.getServer().getWorld(worldName).get();
        Location<World> blockLoc = new Location<World>(world, posX, posY, posZ);
        return blockLoc;
    }

.. warning::

    Note that the above example does not check if the world exists. ``getWorld(worldName).get()`` will fail if there
    is no world of that name loaded.


With this ``Location`` object you can then obtain further information about the block. The following code checks if a
referenced block is any kind of banner by checking the blocks type.

.. code-block:: java

    import org.spongepowered.api.block.BlockType;
    import org.spongepowered.api.block.BlockTypes;

    public boolean isBanner(Location<World> blockLoc) {
        BlockType type = blockLoc.getBlock().getType();
        return type.equals(BlockTypes.STANDING_BANNER)
                || type.equals(BlockTypes.WALL_BANNER);
    }

.. tip::
    
    The function ``==`` could be used in place of ``equals()`` as there is only one :javadoc:`BlockType` instance for
    every block, however it is generally recommended to use ``equals()``.

Block Data Manipulators
~~~~~~~~~~~~~~~~~~~~~~~

The data of a block is held as a :javadoc:`DataManipulator`, similar to other parts of the API. This is the container
that holds information on components of our block such as the orientation of a block, specific types (stone vs.
granite), and so on. Checking the values of these manipulators is easy, you just need to check the block's direction
:javadoc:`DirectionalData`.

.. code-block:: java

    import org.spongepowered.api.data.key.Keys;
    import org.spongepowered.api.data.manipulator.mutable.block.DirectionalData;

    public boolean isFacingNorth(Location<World> blockLoc) {
        Optional<DirectionalData> optionalData = blockLoc.get(DirectionalData.class);
        if (!optionalData.isPresent()) {
            return false;
        }
        DirectionalData data = optionalData.get();
        if (data.get(Keys.DIRECTION).get().equals(Direction.NORTH)) {
            return true;
        }
        return false;
    }

First, we need to know which ``DataManipulator`` sub-interface we need. Those that are applicable to blocks are found
in the :javadoc:`org.spongepowered.api.data.manipulator.mutable` and
:javadoc:`org.spongepowered.api.data.manipulator.mutable.block` packages. Then, we can just pass that class to the
``get(DataManipulator)`` method of ``Location`` which will return an ``Optional``. We then have to check if our
``DataManipulator`` actually exists for our block by checking ``ifPresent()``. If it exists, then we can use it.

More on ``DataManipulator``\s can be found in the :doc:`data documentation <../data/datamanipulators>`.

.. tip::
    
    If a block will never stop supporting a particular ``DataManipulator``, such as ``DirectionalData`` with stairs,
    then there is no need to check for ``isPresent()``. Just remove the optional around the ``DataManipulator`` and
    fetch the non-optional data by adding ``.get()`` to the end of the statement. Note, that this will cause a
    ``NullPointerException`` if a block ever stops supporting a particular ``DataManipulator``.

Block States
~~~~~~~~~~~~

A :javadoc:`BlockState` contains a :javadoc:`BlockType`,  any ``DataManipulator``\ s and properties that are applied to
the block, and any :javadoc:`BlockTrait`\ s for a block. It stores all immutable value's for a particular block. One
use of this is getting an :javadoc:`ImmutableDataManipulator`, as shown below:

.. code-block:: java

    import org.spongepowered.api.block.BlockState;
    import org.spongepowered.api.data.manipulator.immutable.ImmutableWetData;

    public void isWet(Location blockLoc) {
        BlockState sponge = blockLoc.getBlock();
        if (!sponge.getType().equals(BlockTypes.SPONGE)) {
            return false;
        }
        Optional<ImmutableWetData> wetness = sponge.get(ImmutableWetData.class);
        return wetness.isPresent();
    }

More information on mutable and immutable ``DataManipulator``\s can be found in the :doc:`data documentation
<../data/datamanipulators>`.

Block Properties
~~~~~~~~~~~~~~~~

Blocks can contain certain properties. A property is a pre-set value that defines the game logic of that particular
block. For example, blocks can contain pre-determined blast-resistance values that can be used to determine what
you're working with, without actually checking the type of block it could be one by one. For example, if we wanted to
get the blast resistance of a block and checking if it is greater than or equal to one, it would be done like so:

.. code-block:: java

    import org.spongepowered.api.data.property.DoubleProperty;
    import org.spongepowered.api.data.property.block.BlastResistanceProperty;

    public boolean blastResistanceGreaterThanOne(Location<World> blockLoc) {
        Optional<BlastResistanceProperty> optional =
            blockLoc.getProperty(BlastResistanceProperty.class);
        
        if(optional.isPresent()) {
            BlastResistanceProperty resistance = optional.get();
            DoubleProperty one = DoubleProperty.greaterThanOrEqual(1);
            return one.matches(resistance);
        }
        return false;
    }

This will get the blast resistance of our block and compare it to a new :javadoc:`DoubleProperty`, as
:javadoc:`BlastResistanceProperty` inherits from ``DoubleProperty``. The method will then return if the blast
resistance of our block is greater than one, the value in placed ``matches()``. If we wanted to see if it was less than
two, we would replace it with ``lessThan()``.

If we were comparing two pre-existing properties, it will take the ``Operator`` of our first value, the one we are
creating a double property for. If the ``Operator`` is ``DELEGATE``, which is the none operator, then it will take the
``Operator`` of the second value, the one in ``matches()``. Comparison will return false if both are ``DELEGATE``.
An example of comparing two :javadoc:`PoweredProperty`\ s, a :javadoc:`BooleanProperty`, can be seen below:

.. code-block:: java

    import org.spongepowered.api.data.property.block.PoweredProperty;

    public boolean areBlocksPowered(Location<World> blockLoc, Location<World> blockLoc2) {
        Optional<PoweredProperty> optional = blockLoc.getProperty(PoweredProperty.class);
        Optional<PoweredProperty> optional2 = blockLoc2.getProperty(PoweredProperty.class);
        
        if(optional.isPresent() && optional2.isPresent()) {
            PoweredProperty property1 = optional2.get();
            PoweredProperty property2 = optional2.get();
            BooleanProperty booleanProperty = BooleanProperty.of(property1);
            BooleanProperty booleanProperty2 = BooleanProperty.of(true);
            
            if(booleanProperty2.matches(property1)) {
                return booleanProperty.matches(property2);
            }
        }
        return false;
    }

The second ``if`` check checks if one of the properties is true. If it is true and both are equal, then both
of the values must be true. Therefore, eliminating the need to check the second value. Now we know that both
blocks are being powered.

A list of possible block properties can be found in the :javadoc:`org.spongepowered.api.data.property.block` package.

Block Traits
~~~~~~~~~~~~

A block trait is a certain value on the current state of a block. A block may or may not contain block traits depending
on the type of block. For example, a bed has a :javadoc:`BooleanTrait` called
``BED_OCCUPIED``. As a boolean can only have two values, true and false, the ``BED_OCCUPIED`` trait can only be true or
false. Checking this value is simple, just call the :javadoc:`BlockState#getTraitValue(BlockTrait)` method. An example
of this with a bed is shown below:

.. code-block:: java

    import org.spongepowered.api.block.trait.BooleanTraits;

    public boolean isBedOccupied(Location<World> blockLoc) {
        if(blockLoc.getBlock().getType().equals(BlockTypes.BED)) {
            return blockLoc.getBlock().getTraitValue(BooleanTraits.BED_OCCUPIED).get();
        }
        return false;
    }

.. warning::

    If possible, it is recommended to use ``DataManipulator``\s in place of ``BlockTrait``\s where possible as they are
    only to be meant as a fallback for modded compatibility.
