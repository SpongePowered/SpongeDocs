=========
Test file
=========

.. javadoc-import::
    org.spongepowered.api.entity.living.player.User
    org.spongepowered.api.profile.GameProfile
    org.spongepowered.api.service.ban.BanService
    org.spongepowered.api.text.Text
    org.spongepowered.api.util.ban.Ban
    org.spongepowered.api.util.ban.Ban.Builder

Default with spaces
===================

Some text

.. code-block:: java
    
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.service.ban.BanService;
    
    BanService service = Sponge.getServiceManager().provide(BanService.class).get();
    test(0);

Some text

Default without spaces
======================

Some text

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.service.ban.BanService;

    BanService service = Sponge.getServiceManager().provide(BanService.class).get();
    test(1);

Some text

Default no empty line
=====================

Some text

.. code-block:: java
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.service.ban.BanService;
    BanService service = Sponge.getServiceManager().provide(BanService.class).get();
    test(2);

Some text

Extra indent
============

Some text

.. code-block:: java
    if (service.isBanned(user.getProfile())) {
        service.getBanFor(player.getProfile());
    }
    test(3);

Some text

Repeated
========

.. code-block:: java
    
    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.service.ban.BanService;
    BanService service = Sponge.getServiceManager().provide(BanService.class).get();
    test(4);

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
    test(5);

Indented list
=============

* List 1

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
        test(6);

* List 2

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
        test(7);
        
Indented indented list
======================

* List 1

  * List 1

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
        test(8);

  * List 2

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
        test(9);

Indented indented indented list
===============================

* List 1

    * List 1
    
      * List 1
    
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
            test(10);
    
      * List 2
    
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
            test(11);

EOF