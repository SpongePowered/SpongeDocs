=============
TextTemplates
=============

.. javadoc-import::
    org.spongepowered.api.text.Text
    org.spongepowered.api.text.TextElement
    org.spongepowered.api.text.TextTemplate
    org.spongepowered.api.text.TextTemplateArgumentException

:javadoc:`TextTemplate`\ s are an easy and convenient way to store messages with variable elements. For instance, you
may find yourself wanting to create a configurable message for players who have joined the server for the first time.
This is easily attainable with ``TextTemplate``\ s using the following strategy:

Let's say we want to create a join message where the text is all yellow and italicized except the player's name, which
will be bold and aqua and the server's name which will be bold and red. We can create a template of that description
using the following code:

.. code-block:: java

    import static org.spongepowered.api.text.TextTemplate.*;
    import org.spongepowered.api.text.TextTemplate;
    import org.spongepowered.api.text.format.TextColor;
    import org.spongepowered.api.text.format.TextStyle;

    TextTemplate template = of(
        TextColors.YELLOW, TextStyles.ITALIC, "Welcome to ",
        arg("server").color(TextColors.RED).style(TextStyles.BOLD), " ",
        arg("player").color(TextColors.AQUA).style(TextStyles.BOLD), "!"
    );

You can obtain the result of this text template with the :javadoc:`TextTemplate#apply()` method. The ``apply()`` method
accepts a ``Map<String, TextElement>`` of parameters where the keys are the names of the arguments and the values are
the :javadoc:`TextElement` values you wish to replace the arguments with.

.. note::

    Unless an argument is specified as "optional" via ``Arg.optional()`` when it is created, missing parameters
    supplied to the ``apply()`` method will throw a :javadoc:`TextTemplateArgumentException`. Arguments may also
    specify a default value during their creation with ``Arg.defaultValue()``

.. note::

    Although arguments can have text formatting associated with them, this can be overridden by providing a Text object
    with custom formatting to the parameter map via the ``apply()`` method.

``TextTemplate``\s, like :javadoc:`Text` objects themselves are serializable to Configurate. To save a ``TextTemplate``
to a configuration file use the following code. We are also going to add a setting here so the user can define the name
of their server.

.. tip::

    To learn more about how to use Configurate to create configuration files for your plugin please refer to
    :doc:`/plugin/configuration/index`.

.. code-block:: java

    import ninja.leaping.configurate.ConfigurationNode;
    import com.google.common.reflect.TypeToken;

    ConfigurationNode node = loader.load();
    node.getNode("serverName").setValue("My Sponge Server");
    node.getNode("mytemplate").setValue(TypeToken.of(TextTemplate.class), template);
    loader.save(node);

This will produce the following output:

.. code-block:: guess

    serverName="My Sponge Server"
    mytemplate {
        arguments {
            player {
                optional=false
            }
            server {
                optional=false
            }
        }
        content {
            color=yellow
            extra=[
                "Welcome to ",
                {
                    bold=true
                    color=red
                    text="{server}"
                },
                " ",
                {
                    bold=true
                    color=aqua
                    text="{player}"
                },
                "!"
            ]
            italic=true
            text=""
        }
        options {
            closeArg="}"
            openArg="{"
        }
    }

You can retrieve ``TextTemplate``\s from configurations using the following code:

.. code-block:: java

    TextTemplate template = node.getNode("mytemplate").getValue(TypeToken.of(TextTemplate.class));

Once you are happy with the layout of your new ``TextTemplate``\, let's go ahead and send it to the server when a player
joins the server for the first time. We can achieve that using the following code:

.. tip::

    To learn more about how to handle events, please refer to :doc:`/plugin/event/index`.

.. code-block:: java

    import com.google.common.collect.ImmutableMap;
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.data.key.Keys;
    import org.spongepowered.api.entity.living.player.Player;
    import org.spongepowered.api.event.network.ClientConnectionEvent;
    import org.spongepowered.api.text.Text;
    import java.time.Instant;
    import java.util.Optional;

    @Listener
    public void onJoin(ClientConnectionEvent.Join event) {
        Player player = event.getTargetEntity();
        Instant firstPlayed = player.firstPlayed().get();
        Instant lastPlayed = player.lastPlayed().get();
        if (firstPlayed.equals(lastPlayed)) {
            // Player has not been to this server before
            // First we will get the server name from our configuration file
            String serverName = node.getNode("serverName").getString();
            // Next we will send the template to the server,
            // using the "server" and "player" template parameters
            Text message = this.template.apply(ImmutableMap.of(
                    "server", Text.of(serverName), "player", Text.of(player.getName())
            )).build();
            event.setMessage(message);
        }
    }
