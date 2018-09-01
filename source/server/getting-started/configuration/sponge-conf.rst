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

Global Properties of Sponge
~~~~~~~~~~~~~~~~~~~~~~~~~~~

block-capturing
===============

auto-populate
    If 'true', newly discovered blocks will be added to this config with a default value.
mods
    Per-mod block id mappings for controlling capturing behavior
block-tick-capturing
    If 'true', individual capturing (i.e. skip bulk capturing) for scheduled ticks for
    a block type will be performed.
enabled
    If 'false', all specific rules for this mod will be ignored.
block-tick-capturing
    If 'true', individual capturing (i.e. skip bulk capturing) for scheduled ticks for
    a block type will be performed.

block-tracking
==============

block-blacklist
    Block IDs that will be blacklisted for player block placement tracking.
enabled
    If 'true', adds player tracking support for block positions.
    Note: This should only be disabled if you do not care who caused a block to change.

bungeecord
==========

ip-forwarding
    If 'true', allows BungeeCord to forward IP address, UUID, and Game Profile to this server.

cause-tracker
=============

capture-async-spawning-entities
    If set to 'true', when a mod or plugin attempts to spawn an entity
    off the main server thread, Sponge will automatically
    capture said entity to spawn it properly on the main
    server thread. The catch to this is that some mods are
    not considering the consequences of spawning an entity
    off the server thread, and are unaware of potential race
    conditions they may cause. If this is set to false,
    Sponge will politely ignore the entity being spawned,
    and emit a warning about said spawn anyways.
generate-stacktrace-per-phase
    If 'true', more thorough debugging for PhaseStates
    such that a StackTrace is created every time a PhaseState
    switches, allowing for more fine grained troubleshooting
    in the cases of runaway phase states. Note that this is
    not extremely performant and may have some associated costs
    with generating the stack traces constantly.
maximum-printed-runaway-counts
    If verbose is not enabled, this restricts the amount of
    runaway phase state printouts, usually happens on a server
    where a PhaseState is not completing. Although rare, it should
    never happen, but when it does, sometimes it can continuously print
    more and more. This attempts to placate that while a fix can be worked on
    to resolve the runaway. If verbose is enabled, they will always print.
verbose
    If 'true', the phase tracker will print out when there are too many phases
    being entered, usually considered as an issue of phase re-entrance and
    indicates an unexpected issue of tracking phases not to complete.
    If this is not reported yet, please report to Sponge. If it has been
    reported, you may disable this.
verbose-errors
    If 'true', the phase tracker will dump extra information about the current phases
    when certain non-PhaseTracker related exceptions occur. This is usually not necessary, as the information
    in the exception itself can normally be used to determine the cause of the issue

commands
========

aliases
    Command aliases will resolve conflicts when multiple plugins request a specific command,
    Correct syntax is <unqualified command>=<plugin name> e.g. "sethome=homeplugin"
multi-world-patches
    Patches the specified commands to respect the world of the sender instead of applying the
    changes on the all worlds.
config-enabled
    This setting does nothing in the global config. In dimension/world configs, it allows the config
    to override config(s) that it inherits from

debug
=====

concurrent-entity-checks
    Detect and prevent certain attempts to use entities concurrently.
    WARNING: May drastically decrease server performance. Only set this to 'true' to debug a pre-existing issue.
dump-chunks-on-deadlock
    Dump chunks in the event of a deadlock
dump-heap-on-deadlock
    Dump the heap in the event of a deadlock
dump-threads-on-warn
    Dump the server thread on deadlock warning
thread-contention-monitoring
    If 'true', Java's thread contention monitoring for thread dumps is enabled.

entity
======

collision-warn-size
    Number of colliding entities in one spot before logging a warning. Set to 0 to disable
count-warn-size
    Number of entities in one dimension before logging a warning. Set to 0 to disable
entity-painting-respawn-delay
    Number of ticks before a painting is respawned on clients when their art is changed
