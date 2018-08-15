Entity AI
=========

.. javadoc-import::

    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.ai.Goal
    org.spongepowered.api.entity.ai.GoalTypes
    org.spongepowered.api.entity.ai.task.AITask
    org.spongepowered.api.entity.ai.task.AITaskType
    org.spongepowered.api.entity.ai.task.AITaskTypes
    org.spongepowered.api.entity.ai.task.builtin.WatchClosestAITask
    org.spongepowered.api.entity.living.Agent
    org.spongepowered.api.entity.living.player.Player

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

After the cast we can also check whether the AI is actually enabled. Some plugins disable it to simulate statues, guards
or shops.

Goals
-----

In vanilla Minecraft there are two :javadoc:`GoalTypes {types}` of :javadoc:`Goal`\s.

The first one is the default one: :javadoc:`GoalTypes#NORMAL {NORMAL}`. This part of the AI controls the behavior of the
entity. This goal contains tasks such as attacking a target using a melee or ranged weapon and looking idly around the
area, if there are no enemies nearby. If we want to override or modify the AI we will usually change this goal and it's
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
take an idle pose (look straight ahead). The AI does not affect any sound effects that an entity might play randomly.

.. note::

    Please note that editing the goals or tasks of an entity will directly edit the entity. This behavior differs from
    other parts of the API where only copies are returned which must be applied afterwards. Thus it is not possible to
    transfer the tasks from one entity to another.

.. note::
    
    Any changes made to an entities AI will be lost after a server restart or after the entity was unloaded.

Tasks
-----

The behavior of many entity types is similar to each other. For this reason was the entity AI split into multiple
smaller and reusable parts called :javadoc:`AITask`\s. Each of the ``AITask``\s represents a single behavior trait of an
entity such as :javadoc:`AITaskTypes#WATCH_CLOSEST {WATCH_CLOSEST}`, which makes the entity look at the nearest matching
entity, or :javadoc:`AITask#AVOID_ENTITY {AVOID_ENTITY}`, which makes the entity flee from certain matching entities.

.. note::

    Vanilla Minecraft itself provides a huge variety of ``AITask``\s, however most of them are not yet accessible via
    the API.

Adding additional AITasks
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
the chance of this goal triggering is 100%. Thus whenever a :javadoc:`Player` gets closer to the entity than 30 blocks
then the entity will look at him. Also we assign zero as a priority, which is a high priority, thus this goal takes
precedence about almost all other tasks.

.. note::

    Minecraft uses low values as high priority and high values as low priority. By default Minecraft uses priority
    values from zero to roughly ten.

Removing certain AITasks
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

If you want to implement your own ``AITask`` then you must extend ``AbstractAITask`` with your task. The following
example shows an example implementation of such a custom AITask.

.. code-block:: java

    import java.util.Comparator;
    import java.util.Optional;
    import java.util.function.Predicate;
    
    import org.spongepowered.api.GameRegistry;
    import org.spongepowered.api.entity.Entity;
    import org.spongepowered.api.entity.ai.task.AITask;
    import org.spongepowered.api.entity.ai.task.AITaskType;
    import org.spongepowered.api.entity.ai.task.AbstractAITask;
    import org.spongepowered.api.entity.living.Agent;
    
    import com.flowpowered.math.vector.Vector3d;
    import com.google.common.base.Preconditions;
    
    import net.minecraft.entity.EntityLiving;
    import net.minecraft.entity.ai.EntityAIBase;
    import net.minecraft.pathfinding.PathNavigate;
    
    public class CreepyCompanionAITask extends AbstractAITask<Agent> {
    
        private static final double APPROACH_DISTANCE_SQUARED = 3 * 3;
        private static final double MAX_DISTANCE_SQUARED = 3 * 3;
        private static final float EXECUTION_CHANCE = 0.02F;
        private static final int MUTEX_FLAG_MOVE = 1;
        
        private static AITaskType TYPE;
        
        public static void register(Object plugin, GameRegistry gameRegistry) {
            TYPE = gameRegistry
                    .registerAITaskType(plugin, "creepy_companion", "CreepyCompanion", CreepyCompanionAITask2.class);
        }
        
        private final Predicate<Entity> entityFilter;
        
        private Optional<Entity> optTarget;
    
        public CreepyCompanionAITask2(final Predicate<Entity> entityFilter) {
            super(TYPE);
            this.entityFilter = Preconditions.checkNotNull(entityFilter);
            ((EntityAIBase) (Object) this).setMutexBits(MUTEX_FLAG_MOVE);
        }
    
        @Override
        public boolean canRunConcurrentWith(final AITask<Agent> other) {
            return (((EntityAIBase) (Object) this).getMutexBits() & ((EntityAIBase) other).getMutexBits()) == 0;
        }
    
        @Override
        public boolean canBeInterrupted() {
            return true;
        }
    
        @Override
        public void start() {
            getNavigator().getPathToEntityLiving(((EntityLiving) (this.optTarget.get())));
        }
    
        private PathNavigate getNavigator() {
            return ((EntityLiving) (getOwner().get())).getNavigator();
        }
    
        @Override
        public boolean shouldUpdate() {
            final Agent owner = getOwner().get();
            if (owner.getRandom().nextFloat() > EXECUTION_CHANCE) {
                return false;
            }
            final Vector3d position = getPositionOf(owner);
    
            this.optTarget = owner.getWorld()
                    .getEntities().stream()
                    .filter(this.entityFilter)
                    .filter(e -> getPositionOf(e).distanceSquared(position) < MAX_DISTANCE_SQUARED)
                    .sorted(Comparator.comparingDouble(e -> getPositionOf(e).distanceSquared(position)))
                    .findFirst();
            return this.optTarget.isPresent();
        }
    
        @Override
        public void update() {
        }
    
        @Override
        public boolean continueUpdating() {
            if (getNavigator().noPath()) {
                return false;
            }
            if (!this.optTarget.isPresent()) {
                return false;
            }
            final Entity target = this.optTarget.get();
            return getPositionOf(target).distanceSquared(getPositionOf(getOwner().get())) > APPROACH_DISTANCE_SQUARED;
        }
    
        private Vector3d getPositionOf(final Entity entity) {
            return entity.getLocation().getPosition();
        }
    
        @Override
        public void reset() {
            getNavigator().clearPath();
            this.optTarget = null;
        }
    
    }
