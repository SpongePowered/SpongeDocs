================
Setting Up Maven
================

If you already have a pom.xml set up you can simply add the following to the ``<project>`` block of
 your ``pom.xml`` to add the SpongeAPI dependency:

.. code-block:: xml

    <repositories>
        <repository>
            <id>sponge-repo</id>
            <name>Sponge Maven Repository</name>
            <url>https://repo.spongepowered.org/maven</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.spongepowered</groupId>
            <artifactId>spongeapi</artifactId>
            <version>7.2.0</version>
            <type>jar</type>
            <scope>provided</scope>
        </dependency>
    </dependencies>

If you are instead looking for a more complete example which you can tweak to your needs, you can
use the following as your basis:

.. code-block:: xml

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>

        <!-- Don't forget to change all necessary values to your own! -->
        <groupId>com.example</groupId>
        <artifactId>exampleid</artifactId>

        <name>ExamplePluginName</name>
        <version>1.0.0</version>

        <url>http://example.com/</url>
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties>
        <packaging>jar</packaging>

        <repositories>
            <repository>
                <id>sponge-repo</id>
                <url>https://repo.spongepowered.org/maven</url>
            </repository>
        </repositories>

        <dependencies>
            <dependency>
                <groupId>org.spongepowered</groupId>
                <artifactId>spongeapi</artifactId>
                <version>7.2.0</version>
                <type>jar</type>
                <optional>true</optional>
                <scope>provided</scope>
            </dependency>
        </dependencies>

        <build>
            <finalName>ExamplePluginName</finalName>
            <defaultGoal>clean install</defaultGoal>
            <resources>
                <resource>
                    <targetPath>.</targetPath>
                    <directory>${basedir}/</directory>
                </resource>
            </resources>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-clean-plugin</artifactId>
                    <version>3.1.0</version>
                </plugin>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-jar-plugin</artifactId>
                    <version>3.0.2</version>
                </plugin>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.7.0</version>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </project>

