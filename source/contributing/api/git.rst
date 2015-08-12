==========================
Git Workflow for SpongeAPI
==========================

Development of SpongeAPI is focused in a repository on GitHub. Thus, working with Git is an essential element of
contributing.

The process of working with Git is shown below, and assumes a basic knowledge of using the Terminal or command line
for your operating system:

1. Navigate to the directory in which you are working on the project (e.g. ``cd SpongeAPI``).

#. Ensure your master branch is up-to-date with ``git pull``.

#. Create a new branch with ``git checkout -b feature/your-feature-name-here``.

#. Complete your work in the branch - moving files, deleting files, or editing them.

#. Prior to making a commit, ensure the module will compile with ``gradle compileJava``. This will run a simple build
   of the source files.

#. When you are convinced that the build of your branch of SpongeAPI is okay, you may begin to commit. Use ``git status``
   and ``git add FILE(S)`` as necessary to look for files that are not yet staged for a commit.

#. When you are ready, do ``git commit``.

#. Edit the commit message. This message should be detailed, yet concise.

#. You can now push the commit to the repository with ``git push``.

.. tip::

    If you see this warning...

    ``fatal: The current branch YourBranchName has no upstream branch.``

    ...the branch may not be on the upstream remote. This may happen if this is the first time you are pushing a commit
    for the new branch. To push the current branch and set the remote as upstream, use

    ``git push --set-upstream origin YourBranchName``

You may open a pull request once your commit is pushed to the repository.
