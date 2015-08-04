===========
JSON Format
===========

JSON is `JavaScript Object Notation <http://www.json.org/>`_, a "light-weight data-interchange format" that is "easy
for humans to read and write" and "for machines to parse and generate". The
`Minecraft Wiki <http://minecraft.gamepedia.com/Commands#Raw_JSON_Text>`_ details the structure of text represented
in JSON.

For example, the text "Hello World!", formatted with the color red and an underline would have the following representation
in JSON:

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
