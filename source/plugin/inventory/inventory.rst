==
Inventory
==

`Inventory` is the base interface for inventories. Inventories allow you to access its content by querying for various parts/slots.

`slot(index)` gives you the slot at a given index.
`slots()` returns all the slots in the inventory in order
`capacity()` returns the number of slots in the inventory
`peek()` gives you the first non-empty ItemStack following the order of `slots()`.
`peekAt(index)` gives you the ItemStack at a given index. Note that the result is `Optional.empty()` only when the slot does not exist. If the slot is empty the stack is `ItemStack.empty()`
`contains(stack/type)` checks for an exact match in the inventory.
`containsAny(stack)` checks for a match ignoring quantity in the inventory.

To add items to an inventory use the various offer methods:
`offer(stack...)` adds stacks to the inventory filling it up following the order of `slots()`
`offer(index, stack)` adds a stack to the slot at given index.
`canFit(stack)` checks whether a `offer(...)` would succeed.
`freeCapacity()` checks for the number of empty slots. If it is zero `offer(...)` could still succeed if it can stack with an existing stack.
`totalQuantity()` sums total quantity of all stacks in the inventory. In combination with a [query] for a stack this can count the quantity of that stack in the inventory.

The `InventoryTransactionResult` indicates whether offering the stacks to the inventory was successful or not.
e.g. if there was not enough space to fit every item you can check `rejectedItems()` to drop them instead or `revert()` everything.

To remove items from an inventory use the various polling methods:
`poll()` analogous to `peek()` removes the first non-empty stack in the inventory
`poll(limit)` is similar and removes the first non-empty stack in the inventory up the the given limit. If the limit is higher than what is in the first empty slot it will continue removing the same item from slots after it up to the limit.
`pollFrom(index)` removes the stack from given index
`pollFrom(index, limit)` removes the stack from given index but only up to the given limit

All poll operations return a `InventoryTransactionResult.Poll` which returns the `polledItem`.

Note that you can combine `InventoryTransactionResult` and if needed `revert()` or `revertOnFailure()` all of them in one go.

Lastly you can also set the slot-content replacing any previous item using:
`set(index, stack)` to set the content of a single slot.
`clear()` to empty all slots

Note: Usually you can access any slot in an inventory by index although some modded inventories may prevent certain operations.

An inventory may have more than just indexed access.
E.g.
`PlayerInventory` consists of `PrimaryPlayerInventory` (4*9) and the `EquipmentInventory` (armor+offhand)
`PrimaryPlayerInventory` consists of `Hotbar` (1*9) and a `GridInventory` (3*9)
`GridInventory` consists of multiple `InventoryRow` and `InventoryColumn` etc.
Note that some of these implementation are hardcoded so may not be available for modded inventories.

Calling `#children()` gives the next layer of inventories in that structure.
Some inventories provide helper methods to access often used sub-inventories like `PlayerInventory#primary`.
If applicable some inventory interfaces also provide more specific peek/offer/poll/set methods. e.g. `GridInventory.peek(x,y)`

==
Building free form inventories
==

You can build inventories by either joining two inventories together using `union(inventory)`
or building a new inventory from scratch or based on other inventories:

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

==
Viewable Inventories
==

ViewableInventory are a subset of inventories that can be opened by a player.
If possible Sponge tracks players currently viewing this inventory.
ViewableInventory can be used in [InventoryMenu] to allow low level callbacks on the open container instead of using events.

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

To open a `ViewableInventory` pass it to `player.openInventory(inventory)`. The displayName can be passed as a second optional parameter.
Note that although `openInventory` accepts any inventory a Container will only be opened for a ViewableInventory. Most vanilla inventories are ViewableInventories.

Alternatively you can create or pass the ViewableInventory to an InventoryMenu.

An "open" ViewableInventory is a `Container`. Usually a container is a view on two inventories:
e.g. when opening a chest you see the chest and the `PrimaryPlayerInventory` part of the players inventory.


==
InventoryMenu
==

InventoryMenu is Helper and wrapper around ViewableInventories/Containers.

You can swap out the ViewableInventory of an open menu.
If the ViewableInventory has the same ContainerType this can even happen without closing it on the client side.

Registering `InventoryCallbackHandler`s provides low level callbacks for actions with the container.

Setting the container title.
Setting readonly mode.


==
Inventory Events
==

Inventory events have a bunch of sub-events for specific actions that happen but they usually fall in those 3 base categories:

Events changing an inventory: `ChangeInventoryEvent`.
Events for interactions with a container (open inventory): `InteractContainerEvent`/`ClickContainerEvent`.
Events for hopper or droppers transferring items: `TransferInventoryEvent`

All `InteractContainerEvent` have a cursor transaction which represent the change of the stack the player is holding on its cursor.
All `ChangeInventoryEvent` have a list of slot-transactions which represent the changes on slots in the inventory.
`ClickContainerEvent` may have a primary interaction slot.

Some examples:
Picking up an item with left-click in an inventory will cause a change on the cursor and one slot-transaction on the clicked slot. Event `ClickContainerEvent.Primary`
Transferring an item to the other inventory with shift-left-click in an inventory will cause no change on the cursor and but two slot-transaction on the clicked slot and target slot. Event `ClickContainerEvent.Shift.Primary`
Dropping a single item from the cursor with right-click outside the menu will cause a change on the cursor. Event: `ClickContainerEvent.Drop.Outside.Secondary`
Filling in a recipe using the recipe-book will cause a bunch of slot-transactions. Event: `ClickContainerEvent.Recipe.Single`
Scrolling through the hotbar has no cursor or slot-transactions. Event: ChangeInventoryEvent.Held

==
Crafting Events
==

Crafting events fire in addition to the normal click events to allow plugins to change or cancel the recipe result.

`CraftItemEvent.Preview` fires after completing a recipe. Canceling it removes the result from the crafting output slot.
`CraftItemEvent.Craft` fires taking an item out of the crafting slot in any way.

==
Enchanting Events
==

Enchanting an item happens in three steps:
`EnchantItemEvent.CalculateLevelRequirement`
`EnchantItemEvent.CalculateEnchantment`
`EnchantItemEvent.Post`





