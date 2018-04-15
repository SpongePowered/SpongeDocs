==================
Performance Tweaks
==================

Sponge provides several performance enhancing and tweaking options to help you run a lag-free server, even
under heavy load.

.. note::
  While we try to improve the performance of every server, the performance gains depend on your setup. Please run some
  tests to ensure you configured your Sponge server to get the most out of it!

Entity Activation Range
=======================

This setting will alter the loading behaviour of entities around players. Lowering the value will only load close
entities, while raising it will also load entities that are far away from the player. Lower this to improve your
servers performance, especially with high entity and player counts. To disable activation range for a specific entity 
set its value to ``0``.

.. tip::
  It's possible to specify the activation range *per mob*. You can set ``auto-populate`` to ``true`` and Sponge
  will then add all available mobs to the activation range list, it is advised to disable it after the list is filled.
  If you add new mobs to the game, just repeat the procedure and those new mobs will also be added to the list below.

.. code-block:: none

    entity-activation-range {
        # If enabled, newly discovered entities will be added to this config with a default value.
        auto-populate=false
        # Default activation ranges used for all entities unless overridden.
        defaults {
            ambient=32
            aquatic=32
            creature=32
            misc=16
            monster=32
        }
    }

Async Lighting
==============

This setting will run lighting checks on a separate thread to improve performance.

.. code-block:: none

    optimizations {
        # Runs lighting updates async.
        async-lighting {
            # If enabled, runs lighting updates async.
            enabled=true
            # The amount of threads to dedicate for async lighting updates. (Default: 2)
            num-threads=2
        }
    }

Cache Tameable Owners
=====================

This setting will cache tameable entities owners' UUID to save constant lookups from the data watcher.

.. code-block:: none

    optimizations {
        # Caches tameable entities owners to avoid constant lookups against data watchers. If mods cause issue, disable.
        cache-tameable-owners=true
    }

Drops Pre Merge
===============

This setting will pre-process and potentially merge item drops to avoid spawning extra entities that are then merged
post-spawning.

.. code-block:: none

    optimizations {
        # If enabled, block item drops are pre-processed to avoid
        # having to spawn extra entities that will be merged post spawning.
        # Usually, Sponge is smart enough to determine when to attempt an item pre-merge
        # and when not to, however, in certain cases, some mods rely on items not being
        # pre-merged and actually spawned, in which case, the items will flow right through
        # without being merged.
        drops-pre-merge=true
    }
      
Panda Redstone
===============================

An alternative Redstone update algorithm, leads to less block updates when Redstone changes.

.. code-block:: none

    optimizations {
        # If enabled, uses Panda4494's Redstone implementation which improves performance.
        # See https://bugs.mojang.com/browse/MC-11193 for more information.
        # Note: This optimization has a few issues which is explained in the bug report. We are not responsible for any issues this may cause.
        panda-redstone=false
    }

Auto-Saving Interval Adjustment
===============================

Vanilla Minecraft defaults to saving all chunks every 900 ticks (45 seconds). If you wish to raise or
lower this interval, then change it in the servers ``global.conf`` file:

.. code-block:: none

    world {
        # The auto-save tick interval used when saving global player data.
        # Set to 0 to disable. (Default: 900) Note: 20 ticks is equivalent to 1 second.
        auto-player-save-interval=900

        # The auto-save tick interval used to save all loaded chunks in a world.
        # Set to 0 to disable. (Default: 900) Note: 20 ticks is equivalent to 1 second.
        auto-save-interval=900
    }

Reducing this interval increases the load on your server's CPU and storage, but reduces the data loss that might occur
if the server locks up or the power fails. Conversely, increasing the auto-save interval reduces the load on the
hardware, but at the expense of increasing the amount of in-game progress that could be lost in case of server failure.

Realtime
========

Enabling this will just improve your players experience when tick rate is low, it will not improve performance. 
A limited set of entities, tile entities, and world time will use real time instead of ticks to update.

.. code-block:: none

    modules {
        # Use real (wall) time instead of ticks as much as possible
        realtime=false
    }

One example of this is baby animals. Normally, they take 20 minutes to grow into an adult. However, if the server is 
lagging, each animal will receive fewer ticks thus increasing the time they take to grow up. This setting updates some 
of their logic to use the actual elapsed wall-clock time, rather than number of ticks. It will also apply to block 
breaking, so no more "breaking blocks multiple times".

Deny chunk requests
===================

.. warning::

  This is an experimental setting for performance gain, we recommend to not enable it when you have mods on the server 
  and to disable it if you experience any issues regarding the loading of tileentities.
  
.. code-block:: none

    world {
        # If enabled, any request for a chunk not currently loaded will be denied (exceptions apply for things like world gen and player movement). 
        # Note: As this is an experimental setting for performance gain, if you encounter any issues then we recommend disabling it.
        deny-chunk-requests=false
    }

When this option is enabled, mods requesting areas to be loaded to perform various tasks will be denied. Only players 
and specific world generation calls will be allowed to load new chunks.
