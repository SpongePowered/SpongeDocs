.. _inventory-events:


================
Inventory Events
================

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

===============
Crafting Events
===============

Crafting events fire in addition to the normal click events to allow plugins to change or cancel the recipe result.

`CraftItemEvent.Preview` fires after completing a recipe. Canceling it removes the result from the crafting output slot.
`CraftItemEvent.Craft` fires taking an item out of the crafting slot in any way.

=================
Enchanting Events
=================

Enchanting an item happens in three steps:
`EnchantItemEvent.CalculateLevelRequirement`
`EnchantItemEvent.CalculateEnchantment`
`EnchantItemEvent.Post`





