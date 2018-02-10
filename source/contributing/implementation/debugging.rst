===============================
Debugging Sponge Within the IDE
===============================

.. note::

    See the `related GitHub Issue
    <https://github.com/SpongePowered/SpongeDocs/issues/356>`_ for more information on what is required.

Debugging SpongeCommon
======================

By the point you debug, you should already finish making changes in your own branch in your fork (see :doc:`../howtogit`).

Unfortunately, SpongeCommon exists as an abstract layer to reduce duplicate code between SpongeForge and SpongeVanilla; thus, it is not for production environments, and you cannot run SpongeCommon directly. However, you can run SpongeForge or SpongeVanilla with the changes you've applied in SpongeCommon. Below is a description on testing SpongeCommon changes on SpongeForge.

Assume you have a branch ``fix/foo``, based on ``bleeding`` branch of ``SpongePowered/SpongeCommon``, on your SpongeCommon fork. You want to test it against the corresponding SpongeForge (or the ``bleeding`` branch of it). The procedure is as follows:

1. Clone SpongeForge repository from GitHub: Find a clean workspace, run ``git clone folder https://github.com/SpongePowered/SpongeForge.git`` and ``cd folder`` so that you are in the folder of the SpongeForge clone. (You can replace ``folder`` with any folder name you want)

2. Check out your SpongeCommon branch in the submodule: run ``cd SpongeCommon`` to enter the SpongeCommon folder. Run ``git remote add myRemote https://github.com/myAccount/SpongeCommon.git`` (Replace ``myAccount`` with your account or organization where your SpongeCommon fork is located). Then, run ``git fetch myRemote`` and ``git checkout myRemote/fix/foo`` so that the SpongeCommon submodule switches to your changes for testing.

3. Run Minecraft client. Run ``cd ../`` to return to the base repo of SpongeForge. Then, run ``./gradlew setupDecompWorkspace`` for the prerequisites of running Minecraft client. Once the ``setupDecompWorkspace`` task completed successfully, you may open your IDE or sync your gradle project in your IDE so that latest changes by gradle tasks are properly reflected. 

Then, for Intellij Idea users, when Intellij Idea finishes all background processes, run ``./gradlew genIntellijRuns`` to generate run configurations for Minecraft client and server. Go to ``run->Edit Configurations``, you will see a Minecraft Client and a Minecraft Server configuration in the Application section of all available configurations. Select one of the configuration, run and test your code.
