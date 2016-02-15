===================================
The Structure of the Sponge Project
===================================

The Sponge Project consists of different subprojects, hosted in various repositories on Github. Here's a short overview
before going into detail:

+-------------------------------------------------------------------+-------------------------------------------------------+---------------------------------------------------------------------------------+
| Project                                                           | Description                                           | What is done in the repository?                                                 |
+===================================================================+=======================================================+=================================================================================+
| `SpongeAPI <https://github.com/Spongepowered/SpongeAPI>`_         | The API itself                                        | Development of the API itself                                                   |
+-------------------------------------------------------------------+-------------------------------------------------------+---------------------------------------------------------------------------------+
| `SpongeForge <https://github.com/Spongepowered/SpongeForge>`_     | A SpongeAPI implementation built on top of Forge      | Development of the parts of SpongeForge which rely on Forge                     |
+-------------------------------------------------------------------+-------------------------------------------------------+---------------------------------------------------------------------------------+
| `SpongeVanilla <https://github.com/Spongepowered/SpongeVanilla>`_ | A SpongeAPI implementation built directly on top      | Development of the Vanilla Counterpart of the SpongeForge repository            |
|                                                                   | of Vanilla Minecraft                                  |                                                                                 |
+-------------------------------------------------------------------+-------------------------------------------------------+---------------------------------------------------------------------------------+
| `SpongeCommon <https://github.com/Spongepowered/SpongeCommon>`_   | The shared code between SpongeForge and SpongeVanilla | Development of all code which is shared between SpongeForge and SpongeVanilla   |
+-------------------------------------------------------------------+-------------------------------------------------------+---------------------------------------------------------------------------------+
| `Mixin <https://github.com/Spongepowered/Mixin>`_                 | The tool used to inject the implementations into      | Development of our solution to hook Sponge into the Minecraft server            |
|                                                                   | the underlying code structure                         |                                                                                 |
+-------------------------------------------------------------------+-------------------------------------------------------+---------------------------------------------------------------------------------+
| `SpongeDocs <https://github.com/Spongepowered/SpongeDocs>`_       | The official SpongeProject Documentation              | Expanding, fixing and writing the SpongeDocs                                    |
+-------------------------------------------------------------------+-------------------------------------------------------+---------------------------------------------------------------------------------+
| `Ore <https://github.com/Spongepowered/Ore>`_                     | Plugin hosting solution                               | Development of our plugin hosting solution                                      |
+-------------------------------------------------------------------+-------------------------------------------------------+---------------------------------------------------------------------------------+

SpongeCommon, SpongeForge and SpongeVanilla
===========================================

The SpongeCommon repository is the base which contains all code which is shared between the SpongeForge and SpongeVanilla
implementation. The SpongeForge and SpongeVanilla repositories contain all code which can't be shared between them, as
Forge requires some Forge specific things which won't work on Vanilla and vice versa.

When you refer to the SpongeForge implementation, you're basically talking about everything contained in the
SpongeCommon and SpongeForge repositories. The same applies for SpongeVanilla and SpongeCommon. This is the reason why
building SpongeForge or SpongeVanilla from the repository *without* including SpongeCommon won't work.

Ore
===

Ore is our very own plugin hosting solution. It's written in Python, based on Django and of course open-source!

How everything is tied together
===============================

The image shows the various parts of the Sponge Implementations and how they interact with each other and their dependencies.
On the left side is a typical SpongeForge setup with some SpongeAPI plugins, a Forge mod and a hybrid which uses Forge
(as a mod) and Sponge (as a plugin) to interact. On the right side there's a typical SpongeVanilla setup. You'll notice
that SpongeVanilla doesn't support Forge mods or the hybrid, because SpongeVanilla is missing the Forge functionality:

.. image:: /images/contributing/SpongeProject-structure.svg
    :alt: Repo Overview
