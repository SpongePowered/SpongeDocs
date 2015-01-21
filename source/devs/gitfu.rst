=======================
Git-Fu
=======================

Using git is part of developing for Sponge and SpongeAPI.

Using git well improves your contributions.  This document will help
show you how to make the most of git when contributing to Sponge.


Who are You?
=============

Before you even start to work with git and the repostory, make sure your
git configuration has your identity setup.

1.  ```git config --list```
#.  Look for ```user.name``` and ```user.email```
#.  If they are not the same username and email as your Github account
    then set them:
#.  ```git config --global user.name "John Doe"```
#.  ```git config --global user.email johndoe@example.com```


.. tip::
   Do not proceed with any contribution in any Sponge repository until 
   you establish your ```user.name``` and ```user.email```.

Getting an enlistment to Sponge
=============

1. Setup your workspace as described in the main page of the Sponge
   project.

* git clone git@github.com:SpongePowered/Sponge.git
* cd Sponge
* git submodule update --init --recursive
* cp scripts/pre-commit .git/hooks

   Your local copy of Sponge is now setup and your branch is master.


Working on the SpongeAPI
=============
1. ```cd SpongeAPI```

#. ```git pull```

.. tip::
    ```git pull``` brings your branch (master at this point) up to date before 
   branching it.

#. ```git checkout -b YourBranchName```

.. tip::
    The name of your branch should follow a convention.  Please try to use a name like ```feature/your-feature```

#.  Work with the files, create files, etc..  Do your work in the branch.

#.  When you're done working with the files you can do a check that the
    module will compile.  This would be a good idea before even making
    the commit.

.. tip::
    Run ```gradle compileJava``` to run a simple build of the source files.

#.  When you're convinced that the build of your branch of the SpongeAPI is
    ok then you can commit. 

#.  ```git status```

#.  Look for files that are noted as not staged for commit.   Add the
    files that need to be staged for commit.

#.  ```git add FILE(s)```

#.  When all the files are staged for commit, then commit.

#.  ```git commit```

#.  Edit the commit message.  This message should be short and concise.

#.  Now that you've made the commit, you can push this commit to the 
    repository.

#.  ```git push```

#.  Once your commit is pushed to the repository, then you can open
    a pull request



