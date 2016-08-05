=========
Scheduler
=========

.. javadoc-import::
    org.spongepowered.api.scheduler.Scheduler
    org.spongepowered.api.scheduler.SpongeExecutorService
    org.spongepowered.api.scheduler.Task
    org.spongepowered.api.scheduler.Task.Builder
    java.lang.Object
    java.lang.Runnable

Sponge exposes the :javadoc:`Scheduler` to allow you to designate tasks to be executed in the future. The ``Scheduler``
provides a :javadoc:`Task.Builder` with which you can specify task properties such as the delay, interval, name,
(a)synchronicity, and ``Runnable`` (see :ref:`task-properties`).

Task Builder
============

First, obtain an instance of the ``Scheduler``, and retrieve the ``Task.Builder``:

.. code-block:: java

    import org.spongepowered.api.scheduler.Scheduler;
    import org.spongepowered.api.scheduler.Task;
    import org.spongepowered.api.Sponge;

    Scheduler scheduler = Sponge.getScheduler();
    Task.Builder taskBuilder = scheduler.createTaskBuilder();

The only required property is the `Runnable <http://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html>`_,
which you can specify using :javadoc:`Task.Builder#execute(Runnable)`:

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

Lastly, submit the task to the scheduler using :javadoc:`Task.Builder#submit(Object)`.

And that's it! To summarize, a fully functional scheduled task that would run asynchronously every 5 minutes after an
initial delay of 100 milliseconds could be built and submitted using the following code:

.. code-block:: java

    import java.util.concurrent.TimeUnit;

    Scheduler scheduler = Sponge.getScheduler();
    Task.Builder taskBuilder = scheduler.createTaskBuilder();

    Task task = taskBuilder.execute(() -> logger.info("Yay! Schedulers!"))
        .async().delay(100, TimeUnit.MILLISECONDS).interval(5, TimeUnit.MINUTES)
        .name("ExamplePlugin - Fetch Stats from Database").submit(plugin);

To cancel a task, simply call the :javadoc:`Task#cancel()` method:

.. code-block:: java

	task.cancel();

If you need to cancel the task from within the runnable itself, you can instead opt to use a ``Consumer<Task>``` in
order to access the task. The below example will schedule a task that will count down from 60 and cancel itself upon
reaching 0.

.. code-block:: java

    @Listener
    public void onGameInit(GameInitializationEvent event) {
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
            if (seconds < 1) {
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

Compatibility with other libraries
==================================

As your plugin grows in size and scope you might want to start using one of the many concurrency libraries available 
for Java and the JVM.
These libraries do tend to support Java's 
`ExecutorService <https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html>`_ as a means 
of directing on which thread the task is executed.

To allow these libraries to work with Sponge's ``Scheduler`` the following methods can be used:

* :javadoc:`Scheduler#createSyncExecutor(Object)` creates a :javadoc:`SpongeExecutorService` which executes tasks
  through Sponge's synchronous scheduler.
* :javadoc:`Scheduler#createAsyncExecutor(Object)` creates a ``SpongeExecutorService`` which executes tasks through
  Sponge's asynchronous scheduler. Tasks are subject to the restrictions mentioned in `Asynchronous Tasks`_.

One thing to keep in mind is that any tasks that interacts with Sponge outside of the interactions listed in 
`Asynchronous Tasks`_ need to be executed on the ExecutorService created with  ``Scheduler#createSyncExecutor(Object)``
to be thread-safe.

.. code-block:: java

    import org.spongepowered.api.scheduler.SpongeExecutorService;
    
    SpongeExecutorService minecraftExecutor = Sponge.getScheduler().createSyncExecutor(plugin);
    
    minecraftExecutor.submit(() -> { ... });
    
    minecraftExecutor.schedule(() -> { ... }, 10, TimeUnit.SECONDS);

Almost all libraries have some way of adapting the ``ExecutorService`` to natively schedule tasks.
As an example the following paragraphs will explain how the ``ExecutorService`` is used in a number of libraries.

CompletableFuture (Java 8)
~~~~~~~~~~~~~~~~~~~~~~~~~~

.. _CompletableFuture: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html

With Java 8 the CompletableFuture_ object was added to the standard library.
Compared to the ``Future`` object, this allows for the developer to provide a callback that is called when the future
completes rather than blocking the thread until the future eventually completes.

