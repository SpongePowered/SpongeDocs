===========
global.conf
===========

Global Configuration
~~~~~~~~~~~~~~~~~~~~

The global.conf file contains the global configuration settings for Sponge. This file is created in the config/sponge
directory in your server folder. Many of these properties can be also overridden per-world or per-dimension type by
using the config files in the subfolders of config/worlds.

Below is a complete global.conf file with all possible nodes that may be present on a server. Note that certain
sections will not be present immediately, and will be added to the file when the server encounters them. This config
was generated using SpongeForge build 1399, SpongeAPI version 4.1:

.. code-block:: none

    # 1.0
    # 
    # # If you need help with the configuration or have any questions related to Sponge,
    # # join us at the IRC or drop by our forums and leave a post.
    # 
    # # IRC: #sponge @ irc.esper.net ( http://webchat.esper.net/?channel=sponge )
    # # Forums: https://forums.spongepowered.org/
    # 

    sponge {
        block-tracking {
            # Add block ids you wish to blacklist for player block placement tracking.
            block-blacklist=[]
            # If enabled, adds player tracking support for block positions. Note: This should only be disabled if you do not care who caused a block to change.
            enabled=true
        }
        bungeecord {
            # If enabled, allows BungeeCord to forward IP address, UUID, and Game Profile to this server
            ip-forwarding=false
        }
        commands {
            # A mapping from unqualified command alias to plugin id of the plugin that should handle a certain command
            aliases {}
        }
        debug {
            # Dump chunks in the event of a deadlock
            dump-chunks-on-deadlock=false
            # Dump the heap in the event of a deadlock
            dump-heap-on-deadlock=false
            # Dump the server thread on deadlock warning
            dump-threads-on-warn=false
            # Enable Java's thread contention monitoring for thread dumps
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
            # Max size of an entity's bounding box before removing it. Set to 0 to disable
            max-bounding-box-size=1000
            # Square of the max speed of an entity before removing it. Set to 0 to disable
            max-speed=100
        }
        entity-activation-range {
            # If enabled, newly discovered entities will be added to this config with a default value.
            auto-populate=false
            # Default activation ranges used for all entities unless overidden.
            defaults {
                ambient=32
                aquatic=32
                creature=32
                misc=16
                monster=32
            }
            # Per-mod overrides. Refer to the Minecraft default mod for example.
            mods {}
        }
        entity-collisions {
            # If enabled, newly discovered entities/blocks will be added to this config with a default value.
            auto-populate=false
            # Default max collisions used for all entities/blocks unless overidden.
            defaults {}
            # Max amount of entities any given entity or block can collide with. This improves performance when there are more than 8 entities on top of eachother such as a 1x1 spawn pen. Set to 0 to disable.
            max-entities-within-aabb=8
            # Per-mod overrides. Refer to the Minecraft default mod for example.
            mods {
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
                    # Default max collisions used for all entities/blocks unless overidden.
                    defaults {
                        blocks=8
                        entities=8
                    }
                    # Set to false if you want mod to ignore entity collision rules.
                    enabled=true
                    entities {
                        thrownpotion=-1
                    }
                }
            }
        }
        exploits {
            prevent-creative-itemstack-name-exploit=true
            prevent-sign-command-exploit=true
        }
        general {
            # Forces Chunk Loading on provide requests (speedup for mods that don't check if a chunk is loaded)
            chunk-load-override=false
            # Disable warning messages to server admins
            disable-warnings=false
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
            bungeecord=false
            entity-activation-range=true
            entity-collisions=true
            exploits=true
            optimizations=true
            timings=true
        }
        optimizations {
            # Caches chunks internally for faster returns when querying at various positions
            chunk-map-caching=true
            # A simple patch to reduce a few sanity checks for the sake of speed when performing block state operations
            fast-blockstate-lookup=true
            # This prevents chunks being loaded for getting light values at specific block positions. May have side effects.
            ignore-unloaded-chunks-on-get-light=true
        }
        # Configuration options related to the Sql service, including connection aliases etc
        sql {
            # Aliases for SQL connections, in the format jdbc:protocol://[username[:password]@]host/database
            aliases {}
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
            # The auto-save tick interval used when saving global player data. Set to 0 to disable. (Default: 900) Note: 20 ticks is equivalent to 1 second.
            auto-player-save-interval=900
            # The auto-save tick interval used to save all loaded chunks in a world. Set to 0 to disable. (Default: 900) Note: 20 ticks is equivalent to 1 second.
            auto-save-interval=900
            # Lava behaves like vanilla water when source block is removed
            flowing-lava-decay=false
            # Enable if you want the world to generate spawn the moment its loaded.
            generate-spawn-on-load=true
            # Vanilla water source behavior - is infinite
            infinite-water-source=false
            # Enable if this world's spawn should remain loaded with no players.
            keep-spawn-loaded=true
            # Enable if this world should be loaded on startup.
            load-on-startup=true
            # Specifies the radius (in chunks) of where creatures will spawn. This value is capped to the current view distance setting in server.properties
            mob-spawn-range=8
            # A list of all detected portal agents used in this world. In order to override, change the target world name to any other valid world. Note: If world is not found, it will fallback to default.
            portal-agents {
                "minecraft:default_nether"=DIM-1
                "minecraft:default_the_end"=DIM1
            }
            # Enable if this world allows PVP combat.
            pvp-enabled=true
            # Enable if this world should be registered.
            world-enabled=true
        }
    }



