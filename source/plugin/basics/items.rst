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
        return type == ItemTypes.STICK;
    }
    
See how simple that is? Because sticks can stack, we can also find out how many are present.

Getting the amount of items in an ``ItemStack`` is relatively easy. The ``getQuantity()`` method provided by ``ItemStack`` will handle this for us.

Modifying ItemStack Data
~~~~~~~~~~~~~~~~~~~~~~~~

Manipulating data such as durability or the lore of an item is done with a data set. Using the ``getData()`` method, we can get any data type about an item and modify it.

.. code-block:: java

    import org.spongepowered.api.data.manipulator.item.DurabilityData;
    import org.spongepowered.api.item.inventory.ItemStack;
    
    import com.google.common.base.Optional;

    public void setUnbreakable(ItemStack stack) {
        Optional<DurabilityData> optional = stack.getData(DurabilityData.class);
         // We need to make sure that our item can have durability
        if (optional.isPresent()) {
            DurabilityData durability = optional.get();
            durability.setBreakable(false);
             // Return the data back to the item
            stack.offer(durability);
        }
    }
    
This code accepts an ``ItemStack`` and checks to see if it can have durability without having to check if it is a tool, weapon, or armour. If it does, then the durability is set so that it will never diminish.