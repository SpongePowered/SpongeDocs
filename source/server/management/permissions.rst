====================
Managing Permissions
====================

You can configure who has access to what if you are running a server by making use of :doc:`../../plugin/permissions`.
Specific permissions for Sponge, Forge and Minecraft commands are shown on the :doc:`../spongineer/commands` page.


Operator Level
==============

Minecraft comes with a simple way to give permissions: by setting users as operator (or "op" for short). General
information on op status can be found at https://minecraft.gamepedia.com/Op

The abilities of op permission may be adjusted by altering the ``op-permission-level`` setting in the
:doc:`../../server/getting-started/configuration/server-properties` file.


A list of native Minecraft server commands available to players with op can be found at the `Minecraft Wiki
<https://minecraft.gamepedia.com/Commands#Summary_of_commands>`_.


.. warning::
   Minecraft does not have any fine-grained permissions capacity, only op. This is a very high level of permission and
   should be reserved for trusted players. More complicated permission setups require the use of a permissions plugin
   or mod. Sponge is not a permissions-management plugin.


.. note::
   Some plugins and mods may also grant specific permissions to ops.
