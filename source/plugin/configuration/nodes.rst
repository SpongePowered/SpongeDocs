===================
Configuration Nodes
===================

.. javadoc-import::
    ninja.leaping.configurate.ConfigurationNode
    ninja.leaping.configurate.commented.CommentedConfigurationNode
    ninja.leaping.configurate.loader.ConfigurationLoader
    ninja.leaping.configurate.objectmapping.serialize.TypeSerializer
    java.lang.Object

In memory, the configuration is represented using :javadoc:`ConfigurationNode`\ s. A ``ConfigurationNode`` either holds
a value (like a number, a string or a list) or has child nodes, a tree-like configuration structure. When using a
:javadoc:`ConfigurationLoader` to load or create new configurations, it will return the **root node**. It is
recommended that you always keep a reference to that root node stored somewhere.

.. note::

    Depending on the ``ConfigurationLoader`` used, you might even get a :javadoc:`CommentedConfigurationNode`, which in
    addition to normal ``ConfigurationNode`` behavior is able to retain a comment that will persist on the saved config
    file.


Navigating Nodes
================

Every child node is identified by a key, most commonly a string. When going from one node (for example the root node)
to a specific child node, it may be necessary to move through multiple layers of child nodes. The keys of the child
nodes passed make up the **path** of the target node. In the following example, there is a node holding the value
``false`` at the path ``modules`` ``blockCheats`` ``enabled`` (from the root node) in the following HOCON representation
of a config.

.. note::

    When written down, paths are commonly represented by joining the keys together with a separator char, usually ``.``.
    The above mentioned path would be written ``modules.blockCheats.enabled``.

.. code-block:: none

    modules {
        blockCheats {
            enabled=true
        }
    }

In Java, you can get the child node for a path using the ``getNode(...)`` method. The method accepts any number of
arguments, where each argument is one key in the path. The path ``modules.blockCheats.enabled`` from a root node is
acquired as follows.

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;

    ConfigurationNode rootNode = ...;
    ConfigurationNode targetNode = rootNode.getNode("modules", "blockCheats", "enabled");

.. note::

    The function calls ``node.getNode("foo", "bar")`` and ``node.getNode("foo").getNode("bar")`` are equivalent.

Values
======

Basic Values
~~~~~~~~~~~~

Basic value types like ``int``, ``double``, ``boolean`` or ``String`` each have their own convenience getter method
which will return the value or a default if the node does not contain a value of that type. Let's check if the server
administrator wants our plugin to enable its blockCheats module by checking the value at the
``modules.blockCheats.enabled`` path.

.. code-block:: java

    boolean shouldEnable = rootNode.getNode("modules", "blockCheats", "enabled").getBoolean();

Yes, it's really as simple as that. Similar to the above example, methods like :javadoc:`ConfigurationNode#getInt()`,
:javadoc:`ConfigurationNode#getDouble()` or :javadoc:`ConfigurationNode#getString()` exist that allow you to
conveniently grab a value of that type.

To set a basic value to a node, just use the :javadoc:`ConfigurationNode#setValue(Object)` method. Don't be confused
that it accepts an ``Object`` - this means that it can take anything and will determine how to proceed from there by
itself.

Imagine the blockCheats module is deactivated by a user command. This change will need to be reflected in the config
and can be done as follows:

.. code-block:: java

    rootNode.getNode("modules", "blockCheats", "enabled").setValue(false);


.. warning::

    Anything other than basic value types cannot be handled by those basic functions, and must instead be read and
    written using the (de)serializing Methods described below. Basic types are those that are natively handled by the
    underlying implementation of the file format used by the ``ConfigurationLoader``, but generally include the
    primitive data types, ``String``\ s as well as ``List``\ s and ``Map``\ s of basic types.

(De)Serialization
~~~~~~~~~~~~~~~~~

If you attempt to read or write an object that is not one of the basic types mentioned above, you will need to pass it
through deserialization first. In the ``ConfigurationOptions`` used to create your root ``ConfigurationNode``, there
is a collection of :javadoc:`TypeSerializer`\ s that Configurate uses to convert your objects to a
``ConfigurationNode`` and vice versa.

In order to tell Configurate what type it is dealing with, we have to provide a guava ``TypeToken``. Imagine we want
to read a player ``UUID`` from the config node ``towns.aFLARDia.mayor``. To do so, we need to call the ``getValue()``
method while providing a ``TypeToken`` representing the ``UUID`` class.

