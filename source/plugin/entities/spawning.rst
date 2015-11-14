==================
Spawning an Entity
==================

In almost all cases, to spawn an entity, you need an
`EntityType <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/entity/EntityType.html>`__,
an `Extent <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/world/extent/Extent.html>`__,
and a `Location <http://spongepowered.github.io/SpongeAPI/org/spongepowered/api/world/Location.html>`__ to do so.

For example, let's try to spawn a Creeper:

.. code-block:: java

    import org.spongepowered.api.data.key.Keys;
    import org.spongepowered.api.entity.Entity;
    import org.spongepowered.api.entity.EntityTypes;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.extent.Extent;

    import java.util.Optional;

    public void spawnEntity(Location location) {
        Extent extent = location.getExtent();
        // We need to create the entity
        Optional<Entity> optional = extent.createEntity(EntityTypes.CREEPER,
                                                        location.getPosition());
        if (optional.isPresent()) {
            // After this, we can use more API that relates to creeper
            Entity creeper = optional.get();
            // Here, we can use the handy Data API to simplify "setting" various
            // data onto the creeper, such as the explosive radius
            creeper.offer(Keys.EXPLOSIVE_RADIUS, 10);
            // Or display name
            creeper.offer(Keys.DISPLAY_NAME, Texts.of(TextColors.DARK_AQUA, "Inscrutable"));
            // Or even whether the creeper is "charged"
            creeper.offer(Keys.CREEPER_CHARGED, true);

            // Now we're actually spawning in the creeper into the world
            extent.spawnEntity(creeper);
        }
    }

The code excerpt illustrated above will spawn a charged creeper with a higher than normal explosion
radius at the given location.
