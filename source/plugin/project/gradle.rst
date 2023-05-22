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

Below is a simple template in Kotlin format that should be usable for most plugins. **Make sure to replace the group with the group ID
you have chosen before.**

.. code-block:: kotlin

    plugins {
        id 'java-library'
        id("org.spongepowered.plugin") version '0.11.3'
    }

    // This may not be required, but has solved issues in the past
    compileJava.options.encoding = 'UTF-8'

    // TODO: Change the following to match your information
    group = 'com.example'
    version = '1.0.0-SNAPSHOT'
    description = 'Here lies an example plugin definition'

    repositories {
        mavenCentral()
    }

    sponge {
        apiVersion("8.1.0")
        licence("MIT")
        loader {
            name(PluginLoaders.JAVA_PLAIN)
            version("1.0")
        }
        plugin("**plugin Id**") {
            displayName("**Plugin Name**")
            entrypoint("**Plugin Entrypoint**")
            description("**Plugin Description**")
            dependency("spongeapi") {
                loadOrder(PluginDependency.LoadOrder.AFTER)
                optional(false)
            }
        }
    }

These few lines handle most settings you would normally do manually:

* Basic Gradle Java setup
* Add Sponge's Maven repository (and Maven Central)
* Set up a plugin with the project name in lower case as **plugin ID**
* Automatically includes the project name, description and :doc:`version <../project/version-numbers>` in
  :doc:`/plugin/plugin-meta`.

Without SpongeGradle
====================

.. warning::
  We recommend using :ref:`SpongeGradle <using-spongegradle>` for Gradle plugins since it will provide additional Gradle
  integration for Sponge plugins.

Generally, everything necessary to compile a Sponge plugin using Gradle can be done by simply adding the SpongeAPI
dependency to your project:

.. code-block:: kotlin

    repositories {
        mavenCentral()
        maven {
            name = 'sponge-repo'
            url = 'https://repo.spongepowered.org/repository/maven-public/'
        }
    }

    dependencies {
        compile 'org.spongepowered:spongeapi:8.0.0'
    }

.. _SpongeGradle: https://github.com/SpongePowered/SpongeGradle
