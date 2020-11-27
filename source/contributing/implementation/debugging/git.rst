===
Git
===

This article explains some useful features of Git which may assist you.

.. note::
    See :doc:`../../howtogit` for more information about Git, branches, and using the rebase command. Please be sure 
    to follow the **Squashing with Rebase** section.

Rebase
======

There are times when the project you are working on takes longer to complete than planned, and your branch falls 
behind the target branch and cannot get merged without further testing. To capture the other changes in your branch for 
testing, use the ``git rebase`` command.

Before using the command, ensure your local repository has the changes with the ``git pull`` command or the ``git 
fetch`` / ``git merge`` commands. Once your local repository is updated with the changes,

1. Ensure you are in the project's branch
#. Run ``git rebase`` *target branch*

For example, consider your project's branch name is ``feature`` and the target branch is ``bleeding``. After updating 
your local repository with any changes to the ``bleeding`` branch, checkout branch ``feature`` and run ``git rebase 
bleeding``. Your ``feature`` branch will now have the changes included and you can test your own changes with them to 
ensure no issues are introduced.

Finding A Commit
================

Knowing a commit can sometimes help in debugging, and there are many ways to identify them. Below are some ways to 
find a commit:

1. Git log

   At a command prompt, run ``git log`` and Git will begin displaying changes made to the repository.

   In an IDE, use the IDE's version control feature to display the log. Check your IDE's documentation to learn the 
   different ways to do this.

#. MANIFEST.MF file

   If you have the Sponge jar file already, you can view the MANIFEST.MF file to obtain the last git-commit in the 
   file. The file is located in the META-INF directory and contains many key-value pairs. One is the ``Git-Commit`` key 
   and the value of this key is the last commit of the GitHub repository at the time the Sponge jar file was created.

#. Sponge `Downloads <https://www.spongepowered.org/downloads/>`_ page.
   
   Another method for determing the last commit in the file is to locate the build on the Sponge `Downloads`_ page. 
   Under the build's title, click on the first commit message. A new browser tab or window will open on GitHub with 
   the commit displayed on the right hand side along with other commit information.

.. tip::
    For a summary of changes and a quick find for a commit, use ``git log --oneline -x``, where ``x`` can be any 
    numeric value representing the number of commits to display.

Checking Out a Branch
=====================

A good practice when preparing to debug is to create a new branch and give it a name related to the issue you are 
working on. You will delete it after your changes are merged, but the name will help you remember the purpose of the 
branch.

.. note::
    It is important to create the new branch from the appropriate target branch. See :doc:`../../versioning` for more 
    information.

With a name picked out and the appropriate branch to create from checked out, you can now use ``git checkout 
--recurse-submodules -B <new_branch>`` to create the new branch. Git creates the new branch and switches your 
repository to it. Now, you are ready to make any necessary adjustments to settings in your workspace or IDE.

.. tip::
    The ``--recurse-submodules`` parameter ensures SpongeCommon and SpongeAPI are changed to the proper commit as well.

