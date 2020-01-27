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

GlobalConfig
============

**broken-mods**

| Stopgap measures for dealing with broken mods

* **Type:** ``BrokenModCategory``

**bungeecord**

* **Type:** ``BungeeCordCategory``

**cause-tracker**

* **Type:** ``PhaseTrackerCategory``

**commands**

* **Type:** ``CommandsCategory``

**debug**

* **Type:** ``DebugCategory``

**entity**

* **Type:** ``EntityCategory``

**entity-activation-range**

* **Type:** ``EntityActivationRangeCategory``

**entity-collisions**

* **Type:** ``EntityCollisionCategory``

**exploits**

* **Type:** ``ExploitCategory``

**general**

* **Type:** ``GlobalGeneralCategory``

**ip-sets**

* **Type:** ``Map<String, List<IpSet>>``

**logging**

* **Type:** ``LoggingCategory``

**metrics**

* **Type:** ``MetricsCategory``

**modules**

* **Type:** ``ModuleCategory``

**movement-checks**

* **Type:** ``MovementChecksCategory``

**optimizations**

* **Type:** ``OptimizationCategory``

**player-block-tracker**

* **Type:** ``PlayerBlockTracker``

**spawner**

| Used to control spawn limits around players. 
| Note: The radius uses the lower value of mob spawn range and server's view distance.

* **Type:** ``SpawnerCategory``

**sql**

| Configuration options related to the Sql service, including connection aliases etc

* **Type:** ``SqlCategory``

**teleport-helper**

| Blocks to blacklist for safe teleportation.

* **Type:** ``TeleportHelperCategory``

**tileentity-activation**

* **Type:** ``TileEntityActivationCategory``

**timings**

* **Type:** ``TimingsCategory``

**world**

* **Type:** ``GlobalWorldCategory``

BrokenModCategory
=================

**broken-network-handler-mods**

| A list of mod ids that have broken network handlers (they interact with the game from a Netty handler thread).
| All network handlers from a forcibly scheduled to run on the main thread.
| Note that this setting should be considered a last resort, and should only be used as a stopgap measure while waiting for a mod to properly fix the issue.

* **Type:** ``List<String>``

BungeeCordCategory
==================

**ip-forwarding**

| If 'true', allows BungeeCord to forward IP address, UUID, and Game Profile to this server.

* **Type:** ``boolean``
* **Default:** ``false``

PhaseTrackerCategory
====================

**capture-async-spawning-entities**

| If set to 'true', when a mod or plugin attempts to spawn an entity 
| off the main server thread, Sponge will automatically 
| capture said entity to spawn it properly on the main 
| server thread. The catch to this is that some mods are 
| not considering the consequences of spawning an entity 
| off the server thread, and are unaware of potential race 
| conditions they may cause. If this is set to false, 
| Sponge will politely ignore the entity being spawned, 
| and emit a warning about said spawn anyways.

* **Type:** ``boolean``
* **Default:** ``true``

**generate-stacktrace-per-phase**

| If 'true', more thorough debugging for PhaseStates 
| such that a StackTrace is created every time a PhaseState 
| switches, allowing for more fine grained troubleshooting 
| in the cases of runaway phase states. Note that this is 
| not extremely performant and may have some associated costs 
| with generating the stack traces constantly.

* **Type:** ``boolean``
* **Default:** ``false``

**max-block-processing-depth**

| The maximum number of times to recursively process transactions in a single phase.
| Some mods may interact badly with Sponge's block capturing system, causing Sponge to
| end up capturing block transactions every time it tries to process an existing batch.
| Due to the recursive nature of the depth-first processing that Sponge uses to handle block transactions,
| this can result in a stack overflow, which causes us to lose all infomration about the original cause of the issue.
| To prevent a stack overflow, Sponge tracks the current processing depth, and aborts processing when it exceeds
| this threshold.
| The default value should almost always work properly -  it's unlikely you'll ever have to change it.

* **Type:** ``int``
* **Default:** ``100``

**maximum-printed-runaway-counts**

| If verbose is not enabled, this restricts the amount of 
| runaway phase state printouts, usually happens on a server 
| where a PhaseState is not completing. Although rare, it should 
| never happen, but when it does, sometimes it can continuously print 
| more and more. This attempts to placate that while a fix can be worked on 
| to resolve the runaway. If verbose is enabled, they will always print.

