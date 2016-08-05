========
Commands
========

.. javadoc-import::
	org.spongepowered.api.command.CommandCallable
	org.spongepowered.api.command.spec.CommandSpec

.. tip::

	Since the Command API makes use of the Text API, make sure you read :doc:`../text/index` first.

There are two different APIs to create commands in Sponge: The :javadoc:`CommandCallable` interface and the
:javadoc:`CommandSpec` builder.

The most comfortable way to create a new command is the ``CommandSpec`` builder, which will be detailed in this section.
It supports child commands and argument parsing.

Alternatively, you can use ``CommandCallable``, a lower-level interface which provides access to the raw command data.
It is described on :doc:`this page <./commandcallable>`.

Contents
========

.. toctree::
	:maxdepth: 2
	:titlesonly:

	creating
	arguments
	flags
	childcommands
	service
	commandcallable
