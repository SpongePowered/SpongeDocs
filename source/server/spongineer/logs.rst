=========
Log Files
=========

Logfiles are an essential part when it comes to debugging your server and figuring what went wrong. This page contains
logfiles from SpongeForge and SpongeVanilla servers including short descriptions.

.. contents:: **Provided Logfiles**
   :depth: 2
   :local:

Configure Logging
=================

Sometimes plugins log messages that the server owner does not need, or the sheer number of messages hide some more
important information. In other cases, plugins or the server will log debug messages that normally don't appear in any
logs. This section explains how to configure the logging. It is also possible to configure the logging in a way that
splits the logs in two or more separate files. One could be optimized for the moderators that pay attention to their
users' activity/behavior and other logs could be used to monitor plugins that are important to the admins.

.. note::

    If you think that a plugin logs too many/few messages or on wrong log levels, please report it to its author.

The simplest way to configure the logging is modifying the ``log4j2.xml`` configuration file that will be used by
Minecraft/Forge itself. You can find and extract it from the root of the ``forge-...-universal.jar`` and
``minecraft_server.jar``. Do **NOT** edit the file inside the jar.

You can tell log4j2 to use the new config file by adding a start parameter to your server launch script.

.. code-block:: bash

    java -Dlog4j.configurationFile=log4j2_server.xml -jar server.jar

The default configuration looks similar to the following example.

.. code-block:: xml

    <?xml version="1.0" encoding="UTF-8"?>
    <Configuration status="warn" packages="com.mojang.util,net.minecraftforge.server.console.log4j">
        <Appenders>
            <TerminalConsole name="Console">
                <PatternLayout pattern="[%d{HH:mm:ss}] [%t/%level] [%logger]: %msg%n"/>
            </TerminalConsole>

            <RollingRandomAccessFile name="DebugFile" fileName="logs/debug.log" filePattern="logs/debug-%i.log.gz">
                <PatternLayout pattern="[%d{HH:mm:ss}] [%t/%level] [%logger]: %msg%n"/>
                <Policies>
                    <OnStartupTriggeringPolicy/>
                    <SizeBasedTriggeringPolicy size="200MB"/>
                </Policies>
                <DefaultRolloverStrategy max="5" fileIndex="min"/>
            </RollingRandomAccessFile>
        </Appenders>
        <Loggers>
            <Logger level="info" name="org.spongepowered"/>
            <Logger level="info" name="com.example.mod"/>
            <Root level="all">
                <AppenderRef ref="Console" level="info"/>
                <AppenderRef ref="DebugFile"/>
            </Root>
        </Loggers>
    </Configuration>

.. note::

    This example lacks some comments that are present in the original, make sure you read them to understand why they
    are there.

The ``Appenders`` section defines the output channels for the log messages. This could be a file, the console, or even
a central log collection and analysis server. Read more about configuring appenders
`here <https://logging.apache.org/log4j/2.x/manual/configuration.html#Appenders>`__.

The ``Loggers`` section defines filters for loggers and to which targets the messages should be forwarded to. This is
usually the section you must edit if you want to mute a specific plugin in the logs. Let's look into this
a bit more:

.. code-block:: xml

    <Logger level="info" name="com.example.mod"/>

This will limit the logs of ``com.example.mod`` to ``info`` and higher messages. Beware, this affects all output
channels and also any logger that is created for a sub-package of the given path. Read more about filters
`here <https://logging.apache.org/log4j/2.x/manual/configuration.html#Filters>`__.

.. code-block:: xml

    <Root level="all">
        <AppenderRef ref="Console" level="info"/>
        <AppenderRef ref="DebugFile"/>
    </Root>
    
This section configures two output channels. First, the appender called ``Console`` with a log level filter of ``info``
and higher, and last, the appender called ``DebugFile``. It is recommended to keep at least one logger to a persistent
target such as a file for later error search.

.. note::

    If you are wondering why your new plugin's log messages don't seem to show up: The Console's log level is
    configured to be at least info by default which hides your debug messages.

If you don't want to reconfigure the entire logging, but want to hide a certain plugin from the logs you can also use
composite logging options. This can be achieved by referencing both the original logging config and your specialized
config that only contains the changed logging options. The following example shows this:

.. code-block:: bash

    java -Dlog4j.configurationFile=log4j2_server.xml,log4j2_custom.xml -jar server.jar

.. code-block:: xml

    <?xml version="1.0" encoding="UTF-8"?>
    <Configuration status="WARN">
        <Loggers>
            <Logger name="com.example.logspammer" level="off"/>
        </Loggers>
    </Configuration>

In this example all logs from the ``com.example.logspammer`` package won't be shown or saved. For debugging purposes, it
might be useful to include your plugin's log messages in the console so you don't have to ``tail`` the debug log. This
can be achieved using the following example:

.. code-block:: xml

    <?xml version="1.0" encoding="UTF-8"?>
    <Configuration status="WARN">
        <Loggers>
            <Logger name="com.example.newplugin" level="all" additivity="false">
                <AppenderRef ref="Console"/>
                [...]
            </Logger>
        </Loggers>
    </Configuration>

.. tip::

    Logging affects performance. If you log too much you might lose a small amount of tps. Also remember that it will
    become harder to reproduce errors if there are no/incomplete logs available.

SpongeForge logfiles
====================

SpongeForge writes several logfiles to the ``/logs`` folder located inside your server's directory. 
As of Forge 1.12.2 - 14.23.4.2705 these are: 

1. ``debug.log``
#. ``latest.log``

debug.log
~~~~~~~~~

.. note::

 Only a few example lines are shown here. To read the full example log, follow this link:
 :download:`SpongeForge 1.12.2 - 7.1.0-BETA-3126 debug.log file 
 </files/logs/SpongeForge_1.12.2-2705-7.1.0-BETA-3136_debug.log.txt>`

