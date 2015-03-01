=====================
Working with Entities
=====================

Entities are a huge part of Minecraft, and SpongeAPI in general. All entities exist within worlds

Spawning an Entity
==================

In almost all cases, to spawn an entity, you need an `EntityType <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/entity/EntityType.html>`__, a `World <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/world/World.html>`__, and a `Location <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/world/Location.html>`__ to do so.

Example: Charged Creeper
~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: java

    import org.spongepowered.api.entity.EntityTypes;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;

    import com.google.common.base.Optional;

    public void spawnEntity(Location location) {
        World world = location.getWorld();
        Optional<Entity> optional = world.createEntity(EntityTypes.CREEPER, location); // We need to create the entity
        if (optional.isPresent()) {
            Creeper creeper = (Creeper) optional.get(); // After this, we can use more API that relates to creeper
            creeper.setPowered(true);
            creeper.setExplosionRadius(10);
            world.spawnEntity(creeper); // Now we're actually spawning in the creeper into the world
        }
    }

The code excerpt illustrated above will spawn in a charged creeper with a higher than normal explosion radius at the given location.
