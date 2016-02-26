===================
Optionals Explained
===================

Much of the Sponge API makes use of Java's `Optional` system on object accessors, but if you've never used ``Optional``
before this might seem like a bit of a peculiar way of doing things. You might be tempted to ask:
*"why do I need to perform an extra step when fetching something from an API object?"*

This section gives a brief summary of ``Optional`` and explains how - and perhaps more importantly why -
it's used throughout the Sponge API.

Let's start with a little history, and look at how accessors - particularly "getters" - typically work when not making
use of ``Optional``.

1. Implicit Nullable Contracts and Why They Suck
================================================

Let's say we have a simple API object ``Entity`` with a ``getFoo()`` method which returns the Entity's ``Foo``.

.. image:: /images/optionals1.png

In the olden times of yore, our plugin might fetch and use the ``Foo`` from the entity using the ``getter`` like this:

.. code-block:: java

    public void someEventHandler(Entity someEntity) {
        Foo entityFoo = someEntity.getFoo();
        entityFoo.bar();
    }

The problem arises because - when designing the API - we have to rely an *implicit* contract on the ``getFoo`` method
with respect to whether the method can (or cannot) return ``null``. This *implicit contract* can be defined in one of
two ways:

* **In the javadoc** - this is bad because it relies on the plugin author reading the javadoc for the method, and the contract may not be clear to the plugin author
* **Using nullable annotations** - this is not ideal because in general these annotations require a tool to be of any use, for example relying on the IDE or compiler to handle the annotations.

.. image:: /images/optionals2.png

Let's assume that the ``getFoo()`` method can - as part of its contract - return null. This suddenly means that our
code above is unsafe as it may result in a ``NullPointerException`` if ``entityFoo`` is null.

.. code-block:: java

    public void someEventHandler(Entity someEntity) {
        Foo entityFoo = someEntity.getFoo();
        entityFoo.bar();
    }

Let's assume our plugin author is savvy to the nullable nature of our ``getFoo`` method and decides to fix the problem
with null checking. Assuming they have defined a local constant ``Foo``, the resultant code looks like this:


.. code-block:: java

    public void someEventHandler(Entity someEntity) {
        Foo entityFoo = someEntity.getFoo();
        if (entityFoo == null) {
            entityFoo = MyPlugin.DEFAULT_FOO;
        }
        entityFoo.bar();
    }

In this example, the plugin author is aware that the method can return null and has a constant available with a
default instance of ``Foo`` which can be used instead. Of course the plugin could just short-circuit the call entirely,
or it could attempt to fetch `Foo` from somewhere else. The key message is that handling nulls even in simple cases
can lead to spaghetti code quite quickly, and moreover relies on the plugin author to explicitly visit the method's
contract to check whether a null check is necessary in the first place.

That's not the only drawback however. Let's consider the API over the longer term and assume that at the time the author
writes their plugin, they visit the method javadoc and see that the method is guaranteed to never return null
(since every `Entity` always has a ``Foo`` available). Great! No convoluted null check required!

However, let's now assume that in a later version of the game, the game developers remove or deprecate the concept of
``Foo``. The API authors update the API accordingly and state that from now on the ``getFoo()`` method
**can** return ``null`` and write this into the method javadoc. Now there's a problem: even diligent plugin authors who
checked the method contract when they first wrote their code are unwittingly handling the method incorrectly: with no
null check in place any code using the ``Foo`` returned from ``getFoo`` is going to raise an NPE.

Thus we can see that allowing implicit nullable contracts leaves us with a selection of pretty awful solutions to
choose from:

* Plugin authors can assume that **all** methods may return null and code defensively accordingly, however we've already seen that this leads to spaghetti code pretty quickly.
* The API authors can define an implicit nullable contract on every API method, in an attempt to make null handling the plugin author's problem, which only exacerbates the previous problem.
* The API authors can assert that any implicit nullable contract they define will never be altered going forward. This means that in the eventuality that they need to handle the removal of a feature from the base game then they must either:

 * Throw an exception - hardly elegant but certainly easier to diagnose than a loose NPE which may be triggered elsewhere in the codebase and be hard to track down
 * Return a "fake" object or invalid value - this means that consuming (plugin) code will continue to work, but creates an ever-increasing burden on the API developers going forward since every deprecated feature will require the creation of yet more fake objects. This could soon lead to the situation where a big chunk of the API is filled with junk objects whose only purpose is to support parts of the API which are no longer in service.

