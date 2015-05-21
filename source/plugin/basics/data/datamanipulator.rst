================
Datamanipulators
================

.. note::

    As of writing, the features described in this document are not fully implemented yet.

    If acquiring of a ``DataManipulator`` or a ``DataManipulatorBuilder`` fails, it is probably due to the implementation missing.

Data manipulators are the basic way to access and modify mutable data. All data manipulators implement the ``DataManipulator`` interface.

Acquiring Data Manipulators
===========================

In general data manipulators are created using a ``DataManipulatorBuilder``. Those builders can be obtained from the ``DataManipulatorRegistry`` by calling the ``getBuilder()`` method with a class reference detailing what kind of ``DataManipulator`` the builder should construct.
The ``getBuilder()`` method will return ``Optional.absent()`` if the registry contains no builder for the given manipulator class.

The builder can then be used to either construct a new ``DataManipulator`` containing default data by using the ``create()`` method or to construct a manipulator filled with the data from a given ``DataHolder`` with the ``createFrom()`` method. The latter will only return an ``Optional`` as it is possible that supplied ``DataHolder`` does not contain any data which can be represented by this type of ``DataManipulator``, in which case ``Optional.absent()`` will be returned.

**Example: Getting a builder for ``WetData`` manipulators**

.. code-block:: java

    import org.spongepowered.api.data.DataManipulatorBuilder;
    import org.spongepowered.api.data.DataManipulatorRegistry;
    import org.spongepowered.api.data.manipulator.block.TreeData;

    DataManipulatorRegistry registry = game.getRegistry().getManipulatorRegistry();
    Optional<DataManipulatorBuilder<TreeData>> builder = registry.getBuilder(TreeData.class);

**Example: Creating ``TreeData`` objects using the builder**

.. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.DataManipulatorBuilder;
    import org.spongepowered.api.data.manipulator.block.TreeData;

    TreeData defaultData = builder.get().create();

    DataHolder holder = ...;
    Optional<TreeData> copiedData = builder.get().createFrom(holder);

Manipulating Data
=================

Boolean Data
~~~~~~~~~~~~

Some data manipulators represent a single boolean value. Those manipulators usually do not contain any methods for altering their contained data, since their mere presence on a data holder translates to ``true``, while absence signals ``false``.

An example for this is ``Wetdata``. A data holder is wet, if and only if a ``WetData`` object can be created from it.

**Example: Check if a DataHolder contains WetData**

.. code-block:: java

    DataManipulatorBuilder<WetData> builder = ...;
    DataHolder holder = ...;
    boolean isWet = builder.createFrom(holder).isPresent();

Single Value Data
~~~~~~~~~~~~~~~~~

A ``SingleValueData`` manipulator holds a single value of a specific type. For this value, ``getValue()`` and ``setValue()`` methods of the correct type are provided.

Common examples for this type of data manipulator are ``TreeData`` and ``ArtData``, both of which specify the :ref:`Type <dataapi-types>` of the ``DataHolder``.

**Example: Changing TreeData from representing TreeTypes.BIRCH to TreeTypes.DARK_OAK**

.. code-block:: java

    TreeData tree = ...;
    if (tree.getValue() == TreeTypes.BIRCH) {
        tree.setValue(TreeTypes.DARK_OAK);
    }

Integer Data
~~~~~~~~~~~~

``IntData`` is basically a single value data manipulator for ``Integer`` values. In addition to the above mentioned getters and setters, it has a range of valid inputs. This range is limited by a minimal and a maximal value, which are acquired by the ``getMinValue()`` and ``getMaxValue()`` methods respectively. Those boundaries are *inclusive*.

An example for this type of data manipulator is ``ExpirableData``, which denotes the time until its holder expires. This applies, among others, to dropped items.

**Example: Checking if a given integer is valid for a given IntData**

.. code-block:: java

    public boolean isValid(IntData data, int value) {
        return (data.getMinValue() <= value) && (value <= data.getMaxValue());
    }

Listed Data
~~~~~~~~~~~

``ListData`` is pretty much self-explanatory. It provides some of the functions specified in javas ``List`` interface to allow convenient manipulation of the data.

A notable example is ``LoreData``, which contains an ordered list of ``Text``\ s that makes up a description for the ``ItemStack`` it is applied to.

**Example: Appending a line of text to a LoreData**

.. code-block:: java

    LoreData lore = ...;
    lore.add(Texts.of("Infused with FLARD"));

Mapped Data
~~~~~~~~~~~

In a ``MappedData`` manipulator, data is stored in key-value pairings, similar to a java ``Map``. For a given key, not all values may be accepted. Therefore, the ``set()`` methods will return a ``DataTransactionResult`` indicating whether a transfer was successful and, if not, why it was rejected.
To overrule these restrictions, the ``setUnsafe()`` methods may be used instead.

An example for this is ``EnchantmentData`` which maps ``Enchantment``\ s to an integer indicating the level of the enchantment.

.. note::

    For more Information about ``DataTransactionResult``\ s see :ref:`dataapi-transactionresult`.

**Example: Safely applying the "Unbreaking III" enchantment without checking the result**

.. code-block:: java

    EnchantmentData enchantData = ...;
    enchantData.set( Enchantments.UNBREAKING, 3);

Other Data
~~~~~~~~~~

Even if many data manipulators belong to one of the above groups, the Data API is not limited to only those. Each data manipulator interface may provide their own methods of accessing and changing data, so that ultimately it is necessary to check their javadocs or source code to see how the contained data can be read and altered.

An example for such a data manipulator is ``JoinData``, which differs from the above groups in two ways. First of all it contains three methods providing data of two types and in the second place, this data can only be read, not altered.
