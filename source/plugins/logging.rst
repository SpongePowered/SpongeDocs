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
For this purpose, we will use slf4j logger. It is supported by default in Sponge.

1. **Implementing slf4j-api in our IDE:** 
    - Grab this archive from the official server: `slf4j-1.7.9.zip <http://www.slf4j.org/dist/slf4j-1.7.9.zip>`__
    - Extract "**slf4j-api-1.7.9.jar**"
    - Add the file to your project as a JAR library.

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

        @SuppressWarnings("unused")
        @Subscribe
        public void onPreInitialization(PreInitializationEvent event) {
            logger = LoggerFactory.getLogger("example"); //Change the "example" with your plugin ID or name
        }
    
    }


In the "private static Logger **logger**;" we used the **"logger"** as a name. We will use it for emitting logs.
       
Emitting Messages
=================

In the previous section we have implemented a logger in our plugin, now its time to use it.
Correct usage shows an example "ServerStartingEvent" and the log of the plugin when it is loaded correctly.

.. code-block:: java

    @SuppressWarnings("unused")
    @Subscribe
    public void onEnable(ServerStartingEvent event) {
        logger.info("[ExamplePlugin]Plugin successfully loaded!");
    }


We can use the 5 categories of logs. In the case of writing plugins are really used 3:

- **INFO**:
    
.. code-block:: java

    logger.info("[ExamplePlugin]Information message");

It will be displayed in logs like this:

.. code-block:: python

    [00:00:00] [Server thread/INFO]: [ExamplePlugin]Information message!

- **WARN**:
    
.. code-block:: java

    logger.warn("[ExamplePlugin]Warning message");

.. code-block:: python

    [00:00:00] [Server thread/WARN]: [ExamplePlugin]Warning message!

- **ERROR**: (Note: the use of error does not cause the server crash)
    
.. code-block:: java

    logger.error("[ExamplePlugin]Error message");

.. code-block:: python

    [00:00:00] [Server thread/ERROR]: [ExamplePlugin]Error message!