human-player-list-remove-delay
    Number of ticks before the fake player entry of a human is removed from the tab list (range of 0 to 100 ticks).
item-despawn-rate
    Controls the time in ticks for when an item despawns.
living-hard-despawn-range
    The upper bounded range where living entities farther from a player will likely despawn
living-soft-despawn-minimum-life
    The amount of seconds before a living entity between the soft and hard despawn ranges from a player to be considered for despawning
living-soft-despawn-range
    The lower bounded range where living entities near a player may potentially despawn
max-bounding-box-size
    Maximum size of an entity's bounding box before removing it. Set to 0 to disable
max-speed
    Square of the maximum speed of an entity before removing it. Set to 0 to disable

entity-activation-range
=======================

auto-populate
    If 'true', newly discovered entities will be added to this config with a default value.
defaults
    Default activation ranges used for all entities unless overridden.
mods
    Per-mod overrides. Refer to the minecraft default mod for example.

entity-collisions
=================

auto-populate
    If 'true', newly discovered entities/blocks will be added to this config with a default value.
defaults
    Default maximum collisions used for all entities/blocks unless overridden.
max-entities-within-aabb
    Maximum amount of entities any given entity or block can collide with. This improves
    performance when there are more than 8 entities on top of each other such as a 1x1
    spawn pen. Set to 0 to disable.
mods
    Per-mod overrides. Refer to the minecraft default mod for example.
defaults
    Default maximum collisions used for all entities/blocks unless overridden.
enabled
    If 'false', entity collision rules for this mod will be ignored.
defaults
    Default maximum collisions used for all entities/blocks unless overridden.
enabled
    If 'false', entity collision rules for this mod will be ignored.

exploits
========

prevent-creative-itemstack-name-exploit
    Prevents an exploit in which the client sends a packet with the
    itemstack name exceeding the string limit.
prevent-sign-command-exploit
    Prevents an exploit in which the client sends a packet to update a sign containing
    commands from a player without permission.

general
=======

config-dir
    The directory for Sponge plugin configurations, relative to the
    execution root or specified as an absolute path.
    Note that the default: "${CANONICAL_GAME_DIR}/config"
    is going to use the "plugins" directory in the root game directory.
    If you wish for plugin configs to reside within a child of the configuration
    directory, change the value to, for example, "${CANONICAL_CONFIG_DIR}/sponge/plugins".
    Note: It is not recommended to set this to "${CANONICAL_CONFIG_DIR}/sponge", as there is
    a possibility that plugin configurations can conflict the Sponge core configurations.
disable-warnings
    Disable warning messages to server admins
file-io-thread-sleep
    If 'true', sleeping between chunk saves will be enabled, beware of memory issues.
plugins-dir
    Additional directory to search for plugins, relative to the
    execution root or specified as an absolute path.
    Note that the default: "${CANONICAL_MODS_DIR}/plugins"
    is going to search for a plugins folder in the mods directory.
    If you wish for the plugins folder to reside in the root game
    directory, change the value to "${CANONICAL_GAME_DIR}/plugins".

logging
=======

block-break
    Log when blocks are broken
block-modify
    Log when blocks are modified
block-place
    Log when blocks are placed
block-populate
    Log when blocks are populated in a chunk
block-tracking
    Log when blocks are placed by players and tracked
chunk-gc-queue-unload
    Log when chunks are queued to be unloaded by the chunk garbage collector.
chunk-load
    Log when chunks are loaded
chunk-unload
    Log when chunks are unloaded
entity-collision-checks
    Whether to log entity collision/count checks
entity-death
    Log when living entities are destroyed
entity-despawn
    Log when living entities are despawned
entity-spawn
    Log when living entities are spawned
entity-speed-removal
    Whether to log entity removals due to speed
exploit-itemstack-name-overflow
    Log when server receives exploited packet with itemstack name exceeding string limit.