* **Type:** ``int``
* **Default:** ``3``

**verbose**

| If 'true', the phase tracker will print out when there are too many phases 
| being entered, usually considered as an issue of phase re-entrance and 
| indicates an unexpected issue of tracking phases not to complete. 
| If this is not reported yet, please report to Sponge. If it has been 
| reported, you may disable this.

* **Type:** ``boolean``
* **Default:** ``true``

**verbose-errors**

| If 'true', the phase tracker will dump extra information about the current phases 
| when certain non-PhaseTracker related exceptions occur. This is usually not necessary, as the information 
| in the exception itself can normally be used to determine the cause of the issue

* **Type:** ``boolean``
* **Default:** ``false``

CommandsCategory
================

**aliases**

| Command aliases will resolve conflicts when multiple plugins request a specific command, 
| Correct syntax is <unqualified command>=<plugin name> e.g. "sethome=homeplugin"

* **Type:** ``Map<String, String>``

**multi-world-patches**

| Patches the specified commands to respect the world of the sender instead of applying the 
| changes on the all worlds.

* **Type:** ``Map<String, Boolean>``

DebugCategory
=============

**concurrent-chunk-map-checks**

| Detect and prevent parts of PlayerChunkMap being called off the main thread.
| This may decrease sever preformance, so you should only enable it when debugging a specific issue.

* **Type:** ``boolean``
* **Default:** ``false``

**concurrent-entity-checks**

| Detect and prevent certain attempts to use entities concurrently. 
| WARNING: May drastically decrease server performance. Only set this to 'true' to debug a pre-existing issue.

* **Type:** ``boolean``
* **Default:** ``false``

**dump-chunks-on-deadlock**

| Dump chunks in the event of a deadlock

* **Type:** ``boolean``
* **Default:** ``false``

**dump-heap-on-deadlock**

| Dump the heap in the event of a deadlock

* **Type:** ``boolean``
* **Default:** ``false``

**dump-threads-on-warn**

| Dump the server thread on deadlock warning

* **Type:** ``boolean``
* **Default:** ``false``

**thread-contention-monitoring**

| If 'true', Java's thread contention monitoring for thread dumps is enabled.

* **Type:** ``boolean``
* **Default:** ``false``

EntityCategory
==============

**collision-warn-size**

| Number of colliding entities in one spot before logging a warning. Set to 0 to disable

* **Type:** ``int``
* **Default:** ``200``

**count-warn-size**

| Number of entities in one dimension before logging a warning. Set to 0 to disable

* **Type:** ``int``
* **Default:** ``0``

**entity-painting-respawn-delay**

| Number of ticks before a painting is respawned on clients when their art is changed

* **Type:** ``int``
* **Default:** ``2``

**human-player-list-remove-delay**

| Number of ticks before the fake player entry of a human is removed from the tab list (range of 0 to 100 ticks).

* **Type:** ``int``
* **Default:** ``10``

**item-despawn-rate**

| Controls the time in ticks for when an item despawns.

* **Type:** ``int``
* **Default:** ``6000``

**living-hard-despawn-range**

| The upper bounded range where living entities farther from a player will likely despawn

* **Type:** ``int``
* **Default:** ``128``

**living-soft-despawn-minimum-life**

| The amount of seconds before a living entity between the soft and hard despawn ranges from a player to be considered for despawning

* **Type:** ``int``
* **Default:** ``30``

**living-soft-despawn-range**

| The lower bounded range where living entities near a player may potentially despawn

* **Type:** ``int``
* **Default:** ``32``

**max-bounding-box-size**

| Maximum size of an entity's bounding box before removing it. Set to 0 to disable

* **Type:** ``int``
* **Default:** ``1000``

**max-speed**

| Square of the maximum speed of an entity before removing it. Set to 0 to disable

* **Type:** ``int``
* **Default:** ``100``

EntityActivationRangeCategory
=============================

**auto-populate**

| If 'true', newly discovered entities will be added to this config with a default value.

* **Type:** ``boolean``
* **Default:** ``false``

**defaults**

| Default activation ranges used for all entities unless overridden.

* **Type:** ``Map<String, Integer>``

**mods**

| Per-mod overrides. Refer to the minecraft default mod for example.

* **Type:** ``Map<String, EntityActivationModCategory>``

EntityCollisionCategory
=======================

**auto-populate**

