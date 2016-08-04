=========
Tab Lists
=========

.. javadoc-import::
    org.spongepowered.api.entity.living.player.Player
    org.spongepowered.api.entity.living.player.gamemode.GameModes
    org.spongepowered.api.entity.living.player.tab.TabList
    org.spongepowered.api.entity.living.player.tab.TabListEntry
    org.spongepowered.api.entity.living.player.tab.TabListEntry.Builder
    org.spongepowered.api.text.Text
    java.util.UUID

Tab lists are used in Minecraft to display the list of players currently on a server. The SpongeAPI allows for
manipulation of the tab list on a per-player basis.

To get a player's :javadoc:`TabList`, you simply need to call the :javadoc:`Player#getTabList()` method:

.. code-block:: java
    
    import org.spongepowered.api.entity.living.player.Player;
    import org.spongepowered.api.entity.living.player.tab.TabList;
    
    TabList tablist = player.getTabList();

Now that we have obtained the ``TabList``, we can modify several components of it. For example, to set the header or
the footer of the ``TabList``, we simply need to call their appropriate methods:

.. code-block:: java
    
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.text.format.TextColors;
    
    tablist.setHeader(Text.of(TextColors.GOLD, "The tab list header"));
    tablist.setFooter(Text.of(TextColors.RED, "The tab list footer"));

We can call the :javadoc:`TabList#setHeaderAndFooter(Text, Text)` method if we want to alter both of them at once:

.. code-block:: java
    
    tablist.setHeaderAndFooter(Text.of("header"), Text.of("footer"));

.. note::
    
    If you are wanting to alter the tab list header *and* footer, it is recommended to use the ``setHeaderAndFooter()``
    method over individually calling the :javadoc:`TabList#setHeader(Text)` and :javadoc:`TabList#setFooter(Text)`
    methods, as it only sends one packet instead of two separate packets for the header and the footer.

Tab List Entries
================

Now that we have set the header and footer of the ``TabList``, we can also add our own entries to the list. An example
of this is shown below:

.. code-block:: java
    
    import org.spongepowered.api.entity.living.player.gamemode.GameModes;
    import org.spongepowered.api.entity.living.player.tab.TabListEntry;
    import org.spongepowered.api.profile.GameProfile;
    
    TabListEntry entry = TabListEntry.builder()
        .list(tablist)
        .gameMode(GameModes.SURVIVAL)
        .profile(gameProfile)
        .build();
    tablist.addEntry(entry);

Now let's break this down. We set the list associated with the :javadoc:`TabListEntry` to our specified ``TabList``
using the :javadoc:`TabListEntry.Builder#list(TabList)` method. We then set the game mode of our entry to
:javadoc:`GameModes#SURVIVAL`. The game mode of our entry is used to determine various things. On the client, it is
used to determine if a player is in creative or perhaps a spectator. If the game mode is spectator, then their name
will also appears gray and italicized. We then need to specify the ``GameProfile`` that the entry is associated with.
The ``GameProfile`` may be constructed using the ``GameProfile#of()`` method, or it can be obtained from a real
profile, such as a player. For more information, see the :doc:`game-profile-manager` article. To apply the entry to the
tab list, we simply need to call the :javadoc:`TabList#addEntry(TabListEntry)` method.

We can flesh out our basic example by specifying things such as the display name or latency of the entry:

.. code-block:: java
    
    TabListEntry entry = TabListEntry.builder()
        .list(tablist)
        .displayName(Text.of("Spongie"))
        .latency(0)
        .profile(gameProfile)
        .build();
    tablist.addEntry(entry);

Here, we set the display name that our entry will appear under to `Spongie` using the
:javadoc:`TabListEntry.Builder#displayName(Text)` method. We then set the latency for our ``TabListEntry`` to five bars.
See the :javadoc:`TabListEntry#setLatency(int)` method for more information on how to specify other types of bars for
our entry.

Modifying Current Entries
=========================

Using the ``TabList``, we can obtain entries currently on the ``TabList`` for our own modification. To obtain a
specific entry, use the :javadoc:`TabList#getEntry(UUID)` method. This method will return ``Optional.empty()`` if the
specified UUID cannot be found. An example is shown below:

.. code-block:: java
    
    import java.util.Optional;
    
    Optional<TabListEntry> optional = tablist.getEntry(uuid);
    if (optional.isPresent()) {
        TabListEntry entry = optional.get(); 
    }

With this, we can use the methods on ``TabListEntry`` to modify the game mode, latency, and the display name of the
entry:

.. code-block:: java
    
    entry.setDisplayName(Text.of("Pretender Spongie"));
    entry.setLatency(1000);
    entry.setGameMode(GameModes.SPECTATOR);

Alternatively to getting entries, we can also remove a specified entry. We must simply call the
:javadoc:`TabList#removeEntry(UUID)` method, specifying the ``UUID`` of the entry that we wish to remove. As with
``getEntry(UUID)``, this will return ``Optional.empty()`` if the specified UUID cannot be found.

If we don't have a specific entry to modify, then we can iterate through all ``TabListEntry``\ s in a ``TabList``. We
just need to call the :javadoc:`TabList#getEntries()` method to obtain a ``Collection<TabListEntry>`` that we may
iterate through.