It should be pretty clear by now that there are some sizable headaches attached to *implicit* nullable contracts, made
all the more poignant when the API in question is a layer over an extremely unstable base product. Fortunately,
there is a better way:

2. Optional and the Explicit Nullable Contract
==============================================

As mentioned above, APIs for Minecraft are in a difficult situation. Ultimately they need to provide a platform with
a *reasonable amount of implied stability* atop a platform (the game) with *absolutely no amount of implied stability*.
Thus any API for Minecraft needs to be designed with full awareness that any aspect of the game is liable to change at
any time for any reason in any way imaginable; up to and including being removed altogether!

This volatility is what leads to the problem with nullable method contracts described above.

`Optional` solves the above problems by replacing *implicit contracts* with *explicit* ones. The API never advertises,
*"here is your object, kthxbai"*, instead it presents accessors with a
*"here is a box which may or may not contain the object you asked for, ymmv"*.

.. image:: /images/optionals3.png

By encoding the possibility of returning ``null`` into an explicit contract, we replace the concept of
*null checking* with the more nuanced concept of *may not exist*. We also stipulate this contract *from day one*.

So what does this mean?

In a nutshell, no longer do plugin authors have to worry about the possibility of ``null`` being returned. Instead the
very possibility of a particular object not being available becomes encoded in the very fabric of their plugin code.
This has the same level of inherent safety as constantly performing null-checks, but with the benefit of much more
elegant and readable code in order to do so.

To see why, let's take a look at the above example, converted to use a ``getFoo`` method which returns
``Optional<Foo>`` instead:

.. code-block:: java

    public void someEventHandler(Entity someEntity) {
        Optional<Foo> entityFoo = someEntity.getFoo();
        if (entityFoo.isPresent()) {
            entityFoo.get().bar();
        }
    }

You may note that this example looks very much like a standard null-check, however the use of ``Optional`` actually
carries a little more information in the same amount of code. For example, it is not necessary for someone reading
the above code to check the method contract, it is clear that the method may not return a value, and the handling
of the value's absence is explicit and clear.

So what? Our explicit contract in this case results in basically the same amount of code as a null check - albeit
one that is contractually *enforced* by the getter. *"Whoop de do,"* you say, *"so what?"*

Well the `Optional` boxing allows us to take some of the traditionally more awkward aspects of null-checking and
make them more elegant: consider the following code:

.. code-block:: java

    public void someEventHandler(Entity someEntity) {
        Foo entityFoo = someEntity.getFoo().orElse(MyPlugin.DEFAULT_FOO);
        entityFoo.bar();
    }

Hold the phone! Did we just replace the tedious null-check-and-default-assignment from the example above with a
single line of code? Yes indeed we did. In fact, for simple use cases we can even dispense with the assignment:

.. code-block:: java

    public void someEventHandler(Entity someEntity) {
        someEntity.getFoo().orElse(MyPlugin.DEFAULT_FOO).bar();
    }

This is perfectly safe provided that ``MyPlugin.DEFAULT_FOO`` is always available.

Consider the following example with two entities, using an implicit nullable contract we want to use ``Foo`` from the
first entity, or if not available use ``Foo`` from the second ``entity``, and fall back on our default if neither is
available:

.. code-block:: java

    public void someEventHandler(Entity someEntity, Entity entity2) {
        Foo entityFoo = someEntity.getFoo();
        if (entityFoo == null) {
            entityFoo = entity2.getFoo();
        }
        if (entityFoo == null) {
            entityFoo = MyPlugin.DEFAULT_FOO;
        }
        entityFoo.bar();
    }

Using ``Optional`` we can encode this much much more cleanly as:

.. code-block:: java

    public void someEventHandler(Entity someEntity, Entity entity2) {
        someEntity.getFoo().orElse(entity2.getFoo().orElse(MyPlugin.DEFAULT_FOO)).bar();
    }

This is merely the tip of the ``Optional`` iceberg. In java 8 ``Optional`` also supports the ``Consumer`` and
``Supplier`` interfaces, allowing lambas to be used for *absent* failover. Usage examples for those can be found on the
:doc:`usage` page.

.. note::

    Another explanation on the rationale behind avoiding null references can be found on
    `Guava: Using And Avoiding Null Explained <https://github.com/google/guava/wiki/UsingAndAvoidingNullExplained/>`_.
    Beware that the guava ``Optional`` class mentioned in the linked article is different from java's
    ``java.util.Optional`` and therefore will have method names different from those used here.