| If 'true', newly discovered entities/blocks will be added to this config with a default value.

* **Type:** ``boolean``
* **Default:** ``false``

**defaults**

| Default maximum collisions used for all entities/blocks unless overridden.

* **Type:** ``Map<String, Integer>``

**max-entities-within-aabb**

| Maximum amount of entities any given entity or block can collide with. This improves 
| performance when there are more than 8 entities on top of each other such as a 1x1 
| spawn pen. Set to 0 to disable.

* **Type:** ``int``
* **Default:** ``8``

**mods**

| Per-mod overrides. Refer to the minecraft default mod for example.

* **Type:** ``Map<String, CollisionModCategory>``

ExploitCategory
===============

**prevent-creative-itemstack-name-exploit**

| Prevents an exploit in which the client sends a packet with the 
| itemstack name exceeding the string limit.

* **Type:** ``boolean``
* **Default:** ``true``

**prevent-sign-command-exploit**

| Prevents an exploit in which the client sends a packet to update a sign containing 
| commands from a player without permission.

* **Type:** ``boolean``
* **Default:** ``true``

GlobalGeneralCategory
=====================

**config-dir**

| The directory for Sponge plugin configurations, relative to the  
| execution root or specified as an absolute path. 
| Note that the default: "${CANONICAL_GAME_DIR}/config" 
| is going to use the "plugins" directory in the root game directory. 
| If you wish for plugin configs to reside within a child of the configuration 
| directory, change the value to, for example, "${CANONICAL_CONFIG_DIR}/sponge/plugins". 
| Note: It is not recommended to set this to "${CANONICAL_CONFIG_DIR}/sponge", as there is 
| a possibility that plugin configurations can conflict the Sponge core configurations.

* **Type:** ``String``
* **Default:** ``${CANONICAL_GAME_DIR}/config``

**disable-warnings**

| Disable warning messages to server admins

* **Type:** ``boolean``
* **Default:** ``false``

**file-io-thread-sleep**

| If 'true', sleeping between chunk saves will be enabled, beware of memory issues.

* **Type:** ``boolean``
* **Default:** ``false``

**plugins-dir**

| Additional directory to search for plugins, relative to the 
| execution root or specified as an absolute path. 
| Note that the default: "${CANONICAL_MODS_DIR}/plugins" 
| is going to search for a plugins folder in the mods directory. 
| If you wish for the plugins folder to reside in the root game 
| directory, change the value to "${CANONICAL_GAME_DIR}/plugins".

* **Type:** ``String``
* **Default:** ``${CANONICAL_MODS_DIR}/plugins``

LoggingCategory
===============

**block-break**

| Log when blocks are broken

* **Type:** ``boolean``
* **Default:** ``false``

**block-modify**

| Log when blocks are modified

* **Type:** ``boolean``
* **Default:** ``false``

**block-place**

| Log when blocks are placed

* **Type:** ``boolean``
* **Default:** ``false``

**block-populate**

| Log when blocks are populated in a chunk

* **Type:** ``boolean``
* **Default:** ``false``

**block-tracking**

| Log when blocks are placed by players and tracked

* **Type:** ``boolean``
* **Default:** ``false``

**chunk-gc-queue-unload**

| Log when chunks are queued to be unloaded by the chunk garbage collector.

* **Type:** ``boolean``
* **Default:** ``false``

**chunk-load**

| Log when chunks are loaded

* **Type:** ``boolean``
* **Default:** ``false``

**chunk-unload**

| Log when chunks are unloaded

* **Type:** ``boolean``
* **Default:** ``false``

**entity-collision-checks**

| Whether to log entity collision/count checks

* **Type:** ``boolean``
* **Default:** ``false``

**entity-death**

| Log when living entities are destroyed

* **Type:** ``boolean``
* **Default:** ``false``

**entity-despawn**

| Log when living entities are despawned

* **Type:** ``boolean``
* **Default:** ``false``

**entity-spawn**

| Log when living entities are spawned

* **Type:** ``boolean``
* **Default:** ``false``

**entity-speed-removal**

| Whether to log entity removals due to speed

* **Type:** ``boolean``
* **Default:** ``false``

**exploit-itemstack-name-overflow**

| Log when server receives exploited packet with itemstack name exceeding string limit.

* **Type:** ``boolean``
* **Default:** ``false``

**exploit-respawn-invisibility**

| Log when player attempts to respawn invisible to surrounding players.

