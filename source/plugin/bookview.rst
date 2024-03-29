==========
Book Views
==========

.. javadoc-import::
    net.kyori.adventure.text.Component
    org.spongepowered.api.effect.Viewer
    org.spongepowered.api.item.inventory.ItemStack
    org.spongepowered.api.text.BookView
    org.spongepowered.api.text.BookView.Builder
    java.util.Collection

A :javadoc:`BookView` is the representation of the Book GUI on the client. The ``BookView`` is not associated with an
actual :javadoc:`ItemStack` and is only for displaying :javadoc:`Component` through a book to the player. Note that a
``BookView`` is read-only, due to it being impossible to tell the client to open an unsigned book.

To create a ``BookView``, we simply need to obtain a :javadoc:`BookView.Builder`, which is provided through the
:javadoc:`BookView#builder()` method. Using the builder, we can specify the title, the author, and the pages of the
``BookView``. Then to use the view, we have to send it to a :javadoc:`Viewer`. An example of this is shown below:

.. code-block:: java

    import net.kyori.adventure.text.Component;
    import org.spongepowered.api.effect.Viewer;
    import org.spongepowered.api.text.BookView;

    BookView bookView = BookView.builder()
            .title(Component.text("Story Mode"))
            .author(Component.text("Notch"))
            .addPage(Component.text("There once was a Steve..."))
            .build();
    viewer.sendBookView(bookView);

This will display a book to the client with a single page that contains the text specified in the
:javadoc:`BookView.Builder#addPage(Component)` method. Of course, you don't have to call ``addPage(Component)`` for every page
you wish to add. The ``BookView.Builder`` class provides a :javadoc:`BookView.Builder#addPages(Collection<Component>)`
method that accepts multiple ``Component``\ s.

The ``BookView.Builder`` class also provides the :javadoc:`BookView.Builder#insertPage(int, Component)` and the corresponding
:javadoc:`BookView.Builder#insertPages(int, Collection<Component>)` methods for inserting a page or several pages at any
given index.

You may also remove pages of a ``BookView`` by providing either the ``Component`` from the page or by specifying the index
of the page that you wish to remove. You simply need to use the corresponding
:javadoc:`BookView.Builder#removePage(Component)`, :javadoc:`BookView.Builder#removePage(int)`, or
:javadoc:`BookView.Builder#removePages(Collection<Component>)` methods.
