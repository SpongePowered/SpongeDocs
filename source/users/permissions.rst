========================
Managing Who Can Do What
========================

You can configure who has access to what if you are running a server by making use of permissions.

Operator Level
==============

Minecraft comes with a simple way to give permissions: by setting users as operator (or "op" for short).
General information on op status can be found at http://minecraft.gamepedia.com/Op  

The abilities of op permission may be adjusted by altering the ``op-permission-level`` setting in the :doc:`server-properties` file.


A list of native Minecraft server commands available to players with op is available at
http://minecraft.gamepedia.com/Commands#Summary_of_commands


.. warning::
   Minecraft does not have any fine-grained permissions capacity, only op.
   This is a very high level of permission and should be reserved for trusted players.
   More complicated permission setups require the use of a permissions plugin or mod.
   Sponge is not a permissions-management plugin.
   
   
.. note::
   Some plugins and mods may also grant specific permissions to ops.
   
