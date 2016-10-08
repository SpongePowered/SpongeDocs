================
Plugin Debugging
================

When bugs in your plugin's code are hard to pinpoint, you are tired of recompiling after every single change
and logger outputs are cluttering your code, debugging can be very tedious. Therefore, this section will
explain how to set up your plugin to utilize Java's debugging capabilities.

Preparing your workspace
========================

Since we will be running both Sponge and your plugin from within your IDE, you'll need to import either
`SpongeForge <https://github.com/SpongePowered/SpongeForge>`_ or `SpongeVanilla
<https://github.com/SpongePowered/SpongeVanilla>`_, depending on which you want to use, as a project into your
workspace. The instructions to do so are found on the respective project's GitHub page. Follow those instructions
carefully before proceeding here.

Now you need to make sure your plugin project is visible to the **SpongeForge/SpongeVanilla** project you just created.
The steps necessary depend on your IDE.

IntelliJ IDEA
~~~~~~~~~~~~~

In IntelliJ, every project has its own workspace(s). To make your project visible to the **Sponge(Vanilla)** project,
it needs to be a *Module*. Assuming you already created your project as described in
:doc:`workspace/idea`, import it using the following steps.

* Open the ``SpongeVanilla``/``SpongeForge`` project.
* Click ``File``, followed by ``New``, then ``Module from Existing Sources...``.
* Navigate to the directory of your plugin project.

  * If you're using Gradle, select the ``build.gradle`` file, in the next dialog check ``Use auto-import`` and confirm.
  * Otherwise, just select the whole directory and click ``Finish``.

* Click ``Finish``.

.. tip::

    If you have not yet created your plugin, click ``Module...`` instead of ``Module from Existing Sources...``,
    then create your project in the following dialogue.

Eclipse
~~~~~~~

Just create your project as described here: :doc:`workspace/eclipse`. As long as it is in the same workspace
as your ``SpongeVanilla``/``SpongeForge`` project, it will be visible.

Adding Plugin to Sponge classpath
=================================

The idea behind this is that we'll launch Sponge from within your IDE, like normal. However, the difference is that
we'll be adding your plugin to the classpath. Since Sponge will per default load all plugins found in the classpath,
adding the plugin project to Sponge's classpath will rid you of the necessity to rebuild and copy the artifact
file into your server directory after every change.

First you need to ensure that you have your Run/Debug Configuration(s) set appropriately, as shown in the Sponge
`README.md <https://github.com/SpongePowered/Sponge/blob/master/README.md#Running>`_

Then you'll need to edit that Run/Debug Configuration so that it will include your project on the class path.
How to do this, depends on your IDE again:

IntelliJ IDEA
~~~~~~~~~~~~~

* Open your Project Structure.

  * Click ``File``, followed by ``Project Structure...``.
  * OR, click the ``Project Structure`` icon, in the upper right-hand corner of the IDE, next to the ``Search`` icon.

* Click ``Modules``. Expand the ``SpongeForge`` or ``SpongeVanilla`` group (depending on what you chose).
* Make sure ``SpongeForge_main`` or ``SpongeVanilla_main`` is selected.
* On the right column, select the ``Dependencies`` tab.
* Click the ``+`` symbol (``Add``) on the bottom of the column, and select ``Module Dependency``.
* Select ``yourplugin_main``.
* Do NOT check the ``Export`` option on the module, after it is added to the list.

Eclipse
~~~~~~~

* Find your Run/Debug Configuration

  * Click ``Run``, followed by ``Run Configurations...``
  * OR, click the drop-down arrow beside the Run/Debug icons and then ``Run Configurations...`` or
    ``Debug Configurations...``, respectively.

* Select your Run/Debug Configuration for Sponge (Server) on the left side.
* Switch to the ``Classpath`` tab.
* Select ``User Entries``, followed by the ``Add Projects...`` button.
* Select the appropriate Project for your Plugin.
* Click the ``OK`` button.
* Click the ``Apply`` button on the bottom right corner.

