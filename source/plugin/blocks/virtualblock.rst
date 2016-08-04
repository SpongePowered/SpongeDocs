=====================
Virtual Block Changes
=====================

.. javadoc-import::
    com.flowpowered.math.vector.Vector3i
    org.spongepowered.api.block.BlockState
    org.spongepowered.api.effect.Viewer

Virtual block changes allow you to make it seem as if a block has changed on the client without actually changing any
physical blocks in the world.

Sending a virtual block change to the client is as simple as calling the
:javadoc:`Viewer#sendBlockChange(int, int, int, BlockState)` method. You will need to specify the co-ordinates of the
block that you wish to change, as well as the new :javadoc:`BlockState`. An example is shown below:

.. code-block:: java

    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.effect.Viewer;

    viewer.sendBlockChange(0, 65, 0, BlockTypes.COMMAND_BLOCK.getDefaultState());

This will make it seem as if the block at the co-ordinates ``0, 65, 0`` has changed to a command block. Of course, you
are not restricted to the default state of a block. Any ``BlockState`` is accepted by the ``sendBlockChange()`` method.

Besides specifying three integers for the co-ordinates, you may also specify a ``Vector3i``. An example of using the
:javadoc:`Viewer#sendBlockChange(Vector3i, BlockState)` method is shown below:

.. code-block:: java

    import com.flowpowered.math.vector.Vector3i;

    Vector3i vector = new Vector3i(0, 65, 0);
    viewer.sendBlockChange(vector, BlockTypes.COMMAND_BLOCK.getDefaultState());

Resetting Changes
~~~~~~~~~~~~~~~~~

To reset any changes you've made to the client at a specific location, you can call the
:javadoc:`Viewer#resetBlockChange(int, int, int)` method. For example, to undo our damage from the previous example, we
can call the ``resetBlockChange()`` method specifying the co-ordinates from before:

.. code-block:: java

    viewer.resetBlockChange(0, 65, 0);

Note that you may also use a ``Vector3i`` in place of the three integers with this method as well.
