===========
global.conf
===========

Global Configuration
~~~~~~~~~~~~~~~~~~~~

The global.conf file contains the global configuration settings for Sponge. This file is created in the config/sponge
directory in your server folder. Many of these properties can be also overridden per-world or per-dimension type by
using the config files in the subfolders of config/worlds.

Below is a list of available config settings and their comments inside the global.conf file. Note that certain sections
will not be filled immediately, and may optionally be added to the file when the server encounters them. There's also
full example of an unmodified ``global.conf`` file at the bottom of this page, below the following list:

.. note::

    The following section headings refer to the simple class names of the configuration classes. Each of the entries in
    a section refers to a configurable property in the associated class. The `Type` information refers to the
    section/class that describes the nested configuration structure.

.. _ConfigType_GlobalConfig:

GlobalConfig
============

| The main configuration for Sponge: ``global.conf``
|

* **broken-mods**

  | Stopgap measures for dealing with broken mods
  | **Type:** :ref:`BrokenMod<ConfigType_BrokenMod>`
  |

* **bungeecord**

  | **Type:** :ref:`BungeeCord<ConfigType_BungeeCord>`
  |

* **cause-tracker**

  | **Type:** :ref:`PhaseTracker<ConfigType_PhaseTracker>`
  |

* **commands**

  | **Type:** :ref:`Commands<ConfigType_Commands>`
  |

* **debug**

  | **Type:** :ref:`Debug<ConfigType_Debug>`
  |

* **entity**

  | **Type:** :ref:`Entity<ConfigType_Entity>`
  |

* **entity-activation-range**

  | **Type:** :ref:`EntityActivationRange<ConfigType_EntityActivationRange>`
  |

* **entity-collisions**

  | **Type:** :ref:`EntityCollision<ConfigType_EntityCollision>`
  |

* **exploits**

  | **Type:** :ref:`Exploit<ConfigType_Exploit>`
  |

* **general**

  | **Type:** :ref:`GlobalGeneral<ConfigType_GlobalGeneral>`
  |

* **ip-sets**

  | **Type:** ``Map<String, List<IpSet>>``
  |

* **logging**

  | **Type:** :ref:`Logging<ConfigType_Logging>`
  |

* **metrics**

  | **Type:** :ref:`Metrics<ConfigType_Metrics>`
  |

* **modules**

  | **Type:** :ref:`Module<ConfigType_Module>`
  |

* **movement-checks**

  | **Type:** :ref:`MovementChecks<ConfigType_MovementChecks>`
  |

* **optimizations**

  | **Type:** :ref:`Optimization<ConfigType_Optimization>`
  |

* **permission**

  | **Type:** :ref:`Permission<ConfigType_Permission>`
  |

* **player-block-tracker**

  | **Type:** :ref:`PlayerBlockTracker<ConfigType_PlayerBlockTracker>`
  |

* **spawner**

  | Used to control spawn limits around players. 
  | **Note**: The radius uses the lower value of mob spawn range and server's view distance.
  | **Type:** :ref:`Spawner<ConfigType_Spawner>`
  |

* **sql**

  | Configuration options related to the Sql service, including connection aliases etc
  | **Type:** :ref:`Sql<ConfigType_Sql>`
  |

* **teleport-helper**

  | Blocks to blacklist for safe teleportation.
  | **Type:** :ref:`TeleportHelper<ConfigType_TeleportHelper>`
  |

* **tileentity-activation**

  | **Type:** :ref:`TileEntityActivation<ConfigType_TileEntityActivation>`
  |

* **timings**

  | **Type:** :ref:`Timings<ConfigType_Timings>`
  |

* **world**

  | **Type:** :ref:`GlobalWorld<ConfigType_GlobalWorld>`
  |

* **world-generation-modifiers**

  | World Generation Modifiers to apply to the world
  | **Type:** ``List<String>``
  |

.. _ConfigType_BrokenMod:

broken-mods (BrokenMod)
-----------------------

| Stopgap measures for dealing with broken mods
|

* **broken-network-handler-mods**

  | A list of mod ids that have broken network handlers (they interact with the game from a Netty handler thread).
  | All network handlers from a forcibly scheduled to run on the main thread.
  | Note that this setting should be considered a last resort, and should only be used as a stopgap measure while waiting for a mod to properly fix the issue.
  | **Type:** ``List<String>``
  |

.. _ConfigType_BungeeCord:

bungeecord (BungeeCord)
-----------------------

* **ip-forwarding**

  | If ``true``, allows BungeeCord to forward IP address, UUID, and Game Profile to this server.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_PhaseTracker:

cause-tracker (PhaseTracker)
----------------------------

* **auto-fix-null-source-block-providing-tile-entities**

  | A mapping that is semi-auto-populating for TileEntities whose types
  | are found to be providing ``null`` Block sources as neighbor notifications
  | that end up causing crashes or spam reports. If the value is set to 
  | ``true``, then a ``workaround`` will be attempted. If not, the 
  | current BlockState at the target source will be queried from the world.
  | This map having a specific
  | entry of a TileEntity will prevent a log or warning come up to any logs
  | when that ``null`` arises, and Sponge will self-rectify the TileEntity
  | by calling the method ``getBlockType()``. It is advised that if the mod
  | id in question is coming up, that the mod author is notified about the
  | error-prone usage of the field ``blockType``. You can refer them to
  | the following links for the issue:
  |  https://gist.github.com/gabizou/ad570dc09dfed259cac9d74284e78e8b
  |  https://github.com/SpongePowered/SpongeForge/issues/2787
  | Also, please provide them with these links for the example PR to
  | fix the issue itself, as the fix is very simple:
  | https://github.com/TehNut/Soul-Shards-Respawn/pull/24
  | https://github.com/Epoxide-Software/Enchanting-Plus/pull/135
  | **Type:** ``Map<String, Boolean>``
  |

* **capture-async-spawning-entities**

  | If set to ``true``, when a mod or plugin attempts to spawn an entity 
  | off the main server thread, Sponge will automatically 
  | capture said entity to spawn it properly on the main 
  | server thread. The catch to this is that some mods are 
  | not considering the consequences of spawning an entity 
  | off the server thread, and are unaware of potential race 
  | conditions they may cause. If this is set to false, 
  | Sponge will politely ignore the entity being spawned, 
  | and emit a warning about said spawn anyways.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **generate-stacktrace-per-phase**

  | If ``true``, more thorough debugging for PhaseStates 
  | such that a StackTrace is created every time a PhaseState 
  | switches, allowing for more fine grained troubleshooting 
  | in the cases of runaway phase states. Note that this is 
  | not extremely performant and may have some associated costs 
  | with generating the stack traces constantly.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **max-block-processing-depth**

  | The maximum number of times to recursively process transactions in a single phase.
  | Some mods may interact badly with Sponge's block capturing system, causing Sponge to
  | end up capturing block transactions every time it tries to process an existing batch.
  | Due to the recursive nature of the depth-first processing that Sponge uses to handle block transactions,
  | this can result in a stack overflow, which causes us to lose all infomration about the original cause of the issue.
  | To prevent a stack overflow, Sponge tracks the current processing depth, and aborts processing when it exceeds
  | this threshold.
  | The default value should almost always work properly -  it's unlikely you'll ever have to change it.
  | **Type:** ``int``
  | **Default:** ``1000``
  |

* **maximum-printed-runaway-counts**

  | If verbose is not enabled, this restricts the amount of 
  | runaway phase state printouts, usually happens on a server 
  | where a PhaseState is not completing. Although rare, it should 
  | never happen, but when it does, sometimes it can continuously print 
  | more and more. This attempts to placate that while a fix can be worked on 
  | to resolve the runaway. If verbose is enabled, they will always print.
  | **Type:** ``int``
  | **Default:** ``3``
  |

