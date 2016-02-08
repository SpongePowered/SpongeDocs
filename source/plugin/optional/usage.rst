==============
Usage Examples
==============

Now that we learned why ``Optional``\ s are used, let us take a look what we can actually do with them in java. These
code examples (and Sponge) use the `java.util.Optional <https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html>`_
class available in Java 8.

Obtaining the wrapped Value
===========================

The ``get()`` method will unwrap an ``Optional`` and return the wrapped value. If no value is present, calling ``get()``
will throw a ``NoSuchElementException``, so a presence check should be performed first.

.. code-block:: java

    Optional<String> opt = getOptionalString();
    String wrappedString = opt.get();

Handling absent Values
======================

The purpose of the ``Optional`` type is representing a value that might or might not be there. As such, many use cases
revolve around handling absent values.

Presence Check
~~~~~~~~~~~~~~

The ``isPresent()`` method returns true if a value is present on the ``Optional``. It can provide the most basic
verification and is an equivalent to a classic ``null`` check.

.. code-block:: java

    Optional<String> opt = getOptionalString();
    if (opt.isPresent()) {
        String wrappedString = opt.get();
        // more code
    }

Using default Values
~~~~~~~~~~~~~~~~~~~~

A common pattern is falling back to a default value if none is present. The ``orElse()`` method allows for a single
line statement that will return either the value present on the ``Optional`` or the supplied default value.

Instead of

.. code-block:: java

    Optional<String> optionalString = optionalString();
    String someString;
    if (optionalString.isPresent()) {
        someString = optionalString.get();
    } else {
        someString = DEFAULT_STRING;
    }

just use

.. code-block:: java

    String someString = getOptionalString().orElse(DEFAULT_STRING);

In some cases a default value has to be calculated in a way that has side effects or is particularly expensive. In such
a case it is desirable to calculate the default value only if needed (*lazy evaluation*). The ``orElseGet()`` method
accepts a ``Supplier`` instead of a pre-calculated value. If no value is present on the ``Optional`` itself, the
``Supplier`` will be called. Since ``Supplier`` is a functional interface, a lambda expression or method reference can
be passed instead.

Instead of

.. code-block:: java

    Optional<String> optionalString = optionalString();
    String someString;
    if (optionalString.isPresent()) {
        someString = optionalString.get();
    } else {
        someString = myPlugin.defaultString();
    }

just use

.. code-block:: java

    String someString = getOptionalString().orElseGet(myPlugin::defaultString);


Fail on absent Values
~~~~~~~~~~~~~~~~~~~~~

If a value being absent should lead to an exception, it is almost always better to throw a custom exception instead of
relying on the default ``NoSuchElementException``. If you call the ``orElseThrow()`` method with a ``Supplier``, it will
return the wrapped value if it is present, or throw a ``Throwable`` obtained from the ``Supplier`` if the ``Optional``
is empty. Again, as ``Supplier`` is a functional interface, lambda expressions or method references may be used instead.

Instead of

.. code-block:: java

    Optional<String> optionalString = optionalString();
    if (!optionalString.isPresent()) {
        throw new MyException();
    }
    String someString = optionalString.get();

just use

.. code-block:: java

    String someString = getOptionalString().orElseThrow(MyException::new);


.. note::

    If the ``Throwable`` provided by the supplier is a checked exception, it will also have to be included in the
    signature of the surrounding function (for example ``public void doStuff() throws MyException``)

Conditional Code Execution
==========================

If no default value can be used, the code that relies on a value being present cannot be executed. While this might be
dealt with in a simple condition, there are other convenient methods.

Consuming Values
~~~~~~~~~~~~~~~~

If your logic to handle the present value is already encapsulated in a ``Consumer`` or a single-parameter function, the
``ifPresent()`` method will accept the consumer (or a method reference). If a value is present on the ``Optional``, it
will be passed to the consumer. If the ``Optional`` is empty, nothing will happen.

Instead of

.. code-block:: java

    Optional<String> optionalString = getOptionalString();
    if (optionalString.isPresent()) {
        myPlugin.doSomethingWithString(optionalString.get());
    }

just use

