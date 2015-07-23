==================
Spawning an Entity
==================

In almost all cases, to spawn an entity, you need an `EntityType <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/entity/EntityType.html>`__, an `Extent <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/world/extent/Extent.html>`__,
and a `Location <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/world/Location.html>`__ to do so.

For example, let's try to spawn a Creeper:

.. code-block:: java

    import org.spongepowered.api.data.manipulator.DisplayNameData;
    import org.spongepowered.api.data.manipulator.entity.ChargedData;
    import org.spongepowered.api.data.manipulator.entity.ExplosiveRadiusData;
    import org.spongepowered.api.entity.Entity;
    import org.spongepowered.api.entity.EntityTypes;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.extent.Extent;

    import com.google.common.base.Optional;

    public void spawnEntity(Location location) {
        Extent extent = location.getExtent();
         // We need to create the entity
        Optional<Entity> optional = extent.createEntity(EntityTypes.CREEPER,
            location.getPosition());
        if (optional.isPresent()) {
             // After this, we can use more API that relates to creeper
            Entity creeper = optional.get();
            
            ExplosiveRadiusData radiusData = creeper.getData(ExplosiveRadiusData.class)
                .get();
            radiusData.setExplosionRadius(10);
             // Return the modified explosive radius back to the creeper
            creeper.offer(radiusData);
            
             // We need to create the data since the creeper does not have a display name
            DisplayNameData displayData = creeper.getOrCreate(DisplayNameData.class).get();
            displayData.setDisplayName(Texts.of("Dinnerbone"));
            creeper.offer(displayData);
            
             // Same applies here
            ChargedData chargedData = creeper.getOrCreate(ChargedData.class).get();
            creeper.offer(chargedData);
            
             // Now we're actually spawning in the creeper into the world
            extent.spawnEntity(creeper);
        }
    }

The code excerpt illustrated above will spawn in an upside-down charged creeper with a higher than normal explosion radius at the given location.
