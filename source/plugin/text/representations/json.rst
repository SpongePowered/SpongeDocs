===========
JSON Format
===========

JSON is `JavaScript Object Notation <https://www.json.org/>`_, a "light-weight data-interchange format" that is "easy
for humans to read and write" and "for machines to parse and generate". The JSON web site details the structure of 
text represented in the JSON format. In addition, the 
`Minecraft Wiki <https://minecraft.gamepedia.com/Commands#Raw_JSON_Text>`_ provides Minecraft-related information about 
JSON files.

.. tip::

    The data are stored in structures called trees. Each data point on a tree is a node. See 
    :doc:`/plugin/configuration/nodes` for more information on this topic.

For example, the text ``"Hello World!"``, formatted with the color red and an underline, would have the following 
representation in JSON:

.. code-block:: json

    {
        "underlined":true,
        "color":"red",
        "text":"Hello World!"
    }

This output can be produced using the following code:

.. code-block:: java

    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.format.TextStyles;
    import org.spongepowered.api.text.serializer.TextSerializers;

    Text text = Text.of(TextColors.RED, TextStyles.UNDERLINE, "Hello World!");
    String jsonText = TextSerializers.JSON.serialize(text);
