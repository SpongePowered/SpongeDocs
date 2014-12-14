=====================
Migrating from Bukkit
=====================

Do Bukkit Plugins Work?
=======================

No, unfortunately they do not. The Sponge API is different from the Bukkit API, so Bukkit plugins are incompatible.


.. note::
  Although Bukkit plugins are incompatible with Sponge, it may be possible to convert the configuration files
  used by plugins. This depends on how (and if) each plugin is re-implemented in Sponge.
  The same can be said of database files and other data storage structures.
  
.. warning::
  Craftbukkit relocates the ``world_the_end`` and ``world_nether`` folders outside the original ``world`` folder.
  Forge does not follow this behaviour, and therefore migrating an existing Craftbukkit server world may
  require moving the relevant folders back to their expected places.
