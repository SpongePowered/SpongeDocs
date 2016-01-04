=======================
Contribution Guidelines
=======================

There will always be a need for developers to help us improve SpongeAPI. There is no such thing as a perfect project and
things can always be improved. If you are a developer and are interested in helping then please do not hesitate. Just
make sure you follow our guidelines.

General steps
=============

1. Setup your workspace as described in :doc:`../../preparing/index`.

#. Make sure you're familiar with Git and GitHub. If your knowledge needs a refresh, take a look here: :doc:`howtogit`

#. Check for existing issues in the `SpongeAPI <https://github.com/SpongePowered/SpongeAPI/issues>`_,
   `SpongeCommon <https://github.com/SpongePowered/SpongeCommon>`_,
   `SpongeForge <https://github.com/SpongePowered/SpongeForge>`_,
   `SpongeVanilla <https://github.com/SpongePowered/SpongeVanilla>`_, and
   `SpongeDocs <https://github.com/SpongePowered/SpongeDocs>`_ repositories. There is possibly someone else already
   working on the same thing. You can also check
   `issues marked with "help wanted" <https://github.com/SpongePowered/SpongeAPI/labels/help%20wanted>`_ for existing
   issues we need your help with.

.. note::
    Please don't submit pull request for small changes under 20 lines. Instead, `join #sponge on IRC (irc.esper.net)
    <https://webchat.esper.net/?channels=sponge>`_ or `join #spongedev on IRC (irc.esper.net)
    <https://webchat.esper.net/?channels=spongedev>`_ and we'll change it together with the other smaller changes.

4. If the issue requires a bigger change you may want to submit the issues without the necessary changes first, so we
   can confirm the issue and know that you're working on fixing it. You should also create a WIP (work in process) pull
   request prefixed with ``[WIP]`` early so we can already start reviewing them.

#. Fork the project, clone it and make your changes in an extra branch.

#. Test your changes (make sure it compiles!), commit and push them to your fork.

#. Submit the pull request with a short summary what you've changed and why it should be changed in that way.

#. If you make additional changes, push new commits to your branch. **Do not squash your changes**, that makes it
   extremely hard to see what you've changed compared to the previous version of your pull request.

#. Make sure your PR is rebased to the latest changes of the branch you're intending to merge it into. If you need help
   rebasing it, just ask!

.. tip::
  If you're unsure which branch you should base your work on, read about our :doc:`versioning` before submitting your PR.
