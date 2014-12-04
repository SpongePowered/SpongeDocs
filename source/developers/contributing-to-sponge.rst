Contributing to Sponge
======================

Prerequisites
-------------

The setup required to develop plugins for Sponge is quite simple and requires three things.

- Java JDK installed
- Editor of your choice installed
- git client installed

Java JDK download
~~~~~~~~~~~~~~~~~

Download the JDK from Oracle first.

We recommended that you first check that you only have one version of Java installed at any point in time.
Please begin by uninstalling any old versions of Java you may have acquired in the past.

Then visit this site: http://www.oracle.com/technetwork/java/javase/downloads/index.html

Download and install the Java JDK. It's the upper blue button on the right-hand side of the main column.
real
If you cannot find it, try this link for a direct Java 7 JDK download:
http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

Make sure you install the JDK, not the JRE. The JDK, or Java Development Kit,
contains crucial tools for creating Java applications.

Let that install, then reboot your PC.

IDE download
~~~~~~~~~~~~

The second main download you need to perform is getting your IDE (Interactive Development Environment).

There are two commercially available IDEs that we provide instructions for; Eclipse, and IntelliJ IDEA.
Which one you choose is a matter of personal preference, and you might even prefer an IDE that isn't listed here.
That's up to you.

You can download Eclipse here: https://eclipse.org/downloads/    
(Choose "Eclipse IDE for Java Developers")

You can download IntelliJ IDEA here: https://www.jetbrains.com/idea/download/
(Choose the "Community Version" unless you want to pay for the Ultimate Edition.
You do not need the Ultimate Edition to develop plugins, as the Community Version still contains all the tools you need.)

git download
~~~~~~~~~~~~

The download page for git is here:  http://git-scm.com/downloads

Make sure to install the appropriate version for your operating system.

Linux users, git will be available on your distro's package manager, usually under the name "git".

Install it like any other application, and reboot.

Running Gradle
--------------

Once these are downloaded, installed, and you've rebooted (again), then you can begin to setup the workspace specific for Sponge.

(We're going to assume that your `PATH` is correct as well, each operating system has its own way of specifying new directories to search for commands. In Unix-based systems the environment variable is `$PATH`.  In Windows systems it is `%PATH%`%. It's advised to construct your profile in your system such that when you login to the computer, the `PATH` is setup correctly vs. manually updating the `PATH` before working with Sponge)

To get Sponge and built, now we need to use `git` and the tools we just downloaded:

Before we begin with that, a few words of advice:

Spaces in filenames are not ideal. 
Please do not use whitespace in filenames. 
As such, putting your repository into say, the Desktop, or in folders that contain whitespace filenames is a bad idea.

Keep the paths to your source tree as short as possible::

    C:\> mkdir c:\sp\main
    C:\> cd c:\sp\main


Now you're ready to begin the process of getting Sponge sources and beginning to build it for the first time.
Refer to the README.md page for the Sponge project for the most current version of instructions,
but these steps are generally going to be the same throughout the development of Sponge::

    C:\sp\main> git clone git@github.com:SpongePowered/Sponge.git

That will create a clone of the Sponge project on your PC. The process will create the directory Sponge::

    C:\sp\main> cd Sponge

We need to update the project by fetching the submodules linked to Sponge, namely the SpongeAPI module::

    C:\sp\main\Sponge> git submodule update --init --recursive

There is a commit-hook that is used to process checkins to the repository,
we need to put a copy of that hook in the `git` hooks folder::

    C:\sp\main\Sponge> copy scripts\pre-commit .git\hooks

(With Unix-based systems the command is just the same, but using cp and the path separators are /)::

    $ cp scripts/pre-commit .git/hooks

At this point you can begin to setup the workspace with gradle (which you do NOT need to download or install)

The catch is that the gradle build process needs to be aware of the location of your Java compiler.
So before running gradle for the first time to setup the workspace,
we need to specify the environment variable ``JAVA_HOME``.

``JAVA_HOME`` is an environment variable that is set in the shell.
You can set this up once in your login process for your PC or manually set it
each time you run gradle.  I prefer to put it into my per-login process so I don't need to manually set it.

``JAVA_HOME`` is a directory to where the JDK is installed:

If you installed Java as shown above then the JAVA_HOME would be set like this::

    C:\> set JAVA_HOME=c:\program files\java\jdk1.7.0_67

(jdk1.7.0.67 is the version I have at the moment, your version will differ if the download is recent)

To see exactly what the version (path) is then just type::

    C:\> dir C:\program files\java

and inspect the output for the name of the directory that contains the JDK.


Once JAVA_HOME is set then we can run gradle to setup the workspace:

``C:\> gradle setupDecompWorkspace --refresh-dependencies``

This process will do several things:

It will download jar files that are required for Sponge to build.
It will download Forge which is required for Sponge to build.

(remember we're building Sponge, not plugins for Sponge)

Sponge plugins DO NOT need Forge to build (nor should they ever need Forge to build).


This process will take a few minutes depending on your network connection.  It is a process that requires a network connection so be sure you are online for that step.

At this point the output will end with a message like this::

    C:\sp\main\Sponge>gradle setupDecompWorkspace --refresh-dependencies
    ****************************
     Powered By MCP:
     http://mcp.ocean-labs.de/
     Searge, ProfMobius, Fesh0r,
     R4wk, ZeuX, IngisKahn, bspkrs
     MCP Data version : unknown
    ****************************
    :extractMcpData UP-TO-DATE
    :getVersionJson
    :extractUserDev UP-TO-DATE
    :genSrgs SKIPPED
    :extractNatives UP-TO-DATE
    :copyNativesLegacy UP-TO-DATE
    :getAssetsIndex
    :getAssets
    :makeStart
    :downloadMcpTools
    :downloadClient SKIPPED
    :downloadServer SKIPPED
    :mergeJars SKIPPED
    :deobfuscateJar SKIPPED
    :decompile SKIPPED
    :processSources SKIPPED
    :remapJar SKIPPED
    :extractMinecraftSrc SKIPPED
    :recompMinecraft SKIPPED
    :repackMinecraft SKIPPED
    :setupDecompWorkspace

    BUILD SUCCESSFUL

    Total time: 2 mins 45.216 secs
    C:\sp\main\Sponge>

Next Steps
----------

From here you should follow the steps on the SpongePowered/Sponge README.md file to configure your IDE
(Eclipse or InteliJ) to import the Sponge Project and build/run the Sponge Artifact within the IDE.

The next wiki article on [[Debugging Sponge Within the IDE]]
will explain how to setup your IDE to start, run and debug Sponge within the IDE.
