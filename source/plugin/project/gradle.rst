=================
Setting Up Gradle
=================

Generally, everything necessary to compile a Sponge plugin using Gradle can be done by simply adding the SpongeAPI
dependency to your project:

.. code-block:: groovy

    repositories {
        mavenCentral()
        maven {
            name = 'sponge'
            url = 'http://repo.spongepowered.org/maven'
        }
    }

    dependencies {
        compile 'org.spongepowered:spongeapi:4.1.0'
    }

However, for further Gradle integration with :doc:`/plugin/plugin-meta`, we're providing an additional **Gradle
plugin** (called SpongeGradle_) for Sponge plugins to use which allows you to minimize the necessary configuration for
setting up a plugin project using Gradle.

Using SpongeGradle
==================

.. note::
    We recommend using **the latest Gradle version** together with SpongeGradle_. The Gradle plugin may not work
    properly with very old Gradle versions.

Using SpongeGradle_ is very simple and allows you to minimize the necessary Gradle configuration for setting up a
Sponge plugin on Gradle. Additionally, it provides integration for :doc:`/plugin/plugin-meta`, such as automatically
contributing the group, project name, version and description defined in your build script to the built plugin, so you
only need to update your plugin version in one file.

Below is a simple template that should be usable for most plugins. **Make sure to replace the group with the group ID
you have chosen before.**

.. code-block:: groovy

    plugins {
        id 'org.spongepowered.plugin' version '0.6'
    }

    group = 'com.example' // TODO
    version = '1.0-SNAPSHOT'
    description = 'An example plugin'

    dependencies {
        compile 'org.spongepowered:spongeapi:4.1.0'
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

.. _SpongeGradle: https://github.com/SpongePowered/SpongeGradle
