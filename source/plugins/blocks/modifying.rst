================
Modifying Blocks
================

Changing a Blocks Type
~~~~~~~~~~~~~~~~~~~~~~
Changing the Type of a Block is as simple as calling the ``replaceWith()`` method of a ``BlockLoc`` with the new ``BlockType``. For example the following code turns the block at the given ``BlockLoc`` into a sponge.

 .. code-block :: java
 
    BlockLoc block = ...; 
    block.replaceWith(BlockTypes.SPONGE);

It's as simple as that.
    
Altering Block States
~~~~~~~~~~~~~~~~~~~~
Similar to the above example, the ``BlockLoc`` class provides a ``replaceWith()`` method accepting a new ``BlockState``. To make use of it, you first must acquire a ``BlockState`` you can modify. You can do so either by getting the block's current state via the ``getState()`` method provided by the ``BlockLoc`` class or by using a Block Type's default State. The latter is demonstrated below: The default state for a Sponge block is retrieved and then modified to directly create a wet sponge block.

 .. code-block :: java
 
    BlockLoc block = ...;
    BlockState state = BlockTypes.SPONGE.getDefaultState();
    BlockProperty<?> wetness = state.getPropertyByName("wet").get();
    state = state.withProperty(wetness, true);
    block.replaceWith(state);

A BlockState can be considered immutable - the methods ``withProperty()`` or ``cycleProperty()`` will always return a new instance instead of changing the one they were called on.

Copying Blocks
~~~~~~~~~~~~~~
If you want to copy all of a blocks data, the ``BlockSnapshot`` class is your best friend. While it doesn't expose all the data, it stores a Blocks type, its BlockState and, if necessary, all additional Tile Entity Data (for example chest inventories). Conventiently, the ``BlockLoc`` class provides a ``getSnapshot()`` method as well as a ``replaceWith()`` method accepting a ``BlockSnapshot``. That makes copying blocks from one location to another very simple:

 .. code-block :: java
 
    function copyBlock(BlockLoc from, BlockLoc to) {
        BlockSnapshot snapshot = from.getSnapshot();
        to.replaceWith(snapshot);
    }
    
 .. warning ::
    
    As of writing, BlockSnapshot is not yet implemented. Attempting to call the ``getSnapshot()`` method will result in an ``OperationNotSupportedException``