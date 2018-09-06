=====================
Logging and Debugging
=====================

There are a few logging frameworks available for use in Java. Logging is preferable to printing to stdout or stderr with
``System.out.println()`` for a number of reasons:

* Logged messages are labeled with a source name, making it easier to figure out where the logged messages are coming from.
* Logged messages have a severity level which allows for simple filtering (e.g. disable all non-critical notices).
* The available logger frameworks allow you to enable or disable messages from certain sources.

Sponge uses ``org.slf4j.Logger``, not ``java.util.logging.Logger``.

Getting a Logger
================

The Guice module used during the initialization of plugins has a plugin-scoped logger. This allows you to annotate a
field, method, or constructor with ``@Inject`` to get the logger for your plugin, which is pre-configured with the
correct plugin ID.

.. note::
    See :doc:`plugin-class` for information on configuring your plugin ID.

**Example - Field**

.. code-block:: java

    import com.google.inject.Inject;
    import org.slf4j.Logger;

    @Inject
    private Logger logger;

**Example - Method**

.. code-block:: java

    private Logger logger;

    @Inject
    private void setLogger(Logger logger) {
        this.logger = logger;
    }

**Example - Constructor**

.. code-block:: java

    // For the purpose of this example, "Banana" is the class name

    private Logger logger;

    @Inject
    public Banana(Logger logger) {
        this.logger = logger;
    }

It is recommended to set your logger in your main plugin class, as it is instantiated with the Guice injector when the
plugin is loaded.

Creating a getter method for your logger in the same class in which it was set is also ideal, although optional. An
example getter method is illustrated below.

.. code-block:: java

    public Logger getLogger() {
        return logger;
    }

Emitting Messages
=================

Emitting a message with your logger is very simple.

.. note::

    The following example assumes that the getter method for your logger is named ``getLogger()``, as shown in the
    previous section. This may differ for you depending on what you named your getter method.

.. code-block:: java

    getLogger().info("String");
    getLogger().debug("String");
    getLogger().warn("String");
    getLogger().error("String");

The String is the message you wish to emit. For example:

.. code-block:: java

    getLogger().warn("This is a warning!");

Manipulating The Logging
========================

.. note::

    These techniques should only be used in very rare cases such as badly chosen logging defaults in libraries.
    Add an config option to disable those if you use them.

Some libraries use bad logging practices such as logging on a too high log level. In these cases, you have three
choices:

1. Ask the author of that library to adjust his logging standards. This probably the best choice as it fixes the problem
   at its source.
2. Recommend your users to :doc:`configure the logging </server/spongineer/logs>` using a ``log4j2.xml`` config file.
   Which is usually the second-best choice, but especially for beginner server owners this can be hard to achieve.
   Please include a snippet with the recommended configuration additions.
3. Configure the logging in your plugin yourself:

   .. code-block:: java

       ((org.apache.logging.log4j.core.Logger) LogManager.getLogger("FtpLoggingFilter")).setLevel(Level.WARN);

   This configures the log level of the ``FtpLoggingFilter`` logger to ``WARN``. This will hide all messages that use a
   lower log level such as ``INFO`` and ``DEBUG``.
   
   .. warning::
   
      This solution assumes that log4j2 is used as logging framework by the server, however that might not be the case
      for all/future implementations of the SpongeAPI.

If you have any questions regarding logging you can always ask us on IRC, Discord or the Forums.