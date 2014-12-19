==========================
Logging and Debug Messages
==========================

Java comes with a few logging frameworks. Logging is preferrable to printing to stdout (or stderr) with ``System.out.println()`` for a number of reasons:

* Logged messages have a source name attached to them so it is possible to figure out where the logged messages are coming from.
* Logged messages have a severity level which allows for simple filtering (i.e. disable all non-critical notices).
* The available logger frameworks allow you to enable or disable messages from certain sources.

Getting a Logger
================

To be able to use the logs, we need to implement the method.
For this purpose, we use slf4j logger. It is supported by default in Sponge.

1. **Implementing slf4j-api in our IDE:** 
    - Grab this archive from the official server: `slf4j-1.7.9.zip <http://www.slf4j.org/dist/slf4j-1.7.9.zip>`__
    - Extract "**slf4j-api-1.7.9.jar**"
    - Add the file to our project as a JAR library.

2. **Implementing method in plugin code**

.. code-block:: java

    package example;
    
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import org.spongepowered.api.event.state.PreInitializationEvent;
    import org.spongepowered.api.plugin.Plugin;
    import org.spongepowered.api.util.event.Subscribe;
    
    @Plugin(id = "example", name = "Example Plugin", version = "1.0")
    public class ExamplePlugin {

        private static Logger logger;

        public static Logger getLogger() {
            return logger;
        }

        @SuppressWarnings("unused")
        @Subscribe
        public void onPreInitialization(PreInitializationEvent event) {
            logger = LoggerFactory.getLogger("example"); //Change the "example" with your plugin ID or name
        }

        public static Logger getLoggerInstance() {
            if (logger == null) throw new IllegalStateException("Cannot get a null logger");
            return logger;
        }
    
    }


In the "private static Logger **logger**;" we used the **"logger"** as a name. We will use it for emitting logs.

Emitting Messages
=================
