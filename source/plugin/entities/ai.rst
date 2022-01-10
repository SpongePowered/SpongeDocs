Entity AI
=========

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

.. javadoc-import::

    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.ai.Goal
    org.spongepowered.api.entity.ai.GoalTypes
    org.spongepowered.api.entity.ai.task.AITask
    org.spongepowered.api.entity.ai.task.AITaskType
    org.spongepowered.api.entity.ai.task.AITaskTypes
    org.spongepowered.api.event.entity.ai.AITaskEvent.Add
    org.spongepowered.api.event.entity.ai.AITaskEvent.Remove
    org.spongepowered.api.event.entity.ai.SetAITargetEvent
    org.spongepowered.api.entity.ai.task.builtin.WatchClosestAITask
    org.spongepowered.api.entity.living.Agent
    org.spongepowered.api.entity.living.player.Player

Entity AI (not to be confused with actual AI) is the logic that controls agents. It is structured using goals
(e.g. defeat enemies) that an entity tries to achieve using smaller scaled tasks (e.g. go to enemy and hit him with your
weapon). The following sections will describe what the goals, tasks and other components of the entity AI API are, how
you can change the behavior of entities, and describe how you can write your own AI goals.

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

After the cast we can also check whether the AI is actually enabled. Some plugins disable it to simulate statues, guards
or shops.

Goals
-----

In vanilla Minecraft there are two :javadoc:`GoalTypes {types}` of :javadoc:`Goal`\s.

The first one is the default one: :javadoc:`GoalTypes#NORMAL {NORMAL}`. This part of the AI controls the behavior of the
entity. This goal contains tasks such as attacking a target using a melee or ranged weapon and looking idly around the
area if there are no enemies nearby. If we want to override or modify the AI we will usually change this goal and its
tasks.

The second goal is more for hostile mods: :javadoc:`GoalTypes#TARGET {TARGET}`. This goal contains tasks that help the
entity to identify which target it should select and then attack using the normal goal. The target goal contains tasks
such as select nearest attackable target and select the enemy that attacked you first.

The following code gets the ``NORMAL`` goal from the entity and clears it. 

.. code-block:: java

    import org.spongepowered.api.entity.ai.Goal;
    import org.spongepowered.api.entity.ai.GoalTypes;

    Agent entity = ...;

    Optional<Goal<Agent>> normalGoal = entity.getGoal(GoalTypes.NORMAL);
    if (normalGoal.isPresent()) {
        normalGoal.get().clear();
    }

An entity with an empty goal will not move by itself, however it will complete its current action/movement and might
take an idle pose (i.e. look straight ahead). The AI does not affect any sound effects that an entity might play
randomly.

.. note::

    Please note that editing the goals or tasks of an entity will directly edit the entity. This behavior differs from
    other parts of the API where only copies are returned which must be applied afterwards. Thus, it is not possible to
    transfer the tasks from one entity to another.

.. note::
    
    Any changes made to an entities AI will be lost after a server restart or after the entity was unloaded.

Tasks
-----

The behavior of many entity types is similar to each other. For this reason, the entity AI split into multiple
smaller and reusable parts called :javadoc:`AITask`\s. Each of the ``AITask``\s represents a single behavior trait of an
entity such as :javadoc:`AITaskTypes#WATCH_CLOSEST {WATCH_CLOSEST}`, which makes the entity look at the nearest matching
entity, or :javadoc:`AITaskTypes#AVOID_ENTITY {AVOID_ENTITY}`, which makes the entity flee from certain matching entities.

.. note::

    Vanilla Minecraft itself provides a huge variety of ``AITask``\s, however most of them are not yet accessible via
    the API.

Adding Additional AITasks
~~~~~~~~~~~~~~~~~~~~~~~~~

Adding additional ``AITask``\s to an ``Entity``\s goal is pretty easy. We start with creating with a simple ``AITask``.

.. code-block:: java

    import org.spongepowered.api.entity.ai.task.builtin.WatchClosestAITask;

    Agent entity = ...;
    Goal<Agent> goal = ...;

    WatchClosestAITask watchClosestAiTask = WatchClosestAITask.builder()
            .chance(1)
            .maxDistance(30)
            .watch(Player.class)
            .build(entity);
    goal.addTask(0, watchClosestAiTask);

In this example we create an :javadoc:`WatchClosestAITask` using the associated builder. Using the build we define that
the chance of this goal triggering is 100%. Thus, the entity will look at the closest :javadoc:`Player` in a range of 30
blocks. We assign zero as a priority, which is a high priority, thus this goal takes precedence above almost all other
tasks.

.. note::

    Minecraft uses low values as high priority and high values as low priority. By default Minecraft uses priority
    values from zero to roughly ten.

Removing Certain AITasks
~~~~~~~~~~~~~~~~~~~~~~~~

Removing the correct ``AITask``\s can be a little tricky, especially if modded entities or custom AI come into play.
The Sponge API tries to provides a disambiguator. Calling :javadoc:`AITask#getType()` returns an :javadoc:`AITaskType`
which can be used to differentiate between the existing types of tasks.

First we try the simple version which will only work if there are no AI altering mods present:

.. code-block:: java

    Goal<Zombie> goal = ...;
    
    AITask<Zombie> attackTask = (AITask<Zombie>) goal.getTasks().get(1); // EntityAIZombieAttack
    goal.removeTask(attackTask);

In this case we blindly rely on the fact that in vanilla Minecraft 1.12.2 ``Zombie``\s will have the
``EntityAIZombieAttack`` task as their second task. After that you no longer have to fear attacks from that zombie. 
As you can imagine this strategy has some flaws, as it requires explicit knowledge about the order of AI tasks in the
given entity.

.. note::

    It is not possible to remove ``AITask``\s directly from the list returned by :javadoc:`Goal#getTasks()` because it
    is immutable.

A much simpler but also less powerful way of removing tasks is removing them by their type. This is the approach you
should follow, if you don't need the task's internals to identify which task should be removed.

.. code-block:: java

    goal.removeTasks(AITaskTypes.WANDER);

In this case we remove all AITasks that have the ``AITaskType`` :javadoc:`AITaskTypes#WANDER {WANDER}`.

.. note::

    Currently this way is seriously limited in usability due to the incomplete ``AITaskType`` support in the API.

If you want to remove all AITasks, because you want to configure the entity's AI from scratch, you can also use
:javadoc:`Goal#clear()`.

Implement Your Own AITask
~~~~~~~~~~~~~~~~~~~~~~~~~

We can also try to implement our own ``AITask``\s. The :doc:`custom-ai` page describes the process and some obstacles
you will encounter.

Events
~~~~~~

The AI API as well as most other parts of the SpongeAPI utilize events. You can read more about events
:doc:`here </plugin/event/index>`.

The AI API itself makes use of the following 3 events:

* :javadoc:`AITaskEvent.Add`
* :javadoc:`AITaskEvent.Remove`
* :javadoc:`SetAITargetEvent`

The ``AITaskEvent.Add`` event is published whenever a new ``AITask`` has been added to a ``Goal``, likewise, the
``AITaskEvent.Remove`` event is published if an ``AITask`` has been removed.

The ``SetAITargetEvent`` is published every time that an ``Agent`` selects a new target (usually for attacking) or drops
a target.

All of these events are cancelable, thus allowing us to prevent unwanted third-party changes to our custom entities.
