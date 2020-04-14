=================
Mod Compatibility
=================

AKA: *"Why does the server crash when I add SpongeForge?"*

SpongeForge tries to maintain compatiblity with as many mods as possible. However, the enormous breadth of possibilities
available to mod developers can make this goal very difficult. Some mods simply will not work with SpongeForge present,
others need configuration settings or alternative versions to be compatible, and many simply need to initialise *after*
SpongeForge to work correctly. Sometimes there will be a diagnostic message in the server log telling you what went wrong,
but often it can be very hard to tell what went wrong.

To help you resolve which is which, and how to fix the problem (if possible), we have prepared this list of mods that
can cause problems when used alongside SpongeForge. Please note that, whilst we will try to keep it current, changes may
happen as mods are updated. You can help by letting us know when relevant changes happen, on Discord or GitHub.

Known Incompatibilities
=======================

Old Mixins
~~~~~~~~~~

First, a general note: If there is a warning about "old mixins" in the server log, the first step you should attempt is
to rename the SpongeForge file, to make if first in the mod loading order. This can usually be done by prefixing the
SpongeForge jar file name with ``aaa_``, giving a filename like ``aaa_spongeforge-1.12.2-2838-7.2.1-RC4011``. This makes it
load the Sponge version of Mixin first, and can resolve many problems in one step.

FarSeek
~~~~~~~
- Dependencies: Streams mod.
- Versions:
- Problem: Crash on startup
- Solution: No compatibility at present. Choose one or the other.

FoamFix
~~~~~~~
- Versions:
- Problem: Crash on startup (block collision error)
- Solution: configuration changes
- *Never versions of FoamFix automatically correct this when SpongeForge is present.*

Hammercore
~~~~~~~~~~
- Dependencies: TBA
- Versions: 
- Problem: Crash on startup
- Solution: Enable World.ITickable.Override in asm/hammercore.xml

Open Terrain Generator (OTG)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~
- Dependencies: Also requires OTGcore.
- Versions: 
- Problem: Crash on startup / Multiworld Wgen problems
- Solution: Pre-generate world without SpongeForge, then remove OTG and add SpongeForge.

MystCraft
~~~~~~~~~
- Versions:
- Problem: Crash on startup
- Solution: No compatibility at present. Choose one or the other.

There may be many more, please help us keep this list maintained by contributing to the SpongeDocs on GitHub!
