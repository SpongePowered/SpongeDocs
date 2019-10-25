===============
Debugging Mixin
===============

.. tip::
    The **Mixins** section of :doc:`/plugin/practices/best` has a discussion on mixins, coremods, and other low-level 
    definitions.

`Spongepowered Mixin <https://github.com/SpongePowered/Mixin>`_ is the subsystem that allows SpongeAPI and other 
programs to interface with Minecraft. This article is not to provide a comprehensive explanation of Mixin. Please see 
`Mixin's wiki <https://github.com/SpongePowered/Mixin/wiki>`_ for its documentation and support options.

.. note::
    Mixin has its own repository which you can clone and import into IntelliJ to find error messages and research 
    problems. Alternatively, you can specify your local clone of the Mixin repository as a Module in the Project 
    Settings of your Sponge implementation and debug Mixin.

Output
======

By default, a directory named ``.mixin.out`` is created in the ``run`` directory. The directory contains a sub-directory 
named ``audit`` with empty reports in text and comma-separated-values formatted files. These files are used if the 
``mixin.checks.interfaces`` option is enabled.

However, Mixin supports *Java System Properties* to enable various debugging and auditing features. There are times 
when you may need or want to see the results of the process. You can see the mixin output by supplying **one** of the 
following VM options:

+-------------------------------+---------------------------------------------------------+
| ``-Dmixin.debug=true``        | Turns on all debugging features                         |
+-------------------------------+---------------------------------------------------------+
| ``-Dmixin.debug.export=true`` | Turns on only the feature that sends the output to disk |
+-------------------------------+---------------------------------------------------------+

In IntelliJ, open the ``Run/Debug Configurations`` window by clicking on ``Run`` -> ``Edit Configurations...``. Be sure 
to add the options to the appropriate application (``Minecraft Client``, ``Minecraft Server``, or both).

A new directory named ``classes`` is created in the ``.mixin.out`` directory when one of these options is used. This 
directory contains the new class content from the mixin process in a standard package/class structure.

.. note::
    These options are not contingent upon having cloned the Mixin repository, adding Mixin as a module, or using the 
    Minecraft Development for IntelliJ plugin by DemonWav.

.. tip::
    See `Mixin Java System Properties <https://github.com/SpongePowered/Mixin/wiki/Mixin-Java-System-Properties>`_ 
    for more mixin VM options with explanations for each option.

Decompiling
===========

There are a several ways to view the class files from the mixin process.

* IDE

  Opening the file in your IDE will decompile it and display the source code

* Fernflower

  Having the fernflower jar on your runtime classpath will cause the class files to be decompiled as they are created. 

* `JD-Gui <https://java-decompiler.github.io/>`_ is a standalone graphical utility that displays Java source codes of class files.

Phases And Environments
=======================

Important to understanding Mixin is knowing that game execution is split into two distinct phases: ``pre-init`` and 
``default``. The ``pre-init`` phase occurs between starting the game (running a command, script, double-clicking a 
shortcut, clicking a "launch" button or whatever method you use to tell your computer to run Minecraft) and launching 
the game (all modifications are complete and the game starts up and enters its main loop). The ``default`` phase begins 
when the game is in its "ready to be loaded" state and is the same as simply launching Minecraft.

Changes occur to infrastructure classes (loader, transformers, etc.) during the ``pre-init`` phase. Mixin also gathers 
information pertaining to all other mixins during the ``pre-init`` phase and applies them during the ``default`` phase. 

Phases are handled by separating them into environments, where the ``pre-init`` **phase** is the ``PREINIT`` 
**environment** and the ``default`` **phase** is the ``DEFAULT`` **environment**.

.. tip::
    Environments are among the properties specified in mixin configuration files. Examples of how they are specified 
    are ``"target": "@env(PREINIT)"`` and ``"target": "@env(DEFAULT)"``

Configurations
==============

The primary resource Mixin requires is the configuration file(s). Each configuration file defines a mixin set - the 
mixins to be applied with that configuration. Any mixin not specified by a configuration is not applied even though the 
mixin may exist in the code. An application may have one to many sets; however, each set **must** contain mixins for 
only **one** environment. Another reason to separate configurations into multiple files is for organizational purposes.