* **Type:** ``boolean``
* **Default:** ``false``

**exploit-sign-command-updates**

| Log when server receives exploited packet to update a sign containing commands from player with no permission.

* **Type:** ``boolean``
* **Default:** ``false``

**log-stacktraces**

| Add stack traces to dev logging

* **Type:** ``boolean``
* **Default:** ``false``

**world-auto-save**

| Log when a world auto-saves its chunk data. Note: This may be spammy depending on the auto-save-interval configured for world.

* **Type:** ``boolean``
* **Default:** ``false``

MetricsCategory
===============

**default-permission**

| Determines whether plugins that are newly added are allowed to perform
| data/metric collection by default. Plugins detected by Sponge will be added to the "plugin-permissions" section with this value.
| 
| Set to true to enable metric gathering by default, false otherwise.

* **Type:** ``boolean``
* **Default:** ``false``

**plugin-permissions**

| Provides (or revokes) permission for metric gathering on a per plugin basis.
| Entries should be in the format "plugin-id=<true|false>".
| 
| Deleting an entry from this list will reset it to the default specified in
| "default-permission"

* **Type:** ``Map<String, Boolean>``

ModuleCategory
==============

**broken-mod**

| Enables experimental fixes for broken mods

* **Type:** ``boolean``
* **Default:** ``false``

**bungeecord**

* **Type:** ``boolean``
* **Default:** ``false``

**entity-activation-range**

* **Type:** ``boolean``
* **Default:** ``true``

**entity-collisions**

* **Type:** ``boolean``
* **Default:** ``true``

**exploits**

* **Type:** ``boolean``
* **Default:** ``true``

**movement-checks**

| Allows configuring Vanilla movement and speed checks

* **Type:** ``boolean``
* **Default:** ``false``

**optimizations**

* **Type:** ``boolean``
* **Default:** ``true``

**realtime**

| Use real (wall) time instead of ticks as much as possible

* **Type:** ``boolean``
* **Default:** ``false``

**tileentity-activation**

| Controls block range and tick rate of tileentities. 
| Use with caution as this can break intended functionality.

* **Type:** ``boolean``
* **Default:** ``false``

**timings**

* **Type:** ``boolean``
* **Default:** ``true``

**tracking**

* **Type:** ``boolean``
* **Default:** ``true``

MovementChecksCategory
======================

**moved-wrongly**

| Controls whether the 'player/entity moved wrongly!' check will be enforced

* **Type:** ``boolean``
* **Default:** ``true``

**player-moved-too-quickly**

| Controls whether the 'player moved too quickly!' check will be enforced

* **Type:** ``boolean``
* **Default:** ``true``

**player-vehicle-moved-too-quickly**

| Controls whether the 'vehicle of player moved too quickly!' check will be enforced

* **Type:** ``boolean``
* **Default:** ``true``

OptimizationCategory
====================

**async-lighting**

| Runs lighting updates asynchronously.

* **Type:** ``AsyncLightingCategory``

**cache-tameable-owners**

| Caches tameable entities owners to avoid constant lookups against data watchers. If mods 
| cause issues, disable this.

* **Type:** ``boolean``
* **Default:** ``true``

**drops-pre-merge**

| If 'true', block item drops are pre-processed to avoid 
| having to spawn extra entities that will be merged post spawning. 
| Usually, Sponge is smart enough to determine when to attempt an item pre-merge 
| and when not to, however, in certain cases, some mods rely on items not being 
| pre-merged and actually spawned, in which case, the items will flow right through 
| without being merged.

* **Type:** ``boolean``
* **Default:** ``true``

**enchantment-helper-leak-fix**

| If 'true', provides a fix for possible leaks through
| Minecraft's enchantment helper code that can leak
| entity and world references without much interaction
| Forge native (so when running SpongeForge implementation)
| has a similar patch, but Sponge's patch works a little harder
| at it, but Vanilla (SpongeVanilla implementation) does NOT
| have any of the patch, leading to the recommendation that this
| patch is enabled "for sure" when using SpongeVanilla implementation.
| See https://bugs.mojang.com/browse/MC-128547 for more information.

* **Type:** ``boolean``
* **Default:** ``true``

**faster-thread-checks**

