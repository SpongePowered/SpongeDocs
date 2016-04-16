================
Setting Up Maven
================

Add the following to the ``<project>`` block of your ``pom.xml`` to add the SpongeAPI dependency:

.. code-block:: xml

    <repositories>
        <repository>
            <id>sponge</id>
            <url>http://repo.spongepowered.org/maven</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.spongepowered</groupId>
            <artifactId>spongeapi</artifactId>
            <version>4.0.3</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

Using the Plugin Archetype
==========================
Alternatively, Sponge has a simple archetype that generates the basic structure for a plugin.

The generated pom includes a release profile that generates gpg-signed jars for javadocs, binary, and sources as
recommended in the guidelines for submitting projects to Sonatype OSS (However, this option is not currently available
for Sponge plugins due to the fact that Sponge API is not currently hosted on Maven Central).

Properties
~~~~~~~~~~

The archetype plugin accepts a few properties:

============== ========================== =============================================================================
Property        Example value              Description
============== ========================== =============================================================================
groupId         io.github.user             The maven groupId, useful more for plugins used as dependencies, but should
                                           more or less match your package name
artifactId      myproject                  The project id, also used as plugin id and name of the generated folder
version         1.0-SNAPSHOT               The initial version for your plugin. Can (and should) be changed as
                                           development progresses
package         io.github.user.myproject   The package your plugin class will be generated in
githubProject   user/repo                  The Github project. If a value is specified that is not user/repo, issue
                                           tracking and SCM sections are added to the pom
============== ========================== =============================================================================

These can be specified as arguments to Maven in the form ``-Dproperty=value``

Usage
~~~~~
This archetype requires Maven 3 or newer. Invoke maven with the goal ``archetype:generate``. Maven will prompt for any
required properties, but optional properties must be specified on the command line.

.. code-block:: bash

    $ mvn archetype:generate -DarchetypeArtifactId=sponge-plugin-archetype -DarchetypeGroupId=org.spongepowered -DarchetypeRepository=http://repo.spongepowered.org/maven -DarchetypeVersion=1.1 -DgithubProject=waylon531/spongeparty

The first four arguments specify where maven will find the archetype and which version to use. The
``-DgithubProject=waylon531/spongeparty`` parameter is an optional property for the generated project and can be left
out if you do not intend to host your plugin on GitHub.

After your project has been generated, you need to import it in your IDE. See :doc:`../workspace/index` for details.
