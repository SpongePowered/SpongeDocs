================
Plugin Lifecycle
================

Prior to any plugin-visible states, the plugin loader finds the available plugins, determines if all dependencies are satisfied, and sorts plugins by dependency order. All lifecycle events are given to plugins in this order, such that plugin A containing "[required-]after:B" will get each event after plugin B has completed any work it is doing for that state. Additionally, all lifecycle states are global: all plugins visible to each other in any manner must be transitioned through all stages at once.

Initalization States
--------------------

All the initialization events will occur at most once.

CONSTRUCTION
~~~~~~~~~~~~
During the construction stage the ``@Plugin`` class instances for each
plugin are created. The primary 'state event' for this stage is the
``@Plugin`` class constructor.

PRE_INITIALIZATION
~~~~~~~~~~~~~~~~~~

Pre-initialization is the time for a plugin to perform any actions it
needs to get ready for initialization. The pre-initialization event
gives the plugin access to a default logger instance, as well as
information on preferred configuration file locations.

INITIALIZATION
~~~~~~~~~~~~~~
Initialization is the stage where the plugin should
finish any work it needs to do in order to be essentially functional.
Global event handlers and any other registration or information that
does not depend on other plugins or a running server instance should be
handled at this stage.

POST_INITIALIZATION
~~~~~~~~~~~~~~~~~~~
Post-initialization is
the stage where most initialization phase inter-plugin communication
should occur. All plugins that provide an API should be ready to accept
basic requests to their API by this stage.

LOAD_COMPLETE
~~~~~~~~~~~~~
All plugin
initialization should be completed before this stage. At this point
everything should be ready for a server instance to start.

Running States
--------------

These states can occur multiple times during a single run.
SERVER_ABOUT_TO_START may follow from SERVER_STOPPED, and in the
event of errors during any part of the process SERVER_STOPPED may occur
immediately.

SERVER_ABOUT_TO_START
~~~~~~~~~~~~~~~~~~~~~
The server instance exists,
worlds are not yet loaded.

SERVER_STARTING
~~~~~~~~~~~~~~~
The server instance
exists, worlds have been loaded.

SERVER_STARTED
~~~~~~~~~~~~~~
(Appears to currently happen immediately following SERVER_STARTING?)

SERVER_STOPPING
~~~~~~~~~~~~~~~
Immediately following the final tick, prior to saving.

SERVER_STOPPED
~~~~~~~~~~~~~~
Server has been shutdown, possibly by a major error.
No players are connected, no changes to the world will be saved.
