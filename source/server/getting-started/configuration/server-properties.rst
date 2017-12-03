=================
server.properties
=================


Default File
============


Here is the default server.properties file of an unmodified Minecraft 1.8.1 server.

.. code-block:: properties

    #Minecraft server properties
    #(File modification datestamp)
    spawn-protection=16
    max-tick-time=60000
    generator-settings=
    force-gamemode=false
    allow-nether=true
    gamemode=0
    enable-query=false
    player-idle-timeout=0
    difficulty=1
    spawn-monsters=true
    op-permission-level=4
    resource-pack-hash=
    announce-player-achievements=true
    pvp=true
    snooper-enabled=true
    level-type=DEFAULT
    hardcore=false
    enable-command-block=false
    max-players=20
    network-compression-threshold=256
    max-world-size=29999984
    server-port=25565
    server-ip=
    spawn-npcs=true
    allow-flight=false
    level-name=world
    view-distance=10
    resource-pack=
    spawn-animals=true
    white-list=false
    generate-structures=true
    online-mode=true
    max-build-height=256
    level-seed=
    use-native-transport=true
    motd=A Minecraft Server
    enable-rcon=false

Property Explanation
----------------------

Credit goes to the editors at the `Minecraft Wiki <https://minecraft.gamepedia.com>`__ for the explanations.

**Boolean** properties have only two valid values: *true* and *false*.
**Integer** properties must be whole numbers. Where a range is specified, the number must be in that range.
**String** properties can consist of any symbol.

