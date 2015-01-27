================================
Git-Fu for Sponge Implementation
================================

1. ``cd Sponge``

#. ``git pull``

.. tip::
    Bring your master branch current before branching.  ``git pull`` brings your branch (master at this point) up to date before branching it.

#. ``git checkout -b YourBranchName``

.. tip::
    The name of your branch should follow a convention.  Please try to use a name like ``feature/your-feature``

#.  Work with the files, create files, etc..  Do your work in the branch.

#.  When you're done working with the files you can do a check that the
    module will compile.  This would be a good idea before even making
    the commit.

.. tip::
    Run ``gradle compileJava`` to run a simple build of the source files.

#.  When you're convinced that the build of your branch of the Sponge is
    ok then you can commit. 

#.  ``git status``

#.  Look for files that are noted as not staged for commit.   Add the
    files that need to be staged for commit.

#.  ``git add FILE(s)``

#.  Since you're working with the implementation, there is a chance that
    your work also included work in the API.  It's OK to work in both
    at the same time.  The trick is to make sure when you commit and push
    you do so that the pointers for which version of SpongeAPI are matched
    to your version of the branch (your commit).  To ensure that, before
    you finish commit on your implementation work, double check the status
    of the working area.  You may need to add the submodules to the commit.

#.  ``git add SpongeAPI``    (May be required)
#.  ``git add Mixin``        (May be required)
#.  ``git status``           (Look for unstaged commits.  You should have already commited your SpongeAPI changes and pushed them before moving forward with the implementation commit and push).

#.  ``git commit``

#.  Edit the commit message.  This message should be short and concise.

#.  Now that you've made the commit, you can push this commit to the 
    repository.

#.  ``git push``

.. tip::
    If this is the first time you're pushing a commit for this new branch then the branch is not on the upstream remote.  You may see this warning:

    ``fatal: The current branch YourBranchName has no upstream branch.``
    
    To push the current branch and set the remote as upstream, use

    ``git push --set-upstream origin YourBranchName``

#.  Once your commit is pushed to the repository, then you can open
    a pull request

