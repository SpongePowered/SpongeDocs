========================
Creating a Launch Script
========================

.. note::

    These instructions apply only if you plan to run your Minecraft server on your own machine. Most shared Minecraft
    hosts will create a launch script for you.

Writing a Launch Script
=======================

First, open a text editor such as Atom, Sublime Text, or Notepad. Write (or paste) a launch script for your server.
Examples of simple launch scripts for Windows, macOS, and Linux have been provided below. Keep the RAM limitations
of your machine in mind.

.. note::

    The following examples are generic. For a Forge server using Sponge (coremod), change ``forge-1.12.2-XYZ-universal.jar``
    to whatever your Forge version in the server directory is named. To launch a SpongeVanilla server, change
    ``forge-1.12.2-XYZ-universal.jar`` to the name of the SpongeVanilla.jar file.

Windows
~~~~~~~

.. code-block:: bat

    java -Xms1G -Xmx2G -jar forge-1.12.2-XYZ-universal.jar
    pause

Save your Windows launch script as ``launch.bat``.

.. warning::
   Launching your server in a Command Prompt or PowerShell environment can lead to the server hanging due to the 
   ``Quick Edit Mode`` of these shells. This mode freezes the process when you highlight something or click inside the 
   console window. During this time, the only messages captured in the log state the server skipped many seconds or 
   minutes worth of ticks. You can prevent hangs from occurring by not highlighting any text on the screen and not 
   clicking inside the window, or by disabling the ``Quick Edit Mode`` in the Properties dialogue.

macOS
~~~~~

.. code-block:: bash

    #!/bin/bash
    cd "$(dirname "$0")"
    java -Xms1G -Xmx2G -jar forge-1.12.2-XYZ-universal.jar

Save your Mac launch script as ``launch.command``.

Linux
~~~~~

.. code-block:: sh

    #!/bin/sh
    cd "$(dirname "$(readlink -fn "$0")")"
    java -Xms1G -Xmx2G -jar forge-1.12.2-XYZ-universal.jar

Save your Linux launch script as ``launch.sh``.

Running a Launch Script
=======================

Ensure you are running your launch script out of a folder created especially for your server. This is for your own
sanity; unfortunately, Spongie is unable to soak up your tears if you do not do this.

You may run your launch script by double-clicking it. If you are using a console or terminal, navigate to the directory
of the script and run it. Keep in mind that you must agree to the Mojang EULA in order to run a server.

.. note::

    The default Minecraft server GUI console is disabled by Sponge, because it is very processor-intensive.

.. warning::

    If you get a permissions error when attempting to launch your server on a Mac, try this:

    * Open the Terminal.
    * Type ``chmod a+x``, with a space at the end.
    * Drag your launch script to the Terminal.
    * Press enter.
