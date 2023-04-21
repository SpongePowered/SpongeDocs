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
SpongeForge jar file name with ``aaa_``, giving a filename like ``aaa_spongeforge-1.16.5-36.2.5-8.1.0-RC1312``. 
This makes it load the Sponge version of Mixin first, and can resolve many problems in one step.

Forge Permissions
~~~~~~~~~~~~~~~~~

Some mods may register there own permissions with Forge. These cannot be tracked by mods including SpongeForge due to a 
bug that was fixed in December 2021 (after forge stopped updating 1.16.5) so permissions plugins will not be able to use
these permissions.

Applied Energistics 2
~~~~~~~~~~~~~~~~~~~~~

- Versions: 8.4.7
- Problem: Error when opening `Drive` gui
- Solution: None so far
- Github issue: `Logged slot transactions without event <https://github.com/SpongePowered/Sponge/issues/3680>`_

AlexsMobs
~~~~~~~~~

- Version: 1.10.1
- Problem: Crashes randomly
- Solution: None so far
- Github issue: `Exception exiting Phase crash from AlexsMobs <https://github.com/SpongePowered/Sponge/issues/3535>`_

Abnormals_core
~~~~~~~~~~~~~~

- Version: 3.3.1
- Problem: Crash on startup
- Solution: Use unoffical patched for Sponge version on the server. Download `Abnormals_core-1.16.5-3.3.1 <https://cdn.discordapp.com/attachments/406987481825804290/949798054117122058/abnormals_core-1.16.5-3.3.1.jar>`_

All The Mods 6
~~~~~~~~~~~~~~

- Version: 1.8.28
- Problem: Crash on startup
- Solution: Update SpongeForge to the latest
- Github issue: `SpongeForge 1.16.5 InvalidMixinException ATM6 <https://github.com/SpongePowered/Sponge/issues/3647>`_

Bountiful Baubles
~~~~~~~~~~~~~~~~~

- Version: 1.16.5-0.1.0-forge
- Problem: Crash on startup
- Solution: None so far
- Github issue: `Bountiful Baubles blows up SF 1.16.5 <https://github.com/SpongePowered/Sponge/issues/3646>`_

Conquest Reforged
~~~~~~~~~~~~~~~~~

- Version: 5.0.7
- Problem: Breaking a block with a snow layer in hand causes crash
- Solution: None so far
- Github issue: `Server crashes when creaking a block with snow layer in your hand <https://github.com/SpongePowered/Sponge/issues/3621>`_

Create Mod
~~~~~~~~~~

- Version: mc1.16.5_v0.3.2g
- Problem: Error when opening an inventory
- Solution: None so far
- Github issue: `Logged slot transactions without event <https://github.com/SpongePowered/Sponge/issues/3680>`_

Random patches
~~~~~~~~~~~~~~

- Version: 2.4.4
- Problem: Crash on startup
- Solution: Update SpongeForge to the latest
- Github issue: `Server crash with Randompatches <https://github.com/SpongePowered/Sponge/issues/3589>`_

Twilight Forest
~~~~~~~~~~~~~~~

- Version: 4.0.870
- Problem: Crash on startup
- Solution: Update SpongeForge to the latest
- Github issue: `SpongeForge has fatal errors with Twilight Forest mod <https://github.com/SpongePowered/Sponge/issues/3574>`_

Progressive Bosses
~~~~~~~~~~~~~~~~~~

- Version 3.4.3
- Problem: Crash on startup
- Solution: Update SpongeForge to the latest
- Github issue: `Illegal State Exception with Progressive Bosses mod <https://github.com/SpongePowered/Sponge/issues/3714>`_

Pehkui
~~~~~~

- Version: 3.4.2
- Problem: Crash on startup
- Solution: None so far
- Github issue: `SpongeForge incompatibility with pehkui <https://github.com/SpongePowered/Sponge/issues/3829>`_

Physics Mod
~~~~~~~~~~~

- Version: 1.3.4
- Problem: Lag on TNT use 
- Solution: None so far
- Github issue: `Fatel server lag when using TNT and Physics mod <https://github.com/SpongePowered/Sponge/issues/3517>`_

Tickers Construct
~~~~~~~~~~~~~~~~~

- Version: 1.16.5-3.1.2.265
- Problem: Error when opening an inventory
- Solution: None so far
- Github issue: `Tinkers Construct slot transaction spam with SpongeForge <https://github.com/SpongePowered/Sponge/issues/3527>`_

Valkyrien Skies
~~~~~~~~~~~~~~~

- Version: 116-2.0.0-alpha6
- Problem: Crash on startup
- Solution: None so far
- Github issue: `The server can't launch with Valkyrien Skies <https://github.com/SpongePowered/Sponge/issues/3809>`_

World Edit
~~~~~~~~~~

- Version: 7.2.5
- Problem: Errors after startup relating to commands
- Solution: None so far
- Github issue: `WorldEdit command registrar issue on server start <https://github.com/SpongePowered/Sponge/issues/3540>`_