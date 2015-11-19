=====================
Creating an ItemStack
=====================

If you want to create your own Items, you need to go through several steps. Let's go through a basic example and create
an enchanted diamond sword. We start with our imports:

.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.Game;
    import org.spongepowered.api.data.key.Keys;
    import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
    import org.spongepowered.api.data.meta.ItemEnchantment;
    import org.spongepowered.api.item.Enchantment;
    import org.spongepowered.api.item.ItemTypes;
    import org.spongepowered.api.item.inventory.ItemStack;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.text.format.TextColors;

    import java.util.List;
    import java.util.stream.Collectors;

Alright. The first step is done now. Next up is the ``ItemStack.Builder``, it will create the unenchanted sword for us.
If you don't want to enchant anything, or don't want to give it a beautiful name, then you're almost done.

.. code-block:: java

    public class GenerateSword {

        @Inject private Game game;

        public ItemStack generateItem() {
            ItemStack.Builder builder = this.game.getRegistry().createBuilder(ItemStack.Builder.class);
            ItemStack superMegaAwesomeSword = builder.itemType(ItemTypes.DIAMOND_SWORD).build();

            // Include enchantments or naming here

            return superMegaAwesomeSword;
        }

    }

Now the basic item is done. So what about enchanting and naming, you ask? Here we go! Include this before
``return superMegaAwesomeSword();`` to enchant the sword:

.. code-block:: java

    // Let's just give all sorts of enchantments here.
    EnchantmentData enchantmentData = superMegaAwesomeSword.getOrCreate(EnchantmentData.class).get();
    final List<Enchantment> enchantments = this.game.getRegistry().getAllOf(Enchantment.class).stream().collect(Collectors.toList());
    for (Enchantment enchantment : enchantments) {
        enchantmentData.set(enchantmentData.enchantments().add(new ItemEnchantment(enchantment, 1000)));
    }
    superMegaAwesomeSword.offer(enchantmentData);

And this is the code to give it a beatiful name:

.. code-block:: java

    // Oh! We haven't given it a name...
    superMegaAwesomeSword.offer(Keys.DISPLAY_NAME, Texts.of(TextColors.BLUE, "SUPER", TextColors.GOLD, "MEGA", TextColors.DARK_AQUA, "AWESOME", TextColors.AQUA, " Diamond Sword"));

Finally we want it to be unbreakable:

.. code-block:: java

    //make the sword unbreakable
    superMegaAwesomeSword.offer(Keys.UNBREAKABLE, true);

That's it. you now have a fully enchanted, unbreakable and beautifully named sword which you can spawn or give to
players. The sword goes by the name "SUPERMEGAAWESOME Diamond Sword".

If you're still unsure how the complete example looks, here's the final result:


.. code-block:: java

    import com.google.inject.Inject;
    import org.spongepowered.api.Game;
    import org.spongepowered.api.data.key.Keys;
    import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
    import org.spongepowered.api.data.meta.ItemEnchantment;
    import org.spongepowered.api.item.Enchantment;
    import org.spongepowered.api.item.ItemTypes;
    import org.spongepowered.api.item.inventory.ItemStack;
    import org.spongepowered.api.text.Texts;
    import org.spongepowered.api.text.format.TextColors;

    import java.util.List;
    import java.util.stream.Collectors;

    public class GenerateSword {

        @Inject private Game game;

        public ItemStack generateItem() {
            ItemStack.Builder builder = this.game.getRegistry().createBuilder(ItemStack.Builder.class);
            ItemStack superMegaAwesomeSword = builder.itemType(ItemTypes.DIAMOND_SWORD).build();

            // Let's just give all sorts of enchantments here.
            EnchantmentData enchantmentData = superMegaAwesomeSword.getOrCreate(EnchantmentData.class).get();
            final List<Enchantment> enchantments = this.game.getRegistry().getAllOf(Enchantment.class).stream().collect(Collectors.toList());
            for (Enchantment enchantment : enchantments) {
                enchantmentData.set(enchantmentData.enchantments().add(new ItemEnchantment(enchantment, 1000)));
            }
            superMegaAwesomeSword.offer(enchantmentData);

            // Oh! We haven't given it a name...
            superMegaAwesomeSword.offer(Keys.DISPLAY_NAME, Texts.of(TextColors.BLUE, "SUPER", TextColors.GOLD, "MEGA", TextColors.DARK_AQUA, "AWESOME", TextColors.AQUA, " Diamond Sword"));
            //make the sword unbreakable
            superMegaAwesomeSword.offer(Keys.UNBREAKABLE, true);

            return superMegaAwesomeSword;
        }

    }