exploit-respawn-invisibility
    Log when player attempts to respawn invisible to surrounding players.
exploit-sign-command-updates
    Log when server receives exploited packet to update a sign containing commands from player with no permission.
log-stacktraces
    Add stack traces to dev logging
world-auto-save
    Log when a world auto-saves its chunk data. Note: This may be spammy depending on the auto-save-interval configured for world.

metrics-collection
==================

default-permission
    Determines whether newly added plugins can collect server metrics by default
plugin-permissions
    Per-plugin toggles indicating whether a plugin is allowed to collect server metrics

modules
=======

movement-checks
    Allows configuring Vanilla movement and speed checks
realtime
    Use real (wall) time instead of ticks as much as possible
tileentity-activation
    Controls block range and tick rate of tileentities.
    Use with caution as this can break intended functionality.

movement-checks
===============

moved-wrongly
    Controls whether the 'player/entity moved wrongly!' check will be enforced
player-moved-too-quickly
    Controls whether the 'player moved too quickly!' check will be enforced
player-vehicle-moved-too-quickly
    Controls whether the 'vehicle of player moved too quickly!' check will be enforced

optimizations
=============

async-lighting
    Runs lighting updates asynchronously.
enabled
    If 'true', lighting updates are run asynchronously.
num-threads
    The amount of threads to dedicate for asynchronous lighting updates. (Default: 2)
cache-tameable-owners
    Caches tameable entities owners to avoid constant lookups against data watchers. If mods
    cause issues, disable this.
drops-pre-merge
    If 'true', block item drops are pre-processed to avoid
    having to spawn extra entities that will be merged post spawning.
    Usually, Sponge is smart enough to determine when to attempt an item pre-merge
    and when not to, however, in certain cases, some mods rely on items not being
    pre-merged and actually spawned, in which case, the items will flow right through
    without being merged.
enchantment-helper-leak-fix
    If 'true', provides a fix for possible leaks through
    Minecraft's enchantment helper code that can leak
    entity and world references without much interaction
    Forge native (so when running SpongeForge implementation)
    has a similar patch, but Sponge's patch works a little harder
    at it, but Vanilla (SpongeVanilla implementation) does NOT
    have any of the patch, leading to the recommendation that this
    patch is enabled "for sure" when using SpongeVanilla implementation.
    See https://bugs.mojang.com/browse/MC-128547 for more information.
panda-redstone
    If 'true', uses Panda4494's redstone implementation which improves performance.
    See https://bugs.mojang.com/browse/MC-11193 for more information.
    Note: This optimization has a few issues which are explained in the bug report.
structure-saving
    Handles structures that are saved to disk. Certain structures can take up large amounts
    of disk space for very large maps and the data for these structures is only needed while the
    world around them is generating. Disabling saving of these structures can save disk space and
    time during saves if your world is already fully generated.
    Warning: disabling structure saving will break the vanilla locate command.
auto-populate
    If 'true', newly discovered structures will be added to this config with a default value.
mods
    Per-mod overrides. Refer to the minecraft default mod for example.
enabled
    If 'false', this mod will never save its structures.
spawner
    Used to control spawn limits around players.
    Note: The radius uses the lower value of mob spawn range and server's view distance.

spawner
=======

spawn-limit-ambient
    The number of ambients the spawner can potentially spawn around a player.
spawn-limit-animal
    The number of animals the spawner can potentially spawn around a player.
spawn-limit-aquatic
    The number of aquatics the spawner can potentially spawn around a player.
spawn-limit-monster
    The number of monsters the spawner can potentially spawn around a player.
tick-rate-ambient
    The ambient spawning tick rate. Default: 400
tick-rate-animal
    The animal spawning tick rate. Default: 400
tick-rate-aquatic
    The aquatic spawning tick rate. Default: 400
tick-rate-monster
    The monster spawning tick rate. Default: 1
