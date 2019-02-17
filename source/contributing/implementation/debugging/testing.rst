=======================
Testing Plugins or Mods
=======================

.. note::
    The changes made by your PR determines whether you need to provide a testing plugin or a testing mod. Any changes 
    to the API require a testing *plugin*, and any changes to implementation need a testing *mod*. Test plugin will 
    refer to both for the remainder of this article.

Testing is an integral part of any software development project. Supplying a test plugin accomplishes two goals:

1. Testing

   A test plugin demonstrates you have tested your changes, but it also provides others with the ability to check the 
   current changes as well as future changes.

#. Documentation

   A well-commented test plugin provides details not necessarily required in PR documentation. Furthermore, comments 
   in a PR generally last until the PR is merged. Comments in a program last as long as the code remains. They should 
   not provide all of the documentation. Instead, well-commented code supports and completes the documentation.

.. tip::
    A good understanding of :doc:`../../../plugin/index` provides a solid foundation for writing test plugins. Plugin 
    requirements apply to test plugins, such as :doc:`../../../plugin/plugin-identifier` and 
    :doc:`../../../plugin/logging`.

You should provide a test plugin when you are contributing new or modified feature(s) to the API or implementation. The 
plugin is simply a class added to the package ``org.spongepowered.test``, which is found in the 
``SpongeCommon/testplugins/src/main/java/org/spongepowered/test/`` directory. The PR for the contributions should 
include the test plugin.

It is essential that test plugins do not change the game when not intended. As a result, a command should toggle the 
plugin functionality. Be sure not to confuse this with **excluding** the test plugins in the build process. A setting 
in ``SpongeCommon/gradle/implementation.gradle`` can exclude test plugins from the resulting jar file. However, a 
command must toggle the functionality of the test plugin whether or not test plugins get included in a jar file or not. 

.. note::
    JUnit is used on a limited basis and primarily for internal purposes. Generally speaking, JUnit is useful for 
    testing without a player. However, the best practice is not to use JUnit unless you have agreement from a Sponge 
    staff member.
