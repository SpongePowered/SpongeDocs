===================
Using the Scheduler
===================

Sponge exposes the ``Scheduler`` to allow you to designate tasks to be executed in the future. The
``Scheduler`` provides a ``Task.Builder`` with which you can specify task properties such as the delay, interval,
name, (a)synchronicity, and ``Runnable`` (see :ref:`task-properties`).

Working With the Task.Builder
=============================

First, obtain an instance of the ``Scheduler``, and retrieve the ``Task.Builder``:

.. code-block:: java

    import org.spongepowered.api.scheduler.Scheduler;
    import org.spongepowered.api.scheduler.Task;
    import org.spongepowered.api.Sponge;

    Scheduler scheduler = Sponge.getScheduler();
    Task.Builder taskBuilder = scheduler.createTaskBuilder();

The only required property is the `Runnable <http://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html>`_,
which you can specify using ``Task.Builder#execute(Runnable runnable)``:

.. code-block:: java

    taskBuilder.execute(new Runnable() {
        public void run() {
            logger.info("Yay! Schedulers!");
        }
    });

or using Java 8 syntax with ``Task.Builder#execute(Runnable runnable)``

.. code-block:: java

    taskBuilder.execute(
        () -> {
            logger.info("Yay! Schedulers!");
        }
    );

or using Java 8 syntax with ``Task.Builder#execute(Consumer<Task> task)``

.. code-block:: java

    taskBuilder.execute(
        task -> {
            logger.info("Yay! Schedulers! :" + task.getName());
        }
    );

.. _task-properties:

Task Properties
~~~~~~~~~~~~~~~

Using the ``Task.Builder``, you can specify other, optional properties, as described below.

.. _TimeUnit: http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TimeUnit.html

+-----------------+-------------------------+--------------------------------------------------------------------------+
| Property        | Method Used             | Description                                                              |
+=================+=========================+==========================================================================+
| delay           |  delayTicks(long delay) | The optional amount of time to pass before the task is to be run.        |
|                 |                         |                                                                          |
|                 |  delay(long delay,      | The time is specified as a number of ticks with the ``delayTicks()``     |
|                 |        TimeUnit unit)   | method, or it may be provided as a number of a more convenient time      |
|                 |                         | unit by specifying a TimeUnit_ with the delay() method.                  |
|                 |                         |                                                                          |
|                 |                         | *Either method, but not both, can specified per task.*                   |
+-----------------+-------------------------+--------------------------------------------------------------------------+
| interval        |  intervalTicks(         | The amount of time between repetitions of the task.  If an interval is   |
|                 |          long interval) | not specified, the task will not be repeated.                            |
|                 |                         |                                                                          |
|                 |                         | The time is specified as a number of ticks with the ``intervalTicks()``  |
|                 |                         | method, or it may be provided as a number of a more convenient time      |
|                 |  interval(long interval,| unit by specifying a TimeUnit_ with the interval() method.               |
|                 |          TimeUnit unit) |                                                                          |
|                 |                         | *Either method, but not both, can specified per task.*                   |
+-----------------+-------------------------+--------------------------------------------------------------------------+
| synchronization | async()                 | A synchronous task is run in the game's main loop in series with the     |
|                 |                         | tick cycle. If ``Task.Builder#async`` is used, the task will be run      |
|                 |                         | asynchronously. Therefore, it will run in its own thread, independently  |
|                 |                         | of the tick cycle, and may not safely use game state.                    |
|                 |                         | (See `Asynchronous Tasks`_.)                                             |
+-----------------+-------------------------+--------------------------------------------------------------------------+
| name            | name(String name)       | The name of the task. By default, the name of the task will be           |
|                 |                         | PLUGIN_ID "-" ( "A-" | "S-" ) SERIAL_ID. For example, a default task name|
|                 |                         | could look like "FooPlugin-A-12". No two active tasks will have the same |
|                 |                         | serial ID for the same synchronization type. If a task name is specified,|
|                 |                         | it should be descriptive and aid users in debugging your plugin.         |
+-----------------+-------------------------+--------------------------------------------------------------------------+

Lastly, submit the task to the scheduler using ``Task.Builder#submit(Object plugin)``.

And that's it! To summarize, a fully functional scheduled task that would run asynchronously every 5 minutes after an
initial delay of 100 milliseconds could be built and submitted using the following code:

.. code-block:: java

    import java.util.concurrent.TimeUnit;

    Scheduler scheduler = Sponge.getScheduler();
    Task.Builder taskBuilder = scheduler.createTaskBuilder();

    Task task = taskBuilder.execute(() -> logger.info("Yay! Schedulers!"))
        .async().delay(100, TimeUnit.MILLISECONDS).interval(5, TimeUnit.MINUTES)
        .name("ExamplePlugin - Fetch Stats from Database").submit(plugin);

To cancel a task, simply call the ``Task#cancel`` method:

.. code-block:: java

	task.cancel();

If you need to cancel the task from within the runnable itself, you can instead opt to use a `Consumer<Task>` in order to
access the task. The below example will schedule a task that will count down from 60 and cancel itself upon reaching 0.

.. code-block:: java

    @Listener
    public void onGameInit(GameInitializationEvent event){
        Scheduler scheduler = Sponge.getScheduler();
        Task.Builder taskBuilder = scheduler.createTaskBuilder();
        Task task = taskBuilder.execute(new CancellingTimerTask())
            .interval(1, TimeUnit.SECONDS)
            .name("Self-Cancelling Timer Task").submit(plugin);
    }

    private class CancellingTimerTask implements Consumer<Task> {
        private int seconds = 60;
        @Override
        public void accept(Task task) {
            seconds--;
            Sponge.getServer()
                .getBroadcastChannel()
                .send(Text.of("Remaining Time: "+seconds+"s"));
            if(seconds < 1) {
                task.cancel();
            }
        }
    }

Asynchronous Tasks
~~~~~~~~~~~~~~~~~~

Asynchronous tasks should be used primarily for code that may take a significant period of time to execute, namely
requests to another server or database. If done on the main thread, a request to another server could greatly impact
the performance of the game, since the next tick cannot be fired until the request is completed.

Since Minecraft is largely single-threaded, there is little you can do in an asynchronous thread. If you must run a
thread asynchronously, you should execute all of the code that does not use the SpongeAPI/affect Minecraft, then register
another `synchronous` task to handle the code that needs the API. There are a few parts of Minecraft that you can work
with `asynchronously`, including:

* Chat
* Sponge's built-in Permissions handling
* Sponge's scheduler

In addition, there are a few other operations that are safe to do asynchronously:

* Independent network requests
* Filesystem I/O (excluding files used by Sponge)
