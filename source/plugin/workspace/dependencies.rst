
==============================
Adding Sponge API Dependencies
==============================

You must add the Sponge API as a dependency to your plugin in order to create a plugin with it.

The following information may come in handy when you are setting up a project with Maven or Gradle.

* Maven Repository: http://repo.spongepowered.org/maven
* Maven Artifact: ``org.spongepowered:spongeapi:3.1.0``

.. note::

    The syntax of Maven artifacts is ``<groupID>:<artifactID>:<version>`` Some IDEs, such as IntelliJ IDEA, allow you to
    input the full artifact string into a text field. Others, such as Eclipse, separate the Group ID, Artifact ID, and
    version.

Examples
========

Gradle
~~~~~~

.. code-block:: groovy

    repositories {
        mavenCentral()
        maven {
            name = 'sponge'
            url = 'http://repo.spongepowered.org/maven'
        }
    }

    dependencies {
        compile 'org.spongepowered:spongeapi:3.1.0'
    }

Maven
~~~~~

.. code-block:: xml

    <repositories>
        <repository>
            <id>sponge-maven-repo</id>
            <name>Sponge maven repo</name>
            <url>http://repo.spongepowered.org/maven</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.spongepowered</groupId>
            <artifactId>spongeapi</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