* **report-null-source-blocks-on-neighbor-notifications**

  | If true, when a mod attempts to perform a neighbor notification
  | on a block, some mods do not know to perform a ``null`` check
  | on the source block of their TileEntity. This usually goes by
  | unnoticed by other mods, because they may perform ``==`` instance
  | equality checks instead of calling methods on the potentially
  | null Block, but Sponge uses the block to build information to
  | help tracking. This has caused issues in the past. Generally,
  | this can be useful for leaving ``true`` so a proper report is
  | generated once for your server, and can be reported to the
  | offending mod author.
  | This is ``false`` by default in SpongeVanilla.
  | Review the following links for more info:
  |  https://gist.github.com/gabizou/ad570dc09dfed259cac9d74284e78e8b
  |  https://github.com/SpongePowered/SpongeForge/issues/2787
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **resync-commands-from-async**

  | If set to ``true``, when a mod or plugin attempts to submit a command
  | asynchronously, Sponge will automatically capture said command
  | and submit it for processing on the server thread. The catch to
  | this is that some mods are performing these commands in vanilla
  | without considering the possible consequences of such commands
  | affecting any thread-unsafe parts of Minecraft, such as worlds,
  | block edits, entity spawns, etc. If this is set to false, Sponge
  | will politely ignore the command being executed, and emit a warning
  | about said command anyways.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **verbose**

  | If ``true``, the phase tracker will print out when there are too many phases 
  | being entered, usually considered as an issue of phase re-entrance and 
  | indicates an unexpected issue of tracking phases not to complete. 
  | If this is not reported yet, please report to Sponge. If it has been 
  | reported, you may disable this.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **verbose-errors**

  | If ``true``, the phase tracker will dump extra information about the current phases 
  | when certain non-PhaseTracker related exceptions occur. This is usually not necessary, as the information 
  | in the exception itself can normally be used to determine the cause of the issue
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_Commands:

commands (Commands)
-------------------

* **aliases**

  | Command aliases will resolve conflicts when multiple plugins request a specific command, 
  | Correct syntax is <unqualified command>=<plugin name> e.g. ``sethome=homeplugin``
  | **Type:** ``Map<String, String>``
  |

* **command-hiding**

  | Defines how Sponge should act when a user tries to access a command they do not have
  | permission for
  | **Type:** :ref:`CommandsHidden<ConfigType_CommandsHidden>`
  |

* **enforce-permission-checks-on-non-sponge-commands**

  | Some mods may not trigger a permission check when running their command. Setting this to
  | true will enforce a check of the Sponge provided permission (``<modid>.command.<commandname>``).
  | Note that setting this to true may cause some commands that are generally accessible to all to
  | require a permission to run.
  | Setting this to true will enable greater control over whether a command will appear in
  | tab completion and Sponge's help command.
  | If you are not using a permissions plugin, it is highly recommended that this is set to false
  | (as it is by default).
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **multi-world-patches**

  | Patches the specified commands to respect the world of the sender instead of applying the 
  | changes on the all worlds.
  | **Type:** ``Map<String, Boolean>``
  |

.. _ConfigType_CommandsHidden:

commands.command-hiding (CommandsHidden)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| Defines how Sponge should act when a user tries to access a command they do not have
| permission for
|

* **hide-on-discovery-attempt**

  | If this is true, when a user tries to tab complete a command, or use ``/sponge which`` or 
  | ``/sponge:help`` this prevents commands a user does not have permission for from being completed.
  | Note that some commands may not show up during tab complete if a user does not have permission
  | regardless of this setting.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **hide-on-execution-attempt**

  | If this is true, when a user tries to use a command they don't have permission for, Sponge
  | will act as if the command doesn't exist, rather than showing a no permissions message.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_Debug:

debug (Debug)
-------------

* **concurrent-chunk-map-checks**

  | Detect and prevent parts of PlayerChunkMap being called off the main thread.
  | This may decrease sever preformance, so you should only enable it when debugging a specific issue.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **concurrent-entity-checks**

  | Detect and prevent certain attempts to use entities concurrently. 
  | **WARNING**: May drastically decrease server performance. Only set this to ``true`` to debug a pre-existing issue.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **thread-contention-monitoring**

  | If ``true``, Java's thread contention monitoring for thread dumps is enabled.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_Entity:

entity (Entity)
---------------

* **collision-warn-size**

  | Number of colliding entities in one spot before logging a warning. Set to ``0`` to disable
  | **Type:** ``int``
  | **Default:** ``200``
  |

* **entity-painting-respawn-delay**

  | Number of ticks before a painting is respawned on clients when their art is changed
  | **Type:** ``int``
  | **Default:** ``2``
  |

* **human-player-list-remove-delay**

  | Number of ticks before the fake player entry of a human is removed from the tab list (range of ``0`` to ``100`` ticks).
  | **Type:** ``int``
  | **Default:** ``10``
  |

* **item-despawn-rate**

  | Controls the time in ticks for when an item despawns.
  | **Type:** ``int``
  | **Default:** ``6000``
  |

* **living-hard-despawn-range**

  | The upper bounded range where living entities farther from a player will likely despawn
  | **Type:** ``int``
  | **Default:** ``128``
  |

* **living-soft-despawn-minimum-life**

  | The amount of seconds before a living entity between the soft and hard despawn ranges from a player to be considered for despawning
  | **Type:** ``int``
  | **Default:** ``30``
  |

* **living-soft-despawn-range**

  | The lower bounded range where living entities near a player may potentially despawn
  | **Type:** ``int``
  | **Default:** ``32``
  |

* **max-bounding-box-size**

  | Maximum size of an entity's bounding box before removing it. Set to ``0`` to disable
  | **Type:** ``int``
  | **Default:** ``1000``
  |

* **max-speed**

  | Square of the maximum speed of an entity before removing it. Set to ``0`` to disable
  | **Type:** ``int``
  | **Default:** ``100``
  |

.. _ConfigType_EntityActivationRange:

entity-activation-range (EntityActivationRange)
-----------------------------------------------

* **auto-populate**

  | If ``true``, newly discovered entities will be added to this config with a default value.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **defaults**

  | Default activation ranges used for all entities unless overridden.
  | **Type:** ``Map<String, Integer>``
  |

* **mods**

  | Per-mod overrides. Refer to the minecraft default mod for example.
  | **Type:** :ref:`Map\<String, EntityActivationMod><ConfigType_EntityActivationMod>`
  |

.. _ConfigType_EntityActivationMod:

entity-activation-range.mods (EntityActivationMod)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| Per-mod overrides. Refer to the minecraft default mod for example.
|

* **defaults**

  | **Type:** ``Map<String, Integer>``
  |

* **enabled**

  | If ``false``, entity activation rules for this mod will be ignored and always tick.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **entities**

  | **Type:** ``Map<String, Integer>``
  |

.. _ConfigType_EntityCollision:

entity-collisions (EntityCollision)
-----------------------------------

* **auto-populate**

  | If ``true``, newly discovered entities/blocks will be added to this config with a default value.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **max-entities-within-aabb**

  | Maximum amount of entities any given entity or block can collide with. This improves 
  | performance when there are more than ``8`` entities on top of each other such as a 1x1 
  | spawn pen. Set to ``0`` to disable.
  | **Type:** ``int``
  | **Default:** ``8``
  |

* **mods**

  | Per-mod overrides. Refer to the minecraft default mod for example.
  | **Type:** :ref:`Map\<String, CollisionMod><ConfigType_CollisionMod>`
  |

.. _ConfigType_CollisionMod:

entity-collisions.mods (CollisionMod)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| Per-mod overrides. Refer to the minecraft default mod for example.
|

* **blocks**

  | **Type:** ``Map<String, Integer>``
  |

* **defaults**

  | Default maximum collisions used for all entities/blocks unless overridden.
  | **Type:** ``Map<String, Integer>``
  |

* **enabled**

  | If ``false``, entity collision rules for this mod will be ignored.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **entities**

  | **Type:** ``Map<String, Integer>``
  |

.. _ConfigType_Exploit:

exploits (Exploit)
------------------

* **book-size-total-multiplier**

  | If limit-book-size is enabled, controls the multiplier applied to each book page size
  | **Type:** ``double``
  | **Default:** ``0.98``
  |

* **filter-invalid-entities-on-chunk-save**

  | Enables filtering invalid entities when a chunk is being saved
  | such that the entity that does not ``belong`` in the saving
  | chunk will not be saved, and forced an update to the world's
  | tracked entity lists for chunks.
  | See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0311-Prevent-Saving-Bad-entities-to-chunks.patch
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **limit-book-size**

  | Limits the size of a book that can be sent by the client.
  | See https://github.com/PaperMC/Paper/blob/f8058a8187da9f6185d95bb786783e12c79c8b18/Spigot-Server-Patches/0403-Book-Size-Limits.patch
  | (Only affects SpongeVanilla)
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **load-chunk-on-position-set**

  | Enables focing a chunk load when an entity position
  | is set. Usually due to teleportation, vehicle movement
  | etc. can a position lead an entity to no longer exist
  | within it's currently marked and tracked chunk. This will
  | enable that chunk for the position is loaded. Part of several
  | exploits.See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0335-Ensure-chunks-are-always-loaded-on-hard-position-set.patch
  | (Only affects SpongeVanilla)
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **mark-chunks-as-dirty-on-entity-list-modification**

  | Enables forcing chunks to save when an entity is added
  | or removed from said chunk. This is a partial fix for
  | some exploits using vehicles.See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0306-Mark-chunk-dirty-anytime-entities-change-to-guarante.patch
  | (Only affects SpongeVanilla)
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **max-book-page-size**

  | If limit-book-size is enabled, controls the maximum size of a book page
  | **Type:** ``int``
  | **Default:** ``2560``
  |