.. code-block:: java

    import java.util.UUID;

    UUID mayor = rootNode.getNode("towns", "aFLARDia", "mayor").get(TypeToken.of(UUID.class));

This prompts Configurate to locate the proper ``TypeSerializer`` for ``UUID``\ s and then use it to convert the stored
value into a ``UUID``. The ``TypeSerializer`` (and by extension the above method) may throw an ``ObjectMappingException``
if it encounters incomplete or invalid data.

Now if we we want to write a new ``UUID`` to that config node, the syntax is very similar. Use the ``setValue()``
method with a ``TypeToken`` and the object you want to serialize.

.. code-block:: java

    rootNode.getNode("towns","aFLARDia", "mayor").setValue(TypeToken.of(UUID.class), newUuid);

.. note::

    Serializing a value will throw an ``ObjectMappingException`` if no ``TypeSerializer`` for the given ``TypeToken``
    can be found.

For simple classes like ``UUID``, you can just create a ``TypeToken`` using the static ``TypeToken.of()`` method.
But when the class you want to use has type parameters of its own (like ``Map<String,UUID>``) the syntax gets a
little more complicated. In most cases you will know exactly what the type parameters will be at compile time, so
you can just create the ``TypeToken`` as an anonymous class: ``new TypeToken<Map<String,UUID>>() {}``. That way,
even generic types can conveniently be written and read.

.. seealso::
    For more information about ``TypeToken``\ s, refer to the `guava documentation
    <https://github.com/google/guava/wiki/ReflectionExplained>`_

The types serializable using those methods are:

* Any basic value (see above)
* Any ``List`` or ``Map`` of serializable types
* The types ``java.util.UUID``, ``java.net.URL``, ``java.net.URI`` and ``java.util.regex.Pattern``
* Any type that has been made serializable as described on :doc:`the config serialization page <serialization>`


Defaults
~~~~~~~~

Unlike the Sponge API, the Configurate library does not use ``Optional`` for values that might not be present but null.
While the getters for primitive methods (like ``getBoolean()`` or ``getInt()``) might return ``false`` or ``0``, those
that would return an object (like ``getString()``) will return ``null`` if no value is present. If you do not want to
manually handle those special cases, you can use *default values*. Every ``getXXX()`` method discussed above has an
overloaded form accepting an additional parameter as a default value.

Let us take a look at the example for reading a boolean value again.

.. code-block:: java

    boolean shouldEnable = rootNode.getNode("modules", "blockCheats", "enabled").getBoolean();

This call will return ``false`` if either the value ``false`` is saved in the config or the value is not present in the
config. Since those two cases are indistinguishable we have no simple way of setting our variable to ``false`` only if
that is the value specified on the config. Unless we specify ``true`` as the default value.

.. code-block:: java

    boolean shouldEnable = rootNode.getNode("modules", "blockCheats", "enabled").getBoolean(true);

Similarly, you can specify defaults on any value you get from the config, thus avoiding ``null`` returns or
``ObjectMappingException`` caused by the absence of the whole value. It also works on the deserializing ``getValue()``
method. Some examples:

.. code-block:: java

    String greeting = rootNode.getNode("messages", "greeting").getString("FLARD be with you good man!");

    UUID mayor = rootNode.getNode("towns", "aFLARDia", "mayor")
                            .getValue(TypeToken.of(UUID.class), somePlayer.getUniqueId());

Another useful application of those defaults is that they can be copied to your configuration if needed. Upon creation
of your root configuration node, you can create your ``ConfigurationOptions`` with ``setShouldCopyDefaults(true)``.
Subsequently, whenever you provide a default value, Configurate will first check if the value you're trying to get is
present, and if it is not, it will first write your default value to the node before returning the default value.

Let's assume your plugin is running for the first time and the config file does not exist yet. You try to load it
with ``ConfigurationOptions`` that enable copying of default values and get an empty config node. Now you run the
line ``rootNode.getNode("modules", "blockCheats", "enabled").getBoolean(true)``. As the node does not yet exist,
configurate creates it and writes the value ``true`` to it as per the ``ConfigurationOptions`` before returning it.
When the config is then finished, the value ``true`` will persist on the node without ever being explicitly set.
