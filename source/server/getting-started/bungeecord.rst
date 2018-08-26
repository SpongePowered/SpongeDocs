Using Sponge with BungeeCord
============================

BungeeCord is a piece of server proxy software written by md_5 and the SpigotMC team that allows server owners to link
Minecraft servers together so that players can jump between servers without having to disconnect and re-connect.
BungeeCord is typically used by server networks that offer many game modes.

For more information about BungeeCord, what it is, how to set it up and how it works, have a look at the
`BungeeCord website <https://www.spigotmc.org/wiki/bungeecord/>`_. This page will focus on Sponge specific steps.

.. warning::
 In order to connect servers to BungeeCord, you must run the servers in offline mode. In offline mode, without the
 proper precautions, anyone can log into the server using any name they wish, including those who have admin
 permissions. Make sure you protect your servers using firewalls. If you are using linux, there is an IPTables guide
 at `SpigotMC Firewall guide <https://www.spigotmc.org/wiki/firewall-guide/>`_, alternatively, some distributions come
 with `UncomplicatedFirewall "ufw" <https://wiki.ubuntu.com/UncomplicatedFirewall>`_.

If you are not comfortable with tinkering with Linux, or you are unsure as to how to prevent unauthorised access to
your servers, consider consulting with someone who has more experience to ensure the security of your server.

.. note::

  Be sure that if you use SSH to make sure port 22 is ALLOWED, otherwise you run a very real risk of locking yourself
  out of your server!

IP Forwarding
~~~~~~~~~~~~~

BungeeCord has a mode called IP Forwarding, which allows BungeeCord to pass the player's UUID and IP address to any
connected server, even though the servers are being run in offline mode. 

.. warning::
 SpongeForge modded servers that require modded clients, will only work if you use a BungeeCord fork like
 `Waterfall <https://github.com/WaterfallMC/Waterfall/blob/master/README.md#waterfall->`_, or a BungeeCord plugin like
 `SpongePls <https://forums.spongepowered.org/t/spongepls/9891>`_. SpongePowered does not officially support these
 products, although they are developed by trusted members of the community.

For SpongeVanilla or Vanilla clients connecting to unmodded SpongeForge, IP Forwarding works out of the box.
`Pull <https://github.com/SpigotMC/BungeeCord/pull/1557>`_
`requests <https://github.com/SpigotMC/BungeeCord/pull/1678>`_ were made to BungeeCord for modded SpongeForge support,
but these haven't been accepted. Other solutions (mentioned above) include these patches and should be used instead.

Using BungeeCord with IP Forwarding
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If you wish to use IP Forwarding:

* In the BungeeCord ``config.yml``, set ``ip_forward`` to ``true``
* In Sponge's config (``config/sponge/global.conf``), set ``modules.bungeecord`` to ``true`` and
  ``bungeecord.ip-forwarding`` to ``true``
* If you have any other server software, consult the documentation for that server.

This must be done for **all** servers that are connected to the BungeeCord network. Then, just follow the instructions
for using BungeeCord without IP Forwarding.

Using BungeeCord without IP Forwarding
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It is recommended that you use IP Forwarding wherever possible, if you do not wish to do so, simply ensure that
``online-mode`` is set to ``false`` in your ``server.properties`` file and add the server details to Bungee's
``config.yml`` file. Bungee will then forward any connections to the server when required.

This will work with all implementations of Sponge, including with mods.