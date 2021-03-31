============
Placeholders
============

A common task for chat plugins is to provide and consume placeholders, tokens that map to ``Component`` based on 
context. For example, a "name" placeholder may return a player's name, or a "balance" placeholder may return the value
of a player's default account.

Sponge provides a simple Placeholder API that provides a registry for placeholders, as well a structure to parse them.
This allows for a common way for plugins to provide their own placeholders without requiring a specific placeholder 
library being installed.

.. note::
  The Sponge placeholder API does not provide a way to parse entire strings that contain tokens.

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    creatingtokens
    retrievingtext
