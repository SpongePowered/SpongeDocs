=====================
Virtual Block Changes
=====================

Virtual block changes allow you to make it seem as if a block has changed on the client without actually changing any
physical blocks in the world.

Sending a virtual block change to the client is as simple as calling the ``sendBlockChange()`` method provided by
``Viewer``. You will need to specify the co-ordinates of the block that you wish to change, as well as the new
``BlockState``. An example is shown below:

.. code-block:: java

    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.effect.Viewer;

    viewer.sendBlockChange(0, 65, 0, BlockTypes.COMMAND_BLOCK.getDefaultState());

This will make it seem as if the block at the co-ordinates ``0, 65, 0`` has changed to a command block. Of course, you
are not restricted to the default state of a block. Any ``BlockState`` is accepted by the ``sendBlockChange()`` method.

To reset any changes you've made to the client at a specific location, you can call the ``resetBlockChange()`` method
also provided by ``Viewer``. For example, to undo our damage from the previous example, we can call the
``resetBlockChange()`` method specifying the co-ordinates from before:

.. code-block:: java

    viewer.resetBlockChange(0, 65, 0);
