====================
Configuration Format
====================

.. javadoc-import::
    org.spongepowered.api.text.Text
    ninja.leaping.configurate.hocon.HoconConfigurationLoader
    ninja.leaping.configurate.loader.ConfigurationLoader

SpongeAPI offers support to serialize text directly to a Configurate configuration file through the use of the
``TypeToken`` class. :javadoc:`Text` objects are saved using the same node structure as the ``Text``\'s JSON
representation.

.. tip::

    For information on how to use Configurate to create configuration files for your plugin, please refer to
    :doc:`/plugin/configuration/index`.

For example, the text "Hello World!", formatted with the color red and and underline would have the following HOCON
representation:

.. code-block:: guess

    {
        underlined=true
        color=red
        text="Hello, world!"
    }

To save a ``Text`` object simply set the value of your desired node using the following code:

.. code-block:: java

    import com.google.common.reflect.TypeToken;
    import ninja.leaping.configurate.ConfigurationNode;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.format.TextStyles;

    ConfigurationNode node = loader.load();
    Text text = Text.of(TextColors.RED, TextStyles.UNDERLINE, "Hello World!");
    node.getNode("mytext").setValue(TypeToken.of(Text.class), text);
    loader.save(node);

You can then load a ``Text`` object using the following code:

.. code-block:: java

    Text text = node.getNode("mytext").getValue(TypeToken.of(Text.class));

.. note::

    This strategy is not limited to :javadoc:`HoconConfigurationLoader`\; any :javadoc:`ConfigurationLoader` will
    suffice.
