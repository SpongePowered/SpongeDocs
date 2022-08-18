=====================
Basic Inventory Usage
=====================

.. javadoc-import::
    org.spongepowered.api.item.inventory.Inventory
    org.spongepowered.api.item.inventory.Slot


:javadoc:`Inventory` is the base interface for inventories. Sub-Interfaces may provide specialised methods for peek/offer/poll/set/query.

Slots
=====

A :javadoc:`Slot` is a special inventory with a capacity of one.
Slots are accessible by index but some inventories may allow accessing them in different ways too.

.. code-block:: java

    // Returns the slot at a given index
    Optional<Slot> slot = inventory.slot(index);
    // Returns all the slots in the inventory in order
    List<Slot> slots = inventory.slots();
    // returns the number of slots in the inventory
    int capacity = inventory.capacity();

Get items | peek
================
.. code-block:: java

     // Returns the first non-empty ItemStack following the order of `slots()`
    ItemStack firstStack = inventory.peek();
    // Returns the ItemStack at a given index.
    // Note that the result is `Optional.empty()` only when the slot does not exist.
    // If the slot is empty the stack is `ItemStack.empty()`
    Optional<ItemStack> stackAt = inventory.peekAt(index);

    // sums total quantity of all stacks in the inventory.
    // In combination with a query for a stack this can count the quantity of that stack in the inventory.
    int quantity = inventory.totalQuantity();

    // checks for an exact match in the inventory.
    boolean contains = inventory.contains(stack/type);
    // checks for a match ignoring quantity in the inventory.
    boolean containsAny = inventory.containsAny(stack);


Adding items | offer
====================

To add items to an inventory use the offer methods:

.. code-block:: java

    // Adds stacks to the inventory filling it up following the order of `slots()`
    InventoryTransactionResult result1 = inventory.offer(stack);
    // Adds a stack to the slot at given index.
    InventoryTransactionResult result2 = inventory.offer(index, stack);

    // Returns whether a `offer(...)` would succeed.
    boolean canFit = inventory.canFit(stack);
    // Returns the number of empty slots.
    // If it is zero `offer(...)` could still succeed if it can stack with an existing stack.
    int freeCapacity = inventory.freeCapacity();

    // Returns the rejected items if there was not enough space to fit every item.
    List<ItemStackSnapshot> rejected = result1.rejectedItems();
    // Reverts the transaction
    result1.revert();
    // InventoryTransactionResults can be combined
    InventoryTransactionResult combined = result1.and(result2);
    // Reverts the transaction if any of the combined transaction was not successful
    combined.revertOnFailure();

Removing items | poll
=====================

To remove items from an inventory use the polling methods:

.. code-block:: java

    // Removes the first non-empty (analogous to `peek()`) stack in the inventory
    InventoryTransactionResult.Poll result1 = inventory.poll()
    // Removes the first non-empty stack in the inventory up the the given limit.
    // If the limit is higher than what is in the first empty slot it will continue removing the same item from slots after it up to the limit.
    InventoryTransactionResult.Poll result2 = inventory.poll(limit)
    // Removes the stack from given index
    InventoryTransactionResult.Poll result3 = inventory.pollFrom(index)
    // Removes the stack from given index but only up to the given limit
    InventoryTransactionResult.Poll result4 = inventory.pollFrom(index, limit)

    // Returns the polled item
    ItemStackSnapshot polledStack = result1.polledItem();
    // InventoryTransactionResults can be combined
    InventoryTransactionResult combined = result1.and(result2).and(result3).and(result4);
    // Returns the list of polled items - useful when handling combined results
    List<ItemStackSnapshot> polledStack = result.polledItems();


Note that you can combine `InventoryTransactionResult` and if needed `revert()` or `revertOnFailure()` all of them in one go.

Setting items | set
===================

.. code-block:: java

    // Sets the content of a single slot at given index replacing the previous item.
    inventory.set(index, stack);
    // Sets the content of a slot
    slot.set(stack);
    // Sets all slots to ItemStack.empty()
    inventory.clear()

.. note::
  Usually you can access any slot in an inventory by index although some modded inventories may prevent certain operations.


Querying Inventories | query
============================

An inventory may have more than just indexed access.

E.g.

`PlayerInventory` consists of `PrimaryPlayerInventory` (4*9) and the `EquipmentInventory` (armor+offhand)

`PrimaryPlayerInventory` consists of `Hotbar` (1*9) and a `GridInventory` (3*9)

`GridInventory` consists of multiple `InventoryRow` and `InventoryColumn` etc.

Note that some of these implementation are hardcoded so may not be available for modded inventories.

Calling `#children()` gives the next layer of inventories in that structure.

Some inventories provide helper methods to access often used sub-inventories like `PlayerInventory#primary`.

If applicable some inventory interfaces also provide more specific peek/offer/poll/set methods. e.g. `GridInventory.peek(x,y)`

.. code-block:: java

    public static void query() {
        TODO think up some nice query examples
    }

Opening Inventories
===================

ViewableInventory are a subset of inventories that can be opened by a player.
If possible Sponge tracks players currently viewing this inventory.

An "open" ViewableInventory is a `Container`. Usually a container is a view on two inventories:

e.g. when opening a chest you see the chest and the `PrimaryPlayerInventory` part of the players inventory.

In :ref:`inventory-events` involving the player you are most likely to encounter a `Container`.


.. code-block:: java

    // Only ViewableInventory can be opened
    Optional<Container> container1 = player.openInventory(inventory);
    // Optionally provide a title for the container - not supported for all inventories
    Optional<Container> container2 = player.openInventory(inventory, Component.text("My Title"));

Alternatively use an :ref:`inventory-menu`.

.. note::
  Only ViewableInventory can be viewed by a player. But most vanilla inventories are ViewableInventories.



