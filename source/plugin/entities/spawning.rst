==================
Spawning an Entity
==================

.. javadoc-import::
    com.flowpowered.math.vector.Vector3d
    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.EntityType
    org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause
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
    import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
    import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;
    import org.spongepowered.api.world.extent.Extent;

    import java.util.Optional;
    
    public void spawnEntity(Location<World> spawnLocation) {
        Extent extent = spawnLocation.getExtent();
        Optional<Entity> optional = extent
            .createEntity(EntityTypes.CREEPER, spawnLocation.getPosition());
        if (optional.isPresent()) {
            Entity creeper = optional.get();
            extent.spawnEntity(creeper,
                Cause.source(EntitySpawnCause.builder()
                .entity(creeper).type(SpawnTypes.PLUGIN).build()).build());
        }
    }

This will grab the extent from our ``Location``, which we will need for the actual spawning. Next, it uses this extent
to create the entity, but do note that this does not spawn the entity in, it just will create it. We will need to
specify the type of ``Entity`` to spawn, and the co-ordinates from our ``Location``.

The :javadoc:`EntityUniverse#createEntity(EntityType, Vector3d)` method returns an ``Optional`` as the ``Location`` may
not be suitable for spawning an ``Entity``. We then just grab our ``Entity`` from the ``Optional`` and can then use
``Extent`` for spawning the ``Entity`` into the world. We will need to specify a ``Cause`` for the spawning. For
spawning ``Entity``\ s, it is best to use :javadoc:`EntitySpawnCause`. In this example, we stated that our entity was
spawned from a plugin, however we can make it any cause that best describes why we are spawning this in, such as a mob
spawner, or spawn egg.
