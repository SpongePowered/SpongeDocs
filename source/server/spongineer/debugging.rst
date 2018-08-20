=========
Debugging
=========

Logs are an essential part when it comes to debugging your server and figuring what went wrong. This page will show
some basic logging examples and will try to explain what you can do to fix your issues, when encountering them.

Checklist
=========

Whenever you encounter a crash or warning make sure you set SpongeForge or SpongeVanilla up correctly. Here's a short
checklist to help you out. If you're unsure on how to aquire the information needed, have a look at the :doc:`logs` page.
It explains how you get the desired answers out of your logfiles.

1. Is Java 8 installed and is Sponge using it?

Sponge requires Java 8 and will crash when using Java 7 or older.

2. Is the recommended Forge version installed?

Usually SpongeForge will run on older or newer Forge builds than the recommended build.
However it is strongly advised to run the recommended build only.
If you encounter a crash and your versions are mismatching, match them first and try again.
If you're unsure which Forge build you need, take a look at :doc:`/server/getting-started/implementations/spongeforge`

3. Are there any other coremods (besides SpongeForge) installed?

Some coremods modify Forge in a way that makes it impossible to run SpongeForge properly. If you have coremods installed
and Sponge crashes, try to remove them and test again. Please report any incompatible Coremods on
`GitHub <https://github.com/SpongePowered>`__ or the `Sponge Forums <https://forums.spongepowered.org>`__. This allows
staff to solve these issues as soon as possible.

4. Is every plugin you're using built against your desired Sponge build?

SpongeAPI is subject to change sometimes. When you try to use an older plugin on the most recent Sponge build and
a crash occurs, try downgrading Sponge or contact the plugin author to get an updated plugin. If you're on an older
Sponge build and a recent plugin crashes, try to update Sponge first. If that doesn't fix the issue, contact the
plugin author and ask for a fix.

5. Separating a faulty plugin

If the problem still persists, try to remove all plugins and re-add them one by one while trying to start the server
every time you added a plugin.

If you're still unsure why and what exactly crashed, have a look at your crashlog. Some common crashes and common
solutions are listed below.

General Warnings
================

A common source of errors and bugs is a version mismatch between either SpongeForge and Forge or
SpongeForge and Plugins. First we'll have a look at the general warning Forge gives us upon crashing:

.. code-block:: none

 WARNING: coremods are present:
    SpongeForge (sponge-1.8-1521-2.1DEV-750.jar)
 Contact their authors BEFORE contacting forge

This isn't a bug or error, it's just Forge telling you that a Coremod (here: SpongeForge) is installed. Forge advises
you to contact the Sponge developers first, before asking the Forge support for help. Nothing to worry about.

Common Exceptions
=================

Here are some common exceptions and some reasons why you might encounter them.

.. note::

 If you encountered a crash, error or any other malfunction **not** listed here, please report it on the
 `Sponge Forums <https://forums.spongepowered.org/>`_ or on `GitHub <https://github.com/spongepowered/>`_.
 This will help others, who are running into the same issue.

Mismatched SpongeForge and Forge
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: none

 [12:59:21] [main/ERROR] [mixin/]: @Mixin target net.minecraftforge.event.world.BlockEvent$NeighborNotifyEvent was not found mixins.forge.core.json:event.block.MixinEventNotifyNeighborBlock

This is a common crash when you try to run SpongeForge on the wrong Forge build. Note that the target/Mixin can vary.
Always match Forge against SpongeForge! If you're unsure which version of Forge is required and you already got your
SpongeForge build, take a look at: :doc:`../getting-started/implementations/spongeforge/`

Other common errors
~~~~~~~~~~~~~~~~~~~

.. code-block:: none

 Caused by: java.lang.ClassNotFoundException: org.spongepowered.api.event.state.ServerStartedEvent
 Caused by: java.lang.NullPointerException

The first error indicates that a ``Class`` is missing, the second is a NullPointer Exception which indicated that the
plugin you're trying to use relies on missing parameters. This happens when you try to run and older plugin on a newer
SpongeForge or SpongeVanilla build and vice versa.

.. code-block:: none

 java.lang.AbstractMethodError: net.minecraft.entity.player.EntityPlayerMP.getTabList()Lorg/spongepowered/api/entity/living/player/tab/TabList;
 at (...)

