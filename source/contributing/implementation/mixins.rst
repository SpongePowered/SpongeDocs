======
Mixins
======

.. note::
   This page applies to SpongeCommon, SpongeForge, and SpongeVanilla as these three repositories utilize Mixins to hook
   into the underlying implementations (Vanilla Minecraft server and Forge).

Mixins are a way of modifying java code at runtime by adding additional behavior to classes. They enable transplanting
of intended behavior into existing Minecraft objects. Mixins are necessary for all official Sponge implementations
to function.

A basic introduction to some of the core concepts underpinning the mixin functionality we're using to implement Sponge
is available at the `Mixin Wiki <https://github.com/SpongePowered/Mixin/wiki/>`__

*It starts with absolute basics. If you're an experienced java developer, feel free to skip to section 4, where the
mixins themselves are actually discussed.*

If you're looking to get started writing mixins, we also strongly recommend carefully examining all of the examples in
the `SpongeForge repository <https://github.com/SpongePowered/SpongeForge/tree/master/src/example/java/org/spongepowered>`__ which
are extensively documented and cover many of the more complex scenarios. You should also consult the Javadoc of the Mixin
repository itself, since almost everything is already documented.

**Caveat: When contributing mixins, note that you can use neither anonymous classes nor lambda expressions.**

This means expressions like the following will cause mixins to fail horribly and bring death and destruction upon all
that attempt to use Sponge.

.. code-block:: java

    return new Predicate<ItemStack>() {
        @Override
        public boolean test(ItemStack input) {
            return input.getItem().equals(Items.golden_apple);
        }
    }

.. code-block:: java

    return input -> input.getItem().equals(Items.golden_apple);

.. code-block:: java

    return this::checkItem;

This applies to all classes that are annotated with ``@Mixin``. Classes that are not touched by the mixin processor may
make use of those features. However, you can use a static utility class to create your anonymous classes, as unlike
your mixin class that utility class will still exist at runtime, while your mixin class will be merged into the
specified target class. The following code therefore will work.

.. code-block:: java

    public class ItemUtil {
        public static Predicate<ItemStack> typeChecker(final Item item) {
            return new Predicate<ItemStack>() {
                @Override
                public boolean test(ItemStack input) {
                    return input.getItem().equals(item);
                }
            }
        }
    }

    @Mixin(TargetClass.class)
    public abstract class SomeMixin {
        public Predicate<ItemStack> someFunction() {
            return ItemUtil.typeChecker(Items.golden_apple);
        }
    }

.. note::

  The Mixin project will be servicing a number of other projects in addition to Sponge itself. Therefore Mixin has its'
  own documentation together with the repository.
