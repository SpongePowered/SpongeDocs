===========
Permissions
===========

.. javadoc-import::
    org.spongepowered.api.command.CommandSource
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.service.context.Context
    org.spongepowered.api.service.context.ContextCalculator
    org.spongepowered.api.service.permission.PermissionDescription
    org.spongepowered.api.service.permission.PermissionService
    org.spongepowered.api.service.permission.Subject
    org.spongepowered.api.service.permission.SubjectCollection
    org.spongepowered.api.service.permission.SubjectData

Permission
==========

A permission is a case-insensitive hierarchical string key that is used to determine whether a Subject can do a specific
action or not.
The string is split into separate parts using the dot character.
Permissions should be structured like this.

.. code-block:: text
    
    <PluginID>.<MainGroup>.<Subgroup1>...

Allowed characters are:

* "A" - "Z"
* "a" - "z"
* "0" - "9"
* "_"
* "-"
* "."

Inheritance
~~~~~~~~~~~

If a user has the permission ``myPlugin.commands`` then they automatically have all sub permissions such as
``myPlugin.commands.teleport`` unless the permissions are explicitly removed.

.. note::

    There is no such thing as a ``myPlugin.commands.*`` wildcard permission.
    Use ``myPlugin.commands`` for that.

Structure-Example
~~~~~~~~~~~~~~~~~

The following example show one possible way of structuring permissions, but following this structure is not required at
all.

* ``myPlugin``
    * Grants **full** access to all plugin's permissions.

* ``myPlugin.commands``
    * Grants **full** access to all plugin's commands.

* ``myPlugin.commands.teleport.execute``
    * Only grants the user the permission to execute the command. Without this permission he is not able to execute the
      command even if he has other `teleport` permissions.
      (With this permission alone the user would only be able to teleport himself to others in his current world.)

* ``myPlugin.commands.teleport.all``
    * Only grants the user the permission to teleport all players at once.

* ``myPlugin.commands.teleport.worlds``
    * Only grants the user the permission to teleport to all worlds.

* ``myPlugin.commands.teleport.worlds.mynetherworld``
    * Only grants the user the permission to teleport to *mynetherworld*.

PermissionDescription
=====================

The :javadoc:`PermissionDescription` is an utility that is meant to provide the server owner with details on a certain
permission. ``PermissionDescription``\s are an optional feature a :javadoc:`PermissionService` can provide. The creation
of a PermissionDescription does **not** have any impact on whether a permission exists, who has access or what its
default value is.

The description consists of:

* the target permission id
* a Text description
* one or more assigned roles
* the owning plugin

If you have a dynamic element such as a ``World`` or ``ItemType`` then you can use ``<TemplateParts>``.

Usage-Example
~~~~~~~~~~~~~

.. code-block:: text

    import org.spongepowered.api.service.permission.PermissionDescription;
    import org.spongepowered.api.service.permission.PermissionDescription.Builder;
    import org.spongepowered.api.service.permission.PermissionService;
    import org.spongepowered.api.text.Text;
    import java.util.Optional;

    Optional<Builder> optBuilder = permissionService.newDescriptionBuilder(myPlugin);
    if (optBuilder.isPresent()) {
        Builder builder = optBuilder.get();
        builder.id("myPlugin.commands.teleport.execute")
               .description(Text.of("Allows the user to execute the teleport command."))
               .assign(PermissionDescription.ROLE_STAFF, true)
               .register();
    }

Simple-Result
~~~~~~~~~~~~~

.. code-block:: text
    
    myPlugin.commands.teleport.execute
    
    Description: Allows the user to execute the teleport command.
    Role: user
    Owner: MyPlugin v1.2.3

Template-Result
~~~~~~~~~~~~~~~

.. code-block:: text
    
    myPlugin.commands.teleport.worlds.<World>
    
    Description: Allows the user to teleport to the world <World>.
    Role: staff
    Owner: MyPlugin v1.2.3

.. tip::

    You might skip writing descriptions for some parent permission groups such as ``myPlugin.commands.teleport.worlds``
    or ``myPlugin.commands`` as their meaning can be derived from the permission structure and the defined children
    alone.

Subject
=======

