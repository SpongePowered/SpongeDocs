Entity AI
=========

.. javadoc-import::

    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.ai.Goal
    org.spongepowered.api.entity.ai.GoalTypes
    org.spongepowered.api.entity.living.Agent

Entity AI (not to be confused with actual AI) are fixed goals (defeat enemies) that an entity tries to achieve using
smaller scaled tasks (go to enemy and hit him with your weapon). The following sections will describe what goals, tasks
and other components of the entity AI are, how you change the behavior of entities and last but not least describe how
you can write your own AI goals.

Agents
------

An :javadoc:`Agent` is a type of living :javadoc:`Entity` that supports AI and thus simulates the typical behavior of
the mobs. You can check whether an ``Entity`` is also an ``Agent`` by simply trying to cast it.

.. code-block:: java

    import org.spongepowered.api.entity.living.Agent;
    
    World world = ...;
    UUID uuid = ...;

    Agent entity = (Agent) world.getEntity(uuid).get();
    
    if (entity.getAgentData().aiEnabled().get()) {
        configureAI(entity);
    }

After the cast we can also check whether the AI is actually enabled. Some plugins disable it to simulate statues.

Goals
-----

In vanilla minecraft there are two types of :javadoc:`Goal`\s.

The first one is the default one: :javadoc:`GoalTypes#NORMAL`. This part of the AI controls the behavior of the entity.
This goal contains tasks such as attacking a target using a melee or ranged weapon and looking idly around the area. If
we want to override or modify the AI we will usually change this goal and it's tasks.

The second goal is more for hostile mods: :javadoc:`GoalTypes#TARGET`. This goal contains tasks that help the entity to
identify which target it should select and then attack using the normal goal. The target goal contains tasks such as
select nearest attackable target and select the enemy that attacked you first.

The following code gets the ``NORMAL`` goal from the entity and clears it. An entity without any tasks will not move by
itself.

.. code-block:: java

    Agent entity = ...;

    Optional<Goal<Agent>> normalGoal = entity.getGoal(GoalTypes.NORMAL);
    if (normalGoal.isPresent()) {
        normalGoal.get().clear();
    }


.. note::

    Please note that editing the goals or tasks of an entity will directly edit the entity. This behavior differs from
    other parts of the API where only copies are returned which must be applied afterwards. Thus it is not possible to
    transfer the tasks from one entity to another.

Tasks
-----

