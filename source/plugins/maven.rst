=====
Maven
=====

Dependencies
============

.. code-block:: xml

    <repositories>
        <repository>
            <id>sponge-maven-repo</id>
            <name>Sponge maven repo</name>
            <url>http://repo.spongepowered.org/Sponge/maven</url>
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
            <version>1.0</version>
        </dependency>
    </dependencies>