Running the Configuration
=========================

After you've followed the previous steps, you should be ready to start debugging.
If you start your server from your IDE, its working directory will be the ``run`` directory in your
SpongeForge/SpongeVanilla project. All the files usually created by a server (worlds, configs etc) will be stored in
that ``run`` directory and persist over multiple runs of your local test server, just as if you manually copied a
server .jar to the ``run`` directory and started it from there.

IntelliJ IDEA
~~~~~~~~~~~~~

Rather than pressing the Green right-pointing arrow to run your Run/Debug configuration, click the Green icon to the
right of it, ``Debug``.

Eclipse
~~~~~~~

Rather than pressing the green right-pointing arrow to run your Run/Debug configuration, click the drop down arrow of
the Debug icon (the one displaying a bug) and click your ``Test (Server)`` configuration. If it doesn't appear in the
drop-down menu, click ``Debug Configurations``. Select ``Test (Server)`` configuration and hit the ``Debug`` button
on the bottom left.

Using the Debugger
==================

Now that your server (and your Plugin) are running in the Debugger, you can make use of the features it holds.
The most prominently used are explained below in short, though they are not features of Sponge, but the Java
Debugger your IDE makes use of.

Breakpoints
~~~~~~~~~~~

Breakpoints are a useful tool to take a closer look at the code. A breakpoint can be set at the beginning of a
line of code or a function. When reaching a breakpoint, the debugger will halt the code execution and your IDE
will open up a view allowing you to inspect the content of all variables in the current scope. Code execution
will not resume unless you press the according button in your IDE's debugging view.

Breakpoints may also be added, removed or temporarily disabled while the debugging is in process.

.. tip::

    Once a single server tick takes more than a given amount of time, the watchdog will consider the server crashed
    and forcefully shut it down. When working with breakpoints this might occur, so it is recommended that you
    edit your test environments ``server.properties`` file and set the value of ``max-tick-time`` to either a
    very large number (the amount of milliseconds a tick may take) or ``-1`` (to disable the Watchdog completely).

IntelliJ IDEA
+++++++++++++

To add or remove a breakpoint, just left click in the blank space just to the left of your editor.

Alternatively, have your cursor be in the line where you want the breakpoint added or removed and then click
``Run`` followed by ``Toggle Line Breakpoint``.

Eclipse
+++++++

To add or remove a breakpoint, just right click in the blank space just to the left of your editor and click
``Toggle Breakpoint``.

Alternatively, have your cursor be in the line where you want the breakpoint added or removed and then click ``Run``
followed by ``Toggle Breakpoint``.

Code Hotswapping
~~~~~~~~~~~~~~~~

The other major feat of the debugger is that you will not have to restart your server for every small change you
make, thanks to code hotswapping. This means that you can just recompile portions of your code while it is
running in the debugger. However, there are a couple of limitations, the most important of which are:

* You cannot create or remove methods.

    * Changes to methods are limited to code *within* the method. You cannot modify its signature (that means its name,
      return type and parameter types)

* You cannot remove classes.

    * You cannot modify a class' name, superclass or the list of interfaces it implements.
    * You can add classes. However, once it's been built and hotswapped, the class follows the above rules.

You can test this functionality: Introduce a simple command to your plugin that just writes a word, like ``Sponge``
Then save it and start the server as described above. Run the command. It will output ``Sponge``. Now change the
command to write a different word to console, save the file. After a change, do as follows to hotswap the changes to
the running program:

IntelliJ IDEA
+++++++++++++

* Open the ``Run`` menu, from the top of the IDE.
* Below the first category break, click ``Reload Changed Classes``.

Eclipse
+++++++

No action needed. As soon as you save the file, it will be rebuilt and automatically hotswapped with the
currently running debug. Unless you changed this particular default behavior, you will not have to trigger a manual
hotswap.
