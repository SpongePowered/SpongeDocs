===========
JSON Syntax
===========

This is an example of a ``whitelist.json`` file with correct formatting (although the UUID-s are fictional). Your file
should follow the same syntax.

.. code-block:: json

  [
    {
      "uuid": "01234567-89ab-cdef-0123-456789abcdef",
      "name": "Notch"
    },
    {
      "uuid": "a0b1c2d3-e4f5-0617-2839-4a5b6c7d8e9f",
      "name": "sk89q"
    }
  ]

Format Rules
~~~~~~~~~~~~

- Square braces (``[]``) open and close the file
- Each entry in the file is wrapped with curly braces (``{}``)
- Each key and its corresponding value is typed on its own line
- If more than one exists, both entries and key/value pairs are comma separated
- All strings are in quotation marks
- UUID-s are 32 symbols long, and written in hexadecimal (0-9, a-f).
- the UUID symbols are grouped. First is a group of 8, then three groups of 4, then a group of 12. The groups are
  separated by dashes (``-``)
