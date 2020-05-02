============
Placeholders
============

.. javadoc-import::
    org.spongepowered.api.text.Text
    org.spongepowered.api.service.placeholder.PlaceholderService

A common task for chat plugins is to provide and consume placeholders, tokens that map to :javadoc:`Text` based on 
context. For example, a "name" placeholder may return a player's name, or a "balance" placeholder may return the value
of a player's default account.

Sponge provides a Placeholder service and associated API that provides one location for all text placeholders, as well
as the structure to parse them. This allows for a common way for plugins to provide their own placeholders without 
requiring a specific placeholder library being installed.

Placeholder libraries can replace the :javadoc:`PlaceholderService` to enhance its operation.

.. note::
  The Sponge placeholder service does not provide a way to parse entire strings that contains tokens.

Contents
========

.. toctree::
    :maxdepth: 2
    :titlesonly:

    creatingtokens
    retrievingtext
    implementingservice
