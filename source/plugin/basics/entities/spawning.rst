==================
Spawning an Entity
==================

In almost all cases, to spawn an entity, you need an `EntityType <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/entity/EntityType.html>`__, an `Extent <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/world/extent/Extent.html>`__,
and a `Location <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/world/Location.html>`__ to do so.

For example, let's try to spawn a Creeper:

.. code-block:: java

    import org.spongepowered.api.entity.EntityTypes;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.extent.Extent;

    import com.google.common.base.Optional;

    public void spawnEntity(Location location) {
        Extent extent = location.getExtent();
         // We need to create the entity
        Optional<Entity> optional = extent.createEntity(EntityTypes.CREEPER, location.getPosition());
        if (optional.isPresent()) {
            // After this, we can use more API that relates to creeper
            Creeper creeper = (Creeper) optional.get();
            ExplosiveRadiusData radiusData = creeper.getExplosiveRadiusData();
            radiusData.setExplosionRadius(10);
            // Return the modified explosive radius data back to the creeper
            creeper.offer(radiusData);
            // Now we're actually spawning in the creeper into the world
            extent.spawnEntity(creeper);
        }
    }

The code excerpt illustrated above will spawn in a creeper with a higher than normal explosion radius at the given location.
