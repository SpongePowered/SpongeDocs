==========
Code Style
==========

We follow `Google's Java Style Guidelines <https://google.github.io/styleguide/javaguide.html>`_ with a
few additions and modifications, which are described herein.

.. tip::
    You can use our code styles for Eclipse or IntelliJ IDEA to let your IDE format the code correctly for you. See
    :doc:`../../preparing/index` for more information.

* Line endings

  * Use Unix line endings when committing (\\n)

    * Windows users of Git can do ``git config --global core.autocrlf true`` to let Git convert them automatically

* Column width

  * 80 for Javadocs
  * 150 for code
  * Feel free to wrap when it will help with readability

* Indentation

  * Use 4 spaces for indentations, do not use 2 spaces

* Vertical whitespace

  * Place a blank line before the first member of a class, interface, enum, etc. (i.e. after ``class Example {``) as
    well as after the last member

* File headers

  * File headers must contain the license headers for the project. Use the ``licenseFormat`` Gradle task to add them
    automatically.

* Imports

  * Imports must be grouped in the following order, where each group is separated by an empty line

    * Static imports
    * All other imports
    * ``java`` imports
    * ``javax`` imports

  * This differs from Google's style in that imports are not grouped by top-level package, they are all grouped as one.

* Exceptions

  * For exceptions that are to be ignored, name the exception variable ``ignored``

* Field accesses

  * Qualify **all** field accesses with ``this``

* Javadocs

  * Do not use ``@author``
  * Wrap additional paragraphs in ``<p>`` and ``</p>``
  * Capitalize the first letter in the descriptions within each "at clause", i.e. ``@param name Player to affect``, no
    periods

Code Conventions
================

* Use :doc:`/plugin/optional/index` instead of returning
  ``null`` in the API
* Method parameters accepting ``null`` must be annotated with ``@Nullable`` (from javax.*), all methods and parameters
  are ``@Nonnull`` by default.
* Use `Google Preconditions <https://code.google.com/p/guava-libraries/wiki/PreconditionsExplained>`_ for null- and
  argument checking.

The Gist
========

While we urge that you read Google's Java conventions particularly, the two are fairly long documents. To get you
started quickly, here is an example of properly formatted code:

.. code-block:: java

    /*
     * This file is part of Sponge, licensed under the MIT License (MIT).
     *
     * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
     * Copyright (c) contributors
     *
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     *
     * The above copyright notice and this permission notice shall be included in
     * all copies or substantial portions of the Software.
     *
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
     * THE SOFTWARE.
     */
    package com.example.java;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import java.util.Random;
    import java.util.Optional;

    public class Example {

        private static final Logger log = LoggerFactory.getLogger(Example.class);
        private static final Random random = new Random();
        private final String id = "test";

        /**
         * Returns an identifier approximately half of the time.
         *
         * <p>A static instance of {@link Random} is used to calculate the
         * outcome with a 50% chance.</p>
         *
         * @return The ID, if available
         */
        public Optional<String> resolveId() {
            log.info("ID requested");

            if (random.nextBoolean()) {
                return Optional.of(this.id);
            } else {
                return Optional.empty();
            }
        }

        /**
         * Returns an identifier approximately half of the time.
         *
         * <p>A static instance of {@link Random} is used to calculate the
         * outcome with a 50% chance. If the outcome is to not return the ID,
         * the given fallback ID is returned.</p>
         *
         * @param fallback A fallback name to return
         * @return The ID half of the time, the given fallback the other half
         */
        public String resolveId(String fallback) {
            return resolveId().orElse(fallback);
        }

    }
