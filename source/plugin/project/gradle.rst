=================
Setting Up Gradle
=================

.. _using-spongegradle:

Using SpongeGradle
==================

Using SpongeGradle_ is very simple and allows you to minimize the necessary Gradle configuration for setting up a
Sponge plugin on Gradle. Additionally, it provides integration for :doc:`/plugin/plugin-meta`, such as automatically
contributing the group, project name, version and description defined in your build script to the built plugin, so you
only need to update your plugin version in one file.

.. tip::
  Most problems are caused by attempting to use an outdated Gradle version. We recommend using the latest Gradle
  version together with SpongeGradle_. :ref:`The Gradle section of the build systems page <gradle-setup>` explains how
  to setup Gradle on your computer.

Below is a simple template that should be usable for most plugins. **Make sure to replace the group with the group ID
you have chosen before.**

.. code-block:: groovy

    plugins {
        id 'org.spongepowered.plugin' version '0.8.1'
    }

    group = 'com.example' // TODO
    version = '1.0-SNAPSHOT'
    description = 'An example plugin'

    dependencies {
        compile 'org.spongepowered:spongeapi:7.0.0'
    }

These few lines handle most settings you would normally do manually:

* Basic Gradle Java setup
* Set your project to compile with Java 8
* Add Sponge's Maven repository (and Maven Central)
* Set up a plugin with the project name in lower case as **plugin ID**
* Automatically includes the project name, description and version in :doc:`/plugin/plugin-meta`.

Manually setting the plugin ID
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

By default, the Gradle plugin will configure your **plugin ID** with project name (in lowercase) you have configured.
If you want to use a custom **plugin ID** and still use the :doc:`/plugin/plugin-meta` integration you can change it
manually:

.. code-block:: groovy

    sponge {
        plugin {
            id = 'mypluginid'
        }
    }

Overriding defaults
~~~~~~~~~~~~~~~~~~~

By default the Gradle plugin will contribute the **plugin name**, **plugin version** and **description** automatically
to :doc:`/plugin/plugin-meta` with defaults defined in the project properties. It is also possible to override these if
you want to specify them manually:

.. code-block:: groovy

    sponge {
        plugin {
            meta {
                name = 'My Plugin'
                version = '1.0.0'
                description = 'This is a plugin'
            }
        }
    }

You can also remove a default value entirely:

.. code-block:: groovy

    sponge {
        plugin {
            meta {
                description = null
            }
        }
    }

Without SpongeGradle
====================

.. warning::
  We recommend using :ref:`SpongeGradle <using-spongegradle>` for Gradle plugins since it will provide additional Gradle
  integration for Sponge plugins.

Generally, everything necessary to compile a Sponge plugin using Gradle can be done by simply adding SpongeAPI
dependency to your project:

.. code-block:: groovy

    repositories {
        mavenCentral()
        maven {
            name = 'sponge'
            url = 'https://repo.spongepowered.org/maven'
        }
    }

    dependencies {
        compile 'org.spongepowered:spongeapi:7.0.0'
    }

.. _SpongeGradle: https://github.com/SpongePowered/SpongeGradle
