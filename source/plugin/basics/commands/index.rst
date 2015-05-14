=================
Creating Commands
=================

.. tip::

	Since the Command API makes use of the Text API, make sure you read :doc:`../text` first.

There are two different APIs to create commands in Sponge: The ``CommandCallable`` interface and the ``CommandSpec`` builder.

The most comfortable way to create a new command is the ``CommandSpec`` builder, which will be detailed in this section. It supports child commands and argument parsing.

Alternatively, you can use ``CommandCallable``, a lower-level interface which provides access to the raw command data. 
It is described on :doc:`this page <../../advanced/commandcallable>`.

Contents
========

.. toctree::
	:maxdepth: 2
	:titlesonly:
	
	creating
	arguments
	childcommands
	service
	