A :javadoc:`Subject` is a holder of assigned permissions. It can be obtained from the ``PermissionService`` via 
:javadoc:`SubjectCollection`\s.
:javadoc:`CommandSource`\s such as :javadoc:`Player`\s are ``Subject``\s by default, but there are many other types of
``Subject``\s. Anything that has permissions is a Subject even if it just delegates the checks to a contained Subject.
Permissions can be granted or denied to a Subject. If a permission is neither granted nor denied its setting will be
inherited. See Inheritance.
Subjects provide methods to check whether they have a certain permission or not.
Plugins that use this method should only query for the specific permission they want to check. It is the
PermissionService's task to respect the permission and subject inheritance.

Example
~~~~~~~

The following example could be used to check whether the Player is allowed to execute the teleport command.

.. code-block:: java

    import org.spongepowered.api.entity.living.player.Player;
    import org.spongepowered.api.world.World;

    public boolean canTeleport(Player subject, World targetWorld) {
        return subject.hasPermission("myPlugin.command.teleport.execute") 
                && (subject.getWorld() == targetWorld
                        || subject.hasPermission("myPlugin.command.teleport." + targetWorld.getName()));
    }

Inheritance
~~~~~~~~~~~

If a ``Subject`` has a permission assigned, it will use that value.
Otherwise it will be inherited from any parent ``Subject``. It does not matter what kind of parent (e.g. group or 
player) ``Subject`` that might be.

If neither the subject itself nor any parent subjects grant or deny a permission then it will be inherited from the
default ``Subject``\s. Each ``SubjectCollection`` defines its own defaults. The global and weakest default subject can be
obtained from the ``PermissionService``. Plugins may define their own permissions to the default's transient
:javadoc:`SubjectData` during every server start-up. This allows server owners to overwrite the defaults defined by
plugins according to their needs using the default's persistent ``SubjectData``. If you would like to provide a
configuration guideline for server owners use ``PermissionDescription``\'s role-templates instead.

.. warning::

    You should think carefully before granting default permissions to users. By granting the permissions you are
    assuming that all server owners will want these defaults (at least the first time the plugin runs) and that
    exceptions will require server owners to explicitly deny the permissions (which can't even be done without a custom
    permissions service implementation). This should roughly correspond to a guest on a single player lan world without
    cheats. For example a chat plugin would allow sending chat messages by default to imitate vanilla game behaviour
    for features that were changed by the plugin.

.. note::

    The default ``Subject``\s' persistent ``SubjectData``\s take precedence over the transient ones.
    For all other ``Subject``\s the transient ``SubjectData``\s take precedence over the persistent ones.

If neither the Subject, nor any of its parents, nor the defaults assign a value to a permission,
then it is automatically denied.

.. note::

    Order of precedence in descending order:
    
    * Subject itself
        * Transient
        * Persistent
    * Parent Subjects
        * Transient
        * Persistent
    * SubjectCollection Defaults
        * Persistent
        * Transient
    * PermissionService Defaults
        * Persistent
        * Transient
    * Deny permission

SubjectCollections
==================

A container for subjects that can be used to obtain a Subject by name.
These are the default Subject Collections:

* User
    * Contains all on-line ``Player``\s and all off-line ``User``\s (at least those with none-default settings).
* Group 
    * Contains all group ``Subject``. Groups are a simple way of structuring a ``Subject``\'s inheritance tree using
      named ``Subject``\s. Groups should be used if a specific subset of ``Subject``\s have additional permission
      settings such as a team, faction or role.
* System
    * Contains other ``Subject``\s used by the server such as the the console and possible remote consoles. 
* Command Block
    * Contains all ``Subject``\s for command blocks. These are useful if you would like to run a ``CommandBlock`` only
      with the permissions of the creator.
* Role Template
    * Contains all role template subjects that are used in ``PermissionDescription``\s. Useful to lookup all recommended
      permissions for a user. These should not be used for inheritance.

.. note::

    When ``SubjectCollection``\s are queried for a ``Subject`` they will automatically be created, if they do not already
    exist. However they might not necessarily show up in ``getAllSubjects()`` unless none-default values are set.

SubjectData
===========

SubjectData are the actual permission stores connected to the Subject.
There are two types of Subject stores:

* Transient = Only lasts for the duration of the session, it is never saved
* Regular (persistent) = Might be saved somewhere, and therefore be persisted and exist forever. Its recommended for
  ``PermissionService``\s to implement a persistent store, however it is not a requirement. It might also depend on the
  subject type. If there is no persistence then the transient store will be returned in both methods.

