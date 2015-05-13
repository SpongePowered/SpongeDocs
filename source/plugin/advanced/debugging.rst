================================
Plugin Debugging and Hotswapping
================================

Every developer, at some point, has had the *pleasure* of debugging their plugin.
Generally speaking, debugging is done by throwing a multitude of logs in specific areas to narrow down breakpoints,
compiling the plugin, moving the result to the test folder, and running the server. Along that process, crossing every
finger you have that something doesn't immediately fail and make the past hour or so of programming in vain.

However, what many newer programmers (and some seasoned!) don't know, is that there is an easier way. Why bother
compiling and moving folders, when you can test your plugin from right within your IDE? Oh, of course you know how
to run tests during compilation, but that's not quite what I'm talking about. We're talking about running the entire
server implementation and plugin at once, from within the IDE.

How is that possible, you may be asking? Quite simple. Plugin Debugging is fairly easy to setup, once you know how. Once
you're debugging, Hotswapping is the next step (which we'll cover later).

.. tip::

    See the `Setting Up Your Workspace <../basics/workspace>`_ documentation, before attempting to follow this guide.

Preparing your Plugin
=====================

IntelliJ
~~~~~~~~

* Open the **Sponge(Vanilla)** project.
* Click ``File``, followed by ``New``, then ``Module...``.
* Select ``Gradle`` in the popup, and click Next.
* After naming your new project, click ``Finish``.

Eclipse
~~~~~~~

.. tip::

    You can use `Setting Up Eclipse <../basics/workspace/eclipse.rst>`_ to create a new Eclipse project. Eclipse, unlike IntelliJ, does not require any specific setup in regards to how you manage the plugin. Creating a new project is all you need to do.

Adding Plugin to Sponge classpath
=================================

The idea behind this is that we'll launch Sponge from within your IDE, like normal. However, the difference is that
we'll be adding your plugin to the classpath. To do this should only take a few steps, and will enhance your workflow,
having cut down on all of the time that you previously would have spent transferring files and manually starting your
server.

.. tip::

    Please ensure that you have your Run/Debug Configuration(s) set appropriately, as shown in the Sponge `README.md <https://github.com/SpongePowered/Sponge/blob/master/README.md>`_

IntelliJ
~~~~~~~~

* Open your Project Structure.

  * Click ``File``, followed by ``Project Structure...``.
  * OR, click the ``Project Structure`` icon, in the upper right-hand corner of the IDE, next to the ``Search`` icon.

* Click ``Modules``.
* Click the Green ``+`` symbol (``Add``), and select ``Module Dependency``
* Select your Module (Plugin).
* Do NOT check the module, after it is added to the list.

Eclipse
~~~~~~~

* Find your Run/Debug Configuration

  * Click ``Run``, followed by ``Run Configurations...``
  * OR, click the drop-down arrow beside the Run/Debug icons and then ``Run Configurations...`` or ``Debug Configurations...``, respectively.

* Select your Run/Debug Configuration for Sponge (Server) on the left side.
* Switch to the ``Classpath`` tab.
* Select ``User Entries``, followed by the ``Add Projects...`` button.
* Select the appropriate Project for your Plugin.
* Click the ``OK`` button.
* Click the ``Apply`` button on the bottom right corner.

Testing the Configuration
=========================

After you've followed the previous steps, you should be ready to start debugging.

IntelliJ
~~~~~~~~

Rather than pressing the Green right-pointing arrow to run your Run/Debug configuration, click the Green icon to the
right of it, ``Debug``. This is to prepare for Hotswapping, which we're about to cover.

Eclipse
~~~~~~~

Rather than pressing the green right-pointing arrow to run your Run/Debug configuration, click the drop down arrow of
the Debug icon (the one displaying a bug) and click your Sponge (Server) configuration. If it doesn't appear in the
drop-down menu, click ``Debug Configurations``. Select ``Sponge (Server)`` configuration and hit the ``Debug`` button
on the bottom left.

Hotswapping
===========

Now that you've got the server, running, let's try to make some changes. Note, that there are limits to the built-in
capabilities of Hotswapping:

* You cannot create or remove methods.

    * Changes to methods are limited to information *within* the method. You cannot modify its return type, nor its variable requirements

* You cannot remove classes.

    * You cannot modify a class' type, extensions or implementations. Nor can you add an additional import.
    * You can add classes. However, once it's been built and hotswapped, the class follows the above rules.

Go ahead and make a change to an existing method. Make sure that this is something you can trigger from within the
server, to see the change. Once you've done that:

IntelliJ
~~~~~~~~

* Open the ``Run`` menu, from the top of the IDE.
* Below the first category break, click ``Reload Changed Classes``.

Eclipse
~~~~~~~

In Eclipse, when a file is rebuilt, it is automatically hotswapped with the currently running debug. The default setting
for building files insist that it is rebuilt upon every save. Meaning as soon as the file is saved, it's automatically
built and swapped. If you have turned this feature off, you would have to build manually.