An ``AbstractMethodError`` occurs when a plugin tries to call a method which isn't implemented yet. Please check if you're
running the most current build of Sponge and update if a newer version is available. If the problem still exists, either
report it on the official Issuetracker, on the forums or on IRC. You can request the implementation of the missing
feature too.

.. code-block:: none

 [Server thread/INFO]: Starting minecraft server version 1.8
 [Server thread/ERROR]: Encountered an unexpected exception
 java.lang.NoClassDefFoundError: org/spongepowered/api/event/game/state/GameStartingServerEvent

.. note::

 Read the full example crashlog here:
 :download:`SpongeForge 575 crashlog with a plugin built against build 750 </files/crashlogs/crashlog-sponge575-plugin750.txt>`

A ``NoClassDefFoundError`` occurs when the plugin tries to access a class that isn't on the classpath. This happens
when the API got adjusted or refactored lately and you're trying to run an older plugin on a newer build of Sponge
and vice versa. Always try to use the correct version! Either ask the Plugin author which Sponge version he build
against or try updating/downgrading your SpongeForge or SpongeVanilla to solve this.

Exceptions at Runtime
=====================

There are two kinds of errors that may occur on modded servers:

* Plugin internal errors, such as NullPointerExceptions during command execution. These errors are logged using the
  standard Java error handler.
* Errors during the ticking of worlds or entities. Sponge prints out more structured errors to provide as much
  information as possible.

Before we look into the details of what caused the exception, make sure that you read all of the error report. Sometimes
the error is explained in the error report itself (or a few lines above or below it); sometimes it is indicated by a
warning during startup.

The following stacktrace shows an example of this special handling (excluding the line prefix with time and severity):

.. code-block:: text

    /******************************************************************************************************************************/
    /*                                           Exception occurred during a PhaseState                                           */
    /******************************************************************************************************************************/
    /* Sponge's tracking system makes a best effort to not throw exceptions randomly                                              */
    /* but sometimes it is inevitable. In most cases, something else triggered this                                               */
    /* exception and Sponge prevented a crash by catching it. The following stacktrace                                            */
    /* can be used to help pinpoint the cause.                                                                                    */
    /******************************************************************************************************************************/
    /* The PhaseState having an exception: EntityTickPhase                                                                        */
    /* The PhaseContext:                                                                                                          */
    /*     - Owner: EntityPlayerMP['SomePlayer'/270, l='world', x=119,62, y=82,00, z=260,21]                                      */
    /*     - Source: EntityCreeper['Creeper'/346, l='world', x=119,50, y=82,00, z=258,50]                                         */
    /*     - CapturedBlockPosition: CaptureBlockPos{pos=null, world=Optional.empty}                                               */
    /* org.spongepowered.asm.util.PrettyPrinter@56ec63ef                                                                          */
    /******************************************************************************************************************************/
    /* StackTrace:                                                                                                                */
    /* java.lang.NullPointerException: null                                                                                       */
    /*     net.minecraft.util.math.BlockPos.<init>(SourceFile:41)                                                                 */
    /*     net.minecraft.pathfinding.PathNavigateGround.func_75494_a(SourceFile:73)                                               */
    /*     net.minecraft.pathfinding.PathNavigate.func_75497_a(SourceFile:147)                                                    */
    /*     com.example.extendedaiplugin.BrokenAITask.start(BrokenAITask.java:58)                                                  */
    /*     org.spongepowered.common.entity.ai.SpongeEntityAICommonSuperclass.func_75249_e(SpongeEntityAICommonSuperclass.java:43) */
    /*     net.minecraft.entity.ai.EntityAITasks.func_75774_a(SourceFile:102)                                                     */
    /*     net.minecraft.entity.EntityLiving.func_70626_be(EntityLiving.java:763)                                                 */
    /*     net.minecraft.entity.EntityLivingBase.func_70636_d(EntityLivingBase.java:2350)                                         */
    /*     net.minecraft.entity.EntityLiving.func_70636_d(EntityLiving.java:577)                                                  */
    /*     net.minecraft.entity.monster.EntityMob.func_70636_d(EntityMob.java:45)                                                 */
    /*     net.minecraft.entity.EntityLivingBase.func_70071_h_(EntityLivingBase.java:2170)                                        */
    /*     net.minecraft.entity.EntityLiving.func_70071_h_(EntityLiving.java:295)                                                 */
    /*     net.minecraft.entity.monster.EntityMob.func_70071_h_(EntityMob.java:50)                                                */
    /*     net.minecraft.entity.monster.EntityCreeper.func_70071_h_(EntityCreeper.java:172)                                       */
    /*     org.spongepowered.common.event.tracking.TrackingUtil.tickEntity(TrackingUtil.java:160)                                 */
    /*     net.minecraft.world.WorldServer.redirect$onCallEntityUpdate$zlo000(WorldServer.java:2986)                              */
    /*     net.minecraft.world.World.func_72866_a(World.java:4154)                                                                */
    /*     net.minecraft.world.WorldServer.func_72866_a(WorldServer.java:832)                                                     */
    /*     net.minecraft.world.World.func_72870_g(World.java:1952)                                                                */
    /*     net.minecraft.world.World.func_72939_s(World.java:6596)                                                                */
    /*     net.minecraft.world.WorldServer.func_72939_s(WorldServer.java:2300)                                                    */
    /*     net.minecraft.server.MinecraftServer.func_71190_q(MinecraftServer.java:767)                                            */
    /*     net.minecraft.server.dedicated.DedicatedServer.func_71190_q(DedicatedServer.java:396)                                  */
    /*     net.minecraft.server.MinecraftServer.func_71217_p(MinecraftServer.java:668)                                            */
    /*     net.minecraft.server.MinecraftServer.run(MinecraftServer.java:526)                                                     */
    /*     java.lang.Thread.run(Unknown Source)                                                                                   */
    /*  Phases Remaining:                                                                                                         */
    /*                                                                                                                            */
    /* Minecraft : 1.12.2                                                                                                         */
    /* SpongeAPI : 7.1.0-SNAPSHOT-7105dfc                                                                                         */
    /* SpongeForge : 1.12.2-2705-7.1.0-BETA-3361                                                                                  */
    /* Minecraft Forge : 14.23.4.2705                                                                                             */
    /******************************************************************************************************************************/