Global Properties of Sponge
~~~~~~~~~~~~~~~~~~~~~~~~~~~

========================================  ========  ==========  ===============================================
Property                                  Type      Default     Description
========================================  ========  ==========  ===============================================
**Block Tracking**
block-blacklist                           string    null        Adds block ids you wish to blacklist for player
                                                                block placement tracking.
enabled                                   boolean   true        Adds player tracking support for block
                                                                positions.
**Bungeecord**

ip-forwarding                             boolean   false       Allows bungeecord to forward ip address, UUID,
                                                                and Game Profile to the server.
**Commands**
aliases                                   string    null        Alias will resolve conflicts when multiple
                                                                plugins request a specific command. Correct
                                                                syntax is
                                                                ``<unqualified command>=<plugin name>``
                                                                Example: ::

                                                                    aliases = {
                                                                        title=myPlugin
                                                                    }
**Debug Options**
dump-chunks-on-deadlock                   boolean   false       Dumps chunks in the event of a deadlock.
dump-heap-on-deadlock                     boolean   false       Dump the heap in the event of a deadlock.
dump-threads-on-warn                      boolean   false       Dump the server thread on deadlock warning.
thread-contention-monitoring              boolean   false       Enable Java's thread contention monitoring for
                                                                thread dumps.
**Entity Options**
collision-warn-size                       integer   200         Number of colliding entities in one spot before
                                                                logging a warning. Set to 0 to disable.
count-warn-size                           integer   0           Number of entities allowed in one dimension
                                                                before logging a warning. Set to 0 to disable.
entity-painting-respawn-delay             integer   2           Number of ticks before a painting is respawned
                                                                on the client when their art is changed.
human-player-list-remove-delay            integer   10          Number of ticks before the fake player entry of
                                                                a human is removed from the tab list. The
                                                                allowed range is 0 - 100.
item-despawn-rate                         integer   6000        The time in ticks before an item despawns.
living-hard-despawn-range                 integer   128         The upper bounded range where living entities farther
                                                                from a player will likely despawn
living-soft-despawn-minimum-life          integer   30          The amount of seconds before a living entity between
                                                                the soft and hard despawn ranges from a player to be
                                                                considered for despawning
living-soft-despawn-range                 integer   32          The lower bounded range where living entities near a
                                                                player may potentially despawn
max-bounding-box-size                     integer   1000        Maximum size of an entity's bounding box before
                                                                it is removed. Set to 0 to disable.
max-speed                                 integer   100         Square of the maximum speed of an entity before
                                                                it is removed. Set to 0 to disable
**Entity Activation Range**
auto-populate                             boolean   false       If enabled, newly discovered entities will be
                                                                added to this config with a default value.
**Defaults**                                                    Default activation ranges for all entities unless
                                                                overridden.
ambient                                   integer   32          Default activation range for ambient entities.
aquatic                                   integer   32          Default activation range for aquatic entities.
creature                                  integer   16          Default activation range for creatures.
misc                                      integer   16          Default activation range for miscellaneous
                                                                entities.
monster                                   integer   32          Default activation range for monsters.
**Mods**                                                        Per-mod overrides. Refer to the Minecraft default
                                                                mod for example.
**Entity Collisions**
auto-populate                             boolean   false       If enabled, newly discovered entities/blocks will
                                                                be added to this config with a default value.
**Defaults**                                                    Default max collisions used for all entities/blocks
                                                                unless overridden.
max-entities-within-aabb                  integer   8           Max amount of entities any given entity or block
                                                                can collide with. Set to 0 to disable.
**Mods**                                                        Per-mod overrides. Refer to the Minecraft default
                                                                mod for example.
**Minecraft**
**Blocks**
"detector_rail"                           integer   1           Max collisions for a "detector_rail".
"heavy_weighted_pressure_plate"           integer   150         Max collisions for a "heavy_weighted_pressure_plate".
"light_weighted_pressure_plate"           integer   15          Max collisions for a "light_weighted_pressure_plate".
"mob_spawner"                             integer   -1          Max collisions for a "mob_spawner".
"stone_pressure_plate"                    integer   1           Max collisions for a "stone_pressure_plate".
"wooden_button"                           integer   1           Max collisions for a "wooden_button".
"wooden_pressure_plate"                   integer   1           Max collisions for a "wooden_pressure_plate".
**Defaults**                                                    Default max collisions used for all entities/blocks
                                                                unless overridden.