+-------------------------------+-------------+-------------+------------------------------------------------------------+
| Key                           | Type        | Default     | Description                                                |
|                               |             | Value       |                                                            |
+===============================+=============+=============+============================================================+
| allow-flight                  | boolean     | false       | Allows users to use flight on your server while in         |
|                               |             |             | Survival mode, if they have a mod that provides flight     |
|                               |             |             | installed.                                                 |
|                               |             |             |                                                            |
|                               |             |             | With allow-flight enabled griefers will possibly be more   |
|                               |             |             | common, because it will make their work easier. In         |
|                               |             |             | Creative mode this has no effect.                          |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Flight is not allowed (players in air for at  |
|                               |             |             |  least 5 seconds will be kicked).                          |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Flight is allowed.                             |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| allow-nether                  | boolean     | true        | Allows players to travel to the Nether.                    |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Nether portals will not work.                 |
|                               |             |             |                                                            |
|                               |             |             |  **true** - The server will allow portals to send players  |
|                               |             |             |  to the Nether.                                            |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| announce-player-achievements  | boolean     | true        | Allows the server to announce when a player gets an        |
|                               |             |             | achievement.                                               |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| difficulty                    | integer     | 1           | Defines the difficulty (such as damage dealt by mobs and   |
|                               | (0-3)       |             | the way hunger and poison affects players) of the server.  |
|                               |             |             |                                                            |
|                               |             |             |  **0** - Peaceful                                          |
|                               |             |             |                                                            |
|                               |             |             |  **1** - Easy                                              |
|                               |             |             |                                                            |
|                               |             |             |  **2** - Normal                                            |
|                               |             |             |                                                            |
|                               |             |             |  **3** - Hard                                              |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| enable-query                  | boolean     | false       | Enables the GameSpy4 protocol server listener. Used to get |
|                               |             |             | information about the server.                              |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| enable-rcon                   | boolean     | false       | Enables remote access to the server console.               |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| enable-command-block          | boolean     | false       | Enables command blocks.                                    |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| force-gamemode                | boolean     | false       | Force players to join in the default game mode.            |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Players will join in the gamemode they had    |
|                               |             |             |  when they last left.                                      |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Players will always join in the default        |
|                               |             |             |  gamemode.                                                 |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| gamemode                      | integer     | 0           | Defines the mode of gameplay.                              |
|                               | (0-3)       |             |                                                            |
|                               |             |             |  **0** - Survival                                          |
|                               |             |             |                                                            |
|                               |             |             |  **1** - Creative                                          |
|                               |             |             |                                                            |
|                               |             |             |  **2** - Adventure                                         |
|                               |             |             |                                                            |
|                               |             |             |  **3** - Spectator                                         |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| generate-structures           | boolean     | true        | Defines whether structures (such as villages) will be      |
|                               |             |             | generated in new chunks.                                   |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Structures will not be generated.             |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Structures will be generated.                  |
|                               |             |             |                                                            |
|                               |             |             | **Note:** Dungeons will still generate if this is set to   |
|                               |             |             | false.                                                     |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| generator-settings            | string      | *blank*     | The settings used to customize world generation. See       |
|                               |             |             | `Superflat <https://minecraft.gamepedia.com/Superflat>`__  |
|                               |             |             | and                                                        |
|                               |             |             | `Customized <https://minecraft.gamepedia.com/Customized>`__|
|                               |             |             | on the Minecraft Wiki (external links) for possible        |
|                               |             |             | settings and examples.                                     |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| hardcore                      | boolean     | false       | If set to **true**, players will be permanently banned if  |
|                               |             |             | they die.                                                  |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| level-name                    | string      | world       | The "level-name" value will be used as the world name and  |
|                               |             |             | its folder name. You may also copy your saved game folder  |
|                               |             |             | here, and change the name to the same as that folder's to  |
|                               |             |             | load it instead.                                           |
|                               |             |             |                                                            |
|                               |             |             |  Characters such as \' (apostrophe) may need to be escaped |
|                               |             |             |  by adding a backslash (\\) before them.                   |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| level-seed                    | string      | *blank*     | Add a seed for your world, as in Singleplayer.             |
|                               |             |             |                                                            |
|                               |             |             |  Some examples are: minecraft, 404, 1a2b3c.                |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| level-type                    | string      | DEFAULT     | Determines the type of map that is generated.              |
|                               |             |             |                                                            |
|                               |             |             |  **DEFAULT** - Standard world with hills, valleys, water,  |
|                               |             |             |  etc.                                                      |
|                               |             |             |                                                            |
|                               |             |             |  **FLAT** - A flat world with no features, meant for       |
|                               |             |             |  building.                                                 |
|                               |             |             |                                                            |
|                               |             |             |  **LARGEBIOMES** - Same as default, but all biomes are     |
|                               |             |             |  larger.                                                   |
|                               |             |             |                                                            |
|                               |             |             |  **AMPLIFIED** - Same as default, but world-generation     |
|                               |             |             |  height limit is increased.                                |
|                               |             |             |                                                            |
|                               |             |             |  **CUSTOMIZED** - Same as default unless                   |
|                               |             |             |  generator-settings is set to a preset.                    |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| max-build-height              | integer     | 256         | The maximum height in which building is allowed. Terrain   |
|                               |             |             | may still naturally generate above a low height limit.     |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| max-players                   | integer (0- | 20          | The maximum number of players that can play on the server  |
|                               | 2147483647) |             | at the same time. Note that if more players are on the     |
|                               |             |             | server it will use more resources. Note also, op player    |
|                               |             |             | connections are not supposed to count against the max      |
|                               |             |             | players, but ops currently cannot join a full server.      |
|                               |             |             | Extremely large values for this field result in the        |
|                               |             |             | client-side user list being broken.                        |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| max-tick-time                 | integer (0- | 60000       | The maximum number of milliseconds a single tick may take  |
|                               | (2^63-1))   |             | before the server watchdog stops the server with the       |
|                               |             |             | message: \"A single server tick took 60.00 seconds (should |
|                               |             |             | be max 0.05); Considering it to be crashed, server will    |
|                               |             |             | forcibly shutdown\". Once this criteria is met, it calls   |
|                               |             |             | System.exit(1).                                            |
|                               |             |             |                                                            |
|                               |             |             |  **-1** - disable watchdog entirely                        |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| max-world-size                | integer (1- | 29999984    | This sets the maximum possible size in blocks, expressed   |
|                               | 29999984)   |             | as a radius, that the world border can obtain. Setting the |
|                               |             |             | world border bigger causes the commands to complete        |
|                               |             |             | successfully, but the actual border will not move past     |
|                               |             |             | this block limit. Setting the max-world-size higher than   |
|                               |             |             | the default doesn't appear to do anything.                 |
|                               |             |             |                                                            |
|                               |             |             | Examples:                                                  |
|                               |             |             |                                                            |
|                               |             |             |  - Setting max-world-size to 1000 will allow you to have a |
|                               |             |             |    2000x2000 world border.                                 |
|                               |             |             |                                                            |
|                               |             |             |  - Setting max-world-size to 4000 will give you an         |
|                               |             |             |    8000x8000 world border.                                 |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| motd                          | string      | *A*         | This is the message that is displayed in the server list   |
|                               |             | *Minecraft* | of the client, below the name.                             |
|                               |             | *Server*    |                                                            |
|                               |             |             |  - The MOTD does support color and formatting codes.       |
|                               |             |             |                                                            |
|                               |             |             |  - If the MOTD is over 59 characters, the server list will |
|                               |             |             |    likely report a communication error.                    |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| network-compression-threshold | integer     | 256         | By default it allows packets that are n-1 bytes big to go  |
|                               |             |             | normally, but a packet that n bytes or more will be        |
|                               |             |             | compressed down. So, lower number means more compression   |
|                               |             |             | but compressing small amounts of bytes might actually end  |
|                               |             |             | up with a larger result than what went in.                 |
|                               |             |             |                                                            |
|                               |             |             |  **-1** - disable compression entirely                     |
|                               |             |             |                                                            |
|                               |             |             |  **0** - compress everything                               |
|                               |             |             |                                                            |
|                               |             |             | **Note:** *The ethernet spec requires that packets less*   |
|                               |             |             | *than 64 bytes become padded to 64 bytes. Thus, setting a* |
|                               |             |             | *value lower than 64 may not be beneficial. It is also*    |
|                               |             |             | *not recommended to exceed the MTU (Maximum Transmission*  |
|                               |             |             | *Unit), typically 1500 bytes.*                             |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| online-mode                   | boolean     | true        | Server checks connecting players against Minecraft's       |
|                               |             |             | account database. Only set this to false if your server is |
|                               |             |             | not connected to the Internet. Hackers with fake accounts  |
|                               |             |             | can connect if this is set to false! If minecraft.net is   |
|                               |             |             | down or inaccessible, no players will be able to connect   |
|                               |             |             | if this is set to true. Setting this variable to off       |
|                               |             |             | purposely is called "cracking" a server, and servers that  |
|                               |             |             | are presently in offline mode are called "cracked"         |
|                               |             |             | servers, allowing players with unlicensed copies of        |
|                               |             |             | Minecraft to join.                                         |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Disabled. The server will not attempt to      |
|                               |             |             |  check connecting players.                                 |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Enabled. The server will assume it has an      |
|                               |             |             |  Internet connection and check every connecting player.    |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| op-permission-level           | integer     | 4           | Sets permission level for ops. Each level also contains    |
|                               | (1-4)       |             | the permissions of the levels below it.                    |
|                               |             |             |                                                            |
|                               |             |             |  **1** - Ops can bypass spawn protection.                  |
|                               |             |             |                                                            |
|                               |             |             |  **2** - Ops can use /clear, /difficulty, /effect,         |
|                               |             |             |  /gamemode, /gamerule, /give, and /tp, and can edit        |
|                               |             |             |  command blocks.                                           |
|                               |             |             |                                                            |
|                               |             |             |  **3** - Ops can use /ban, /deop, /kick, and /op.          |
|                               |             |             |                                                            |
|                               |             |             |  **4** - Ops can use /stop.                                |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| player-idle-timeout           | integer     | 0           | If non-zero, players are kicked from the server if they    |
|                               |             |             | are idle for more than that many minutes.                  |
|                               |             |             |                                                            |
|                               |             |             |  **Note:** *Idle time is reset when the server receives*   |
|                               |             |             |  *one of the following packets:*                           |
|                               |             |             |                                                            |
|                               |             |             |   - 102 (0x66) WindowClick                                 |
|                               |             |             |   - 108 (0x6c) ButtonClick                                 |
|                               |             |             |   - 130 (0x82) UpdateSign                                  |
|                               |             |             |   - 14 (0xe) BlockDig                                      |
|                               |             |             |   - 15 (0xf) Place                                         |
|                               |             |             |   - 16 (0x10) BlockItemSwitch                              |
|                               |             |             |   - 18 (0x12) ArmAnimation                                 |
|                               |             |             |   - 19 (0x13) EntityAction                                 |
|                               |             |             |   - 205 (0xcd) ClientCommand                               |
|                               |             |             |   - 3 (0x3) Chat                                           |
|                               |             |             |   - 7 (0x7) UseEntity                                      |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| pvp                           | boolean     | true        | Enable PvP on the server. Players shooting themselves with |
|                               |             |             | arrows will only receive damage if PvP is enabled.         |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Players cannot kill other players (also known |
|                               |             |             |  as **Player versus Environment** (**PvE**)).              |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Players will be able to kill each other.       |
|                               |             |             |                                                            |
|                               |             |             | **Note:** *Indirect damage sources spawned by players*     |
|                               |             |             | *(such as lava, fire, TNT and to some extent water, sand*  |
|                               |             |             | *and gravel) will still deal damage to other players.*     |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| query.port                    | integer (1- | 25565       | Sets the port for the query server (see **enable-query**). |
|                               | 65534)      |             |                                                            |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| rcon.password                 | string      | *blank*     | Sets the password for remote connection.                   |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| rcon.port                     | integer (1- | 25575       | Sets the port for remote connection.                       |
|                               | 65534)      |             |                                                            |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| resource-pack                 | string      | *blank*     | Optional URI to a resource pack. The player may choose to  |
|                               |             |             | use it.                                                    |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| resource-pack-hash            | string      | *blank*     | Optional SHA-1 digest of the resource pack, in lowercase   |
|                               |             |             | hexadecimal. It's recommended to specify this. This is not |
|                               |             |             | yet used to verify the integrity of the resource pack, but |
|                               |             |             | improves the effectiveness and reliability of caching.     |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| server-ip                     | string      | *blank*     | Set this if you want the server to bind to a particular    |
|                               |             |             | IP. It is strongly recommended that you leave this blank.  |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| server-port                   | integer (1- | 25565       | Changes the port the server is hosting (listening) on.     |
|                               | 65534)      |             | This port must be forwarded if the server is hosted in a   |
|                               |             |             | network using NAT (If you have a home router/firewall).    |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| snooper-enabled               | boolean     | true        | Sets whether the server sends snoop data regularly to      |
|                               |             |             | http://snoop.minecraft.net. (external link)                |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Disable sending of data.                      |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Enable sending of data.                        |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| spawn-animals                 | boolean     | true        | Determines whether animals will be able to spawn.          |
|                               |             |             |                                                            |
|                               |             |             |  **false** - All animals will immediately vanish, and none |
|                               |             |             |  will spawn.                                               |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Animals spawn as normal.                       |
|                               |             |             |                                                            |
|                               |             |             | *Tip: if you have major lag, set this to false.*           |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| spawn-monsters                | boolean     | true        | Determines whether hostile mobs will be able to spawn.     |
|                               |             |             |                                                            |
|                               |             |             |  **false** - All mobs will immediately vanish, and none    |
|                               |             |             |  will spawn anywhere, or at any time of day.               |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Mobs spawn as normal; in darkness and at       |
|                               |             |             |  night.                                                    |
|                               |             |             |                                                            |
|                               |             |             | This will have no effect if difficulty is set to Peaceful. |
|                               |             |             |                                                            |
|                               |             |             | *Tip: if you have major lag, set this to false.*           |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| spawn-npcs                    | boolean     | true        | Determines if villagers will be spawned.                   |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Enabled. Villagers will spawn.                 |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Disabled. No villagers.                       |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| spawn-protection              | integer     | 16          | Determines the radius of the spawn protection. Setting     |
|                               |             |             | this to 0 will not disable spawn protection. 0 will        |
|                               |             |             | protect the single block at the spawn point. 1 will        |
|                               |             |             | protect a 3x3 area centered on the spawn point. 2 will     |
|                               |             |             | protect 5x5, 3 will protect 7x7, etc. This option is not   |
|                               |             |             | generated on the first server start and appears when the   |
|                               |             |             | first player joins. If there are no ops set on the server, |
|                               |             |             | the spawn protection will be disabled automatically.       |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| use-native-transport          | boolean     | true        | Linux server performance improvements: optimized packet    |
|                               |             |             | sending/receiving on Linux.                                |
|                               |             |             |                                                            |
|                               |             |             |  **false** - Disabled. Disable Linux packet                |
|                               |             |             |  sending/receiving optimization.                           |
|                               |             |             |                                                            |
|                               |             |             |  **true** - Enabled. Enable Linux packet                   |
|                               |             |             |  sending/receiving optimization.                           |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| view-distance                 | integer     | 10          | Sets the amount of world data the server sends the client, |
|                               |             |             | measured in chunks in each direction of the player         |
|                               |             |             | (radius, not diameter). It determines the server-side      |
|                               |             |             | viewing distance.                                          |
|                               | (3-15)      |             |                                                            |
|                               |             |             | *10 is the default/recommended. If you have major lag,*    |
|                               |             |             | *reduce this value.*                                       |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
| white-list                    | boolean     | false       | Enables a whitelist on the server.                         |
|                               |             |             | See :doc:`../../../server/management/whitelist`.           |
|                               |             |             | With a whitelist enabled, users not on the whitelist will  |
|                               |             |             | be unable to connect. Intended for private servers, such   |
|                               |             |             | as those for real-life friends or strangers carefully      |
|                               |             |             | selected via an application process, for example.          |
|                               |             |             |                                                            |
|                               |             |             |  **false** - No whitelist is used.                         |
|                               |             |             |                                                            |
|                               |             |             |  **true** - The file ``whitelist.json`` is used to         |
|                               |             |             |  generate the whitelist.                                   |
|                               |             |             |                                                            |
|                               |             |             | **Note:** *Ops are automatically whitelisted, and there*   |
|                               |             |             | *is no need to add them to the whitelist.*                 |
+-------------------------------+-------------+-------------+------------------------------------------------------------+
