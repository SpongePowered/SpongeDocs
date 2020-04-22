=================
Mod Compatibility
=================

AKA: *"Why does the server crash when I add SpongeForge?"*

SpongeForge tries to maintain compatiblity with as many mods as possible. However, the enormous breadth of possibilities
available to mod developers can make this goal very difficult. Some mods simply will not work with SpongeForge present,
others need configuration settings or alternative versions to be compatible, and many simply need to initialise *after*
SpongeForge to work correctly. Sometimes there will be a diagnostic message in the server log telling you what broke,
but often it can be very hard to tell what went wrong.

To help you resolve which is which, and how to fix the problem (if possible), we have prepared this list of mods that
can cause problems when used alongside SpongeForge. Please note that, whilst we will try to keep it current, changes may
happen as mods are updated. You can help by letting us know when relevant changes happen, on Discord or GitHub.

Known Incompatibilities
=======================

Old Mixins
~~~~~~~~~~

First, a general note: If there is a warning about "old mixins" in the server log, the first step you should attempt is
to rename the SpongeForge file, to make it first in the mod loading order. This can usually be done by prefixing the
SpongeForge jar file name with ``aaa_``, giving a filename like ``aaa_spongeforge-1.12.2-2838-7.2.1-RC4011``. This makes it
load the Sponge version of Mixin first, and can resolve many problems in one step.

FarSeek
~~~~~~~
- Dependents: Streams mod
- Versions: 2.3, 2.4
- Problem: Crash on startup / Failed world generation
- Solution: Update to version 2.5.

FoamFix
~~~~~~~
- Versions: Up to 0.10.7
- Problem: Crash on startup (block collision error)
- Solution: Update to version 0.10.8 or newer, or change these two configuration options:
  ``optimizedBlockPos=false`` and ``patchChunkSerialization=false``
- *Newer versions of FoamFix (0.10.8+) automatically correct this when SpongeForge is present.*

Hammer Core
~~~~~~~~~~~
- Dependents: Many
- Versions: 2.0.6.1.2+
- Problem: Crash on startup. Typically, the following error message is returned:

.. code-block:: none

    org.spongepowered.asm.mixin.injection.throwables.InjectionError: Critical injection failure: Redirector onUpdateTileEntities(Lnet/minecraft/util/ITickable;)V in mixins.common.core.json:world.WorldMixin failed injection check, (0/1) succeeded. Scanned 1 target(s). Using refmap mixins.common.refmap.json

- Solution: Change ``<entry key="World.ITickable.Override">true</entry>`` to ``false`` in the ``/asm/hammercore.xml`` file

Just Enough IDs (JEID)
~~~~~~~~~~~~~~~~~~~~~~
- Versions: Up to 1.0.3-48
- Problem: Crash on startup (mixin conflict).
- Solution: Update to version 1.0.3-54 or newer.

LagGoggles
~~~~~~~~~~
- Versions: Older than 1.12.2-5.3-113
- Problem: Crash on startup (mixin conflict).
- Solution: Update to version 1.12.2-5.3-113 and install *TickCentral* core-mod.

MystCraft
~~~~~~~~~
- Versions: All (so far)
- Problem: Crash on startup
- Solution: No compatibility at present. Choose one or the other.

Open Terrain Generator (OTG)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~
- Dependencies: OTGcore
- Versions: All (so far)
- Problem: Crash on startup / Multiworld Wgen problems
- Solution: Pre-generate world without SpongeForge, then remove OTG and add SpongeForge.

Phosphor
~~~~~~~~
- Versions: Up to 0.2.4
- Problem (1): Crash on startup
- Solution: Update to version 0.2.5 or newer, which is compatible with Sponge RC3844.
- Problem (2): Poor graphic performance
- Solution: Set the optimisation ``async-lighting`` to ``false`` in the Sponge ``global.conf`` file.

There may be many more, please help us keep this list maintained by contributing to the SpongeDocs on GitHub!
The :doc:`debugging` page may also be of help if your issue is not one of those mentioned above.
