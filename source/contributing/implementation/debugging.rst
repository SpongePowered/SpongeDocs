===============================
Debugging Sponge Within the IDE
===============================

.. note::

    This documentation is not yet complete see the `related GitHub Issue
    <https://github.com/SpongePowered/SpongeDocs/issues/356>`_ for more information on what is required.

Debugging SpongeCommon
======================

By the point you debug, you should already finish making changes in your own branch in your fork (see
:doc:`../howtogit`).

Unfortunately, SpongeCommon exists as an abstract layer to reduce duplicate code between SpongeForge and SpongeVanilla;
thus, it is not for production environments, and you cannot run SpongeCommon directly. However, you can run SpongeForge
or SpongeVanilla with the changes you've applied in SpongeCommon. Below is a description on testing SpongeCommon changes
on SpongeForge.

Assume you have a branch ``fix/foo``, based on ``bleeding`` branch of ``SpongePowered/SpongeCommon``, on your
SpongeCommon fork. You want to test it against the corresponding SpongeForge (or the ``bleeding`` branch of it). The
procedure is as follows: (run the commands below in the Git Gui)

1. Clone SpongeForge repository from GitHub: Find a clean workspace, run 
   ``git clone https://github.com/SpongePowered/SpongeForge`` and ``cd SpongeForge`` so that you are in the folder
   of the SpongeForge clone.
   
2. Check out your SpongeCommon branch in the submodule: Run ``cd SpongeCommon`` to enter the SpongeCommon folder. Run
   ``git remote add myRemote https://github.com/myAccount/SpongeCommon.git`` (Replace ``myAccount`` with your account or
   organization where your SpongeCommon fork is located; replace ``myRemote`` with the choice of your remote name if you
   had a different one). Then, run ``git fetch myRemote`` and  ``git checkout myRemote/fix/foo`` so that the SpongeCommon
   submodule switches to your changes for testing.

3. Run Minecraft client. Run ``cd ../`` to return to the base repo of SpongeForge. Then, run
   ``./gradlew setupDecompWorkspace`` for the prerequisites of running Minecraft client. 
   
Intellij Idea specific operations:

Close Intellij Idea if it is open.

Run ``./gradlew idea genIntellijRuns`` to generate run configurations for Minecraft client and server.

Once all gradle takes completed, you may open Intellij Idea and sync the Gradle project (click on a blue refresh icon on
the top left of the Gradle window) so that latest changes by gradle tasks are properly reflected.

Go to ``run->Edit Configurations``. You will see a ``Minecraft Client`` and a ``Minecraft Server`` configuration in the
``Application`` section of all available configurations. You need to update the ``use classpath of module`` section for
the configuration you want to run to ``SpongeForge_main`` so that it can run properly.

Before you run the configurations, consider adding some Java System Properties to help debugging, such as `Mixin
Java System Properties <https://github.com/SpongePowered/Mixin/wiki/Mixin-Java-System-Properties>`_.

Finally, you can run the configuration and test the changes.
