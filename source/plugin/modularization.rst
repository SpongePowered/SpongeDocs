===================
Code Modularization
===================

When writing Sponge plugins, you may find that you need to modularize your code. Maybe you want to split your core into
another project so that others can make use of it, or maybe you want to utilize a service another plugin provides. With
Sponge, gone are the days of shading entire plugins into your plugin.

Problem 1: Providing Good Service
=================================

Let's say you want to provide some awesome service for ``WarpService``. You want to make ``AwesomeWarpService`` override
some other plugin's ``CoreWarpService`` with your own.

But when you go to use ``Sponge.getServiceManager().provide(WarpService.class)``, you are shocked to find that referencing
``WarpService`` throws a ``ClassNotFoundException``!

What's Happening
~~~~~~~~~~~~~~~~

When you compile your plugin, the compiler finds ``WarpService.class`` because it's present in the workspace (either via
a build tool like Gradle or by just adding it to the classpath). However, when Sponge goes to run your plugin, it can't
find the referenced ``WarpService.class`` in your plugin's "container".

Solution
~~~~~~~~

Using Sponge's dependency system, you can declare hard and soft dependencies using the ``dependencies`` field of the
``@Plugin`` tag. Here's an example:

.. code-block:: java

    @Plugin(
      id = "Example",
      name = "Example",
      version = "1.0-SNAPSHOT",
      description = "Example Plugin",
      url = "https://example.com",
      authors = { "Spongie" },
      dependencies = {
        @Dependency(
          id="examplecore",
          optional=false
        )
      }
    )

Now, if a plugin by the ID of "examplecore" is not found, your plugin is not loaded at all. If it is found, the
dependency is added to your plugin's runtime.

Problem 2: Getting an Instance Another Plugin
=============================================

So now that you've got your dependencies straight, you want to get an instance of the other plugin's main class. This
could be in order to interface with an API or whatnot.

Solution
~~~~~~~~

In Sponge, all plugins are Singletons and you can use the ``PluginManager`` to retrieve an instance of another plugin
(by ID). Be sure to read about the :doc:`manager`.
