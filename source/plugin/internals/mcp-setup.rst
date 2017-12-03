====================
Using MCP in Plugins
====================

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

Choosing a MCP mappings version
```````````````````````````````
To setup a MCP workspace you need to specify the MCP mappings version that will be used to de-obfuscate the Minecraft
source with human-readable names. A list of MCP mappings versions is available on the
`Export page of the MCPBot <http://export.mcpbot.bspk.rs>`_.

There are stable versions (released from time to time) and daily snapshots which contain the latest name changes. If you
do not need a specific name that was added recently, use a stable version (if available for your Minecraft version),
otherwise use the latest snapshot version.

Click the button for the version you want to use and select "Use in ForgeGradle". Then copy the provided version to your
Gradle build script (insert it in the ``snapshot_xxx`` placeholder below).

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
            classpath 'net.minecraftforge.gradle:ForgeGradle:2.2-SNAPSHOT'
        }
    }

    plugins {
        id 'org.spongepowered.plugin' version '0.8.1'
        id 'net.minecrell.vanillagradle.server' version '2.2-3'
    }

    minecraft {
        version = '1.10.2'
        // TODO: Replace with your mappings version, e.g. snapshot_20170120
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
            classpath 'net.minecraftforge.gradle:ForgeGradle:2.2-SNAPSHOT'
        }
    }

    plugins {
        id 'org.spongepowered.plugin' version '0.8.1'
    }

    apply plugin: 'net.minecraftforge.gradle.forge'

    minecraft {
        forgeVersion = '1944' // TODO: Configure Forge build here
        // TODO: Replace with your mappings version, e.g. snapshot_20170120
        mappings = 'YOUR_MAPPINGS_VERSION'
    }

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
