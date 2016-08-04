===============================
Formatting Code & Legacy Format
===============================

.. javadoc-import::
    org.spongepowered.api.text.Text
    org.spongepowered.api.text.serializer.FormattingCodeTextSerializer
    org.spongepowered.api.text.serializer.TextSerializer
    org.spongepowered.api.text.serializer.TextSerializers

Text can be represented by using a special character indicating a formatting *of some kind* followed by a unique character
indicating the specific formatting to be used. In the SpongeAPI, there are two different characters that are supported
by default: the ampersand (&) and the section character (§).

Both formatting schemes use the codes found at the `Minecraft Wiki <http://minecraft.gamepedia.com/Formatting_codes>`_.
It is important to note that the Minecraft Wiki only displays the use of the section character (§), but the same codes
work with the ampersand (&) as well.

.. warning::
    Text serialization using any kind of formatting code is limited to representing only some of the capabilities
    of a :javadoc:`Text`, namely formatting. It cannot represent any kind of click or hover actions. If brevity is not
    an issue (as it is in Minecraft chat), it is recommended to use either the :doc:`./xml` or the :doc:`./json`.

Ampersand Formatting
====================
By default, Sponge supports the formatting character `&` (ampersand). Using the ampersand format allows for easier user
input of text formatting and is useful in such cases where brevity is necessary, such as in the Minecraft chat console.

To use this formatting, you can access its corresponding :javadoc:`TextSerializer` with
:javadoc:`TextSerializers#FORMATTING_CODE`. From there, you can use the ``serialize`` or ``deserialize`` methods as
normal:

.. code-block:: java

    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.format.TextColors;
    import org.spongepowered.api.text.format.TextStyles;
    import org.spongepowered.api.text.serializer.TextSerializers;

    Text text = Text.of(TextColors.RED, TextStyles.UNDERLINE, "Hello World!");
    String ampersandFormattedText = TextSerializers.FORMATTING_CODE.serialize(text);

For example, the text "Hello World!", formatted with the color red and an underline would have the following representation
with the ampersand formatting code: `&c&nHello World!`

Legacy Formatting
=================

The legacy text representation is the format widely used in older versions of Minecraft, represented by the section
character (§). Sponge provides serialization and deserialization using the legacy format **only for compatibility**.
It should not be used unless absolutely necessary. Rather, it is recommended to use formatting with the ampersand, as
detailed above. The legacy formatting has a few limitations that the ampersand formatting does not have, the most
obvious among them being that users cannot easily type the section character into the chat.

To use this formatting, you can access its corresponding ``TextSerializer`` with
:javadoc:`TextSerializers#LEGACY_FORMATTING_CODE`. From there, you can use the ``serialize`` or ``deserialize`` methods
as normal:

.. code-block:: java

    Text text = Text.of(TextColors.RED, TextStyles.UNDERLINE, "Hello World!");
    String legacyText = TextSerializers.LEGACY_FORMATTING_CODE.serialize(text);

For example, the text "Hello World!", formatted with the color red and an underline would have the following representation
in the legacy format: `§c§nHello World!`

.. tip::
    Although its use is not recommended, you can get a :javadoc:`FormattingCodeTextSerializer` using whichever
    formatting character you need by calling :javadoc:`TextSerializers#formattingCode(char)`, passing in a ``char`` as
    the only argument.
