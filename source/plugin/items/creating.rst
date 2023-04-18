=====================
Creating an ItemStack
=====================

.. javadoc-import::
    org.spongepowered.api.block.BlockState
    org.spongepowered.api.data.Keys
    org.spongepowered.api.entity.Entity
    org.spongepowered.api.entity.EntityType
    org.spongepowered.api.entity.EntityTypes
    org.spongepowered.api.entity.Item
    org.spongepowered.api.item.ItemType
    org.spongepowered.api.item.inventory.ItemStack
    org.spongepowered.api.item.inventory.ItemStack.Builder

If you want to create your own items, you need to go through several steps. Let's go through a basic example and create
an enchanted diamond sword.

To create an :javadoc:`ItemStack`, we need to first grab the builder from the ``ItemStack``. This is done with the
:javadoc:`ItemStack#builder()` method. In the builder, we can specify things such as the :javadoc:`ItemType` or the
quantity of the item. In our example, we will be creating a diamond sword that contains enchantments, a custom name,
and is unbreakable. If you want a plain sword without any other data, then this is all you need to do:

.. code-block:: java

    import org.spongepowered.api.item.ItemTypes;
    import org.spongepowered.api.item.inventory.ItemStack;

    public ItemStack generateSword() {
        ItemStack superMegaAwesomeSword = ItemStack.builder()
            .itemType(ItemTypes.DIAMOND_SWORD).build();
        return superMegaAwesomeSword;
    }

Creating the basic item is done. Now this is a normal diamond sword that we created, but what if we wanted something
more interesting? What about enchanting and naming our sword? We can use :javadoc:`Keys#APPLIED_ENCHANTMENTS` to give 
our sword some enchantments. The following example will give our sword every enchantment in the game, to level 1000.

.. code-block:: java

    import java.util.List;
    import java.util.stream.Collectors;

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.data.meta.ItemEnchantment
    import org.spongepowered.api.item.Enchantment;

    public void withThousandEnchantmentLevel(ItemStack superMegaAwesomeSword){
        List<Enchantment> enchantments = RegistryTypes
            .ENCHANTMENT_TYPE
            .get()
            .stream()
            .filter(type -> type.canBeAppliedToStack(superMegaAwesomeSword))
            .map(type -> Enchantment.of(type, 1000))
            .collect(Collectors.toList());

        superMegaAwesomeSword.offer(Keys.APPLIED_ENCHANTMENTS);
    }

Now let's say we wanted to give our overpowered sword a cool name to go with it. Here, we can directly offer a key to
the ``ItemStack``. Using this key, we can change the name of the ``ItemStack`` to "SUPER MEGA AWESOME Diamond Sword"

.. code-block:: java

    import net.kyori.adventure.text.Component;
    import net.kyori.adventure.text.TextComponent;
    import net.kyori.adventure.text.format.NamedTextColor;
    import org.spongepowered.api.data.Keys;
    import org.spongepowered.api.item.ItemTypes;

    superMegaAwesomeSword.offer(Keys.DISPLAY_NAME, TextComponent.ofChildren(
        Component.text("SUPER ", NamedTextColor.BLUE),
        Component.text("MEGA ", NamedTextColor.GOLD),
        Component.text("AWESOME ", NamedTextColor.DARK_AQUA),
        ItemTypes.DIAMOND_SWORD.get().asComponent().color(NamedTextColor.AQUA));

Finally, to make the sword unbreakable, we can use keys again:

.. code-block:: java

    superMegaAwesomeSword.offer(Keys.UNBREAKABLE, true);

That's it. You now have a fully enchanted, unbreakable, and beautifully named sword which you can give to players.

Spawning the Item
=================

Sure, we can simply put the sword into a player's inventory, but what if we wanted to throw it out into the open world
and spawn the item? This is where :doc:`entity spawning <../entities/spawning>` comes into play. Since the in-game
graphical representation of an ``ItemStack`` is :javadoc:`Item`, we can spawn it in similarly to a normal
:javadoc:`Entity`. The :javadoc:`EntityType` will simply be :javadoc:`EntityTypes#ITEM` and we will need to specify
that the ``Entity`` will represent our ``ItemStack``. This can be done using the :javadoc:`Keys#REPRESENTED_ITEM` key.
An example is shown below:

.. code-block:: java

    import org.spongepowered.api.entity.Entity;
    import org.spongepowered.api.entity.EntityTypes;
    import org.spongepowered.api.event.CauseStackManager.StackFrame;
    import org.spongepowered.api.world.Location;
    import org.spongepowered.api.world.World;
    import org.spongepowered.api.world.server.ServerWorld;

    import java.util.Optional;
    
    public void spawnItem(ItemStack superMegaAwesomeSword, ServerLocation spawnLocation) {
        ServerWorld world = spawnLocation.world();
        Item item = world.createEntity(EntityTypes.ITEM, spawnLocation.getPosition());
        item.offer(Keys.REPRESENTED_ITEM, superMegaAwesomeSword.createSnapshot());

        try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
            frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLACEMENT);
            extent.spawnEntity(item);
        }
    }

Creating an ItemStack From a Block
==================================

An ``ItemStack`` for a block can be created by using the method :javadoc:`ItemStack.Builder#itemType(ItemType)`
similarly to normal items, but what if we wanted to create an ``ItemStack`` from a :javadoc:`BlockState` itself? To
create an ``ItemStack`` from a ``BlockState``, you would need to use the
:javadoc:`ItemStack.Builder#fromBlockState(BlockState)` method on the ``ItemStack`` builder. An example of this is
shown below:

.. code-block:: java

    import org.spongepowered.api.block.BlockState;

    public ItemStack createStack(BlockState state) {
        return ItemStack.builder().fromBlockState(state).build();
    }
