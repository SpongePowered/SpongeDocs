===================
Modifying an Entity
===================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

.. javadoc-import::
    org.spongepowered.api.data.DataHolder
    org.spongepowered.api.data.key.Keys
    org.spongepowered.api.data.manipulator.DataManipulator
    org.spongepowered.api.data.manipulator.mutable.DisplayNameData
    org.spongepowered.api.data.manipulator.mutable.entity.ExplosionRadiusData
    org.spongepowered.api.data.manipulator.mutable.entity.FoodData
    org.spongepowered.api.data.manipulator.mutable.entity.HealthData
    org.spongepowered.api.entity.Entity

Sure, spawning a regular-old entity is nice and all, but there has to be something more interesting than that? This is
where :javadoc:`DataManipulator`\ s come into play. An :javadoc:`Entity` is a :javadoc:`DataHolder`, which means that
our ``Entity`` can hold data. More on ``DataHolder``\ s can be found in the :doc:`data documentation <../data/index>`.

``DataManipulator``\ s that apply to ``Entity``\ s are things such as :javadoc:`FoodData` or :javadoc:`HealthData`. A
list of applicable ``DataManipulator``\ s can be found at :javadoc:`org.spongepowered.api.data.manipulator.mutable` and
:javadoc:`org.spongepowered.api.data.manipulator.mutable.entity`. Note that not all ``DataManipulator``\ s found there
may apply to all entities.

Entity Type
~~~~~~~~~~~

Before we can jump behind the wheel with our ``Entity``, we should check what type of ``Entity`` it is, as we may
receive an ``Entity`` we didn't create and thus, do not know its type. Doing this is a simple equality check. Here is
an example of checking if our ``Entity`` is a creeper:

.. code-block:: java

    import org.spongepowered.api.entity.Entity;
    import org.spongepowered.api.entity.EntityTypes;
    
    public boolean isCreeper(Entity entity) {
        return entity.getType().equals(EntityTypes.CREEPER);
    }

Entity Data Manipulators
~~~~~~~~~~~~~~~~~~~~~~~~

Now that we are certain that our ``Entity`` is a creeper, we can apply creeper specific ``DataManipulator``\ s to it.
For example, :javadoc:`ExplosionRadiusData` is a ``DataManipulator`` that creepers can have, but not all ``Entity``\ s.
An example of changing an ``Entity``\ s explosive radius to 50 can be seen below:

.. code-block:: java

    import org.spongepowered.api.data.manipulator.mutable.entity.ExplosiveRadiusData;
    
    public void explosionRadius50(Entity creeper) {
        ExplosionRadiusData radiusData = creeper.get(ExplosionRadiusData.class).get();
        creeper.offer(radiusData.explosionRadius().setTo(50));
    }
    
This will get the ``ExplosiveRadiusData`` of our ``Entity`` for our use. We then use that data to set the explosive
radius of our creeper to 50. We then have to offer the data back to the creeper, as the data we received from our
``Entity`` is only a copy of the live data.

Perhaps we want to give our ``Entity`` a name to customize it a bit! This would be done by using
:javadoc:`DisplayNameData`. An example of this in action can be seen below:

.. code-block:: java

    import net.kyori.adventure.text.Component;
    import net.kyori.adventure.text.format.NamedTextColor;
    import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
    
    public void setDisplayName(Entity creeper) {
        DisplayNameData displayData = creeper.get(DisplayNameData.class).get();
        creeper.offer(displayData.displayName()
            .set(Component.text("Inscrutable", NamedTextColor.DARK_AQUA)));
    }

Another, shorter way to do this is by just using :javadoc:`Keys` on our ``Entity`` instead of using
``DataManipulator``\ s. This would be done like so:

.. code-block:: java

    import org.spongepowered.api.data.key.Keys;
    
    public void explosionRadius50(Entity creeper) {
        creeper.offer(Keys.EXPLOSION_RADIUS, Optional.of(50));
        creeper.offer(Keys.DISPLAY_NAME, Component.text("Inscrutable", NamedTextColor.DARK_AQUA));
    }

This would neaten our code and is easier to perform. See the :doc:`data documentation <../data/customdata>` on
the specific benefits of using either ``DataManipulator``\ s or just ``Keys``.