* **prevent-creative-itemstack-name-exploit**

  | Prevents an exploit in which the client sends a packet with the 
  | itemstack name exceeding the string limit.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **sync-player-positions-for-vehicle-movement**

  | Enables forcing updates to the player's location on vehicle movement.
  | This is partially required to update the server's understanding of
  | where the player exists, and allows chunk loading issues to be avoided
  | with laggy connections and/or hack clients.See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0378-Sync-Player-Position-to-Vehicles.patch
  | (Only affects SpongeVanilla)
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **update-tracked-chunk-on-entity-move**

  | Enables forcing a chunk-tracking refresh on entity movement.
  | This enables a guarantee that the entity is tracked in the 
  | proper chunk when moving.https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0315-Always-process-chunk-registration-after-moving.patch
  | (Only affects SpongeVanilla)
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

.. _ConfigType_GlobalGeneral:

general (GlobalGeneral)
-----------------------

* **config-dir**

  | The directory for Sponge plugin configurations, relative to the  
  | execution root or specified as an absolute path. 
  | Note that the default: ``${CANONICAL_GAME_DIR}/config`` 
  | is going to use the ``config`` directory in the root game directory. 
  | If you wish for plugin configs to reside within a child of the configuration 
  | directory, change the value to, for example, ``${CANONICAL_CONFIG_DIR}/sponge/plugins``. 
  | **Note**: It is not recommended to set this to ``${CANONICAL_CONFIG_DIR}/sponge``, as there is 
  | a possibility that plugin configurations can conflict the Sponge core configurations.
  | **Type:** ``String``
  | **Default:** ``${CANONICAL_GAME_DIR}/config``
  |

* **file-io-thread-sleep**

  | If ``true``, sleeping between chunk saves will be enabled, beware of memory issues.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **plugins-dir**

  | Additional directory to search for plugins, relative to the 
  | execution root or specified as an absolute path. 
  | Note that the default: ``${CANONICAL_MODS_DIR}/plugins`` 
  | is going to search for a plugins folder in the mods directory. 
  | If you wish for the plugins folder to reside in the root game 
  | directory, change the value to ``${CANONICAL_GAME_DIR}/plugins``.
  | **Type:** ``String``
  | **Default:** ``${CANONICAL_MODS_DIR}/plugins``
  |

.. _ConfigType_Logging:

logging (Logging)
-----------------

* **block-break**

  | Log when blocks are broken
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **block-modify**

  | Log when blocks are modified
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **block-place**

  | Log when blocks are placed
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **block-populate**

  | Log when blocks are populated in a chunk
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **block-tracking**

  | Log when blocks are placed by players and tracked
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **chunk-gc-queue-unload**

  | Log when chunks are queued to be unloaded by the chunk garbage collector.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **chunk-load**

  | Log when chunks are loaded
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **chunk-unload**

  | Log when chunks are unloaded
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **entity-collision-checks**

  | Whether to log entity collision/count checks
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **entity-death**

  | Log when living entities are destroyed
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **entity-despawn**

  | Log when living entities are despawned
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **entity-spawn**

  | Log when living entities are spawned
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **entity-speed-removal**

  | Whether to log entity removals due to speed
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **exploit-itemstack-name-overflow**

  | Log when server receives exploited packet with itemstack name exceeding string limit.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **exploit-respawn-invisibility**

  | Log when player attempts to respawn invisible to surrounding players.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **exploit-sign-command-updates**

  | Log when server receives exploited packet to update a sign containing commands from player with no permission.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **log-stacktraces**

  | Add stack traces to dev logging
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **world-auto-save**

  | Log when a world auto-saves its chunk data. 
  | **Note**: This may be spammy depending on the auto-save-interval configured for world.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_Metrics:

metrics (Metrics)
-----------------

* **global-state**

  | The global collection state that should be respected by all plugins that have no specified collection state. If undefined then it is treated as disabled.
  | **Type:** ``Tristate``
  | **Possible values:** 
  | - ``TRUE``
  | - ``FALSE``
  | - ``UNDEFINED``
  | **Default:** ``UNDEFINED``
  |

* **plugin-states**

  | Plugin-specific collection states that override the global collection state.
  | **Type:** ``Map<String, Tristate>``
  | **Possible values:** 
  | - ``TRUE``
  | - ``FALSE``
  | - ``UNDEFINED``
  |

.. _ConfigType_Module:

modules (Module)
----------------

* **broken-mod**

  | Enables experimental fixes for broken mods
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **bungeecord**

  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **entity-activation-range**

  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **entity-collisions**

  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **exploits**

  | Controls whether any exploit patches are applied.
  | If there are issues with any specific exploits, please
  | test in the exploit category first, before disabling all
  | exploits with this toggle.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **movement-checks**

  | Allows configuring Vanilla movement and speed checks
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **optimizations**

  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **realtime**

  | Use real (wall) time instead of ticks as much as possible
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **tileentity-activation**

  | Controls block range and tick rate of tileentities. 
  | Use with caution as this can break intended functionality.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **timings**

  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **tracking**

  | **Type:** ``boolean``
  | **Default:** ``true``
  |

.. _ConfigType_MovementChecks:

movement-checks (MovementChecks)
--------------------------------

* **moved-wrongly**

  | Controls whether the ``player/entity moved wrongly!`` check will be enforced
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **player-moved-too-quickly**

  | Controls whether the ``player moved too quickly!`` check will be enforced
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **player-vehicle-moved-too-quickly**

  | Controls whether the ``vehicle of player moved too quickly!`` check will be enforced
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

.. _ConfigType_Optimization:

optimizations (Optimization)
----------------------------

* **async-lighting**

  | Runs lighting updates asynchronously.
  | **Type:** :ref:`AsyncLighting<ConfigType_AsyncLighting>`
  |

* **cache-tameable-owners**

  | Caches tameable entities owners to avoid constant lookups against data watchers. If mods 
  | cause issues, disable this.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **disable-failing-deserialization-log-spam**

  | Occasionally, some built in advancements, 
  | recipes, etc. can fail to deserialize properly
  | which ends up potentially spamming the server log
  | and the original provider of the failing content
  | is not able to fix. This provides an option to
  | suppress the exceptions printing out in the log.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **drops-pre-merge**

  | If ``true``, block item drops are pre-processed to avoid 
  | having to spawn extra entities that will be merged post spawning. 
  | Usually, Sponge is smart enough to determine when to attempt an item pre-merge 
  | and when not to, however, in certain cases, some mods rely on items not being 
  | pre-merged and actually spawned, in which case, the items will flow right through 
  | without being merged.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **eigen-redstone**

  | Uses theosib's redstone algorithms to completely overhaul the way redstone works.
  | **Type:** :ref:`EigenRedstone<ConfigType_EigenRedstone>`
  |

* **enchantment-helper-leak-fix**

  | If ``true``, provides a fix for possible leaks through
  | Minecraft's enchantment helper code that can leak
  | entity and world references without much interaction
  | Forge native (so when running SpongeForge implementation)
  | has a similar patch, but Sponge's patch works a little harder
  | at it, but Vanilla (SpongeVanilla implementation) does NOT
  | have any of the patch, leading to the recommendation that this
  | patch is enabled ``for sure`` when using SpongeVanilla implementation.
  | See https://bugs.mojang.com/browse/MC-128547 for more information.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **faster-thread-checks**

  | If ``true``, allows for Sponge to make better assumptinos on single threaded
  | operations with relation to various checks for server threaded operations.
  | This is default to true due to Sponge being able to precisely inject when
  | the server thread is available. This should make an already fast operation
  | much faster for better thread checks to ensure stability of sponge's systems.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **map-optimization**

  | If ``true``, re-writes the incredibly inefficient Vanilla Map code.
  | This yields enormous performance enhancements when using many maps, but has a tiny chance of breaking mods that invasively modify Vanilla.It is strongly reccomended to keep this on, unless explicitly advised otherwise by a Sponge developer
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **optimize-hoppers**

  | Based on Aikar's optimizationo of Hoppers, setting this to ``true``
  | will allow for hoppers to save performing server -> client updates
  | when transferring items. Because hoppers can transfer items multiple
  | times per tick, these updates can get costly on the server, with
  | little to no benefit to the client. Because of the nature of the
  | change, the default will be ``false`` due to the inability to pre-emptively
  | foretell whether mod compatibility will fail with these changes or not.
  | Refer to: https://github.com/PaperMC/Paper/blob/8175ec916f31dcd130fe0884fe46bdc187d829aa/Spigot-Server-Patches/0269-Optimize-Hoppers.patch
  | for more details.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **panda-redstone**

  | If ``true``, uses Panda4494's redstone implementation which improves performance. 
  | See https://bugs.mojang.com/browse/MC-11193 for more information. 
  | **Note**: This optimization has a few issues which are explained in the bug report. 
  | We strongly recommend using eigen redstone over this implementation as this will
  | be removed in a future release.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **structure-saving**

  | Handles structures that are saved to disk. Certain structures can take up large amounts 
  | of disk space for very large maps and the data for these structures is only needed while the 
  | world around them is generating. Disabling saving of these structures can save disk space and 
  | time during saves if your world is already fully generated. 
  | **Warning**: disabling structure saving will break the vanilla locate command.
  | **Type:** :ref:`StructureSave<ConfigType_StructureSave>`
  |