.. code-block:: java

    Optional<String> optionalString = getOptionalString();
    optionalString.ifPresent(s -> myPlugin.doSomethingWithString(s));

or

.. code-block:: java

    getOptionalString().ifPresent(myPlugin::doSomethingWithString);

Filtering
~~~~~~~~~

It is also possible to pass a ``Predicate``. Only values that this ``Predicate`` returns true for will be retained. If
no value is present or the ``Predicate`` returns ``false``, an empty ``Optional`` will be returned. Since this method
returns an optional, it allows for chaining with other methods.

Instead of

.. code-block:: java

    Optional<String> optionalString = getOptionalString();
    if (optionalString.isPresent()) {
        String someString = optionalString.get();
        if (stringTester.isPalindromic(someString)) {
            myPlugin.doSomethingWithString(someString);
        }
    }

just use

.. code-block:: java

    getOptionalString()
          .filter(stringTester::isPalindromic)
          .ifPresent(myPlugin::doSomethingWithString);

.. note::

    Neither this filtering function nor the mapping functions described below modify the instance they are called on.
    ``Optional``\ s are always immutable.

Mapping
~~~~~~~

Another chainable operation is mapping the potential value to a different one. If no value is present, nothing will
change. But if it is present, the ``map()`` method will return an ``Optional`` of the value returned by the provided
``Function`` (or an empty ``Optional`` if that return value is ``null``).

Instead of

.. code-block:: java

    Optional<String> optionalString = getOptionalString();
    if (optionalString.isPresent()) {
        String someString = optionalString.toLowerCase();
        myPlugin.doSomethingWithString(someString);
    }

just use

.. code-block:: java

    getOptionalString()
          .map(s -> s.toLowerCase)
          .ifPresent(myPlugin::doSomethingWithString);

.. tip::

    If your mapping function already returns an ``Optional``, use the ``flatMap()`` method instead. It will behave just
    like ``map()``, except that it expects the mapping function to already return an ``Optional`` and therefore will
    not wrap the result.

Combined Example
~~~~~~~~~~~~~~~~

Imagine a plugin that allows each player to have a pet following. Assume the existance of the following methods:

* ``petRegistry.getPetForPlayer()`` accepting a ``Player`` and returning an ``Optional<Pet>``. This method looks up the pet associated with a given player
* ``petHelper.canSpawn()`` accepting a ``Pet`` and returning a ``boolean``. This method performs all the necessary checks to make sure the given pet may be spawned.
* ``petHelper.spawnPet()`` accepting a ``Pet`` and returning nothing. This method will spawn a previously not spawned pet.

Now from somewhere (probably the execution of a command) we got the ``optionalPlayer`` variable holding an
``Optional<Player>``. We now want to obtain this players pet, check if the pet is spawned and if it is not spawned,
spawn it while performing the according checks if each and every ``Optional`` actually contains a value. The code only
using the basic ``isPresent()`` and ``get()`` methods gets nasty really quickly.

.. code-block:: java

    if (optionalPlayer.isPresent()) {
        Player player = optionalPlayer.get();
        Optional<Pet> optionalPet = petRegistry.getPetForPlayer(player);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            if (petHelper.canSpawn(pet)) {
                petHelper.spawnPet(pet);
            }
        }
    }

However through use of ``Optional``\ s methods for conditional code execution, all those presence checks are hidden,
reducing the boilerplate and indentation levels and thus leaving the code much more readable:

.. code-block:: java

    optionalPlayer
          .flatMap(petRegistry::getPetForPlayer)
          .filter(petHelper::canSpawn)
          .ifPresent(petHelper::spawnPet);

Creating Optionals
==================

Should you choose to provide an API following the same contract of using ``Optional`` instead of returning ``null``
values, you will have to create ``Optional``\ s in order to be able to return them. This is done by calling one of the
three static constructor methods.

``Optional.empty()`` will always return an empty ``Optional``.

``Optional.of()`` will return an optional wrapping the given value and raise a ``NullPointerException`` if the value was
``null``.

``Optional.ofNullable()`` will return an empty ``Optional`` if the supplied value is ``null``, otherwise it will return
an ``Optional`` wrapping the value.
