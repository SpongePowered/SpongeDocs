=============
Bad Practices
=============

.. javadoc-import::
    org.spongepowered.api.block.tileentity.TileEntity
    org.spongepowered.api.command.CommandSource
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.entity.living.player.User
    org.spongepowered.api.service.permission.Subject
    org.spongepowered.api.world.Chunk
    org.spongepowered.api.world.Dimension
    org.spongepowered.api.world.World

These bad practices should be avoided, as they can lead to memory leaks (``OutOfMemoryError``), lag or
inconsistencies.


Storing References
==================

Some instances such as 

* :javadoc:`DataHolder`
* :javadoc:`TileEntity`
* :javadoc:`CommandSource`
* :javadoc:`Entity`
* :javadoc:`Player`
* :javadoc:`Subject`
* :javadoc:`Chunk`
* :javadoc:`Dimension`
* :javadoc:`World`
* :javadoc:`User`
* and containers that **MIGHT** contain any of the above elements, including
    * ``Collections``
    * ``Maps``

should **NEVER** be stored or cached in plugins.

These are the main reasons for this:

* The references prevent proper garbage collection
* The instances might no longer be valid

This can easily be avoided by using the corresponding snapshots or saving the UUID of the given instances and requesting
a live instance when you need it.


IO on the main thread
=====================

Executing some IO operations such as loading a config/data file or checking for updates/connecting to a website takes
much time and greatly affects the TPS on the server. Such tasks should be done either in their own threads, or using the
inbuilt scheduler's async feature. However, it is perfectly fine to load required files/config on the main thread during
server startup/plugin initialization.

.. code-block:: java

    this.game.getScheduler().createAsyncExecutor(this).execute(this::checkForUpdates);

For more details refer to the :doc:`scheduler <../scheduler>` docs.

If this is done incorrectly, you may see clients time out, or the watchdog may even kill the server.


Accessing game objects outside the main thread
==============================================

Accessing game objects outside of the main thread can lead to crashes, inconsistencies and various other problems and
should be avoided. If you have a lengthy operation on a different thread use the :doc:`scheduler <../scheduler>` to make
changes to such game objects on the main thread. If you want to use a game object in a different thread use a snapshot
of the instance or a detached data container.

.. warning::

    If this is done wrong, you can get a ``ConcurrentModificationException`` with or without a server crash at best and
    a corrupted player/world/server at worst.