sql
    Configuration options related to the Sql service, including connection aliases etc

sql
===

aliases
    Aliases for SQL connections, in the format jdbc:protocol://[username[:password]@]host/database
teleport-helper
    Blocks to blacklist for safe teleportation.

teleport-helper
===============

force-blacklist
    If 'true', this blacklist will always be respected, otherwise, plugins can choose whether
    or not to respect it.
unsafe-body-block-ids
    Block IDs that are listed here will not be selected by Sponge's safe teleport routine as
    a safe block for players to warp into.
    You should only list blocks here that are incorrectly selected, solid blocks that prevent
    movement are automatically excluded.
unsafe-floor-block-ids
    Block IDs that are listed here will not be selected by Sponge's safe
    teleport routine as a safe floor block.

tileentity-activation
=====================

auto-populate
    If 'true', newly discovered tileentities will be added to this config with default settings.
default-block-range
    Default activation block range used for all tileentities unless overridden.
default-tick-rate
    Default tick rate used for all tileentities unless overridden.
mods
    Per-mod overrides. Refer to the minecraft default mod for example.

timings
=======


world
=====

auto-player-save-interval
    The auto-save tick interval used when saving global player data. (Default: 900)
    Note: 20 ticks is equivalent to 1 second. Set to 0 to disable.
auto-save-interval
    The auto-save tick interval used to save all loaded chunks in a world.
    Set to 0 to disable. (Default: 900)
    Note: 20 ticks is equivalent to 1 second.
chunk-gc-load-threshold
    The number of newly loaded chunks before triggering a forced cleanup.
    Note: When triggered, the loaded chunk threshold will reset and start incrementing.
    Disabled by default.
chunk-gc-tick-interval
    The tick interval used to cleanup all inactive chunks that have leaked in a world.
    Set to 0 to disable which restores vanilla handling. (Default: 600)
chunk-unload-delay
    The number of seconds to delay a chunk unload once marked inactive. (Default: 15)
    Note: This gets reset if the chunk becomes active again.
deny-chunk-requests
    If 'true', any request for a chunk not currently loaded will be denied (exceptions apply
    for things like world gen and player movement).
    Warning: As this is an experimental setting for performance gain, if you encounter any issues
    then we recommend disabling it.
flowing-lava-decay
    Lava behaves like vanilla water when source block is removed
gameprofile-lookup-batch-size
    The amount of GameProfile requests to make against Mojang's session server. (Default: 1)
    Note: Mojang accepts a maximum of 600 requests every 10 minutes from a single IP address.
    If you are running multiple servers behind the same IP, it is recommended to raise the 'gameprofile-task-interval' setting
    in order to compensate for the amount requests being sent.
    Finally, if set to 0 or less, the default batch size will be used.
    For more information visit http://wiki.vg/Mojang_API
gameprofile-lookup-task-interval
    The interval, in seconds, used by the GameProfileQueryTask to process queued GameProfile requests. (Default: 4)
    Note: This setting should be raised if you experience the following error:
    "The client has sent too many requests within a certain amount of time".
    Finally, if set to 0 or less, the default interval will be used.
generate-spawn-on-load
    If 'true', this world will generate its spawn the moment its loaded.
infinite-water-source
    Vanilla water source behavior - is infinite
invalid-lookup-uuids
    The list of uuid's that should never perform a lookup against Mojang's session server.
    Note: If you are using SpongeForge, make sure to enter any mod fake player's UUID to this list.
item-merge-radius
    The defined merge radius for Item entities such that when two items are
    within the defined radius of each other, they will attempt to merge. Usually,
    the default radius is set to 0.5 in Vanilla, however, for performance reasons
    2.5 is generally acceptable.
    Note: Increasing the radius higher will likely cause performance degradation
    with larger amount of items as they attempt to merge and search nearby
    areas for more items. Setting to a negative value is not supported!
