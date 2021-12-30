==================
Spawning an Entity
==================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

.. javadoc-import::
    com.flowpowered.math.vector.Vector3d
    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.EntityType
    org.spongepowered.api.event.cause.entity.spawn.SpawnType
    org.spongepowered.api.event.cause.entity.spawn.SpawnTypes
    org.spongepowered.api.world.Location
    org.spongepowered.api.world.extent.Extent
    org.spongepowered.api.world.extent.EntityUniverse

You will need three things for spawning an :javadoc:`Entity`: a :javadoc:`Location`, an :javadoc:`Extent`, and an
:javadoc:`EntityType`. The process for getting these is quite simple, you just need to grab a ``Location`` from
somewhere in your plugin code and choose the type of ``Entity`` you wish to spawn.

For example, let's try to spawn a Creeper:

.. code-block:: java

    import org.spongepowered.api.entity.Entity;
    import org.spongepowered.api.entity.EntityTypes;
    import org.spongepowered.api.event.CauseStackManager.StackFrame;
    import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;

    import java.util.Optional;

    public void spawnEntity(Location<World> spawnLocation) {
        World world = spawnLocation.getExtent();

        Entity creeper = world.createEntity(EntityTypes.CREEPER, spawnLocation.getPosition());

        // We need to push a new cause StackFrame to the stack so we can add our own causes
        // In previous versions of the API you had to submit a Cause parameter
        // that would often not contain the real root cause
        // By default the current plugin's PluginContainer is already pushed to the stack.
        try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
            frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLUGIN);
            world.spawnEntity(creeper);
        }
    }

This will grab the world from our ``Location``, which we will need for the actual spawning. Next, it uses
:javadoc:`EntityUniverse#createEntity(EntityType, Vector3d)` to create the entity, but do note that this does not
spawn the entity into the world, it just will create it. We will need to specify the type of ``Entity`` to spawn, and
the co-ordinates from our ``Location``.

Once we have created our ``Entity`` we can then use the world for spawning the ``Entity``. We should specify a
``Cause`` for the spawning so other plugins can handle it accordingly. For spawning ``Entity``\ s, it is best to use a
:javadoc:`SpawnType` as part of the context. In this example, we stated that our entity was spawned from a plugin,
however we can make it any cause/context that best describes why we are spawning this in, such as a mob spawner
(See :javadoc:`SpawnTypes#MOB_SPAWNER`), or spawn egg (See :javadoc:`SpawnTypes#SPAWN_EGG`).
