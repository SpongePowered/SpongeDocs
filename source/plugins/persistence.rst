=========================
Using the Persistence API
=========================

The Sponge Persistence API introduces the common data structure to represent any possible data produced by the Sponge API.

The Persistence API adds interfaces for DataContainer, DataView, and DataOptions.

DataContainers
==============

Using a DataContainer is very simple:

.. code-block:: java

    @Subscribe(order = Order.FIRST, ignoreCancelled = false)
    public void onPlayerInteract(PlayerInteractBlockEvent event) {
        Optional<ItemStack> itemOption = event.getPlayer().getItemInHand();
        if (itemOption.isPresent()) {
            DataContainer itemContainer = itemOption.toContainer();
            MyCustomDataManager.logItem(event.getPlayer(), itemContainer);
            Optional<List<String>> loreOptions = itemContainer.getList("lore");
            if (loreOptions.isPresent()) {
                for (String string : loreOptions.get()) {
                    System.out.println(string);
                }
            } 
        }
    }

These data structures are map based, so serialization to various data handlers is possible (ie. NBT, flat file, SQL, etc.)

This also enables adding annotations for SerializableAs and DataPath to mark methods and fields with keys that can easily be referenced for automated serialization (eg. Gson).

Example usage:

.. code-block:: java

    @SerializableAs("Banner")
    public interface BannerData extends DataSerializable {
    
        @DataPath("id") String getId();
    
        @DataPath(collapse = true) Vec3i getPosition();
    
        @DataPath("base") DyeColor getBaseColor();
    
        @DataPath(key = "patterns") List<BannerPattern> getPatterns();
    
    
        @SerializableAs(key = "pattern", compoundable = true)
        interface BannerPattern extends DataSerializable {
    
            // Since banners use dye to create their colors
            @DataPath("color") DyeColor getColor();
    
            @DataPath("pattern") List<String> getPatterns();
    
        }
    
    
      void setPosition(Vec3i pos);
      void setColor(DyeColor color);
      void setPatterns(List<BannerPattern> patterns);
      void setPattern(BannerPattern pattern);
    
    }
    
    @SerializableAs(key = "position", compoundable = true)
    public class Vec3i implements DataSerializable {
    
        @DataPath("x") int x;
        @DataPath("y") int y;
        @DataPath("z") int z;
    
    }

Displaying the importance of the viability for BannerData as a DataContainer, the following would exist:

.. code-block:: none

    "Banner" // Name of the DataContainer
        |- "id" -> String
        |- "x" -> Integer
        |- "y" -> Integer
        |- "z" -> Integer
        |- "base" -> DyeColor
        |- "patterns" -> List<BannerPattern>
             |- "BannerPattern" -> BannerPattern
             |    |- "pattern" -> String
             |    |- "color" -> DyeColor
             |- "BannerPattern" -> BannerPattern
             |    |- "pattern" -> String
             |    |- "color" -> DyeColor
             |- "BannerPattern" -> BannerPattern
                  |- "pattern" -> String
                  |- "color" -> DyeColor

As shown in the example, the map based data structure, ``DataContainer#getString("id")`` would return the actual string id provided from the BannerData and likewise,

``DyeColor color = BannerData.serialize().getList("patterns").get(0).getSerializable("color", DyeColor.class);``

These maps may be used to serialize and deserialize Sponge API objects. Internally, various implementations can be used to translate and manipulate similar data structures in NBT (such as, in this case, banner data).

Likewise, all of the DataContainer system is designed to allow serializing EntitySnapshots for various reasons.

[To be continued]
