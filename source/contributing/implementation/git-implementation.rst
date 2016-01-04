========================================
Git Workflow for API and Implementations
========================================

Developing the API
==================

The basic process of adding your changes is explained in the :doc:`../howtogit` section. On top of that we suggest that
you create your new branch with a meaningful name.With the new branching model you need to be aware which
branch you need to base your PRs on and where it should get merged afterwards. Read about the new branching and
versioning model here: :doc:`../versioning`

Additionally we require that you ensure the module will compile with ``gradle compileJava``.
This will run a simple build of the source files. When finished successfully, you can PR your changes to the SpongeAPI
repo.

Developing the Implementation
=============================

The process for the implementations is almost the same as for the API. You add your changes as described in :doc:`../howtogit`.
Note that you should give your branches a meaningful name. With the new branching model you need to be aware which
branch you need to base your PRs on and where it should get merged afterwards. Read about the new branching and
versioning model here: :doc:`../versioning`

Run ``gradle compileJava`` to check if everything compiles without errors.

Since you are working on the implementation, there is a possibility that your work included changes in the API. This is
okay. Just remember to ensure the pointers for the version of the SpongeAPI match the version of your branch prior to
committing and pushing. To do this, you may need to add the submodules to the commit (with ``git add SpongeAPI`` and/or
``git add Mixin``) prior to committing on your implementation work.

You may open a pull request once your commit is pushed to your fork or the repository.
