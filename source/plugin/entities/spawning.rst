==================
Spawning an Entity
==================

.. javadoc-import::
    com.flowpowered.math.vector.Vector3d
    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.EntityType
    org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause
    org.spongepowered.api.event.cause.entity.spawn.MobSpawnerSpawnCause
    org.spongepowered.api.event.cause.entity.spawn.SpawnCause
    org.spongepowered.api.world.Location
    org.spongepowered.api.world.extent.Extent
    org.spongepowered.api.world.extent.EntityUniverse

You will need four things for spawning in an :javadoc:`Entity`, a :javadoc:`Location`, an :javadoc:`Extent`, and an
:javadoc:`EntityType`. The process for getting these is quite simple, you just need to grab a ``Location`` from
somewhere in your plugin code and choose the type of ``Entity`` you wish to spawn.

For example, let's try to spawn a Creeper:

.. code-block:: java

    import org.spongepowered.api.entity.Entity;
    import org.spongepowered.api.entity.EntityTypes;
    import org.spongepowered.api.event.cause.Cause;
    import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
    import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;

    import java.util.Optional;

    public void spawnEntity(Location<World> spawnLocation) {
        World world = spawnLocation.getExtent();
        Entity creeper = world
            .createEntity(EntityTypes.CREEPER, spawnLocation.getPosition());
        SpawnCause spawnCause = SpawnCause.builder().type(SpawnTypes.PLUGIN).build();
        world.spawnEntity(creeper, Cause.source(spawnCause).build());
    }

This will grab the world from our ``Location``, which we will need for the actual spawning. Next, it uses
:javadoc:`EntityUniverse#createEntity(EntityType, Vector3d)` to create the entity, but do note that this does not
spawn the entity into the world, it just will create it. We will need to specify the type of ``Entity`` to spawn, and the
co-ordinates from our ``Location``.

Once we have created our ``Entity`` we can then use the world for spawning the ``Entity``. We will need
to specify a ``Cause`` for the spawning. For spawning ``Entity``\ s, it is best to use a :javadoc:`SpawnCause` as the root
of the cause. In this example, we stated that our entity was spawned from a plugin, however we can make it any cause
that best describes why we are spawning this in, such as a mob spawner (See :javadoc:`MobSpawnerSpawnCause`), or spawn egg
(See :javadoc:`EntitySpawnCause`).
