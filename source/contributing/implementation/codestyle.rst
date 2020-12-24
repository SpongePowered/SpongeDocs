==========
Code Style
==========

We follow `Google's Java Style Guidelines <https://google.github.io/styleguide/javaguide.html>`_ with a
few additions and modifications, which are described herein.

.. tip::
    You can use our code styles for Eclipse or IntelliJ IDEA to let your IDE format the code correctly for you. Visit
    `Sponge Code Styles <https://github.com/SpongePowered/SpongeAPI/tree/api-8/extra>`_.

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

  * File headers must contain the license headers for the project. Use the ``updateLicenses`` Gradle task to add them
    automatically

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

* End of file

  * Each file should end with an empty line

Code Conventions
================

* Use :doc:`/plugin/optional/index` instead of returning
  ``null`` in the API
* Method parameters accepting ``null`` must be annotated with ``@Nullable`` (from javax.*), all methods and parameters
  are ``@Nonnull`` by default.
* API: Use ``java.util.Objects.requireNonNull`` for null checks and ``if``\s for argument checking.
* Impl: Use `Google Preconditions <https://github.com/google/guava/wiki/PreconditionsExplained>`_ for null- and
  argument checking.

The Gist
========

While we urge that you read Google's Java conventions particularly, the two are fairly long documents. To get you
started quickly, here is an example of properly formatted code:

.. code-block:: java

    /*
    * This file is part of Sponge, licensed under the MIT License (MIT).
    *
    * Copyright (c) SpongePowered <https://www.spongepowered.org>
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
    package org.spongepowered.example;

    import static com.google.common.base.Preconditions.checkNotNull;

    import com.google.inject.Inject;
    import org.slf4j.Logger;

    import java.util.Optional;
    import java.util.Random;
    import java.util.concurrent.ThreadLocalRandom;

    import javax.annotation.Nullable;

    /**
    * An example class which generates a new ID based on a specified base string
    * and a randomly generated integer.
    *
    * <p>There is a chance the integer purposely fails to generate, in which case
    * you can choose to provide a backup integer.</p>
    */
    public class Example {

        private static final long SEED = 4815162342L;

        @Inject
        private Logger logger;

        private final String base;
        private final Random random;

        public Example(String base) {
            checkNotNull(base, "The specified base string cannot be null!");
            this.base = base;
            this.random = ThreadLocalRandom.current();
            this.random.setSeed(SEED);
        }

        /**
        * Generates and returns an ID using the base string specified on creation
        * or the alternative string if specified as well as a randomly generated
        * integer, which purposely fails to generate around 50% of the time.
        *
        * <p>A {@link ThreadLocalRandom} is used to check if the integer should
        * be generated and generates the integer itself if so.</p>
        *
        * @param alternate An alternate base string which will be used if not null
        * @return The generated ID, if available
        */
        public Optional<String> generateId(@Nullable String alternate) {
            if (this.random.nextBoolean()) {
                return Optional.of(alternate == null ? this.base : alternate + " - " + this.random.nextInt());
            }

            return Optional.empty();
        }

        /**
        * Generates and returns an ID using the base string specified on creation,
        * using a randomly generated integer if it was generated successfully, or
        * using the backup integer you specify.
        * 
        * <p>A {@link ThreadLocalRandom} is used to check if the integer should
        * be generated and generates the integer itself if so. If it was not
        * generated, that is when your backup integer will be used.</p>
        *
        * @param backup A backup integer to use to create the ID with
        * @return The generated ID using the generated integer or the ID created
        *     using the backup integer specified
        */
        public String generateId(int backup) {
            return generateId(null).orElse(this.base + " - " + backup);
        }

    }

