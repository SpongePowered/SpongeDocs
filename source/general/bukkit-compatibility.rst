Bukkit Compatibility
====================

A bumpy path from Bukkit to Sponge
----------------------------------

Migrating your existing server, or setting up a new one, you want to
know what you can keep and what to throw away. Bukkit and Craftbukkit
made it easier for you as a Server Admin, and we hope that Sponge will
fulfil the same role. However, there are many things that have changed.

Will Bukkit plugins still work?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

**NO**.

Server Plugins for Sponge must be written to use the Sponge API, or
employ some kind of compatibility layer to run (eg. Pore, still a
work-in-progress). A large number of Bukkit plugin developers have
offered to port their work to Sponge when it is ready. There is an
extensive list available on the Sponge Forums.

For more information on the Sponge server,
please read :doc:`admins/implementations`.

What about other tools?
~~~~~~~~~~~~~~~~~~~~~~~

Anything that relies on the Bukkit API is a dead duck. Sponge needs a
completely blank slate to work with, and will not be implementing the
old Bukkit functions as they were. New methods will be developed and
made available to supplant them, but this process will take time.

-  For example, drdanick's magnificent RemoteToolKit. It won't work,
   because it needs a plugin to interact with the server API.
-  Dynmap *will* work with Minecraft Forge, but will not be able to
   interact with Sponge until it is updated to use the Sponge API.
-  MCEdit and other common Minecraft tools will work as usual. Sponge
   won't affect your save files.