| If 'true', allows for Sponge to make better assumptinos on single threaded
| operations with relation to various checks for server threaded operations.
| This is default to true due to Sponge being able to precisely inject when
| the server thread is available. This should make an already fast operation
| much faster for better thread checks to ensure stability of sponge's systems.

* **Type:** ``boolean``
* **Default:** ``true``

**map-optimization**

| If 'true', re-writes the incredibly inefficient Vanilla Map code.
| This yields enormous performance enhancements when using many maps, but has a tiny chance of breaking mods that invasively modify Vanilla.It is strongly reccomended to keep this on, unless explicitly advised otherwise by a Sponge developer

* **Type:** ``boolean``
* **Default:** ``true``

**panda-redstone**

| If 'true', uses Panda4494's redstone implementation which improves performance. 
| See https://bugs.mojang.com/browse/MC-11193 for more information. 
| Note: This optimization has a few issues which are explained in the bug report.

* **Type:** ``boolean``
* **Default:** ``false``

**structure-saving**

| Handles structures that are saved to disk. Certain structures can take up large amounts 
| of disk space for very large maps and the data for these structures is only needed while the 
| world around them is generating. Disabling saving of these structures can save disk space and 
| time during saves if your world is already fully generated. 
| Warning: disabling structure saving will break the vanilla locate command.

* **Type:** ``StructureSaveCategory``

PlayerBlockTracker
==================

**block-blacklist**

| Block IDs that will be blacklisted for player block placement tracking.

* **Type:** ``List<String>``

**enabled**

| If 'true', adds player tracking support for block positions. 
| Note: This should only be disabled if you do not care who caused a block to change.

* **Type:** ``boolean``
* **Default:** ``true``

SpawnerCategory
===============

**spawn-limit-ambient**

| The number of ambients the spawner can potentially spawn around a player.

* **Type:** ``int``
* **Default:** ``15``

**spawn-limit-animal**

| The number of animals the spawner can potentially spawn around a player.

* **Type:** ``int``
* **Default:** ``15``

**spawn-limit-aquatic**

| The number of aquatics the spawner can potentially spawn around a player.

* **Type:** ``int``
* **Default:** ``5``

**spawn-limit-monster**

| The number of monsters the spawner can potentially spawn around a player.

* **Type:** ``int``
* **Default:** ``70``

**tick-rate-ambient**

| The ambient spawning tick rate. Default: 400

* **Type:** ``int``
* **Default:** ``400``

**tick-rate-animal**

| The animal spawning tick rate. Default: 400

* **Type:** ``int``
* **Default:** ``400``

**tick-rate-aquatic**

| The aquatic spawning tick rate. Default: 400

* **Type:** ``int``
* **Default:** ``400``

**tick-rate-monster**

| The monster spawning tick rate. Default: 1

* **Type:** ``int``
* **Default:** ``1``

SqlCategory
===========

**aliases**

| Aliases for SQL connections, in the format jdbc:protocol://[username[:password]@]host/database

* **Type:** ``Map<String, String>``

TeleportHelperCategory
======================

**force-blacklist**

| If 'true', this blacklist will always be respected, otherwise, plugins can choose whether 
| or not to respect it.

* **Type:** ``boolean``
* **Default:** ``false``

**unsafe-body-block-ids**

| Block IDs that are listed here will not be selected by Sponge's safe teleport routine as 
| a safe block for players to warp into. 
| You should only list blocks here that are incorrectly selected, solid blocks that prevent 
| movement are automatically excluded.

* **Type:** ``List<String>``

**unsafe-floor-block-ids**

| Block IDs that are listed here will not be selected by Sponge's safe 
| teleport routine as a safe floor block.

* **Type:** ``List<String>``

TileEntityActivationCategory
============================

**auto-populate**

| If 'true', newly discovered tileentities will be added to this config with default settings.

* **Type:** ``boolean``
* **Default:** ``false``

**default-block-range**

| Default activation block range used for all tileentities unless overridden.

* **Type:** ``int``
* **Default:** ``64``

**default-tick-rate**

| Default tick rate used for all tileentities unless overridden.

* **Type:** ``int``
* **Default:** ``1``

**mods**

| Per-mod overrides. Refer to the minecraft default mod for example.

* **Type:** ``Map<String, TileEntityActivationModCategory>``

TimingsCategory
===============

**enabled**

* **Type:** ``boolean``
* **Default:** ``true``

**hidden-config-entries**

* **Type:** ``List<String>``

**history-interval**

