====================
Writing Raw Commands
====================

.. javadoc-import::
    net.kyori.adventure.text.Component
    org.spongepowered.api.command.Command
	org.spongepowered.api.command.Command.Raw
    org.spongepowered.api.command.Command.Parameterized
    org.spongepowered.api.command.CommandCause
    org.spongepowered.api.command.parameter.ArgumentReader
    org.spongepowered.api.command.parameter.ArgumentReader.Mutable
    org.spongepowered.api.command.registrar.tree.CommandTreeNode.Root
    org.spongepowered.api.command.registrar.tree.CommandTreeNodeTypes
    org.spongepowered.api.event.lifecycle.RegisterCommandEvent


.. note::
    
    While Sponge provides the ability for you to control the string parsing yourself, it is recommended that you use
    :doc:`the parameterized commands instead <parameterized/index>`, so that client completions are supported with
    minimal effort from you.
    
A raw command, also referred to as a *low level command*, is a command that provides developers full control over how
their command is parsed and executed. Such commands allow additional flexibility than the standard parameterized 
commands, however, they require you to do everything yourself, including argument parsing, permission checks, and 
managing the command tree (for those that want client completions). For that reason, we recommend most developers
use :javadoc:`Command.Parameterized` instead, which will manage most of these things for you, allowing you to get
on with writing your command.

.. note::
    
    Plugins that wish to provide their own command framework may wish to investigate 
    :doc:`command registrars <commandregistrars>` instead. Doing so allows you to provide your own integration
    points totally separate from the Sponge way of doing things, rather than complicating your APIs by inheriting
    the Sponge interfaces.

Creating a Raw Command
======================

Developers wishing to use raw commands should implement :javadoc:`Command.Raw`. 

.. warning::

    Do not implement the root interface ``Command`` directly as this will not be registerable.

Most methods are documented in the javadocs. The most important ones to be aware of are:

* :javadoc:`Command#canExecute(CommandCause)` which tests whether the given :javadoc:`CommandCause` can execute your 
  command - generally this is a permission check;
* :javadoc:`Command#process(CommandCause, ArgumentReader.Mutable)` which runs your command; and
* :javadoc:`Command#complete(CommandCause, ArgumentReader.Mutable)` which attempts to provide a completion for your 
  command based on the current argument string (which is separate from client side completions, which is discussed
  in the "Supporting Client Completions" section below).

Parsing Command Arguments
-------------------------

The ``process`` and ``complete`` methods provide an :javadoc:`ArgumentReader.Mutable` which contains the argument 
string provided to the command. There are methods on the reader that allow you to read the string in chunks in the
same way that Minecraft would read them, which is useful if you also provide client completions and want to remain
close to those. The various ``parse*`` methods allow you to extract various tokens from the string.

The state of the ``ArgumentReader.Mutable`` does not matter to the command processor for raw commands, you can 
choose to use it as you see fit. If you would prefer to just get the input argument string, you can do so by 
using :javadoc:`ArgumentReader#input()` method -- remember that this will only contain the arguments, and not
the command that was executed (which you can get from the ``CommandCause`` by checking the ``COMMAND`` context).

Supporting Client Completions
-----------------------------

By default, Sponge will register a raw command with one optional greedy string, which mimics older versions of 
Sponge and Minecraft. However, you can provide Sponge with a different set of client completions to enable a 
richer client experience.

To do so, you must implement the :javadoc:`Command.Raw#commandTree()` method on the interface, and build up a
tree of command arguments, starting with a :javadoc:`CommandTreeNode.Root`. The default case looks like this:

.. code-block:: java

    CommandTreeNode.root().executable().child("arguments",
                    CommandTreeNodeTypes.STRING.get().createNode().greedy().executable().customCompletions());

This command tree node chain:

* States the root is executable - meaning that you can run the command with no arguments
* Has a child argument that is called "arguments" and is a string, which:
    * is greedy - so will parse as long as there are characters;
    * is executable, so you can run the command with the single argument; and
    * has "custom completions", meaning that it will ask the server what valid completions are.

A more complicated example is adding two strings that aren't greedy - one might go about doing so like this:

.. code-block:: java

        final CommandTreeNode.Argument<?> firstStringKey = CommandTreeNodeTypes.STRING.get().createNode()
                .customCompletions().executable();
        final CommandTreeNode.Argument<?> secondStringKey =
                CommandTreeNodeTypes.STRING.get().createNode().customCompletions().executable();
        firstStringKey.child("s2", secondStringKey);
        return CommandTreeNode.root().executable().child("s1", firstStringKey);

This registers a command tree with two string arguments, both of which are "executable" (meaning that 
``/command s1`` and ``/command s1 s2`` are both valid) and have custom completions.

Other types of node exist, for example, numeric and boolean and :javadoc:`Component` types. They can all be
found in the Javadocs, under the :javadoc:`CommandTreeNodeTypes` class. Some types have additional options
that can be used for richer client side information.

.. warning::

    Remember that ``CommandTreeNodeTypes`` only affect client-side rendering. They do not affect how your command 
    processes commands. It is up to you to ensure that the client completions match up with the way you build up
    and process your commands.

Registering a Raw Command
=========================

Like all other commands, you should register your command during the :javadoc:`RegisterCommandEvent`, specifically
the one for ``Command.Raw``.

.. code-block:: java

    private final PluginContainer pluginContainer = ...

    @Listener
    public void onRawCommandRegisterEvent(final RegisterCommandEvent<Command.Raw> event) {
        final Command.Raw rawCommand = new Command.Raw() { ... } // your raw command would be here
        event.register(pluginContainer, rawCommand, "rawCommand");
    }