Each mixin set (configuration file) is subdivided into three discrete areas: common, client, and server. The common 
mixins are mixed first followed by client **or** server, depending on the detected *side*. 

Who Started It?
===============

The Mixin subsystem interacts with all programs through a single instance of the subsystem, regardless of file, author, 
or organization. Yet, only one **starts** the subsystem, and it determines which version of Mixin is used. A problem 
occurs many times because an older version of Mixin is started. So, the order in which files are launched is important. 
Ideally, Sponge should launch first after Forge and before other tweakers and coremods. 

A Broken Mixin
==============

The game will crash when a class cannot be loaded, and this condition is known as a broken mixin. A broken mixin 
generally means a problem exists with the annotations and/or signature. For example, consider the following log 
snippet:  

.. code-block:: text

    MixinSpongeSmeltingRecipe.java:41: error: No obfuscation mapping for @Overwrite method
        default String getId() {
                       ^

This error was corrected by changing the ``@Overwrite`` annotation to ``@Overwrite(remap = false)``. The 
remap element set to *false* causes the annotation processor to skip this annotation when attempting to build the 
obfuscation table for the mixin.

Analysis of the source code might lead one to think the ``default`` keyword in the method declaration is the problem. 
Changing the keyword to ``static`` results in the following log snippet: 

.. code-block:: text

    MixinSpongeSmeltingRecipe.java:41: error: getId() in MixinSpongeSmeltingRecipe clashes with getId() in 
        static String getId() {
                      ^
      overriding method is static

    MixinSpongeSmeltingRecipe.java:40: error: method does not override or implement a method from a supertype
        @Override
        ^

    MixinSpongeSmeltingRecipe.java:42: error: non-static variable this cannot be referenced from a static context
            return CustomSmeltingRecipeIds.getDefaultId((SmeltingRecipe) this);
                                                                         ^

As you can see, three different errors occurred instead of the one error. The correct fix was adding the ``remap`` 
element as described above. Keep in mind, though, the point of this example is not to show how to solve this problem, 
but to demonstrate what a broken mixin looks like and to point out that most broken mixins are the result of incorrect 
annotations or signatures.

.. note::
    See the Mixin Wiki for a description on `methods' signatures 
    <https://github.com/SpongePowered/Mixin/wiki/Introduction-to-Mixins---Understanding-Mixin-Architecture#2-through-the-looking-glass>`_

    This section will be expanded in the future to list common causes of broken mixins and the solutions to fix them. 
    If you feel like you can help, you can do so on `our GitHub repository 
    <https://github.com/spongepowered/spongedocs>`_. 

Minecraft Development for IntelliJ
==================================

A useful tool for working with Mixin is the `Minecraft Development <https://github.com/minecraft-dev/MinecraftDev>`_ 
plugin for IntelliJ. You can debug and step through mixin code without having to deal with outputted class files from 
the mixin class loader. 

.. note::
    The plugin's `website <https://minecraftdev.org/>`_ provides information about installing and support options. A 
    link to its GitHub repository is also provided where you can contribute and/or learn more about the project.

Installing
----------

To use the plugin without cloning the source:

1. Open ``File -> Settings -> Plugins`` and select ``Marketplace``.
#. Search for *minecraft* in the search bar and select ``Minecraft Development by DemonWav``.
#. Click the ``Install`` button (do **not** restart IntelliJ *yet*).
#. Go to https://github.com/minecraft-dev/MinecraftDev and download a ZIP file of the repository.
#. Do **one** of the following:

   * Open your program of choice for extracting zip files. Navigate to the ``idea-configs`` directory and copy/extract 
     **the contents** of that directory to the ``.idea`` directory of your Sponge workspace.
   * Extract the zip file to a location on your drive.  Navigate to the ``idea-configs`` directory and copy **the 
     contents** of that directory to the ``.idea`` directory of your Sponge workspace.
#. Restart IntelliJ

The plugin will now be active and your project will have useful configurations and copyright settings.

Using
-----

Coming soon!

