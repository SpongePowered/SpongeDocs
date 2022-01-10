=================
Setting Up Gradle
=================

.. _using-spongegradle:

.. warning::
    These docs were written for SpongeAPI 7 and are likely out of date. 
    `If you feel like you can help update them, please submit a PR! <https://github.com/SpongePowered/SpongeDocs>`__

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
        id 'java'
        id 'org.spongepowered.plugin' version '0.11.3'
    }

    // This may not be required, but has solved issues in the past
    compileJava.options.encoding = 'UTF-8'

    // TODO: Change the following to match your information
    group = 'com.example'
    version = '1.0.0-SNAPSHOT'
    description = 'Here lies an example plugin definition'

    repositories {
        jcenter()
    }

    dependencies {
        compile 'org.spongepowered:spongeapi:8.0.0'
    }

These few lines handle most settings you would normally do manually:

* Basic Gradle Java setup
* Set your project to compile with Java 8
* Add Sponge's Maven repository (and Maven Central)
* Set up a plugin with the project name in lower case as **plugin ID**
* Automatically includes the project name, description and :doc:`version <../project/version-numbers>` in
  :doc:`/plugin/plugin-meta`.

Manually setting the plugin ID
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

By default, the Gradle plugin will configure your **plugin ID** with project name (in lowercase) you have configured.
If you want to use a custom **plugin ID** and still use the :doc:`/plugin/plugin-meta` integration you can change it
manually:

.. code-block:: groovy

    sponge {
        plugin {
            id = 'pluginidgoeshere'
        }
    }

Overriding defaults
~~~~~~~~~~~~~~~~~~~

By default, the Gradle plugin will contribute the **plugin name**, **plugin version**, and **description** automatically
to :doc:`/plugin/plugin-meta` with defaults defined in the project properties. It is also possible to override these if
you want to specify them manually:

.. code-block:: groovy

    sponge {
        plugin {
            meta {
                name = 'Example Plugin'
                version = '1.0.0-SNAPSHOT'
                description = 'This is an example plugin'
                url = 'http://www.example.com/'
            }
        }
    }

You can also remove a default value entirely:

.. code-block:: groovy

    sponge {
        plugin {
            meta {
                name = null
                description = null
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

.. code-block:: groovy

    repositories {
        jcenter()
        maven {
            name = 'sponge-repo'
            url = 'https://repo.spongepowered.org/repository/maven-public/'
        }
    }

    dependencies {
        compile 'org.spongepowered:spongeapi:8.0.0'
    }

.. _SpongeGradle: https://github.com/SpongePowered/SpongeGradle