* **Type:** ``int``
* **Default:** ``300``

**history-length**

* **Type:** ``int``
* **Default:** ``3600``

**server-name-privacy**

* **Type:** ``boolean``
* **Default:** ``false``

**verbose**

* **Type:** ``boolean``
* **Default:** ``false``

GlobalWorldCategory
===================

**auto-player-save-interval**

| The auto-save tick interval used when saving global player data. (Default: 900) 
| Note: 20 ticks is equivalent to 1 second. Set to 0 to disable.

* **Type:** ``int``
* **Default:** ``900``

**auto-save-interval**

| The auto-save tick interval used to save all loaded chunks in a world. 
| Set to 0 to disable. (Default: 900) 
| Note: 20 ticks is equivalent to 1 second.

* **Type:** ``int``
* **Default:** ``900``

**chunk-gc-load-threshold**

| The number of newly loaded chunks before triggering a forced cleanup. 
| Note: When triggered, the loaded chunk threshold will reset and start incrementing. 
| Disabled by default.

* **Type:** ``int``
* **Default:** ``0``

**chunk-gc-tick-interval**

| The tick interval used to cleanup all inactive chunks that have leaked in a world. 
| Set to 0 to disable which restores vanilla handling. (Default: 600)

* **Type:** ``int``
* **Default:** ``600``

**chunk-unload-delay**

| The number of seconds to delay a chunk unload once marked inactive. (Default: 15) 
| Note: This gets reset if the chunk becomes active again.

* **Type:** ``int``
* **Default:** ``15``

**deny-chunk-requests**

| If 'true', any request for a chunk not currently loaded will be denied (exceptions apply 
| for things like world gen and player movement). 
| Warning: As this is an experimental setting for performance gain, if you encounter any issues 
| then we recommend disabling it.

* **Type:** ``boolean``
* **Default:** ``true``

**flowing-lava-decay**

| Lava behaves like vanilla water when source block is removed

* **Type:** ``boolean``
* **Default:** ``false``

**gameprofile-lookup-batch-size**

| The amount of GameProfile requests to make against Mojang's session server. (Default: 1) 
| Note: Mojang accepts a maximum of 600 requests every 10 minutes from a single IP address. 
| If you are running multiple servers behind the same IP, it is recommended to raise the 'gameprofile-task-interval' setting  
| in order to compensate for the amount requests being sent. 
| Finally, if set to 0 or less, the default batch size will be used. 
| For more information visit http://wiki.vg/Mojang_API

* **Type:** ``int``
* **Default:** ``1``

**gameprofile-lookup-task-interval**

| The interval, in seconds, used by the GameProfileQueryTask to process queued GameProfile requests. (Default: 4) 
| Note: This setting should be raised if you experience the following error: 
| "The client has sent too many requests within a certain amount of time". 
| Finally, if set to 0 or less, the default interval will be used.

* **Type:** ``int``
* **Default:** ``4``

**generate-spawn-on-load**

| If 'true', this world will generate its spawn the moment its loaded.

* **Type:** ``Boolean``

**infinite-water-source**

| Vanilla water source behavior - is infinite

* **Type:** ``boolean``
* **Default:** ``false``

**invalid-lookup-uuids**

| The list of uuid's that should never perform a lookup against Mojang's session server. 
| Note: If you are using SpongeForge, make sure to enter any mod fake player's UUID to this list.

* **Type:** ``List<UUID>``

**item-merge-radius**

| The defined merge radius for Item entities such that when two items are 
| within the defined radius of each other, they will attempt to merge. Usually, 
| the default radius is set to 0.5 in Vanilla, however, for performance reasons 
| 2.5 is generally acceptable. 
| Note: Increasing the radius higher will likely cause performance degradation 
| with larger amount of items as they attempt to merge and search nearby 
| areas for more items. Setting to a negative value is not supported!

* **Type:** ``double``
* **Default:** ``2.5``

**keep-spawn-loaded**

| If 'true', this worlds spawn will remain loaded with no players.

* **Type:** ``Boolean``
* **Default:** ``true``

**leaf-decay**

| If 'true', natural leaf decay is allowed.

* **Type:** ``boolean``
* **Default:** ``true``

**load-on-startup**

| If 'true', this world will load on startup.

* **Type:** ``Boolean``
* **Default:** ``true``

**max-chunk-unloads-per-tick**