This stacktrace contains the most important version numbers, as well some information about the phase the server was in.
In this case a ``NullPointerException`` has been thrown during ``EntityTickPhase``. At this point it's important to
check which plugins are involved with the crash. This requires some research as you have to match the package names with
mod names; checking the ``Caused by`` blocks may also help.

* ``java`` classes can be ignored during the error search.
* ``net.minecraft`` is the vanilla Minecraft code. If only these elements are present, it's either a Minecraft bug or a
  really nasty one (these are explained later).
* ``org.spongepowered`` is from Sponge itself, having only these and Minecraft packages present usually indicates a
  Sponge bug (or a nasty one). The :doc:`bugreport` chapter explains where to report bugs.
* Other classes have to be mapped to their mods by hand. In this case there is an entry ``com.example.extendedaiplugin`` 
  which gives us the following hints. ``com`` probably from an English speaking mod author. This could also be a
  language code such as ``de`` or ``zh``. The next part usually indicates the mod authors name, but in some cases it may
  also point to a hosting platform such as ``github``. In those cases you should also take a look at the next parts.
  After that there is usually something related to the mod itself. ``extendedaiplugin`` might be related to the plugin
  name. Please be aware that the mod may use additional separator characters such as ``-`` or spaces, or use a different
  character case for some parts. Beware this strategy is not always accurate, even less so in mod packs or bundles.

.. note::

    If you encounter a bug its usually a good idea to create a backup first, then trying to reproduce it, then narrowing
    it down by removing mods. Only then should you report the error. If the error occurs in the absence of Sponge
    plugins, try removing SpongeForge. If the error persists its not related to Sponge. Its usually a good idea to
    report bugs to the mod authors first as they have good knowledge of the parts of code they are working with. However
    you can always contact us through IRC or other means. Please provide logs for bug reports, if possible.

Nasty bugs: Minecraft modding uses some advanced techniques such as Mixins and ClassLoaderTransformations, which means
that although a Minecraft class has been reported as the cause, it does not mean the code executed inside is from
Minecraft itself. Sponge and other plugins hook into the native methods and execute their own code; such as
posting events. In that case you have to do a blind search for the malfeasant mod. These often occur only through
interaction between some combinations of plugins and thus are very hard to find.