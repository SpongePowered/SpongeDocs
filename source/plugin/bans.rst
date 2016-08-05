====
Bans
====

.. javadoc-import::
    org.spongepowered.api.entity.living.player.User
    org.spongepowered.api.profile.GameProfile
    org.spongepowered.api.service.ban.BanService
    org.spongepowered.api.text.Text
    org.spongepowered.api.util.ban.Ban
    org.spongepowered.api.util.ban.Ban.Builder

The :javadoc:`BanService` is a service built into the SpongeAPI that adds the functionality for you to ban or pardon
users in your plugin. The ``BanService`` provides several methods to do things such as banning users, pardoning users,
or even getting a :javadoc:`Ban` and the information on the ``Ban``.

.. tip::

    For a basic understanding of services, make sure you read :doc:`services` first.

Getting the BanService
======================

You will need to get the ``BanService`` to actually add bans to the server. Fortunately, this can be done similarly to
other services in the Sponge API:

.. code-block:: java
    
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.service.ban.BanService;
    
    BanService service = Sponge.getServiceManager().provide(BanService.class).get();

Now with the ``BanService``, we can perform additional operations. For example, if we want to check if a provided
:javadoc:`User` is already banned, we can use the :javadoc:`BanService#isBanned(GameProfile)` method. Or perhaps if we
wanted to get information on a ban from a ``User``, we can use the :javadoc:`BanService#getBanFor(GameProfile)` method.
An example of this is shown below:

.. code-block:: java
    
    import java.util.Optional;
    
    import org.spongepowered.api.entity.living.player.User;
    import org.spongepowered.api.text.Text;
    import org.spongepowered.api.util.ban.Ban;
    
    if (service.isBanned(user.getProfile())) {
        Optional<Ban.Profile> optionalBan = service.getBanFor(player.getProfile());
        if (optionalBan.isPresent()) {
            Ban.Profile profileBan = optionalBan.get();
            Optional<Text> optionalReason = profileBan.getReason();
            if (optionalReason.isPresent()) {
                Text banReason = optionalReason.get();
            }
        }
    }

Creating a Ban
==============

So now we can obtain the ``BanService`` and the information on a ``Ban``, but what if we wanted to create our own bans?
We can use a :javadoc:`Ban.Builder` to create our own ``Ban``. To get a ``Ban.Builder``, simply call the
:javadoc:`Ban#builder()` method. Using our builder, we can specify things such as the type of the ban, the reason for
the ban, or the ``User`` we wish to ban. An example of all of these things is shown below:

.. code-block:: java
    
    import org.spongepowered.api.util.ban.BanTypes;
    
    Ban ban = Ban.builder().type(BanTypes.PROFILE).profile(user.getProfile())
        .reason(Text.of("The Sponge Council has Spoken!")).build();

Alternatively, you can specify an ip ban on an online player:

.. code-block:: java
    
    Ban ban = Ban.builder().type(BanTypes.IP)
        .address(player.getConnection().getAddress().getAddress())
        .reason(Text.of("The Sponge Council has Spoken!")).build();

Note that if you wish to create a simple, indefinite ban on a ``User``, you can use the :javadoc:`Ban#of(GameProfile)`
method or the :javadoc:`Ban#of(GameProfile, Text)` method to quickly construct a ban.

Adding a Ban
~~~~~~~~~~~~

Now that we have created our ban, we can now register it to be used in Sponge. Using our ``BanService`` from before, we
can use the :javadoc:`BanService#addBan(Ban)` method to accomplish this. Note that adding a ban will remove any
previously existing ban.

Pardoning
~~~~~~~~~

Now let's say we wanted to remove a ban from a user. We can use the :javadoc:`BanService#pardon(GameProfile)` method.
This method returns a boolean, which specifies if the user had a ban in place previously.

Putting it All Together
~~~~~~~~~~~~~~~~~~~~~~~

We can create a ``Ban`` using a ``Ban.Builder`` that is obtained using the ``Ban#builder()`` method. We can specify
things such as the type, the ``User`` to be banned, or the reason for the ban. We then simply grab our ``BanService``
and use it to add our ``Ban``. Here is the full code for doing this:

.. code-block:: java
    
    BanService service = Sponge.getServiceManager().provide(BanService.class).get();
    Ban ban = Ban.builder().type(BanTypes.PROFILE).profile(user.getProfile())
        .reason(Text.of("The Sponge Council has Spoken!")).build();
    service.addBan(ban);
