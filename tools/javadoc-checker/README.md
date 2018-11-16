Javadoc-Checker
===============

A helper to search for invalid javadoc references.

Requirements
------------

* Java 10
* Maven 3.5

Usage
-----

This application takes two optional parameters: `[path_to_SpongeDocs/sources [checkDeprecated]]`

There a three variants to run this project.

### Direct

The simplest one is just run the following command in the command line:

````bash
mvn
````

This uses the API version as specified in the pom and assumes it is executed from within the SpongeDocs repo.

You can also pass command line arguments to this like:

````bash
mvn -Dexec.args="../../source true"
````

### Stand-alone

Or you can build the jar and then run the stand-alone variant:

````bash
mvn clean package
java -jar javadoc-checker-x.y.z-API-a.b.c.jar <args...>
````

### Independent

Or use the independent version that can be used to check any API version:

````bash
mvn clean package
java -cp "spongeapi-a.b.c-shaded.jar;javadoc-checker-x.y.z-independent.jar" org.spongepowered.docs.tools.javadoc.Main <args...>
````
