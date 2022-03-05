.. _inventory-menu:

==============
Inventory Menu
==============

InventoryMenu is Helper and wrapper around ViewableInventories/Containers.

ViewableInventory can be used in menus to allow low level callbacks on the open container instead of using events.

You can swap out the ViewableInventory of an open menu.
If the ViewableInventory has the same ContainerType this can even happen without closing it on the client side.

Registering `InventoryCallbackHandler`s provides low level callbacks for actions with the container.

Setting the container title.
Setting readonly mode.

.. code-block:: java

    public static void callbacks() {
        TODO think up some nice callback examples
    }
