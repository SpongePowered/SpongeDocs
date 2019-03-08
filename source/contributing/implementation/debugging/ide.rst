===============================
Debugging Sponge Within the IDE
===============================

.. note::
    See :doc:`/plugin/workspace/index`  and :doc:`/plugin/project/index` for more information about preparing your IDE 
    and dependencies.

    See the :ref:`runConfig` section and the following :ref:`usingDebugger` section on the Plugin Debugging page for 
    more information on debugging with an IDE.

    This article is currently based on IntelliJ. If you are an Eclipse user and feel like you can expand this article 
    to include Eclipse, you can do so on `our GitHub repository <https://github.com/spongepowered/spongedocs>`_. 

Using your IDE to debug Sponge is relatively straight-forward. For the most part, you import the ``build.gradle`` file 
into your IDE after you have :ref:`setupWorkspace`. Ensure you have JDK 8 installed and specified in your IDE, and you 
import the project as **a Gradle project**, then you are ready to run the code and perform tests.

Key Settings
============

Here are some settings to check to help minimize any problems. 

Gradle Plugin
-------------

Ensure the Gradle plugin is enabled. To do so, go to ``File`` -> ``Settings`` -> ``Plugins``. Look for the ``Gradle`` 
plugin in the ``Installed`` tab and verify a checkmark next to the plugin's name. If you do not see the Gradle plugin, 
search for it in the ``Marketplace`` tab and install it.

Project SDK
-----------

The project SDK should be ``1.8.0_20`` or higher. The project language level should be ``8 - Lambdas, type annotations 
etc.``

Run/Debug Configurations
------------------------

The Gradle task ``genIntelliJRuns`` will create the Run/Debug configurations for you. Run ``./gradlew genIntelliJRuns`` 
in the project's root directory. Perform this task **before** launching the IDE. Or, you can restart your IDE or 
relaunch the project after you run it. You should see ``Minecraft Client`` and ``Minecraft Server`` listed as 
application configurations and ``SpongeForge [jar]`` and ``SpongeForge [clean]`` listed as Gradle configurations.

Coremods
--------

.. note::
    Coremods do not pertain to SpongeVanilla. See the :doc:`/about/glossary` for a definition of coremod.

Except for the Sponge coremod, Gradle or other automation tools provided by Sponge do not set up coremods in your 
project. As a result, you must add them manually. To do so, specify each coremod in a comma-separated list with the 
``-Dfml.coreMods.load`` parameter in the VM options for your project. You can locate the VM options in the Run/Debug 
Configurations. 

.. tip::
    The **Mixins** section of :doc:`/plugin/practices/best` has a discussion on mixins, coremods, and other low-level 
    definitions.

    :doc:`mixin` also discusses other useful VM options in the **Output** section.

Classpath
---------

While in the Run/Debug Configurations window, verify the ``Use classpath of module:`` is populated. Click on the 
drop-down menu and select a module with ``_main`` in its name when it specifies ``<no module>``.

.. tip:: 

   When in doubt, select the project on which you are working. For example, select ``SpongeForge_main`` when working 
   with SpongeForge or ``SpongeVanilla_main`` when working with SpongeVanilla. 

Using Your IDE
==============

Now with everything working, you can do all of the usual tasks when in your IDE, such as setting breakpoints, stepping 
through code, examining variables, and evaluating expressions. You can also utilize regular aspects of the game, such 
as commands and log output.

Debugging With Mods And Plugins
-------------------------------

When debugging code with a mod or plugin, place a copy of the mod's or plugin's jar file in the ``run/mods`` directory. 
The IDE will allow you to examine the code and set breakpoints. However, you must specify the directory as a library.

To do this, open ``Project Settings`` and select the ``Libraries`` project component. Click on ``+`` near the top of 
the middle column. Click on ``Java`` and navigate to the ``mods`` folder and select it. Click ``OK``. You can now 
view source code and set breakpoints in the mods or plugins.

.. warning::

    Be sure to comply with all copyright notices and license agreements when using this feature. 
