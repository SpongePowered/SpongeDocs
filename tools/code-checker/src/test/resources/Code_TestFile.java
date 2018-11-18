package org.spongepowered.docs.generated.codeblocks;

import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.Ban.Builder;

import static org.spongepowered.docs.generated.Utils.*;
public class Code_TestFile {

    // 0 - Assignment
    public void inner0() throws Throwable {

        BanService service = Sponge.getServiceManager().provide(BanService.class).get();
        test(0);

    }

    // 1 - Assignment
    public void inner1() throws Throwable {

        BanService service = Sponge.getServiceManager().provide(BanService.class).get();
        test(1);

    }

    // 2 - Assignment
    public void inner2() throws Throwable {

        BanService service = Sponge.getServiceManager().provide(BanService.class).get();
        test(2);

    }

    // 3 - Keyword
    public void inner3() throws Throwable {

        if (service.isBanned(user.getProfile())) {
            service.getBanFor(player.getProfile());
        }
        test(3);

    }

    // 4 - Assignment
    public void inner4() throws Throwable {

        BanService service = Sponge.getServiceManager().provide(BanService.class).get();
        test(4);

    }

    // 5 - Keyword
    public void inner5() throws Throwable {

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

    }

    // 6 - Keyword
    public void inner6() throws Throwable {

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

    }

    // 7 - Keyword
    public void inner7() throws Throwable {

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

    }

    // 8 - Keyword
    public void inner8() throws Throwable {

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

    }

    // 9 - Keyword
    public void inner9() throws Throwable {

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

    }

    // 10 - Keyword
    public void inner10() throws Throwable {

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

    }

    // 11 - Keyword
    public void inner11() throws Throwable {

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

    }

}

