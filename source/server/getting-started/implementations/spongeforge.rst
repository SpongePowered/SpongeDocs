======================
Installing SpongeForge
======================

SpongeForge integrates `Minecraft Forge <http://www.minecraftforge.net/>`__ so you can also run Minecraft Forge mods.
In fact, it's more like Sponge itself is a Forge mod that then loads Sponge plugins, but this is a technical detail.

Users who do not want to use Minecraft Forge can consider :doc:`SpongeVanilla <spongevanilla>`.

Download
========

Grab your copy of `Sponge Forge here <https://www.spongepowered.org/downloads>`_.

Reading the Download Filename
=============================

When you download SpongeForge, the name of the file will provide some important version information. It includes a
Forge build number which this version of SpongeForge is compatible with. Other builds, even ones differing by only a
few build numbers are not officially supported.

However, SpongeForge usually updates to a new Forge build fairly soon after it's released, so you needn't
worry about always having to run an outdated Forge version in order to use SpongeForge.


The format of the filename is ``spongeforge-<MCVersion>-<ForgeBuildId>-<SpongeAPIVersion>-BETA-<SpongeBuildId>.jar``

+----------------------+-----------------------------------------------------------------------------------------------+
| ``MCVersion``        | The Minecraft version. Only clients compatible with this version can connect.                 |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``ForgeBuildId``     | Preferably your server should run this exact version of Forge (which can be identified in the |
|                      | last part of Forge's version string).                                                         |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``SpongeAPIVersion`` | The version of the SpongeAPI implemented by this file. This is what Sponge plugins depend on. |
+----------------------+-----------------------------------------------------------------------------------------------+
| ``SpongeBuildId``    | The build number of Sponge. This is what you should supply when reporting bugs or seeking     |
|                      | support.                                                                                      |
+----------------------+-----------------------------------------------------------------------------------------------+

Example
~~~~~~~

SpongeForge Jar files will always follow this naming scheme, to allow you to easily identify compatibility.

For example the file name ``spongeforge-1.12.2-2611-7.1.0-BETA-2990.jar`` is compatible with Minecraft version
``1.12.2``, was built with Forge ``114.23.2.2611`` (Build ``2611``), provides SpongeAPI ``7.1.0`` and was build number ``2990`` of SpongeForge.

.. note::

    Normal Forge mods can usually run on any build of Forge for a given Minecraft version (e.g. 1.8.0) without any
    problem. However, SpongeForge needs to access, among other things, internal parts of Forge, which most mods
    shouldn’t be touching, let alone modifying as Sponge does. Since Forge is free to change their internal code
    whenever they want to, its normal guarantee of backwards-compatibility doesn’t apply to SpongeForge. Feel free to
    use more recent versions of Forge, than the one used for SpongeForge, but we can't always guarantee compatibility.


.. warning::
    
    When investigating crash issues, you can freely try newer versions of Forge than listed on the SpongeForge Jar.
    However it is recommended to also check with the matching version, to make sure your issue is not related to a
    version mismatch. 
    Even though there will be no guarantee of compatibility, please report any breakage to the issue tracker, so that
    SpongeForge can be updated.

Installing SpongeForge
======================

.. note::

    If you use (or are planning to use) a game server host, they may have a control panel that can install Sponge for
    you.
 
Single Player / In-Game LAN Servers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

1. Download the Minecraft Forge installer from the `Minecraft Forge website <https://files.minecraftforge.net/>`_. Make
   sure to use **exactly the same build number** as shown above.
#. Run the provided Forge installer. A new Forge profile will be created in the Minecraft launcher.
#. Open the Minecraft launcher, and select the new Forge profile.
#. Click "Options" and click "Open Game Dir".
#. Download SpongeForge from the Sponge website and put it into the ``mods`` folder. Create the folder if it does
   not yet exist.
#. Sponge should work in both in single player and if you open your world to LAN.

Next, learn how you can :doc:`configure Sponge <../configuration/index>` and how to
:doc:`manage your instance of Sponge </server/management/index>` (including installing plugins).

Dedicated Servers
~~~~~~~~~~~~~~~~~

.. note::

    If you already have a Forge server, just put the Sponge mod into your ``mods`` folder. Remember to update your Forge
    version to match the one that SpongeForge requires. Have a look at the top of this page if you're unsure which
    version you need.

.. note::

  When using the Mojang installer, Mojang makes use of their own Java version and not the one you installed on your
  system. The installer currently ships with Java ``1.8.0_25`` for Windows and ``1.8.0_60`` for macOS. Note that Sponge
  requires **at least** ``1.8.0_20`` or above to run properly.

Installing Forge via Commandline
--------------------------------

1. Visit the `Minecraft Forge website <https://files.minecraftforge.net/>`_ and click "Show all downloads" to view the full
   set of available options. Identify the version matching the one listed :doc:`in the filename of the SpongeForge download
   <spongeforge>`, and hover over the (i) next to "Installer" to get the direct download link.
#. Use your favorite download method to download the jar to its destination.
   Example: ``wget http://url.to/forge-version-installer.jar``
#. From the folder in which you wish to install Forge, execute the jar with the ``--installServer`` option. Example:
   ``java -jar forge-version-installer.jar --installServer``
#.  Continue to Adding SpongeForge to Forge below.


Installing Forge via GUI
------------------------

1. Download the Minecraft Forge installer from the `Minecraft Forge website <https://files.minecraftforge.net/>`_ for the version
   matching the one listed in the filename of the SpongeForge download. See above for the naming scheme of SpongeForge
   and Forge.
#. Run the provided Forge installer, select "Install Server", choose an empty folder to place the server's files,
   and then click OK.
#. Continue to Adding SpongeForge to Forge below.


Adding SpongeForge to Forge
---------------------------

1. Download SpongeForge from the Sponge website and put it into the ``mods`` folder in your server directory.
   Create the folder if it does not yet exist.
#. You may now launch the server via command or launch script ``java -jar forge-version-XYZ.jar``.
#. If operating from home, set up :doc:`../port-forward` to ensure others can connect.

Next, learn how you can create and use a :doc:`launch-script <../launch-script>`,
:doc:`configure Sponge <../configuration/index>` and :doc:`manage your server
</server/management/index>` (including installing plugins).

Links
=====

* `Homepage <https://www.spongepowered.org/>`__
* `GitHub <https://github.com/SpongePowered/SpongeForge>`__
