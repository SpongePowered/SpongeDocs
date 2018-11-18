/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.docs.generated;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.tab.TabList;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.ServiceManager;
import org.spongepowered.api.service.context.ContextCalculator;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

/**
 * Utility class which will be used for static imports of fields, to reduce the
 * total number of fake errors.
 */
public class Utils {

    public static CommandCallable myCommandSpec = magic();
    public static CommandContext args = magic();
    public static CommandManager cmdManager = magic();
    public static CommandSource src = magic();
    public static ConfigurationLoader<?> loader = magic();
    public static ConfigurationLoader<?> someConfigurationLoader = magic();
    public static ConfigurationNode node = magic();
    public static ConfigurationNode rootNode = magic();
    public static ContextCalculator<Subject> contextCalculator = magic();
    public static EconomyService economyService = magic();
    public static Event event = magic();
    public static Event theEventObject = magic();
    public static Game game = magic();
    public static GameProfile gameProfile = magic();
    public static GameProfile profile = magic();
    public static HealthData healthData = magic();
    public static ItemStack itemStack = magic();
    public static ItemStack superMegaAwesomeSword = magic();
    public static Logger logger = magic();
    public static Map<Key<?>, Object> keyMap;
    public static Path configPath = magic();
    public static PermissionService permissionService = magic();
    public static Player msgReceiver = magic();
    public static Player player = magic();
    public static Player somePlayer = magic();
    public static Player victim = magic();
    public static Player viewer = magic();
    public static PluginContainer myplugin = magic();
    public static PluginContainer plugin = magic();
    public static PluginManager pluginManager = magic();
    public static PotionEffect potion = magic();
    public static ServiceManager serviceManager = magic();
    public static String json = magic();
    public static TabList tablist = magic();
    public static Task.Builder taskBuilder = magic();
    public static TextTemplate template = magic();
    public static User user = magic();
    public static UUID newUuid = magic();
    public static UUID uuid = magic();
    public static Vector3d position = magic();
    public static World world = magic();

    public static Path getConfigPath() {
        return null;
    }

    public static Logger getLogger() {
        return null;
    }

    public static <T> T magic() {
        return null;
    }

}
