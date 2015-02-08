================================
Artifacts for Plugin Development
================================

Overview
========

Developers who are interested in creating plugins for Sponge can add the Sponge API as a dependency to their project. The dependencies of a project are specified as artifacts.

The following information may come in handy when you are setting up a project with Maven or Gradle.

* Maven Repository: http://repo.spongepowered.org/maven
* Maven Artifact: ``org.spongepowered:spongeapi:1.1-SNAPSHOT``

.. note::

    The syntax of Maven artifacts is ``<groupID>:<artifactID>:<version>`` Some IDEs, such as IntelliJ IDEA, allow you to input the full artifact string into a text field. Others, such as Eclipse, separate the Group ID, Artifact ID, and version.

Examples
========

Gradle
~~~~~~

.. code-block:: none

    repositories {
        mavenCentral()
        maven {
            name 'Sponge maven repo'
            url 'http://repo.spongepowered.org/maven'
        }
    }

    dependencies {
        compile "org.spongepowered:spongeapi:1.1-SNAPSHOT"
    }

Maven
~~~~~

.. code-block:: none

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
            <version>1.1-SNAPSHOT</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