CompletableFuture_ is a fluent interface which usually has the following three variations for each of its functions:

* ``CompletableFuture#<function>Async(..., Executor ex)`` Executes this function through ``ex``
* ``CompletableFuture#<function>Async(...)`` Executes this function through ``ForkJoinPool.commonPool()``
* ``CompletableFuture#<function>(...)`` Executes this function on whatever thread the previous ``CompletableFuture`` was completed on.

.. code-block:: java

    import java.util.concurrent.CompletableFuture;
    
    SpongeExecutorService minecraftExecutor = Sponge.getScheduler().createSyncExecutor(plugin);

    CompletableFuture.supplyAsync(() -> {
        // ASYNC: ForkJoinPool.commonPool()
        return 42;
    }).thenAcceptAsync((awesomeValue) -> {
        // SYNC: minecraftExecutor
    }, minecraftExecutor).thenRun(() -> {
        // SYNC: minecraftExecutor
    });

RxJava
~~~~~~

`RxJava <https://github.com/ReactiveX/RxJava>`_ is an implementation of the 
`Reactive Extensions <http://reactivex.io/>`_ concept for the JVM.

Multithreading in Rx is managed through various 
`Schedulers <http://reactivex.io/documentation/scheduler.html>`_.
Using the ``Schedulers#from(Executor executor)`` function the ``Executor`` provided by Sponge can be turned into a 
``Scheduler``.

Much like ``CompletableFuture`` by default actions are executed on the same thread that completed the previous part 
of the chain.
Use ``Observable#observeOn(Scheduler scheduler)`` to move between threads.

One important thing to bear in mind is that the root ``Observable`` gets invoked on whatever thread 
``Observable#subscribe()`` was called on. If the root observable interacts with Sponge it should be forced to run 
synchronously using ``Observable#subscribeOn(Scheduler scheduler)``.

.. code-block:: java

    import rx.Observable;
    import rx.Scheduler;
    import rx.schedulers.Schedulers;

    SpongeExecutorService executor = Sponge.getScheduler().createSyncExecutor(plugin);
    Scheduler minecraftScheduler = Schedulers.from(executor);
    
    Observable.defer(() -> Observable.from(Sponge.getServer().getOnlinePlayers()))
              .subscribeOn(minecraftScheduler) // defer -> SYNC: minecraftScheduler
              .observeOn(Schedulers.io()) // -> ASYNC: Schedulers.io()
              .filter(player -> {
                  // ASYNC: Schedulers.io()
                  return "Flards".equals(player.getName());
              })
              .observeOn(minecraftScheduler) // -> SYNC: minecraftScheduler
              .subscribe(player -> {
                  // SYNC: minecraftScheduler
                  player.kick(Text.of("Computer says no"));
              });

Scala
~~~~~

Scala comes with a built-in `Future <http://www.scala-lang.org/api/current/#scala.concurrent.Future>`_ object which 
a lot of scala framework mirror in design.
Most methods of the Future accept an 
`ExecutionContext <http://www.scala-lang.org/api/current/index.html#scala.concurrent.ExecutionContext$>`_ which
determined where that part of the operation is executed.
This is different from the CompletableFuture or RxJava since they default to executing on the same thread on which
the previous operation ended.

The fact that all these operation try to implicitly find an ``ExecutionContext`` means that you can easily use 
the default ``ExecutionContext.global`` and specifically run the parts that need to be thread-safe on the Sponge 
server thread.

To avoid accidentally scheduling work on through the Sponge ``ExecutorContext`` another context should be implicitly
defined so it acts as the default choice. To maintain thread safety only the functions that actually interact with Sponge
will need to have the Sponge executor specified.

.. code-block:: scala

    import scala.concurrent.ExecutionContext

    val executor = Sponge.getScheduler().createSyncExecutor(plugin)

    import ExecutionContext.Implicits.global
    val ec = ExecutionContext.fromExecutorService(executor)
	
    val future = Future {
        // ASYNC: ExecutionContext.Implicits.global
    } 
    
    future foreach {
        case value => // SYNC: ec
    }(ec)
    
    future map {
        case value => 42 // SYNC: ec
    }(ec).foreach {
        case value => println(value) // ASYNC: ExecutionContext.Implicits.global
    }
