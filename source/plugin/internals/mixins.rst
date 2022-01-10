=============
Plugin Mixins
=============

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

`Mixins <https://github.com/SpongePowered/Mixin>`_ can be used to modify classes at runtime before they are loaded. You
can use them in plugins if you want to optimize a part of the game specifically for your server - without having to fork
Sponge. The modifications will be bundled directly with your plugin and are only active as long as the plugin is loaded.

.. seealso::

    `Mixin documentation <https://github.com/SpongePowered/Mixin/wiki>`_
        Mixin documentation including an introduction to Mixins.
    `Example plugin <https://github.com/SpongePowered/Cookbook/tree/master/Plugin/PluginMixinTest>`_
        Example plugin which uses Plugin Mixins to print a message when the server is starting.


Setup
-----

#. Add the Mixin library as dependency to your plugin:

    .. code-block:: groovy

        dependencies {
            compile 'org.spongepowered:mixin:0.7.11-SNAPSHOT'
        }

#. Add a new Mixin configuration for your plugin, e.g. ``mixins.myplugin.json`` inside your resource folder:

    .. code-block:: json

        {
            "required": true,
            "minVersion": "0.7.10",
            "package": "com.example.myplugin.mixin",
            "refmap": "mixins.myplugin.refmap.json",
            "target": "@env(DEFAULT)",
            "compatibilityLevel": "JAVA_8",
            "mixins": [
                "MixinMinecraftServer"
            ]
        }

#. Add a Mixin class to the specified package:

    .. code-block:: java

        package com.example.myplugin.mixin;

        import net.minecraft.server.MinecraftServer;
        import org.spongepowered.asm.mixin.Mixin;

        @Mixin(MinecraftServer.class)
        public abstract class MixinMinecraftServer {

        }

Debugging
`````````

Normally, the Mixin configuration is registered inside JAR manifest of the plugin. Since the plugin is not packaged in a
JAR while debugging inside the IDE you need specify the Mixins to apply as command line options:

#. Add a ``--mixin <mixin config file name>`` option for each Mixin configuration file to the program arguments of your
   run configuration:

    .. code-block:: bash

        --mixin mixins.myplugin.json

Production
``````````

If your Mixin is working in your development environment you still need to make some changes to make it work in
production:

#. Apply the `MixinGradle <https://github.com/SpongePowered/MixinGradle>`_ plugin to your build script:

    .. code-block:: groovy

        buildscript {
            repositories {
                maven {
                    name = 'sponge'
                    url = 'https://repo.spongepowered.org/repository/maven-public/'
                }
            }
            dependencies {
                classpath 'org.spongepowered:mixingradle:0.6-SNAPSHOT'
            }
        }

        apply plugin: 'org.spongepowered.mixin'

#. Set the refmap from your Mixin configuration:

    .. code-block:: groovy

        sourceSets {
            main {
                ext.refMap = "mixins.myplugin.refmap.json"
            }
        }

#. Add your Mixin configuration to the JAR manifest. The ``FMLCorePluginContainsFMLMod`` manifest entry is necessary if
   you want to load your Mixin on SpongeForge:

    .. code-block:: groovy

        jar {
            manifest.attributes(
                'TweakClass': 'org.spongepowered.asm.launch.MixinTweaker',
                'MixinConfigs': 'mixins.myplugin.json',
                'FMLCorePluginContainsFMLMod': 'true',
            )
        }

#. Make sure to re-build the plugin using Gradle. The Mixin should then get applied by SpongeVanilla and SpongeForge.

    .. code-block:: bash

        gradle clean build