Plugin authors should consider whether it is necessary to persist a value when choosing between them.

* If it is only for a short time (e.g. during a minigame) then use the transient one.
* If it is for a long time or forever (e.g. a promotion to VIP) use the regular (persistent) one.

Please refer to the Inheritance section if want to know more about the inheritance and precedence of the transient
and persistent ``SubjectData``\s.

Subject Options
===============

Subjects also provide the possibility to store string options. These are basically key value pairs that can be
assigned and inherited. Unlike the permission strings the keys are not hierarchical and don't provide any inheritance
mechanisms themselves, but the key value pairs itself are inherited from parent ``Subject``\s in the same way permissions
are.

Contexts
========

If you consider each permission to a privilege or ability to be able to do something, a :javadoc:`Context` is the
circumstances where that privilege is usable.

You might want to give a ``Subject`` permission to do something, but only when the ``Subject`` is in a certain world,
or in a certain region.

Contexts are accumulated by a ``Subject``, and are then used by the ``PermissionService`` to decide if the ``Subject``
has a privilege or not.

Sponge provides some contexts by default, but it is generally down to other plugins to provide additional contexts to
the PermissionService, through a :javadoc:`ContextCalculator`.

When creating contexts for your own plugin please try to avoid conflicts with other plugins (e.g. by prefixing the
context key with your plugin id) unless these contexts are meant to be shared.

.. note::
    
    Please make sure that your ``ContextCalculator`` responds as fast as possible as it will get called frequently.

Example
~~~~~~~

Your ``ContextCalculator`` may look like this:

.. code-block:: java

    import org.spongepowered.api.command.CommandSource;
    import org.spongepowered.api.service.context.Context;
    import org.spongepowered.api.service.context.ContextCalculator;
    import org.spongepowered.api.service.permission.Subject;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.Set;
    import java.util.UUID;

    public class ExampleCalculator implements ContextCalculator<Subject> {
    
        private static final Context IN_ANY_ARENA = new Context("myArenaPlugin-inAnyArena", "true");
        private static final Context NOT_ANY_ARENA = new Context("myArenaPlugin-inAnyArena", "false");
        private static final String ARENA_KEY = "myArenaPlugin-arena";
    
        private final Map<UUID, String> playerArenas = new HashMap<>();
    
        @Override
        public void accumulateContexts(Subject calculable, Set<Context> accumulator) {
            final Optional<CommandSource> commandSource = calculable.getCommandSource();
    
            if (commandSource.isPresent() && commandSource.get() instanceof Player) {
                final Player player = (Player) commandSource.get();
    
                final UUID uuid = player.getUniqueId();
                if (this.playerArenas.containsKey(uuid)) {
                    accumulator.add(IN_ANY_ARENA);
                    accumulator.add(new Context(ARENA_KEY, this.playerArenas.get(uuid)));
                } else {
                    accumulator.add(NOT_ANY_ARENA);
                }
            }
        }
    
        @Override
        public boolean matches(Context context, Subject subject) {
            if (!context.equals(IN_ANY_ARENA) && !context.equals(NOT_ANY_ARENA) && !context.getKey().equals(ARENA_KEY)) {
                return false;
            }
    
            final Optional<CommandSource> commandSource = subject.getCommandSource();
            if (!commandSource.isPresent() || !(commandSource.get() instanceof Player)) {
                return false;
            }
    
            final Player player = (Player) commandSource.get();
    
            if (context.equals(IN_ANY_ARENA) && !this.playerArenas.containsKey(player.getUniqueId())) {
                return false;
            }
    
            if (context.equals(NOT_ANY_ARENA) && this.playerArenas.containsKey(player.getUniqueId())) {
                return false;
            }
    
            if (context.getKey().equals(ARENA_KEY)) {
                if (!this.playerArenas.containsKey(player.getUniqueId())) {
                    return false;
                }
    
                if (!this.playerArenas.get(player.getUniqueId()).equals(context.getValue())) {
                    return false;
                }
            }
    
            return true;
        }
    }

The ``ContextCalculator`` can be registered via: 

.. code-block:: java
    
    permissionService.registerContextCalculator(contextCalculator);
