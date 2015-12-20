===============
How to Git(Hub)
===============

If you want to assist in creating Sponge, you have an awesome addition to the API, or you want to improve our Docs,
then you'll need to become familiar with ``git`` and GitHub. If you're already familiar with forking, branches,
issues, pull-requests and commits, then just skip this topic. If you have no clue what we're talking about, then read on.

.. note::
  This guide assumes that you've read :doc:`../preparing/git` and that you've already setup your machine with a Git
  client of your choice.

The Basic Concept of Git and GitHub
===================================

Git allows many different developers to develop a single piece of software at the same time. Before we start, here
is a short glossary to help you to get familiar with the topic:

+---------------------------------------+----------------------------------------------------------------+
|Git                                    | Tool to manage a project with many contributors                |
+---------------------------------------+----------------------------------------------------------------+
|GitHub                                 | Website where different organizations can store their          |
|                                       | repositories uses Git to manage the repositiories              |
+---------------------------------------+----------------------------------------------------------------+
|Repository                             | Folder where a project (for example Sponge) is stored at       |
+---------------------------------------+----------------------------------------------------------------+
|Fork                                   | A copy of a repository, usually by you or another user         |
+---------------------------------------+----------------------------------------------------------------+
|Branch                                 | .. todo: add branch explanation here                           |
+---------------------------------------+----------------------------------------------------------------+
|Pull-Request "PR"                      | A proposed addition or change to a repository                  |
+---------------------------------------+----------------------------------------------------------------+
|Issue                                  | The method to report problems and propose improvements to a    |
|                                       | repository.                                                    |
+---------------------------------------+----------------------------------------------------------------+
|Commit                                 | Changes to the repository that are packed to a single package  |
+---------------------------------------+----------------------------------------------------------------+

Now that you know some of the vocabulary, let's put these terms into context - beginning with the *repository*.
The repository (*repo*) is the place where a project stores its files. The SpongePowered repositories are located at
`GitHub <http://github.com/spongepowered>`__. However, this repo has some access restrictions to preserve it from
unwanted or malicious changes. You can't simply make changes yourself, as the repo is read-only for regular users.
Now you may wonder how you're supposed to file proposals and changes. Well, that's where *forks* come into play.
You can grab a copy of the SpongePowered repos, and make your changes there. When you're done, you open it as a PR
on our repository. Your proposed additions and changes can then be reviewed, and staff will tell you if something is
wrong, or needs improvement, and eventually merge the final PR.

Here's a short summary of the procedure described above, before we go into detail:

1. Fork the repo of your choice
#. Clone it to your local machine
#. Create a new branch
#. Make the desired changes
#. Test if everything works
#. Commit the changes
#. Sync them to GitHub
#. Propose the changes in a PR to the SpongePowered Repo
#. Amend to your PR if necessary
#. Your PR gets pulled into master by staff

Spare Me the Details!
=====================

1. Forking a Repo
-----------------

Now that you know the basic concept, we'll discuss the details. First you need to fork the repository you want to
make changes to. This can be done on GitHub.com, where you'll find a ``Fork`` button at the top of the repositories page.
After pressing it, GitHub will do some work and present a clone of the original repo to you. You'll notice that the
clone is now located at ``YourGitHubAccount/ClonedRepoName``. Alright, first step completed.

2. Cloning the Fork to Your Machine
-----------------------------------

Now you need to get this fork to your local machine to make your changes. Open the Git Client of your choice
(:doc:`../preparing/git`) and ``clone`` your fork to your local machine. The client will ask you for a folder to store
everything in. Second step finished, well done! 

Alternatively you can do this via CLI. Note that you need to create the folder everything is getting cloned
to yourself:

.. code-block:: none

  git clone git://github.com/YourGitHubAccount/ClonedRepoName.git

3. Creating a New Branch
------------------------

Now that you have a local copy of your fork, it's time to introduce branches. Branches were designed to be able
to develop and test different features or additions at the same time, without causing problems and errors due to
interferences of the additions. It's strongly advised that you **don't** make your changes on the ``master`` branch; instead,
create a new branch yourself (with a sensible name) and make the changes there.