.. code-block:: none

 [main/INFO] [FML]: Forge Mod Loader version 14.23.4.2705 for Minecraft 1.12.2 loading
 [main/INFO] [FML]: Java is Java HotSpot(TM) 64-Bit Server VM, version 1.8.0_162, running on Windows 10:amd64:10.0, installed at C:\Program Files\Java\jre1.8.0_162
 [main/DEBUG] [FML]: Java classpath at launch is:
 [main/DEBUG] [FML]:     forge-1.12.2-14.23.4.2705-universal.jar
 [main/DEBUG] [FML]: Java library path at launch is:
 [main/DEBUG] [FML]:     C:\ProgramData\Oracle\Java\javapath

The example log indicates that we're running:

* Forge 14.23.4.2705 (Version 2705)
* Java 8 64bit Update 162
* Windows 10 x64
* the ``directory`` Java was installed to (Line 4)

.. warning::

 In order to run Sponge, you **must** be running Java 8 Update 20 or above.
 Older builds or newer Java major versions (like 9 or 10) are not supported.

.. code-block:: none

 [main/DEBUG] [FML]: Examining for coremod candidacy spongeforge-1.12.2-2705-7.1.0-BETA-3136.jar
 [main/INFO] [FML]: Loading tweaker org.spongepowered.asm.launch.MixinTweaker from spongeforge-1.12.2-2705-7.1.0-BETA-3136.jar

This indicates that SpongeForge 3136 was found and loaded by Forge. For further help regarding the SpongeForge
naming scheme, have a look at :doc:`../../versions/filenames`.

latest.log
~~~~~~~~~~

.. note::

 Only a few example lines are shown here. To read the full example log, follow this link:
 :download:`SpongeForge 1521 latest.log </files/logs/SpongeForge_1.12.2-2705-7.1.0-BETA-3136_latest.log.txt>`

This is the output that you would see in the Minecraft server GUI.


SpongeVanilla logfiles
======================

latest.log
~~~~~~~~~~

.. note::

 Only a few example lines are shown here. To read the full example log, follow this link:
 :download:`SpongeVanilla 1.12.2-7.1.0-BETA-54 latest.log 
 </files/logs/SpongeVanilla_1.12.2-7.1.0-BETA-54_latest.log.txt>`

This is the output that you would see in the Minecraft server GUI.

Reading logfiles
================

If you're unsure on how to read a common crashlog, you'll find help here, but first we need a crashlog. For this short
introduction we will just use an example crash from the :doc:`debugging` page:
:download:`Example crashlog of an outdated SpongeForge build </files/crashlogs/crashlog-sponge575-plugin750.txt>`.

.. code-block:: none

 WARNING: coremods are present:
 SpongeCoremod (sponge-1.8-1499-2.1DEV-575.jar)
 Contact their authors BEFORE contacting forge

The first thing you'll notice is a ``Warning`` that coremods are present. Nothing to worry about here, that's not an
error, just a warning to contact Sponge support, not Forge.

.. code-block:: none

 java.lang.NoClassDefFoundError: org/spongepowered/api/event/game/state/GameStartingServerEvent

A few lines below the actual error is found. In this case it's a ``NoClassDefFoundError`` If you're unsure what that
means, head over to our :doc:`debugging` page. If it's a common error, it will be listed there. If it isn't, you can
always ask on the forums for help! Make sure you provide the full crashlog.

Luckily your systems details are included at the bottom of the crashlog:

.. code-block:: none

 Minecraft Version: 1.8
 Operating System: Windows 8.1 (amd64) version 6.3
 Java Version: 1.8.0_51, Oracle Corporation
 Java VM Version: Java HotSpot(TM) 64-Bit Server VM (mixed mode), Oracle Corporation
 Memory: 515666256 bytes (491 MB) / 782761984 bytes (746 MB) up to 1847590912 bytes (1762 MB)
 JVM Flags: 0 total;
 IntCache: cache: 0, tcache: 0, allocated: 0, tallocated: 0
 FML: MCP v9.10 FML v8.0.99.99 Minecraft Forge 11.14.3.1521 5 mods loaded, 5 mods active
 States: 'U' = Unloaded 'L' = Loaded 'C' = Constructed 'H' = Pre-initialized 'I' = Initialized 'J' = Post-initialized 'A' = Available 'D' = Disabled 'E' = Errored
 UC	mcp{9.05} [Minecraft Coder Pack] (minecraft.jar)
 UC	FML{8.0.99.99} [Forge Mod Loader] (forge.jar)
 UC	Forge{11.14.3.1521} [Minecraft Forge] (forge.jar)
 UC	Sponge{1.8-1499-2.1DEV-575} [SpongeForge] (minecraft.jar)
 U	Core{unknown} [Core Plugin] (Core.jar)
 Loaded coremods (and transformers):
 SpongeCoremod (sponge-1.8-1499-2.1DEV-575.jar)

This indicates that

* Minecraft 1.8 with Forge 1521 was running on
* Java 8 Update 51 (64bit version) and that
* 2 additional mods were installed

    * SpongeForge 1.8-1499-2.1DEV-575 (which is build #575) and
    * Core

.. note::
 Please note that the other three installed mods (mcp, FML, Forge) are required on every Forge server and necessary to
 run properly.

Now the following assumptions can be made:

* maybe the plugin crashed the server
* SpongeForge doesn't match the Forge version: 1499 required, 1521 installed

If you want to know how to solve this, head over to our checklist on the :doc:`debugging` page.

Common errors
=============

Head over to :doc:`debugging` to read about common errors and exceptions.