* **use-active-chunks-for-collisions**

  | Vanilla performs a lot of is area loaded checks during
  | entity collision calculations with blocks, and because
  | these calculations require fetching the chunks to see
  | if they are loaded, before getting the block states
  | from those chunks, there can be some small performance
  | increase by checking the entity's owned active chunk
  | it may currently reside in. Essentially, instead of
  | asking the world if those chunks are loaded, the entity
  | would know whether it's chunks are loaded and that neighbor's
  | chunks are loaded.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_AsyncLighting:

optimizations.async-lighting (AsyncLighting)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| Runs lighting updates asynchronously.
|

* **enabled**

  | If ``true``, lighting updates are run asynchronously.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **num-threads**

  | The amount of threads to dedicate for asynchronous lighting updates.
  | **Type:** ``int``
  | **Default:** ``2``
  |

.. _ConfigType_EigenRedstone:

optimizations.eigen-redstone (EigenRedstone)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| Uses theosib's redstone algorithms to completely overhaul the way redstone works.
|

* **enabled**

  | If ``true``, uses theosib's redstone implementation which improves performance. 
  | See https://bugs.mojang.com/browse/MC-11193 and 
  |      https://bugs.mojang.com/browse/MC-81098 for more information. 
  | **Note**: We cannot guarantee compatibility with mods. Use at your discretion.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **vanilla-decrement**

  | If ``true``, restores the vanilla algorithm for computing wire power levels when powering off.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **vanilla-search**

  | If ``true``, restores the vanilla algorithm for propagating redstone wire changes.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_StructureSave:

optimizations.structure-saving (StructureSave)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| Handles structures that are saved to disk. Certain structures can take up large amounts 
| of disk space for very large maps and the data for these structures is only needed while the 
| world around them is generating. Disabling saving of these structures can save disk space and 
| time during saves if your world is already fully generated. 
| **Warning**: disabling structure saving will break the vanilla locate command.
|

* **auto-populate**

  | If ``true``, newly discovered structures will be added to this config
  | with a default value of ``true``. This is useful for finding out
  | potentially what structures are being saved from various mods, and
  | allowing those structures to be selectively disabled.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **enabled**

  | If ``false``, disables the modification to prevent certain structures
  | from saving to the world's data folder. If you wish to prevent certain
  | structures from saving, leave this ``enabled=true``. When ``true``, the
  | modification allows for specific ``named`` structures to NOT be saved to
  | disk. Examples of some structures that are costly and somewhat irrelivent
  | is ``mineshaft``\s, as they build several structures and save, even after
  | finished generating.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **mods**

  | Per-mod overrides. Refer to the minecraft default mod for example.
  | **Type:** :ref:`Map\<String, StructureMod><ConfigType_StructureMod>`
  |

.. _ConfigType_StructureMod:

optimizations.structure-saving.mods (StructureMod)
""""""""""""""""""""""""""""""""""""""""""""""""""

| Per-mod overrides. Refer to the minecraft default mod for example.
|

* **enabled**

  | If ``false``, this mod will never save its structures. This may
  | break some mod functionalities when requesting to locate their
  | structures in a World. If true, allows structures not overridden
  | in the section below to be saved by default. If you wish to find
  | a structure to prevent it being saved, enable ``auto-populate`` and
  | restart the server/world instance.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **structures**

  | Per structure override. Having the value of ``false`` will prevent
  | that specific named structure from saving.
  | **Type:** ``Map<String, Boolean>``
  |

.. _ConfigType_Permission:

permission (Permission)
-----------------------

* **forge-permissions-handler**

  | If ``true``, Sponge plugins will be used to handle permissions rather than any Forge mod
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_PlayerBlockTracker:

player-block-tracker (PlayerBlockTracker)
-----------------------------------------

* **block-blacklist**

  | Block IDs that will be blacklisted for player block placement tracking.
  | **Type:** ``List<String>``
  |

* **enabled**

  | If ``true``, adds player tracking support for block positions. 
  | **Note**: This should only be disabled if you do not care who caused a block to change.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

.. _ConfigType_Spawner:

spawner (Spawner)
-----------------

| Used to control spawn limits around players. 
| **Note**: The radius uses the lower value of mob spawn range and server's view distance.
|

* **spawn-limit-ambient**

  | The number of ambients the spawner can potentially spawn around a player.
  | **Type:** ``int``
  | **Default:** ``15``
  |

* **spawn-limit-animal**

  | The number of animals the spawner can potentially spawn around a player.
  | **Type:** ``int``
  | **Default:** ``15``
  |

* **spawn-limit-aquatic**

  | The number of aquatics the spawner can potentially spawn around a player.
  | **Type:** ``int``
  | **Default:** ``5``
  |

* **spawn-limit-monster**

  | The number of monsters the spawner can potentially spawn around a player.
  | **Type:** ``int``
  | **Default:** ``70``
  |

* **tick-rate-ambient**

  | The ambient spawning tick rate. Default: ``400``
  | **Type:** ``int``
  | **Default:** ``400``
  |

* **tick-rate-animal**

  | The animal spawning tick rate. Default: ``400``
  | **Type:** ``int``
  | **Default:** ``400``
  |

* **tick-rate-aquatic**

  | The aquatic spawning tick rate. Default: ``1``
  | **Type:** ``int``
  | **Default:** ``1``
  |

* **tick-rate-monster**

  | The monster spawning tick rate. Default: ``1``
  | **Type:** ``int``
  | **Default:** ``1``
  |

.. _ConfigType_Sql:

sql (Sql)
---------

| Configuration options related to the Sql service, including connection aliases etc
|

* **aliases**

  | Aliases for SQL connections, in the format jdbc:protocol://[username[:password]@]host/database
  | **Type:** ``Map<String, String>``
  |

.. _ConfigType_TeleportHelper:

teleport-helper (TeleportHelper)
--------------------------------

| Blocks to blacklist for safe teleportation.
|

* **force-blacklist**

  | If ``true``, this blacklist will always be respected, otherwise, plugins can choose whether 
  | or not to respect it.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **unsafe-body-block-ids**

  | Block IDs that are listed here will not be selected by Sponge's safe teleport routine as 
  | a safe block for players to warp into. 
  | You should only list blocks here that are incorrectly selected, solid blocks that prevent 
  | movement are automatically excluded.
  | **Type:** ``List<String>``
  |

* **unsafe-floor-block-ids**

  | Block IDs that are listed here will not be selected by Sponge's safe 
  | teleport routine as a safe floor block.
  | **Type:** ``List<String>``
  |

.. _ConfigType_TileEntityActivation:

tileentity-activation (TileEntityActivation)
--------------------------------------------

* **auto-populate**

  | If ``true``, newly discovered tileentities will be added to this config with default settings.
  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **default-block-range**

  | Default activation block range used for all tileentities unless overridden.
  | **Type:** ``int``
  | **Default:** ``64``
  |

* **default-tick-rate**

  | Default tick rate used for all tileentities unless overridden.
  | **Type:** ``int``
  | **Default:** ``1``
  |

* **mods**

  | Per-mod overrides. Refer to the minecraft default mod for example.
  | **Type:** :ref:`Map\<String, TileEntityActivationMod><ConfigType_TileEntityActivationMod>`
  |

.. _ConfigType_TileEntityActivationMod:

tileentity-activation.mods (TileEntityActivationMod)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

| Per-mod overrides. Refer to the minecraft default mod for example.
|

* **block-range**

  | **Type:** ``Map<String, Integer>``
  |

* **default-block-range**

  | **Type:** ``Integer``
  |

* **default-tick-rate**

  | **Type:** ``Integer``
  |

* **enabled**

  | If ``false``, tileentity activation rules for this mod will be ignored and always tick.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **tick-rate**

  | **Type:** ``Map<String, Integer>``
  |

.. _ConfigType_Timings:

timings (Timings)
-----------------

* **enabled**

  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **hidden-config-entries**

  | **Type:** ``List<String>``
  |

