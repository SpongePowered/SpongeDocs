===========
global.conf
===========

Global Configuration
~~~~~~~~~~~~~~~~~~~~

The global.conf file contains the global configuration settings for Sponge. This file is created in the config/sponge
directory in your server folder. Many of these properties can be also overridden per-world or per-dimension type by
using the config files in the subfolders of config/worlds.

Below is a list of available config settings and their comments inside the global.conf file. Note that certain sections
will not be filled immediately, and may optionally be added to the file when the server encounters them. There's also
full example of an unmodified ``global.conf`` file at the bottom of this page, below the following list:

.. note::

    The following section headings refer to the path within the configuration (and the corresponding simple class name).
    Each of the entries in a section refers to a configurable property in the associated class. The `Type` information
    refers to the section/class that describes the nested configuration structure.

.. include:: global.conf-rst.generated

------------------------------------------------------------------------------------------------------------

This configuration file was generated using SpongeForge 7.3.0 (Forge 2838, SpongeAPI 7.3):

.. literalinclude:: global.conf
   :language: guess
