Using Sponge with Server Proxies
================================

.. note::

  While SpongeForge and SpongeVanilla offer the ability to enable "IP forwarding" for connecting to server proxies,
  Sponge does not officially support or recommend any specific proxy software. Any issues with the proxies themselves
  should be directed to the relevant support community.

Server proxies allow server owners to link Minecraft servers together so that players can jump between
servers without having to disconnect and re-connect. One of the most well known of these is BungeeCord, written
by md_5 of SpigotMC, though forks exist that improve Forge and Sponge compatibility, such as Waterfall and Hexacord.
There are also other alternatives, such as Velocity. Server proxies are typically used by server networks that offer
many game modes. Sponge can work with most server proxies, with player information forwarding (or IP forwarding) 
support for servers that support adding Forge markers into a player's game profile.

Choosing Your Proxy Server Software
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

SpongeVanilla works with most proxy server software. However, SpongeForge servers that require modded clients to join
**will not work** with the standard BungeeCord software. Should you wish to link modded servers together via a proxy,
you should use a fork of BungeeCord (such as
`Waterfall <https://github.com/WaterfallMC/Waterfall/blob/master/README.md#waterfall->`_) or a different proxy solution
(such as `Velocity <https://velocitypowered.com>`_) that both contain code to support Forge environments.

Setting Up Your Proxy Server Network
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. warning::

  In order to connect servers to your server proxy, you must run the servers in offline mode. In offline mode, without
  the proper precautions, anyone can log into the server using any name they wish, including those who have admin
  permissions. Make sure you protect your servers using firewalls. If you are using Linux, there is an IPTables guide
  at `SpigotMC Firewall guide <https://www.spigotmc.org/wiki/firewall-guide/>`_, alternatively, some distributions come
  with `UncomplicatedFirewall "ufw" <https://wiki.ubuntu.com/UncomplicatedFirewall>`_.

  Be sure that if you use SSH to make sure port 22 is ALLOWED, otherwise you run a very real risk of locking yourself
  out of your server!
 
  If you are not comfortable with tinkering with Linux, or you are unsure as to how to prevent unauthorized access to
  your servers, consider consulting with someone who has more experience to ensure the security of your server.

To accept connections from a proxy, ensure that ``online-mode`` is set to ``false`` in your ``server.properties`` file
on your Sponge server - this is because the proxy will do the authentication for you. Follow the server proxy's
documentation to add your server to the proxy.

While this is all you need to do to enable the proxy to connect, we **strongly** recommend that you enable either
Player Information Forwarding or IP Forwarding to enable Sponge to treat the server as if it's in online mode.

Player Information Forwarding (also known as IP Forwarding)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Server proxy sofware typically comes with a mode known as either "Player Information Forwarding" or "IP Forwarding",
which allows the proxy server software to pass the player's UUID and IP address to any connected server, enabling
servers to act as if they are in online mode. We strongly recommend that these options are enabled, and they must be
enabled on both your proxy and in Sponge.

To enable player information forwarding on the proxy, consult your proxy software documentation for how to enable this
option. It may be named "IP forwarding" on BungeeCord and its forks. If you are using SpongeForge, you may also need to
enable forge support on the proxy. If you are using Velocity, ensure that you are using the ``LEGACY`` player
information forwarding option.

To enable player information forwarding on Sponge, you must set the following two options in Sponge's config
(``config/sponge/global.conf``) to ``true``:

* ``modules.bungeecord``
* ``bungeecord.ip-forwarding``

While these options are named after BungeeCord, they will work for any proxy that uses the same protocol.

Once set, you **must** restart your server. You must ensure these options are enabled for **all** Sponge servers on 
your network. Equivalent options must also be set on other servers that will be accessed by the proxy.