| The maximum number of queued unloaded chunks that will be unloaded in a single tick. 
| Note: With the chunk gc enabled, this setting only applies to the ticks 
| where the gc runs (controlled by 'chunk-gc-tick-interval') 
| Note: If the maximum unloads is too low, too many chunks may remain 
| loaded on the world and increases the chance for a drop in tps. (Default: 100)

* **Type:** ``int``
* **Default:** ``100``

**mob-spawn-range**

| Specifies the radius (in chunks) of where creatures will spawn. 
| This value is capped to the current view distance setting in server.properties

* **Type:** ``int``
* **Default:** ``4``

**portal-agents**

| A list of all detected portal agents used in this world. 
| In order to override, change the target world name to any other valid world. 
| Note: If world is not found, it will fallback to default.

* **Type:** ``Map<String, String>``

**pvp-enabled**

| If 'true', this world will allow PVP combat.

* **Type:** ``boolean``
* **Default:** ``true``

**view-distance**

| Override world distance per world/dimension 
| The value must be greater than or equal to 3 and less than or equal to 32 
| The server-wide view distance will be used when the value is -1.

* **Type:** ``int``
* **Default:** ``-1``

**weather-ice-and-snow**

| If 'true', natural formation of ice and snow in supported biomes will be allowed.

* **Type:** ``boolean``
* **Default:** ``true``

**weather-thunder**

| If 'true', thunderstorms will be initiated in supported biomes.

* **Type:** ``boolean``
* **Default:** ``true``

**world-enabled**

| If 'true', this world will be registered.

* **Type:** ``boolean``
* **Default:** ``true``



------------------------------------------------------------------------------------------------------------

This config was generated using SpongeForge recommendation 5 (SpongeForge 3554 with Forge 2768), SpongeAPI version 7.1:

.. code-block:: guess

    # 1.0
    #
    # # If you need help with the configuration or have any questions related to Sponge,
    # # join us at the IRC, Discord, or drop by our forums and leave a post.
    #
    # # IRC: #sponge @ irc.esper.net ( https://webchat.esper.net/?channel=sponge )
    # # Discord: (https://discord.gg/sponge)
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
            max-block-processing-depth=100
            # If verbose is not enabled, this restricts the amount of
            # runaway phase state printouts, usually happens on a server
            # where a PhaseState is not completing. Although rare, it should
            # never happen, but when it does, sometimes it can continuously print
            # more and more. This attempts to placate that while a fix can be worked on
            # to resolve the runaway. If verbose is enabled, they will always print.
            maximum-printed-runaway-counts=3
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
            # Patches the specified commands to respect the world of the sender instead of applying the
            # changes on the all worlds.
            multi-world-patches {}
        }
        debug {
            # Detect and prevent parts of PlayerChunkMap being called off the main thread.
            # This may decrease sever preformance, so you should only enable it when debugging a specific issue.
            concurrent-chunk-map-checks=false
            # Detect and prevent certain attempts to use entities concurrently.
            # WARNING: May drastically decrease server performance. Only set this to 'true' to debug a pre-existing issue.
            concurrent-entity-checks=false
            # Dump chunks in the event of a deadlock
            dump-chunks-on-deadlock=false
            # Dump the heap in the event of a deadlock
            dump-heap-on-deadlock=false
            # Dump the server thread on deadlock warning
            dump-threads-on-warn=false
            # If 'true', Java's thread contention monitoring for thread dumps is enabled.
            thread-contention-monitoring=false
        }
        entity {
            # Number of colliding entities in one spot before logging a warning. Set to 0 to disable
            collision-warn-size=200
            # Number of entities in one dimension before logging a warning. Set to 0 to disable
            count-warn-size=0
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
            # Default maximum collisions used for all entities/blocks unless overridden.
            defaults {
                blocks=8
                entities=8
            }
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
                        botaniacorporeaspark=-1
                        botaniaspark=-1
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
            limit-book-size=true
            # Enables focing a chunk load when an entity position
            # is set. Usually due to teleportation, vehicle movement
            # etc. can a position lead an entity to no longer exist
            # within it's currently marked and tracked chunk. This will
            # enable that chunk for the position is loaded. Part of several
            # exploits.See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0335-Ensure-chunks-are-always-loaded-on-hard-position-set.patch
            load-chunk-on-position-set=true
            # Enables forcing chunks to save when an entity is added
            # or removed from said chunk. This is a partial fix for
            # some exploits using vehicles.See https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0306-Mark-chunk-dirty-anytime-entities-change-to-guarante.patch
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
            sync-player-positions-for-vehicle-movement=true
            # Enables forcing a chunk-tracking refresh on entity movement.
            # This enables a guarantee that the entity is tracked in the
            # proper chunk when moving.https://github.com/PaperMC/Paper/blob/fd1bd5223a461b6d98280bb8f2d67280a30dd24a/Spigot-Server-Patches/0315-Always-process-chunk-registration-after-moving.patch
            update-tracked-chunk-on-entity-move=true
        }
        general {
            # The directory for Sponge plugin configurations, relative to the
            # execution root or specified as an absolute path.
            # Note that the default: "${CANONICAL_GAME_DIR}/config"
            # is going to use the "plugins" directory in the root game directory.
            # If you wish for plugin configs to reside within a child of the configuration
            # directory, change the value to, for example, "${CANONICAL_CONFIG_DIR}/sponge/plugins".
            # Note: It is not recommended to set this to "${CANONICAL_CONFIG_DIR}/sponge", as there is
            # a possibility that plugin configurations can conflict the Sponge core configurations.
            #
            config-dir="${CANONICAL_GAME_DIR}/config"
            # Disable warning messages to server admins
            disable-warnings=false
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
            # Determines whether plugins that are newly added are allowed to perform
            # data/metric collection by default. Plugins detected by Sponge will be added to the "plugin-permissions" section with this value.
            #
            # Set to true to enable metric gathering by default, false otherwise.
            default-permission=false
            # Provides (or revokes) permission for metric gathering on a per plugin basis.
            # Entries should be in the format "plugin-id=<true|false>".
            #
            # Deleting an entry from this list will reset it to the default specified in
            # "default-permission"
            plugin-permissions {
                docsconfiglister=false
            }
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
            # If 'true', block item drops are pre-processed to avoid
            # having to spawn extra entities that will be merged post spawning.
            # Usually, Sponge is smart enough to determine when to attempt an item pre-merge
            # and when not to, however, in certain cases, some mods rely on items not being
            # pre-merged and actually spawned, in which case, the items will flow right through
            # without being merged.
            drops-pre-merge=false
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
            # If 'true', uses Panda4494's redstone implementation which improves performance.
            # See https://bugs.mojang.com/browse/MC-11193 for more information.
            # Note: This optimization has a few issues which are explained in the bug report.
            panda-redstone=false
            # Handles structures that are saved to disk. Certain structures can take up large amounts
            # of disk space for very large maps and the data for these structures is only needed while the
            # world around them is generating. Disabling saving of these structures can save disk space and
            # time during saves if your world is already fully generated.
            # Warning: disabling structure saving will break the vanilla locate command.
            structure-saving {
                # If 'true', newly discovered structures will be added to this config with a default value.
                auto-populate=false
                enabled=false
                # Per-mod overrides. Refer to the minecraft default mod for example.
                mods {
                    minecraft {
                        # If 'false', this mod will never save its structures.
                        enabled=true
                        structures {
                            mineshaft=false
                        }
                    }
                }
            }
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
            # The aquatic spawning tick rate. Default: 400
            tick-rate-aquatic=400
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
            # Lava behaves like vanilla water when source block is removed
            flowing-lava-decay=false
            # The amount of GameProfile requests to make against Mojang's session server. (Default: 1)
            # Note: Mojang accepts a maximum of 600 requests every 10 minutes from a single IP address.
            # If you are running multiple servers behind the same IP, it is recommended to raise the 'gameprofile-task-interval' setting
            # in order to compensate for the amount requests being sent.
            # Finally, if set to 0 or less, the default batch size will be used.
            # For more information visit https://wiki.vg/Mojang_API
            gameprofile-lookup-batch-size=1
            # The interval, in seconds, used by the GameProfileQueryTask to process queued GameProfile requests. (Default: 4)
            # Note: This setting should be raised if you experience the following error:
            # "The client has sent too many requests within a certain amount of time".
            # Finally, if set to 0 or less, the default interval will be used.
            gameprofile-lookup-task-interval=4
            # If 'true', this world will generate its spawn the moment its loaded.
            generate-spawn-on-load=false
            # Vanilla water source behavior - is infinite
            infinite-water-source=false
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
    }
