=======
Effects
=======

.. javadoc-import::
    org.spongepowered.api.data.manipulator.mutable.PotionEffectData
    org.spongepowered.api.effect.Viewer
    org.spongepowered.api.effect.particle.ParticleEffect.Builder
    org.spongepowered.api.effect.particle.ParticleOptions
    org.spongepowered.api.effect.particle.ParticleTypes
    org.spongepowered.api.effect.potion.PotionEffect
    org.spongepowered.api.effect.sound.PitchModulation
    org.spongepowered.api.effect.sound.SoundCategories
    org.spongepowered.api.effect.sound.SoundCategory
    org.spongepowered.api.effect.sound.SoundType
    org.spongepowered.api.world.Location

Using the effect API in Sponge, we can create special effects to be used on a server. Using a
:javadoc:`Viewer`, we can play sounds or spawn particles on the server.

Playing Sounds
==============

With any given ``Viewer``, we can simply play a sound at a location:

.. code-block:: java
    
    import org.spongepowered.api.effect.Viewer;
    import org.spongepowered.api.effect.sound.SoundTypes;
    
    import com.flowpowered.math.vector.Vector3d;
    
    viewer.playSound(SoundTypes.ENTITY_CREEPER_PRIMED, new Vector3d(1, 65, 1), 1);

Now let's break this down. First, the :javadoc:`SoundType` specifies the sound that will be
played. Next we have a ``Vector3d`` position. This position can be constructed, or it can be retrieved from a
``Location`` using the :javadoc:`Location#getPosition()` method. In the example above, the sound will be played at the
coordinates ``1, 65, 1``. Lastly, we have the volume that the sound will be played at. The volume is a double that
ranges from zero to two.

Now that we can play basic sounds, we can go further in-depth with our sounds. Let's say we wanted to play our sound at
a specified pitch. We can use the :javadoc:`PitchModulation` class to modulate the pitch to a specified note. We can
also use a :javadoc:`SoundCategory` to specify what sound category we are playing. An example of these are shown below:

.. code-block:: java
    
    import org.spongepowered.api.effect.sound.PitchModulation;
    import org.spongepowered.api.effect.sound.SoundCategories;
    
    viewer.playSound(SoundTypes.ENTITY_CREEPER_PRIMED, SoundCategories.HOSTILE,
        new Vector3d(1, 65, 1), 1, PitchModulation.AFLAT0);

If a ``SoundCategory`` isn't specified when playing a sound, :javadoc:`SoundCategories#MASTER` will be used.

Spawning Particles
==================

Similarly to sounds, we can use the ``Viewer`` class to spawn particles within the world:

.. code-block:: java
    
    import org.spongepowered.api.effect.particle.ParticleEffect;
    import org.spongepowered.api.effect.particle.ParticleTypes;
    
    ParticleEffect effect = ParticleEffect.builder()
            .type(ParticleTypes.LAVA)
            .quantity(50)
            .build();
    viewer.spawnParticles(effect, position);

Using a :javadoc:`ParticleEffect.Builder`, we can specify the type of particle we
would like to spawn. With this, we also specify that fifty particles will be in the particle effect.

Now if we wanted to make a more specific particle, say the particle of a block, then we can use one of the several
types found in the :javadoc:`ParticleTypes` class. For example, let's say we wanted to spawn particles of a cracking
block of sand, :javadoc:`ParticleTypes#BLOCK_CRACK`. We would need to use the :javadoc:`ParticleOptions#BLOCK_STATE`
option and specify that we would like to use a sand block. This can be done like so:

.. code-block:: java
    
    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.effect.particle.ParticleOptions;
    
    ParticleEffect particle = ParticleEffect.builder()
            .type(ParticleTypes.BLOCK_CRACK)
            .option(ParticleOptions.BLOCK_STATE, BlockTypes.SAND.getDefaultState())
            .build();
    viewer.spawnParticles(particle, position);

Creating Potions
================

Similarly to particles and sounds, we need to use a builder to create our potion effect:

.. code-block:: java
    
    import org.spongepowered.api.effect.potion.PotionEffect;
    import org.spongepowered.api.effect.potion.PotionEffectTypes;
    
    PotionEffect potion = PotionEffect.builder()
            .potionType(PotionEffectTypes.HASTE)
            .duration(10)
            .amplifier(5)
            .build();

Using this, we can create a haste :javadoc:`PotionEffect` that will last for ten ticks and have an amplifier of five.
Unlike particles and sounds, potions cannot be applied to a ``Viewer``. Instead, we need an entity that supports
:javadoc:`PotionEffectData`, such as a player.

.. code-block:: java
    
    import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
    import org.spongepowered.api.entity.living.player.Player;
    
    PotionEffectData effects = player.getOrCreate(PotionEffectData.class).get();
    effects.addElement(potion);
    player.offer(effects);

This will get or create a ``PotionEffectData`` from a player. We then add our previous potion effect to the list and
offer it back to the player.
