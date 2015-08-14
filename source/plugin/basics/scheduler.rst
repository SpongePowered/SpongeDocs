===================
Using the Scheduler
===================

Sponge exposes the ``SchedulerService`` to allow you to designate tasks to be executed in the future. The
``SchedulerService`` provides a ``TaskBuilder`` with which you can specify task properties such as the delay, interval,
name, (a)synchronicity, and ``Runnable`` (see :ref:`task-properties`).

Working With the TaskBuilder
============================

First, obtain an instance of the ``SchedulerService``, and retrieve the ``TaskBuilder``:

.. code-block:: java

    import org.spongepowered.api.service.scheduler.SchedulerService;
    import org.spongepowered.api.service.scheduler.TaskBuilder;

    SchedulerService scheduler = game.getScheduler();
    TaskBuilder taskBuilder = scheduler.createTaskBuilder();

The only required property is the `Runnable <http://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html>`_,
which you can specify using ``TaskBuilder#execute(Runnable runnable)``:

.. code-block:: java

    taskBuilder.execute(new Runnable() {
        public void run() {
            logger.info("Yay! Schedulers!");
        }
    });

.. _task-properties:

Task Properties
~~~~~~~~~~~~~~~

Using the ``TaskBuilder``, you can specify other, optional properties, as described below.

.. _TimeUnit: http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html

+-----------------+-------------------------+--------------------------------------------------------------------------+
| Property        | Method Used             | Description                                                              |
+=================+=========================+==========================================================================+
| delay           | delay(long delay)       | The amount of time in ticks before the task is to be run. Optionally,    |
|                 |                         | specify a TimeUnit_ as the second argument (applicable only for          |
|                 |                         | asynchronous tasks). If a ``TimeUnit``  is not specified  for an         |
|                 |                         | asynchronous task, the specified time in ticks will be converted to the  |
|                 |                         | equivalent wall clock time.                                              |
+-----------------+-------------------------+--------------------------------------------------------------------------+
| interval        | interval(long interval) | The amount of time in ticks between repetitions of the task. If an       |
|                 |                         | interval is not specified, the task will not be repeated. Optionally,    |
|                 |                         | specify a TimeUnit_ as the second argument (applicable only for          |
|                 |                         | asynchronous tasks). If a ``TimeUnit`` is not specified for an           |
|                 |                         | asynchronous task, the specified time in ticks will be converted to the  |
|                 |                         | equivalent wall clock time.                                              |
+-----------------+-------------------------+--------------------------------------------------------------------------+
| synchronization | async()                 | A synchronous task is run in the game's main loop in series with the     |
|                 |                         | tick cycle. If ``TaskBuilder#async`` is used, the task will be run       |
|                 |                         | asynchronously. Therefore, it will run in its own thread, independently  |
|                 |                         | of the tick cycle, and will not be thread safe with game data.           |
+-----------------+-------------------------+--------------------------------------------------------------------------+
| name            | name(String name)       | The name of the task. By default, the name of the task will be           |
|                 |                         | PLUGIN_ID "-" ( "A-" | "S-" ) SERIAL_ID. For example, a default task name|
|                 |                         | could look like "FooPlugin-A-12". No two active tasks will have the same |
|                 |                         | serial ID for the same synchronization type. If a task name is specified,|
|                 |                         | it should be descriptive and aid users in debugging your plugin.         |
+-----------------+-------------------------+--------------------------------------------------------------------------+

Last, submit the task to the scheduler using ``TaskBuilder#submit(Object plugin)``.

And that's it! To summarize, a fully functional scheduled task that would run asynchronously every 5 minutes after an
initial delay of 100 milliseconds could be built and submitted using the following code:

.. code-block:: java

	import org.spongepowered.api.service.scheduler.SchedulerService;
	import org.spongepowered.api.service.scheduler.Task;
	import org.spongepowered.api.service.scheduler.TaskBuilder;

	SchedulerService scheduler = game.getScheduler();
	TaskBuilder taskBuilder = scheduler.createTaskBuilder();

	Task task = taskBuilder.execute(new Runnable() {
		public void run() {
			logger.info("Yay! Schedulers!");
		}
	}).async().delay(100, TimeUnit.MILLISECONDS).interval(5, TimeUnit.MINUTES)
        .name("ExamplePlugin - Fetch Stats from Database").submit(plugin);

To cancel a task, simply call the ``Task#cancel`` method:

.. code-block:: java

	task.cancel();

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

In addition, there are a few other operations that are safe to do asynchronously:

* Independent network requests
* Filesystem I/O (excluding files used by Sponge)
