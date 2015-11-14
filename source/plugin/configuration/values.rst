==================
Giving Nodes Value
==================

Of course, the entire purpose of a configuration file is to be able to write and retrieve data. So, let's learn about
retrieving them first. We'll continue to use the example path node discussed in the navigation section.
(``config.blockCheats.enabled``)

Reading Values
~~~~~~~~~~~~~~

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import ninja.leaping.configurate.ConfigurationOptions;

    ConfigurationNode rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());
    ConfigurationNode valueNode = rootNode.getNode((Object[]) "config.blockCheats.enabled".split("\\."));

First, we'll read the value to see if the administrator wants this function enabled. Normally, you can just use the
``getValue()`` method and cast it as a boolean. However, to make things easier for us, Configurate has methods to
automatically try to read them as booleans, as well as other types, for us. Here, we'll use the ``getBoolean(boolean)``
method to retrieve our value.

.. code-block:: java

    boolean blockCheatsEnabled = valueNode.getBoolean(true);

Note the parameter passed to the ``getBoolean(boolean)`` method. The parameter is what the method returns if it cannot
find a value or finds the value invalid for the given type. This is available for most of the value retrieval methods.

Writing Values
~~~~~~~~~~~~~~

Now, what if we'd like to do the opposite and perhaps write this value to save as a default configuration? Of course,
you'll be able to set these values with ``setValue(Object)``. You don't need to worry about casting it either, Configurate
will do that for you.

.. note::

    It will *NOT* deserialize objects it doesn't recognize for you, however. Objects that it recognizes are limited to
    the default types you can seamlessly grab through a convenience getter method, plus the registered mappers that allow
    it to serialize and deserialize an object into a type mapping that allows you to reload the object's values. This will
    be discussed in a different section, but you can also check the official wiki, linked above.

.. code-block:: java

    valueNode.setValue(true);

And now that we've set our value (or values, if you set multiple, which you can), all that's left is to save it using the
``save(ConfigurationNode)`` method of our ``ConfigurationLoader``. The passed ``ConfigurationNode`` should be the root
node of your configuration file, the node that is able to list all the values existing within the configuration. Be sure
to handle the IOException that can be thrown if saving goes wrong.

.. code-block:: java

    import java.io.IOException;

    try {
        loader.save(rootNode);
    } catch(IOException e) {
        //oops!
    }
