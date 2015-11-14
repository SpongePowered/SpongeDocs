========================
Navigating Through Nodes
========================

.. note::

    The following discussion about paths uses a period (.) as a separator character. This is not always the character
    used, but it is a common way of denoting the next node in a path.

Let's discuss how node paths work first. They're just like file paths, a collection of nodes, separated by a single
character, leading to the final child node that holds a value, whether that value be a normal value, a list, or simply
another node. As an example, ``config.blockCheats.enabled`` is a path, leading to the node ``enabled``, under
``blockCheats``, which is under ``config``. In a configuration file, this would appear like so:

.. code-block:: json

    config {
        blockCheats {
            enabled=value
        }
    }

Moving Around
~~~~~~~~~~~~~

.. tip::

    To make node retrieval easier (and to give less load on the system), it is advised to store a reference to the root
    node of your configuration file. This allows you to always be able to get the node using the full path, and avoids
    using the ``getPath()`` method, which is said to be a little intensive by official documentation.

Let's start off by defining our root node again. We'll be using a blank node to write on.

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.ConfigurationOptions;

    ConfigurationNode rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());

The method to get a node from a node is the ``getNode(Object...)`` method. Note that ``Object...`` type for the parameter,
as this means you cannot simply just give it a path like ``config.blockCheats.enabled``. Doing so results in a config file
like this:

.. code-block:: json

    config.blockCheats.enabled=value

... which is not the organized intended result we're looking for. Instead, what you -can- do is split the path by the
separator character so it ends up as separate arguments.

.. code-block:: java

    rootNode.getNode("config", "blockCheats", "enabled");

With that line of code, it should give us a ``ConfigurationNode`` object representative of the child node at the path of
``config.blockCheats.enabled``. At that point, it is up to you what you want to do with it.
