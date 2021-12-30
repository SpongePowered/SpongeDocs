====================
Using MCP in Plugins
====================

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

ForgeGradle_ is a Gradle plugin which integrates the MCP workflow into the Gradle build system. It handles setting up
the workspace, as well as the re-obfuscation of your plugin.

.. note::
    Since ForgeGradle depends on Gradle, the following pages assume you are using Gradle to build your plugin. See
    :doc:`../project/gradle` to get started.

Configuring ForgeGradle
-----------------------
You can choose between two different types of workspaces:

- **Vanilla workspace:** Supports plugins for SpongeVanilla **and** SpongeForge.
- **Forge workspace:** Supports **only** plugins for SpongeForge (and **not** SpongeVanilla).

.. note::
    In most cases, the Vanilla workspace can be used for SpongeVanilla and SpongeForge. In some cases, there may be
    problems on one of the platforms because of changes in the Minecraft code by Forge. Make sure to always test your
    plugin on both platforms when using MCP.

Choosing an MCP mappings version
````````````````````````````````
To setup an MCP workspace you need to specify the MCP mappings version that will be used to de-obfuscate the Minecraft
source with human-readable names. A list of MCP mappings versions is available on the
`Export page of the MCPBot <http://export.mcpbot.bspk.rs>`_.

There are stable versions (released from time to time) and daily snapshots which contain the latest name changes. If you
do not need a specific name that was added recently, use a stable version (if available for your Minecraft version),
otherwise use the latest snapshot version.

Click the button for the version you want to use and select "Use in ForgeGradle". Then copy the provided version to your
Gradle build script (insert it in the ``YOUR_MAPPINGS_VERSION`` placeholder below).

Vanilla Workspace
`````````````````

.. code-block:: groovy

    buildscript {
        repositories {
            maven {
                name = 'forge'
                url = 'https://files.minecraftforge.net/maven'
            }
        }

        dependencies {
            classpath 'net.minecraftforge.gradle:ForgeGradle:2.3-SNAPSHOT'
        }
    }

    plugins {
        id 'org.spongepowered.plugin' version '0.9.0'
        id 'net.minecrell.vanillagradle.server' version '2.2-6'
    }

    dependencies {
        compile 'org.spongepowered:spongeapi:7.1.0'
    }

    minecraft {
        version = '1.12.2'
        // TODO: Replace with your mappings version, e.g. stable_39 or snapshot_20180814
        mappings = 'YOUR_MAPPINGS_VERSION'
    }

Forge Workspace
```````````````

.. code-block:: groovy

    buildscript {
        repositories {
            maven {
                name = 'forge'
                url = 'https://files.minecraftforge.net/maven'
            }
        }

        dependencies {
            classpath 'net.minecraftforge.gradle:ForgeGradle:2.3-SNAPSHOT'
        }
    }

    plugins {
        id 'org.spongepowered.plugin' version '0.9.0'
    }

    dependencies {
        compile 'org.spongepowered:spongeapi:7.1.0'
    }

    apply plugin: 'net.minecraftforge.gradle.forge'

    minecraft {
        // TODO: Configure Forge build here
        forgeVersion = '2705'
        // TODO: Replace with your mappings version, e.g. stable_39 or snapshot_20180814
        mappings = 'YOUR_MAPPINGS_VERSION'
    }

It is recommended to use the same Forge version as used by the SpongeForge build you are going to use.

.. note::

    Make sure you use a version that corresponds to your Minecraft version. Using a wrong version will probably cause
    inexplicable exceptions during the preparation of the workspace or during development. See also 
    :ref:`associated-minecraft-version`.

Setting Up the Workspace
------------------------
Every time you update the Minecraft or mappings version, or want to re-import your project, you need to start with setting
up your workspace using Gradle. To do that, run the ``setupDecompWorkspace`` Gradle task of your project, before
importing the project into your IDE:

.. code-block:: bash

    gradle setupDecompWorkspace

Now you can import your Gradle project, as described in :doc:`../project/gradle`. If your project is already imported,
make sure to refresh the Gradle configuration so your IDE can register the new Minecraft dependency.

Building Your Plugin
--------------------
ForgeGradle automatically configures your plugin to re-obfuscate to Searge mappings when building it so you can run it
in production. Make sure to use Gradle's ``build`` task, and not ``jar`` directly.

.. code-block:: bash

    gradle clean build

.. _ForgeGradle: https://github.com/MinecraftForge/ForgeGradle
