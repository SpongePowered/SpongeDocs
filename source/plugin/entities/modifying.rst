===================
Modifying an Entity
===================

Sure, spawning a regular-old entity is nice and all, but there has to be something more interesting than that? This is
where ``DataManipulator``\ s come into play. An ``Entity`` is a ``DataHolder``, which means that our ``Entity`` can
hold data. More on ``DataHolder``\ s can be found in the :doc:`data documentation <../data/index>`.

``DataManipulator``\ s that apply to ``Entity``\ s are things such as ``FoodData`` or ``HealthData``.
A list of applicable ``DataManipulator``\ s can be found at ``org.spongepowered.api.data.manipulator.mutable`` and
``org.spongepowered.api.data.manipulator.mutable.entity``. Note that not all ``DataManipulator``\ s found may apply to
all entities.

Entity Type
~~~~~~~~~~~

Before we can jump behind the wheel with our ``Entity``, we should check what type of ``Entity`` it is, as we may
receive an ``Entity`` we didn't create and thus, do not know it's type. Doing this is a simple equality check. Here is
an example of checking if our ``Entity`` is a creeper:

.. code-block:: java

    import org.spongepowered.api.entity.Entity;
    import org.spongepowered.api.entity.EntityTypes;
    
    public boolean isCreeper(Entity entity) {
        if (entity.getType().equals(EntityTypes.CREEPER)) {
            return true;
        }
        return false;
    }

Entity Data Manipulators
~~~~~~~~~~~~~~~~~~~~~~~~

Now that we are certain that our ``Entity`` is a creeper, we can apply creeper specific ``DataManipulator``\ s to it.
For example, ``ExplosiveRadiusData`` is a ``DataManipulator`` that creepers can have, but not all ``Entity``\ s. An
example of changing an ``Entity``\ s explosive radius to 50 can be seen below:

.. code-block:: java

    import org.spongepowered.api.data.manipulator.mutable.entity.ExplosiveRadiusData;
    
    public void explosionRadius50(Entity creeper) {
        ExplosiveRadiusData radiusData = creeper.get(ExplosiveRadiusData.class).get();
        creeper.offer(radiusData.explosiveRadius().set(50));
    }
    
This will get the ``ExplosiveRadiusData`` of our ``Entity`` for our use. We then use that data to set the explosive
radius of our creeper to 50. We then have to offer the data back to the creeper, as the data we received from our
``Entity`` is only a copy of the live data.

Perhaps we want to give our ``Entity`` a name to customize it a bit! This would be done by using ``DisplayNameData``.
An example of this in action can be seen below:

.. code-block:: java

    import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.format.TextColors;
    
    public void setDisplayName(Entity creeper) {
        DisplayNameData displayData = creeper.get(DisplayNameData.class).get();
        creeper.offer(displayData.displayName().set(Text.of(TextColors.DARK_AQUA,
                                                              "Inscrutable")));
    }

Another, shorter way to do this is by just using ``Keys`` on our ``Entity`` instead of using ``DataManipulator``\ s.
This would be done like so:

.. code-block:: java

    import org.spongepowered.api.data.key.Keys;
    
    public void explosionRadius50(Entity creeper) {
        creeper.offer(Keys.EXPLOSIVE_RADIUS, 50);
        creeper.offer(Keys.DISPLAY_NAME, Text.of(TextColors.DARK_AQUA, "Inscrutable"));
    }

This would neaten our code and is easier to perform. See the :doc:`data documentation <../data/datamanipulators>` on
the specific benefits of using either ``DataManipulator``\ s or just ``Keys``.
