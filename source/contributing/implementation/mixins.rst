======
Mixins
======

.. note:: this page applies to Common, Sponge, and Vanilla.

Mixins are a way of modifying java code at runtime by adding additional behavior to classes. They enable transplanting
of intended behavior into existing Minecraft objects. Mixins are necessary for the Sponge coremod to function.

A basic introduction to some of the core concepts underpinning the mixin functionality we're using to implement Sponge
is available at the `Mixin Wiki <https://github.com/SpongePowered/Mixin/wiki/>`__

*It starts with absolute basics. If you're an experienced java developer, feel free to skip to section 4, where the
mixins themselves are actually discussed.*

If you're looking to get started writing mixins, we also strongly recommend carefully examining all of the examples in
the `Sponge repository <https://github.com/SpongePowered/Sponge/tree/master/src/example/java/org/spongepowered>`__ which
are extensively documented and cover many of the more complex scenarios. You should also consult the Javadoc of the Mixin
repository itself, since almost everything is already documented.

.. Note::
  The Mixin project will be servicing a number of other projects in addition to Sponge itself. Therefore Mixin has its'
  own documentation together with the repository.
