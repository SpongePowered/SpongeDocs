==================
Inventory Creation
==================


Building free form inventories
==============================

You can build inventories by either joining two inventories together using `union(inventory)`
or building a new inventory from scratch or based on other inventories:

.. note::
  Free form inventories **cannot** be opened by a player. To show the inventory to a player a ViewableInventory is needed.

.. code-block:: java

    // Basic indexed Inventory
    public static Inventory buildIndexedInventory(int capacity) {
        return Inventory.builder().slots(capacity).completeStructure().plugin(this.pluginContainer).build();
    }

    // Basic grid Inventory
    public static Inventory buildIndexedInventory(int x, int y) {
        return Inventory.builder().grid(x, y).completeStructure().plugin(this.pluginContainer).build();
    }

    // Combined inventories
    public static Inventory buildIndexedInventory(Inventory base, int additionalCapacity) {
        return Inventory.builder().inventory(base).slots(additionalCapacity).completeStructure().plugin(this.pluginContainer).build();
    }

    // Inventory with carrier and identity
    public static Inventory buildIndexedInventory(int capacity) {
        return Inventory.builder().slots(capacity).completeStructure()
            .plugin(this.pluginContainer)
            .identity(uuid)
            .carrier(carrier)
            .build();
    }

Building viewable inventories
=============================

To build a viewable inventory you need to follow the strict structure of a `ContainerType`.
Completing the structure on en empty structure will attempt to replicate the vanilla inventory.
Completing the structure on an incomplete structure will fill the rest with empty dummy slots.

.. code-block:: java

    // Simple 3x3 inventory - looks like a Dropper
    public ViewableInventory simpleThreeByThree() {
        return ViewableInventory.builder().type(ContainerTypes.GENERIC_3X3).completeStructure().plugin(this.plugin).build();
    }

    // To replicate vanilla behaviour of an inventory. e.g. anvil provide no structure
    public ViewableInventory vanillaAnvil() {
        return ViewableInventory.builder().type(ContainerTypes.ANVIL).completeStructure().plugin(this.plugin).build();
    }

    // Chest 9x3 View of 3 other 3x3 inventories
    public ViewableInventory threeByThreeByThree() {
        Inventory i1 = Inventory.builder().grid(3, 3).completeStructure().plugin(this.pluginContainer).build();
        Inventory i2 = Inventory.builder().grid(3, 3).completeStructure().plugin(this.pluginContainer).build();
        Inventory i3 = Inventory.builder().grid(3, 3).completeStructure().plugin(this.pluginContainer).build();
        return ViewableInventory.builder().type(ContainerTypes.GENERIC_9X3)
            .grid(i1.slots(), Vector2i.from(3, 3), Vector2i.from(0, 0))
            .grid(i2.slots(), Vector2i.from(3, 3), Vector2i.from(3, 0))
            .grid(i3.slots(), Vector2i.from(3, 3), Vector2i.from(6, 0))
            .completeStructure().plugin(this.plugin).build();
    }
