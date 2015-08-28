==================
Working with Items
==================

Items are represented through an ``ItemStack``. An ``ItemStack`` is an inventory item with information such as the amount of the item in the stack, the type of the item, and extra data such as durability. An ``Item`` itself is the graphical representation of an ``ItemStack`` as an entity.

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

Getting the amount of items in an ``ItemStack`` is relatively easy. The ``getQuantity()`` method provided by ``ItemStack`` will handle this for us.

Modifying ItemStack Data
~~~~~~~~~~~~~~~~~~~~~~~~

Manipulating data such as durability or the lore of an item is accomplished by simply using keys. You just need to specify the key that needs to be changed:

.. code-block:: java
    
    import org.spongepowered.api.data.key.Keys;
    import org.spongepowered.api.item.inventory.ItemStack;

    public void setUnbreakable(ItemStack stack) {
        stack.offer(Keys.UNBREAKABLE, true);
    }
    
In this, we specified that the ``UNBREAKABLE`` key is the key that we would like to change. We then set its value to ``true`` to imply that the item will never break. All of this is enclosed within the ``offer()`` method of the ``ItemStack`` to return our changes back to the ``ItemStack``.

Different keys will require different values based on their job. For example, to change the lore of an item, one would need to specify a ``List`` of ``Text`` rather than an integer. It is also important to perform checks to see if the key can actually apply to the item. For example, some items might not have durability or may already have lore applied to the item.

.. code-block:: java
    
    import java.util.List;
    
    import org.spongepowered.api.data.key.Keys;
    import org.spongepowered.api.item.inventory.ItemStack;
    import org.spongepowered.api.text.Text;

    public void setLore(ItemStack stack, List<Text> itemLore) {
        if(item.get(Keys.ITEM_LORE).isPresent()) {
            item.offer(Keys.ITEM_LORE, itemLore);
        }
    }