keep-spawn-loaded
    If 'true', this worlds spawn will remain loaded with no players.
leaf-decay
    If 'true', natural leaf decay is allowed.
load-on-startup
    If 'true', this world will load on startup.
max-chunk-unloads-per-tick
    The maximum number of queued unloaded chunks that will be unloaded in a single tick.
    Note: With the chunk gc enabled, this setting only applies to the ticks
    where the gc runs (controlled by 'chunk-gc-tick-interval')
    Note: If the maximum unloads is too low, too many chunks may remain
    loaded on the world and increases the chance for a drop in tps. (Default: 100)
mob-spawn-range
    Specifies the radius (in chunks) of where creatures will spawn.
    This value is capped to the current view distance setting in server.properties
portal-agents
    A list of all detected portal agents used in this world.
    In order to override, change the target world name to any other valid world.
    Note: If world is not found, it will fallback to default.
pvp-enabled
    If 'true', this world will allow PVP combat.
view-distance
    Override world distance per world/dimension
    The value must be greater than or equal to 3 and less than or equal to 32
    The server-wide view distance will be used when the value is -1.
weather-ice-and-snow
    If 'true', natural formation of ice and snow in supported biomes will be allowed.
weather-thunder
    If 'true', thunderstorms will be initiated in supported biomes.
world-enabled
    If 'true', this world will be registered.

------------------------------------------------------------------------------------------------------------

This config was generated using SpongeForge build 2990 (with Forge 2611), SpongeAPI version 7.0.0:

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
        block-capturing {
            # If 'true', newly discovered blocks will be added to this config with a default value.
            auto-populate=false
            # Per-mod block id mappings for controlling capturing behavior
            mods {
                extrautils2 {
                    # If 'true', individual capturing (i.e. skip bulk capturing) for scheduled ticks for
                    # a block type will be performed.
                    block-tick-capturing {
                        redstoneclock=true
                    }
                    # If 'false', all specific rules for this mod will be ignored.
                    enabled=true
                }
            }
        }
        block-tracking {
            # Block IDs that will be blacklisted for player block placement tracking.
            block-blacklist=[]
            # If 'true', adds player tracking support for block positions.
            # Note: This should only be disabled if you do not care who caused a block to change.
            enabled=true
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
            multi-world-patches {
                defaultgamemode=true
                difficulty=true
                gamerule=true
                seed=true
                setdefaultspawnpoint=true
                time=true
                toggledownfall=true
                weather=true
                worldborder=true
            }
        }
        # This setting does nothing in the global config. In dimension/world configs, it allows the config
        # to override config(s) that it inherits from
        config-enabled=false
        debug {
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
            # Prevents an exploit in which the client sends a packet with the
            # itemstack name exceeding the string limit.
            prevent-creative-itemstack-name-exploit=true
            # Prevents an exploit in which the client sends a packet to update a sign containing
            # commands from a player without permission.
            prevent-sign-command-exploit=true
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
        modules {
            block-capturing-control=true
            bungeecord=false
            entity-activation-range=true
            entity-collisions=true
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
            verbose=true
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
            # For more information visit http://wiki.vg/Mojang_API
            gameprofile-lookup-batch-size=1
            # The interval, in seconds, used by the GameProfileQueryTask to process queued GameProfile requests. (Default: 4)
            # Note: This setting should be raised if you experience the following error:
            # "The client has sent too many requests within a certain amount of time".
            # Finally, if set to 0 or less, the default interval will be used.
            gameprofile-lookup-task-interval=4
            # If 'true', this world will generate its spawn the moment its loaded.
            generate-spawn-on-load=null
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
            keep-spawn-loaded=null
            # If 'true', natural leaf decay is allowed.
            leaf-decay=true
            # If 'true', this world will load on startup.
            load-on-startup=null
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
                "minecraft:default_nether"=DIM-1
                "minecraft:default_the_end"=DIM1
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
