======
Gradle
======

Dependencies
============

.. code-block:: groovy

    repositories {
        mavenCentral()
        maven {
            name 'Sponge maven repo'
            url 'http://repo.spongepowered.org/maven'
        }
    }

    dependencies {
        compile "org.spongepowered:spongeapi:1.0"
    }
