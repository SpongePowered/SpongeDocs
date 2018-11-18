================================
Ore Plugin Submission Guidelines
================================

Welcome to the Ore submission guidelines. This document provides an outline of our expectations for both project and
file submissions.

Remember that these are just guidelines and the Ore team, referred to as the “staff” throughout these guidelines, may
choose to allow or disallow an action that is not explicitly listed here at our own discretion. 

--------

========
Projects
========

Submitted projects should meet the following expectations:


Name
~~~~

Your project’s submitted name should not include a version, tagline, or other description. The name should be unique
and original and must not have a name implying it is an official Sponge project.

Examples of names that are **not acceptable**:

- *SpongeWarp*
- *SpongeHome*
- *SpongeEssentials*
- *SpongeCloud*

Examples of names that are **acceptable**:

- *CoolWarps-Sponge*
- *MoneyMiner-Sponge*
- *Golfball-for-Sponge*
- *Calendar-for-Sponge*

Main Documentation Page (Home)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This is the first page anyone sees when visiting your project. Information here should include a description of your
project’s features. The following, if present in your plugin, should be documented on the main page if not documented
elsewhere on the Ore project: commands, configuration, and permission nodes. Additionally, the below information must
be documented on the main page if relevant:


    **External Connections**

    If your project utilizes a web API, phones home to collect data, or otherwise connects to a system external to
    the server it is running on, the presence of this feature as well as information on how to enable or disable it
    must be displayed prominently on the main page. If your project’s sole purpose involves utilizing an external
    system (such as a Sponge plugin that translates chat between languages), a configuration option to toggle making
    connections to that service is not required. If your plugin sends information (for example, plugin list or
    player data), the information collected must be listed on the main page.

    Examples of systems that require documentation:

     - Statistics or usage information collection (‘metrics’)
     - Geolocation
     - Translation service
     - Web server that runs on the plugin, serving information to users
     - Server that runs on the plugin, listening to requests from other services
     - IRC/Discord/Telegram/WhatsApp bot or relay


Category
~~~~~~~~

The category you choose should be accurate. Your project should use the narrowest category possible rather than any
category that slightly applies. If no category appears accurate, the Miscellaneous category should be used.


Download Links
~~~~~~~~~~~~~~

Ore provides, on each project page, a download button which will automatically choose the most recent release. If you
wish to add additional download links, all links must point to files hosted on Ore. In addition to links to approved
files, you may link to the download pages of unapproved Ore file submissions, but these links must not be recommended
over any approved submission by any means. This includes both explicitly marking a link to an unapproved file as
recommended, or implicitly by making such links as or more prominent than links to approved builds. Further, you must
not attempt to circumvent any plugin warnings on Ore, including warnings that inform the user that a project has not
yet been reviewed.


Monetization / Advertising
~~~~~~~~~~~~~~~~~~~~~~~~~~

Submissions may not be sold, nor may additional features be unlockable with payment. Advertisements and other revenue
generating links (e.g. adfly) are not permitted. The documentation may contain a link to a page to donate to the
project maintainer or other contributors as thanks but that page may not offer additional features or other
plugins/mods for sale. 


“Cracked” / Offline-mode / online-mode=false Support
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Projects that explicitly state they are designed for such uses are not allowed. Some projects, such as authentication
systems, may have functionality that can be useful for servers regardless of the server’s use of Mojang
authentication, but they may not promote this additional usage or be specifically designed for servers avoiding
Mojang auth. Projects designed for proxies requiring online-mode=false are allowed, provided they are not written to
facilitate circumvention of Minecraft account ownership.


EULA
~~~~

We aim to comply entirely with the Mojang EULA. Any plugins, services, posts, and/or links suspected of violating the
EULA may be removed at the discretion of the Sponge Staff or at the request of Mojang AB.


Forks
~~~~~

Forks are permitted, provided they meet all items in the below list. Staff have the final say in what constitutes an
accepted fork. Follow the license of the parent project appropriately.

Either:

 - Contain significant changes warranting the creation of a new project. This is to avoid “I changed the message
   colors in Plugin X and now I claim credit!”, or
 - Continue a plugin that has been abandoned, with proof the author has not been answering messages or has stated
   the project will no longer be updated.

Acknowledge or credit the past plugin and developers. Essentially, don’t claim it is a new plugin and exclusively
your creation.

--------

=====
Files
=====

Files submitted should meet the following expectations:


Obfuscation
~~~~~~~~~~~

A file that utilizes obfuscation will be denied unless it falls under the following exception:

    **NMS Obfuscation**

    This only applies for plugins which reference Minecraft or a Forge mod. Examples would be a plugin using Mixins or
    a plugin which doubles as a Forge mod (hybrid plugin). Provided that the only obfuscated references are to
    obfuscated source generated using ForgeGradle or VanillaGradle, the plugin is allowed to proceed through the
    review process.


Core Mods and Mixins: Modification of the Minecraft Base Code
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Plugins and mods that use a system that modifies the Minecraft base code at runtime, (such as core mods and mixins)
must disclose the edits that they make to the Minecraft code, and their reasoning for them. Sponge plugins should use
SpongeAPI where possible. Sponge implementations may implement technical restrictions to prevent such modifications
from being applied by default. Files are not permitted to attempt to work around these restrictions, but can notify
the user that enhanced functionality can be enabled via the Sponge provided configuration options.


External Connections (Web API, Phoning Home, etc.)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Many great features can be written by making calls to external systems. As well as being clearly documented in
project descriptions, such functionality should be configurable and disabled by default. If your project’s sole
purpose involves utilizing an external system (such as a Sponge plugin that translates chat between languages),
connecting to that system does not need to be disableable. If your plugin sends information (e.g. a plugin list,
player data, or map data) to external systems, the information collected must be listed on the main page (see above).

    **Metrics (Data Collection)**

    Whenever data collected about the server (often referred to as "stats" or "metrics" data, such as server or
    plugin versions, as well as usage information) is to be sent to an external service, the plugin must first query
    the Sponge API MetricsConfigManager. Documentation on doing so can be found :doc:`Here </plugin/metrics.rst>`.
    This API must be checked each time data is sent, not only once. Plugins may not modify the values the API
    returns, but may encourage users to make the decision to enable the collection and sending of this data for their
    plugin.
    
.. note::

    This API was added in API 7.1.0. Plugins built against older API versions must instead check against a variable in
    a configuration file unique to that plugin for the enabled/disabled status, which must also default to disabled.


Execution of Downloaded Code
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This is a security risk we will not tolerate. This includes downloading jar or class files, generation of bytecode
from downloaded sources, and execution shell scripts.


Monetization / Advertising
~~~~~~~~~~~~~~~~~~~~~~~~~~

All functionality present in your plugin should be usable without restriction, and cannot require a license key to
operate. External APIs, such as translation or geolocation services, that require payment for functionality can be
allowed but must be discussed among staff prior to approval. Plugins may not be used to display advertisements.


Update Checking
~~~~~~~~~~~~~~~

Checking for updates should be performed using the provided Ore API. Your plugin may not link anywhere but Ore
when directing users of your plugin to download new versions. Note that this update checking counts as an external
connection, which must be documented and for which configuration must exist to disable it.

Privilege Granting
~~~~~~~~~~~~~~~~~~

Plugins must not grant or revoke feature access to any particular user or group of users determined by the plugin
developer. This includes the author granting themselves a special display name or letting themselves use a special
command. Features, when applicable, should be locked behind permission nodes, rather than access being predetermined
by the author. Commands for granting specific, pre-programmed users OP or permissions are not acceptable.