blocks                                    integer   8           Default max collisions for blocks.
entities                                  integer   8           Default max collisions for entities.
enabled                                   boolean   true        Set to false if you want mod to ignore entity
                                                                collision rules.
**Entities**
thrownpotion                              integer   -1          Max collisions for a thrown potion.
**Exploits**
prevent-creative-itemstack-name-exploit   boolean   true        Prevents an exploit in which the client sends a
                                                                packet with the itemstack name exceeding the
                                                                string limit.
prevent-sign-command-exploit              boolean   true        Prevents an exploit in which the client sends a
                                                                packet to update a sign containing commands from
                                                                a player without permission.
**General Settings**
chunk-load-override                       boolean   false       Forces chunk loading on provide requests.
                                                                This is a speed-up for mods that don't check if
                                                                a chunk is loaded.
disable-warnings                          boolean   false       Disable warning messages to server Admins.
**Ip Sets**

.. TODO Explain IP Sets

**Logging Options**
block-break                               boolean   false       Logs when a block is broken.
block-modify                              boolean   false       Logs when blocks are modified.
block-place                               boolean   false       Logs when blocks are placed.
block-populate                            boolean   false       Logs when blocks are populated in a chunk.
block-tracking                            boolean   false       Logs when blocks are placed by players and
                                                                tracked.
chunk-load                                boolean   false       Log when chunks are loaded.
chunk-unload                              boolean   false       Log when chunks are unloaded.
entity-collision-checks                   boolean   false       Whether to log entity collision/count checks.
entity-death                              boolean   false       Log when living entities are destroyed.
entity-despawn                            boolean   false       Log when living entities are despawned.
entity-spawn                              boolean   false       Log when living entities are spawned.
entity-speed-removal                      boolean   false       Whether to log entity removals due to speed.
exploit-itemstack-name-overflow           boolean   false       Logs when a server receives exploited packets
                                                                with itemstack name exceeding string limit.
exploit-respawn-invisibility              boolean   false       Logs when a player attempts to respawn
                                                                invisible to surrounding players.
exploit-sign-command-updates              boolean   false       Logs when a server receives an exploited packet
                                                                containing commands from a player with no
                                                                permission.
log-stacktraces                           boolean   false       Add stack traces to dev logging.
world-auto-save                           boolean   false       If true, logs when a world auto-saves its chunk data.
**Modules**
bungeecord                                boolean   false       Enables bungeecord support.
entity-activation-range                   boolean   true        Enables the entity activation range settings.
entity-collisions                         boolean   true        Enables entity collision settings.
exploits                                  boolean   true        Enables the exploit prevention module.
optimizations                             boolean   true        Enables the optimizations module.
timings                                   boolean   true        Enables timing settings.
**SQL**
aliases                                   string    null        Aliases for SQL connections. This is done in
                                                                the format
                                                                ``jdbc:protocol://[username[:password]@]host/database``
**Timings**
enabled                                   boolean   true        If timings are enabled.
hidden-config-entries                     string    sponge.sql  The hidden config entries.
history-interval                          integer   300         The interval between timing history report
                                                                generation.
history-length                            integer   3600        How long, in ticks, that the timing history
                                                                will be kept for the server.
server-name-privacy                       boolean   false       Whether to include information such as the
                                                                server name, motd, online-mode, and server
                                                                icon in the report.
verbose                                   boolean   true        Whether or not for timings to monitor at
                                                                the verbose level.
**World Settings**
auto-player-save-interval                 integer   900         The auto-save tick interval used when saving global
                                                                player data.
auto-save-interval                        integer   900         The auto-save tick interval used to save all loaded
                                                                chunks in a world.
flowing-lava-decay                        boolean   false       Lava behaves like vanilla water when the source
                                                                block is removed, when set to true.
generate-spawn-on-load                    boolean   true        If the world should generate spawn when the
                                                                world is loaded.
infinite-water-source                     boolean   false       False = Default vanilla water source behaviour.
keep-spawn-loaded                         boolean   true        If the spawn should stay loaded with no players.
load-on-startup                           boolean   true        If the world should be loaded on startup.
mob-spawn-range                           integer   8           Specifies the radius (in chunks) of where creatures
                                                                will spawn. This value is capped to the current
                                                                view distance setting in server.properties.
**Portal Agents**                                               A list of all detected portal agents used in this
                                                                world. In order to override, change the target world
                                                                name to any other valid world. If world is not found,
                                                                it will fallback to default.
"minecraft:default_nether"                world     DIM-1       The default nether world.
"minecraft:default_the_end"               world     DIM1        The default end world.
pvp-enabled                               boolean   true        If the would allows PVP combat.
world-enabled                             boolean   true        Enable if this world should be registered.
========================================  ========  ==========  ===============================================