This implies that we need to create a ``branch`` first, so let's go! You can do this via your client (there
should be a ``create branch`` button somewhere), or you can use the CLI with git:

.. code-block:: none

  git checkout -b [name_of_your_new_branch]

This will create a ``branch`` with the name of your choice and switch to it. All changes you're about to make will be
on this branch. If you need to switch to another branch ( for example ``master``), just reuse this command. Third step
done! Good job so far! To get an overview of your branches, just have a look at your git client or use:

.. code-block:: none

  git branch

4. Making Your Changes
----------------------

**Now it's time to make your changes**. Use the editor or IDE of your choice to do this.

5. Test if Your Changes Work
----------------------------

For SpongeAPI and the implementations you have to run ``gradle compileJava``. Proceed to the next step if it finishes
without errors. If it doesn't, make the appropriate corrections and try again.

For SpongeDocs you can just submit your PR. It will get built automatically and reveal possible errors. Another option
is to build the Docs locally. Have a look at the
`Readme.md on the Docs <https://github.com/SpongePowered/SpongeDocs/blob/master/README.md>`_ for further instructions.

6. Commit the Changes
---------------------

When you're done, you need to bundle them into a single package (a ``commit``) and get them into the branch. Again your
git client will help you out. Add a meaningful name to your commit and a short description if needed. This can be done
via CLI too:

First collect all files and folders you want to put into a commit:

.. code-block:: none

  git add <file>
  git add <folder>

Now that the files are added to your list of changes you want included in the commit, just do

.. code-block:: none

  git commit

It will open a text window, where you can add a message if you desire.

.. note::
  You can have multiple commits in a PR. Just go ahead and change everything you need and commit the changes.
  You can merge the commits onto a single commit later.

So now, the sixth step is done. Almost there!

7. Sync to GitHub
-----------------

Now we need to get the changes to your fork on GitHub. Everything you've made so far is only stored locally
right now. As always, you can use your git client to do this (there's a button somewhere), or you can do it via CLI:

.. code-block:: none

  git push

8. Propose the Changes in a PR to the SpongePowered Repo
--------------------------------------------------------

You can either go to your forks page on GitHub.com (there should be a notice to guide you), or you can use your
GitHub client to create a pull-request. The official GitHub client uses the the top right corner of the window for this.

9. Amend Your PR if Necessary
-----------------------------

If we want you to make changes to your PR, then just make more commits to the branch created above.
Further commits will be added to your PR automatically.

10. Your PR Gets Pulled
-----------------------

That's it. We're all set! Great job!

Advanced Git
============

.. ToDo: add squashing, setting up a remote, rebasing

Squashing with Rebase
---------------------

Let's say you have finished your additions to the repo, and let's pretend that you made 137 commits while getting it done.
Your commit history will certainly look cluttered. It would be a shame if they were all recorded into the repo, wouldn't it?
Too many trivial commits also clutters the project commit history. Fortunately Git has a nice tool to circumvent this, it's
called a ``rebase``. Rebasing can take your 137 small commits and just turn them into one big commit. Awesome, isn't it?
Instead of reinventing the wheel, we'll just pass you a link to a very short and easily understandable squashing tutorial:

`Gitready: Squashing with Rebase <http://gitready.com/advanced/2009/02/10/squashing-commits-with-rebase.html>`_

Setting Up a Remote
-------------------

If you see the warning ``fatal: The current branch YourBranchName has no upstream branch.``, then the branch may not be on
the upstream remote. This may happen if this is the first time you are pushing a commit for the new branch. To push the
current branch and set the remote as upstream, use ``git push --set-upstream origin YourBranchName``.

Rebasing
--------

Let's say you made some changes to your desired branch, but in the meantime someone else updated the repo. This
means that your fork is outdated, which is not a big problem, but to avoid problems when merging your additions later on,
it's strongly advised to ``rebase`` your changes against the lastest changes in the original repo. If you haven't set up
the remote repo yet, do it before trying to rebase.

A successfull rebase requires several steps:


.. CHECK IF BELOW IS CORRECT!!!

1. you must fetch the changes in the remote repo
2. you merge the remote changes into your local folder (preferably into your master branch)
3. you rebase the local branch which contains your changes against your local master branch
4. push everything to your fork on GitHub
