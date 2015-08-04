===============
TextXML Format
===============

Sponge uses a predefined form of `XML <https://en.wikipedia.org/wiki/XML>`_ called "TextXML" to denote the coloring
and styling of text. A description of the elements and attributes are as follows:

+-------------------+-----------+---------------------------------------------------------------------------------------+
| Element           | Attribute | Description                                                                           |
+===================+===========+=======================================================================================+
| a (anchor)        | href      | anchor element; Indicates that this text will open the link in the ``href`` attribute |
|                   |           | when clicked.                                                                         |
+-------------------+-----------+---------------------------------------------------------------------------------------+
| b (bold)          |           | **Bolds** the text.                                                                   |
+-------------------+-----------+---------------------------------------------------------------------------------------+
| c (color)         | n         | Applies a color indicated by the ``n`` (name) attribute to the text.                  |
+-------------------+-----------+---------------------------------------------------------------------------------------+
| i (italics)       |           | *Italicizes* the text.                                                                |
+-------------------+-----------+---------------------------------------------------------------------------------------+
| o (obfuscated)    |           | Obfuscates the text (replaces the characters with random characters that change       |
|                   |           | continuously).                                                                        |
+-------------------+-----------+---------------------------------------------------------------------------------------+
| s (strikethrough) |           | Strikes through the text.                                                             |
+-------------------+-----------+---------------------------------------------------------------------------------------+
| span              | onClick,  | Generic holder element that can have click and/or hover actions indicated by the      |
|                   | onHover   | ``onClick`` and ``onHover`` attributes, respectively                                  |
+-------------------+-----------+---------------------------------------------------------------------------------------+
| tr (translation)  | key       | Indicates a text translation                                                          |
+-------------------+-----------+---------------------------------------------------------------------------------------+
| u (underline)     |           | Underlines the text.                                                                  |
+-------------------+-----------+---------------------------------------------------------------------------------------+

For example, the text "Hello World!", formatted with the color red and an underline would have the following representation
in the TextXML format:

.. code-block:: xml

    <c n="red">
        <u>Hello World!</u>
    </c>

This output can be produced using the following code:

.. code-block:: java

    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.format.TextStyles;
    import org.spongepowered.api.text.serializer.TextSerializers;

    Text text = Text.of(TextColors.RED, TextStyles.UNDERLINE, "Hello World!");
    String xmlText = TextSerializers.TEXT_XML.serialize(text);
