================
Basic Item Usage
================

Items are represented through an ``ItemStack``. An ``ItemStack`` is an inventory item with information such as the
amount of the item in the stack, the type of the item, and extra data such as durability. An ``Item`` itself is the
graphical representation of an ``ItemStack`` as an entity. Be aware that you'll always get a copy and *not* the actual
``ItemStack`` and thus, you will need to set it back into an inventory if desired.

Checking an Item's Type
~~~~~~~~~~~~~~~~~~~~~~~

Checking the type of the item is very simple. You just need to call the ``getItem()`` method in the ``ItemStack``.

.. code-block:: java

    import org.spongepowered.api.item.ItemType;
    import org.spongepowered.api.item.ItemTypes;
    import org.spongepowered.api.item.inventory.ItemStack;

    public boolean isStick(ItemStack stack) {
        ItemType type = stack.getItem();
        return type.equals(ItemTypes.STICK);
    }

See how simple that is? Because sticks can stack, we can also find out how many are present.

Getting the amount of items in an ``ItemStack`` is relatively easy. The ``getQuantity()`` method provided by
``ItemStack`` will handle this for us.

Modifying ItemStack Data
~~~~~~~~~~~~~~~~~~~~~~~~

Manipulating data such as durability or the lore of an item is accomplished by simply using keys. You just need to
specify the key that needs to be changed:

.. code-block:: java

    import org.spongepowered.api.data.key.Keys;

    public void setUnbreakable(ItemStack stack) {
        stack.offer(Keys.UNBREAKABLE, true);
    }

In this, we specified that the ``UNBREAKABLE`` key is the key that we would like to change. We then set its value to
``true`` to imply that the item will never break. All of this is enclosed within the ``offer()`` method of the
``ItemStack`` to return our changes back to the ``ItemStack``.

Different keys will require different values based on their job. For example, to change the lore of an item, one would
need to specify a ``List`` of ``Text`` rather than an boolean or other value. It is also important to perform checks to
see if the key can actually apply to the item. For example, some items might not have durability or may already have
lore applied to the item.

.. code-block:: java

    import org.spongepowered.api.text.Text;

    import java.util.List;

    public void setLore(ItemStack stack, List<Text> itemLore) {
        if (stack.get(Keys.ITEM_LORE).isPresent()) {
            stack.offer(Keys.ITEM_LORE, itemLore);
        }
    }

Item Properties
~~~~~~~~~~~~~~~

Certain items may hold specific properties. For example, certain items can mine specific blocks, such as a diamond
pickaxe to obsidian. Properties are used for determining if an item can cause an action without actually checking up
the type of the item. We can check if a block can mine obsidian by using the ``HarvestingProperty`` of that item.

.. code-block:: java

    import org.spongepowered.api.block.BlockTypes;
    import org.spongepowered.api.data.property.item.HarvestingProperty;

    import java.util.Optional;

    public boolean canMineObsidian(ItemStack stack) {
        Optional<HarvestingProperty> optional =
            stack.getProperty(HarvestingProperty.class);

        if (optional.isPresent()) {
            HarvestingProperty property = optional.get();
            return property.getValue().contains(BlockTypes.OBSIDIAN);
        }
        return false;
    }

This code will check to see if the item has a ``HarvestingProperty``, such as a pickaxe. If present, it will then
return if this item can harvest obsidian without the need to check the type of the item. This is useful in the event
that a mod or a Minecraft update adds a new tool with the capabilities of mining obsidian.

.. TODO Link to docs on custom datamaipulators
