======================
The Pagination Service
======================

.. javadoc-import::
    net.kyori.adventure.audience.Audience
    org.spongepowered.api.service.pagination.PaginationList.Builder
    org.spongepowered.api.service.pagination.PaginationService

.. tip::

    For a basic understanding of services, make sure you read :doc:`../services` first.

The :javadoc:`PaginationService` acts as a way to split up content into discrete pages. The service provides a
:javadoc:`PaginationList.Builder` with which you can specify attributes such as title, contents, header, and padding.
This is a Sponge-specific feature.

Pagination List Builder
=======================

First obtain an instance of a ``PaginationList.Builder``:

.. code-block:: java

    import org.spongepowered.api.service.pagination.PaginationList;

    PaginationList.Builder builder = PaginationList.builder();

There are two different ways to specify the contents of paginated list:

* With an ``Iterable<Component>``

 .. code-block:: java

    import net.kyori.adventure.text.Component;

    import java.util.ArrayList;
    import java.util.List;

    List<Component> contents = new ArrayList<>();
    contents.add(Component.text("Item 1"));
    contents.add(Component.text("Item 2"));
    contents.add(Component.text("Item 3"));

    builder.contents(contents);

 .. note::

    If the ``Iterable`` is a ``List``, then bidirectional navigation is supported. Otherwise, only forwards navigation
    is supported.

* With an array of ``Component``\ s

 .. code-block:: java

    builder.contents(Component.text("Item 1"), Component.text("Item 2"), Component.text("Item 3"));

You can also specify various other components of a paginated list, such as a title, header, footer, and padding. The
diagram below shows which component is displayed in each part of the paginated list. In the following diagram, the
padding string is shown as the letter `p`.

::

    pppppppppppppppppppppppp Title pppppppppppppppppppppppp
    Header
    Item 1
    Item 2
    Item 3
    ...
    ppppppppppppppppppppppp < 2/3 > ppppppppppppppppppppppp
    Footer

To achieve the preceding output, we might use the following builder pattern:

.. code-block:: java

    builder.title(Component.text("Title"))
        .contents(Component.text("Item 1"), Component.text("Item 2"), Component.text("Item 3"))
        .header(Component.text("Header"))
        .footer(Component.text("Footer"))
        .padding(Component.text("p"));

.. note::

    With the exception of contents, all components of the paginated list are optional. However, a title is strongly
    recommended.

Finally, to send the paginated list to a player or another :javadoc:`Audience`-like object, use
:javadoc:`PaginationList.Builder#sendTo(Audience)`.

And that's it! To recap, a fully functional paginated list could be generated and sent to a previously defined
``msgReceiver`` using the following code:

.. code-block:: java

    PaginationList.builder()
        .title(Component.text("Title"))
        .contents(Component.text("Item 1"), Component.text("Item 2"), Component.text("Item 3"))
        .header(Component.text("Header"))
        .footer(Component.text("Footer"))
        .padding(Component.text("p"))
        .sendTo(msgReceiver);
