======================
Working with Optionals
======================

Now that you've learned what ``Optionals`` are, we're heading towards the usage and common use-cases.
First we'll invetigate what ``Optionals<T>`` does, how it works and what issues you may run into.

Basic Usage
===========

Optional <T>
~~~~~~~~~~~~

``Optionals`` are used when a ``value`` might be present or missing. So your plugin has to check the precense of the
``value`` and handle both cases. There are different possibilities to use ``Optionals``:

.. code-block:: java

 if (opt.isPresent()) {
  T value = opt.get();
 } else {
   // handle failure
 }

* Why you should call ``Optional#get()`` very carefully: throwing hard-to-debug errors, essentially lifting NullPointerException into a NoSuchElementException
* Using ``Optional#orElse`` and ``Optional#orElseThrow``: orElse for a default value, orElseThrow to throw a descriptive exception that you should handle later

The standard pattern of:



Advanced Usage
==============

* Using Optional#ifPresent: Only do something if it exists, no errors otherwise
* Using Optional#flatMap to chain optionals: useful especially with Data API, you can use this to chain an optional value with methods that return additional methods, i.e. opt.flatMap(value -> value.getData(SomeData.class))
* Constructing your own optionals: using Optional.empty() for empty, Optional.of for present, and Optional.ofNullable to lift a null into an Optional



.. possibly relevant link: http://www.nurkiewicz.com/2013/08/optional-in-java-8-cheat-sheet.html
