=====================
Creating an ItemStack
=====================

Let's create a Diamond Sword:

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

            superMegaAwesomeSword.offer(Keys.UNBREAKABLE, true);

            return superMegaAwesomeSword;
        }

    }


The code above will generate a diamond sword with all available enchantments and the colored display of "SUPERMEGAAWESOME Diamond Sword".