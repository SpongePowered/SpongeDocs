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

The Sponge API is subject to change sometimes. When you try to use an older plugin on the most recent Sponge build and
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
