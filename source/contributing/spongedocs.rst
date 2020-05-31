==================
SpongeDocs Writing
==================

The Sponge documentation, also referred to as "SpongeDocs", is the official documentation of the Sponge project. The
goal of SpongeDocs is to:

* Help users set up their own servers powered by a Sponge implementation.
* Provide developers with information on how to contribute to the Sponge project.
* Provide developers with information on how to get started with plugin development.

Reporting Problems
==================

It may always occur that a page gets outdated, an error sneaks in or you just look at a page and think "Well, there is a
better way of explaining this." If that is the case and you are for some reason not able to provide a fix yourself,
there are four ways of making us aware of the problem:

#. Create an `Issue on the SpongeDocs GitHub <https://github.com/SpongePowered/SpongeDocs/issues>`_
#. Create a Posting on the `SpongeDocs Forums Category <https://forums.spongepowered.org/c/sponge-docs>`_
#. Visit us in the `#spongedocs channel on irc.esper.net <ircs://irc.esper.net:6697/#spongedocs>`_ (you need to be registered)
#. Visit us on our `Discord Server <https://discord.gg/sponge>`_, namely the ``#docs`` channel.

Writing the Docs
================

Changes and additions to SpongeDocs should be submitted as a pull request to the `SpongeDocs repository on GitHub
<https://github.com/SpongePowered/SpongeDocs>`_. We do not require it to be perfect right away as it is common for pull
requests to be refined during the review process. Incomplete explanations are also welcome, so don't shy away if there
are some parts you do not understand. There will always be someone able to fill in the gaps.

The Docs are written in reStructuredText (reST), if you're familiar with Markdown (md) the step to reST shouldn't be too
hard. If you're having issues with it we suggest that you join our `#spongedocs on irc.esper.net <ircs://irc.esper.net:6697/#spongedocs>`_
and ask for help there, or join our `Discord Server <https://discord.gg/sponge>`_ and post on the ``#docs`` channel.
Discord and IRC are linked, so you'll get the same support irrespective of the platform you choose.
Alternatively, you can post on our `forums <https://forums.spongepowered.org/>`_ if you think it is more apropriate.


The Sponge Documentation is licensed under the `Creative Commons - Share-Alike license 
<https://creativecommons.org/licenses/by-sa/4.0/>`_. Art assets are held under the related `Creative Commons - 
Non Commercial, No Derivatives license <https://creativecommons.org/licenses/by-nc-nd/4.0/>`_. Contributors implicitly 
accept this licensing.

Style Guide
===========

To make sure we have consistent format across all SpongeDocs pages, here are the guidelines we have developed for
writing Sponge Documentation. This list may get added to (or bent out of shape) as the Docs get bigger.

1. Headings Should Be Written in Title Case (<- example) [unless #8 applies].
2. Page headings should be meaningful (the heading appears as a link).
3. Program code should be contained in `inline literals <https://docutils.sourceforge.io/docs/ref/rst/roles.html#literal>`__
   or code blocks.

  i. Try not to put too much text in code blocks, as they cannot be translated.
     Contributors are discouraged from commenting in code blocks wherever possible. Simple place-holder text may be
     necessary in some examples. Ideally, code block examples will be short, and followed by an explanation for each
     example in the body text. Of course, there may be some concepts that cannot be illustrated with a short example.
  ii. Use the following code block types for particular code blocks:

     * Groovy/Gradle-Files -> ``groovy``
     * Hocon-Config -> ``guess`` (hocon highlighting is not yet supported)
     * In-Game/Server Console Command -> ``none``
     * Java-Code -> ``java``
     * Json-Config -> ``json``
     * Linux-Terminal -> ``bash``
     * Logs -> ``none``
     * Text -> ``text``
     * Windows-Console -> ``bat``

4. Keep separate areas for Users, Plugin Devs, and Sponge Devs.
5. Avoid repetition by sharing pages where possible.
6. Link to external resources rather than reproduce them.

  i. Some exceptions are made for translation purposes.

7. Make distinction between SpongeForge, SpongeVanilla and SpongeAPI.
8. If it looks awful in your language, invent your own rules.
9. Sponge is the Project Title and should NOT be translated.

  i. Some languages may wish to use a phonetic translation as well.

10. Automated translations (e.g. Google Translate) are strongly discouraged. These often contain serious errors, and are
    very likely to be rejected.
11. Page Titles and Section Headings should be plain text, avoiding literal blocks and other formatting.
12. Code symbols should be capitalized in their original form and have no extra spaces (e.g. blockState (a field name)
    or BlockState (a class name), rather than *block state*). They should also be formatted as a literal using double
    backticks (e.g. ``blockState``) in body text.
13. Lines should have a maximum length of 120 characters.
14. Imports should be written out in code blocks the first time they are referenced in each article, but not repeated
    after the first time.


.. Note::

    As Sponge is still in a state of flux, a shortfall of development docs is to be expected. Until official release of
    Sponge, there are sure to be voids in many subjects. Nevertheless, SpongeDocs is a living document, always subject
    to edit. It's never going to be perfect, just beaten into shape as needs demand.

*Contributions, suggestions and corrections are always welcome.*
