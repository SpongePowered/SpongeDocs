=================
Data Manipulators
=================

Data manipulators are the basic way to access and modify mutable data. All data manipulators implement the ``DataManipulator`` interface.

Acquiring Data Manipulators
===========================

In general data manipulators are created using a ``DataManipulatorBuilder``. Those builders can be obtained from the ``DataManipulatorRegistry`` by calling the ``getBuilder()`` method with a class reference detailing what kind of ``DataManipulator`` the builder should construct.
The ``getBuilder()`` method will return ``Optional.absent()`` if the registry does not provide a builder for the given manipulator class.

The builder can then be used to either construct a new ``DataManipulator`` containing default data by using the ``create()`` method or to construct a manipulator filled with the data from a given ``DataHolder`` with the ``createFrom()`` method. The latter will only return an ``Optional`` as it is possible that supplied ``DataHolder`` does not contain any data which can be represented by this type of ``DataManipulator``, in which case ``Optional.absent()`` will be returned.

**Example: Getting a builder**

.. code-block:: java

    import org.spongepowered.api.data.DataManipulatorBuilder;
    import org.spongepowered.api.data.DataManipulatorRegistry;
    import org.spongepowered.api.data.manipulator.block.TreeData;

    DataManipulatorRegistry registry = game.getRegistry().getManipulatorRegistry();
    Optional<DataManipulatorBuilder<TreeData>> builder = registry.getBuilder(TreeData.class);

**Example: Creating TreeData objects using the builder**

.. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.DataManipulatorBuilder;
    import org.spongepowered.api.data.manipulator.block.TreeData;

    TreeData defaultData = builder.get().create();

    DataHolder holder = ...;
    Optional<TreeData> copiedData = builder.get().createFrom(holder);

Manipulating Data
=================

Depending on its subclass, a data holder provides methods for accessing and modifying the contained data. Refer to the JavaDocs of the respective data manipulator class for further information. However, some common behaviours are explained below.

Boolean Data
~~~~~~~~~~~~

Data manipulators representing a single boolean value do not contain any methods for altering their data. If such a data manipulator can be built from a data holder, it means that the data represented evaluates to ``true``. If it cannot be built but is compatible to the data holder, it evaluates to ``false``.

An example for this is ``Wetdata``. A data holder is wet, if and only if a ``WetData`` object can be created from it.

**Example: Check if a DataHolder is wet**

.. code-block:: java

    import org.spongepowered.api.data.DataHolder;
    import org.spongepowered.api.data.DataManipulatorBuilder;
    import org.spongepowered.api.data.manipulator.WetData;

    DataManipulatorBuilder<WetData> builder = ...;
    DataHolder holder = ...;
    boolean isWet = builder.createFrom(holder).isPresent();


Validity Checks
~~~~~~~~~~~~~~~

For some data manipulators, not every value that can be supplied will also be valid. Therefore, data manipulators may reject invalid values. There are currently two possibilities how invalid values may be handled.

The common way of reacting to invalid input is throwing an ``IllegalArgumentException``. This is also what generally will occur on data manipulators. Some data manipulators will also provide means to check if a value is valid. Refer to the API docs of your respective data manipulator to learn which values are valid and if it is possible to perform a validity check before trying to set a value.

However, if a data manipulator accepts data in key-value pairings (``Map``\ s) it may occur that only a portion of the supplied data is invalid and other portions can be applied. Since the operation of setting mapped data is not guaranteed to be atomic, it may be possible that the data was partially applied. The returned ``DataTransactionResult`` allows for checking if the operation was successful and also specifies which data was accepted. A more detailed take on ``DataTransactionResult`` can be found :ref:`here <dataapi-transactionresult>`. This validity check may be bypassed using the ``setUnsafe`` methods.

**Example: CooldownData**

``CooldownData`` stores a single integer as data. However, a cooldown can never be negative, therefore negative values are not valid.

.. code-block:: java

    import org.spongepowered.api.data.manipulator.tileentity.CooldownData;

    CooldownData cooldown = ...
    cooldown.setValue(50);

Will work perfectly fine since the supplied value ``50`` is within the allowed range. If a negative number like ``cooldown.setValue(-3)`` were set, it would fail and throw an ``IllegalArgumentException``.

**Example: EnchantmentData**

``EnchantmentData`` maps ``Enchantment``\ s to their levels (represented as ``Integer``). When trying to set data using the ``set`` method, data is rejected if an enchantment is mapped to a level that is impossible to achieve in vanilla minecraft.

.. code-block:: java

    import org.spongepowered.api.data.DataTransactionResult;
    import org.spongepowered.api.data.manipulator.item.EnchantmentData;
    import org.spongepowered.api.item.Enchantments;

    EnchantmentData enchantment = ...
    DataTransactionResult result = enchantment.set(Enchantments.UNBREAKING, 3);

In the above example, the ``result`` will indicate success since an Unbreaking III enchantment is legally obtainable playing vanilla minecraft. 

Since the Sharpness enchantment can only be obtained up to level 5 using in-game means, in the following example ``result`` will indicate that the operation failed.

.. code-block:: java

    import org.spongepowered.api.data.DataTransactionResult;
    import org.spongepowered.api.data.manipulator.item.EnchantmentData;
    import org.spongepowered.api.item.Enchantments;

    EnchantmentData enchantment = ...
    DataTransactionResult result = enchantment.set(Enchantments.SHARPNESS, 10);