* **history-interval**

  | **Type:** ``int``
  | **Default:** ``300``
  |

* **history-length**

  | **Type:** ``int``
  | **Default:** ``3600``
  |

* **server-name-privacy**

  | **Type:** ``boolean``
  | **Default:** ``false``
  |

* **verbose**

  | **Type:** ``boolean``
  | **Default:** ``false``
  |

.. _ConfigType_GlobalWorld:

world (GlobalWorld)
-------------------

* **auto-player-save-interval**

  | The auto-save tick interval used when saving global player data. 
  | **Note**: ``20`` ticks is equivalent to ``1`` second. Set to ``0`` to disable.
  | **Type:** ``int``
  | **Default:** ``900``
  |

* **auto-save-interval**

  | The auto-save tick interval used to save all loaded chunks in a world. 
  | Set to ``0`` to disable. 
  | **Note**: ``20`` ticks is equivalent to ``1`` second.
  | **Type:** ``int``
  | **Default:** ``900``
  |

* **chunk-gc-load-threshold**

  | The number of newly loaded chunks before triggering a forced cleanup. 
  | **Note**: When triggered, the loaded chunk threshold will reset and start incrementing. 
  | Disabled by default.
  | **Type:** ``int``
  | **Default:** ``0``
  |

* **chunk-gc-tick-interval**

  | The tick interval used to cleanup all inactive chunks that have leaked in a world. 
  | Set to ``0`` to disable which restores vanilla handling.
  | **Type:** ``int``
  | **Default:** ``600``
  |

* **chunk-unload-delay**

  | The number of seconds to delay a chunk unload once marked inactive. 
  | **Note**: This gets reset if the chunk becomes active again.
  | **Type:** ``int``
  | **Default:** ``15``
  |

* **deny-chunk-requests**

  | If ``true``, any request for a chunk not currently loaded will be denied (exceptions apply 
  | for things like world gen and player movement). 
  | **Warning**: As this is an experimental setting for performance gain, if you encounter any issues 
  | then we recommend disabling it.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **gameprofile-lookup-task-interval**

  | The interval, in seconds, used by the GameProfileQueryTask to process queued GameProfile requests. 
  | **Note**: This setting should be raised if you experience the following error: 
  | ``The client has sent too many requests within a certain amount of time``. 
  | Finally, if set to ``0`` or less, the default interval will be used.
  | **Type:** ``int``
  | **Default:** ``4``
  |

* **generate-spawn-on-load**

  | If ``true``, this world will generate its spawn the moment its loaded.
  | **Type:** ``Boolean``
  | **Default:** ``false``
  |

* **invalid-lookup-uuids**

  | The list of uuid's that should never perform a lookup against Mojang's session server. 
  | **Note**: If you are using SpongeForge, make sure to enter any mod fake player's UUID to this list.
  | **Type:** ``List<UUID>``
  |

* **item-merge-radius**

  | The defined merge radius for Item entities such that when two items are 
  | within the defined radius of each other, they will attempt to merge. Usually, 
  | the default radius is set to ``0.5`` in Vanilla, however, for performance reasons 
  | ``2.5`` is generally acceptable. 
  | **Note**: Increasing the radius higher will likely cause performance degradation 
  | with larger amount of items as they attempt to merge and search nearby 
  | areas for more items. Setting to a negative value is not supported!
  | **Type:** ``double``
  | **Default:** ``2.5``
  |

* **keep-spawn-loaded**

  | If ``true``, this worlds spawn will remain loaded with no players.
  | **Type:** ``Boolean``
  | **Default:** ``true``
  |

* **leaf-decay**

  | If ``true``, natural leaf decay is allowed.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **load-on-startup**

  | If ``true``, this world will load on startup.
  | **Type:** ``Boolean``
  | **Default:** ``false``
  |

* **max-chunk-unloads-per-tick**

  | The maximum number of queued unloaded chunks that will be unloaded in a single tick. 
  | **Note**: With the chunk gc enabled, this setting only applies to the ticks 
  | where the gc runs (controlled by ``chunk-gc-tick-interval``) 
  | **Note**: If the maximum unloads is too low, too many chunks may remain 
  | loaded on the world and increases the chance for a drop in tps.
  | **Type:** ``int``
  | **Default:** ``100``
  |

* **mob-spawn-range**

  | Specifies the radius (in chunks) of where creatures will spawn. 
  | This value is capped to the current view distance setting in server.properties
  | **Type:** ``int``
  | **Default:** ``4``
  |

* **portal-agents**

  | A list of all detected portal agents used in this world. 
  | In order to override, change the target world name to any other valid world. 
  | **Note**: If world is not found, it will fallback to default.
  | **Type:** ``Map<String, String>``
  |

* **pvp-enabled**

  | If ``true``, this world will allow PVP combat.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **view-distance**

  | Override world distance per world/dimension 
  | The value must be greater than or equal to ``3`` and less than or equal to ``32`` 
  | The server-wide view distance will be used when the value is ``-1``.
  | **Type:** ``int``
  | **Default:** ``-1``
  |

* **weather-ice-and-snow**

  | If ``true``, natural formation of ice and snow in supported biomes will be allowed.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **weather-thunder**

  | If ``true``, thunderstorms will be initiated in supported biomes.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |

* **world-enabled**

  | If ``true``, this world will be registered.
  | **Type:** ``boolean``
  | **Default:** ``true``
  |


------------------------------------------------------------------------------------------------------------

This configuration file was generated using SpongeForge 7.1.9 (Forge 2838, SpongeAPI 7.1):

