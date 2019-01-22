===========
Git Commits
===========

The first thing in debugging is determining which state your local repo needs to be at. You can use the ``stable-x`` 
branch to learn the system and/or subsystems. However, researching a problem and working on a bug requires you to 
revert the repo to a certain state in the past. For instance, you decide to debug an issue which specifies Forge 
version: 2705 and SpongeForge version: 3371. You need to revert your repo to the point in time when the Sponge jar 
file was created for SpongeForge version 3371.

How do you do this? You determine the last commit in the Sponge jar file, and there are two ways to accomplish this.

1. MANIFEST.MF file

   If you have the Sponge jar file already, you can view the MANIFEST.MF file to obtain the last git-commit in the 
   file. The file is located in the META-INF directory and is a plain text file. It contains many key-value pairs; one 
   is the ``Git-Commit`` key. The value of this key is the last commit of the GitHub repository at the time the Sponge 
   jar file was created. Note the first 7-10 characters of the commit sha.

2. Sponge `Downloads <https://www.spongepowered.org/downloads>`_ page.
   
   Another method for determing the last commit in the file is to locate the build on the Sponge `Downloads`_ page. 
   Under the build's title, click on the first commit message. A new browser tab or window will open on GitHub with 
   the commit sha displayed on the right hand side along with other commit information. Note the first 7-10 characters 
   of the commit sha.

With the commit sha, you can now use ``git checkout --recurse-submodules -B <new_branch> <commit_sha>`` to create a 
new branch in your local repo set to the last commit in the Sponge jar file. A good practice is to give the new branch 
a name related to the issue or problem you are working on. You will delete the branch after your changes are merged, 
and the name will help you remember what the branch is for.

.. tip::
    The ``--recurse-submodules`` parameter ensures SpongeCommon and SpongeAPI are changed to the proper commit as well.

