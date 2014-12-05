==========================
Logging and Debug Messages
==========================

Java comes with a few logging frameworks. Logging is preferrable to printing to stdout (or stderr) with ``System.out.println()`` for a number of reasons:

* Logged messages have a source name attached to them so it is possible to figure out where the logged messages are coming from.
* Logged messages have a severity level which allows for simple filtering (i.e. disable all non-critical notices).
* The available logger frameworks allow you to enable or disable messages from certain sources.

Getting a Logger
================

Emitting Messages
=================