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
servers performance, especially with high entity and player counts.

.. tip::
  It's possible to specify the activation range *per mob*. You can set ``auto-populate`` to ``true`` and Sponge
  will then add all available mobs to the activation range list, it is advised to disable it after the list is filled.
  If you add new mobs to the game, just repeat the procedure and those new mobs will also be added to the list below.

.. code-block:: none

  entity-activation-range {
        # If enabled, newly discovered entities will be added to this config with
        # a default value.
        auto-populate=false

        # Default activation ranges used for all entities unless overidden.
        defaults {
            ambient=32
            aquatic=32
            creature=32
            misc=16
            monster=32
        }

Lighting Patch to Ignore Unloaded Chunks
========================================

Enabling this setting avoids loading lighting data from not yet generated chunks. This reduces disk access and chunk
generation and thus improves performances while applying light levels to blocks.

.. code-block:: none

  optimizations {
        # This prevents chunks being loaded for getting light values at specific
        # block positions. May have side effects.
        ignore-unloaded-chunks-on-get-light=true
        }

Cache Chunk Lookups
===================

This setting enables Sponge's internal chunk caching to improve the server's performance. It uses a small, additional
amount of memory for caching purposes. If you run out of memory, try to disable it.

.. code-block:: none

  optimizations {
        # Caches chunks internally for faster returns when querying at various
        # positions
        chunk-map-caching=true
        }

Reduce Hash Lookups on BlockState Queries
=========================================

Enabling this patch reduces hash lookups. This is achieved by removing a 2nd unnecessary lookup, which the server would
only need when a blockstate becomes null. If you run into issues, disable this patch to revert the server to vanilla
behaviour.

.. code-block:: none

  optimizations {
        # A simple patch to reduce a few sanity checks for the sake of speed when
        # performing block state operations
        fast-blockstate-lookup=true
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
