========================================
Implementing a Custom PlaceholderService
========================================

.. javadoc-import::
    org.spongepowered.api.text.Text
    org.spongepowered.api.service.placeholder.PlaceholderParser
    org.spongepowered.api.service.placeholder.PlaceholderService
    org.spongepowered.api.service.placeholder.PlaceholderText
    org.spongepowered.api.service.placeholder.PlaceholderText.Builder

The :javadoc:`PlaceholderService`, as a service, is replacable by third party plugins to enhance its capabilities. When
replacing the service, the following must also be considered.

PlaceholderParsers
==================

As :javadoc:`PlaceholderParser {PlaceholderParsers}` may be implemented by plugins providing placeholders, rather then
a plugin replacing the service, all implementations that replace the service must handle parsers that are not aware of
any features that your plugin provides.

This should be simple by ensuring you implement all other interfaces correctly, conforming to the javadocs and guidance
here.

PlaceholderText and PlaceholderText.Builder
===========================================

Third party plugins are expected to implement their own :javadoc:`PlaceholderText` and :javadoc:`PlaceholderText.Builder`
and conform to the contracts specified in the javadocs. Alternate implementations may include extra context, however,
they **must** be compatible with standard :javadoc:`PlaceholderParser {PlaceholderParsers}` that do not check for 
alternate implementations.

PlaceholderService
==================

The :javadoc:`PlaceholderService` does two things:

* Provides :javadoc:`PlaceholderText.Builder {PlaceholderText.Builders}`
* Selects an appropriate :javadoc:`PlaceholderParser` when supplied with a token

For the first, :javadoc:`PlaceholderService#placeholderBuilder()` should return your implementation of 
:javadoc:`PlaceholderText.Builder`.

Token Matching
~~~~~~~~~~~~~~

The :javadoc:`PlaceholderService#getParser(String)` method attempts to select a :javadoc:`PlaceholderParser` based on 
the supplied token. If the token matches the ID of a registered parser exactly then the service **must** return that
parser. 

Otherwise, the service may use whatever sensible criteria it sees fit to match the input with a token. Any
criteria that you specify must also be shared with the various ``parse`` methods on the service.

.. tip::
  As an example, you may wish to match the token ``name`` with ``sponge:name``, or provide aliases to your own parsers,
  as long as you are consistent across all methods on your service.

The standard service only returns a parser that is an exact match to the supplied token.
