Code-Checker
============

A helper to extract the code blocks from the docs for the analysis, whether the code still compiles and is up to date.

Requirements
------------

* Java 10
* Maven 3.5

Usage
-----

This application takes two optional parameters: `[path_to_SpongeDocs/sources [path_for_generated_sources]]`

There a two variants to run this project.

### Direct

The simplest one is just run the following command in the command line:

````bash
mvn
````

This way assumes it is executed from within the SpongeDocs repo.

You can also pass command line arguments to this like:

````bash
mvn -Dexec.args="../../source target/generated-sources/codeblocks"
````

### Stand-alone

Or you can build the jar and then run it:

````bash
mvn clean package
java -jar code-checker-x.y.z.jar <args...>
````
