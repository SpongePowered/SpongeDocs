=====================
Introduction to HOCON
=====================

HOCON (Human-Optimized Config Object Notation) is an easy-to-use configuration format. It is used by Sponge and
individual plugins utilizing the Sponge API to store important data, such as configuration or player data. HOCON files
typically use the suffix ``.conf``.

Components
===========

* a ``key`` is a string preceding a value
* a ``value`` is a string, number, object, array, or boolean following a ``key``
* a ``key-value separator`` separates keys from values, and can be either ``:`` or ``=``
* a ``comment`` is prefixed with ``#`` or ``//``, typically serving to provide feedback or instructions

**Example:**

.. code-block:: none

      yellow-thing: "Sponge"

In this example, the ``key`` is ``yellow-thing``, the ``value`` is ``Sponge``, and the ``key-value separator`` is ``:``.

Working with HOCON
==================

HOCON is more flexible than the JSON (JavaScript Object Notation) format in that there are several ways to write valid
HOCON. Below are two examples of valid HOCON.

**Example #1:**

.. code-block:: none

    player: {
        name: "Steve",
        level: 30
    }

**Example #2:**

.. code-block:: none

    player {
        name = "Steve"
        level = 30
    }

In practice, it is best to follow the formatting conventions of the HOCON configuration you are editing. When editing
a HOCON configuration for Sponge or an individual plugin utilizing the Sponge API, the values are likely the only thing
you will be changing unless otherwise specified.

Debugging your configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If a HOCON configuration does not appear to be working, here are some tips.

* Curly braces must be balanced
* Quotation marks must be balanced
* Duplicate keys that appear later take precedence

Specification
=============

More information about the HOCON format can be found `here <https://github.com/typesafehub/config/blob/master/HOCON.md>`__.
