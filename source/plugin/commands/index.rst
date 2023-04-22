===============
Plugin Commands
===============

.. javadoc-import::
    org.spongepowered.api.command.Command.Raw
    org.spongepowered.api.command.Command.Parameterized

.. tip::

    Since the Command API makes use of the Component API, make sure you 
    read `Component <https://docs.advntr.dev/text.html>`_ first

There are two different API's to create commands in Sponge: 
The :javadoc:`Command.Parameterized` builder and the :javadoc:`Command.Raw` interface.

The most comfortable way to create a new command is the ``Command.Parameterized`` builder, which will be detailed in 
this section. It supports child commands and takes advantage of Minecraft's Brigadier system.

Alternatively, you can use ``Command.Raw``, a lower-level interface which you can extend your own command system onto.

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    commandbuilding
    argumentparsing
    childcommands
    commandmanager
    rawcommand