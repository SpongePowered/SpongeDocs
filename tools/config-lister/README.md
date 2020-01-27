Config-Lister
=============

A library that generates a page that explains the configuration options in Sponge.

Requirements
------------

* Java 8
* Maven 3.5

Usage
-----

1. Update the SpongeCommon dependendy to the latest recommended release in the pom.
2. And then run the generated jar file:

````bash
java -jar config-lister.jar
````

or run the maven command:

````bash
mvn exec:java
````

3. Copy the output from your console into `source/server/getting-started/configuration/sponge-conf.rst`  
   and update the reference to the SpongeCommon version on that page