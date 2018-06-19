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
the `SpongeCommon repository <https://github.com/SpongePowered/SpongeCommon/tree/stable-7/src/example/java/org/spongepowered>`__ which
are extensively documented and cover many of the more complex scenarios. You should also consult the Javadoc of the Mixin
repository itself, since almost everything is already documented.

.. note::

  The Mixin project will be servicing a number of other projects in addition to Sponge itself. Therefore Mixin has its'
  own documentation together with the repository.


Mixins and Inner Classes
========================

While you can use lambdas, anonymous and inner classes inside mixins, you cannot use an anonymous/inner class within
another anonymous/inner class that is also inside a mixin, unless one of the inner classes is ``static``.

This means expressions like the following will cause mixins to fail horribly and bring death and destruction upon all
that attempt to use Sponge.

.. code-block:: java

    return new Collection<ItemStack>() {
        @Override
        public Iterator<ItemStack> iterator() {
            return new Iterator<ItemStack>() {
                // THIS WILL NOT WORK!

                @Override
                public boolean hasNext() {
                    // Code skipped
                }

                @Override
                public ItemStack next() {
                    // Code skipped
                }
            };
        }

        // Other methods skipped
    };

This applies to all classes that are annotated with ``@Mixin``. Classes that are not touched by the mixin processor may make use of those
features. There are two ways to work around this.

One option is to use a separate utility class, as unlike your mixin class that utility class will still exist at runtime, while your mixin
class will be merged into the specified target class. The following code therefore will work.

.. code-block:: java

    public class SampleCollection implements Collection<ItemStack> {

        private final TargetClass target;

        public SampleCollection(TargetClass target) {
            this.target = target;
        }

        @Override
        public Iterator<ItemStack> iterator() {
            return new Iterator<ItemStack>() {

                @Override
                public boolean hasNext() {
                    // Code skipped
                }

                @Override
                public ItemStack next() {
                    // Code skipped
                }
            };
        }

        // Other methods skipped
    }

    @Mixin(TargetClass.class)
    public abstract class SomeMixin {
        public Collection<ItemStack> someFunction() {
            return new SampleCollection((TargetClass) (Object) this);
        }
    }


The other option is simply to place all of the nested inner classes directly into the mixin or one of its methods, as opposed to one inside
the other. For example:

.. code-block:: java

    @Mixin(TargetClass.class)
    public abstract class SomeMixin {

        private final class SampleIterator implements Iterator<ItemStack> {

            @Override
            public boolean hasNext() {
                // Code skipped
            }

            @Override
            public ItemStack next() {
                // Code skipped
            }
        }

        public Collection<ItemStack> someFunction() {
            return new Collection<ItemStack>() {
                @Override
                public Iterator<ItemStack> iterator() {
                    return new SampleIterator();
                }

                // Other methods skipped
            };
        }
    }