.. code-block:: guess

        # 1.0
    # 
    # # If you need help with the configuration or have any questions related to Sponge,
    # # join us at the IRC or drop by our forums and leave a post.
    # 
    # # IRC: #sponge @ irc.esper.net ( https://webchat.esper.net/?channel=sponge )
    # # Forums: https://forums.spongepowered.org/
    # 

    sponge {
        # Stopgap measures for dealing with broken mods
        broken-mods {
            # A list of mod ids that have broken network handlers (they interact with the game from a Netty handler thread).
            # All network handlers from a forcibly scheduled to run on the main thread.
            # Note that this setting should be considered a last resort, and should only be used as a stopgap measure while waiting for a mod to properly fix the issue.
            broken-network-handler-mods=[]
        }
        bungeecord {
            # If 'true', allows BungeeCord to forward IP address, UUID, and Game Profile to this server.
            ip-forwarding=false
        }
        cause-tracker {
            # A mapping that is semi-auto-populating for TileEntities whose types
            # are found to be providing "null" Block sources as neighbor notifications
            # that end up causing crashes or spam reports. If the value is set to 
            # "true", then a "workaround" will be attempted. If not, the 
            # 
            # current BlockState at the target source will be queried from the world.
            # This map having a specific
            # entry of a TileEntity will prevent a log or warning come up to any logs
            # when that "null" arises, and Sponge will self-rectify the TileEntity
            # by calling the method "getBlockType()". It is advised that if the mod
            # id in question is coming up, that the mod author is notified about the
            # error-prone usage of the field "blockType". You can refer them to
            # the following links for the issue:
            # https://gist.github.com/gabizou/ad570dc09dfed259cac9d74284e78e8b
            # https://github.com/SpongePowered/SpongeForge/issues/2787
            # Also, please provide them with these links for the example PR to
            # fix the issue itself, as the fix is very simple:
            # https://github.com/TehNut/Soul-Shards-Respawn/pull/24
            # https://github.com/Epoxide-Software/Enchanting-Plus/pull/135
            # 
            auto-fix-null-source-block-providing-tile-entities {}
            # If set to 'true', when a mod or plugin attempts to spawn an entity 
            # off the main server thread, Sponge will automatically 
            # capture said entity to spawn it properly on the main 
            # server thread. The catch to this is that some mods are 
            # not considering the consequences of spawning an entity 
            # off the server thread, and are unaware of potential race 
            # conditions they may cause. If this is set to false, 
            # Sponge will politely ignore the entity being spawned, 
            # and emit a warning about said spawn anyways.
            capture-async-spawning-entities=true
            # If 'true', more thorough debugging for PhaseStates 
            # such that a StackTrace is created every time a PhaseState 
            # switches, allowing for more fine grained troubleshooting 
            # in the cases of runaway phase states. Note that this is 
            # not extremely performant and may have some associated costs 
            # with generating the stack traces constantly.
            generate-stacktrace-per-phase=false
            # The maximum number of times to recursively process transactions in a single phase.
            # Some mods may interact badly with Sponge's block capturing system, causing Sponge to
            # end up capturing block transactions every time it tries to process an existing batch.
            # Due to the recursive nature of the depth-first processing that Sponge uses to handle block transactions,
            # this can result in a stack overflow, which causes us to lose all infomration about the original cause of the issue.
            # To prevent a stack overflow, Sponge tracks the current processing depth, and aborts processing when it exceeds
            # this threshold.
            # The default value should almost always work properly -  it's unlikely you'll ever have to change it.
            max-block-processing-depth=1000
            # If verbose is not enabled, this restricts the amount of 
            # runaway phase state printouts, usually happens on a server 
            # where a PhaseState is not completing. Although rare, it should 
            # never happen, but when it does, sometimes it can continuously print 
            # more and more. This attempts to placate that while a fix can be worked on 
            # to resolve the runaway. If verbose is enabled, they will always print.
            maximum-printed-runaway-counts=3
            # If true, when a mod attempts to perform a neighbor notification
            # on a block, some mods do not know to perform a 'null' check
            # on the source block of their TileEntity. This usually goes by
            # unnoticed by other mods, because they may perform '==' instance
            # equality checks instead of calling methods on the potentially
            # null Block, but Sponge uses the block to build information to
            # help tracking. This has caused issues in the past. Generally,
            # this can be useful for leaving "true" so a proper report is
            # generated once for your server, and can be reported to the
            # offending mod author.
            # This is 'false' by default in SpongeVanilla.
            # Review the following links for more info:
            # https://gist.github.com/gabizou/ad570dc09dfed259cac9d74284e78e8b
            # https://github.com/SpongePowered/SpongeForge/issues/2787
            # 
            report-null-source-blocks-on-neighbor-notifications=false
            # If set to 'true', when a mod or plugin attempts to submit a command
            # asynchronously, Sponge will automatically capture said command
            # and submit it for processing on the server thread. The catch to
            # this is that some mods are performing these commands in vanilla
            # without considering the possible consequences of such commands
            # affecting any thread-unsafe parts of Minecraft, such as worlds,
            # block edits, entity spawns, etc. If this is set to false, Sponge
            # will politely ignore the command being executed, and emit a warning
            # about said command anyways.
            resync-commands-from-async=true
            # If 'true', the phase tracker will print out when there are too many phases 
            # being entered, usually considered as an issue of phase re-entrance and 
            # indicates an unexpected issue of tracking phases not to complete. 
            # If this is not reported yet, please report to Sponge. If it has been 
            # reported, you may disable this.
            verbose=true
            # If 'true', the phase tracker will dump extra information about the current phases 
            # when certain non-PhaseTracker related exceptions occur. This is usually not necessary, as the information 
            # in the exception itself can normally be used to determine the cause of the issue
            verbose-errors=false
        }
        commands {
            # Command aliases will resolve conflicts when multiple plugins request a specific command, 
            # Correct syntax is <unqualified command>=<plugin name> e.g. "sethome=homeplugin"
            aliases {}
            # Defines how Sponge should act when a user tries to access a command they do not have
            # permission for
            command-hiding {
                # If this is true, when a user tries to tab complete a command, or use "/sponge which" or 
                # "/sponge:help" this prevents commands a user does not have permission for from being completed.
                # 
                # Note that some commands may not show up during tab complete if a user does not have permission
                # regardless of this setting.
                hide-on-discovery-attempt=true
                # If this is true, when a user tries to use a command they don't have permission for, Sponge
                # will act as if the command doesn't exist, rather than showing a no permissions message.
                hide-on-execution-attempt=false
            }
            # Some mods may not trigger a permission check when running their command. Setting this to
            # true will enforce a check of the Sponge provided permission ("<modid>.command.<commandname>").
            # Note that setting this to true may cause some commands that are generally accessible to all to
            # require a permission to run.
            # 
            # Setting this to true will enable greater control over whether a command will appear in
            # tab completion and Sponge's help command.
            # 
            # If you are not using a permissions plugin, it is highly recommended that this is set to false
            # (as it is by default).
            enforce-permission-checks-on-non-sponge-commands=false
            # Patches the specified commands to respect the world of the sender instead of applying the 
            # changes on the all worlds.
            multi-world-patches {
                ""=true
            }
        }
        debug {
            # Detect and prevent parts of PlayerChunkMap being called off the main thread.
            # This may decrease sever preformance, so you should only enable it when debugging a specific issue.
            concurrent-chunk-map-checks=false
            # Detect and prevent certain attempts to use entities concurrently. 
            # WARNING: May drastically decrease server performance. Only set this to 'true' to debug a pre-existing issue.
            concurrent-entity-checks=false
            # If 'true', Java's thread contention monitoring for thread dumps is enabled.
            thread-contention-monitoring=false
        }
        entity {
            # Number of colliding entities in one spot before logging a warning. Set to 0 to disable
            collision-warn-size=200
            # Number of ticks before a painting is respawned on clients when their art is changed
            entity-painting-respawn-delay=2
            # Number of ticks before the fake player entry of a human is removed from the tab list (range of 0 to 100 ticks).
            human-player-list-remove-delay=10
            # Controls the time in ticks for when an item despawns.
            item-despawn-rate=6000
            # The upper bounded range where living entities farther from a player will likely despawn
            living-hard-despawn-range=128
            # The amount of seconds before a living entity between the soft and hard despawn ranges from a player to be considered for despawning
            living-soft-despawn-minimum-life=30
            # The lower bounded range where living entities near a player may potentially despawn
            living-soft-despawn-range=32
            # Maximum size of an entity's bounding box before removing it. Set to 0 to disable
            max-bounding-box-size=1000
            # Square of the maximum speed of an entity before removing it. Set to 0 to disable
            max-speed=100
        }
        entity-activation-range {
            # If 'true', newly discovered entities will be added to this config with a default value.
            auto-populate=false
            # Default activation ranges used for all entities unless overridden.
            defaults {
                ambient=32
                aquatic=32
                creature=32
                misc=16
                monster=32
            }
            # Per-mod overrides. Refer to the minecraft default mod for example.
            mods {}
        }
        entity-collisions {
            # If 'true', newly discovered entities/blocks will be added to this config with a default value.
            auto-populate=false
            # Maximum amount of entities any given entity or block can collide with. This improves 
            # performance when there are more than 8 entities on top of each other such as a 1x1 
            # spawn pen. Set to 0 to disable.
            max-entities-within-aabb=8
            # Per-mod overrides. Refer to the minecraft default mod for example.
            mods {
                botania {
                    blocks {}
                    # Default maximum collisions used for all entities/blocks unless overridden.
                    defaults {}
                    # If 'false', entity collision rules for this mod will be ignored.
                    enabled=true
                    entities {
                        corporeaspark=-1
                        spark=-1
                    }
                }
                minecraft {
                    blocks {
                        "detector_rail"=1
                        "heavy_weighted_pressure_plate"=150
                        "light_weighted_pressure_plate"=15
                        "mob_spawner"=-1
                        "stone_pressure_plate"=1
                        "wooden_button"=1
                        "wooden_pressure_plate"=1
                    }
                    # Default maximum collisions used for all entities/blocks unless overridden.
                    defaults {}
                    # If 'false', entity collision rules for this mod will be ignored.
                    enabled=true
                    entities {
                        thrownpotion=-1
                    }
                }
            }
        }
        exploits {
            # If limit-book-size is enabled, controls the multiplier applied to each book page size
            book-size-total-multiplier=0.98
            # Enables filtering invalid entities when a chunk is being saved
            # such that the entity that does not "belong" in the saving
            # chunk will not be saved, and forced an update to the world's
            # tracked entity lists for chunks.
            # See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0311-Prevent-Saving-Bad-entities-to-chunks.patch
            filter-invalid-entities-on-chunk-save=true
            # Limits the size of a book that can be sent by the client.
            # See https://github.com/PaperMC/Paper/blob/f8058a8187da9f6185d95bb786783e12c79c8b18/Spigot-Server-Patches/0403-Book-Size-Limits.patch
            # (Only affects SpongeVanilla)
            limit-book-size=true
            # Enables focing a chunk load when an entity position
            # is set. Usually due to teleportation, vehicle movement
            # etc. can a position lead an entity to no longer exist
            # within it's currently marked and tracked chunk. This will
            # enable that chunk for the position is loaded. Part of several
            # exploits.See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0335-Ensure-chunks-are-always-loaded-on-hard-position-set.patch
            # (Only affects SpongeVanilla)
            load-chunk-on-position-set=true
            # Enables forcing chunks to save when an entity is added
            # or removed from said chunk. This is a partial fix for
            # some exploits using vehicles.See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0306-Mark-chunk-dirty-anytime-entities-change-to-guarante.patch
            # (Only affects SpongeVanilla)
            mark-chunks-as-dirty-on-entity-list-modification=true
            # If limit-book-size is enabled, controls the maximum size of a book page
            max-book-page-size=2560
            # Prevents an exploit in which the client sends a packet with the 
            # itemstack name exceeding the string limit.
            prevent-creative-itemstack-name-exploit=true
            # Enables forcing updates to the player's location on vehicle movement.
            # This is partially required to update the server's understanding of
            # where the player exists, and allows chunk loading issues to be avoided
            # with laggy connections and/or hack clients.See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0378-Sync-Player-Position-to-Vehicles.patch
            # (Only affects SpongeVanilla)
            sync-player-positions-for-vehicle-movement=true
            # Enables forcing a chunk-tracking refresh on entity movement.
            # This enables a guarantee that the entity is tracked in the 
            # proper chunk when moving.https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0315-Always-process-chunk-registration-after-moving.patch
            # (Only affects SpongeVanilla)
            update-tracked-chunk-on-entity-move=true
        }
        general {
            # The directory for Sponge plugin configurations, relative to the  
            # execution root or specified as an absolute path. 
            # Note that the default: "${CANONICAL_GAME_DIR}/config" 
            # is going to use the "config" directory in the root game directory. 
            # If you wish for plugin configs to reside within a child of the configuration 
            # directory, change the value to, for example, "${CANONICAL_CONFIG_DIR}/sponge/plugins". 
            # Note: It is not recommended to set this to "${CANONICAL_CONFIG_DIR}/sponge", as there is 
            # a possibility that plugin configurations can conflict the Sponge core configurations. 
            # 
            config-dir="${CANONICAL_GAME_DIR}/config"
            # If 'true', sleeping between chunk saves will be enabled, beware of memory issues.
            file-io-thread-sleep=false
            # Additional directory to search for plugins, relative to the 
            # execution root or specified as an absolute path. 
            # Note that the default: "${CANONICAL_MODS_DIR}/plugins" 
            # is going to search for a plugins folder in the mods directory. 
            # If you wish for the plugins folder to reside in the root game 
            # directory, change the value to "${CANONICAL_GAME_DIR}/plugins".
            plugins-dir="${CANONICAL_MODS_DIR}/plugins"
        }
        ip-sets {}
        logging {
            # Log when blocks are broken
            block-break=false
            # Log when blocks are modified
            block-modify=false
            # Log when blocks are placed
            block-place=false
            # Log when blocks are populated in a chunk
            block-populate=false
            # Log when blocks are placed by players and tracked
            block-tracking=false
            # Log when chunks are queued to be unloaded by the chunk garbage collector.
            chunk-gc-queue-unload=false
            # Log when chunks are loaded
            chunk-load=false
            # Log when chunks are unloaded
            chunk-unload=false
            # Whether to log entity collision/count checks
            entity-collision-checks=false
            # Log when living entities are destroyed
            entity-death=false
            # Log when living entities are despawned
            entity-despawn=false
            # Log when living entities are spawned
            entity-spawn=false
            # Whether to log entity removals due to speed
            entity-speed-removal=false
            # Log when server receives exploited packet with itemstack name exceeding string limit.
            exploit-itemstack-name-overflow=false
            # Log when player attempts to respawn invisible to surrounding players.
            exploit-respawn-invisibility=false
            # Log when server receives exploited packet to update a sign containing commands from player with no permission.
            exploit-sign-command-updates=false
            # Add stack traces to dev logging
            log-stacktraces=false
            # Log when a world auto-saves its chunk data. Note: This may be spammy depending on the auto-save-interval configured for world.
            world-auto-save=false
        }
        metrics {
            # The global collection state that should be respected by all plugins that have no specified collection state. If undefined then it is treated as disabled.
            global-state=UNDEFINED
            # Plugin-specific collection states that override the global collection state.
            plugin-states {}
        }
        modules {
            # Enables experimental fixes for broken mods
            broken-mod=false
            bungeecord=false
            entity-activation-range=true
            entity-collisions=true
            # Controls whether any exploit patches are applied.
            # If there are issues with any specific exploits, please
            # test in the exploit category first, before disabling all
            # exploits with this toggle.
            exploits=true
            # Allows configuring Vanilla movement and speed checks
            movement-checks=false
            optimizations=true
            # Use real (wall) time instead of ticks as much as possible
            realtime=false
            # Controls block range and tick rate of tileentities. 
            # Use with caution as this can break intended functionality.
            tileentity-activation=false
            timings=true
            tracking=true
        }
        movement-checks {
            # Controls whether the 'player/entity moved wrongly!' check will be enforced
            moved-wrongly=true
            # Controls whether the 'player moved too quickly!' check will be enforced
            player-moved-too-quickly=true
            # Controls whether the 'vehicle of player moved too quickly!' check will be enforced
            player-vehicle-moved-too-quickly=true
        }
        optimizations {
            # Runs lighting updates asynchronously.
            async-lighting {
                # If 'true', lighting updates are run asynchronously.
                enabled=true
                # The amount of threads to dedicate for asynchronous lighting updates. (Default: 2)
                num-threads=2
            }
            # Caches tameable entities owners to avoid constant lookups against data watchers. If mods 
            # cause issues, disable this.
            cache-tameable-owners=true
            # Occasionally, some built in advancements, 
            # recipes, etc. can fail to deserialize properly
            # which ends up potentially spamming the server log
            # and the original provider of the failing content
            # is not able to fix. This provides an option to
            # suppress the exceptions printing out in the log.
            disable-failing-deserialization-log-spam=true
            # If 'true', block item drops are pre-processed to avoid 
            # having to spawn extra entities that will be merged post spawning. 
            # Usually, Sponge is smart enough to determine when to attempt an item pre-merge 
            # and when not to, however, in certain cases, some mods rely on items not being 
            # pre-merged and actually spawned, in which case, the items will flow right through 
            # without being merged.
            drops-pre-merge=false
            # Uses theosib's redstone algorithms to completely overhaul the way redstone works.
            eigen-redstone {
                # If 'true', uses theosib's redstone implementation which improves performance. 
                # See https://bugs.mojang.com/browse/MC-11193 and 
                #     https://bugs.mojang.com/browse/MC-81098 for more information. 
                # Note: We cannot guarantee compatibility with mods. Use at your discretion.
                enabled=false
                # If 'true', restores the vanilla algorithm for computing wire power levels when powering off.
                vanilla-decrement=false
                # If 'true', restores the vanilla algorithm for propagating redstone wire changes.
                vanilla-search=false
            }
            # If 'true', provides a fix for possible leaks through
            # Minecraft's enchantment helper code that can leak
            # entity and world references without much interaction
            # Forge native (so when running SpongeForge implementation)
            # has a similar patch, but Sponge's patch works a little harder
            # at it, but Vanilla (SpongeVanilla implementation) does NOT
            # have any of the patch, leading to the recommendation that this
            # patch is enabled "for sure" when using SpongeVanilla implementation.
            # See https://bugs.mojang.com/browse/MC-128547 for more information.
            # 
            enchantment-helper-leak-fix=true
            # If 'true', allows for Sponge to make better assumptinos on single threaded
            # operations with relation to various checks for server threaded operations.
            # This is default to true due to Sponge being able to precisely inject when
            # the server thread is available. This should make an already fast operation
            # much faster for better thread checks to ensure stability of sponge's systems.
            faster-thread-checks=true
            # If 'true', re-writes the incredibly inefficient Vanilla Map code.
            # This yields enormous performance enhancements when using many maps, but has a tiny chance of breaking mods that invasively modify Vanilla.It is strongly reccomended to keep this on, unless explicitly advised otherwise by a Sponge developer
            map-optimization=true
            # Based on Aikar's optimizationo of Hoppers, setting this to 'true'
            # will allow for hoppers to save performing server -> client updates
            # when transferring items. Because hoppers can transfer items multiple
            # times per tick, these updates can get costly on the server, with
            # little to no benefit to the client. Because of the nature of the
            # change, the default will be 'false' due to the inability to pre-emptively
            # foretell whether mod compatibility will fail with these changes or not.
            # Refer to: https://github.com/PaperMC/Paper/blob/8175ec916f31dcd130fe0884fe46bdc187d829aa/Spigot-Server-Patches/0269-Optimize-Hoppers.patch
            # for more details.
            optimize-hoppers=false
            # If 'true', uses Panda4494's redstone implementation which improves performance. 
            # See https://bugs.mojang.com/browse/MC-11193 for more information. 
            # Note: This optimization has a few issues which are explained in the bug report. 
            # We strongly recommend using eigen redstone over this implementation as this will
            # be removed in a future release.
            panda-redstone=false
            # Handles structures that are saved to disk. Certain structures can take up large amounts 
            # of disk space for very large maps and the data for these structures is only needed while the 
            # world around them is generating. Disabling saving of these structures can save disk space and 
            # time during saves if your world is already fully generated. 
            # Warning: disabling structure saving will break the vanilla locate command.
            structure-saving {
                # If 'true', newly discovered structures will be added to this config
                # with a default value of 'true'. This is useful for finding out
                # potentially what structures are being saved from various mods, and
                # allowing those structures to be selectively disabled.
                auto-populate=false
                # If 'false', disables the modification to prevent certain structures
                # from saving to the world's data folder. If you wish to prevent certain
                # structures from saving, leave this "enabled=true". When 'true', the
                # modification allows for specific 'named' structures to NOT be saved to
                # disk. Examples of some structures that are costly and somewhat irrelivent
                # is 'mineshaft's, as they build several structures and save, even after
                # finished generating.
                enabled=false
                # Per-mod overrides. Refer to the minecraft default mod for example.
                mods {
                    minecraft {
                        # If 'false', this mod will never save its structures. This may
                        # break some mod functionalities when requesting to locate their
                        # structures in a World. If true, allows structures not overridden
                        # in the section below to be saved by default. If you wish to find
                        # a structure to prevent it being saved, enable 'auto-populate' and
                        # restart the server/world instance.
                        enabled=true
                        # Per structure override. Having the value of 'false' will prevent
                        # that specific named structure from saving.
                        structures {
                            mineshaft=false
                        }
                    }
                }
            }
            # Vanilla performs a lot of is area loaded checks during
            # entity collision calculations with blocks, and because
            # these calculations require fetching the chunks to see
            # if they are loaded, before getting the block states
            # from those chunks, there can be some small performance
            # increase by checking the entity's owned active chunk
            # it may currently reside in. Essentially, instead of
            # asking the world if those chunks are loaded, the entity
            # would know whether it's chunks are loaded and that neighbor's
            # chunks are loaded.
            use-active-chunks-for-collisions=false
        }
        permission {
            # If 'true', Sponge plugins will be used to handle permissions rather than any Forge mod
            forge-permissions-handler=false
        }
        player-block-tracker {
            # Block IDs that will be blacklisted for player block placement tracking.
            block-blacklist=[]
            # If 'true', adds player tracking support for block positions. 
            # Note: This should only be disabled if you do not care who caused a block to change.
            enabled=true
        }
        # Used to control spawn limits around players. 
        # Note: The radius uses the lower value of mob spawn range and server's view distance.
        spawner {
            # The number of ambients the spawner can potentially spawn around a player.
            spawn-limit-ambient=15
            # The number of animals the spawner can potentially spawn around a player.
            spawn-limit-animal=15
            # The number of aquatics the spawner can potentially spawn around a player.
            spawn-limit-aquatic=5
            # The number of monsters the spawner can potentially spawn around a player.
            spawn-limit-monster=70
            # The ambient spawning tick rate. Default: 400
            tick-rate-ambient=400
            # The animal spawning tick rate. Default: 400
            tick-rate-animal=400
            # The aquatic spawning tick rate. Default: 1
            tick-rate-aquatic=1
            # The monster spawning tick rate. Default: 1
            tick-rate-monster=1
        }
        # Configuration options related to the Sql service, including connection aliases etc
        sql {
            # Aliases for SQL connections, in the format jdbc:protocol://[username[:password]@]host/database
            aliases {}
        }
        # Blocks to blacklist for safe teleportation.
        teleport-helper {
            # If 'true', this blacklist will always be respected, otherwise, plugins can choose whether 
            # or not to respect it.
            force-blacklist=false
            # Block IDs that are listed here will not be selected by Sponge's safe teleport routine as 
            # a safe block for players to warp into. 
            # You should only list blocks here that are incorrectly selected, solid blocks that prevent 
            # movement are automatically excluded.
            unsafe-body-block-ids=[]
            # Block IDs that are listed here will not be selected by Sponge's safe 
            # teleport routine as a safe floor block.
            unsafe-floor-block-ids=[]
        }
        tileentity-activation {
            # If 'true', newly discovered tileentities will be added to this config with default settings.
            auto-populate=false
            # Default activation block range used for all tileentities unless overridden.
            default-block-range=64
            # Default tick rate used for all tileentities unless overridden.
            default-tick-rate=1
            # Per-mod overrides. Refer to the minecraft default mod for example.
            mods {}
        }
        timings {
            enabled=true
            hidden-config-entries=[
                "sponge.sql"
            ]
            history-interval=300
            history-length=3600
            server-name-privacy=false
            verbose=false
        }
        world {
            # The auto-save tick interval used when saving global player data. (Default: 900) 
            # Note: 20 ticks is equivalent to 1 second. Set to 0 to disable.
            auto-player-save-interval=900
            # The auto-save tick interval used to save all loaded chunks in a world. 
            # Set to 0 to disable. (Default: 900) 
            # Note: 20 ticks is equivalent to 1 second.
            auto-save-interval=900
            # The number of newly loaded chunks before triggering a forced cleanup. 
            # Note: When triggered, the loaded chunk threshold will reset and start incrementing. 
            # Disabled by default.
            chunk-gc-load-threshold=0
            # The tick interval used to cleanup all inactive chunks that have leaked in a world. 
            # Set to 0 to disable which restores vanilla handling. (Default: 600)
            chunk-gc-tick-interval=600
            # The number of seconds to delay a chunk unload once marked inactive. (Default: 15) 
            # Note: This gets reset if the chunk becomes active again.
            chunk-unload-delay=15
            # If 'true', any request for a chunk not currently loaded will be denied (exceptions apply 
            # for things like world gen and player movement). 
            # Warning: As this is an experimental setting for performance gain, if you encounter any issues 
            # then we recommend disabling it.
            deny-chunk-requests=false
            # The interval, in seconds, used by the GameProfileQueryTask to process queued GameProfile requests. (Default: 4) 
            # Note: This setting should be raised if you experience the following error: 
            # "The client has sent too many requests within a certain amount of time". 
            # Finally, if set to 0 or less, the default interval will be used.
            gameprofile-lookup-task-interval=4
            # If 'true', this world will generate its spawn the moment its loaded.
            generate-spawn-on-load=false
            # The list of uuid's that should never perform a lookup against Mojang's session server. 
            # Note: If you are using SpongeForge, make sure to enter any mod fake player's UUID to this list.
            invalid-lookup-uuids=[
                "00000000-0000-0000-0000-000000000000",
                "0d0c4ca0-4ff1-11e4-916c-0800200c9a66",
                "41c82c87-7afb-4024-ba57-13d2c99cae77"
            ]
            # The defined merge radius for Item entities such that when two items are 
            # within the defined radius of each other, they will attempt to merge. Usually, 
            # the default radius is set to 0.5 in Vanilla, however, for performance reasons 
            # 2.5 is generally acceptable. 
            # Note: Increasing the radius higher will likely cause performance degradation 
            # with larger amount of items as they attempt to merge and search nearby 
            # areas for more items. Setting to a negative value is not supported!
            item-merge-radius=2.5
            # If 'true', this worlds spawn will remain loaded with no players.
            keep-spawn-loaded=true
            # If 'true', natural leaf decay is allowed.
            leaf-decay=true
            # If 'true', this world will load on startup.
            load-on-startup=false
            # The maximum number of queued unloaded chunks that will be unloaded in a single tick. 
            # Note: With the chunk gc enabled, this setting only applies to the ticks 
            # where the gc runs (controlled by 'chunk-gc-tick-interval') 
            # Note: If the maximum unloads is too low, too many chunks may remain 
            # loaded on the world and increases the chance for a drop in tps. (Default: 100)
            max-chunk-unloads-per-tick=100
            # Specifies the radius (in chunks) of where creatures will spawn. 
            # This value is capped to the current view distance setting in server.properties
            mob-spawn-range=4
            # A list of all detected portal agents used in this world. 
            # In order to override, change the target world name to any other valid world. 
            # Note: If world is not found, it will fallback to default.
            portal-agents {
                "minecraft:default_the_end"=DIM1
                "minecraft:default_the_nether"=DIM-1
            }
            # If 'true', this world will allow PVP combat.
            pvp-enabled=true
            # Override world distance per world/dimension 
            # The value must be greater than or equal to 3 and less than or equal to 32 
            # The server-wide view distance will be used when the value is -1.
            view-distance=-1
            # If 'true', natural formation of ice and snow in supported biomes will be allowed.
            weather-ice-and-snow=true
            # If 'true', thunderstorms will be initiated in supported biomes.
            weather-thunder=true
            # If 'true', this world will be registered.
            world-enabled=true
        }
        # World Generation Modifiers to apply to the world
        world-generation-modifiers=[]
    }
