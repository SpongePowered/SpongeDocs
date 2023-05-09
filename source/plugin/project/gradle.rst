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

.. tip::
  The `Sponge plugin template <https://github.com/SpongePowered/sponge-plugin-template/>`__ is a `Template repository <https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template>`__
  that demonstrate SpongeGradle.

Below is a simple template that should be usable for most plugins. **Make sure to replace the group with the group ID
you have chosen before.**

.. code-block:: kotlin

    plugins {
        r"""`java-library`
        id("org.spongepowered.gradle.plugin") version "2.0.1"
    }

    // TODO: Change the following to match your information
    group = "com.example"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    sponge {
        apiVersion("8.0.0")
        license("CHANGEME")
        loader {
            name(PluginLoaders.JAVA_PLAIN)
            version("1.0")
        }
        plugin("example") {
            description("Just testing things...")
            entrypoint("org.spongepowered.example.Example")
        }
    }

    val javaTarget = 8 // Sponge targets a minimum of Java 8
    java {
        sourceCompatibility = JavaVersion.toVersion(javaTarget)
        targetCompatibility = JavaVersion.toVersion(javaTarget)
    }

    tasks.withType(JavaCompile::class).configureEach {
        options.apply {
            encoding = "utf-8" // Consistent source file encoding
            if (JavaVersion.current().isJava10Compatible) {
                release.set(javaTarget)
            }
        }
    }

These few lines handle most settings you would normally do manually:

* Basic Gradle Java setup
* Set your project to compile with Java 8
* Add Sponge's Maven repository (and Maven Central)
* Automatically includes the project name, description and :doc:`version <../project/version-numbers>` in
  :doc:`/plugin/plugin-meta`.

Overriding defaults
~~~~~~~~~~~~~~~~~~~

By default, the Gradle plugin will contribute the **plugin name**, **plugin version**, and **description** automatically
to :doc:`/plugin/plugin-meta` with defaults defined in the project properties. It is also possible to override these if
you want to specify them manually:

.. code-block:: kotlin

    sponge {
        apiVersion("8.0.0-SNAPSHOT")
        license("CHANGEME")
        loader {
            name(PluginLoaders.JAVA_PLAIN)
            version("1.0")
        }
        plugin("example") {
            displayName("Example")
            entrypoint("org.spongepowered.example.Example")
            description("Just testing things...")
            links {
                homepage("https://spongepowered.org")
                source("https://spongepowered.org/source")
                issues("https://spongepowered.org/issues")
            }
            contributor("Spongie") {
                description("Lead Developer")
            }
            dependency("spongeapi") {
                loadOrder(PluginDependency.LoadOrder.AFTER)
                optional(false)
            }
        }
    }

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
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "sponge"
        }
    }

    dependencies {
        compileOnlyApi("org.spongepowered:spongeapi:8.0.0")
    }

.. _SpongeGradle: https://github.com/SpongePowered/SpongeGradle