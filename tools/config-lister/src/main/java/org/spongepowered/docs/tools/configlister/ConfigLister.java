/**
 * This file is part of ConfigLister, licensed under the MIT License (MIT).
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.spongepowered.docs.tools.configlister;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Plugin(id = "configlister", name = "Config Lister", version = "1.0", description = "Config Lister!")
public class ConfigLister {

    private final Path configLocation = Paths.get("config/sponge/global.conf").toAbsolutePath();
    private final Path configDictLoc = Paths.get("config-dictionary.txt").toAbsolutePath();
    private List<String> toWrite = new ArrayList<>();

    @Listener
    public void onStarted(GameStartedServerEvent event) {
        try {
            CommentedConfigurationNode rootNode = HoconConfigurationLoader.builder().setPath(this.configLocation)
                    .build().load();
            this.loopEntries(rootNode.getChildrenMap().get("sponge"));

            Files.write(this.configDictLoc, this.toWrite, Charset.forName("UTF-8"), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loopEntries(CommentedConfigurationNode node) {
        if (node.getParent().getKey() != null) {
            // Ip-sets is empty, config-enabled is for older sponge versions
            if (node.getParent().getKey().equals("sponge") && !node.getKey().equals("config-enabled") && !node.getKey().equals("ip-sets")) {
                this.toWrite.add(System.lineSeparator() + node.getKey() + System.lineSeparator()
                        + StringUtils.repeat('=', node.getKey().toString().length()) + System.lineSeparator());
            }
        }
        if (node.hasMapChildren()) {
            List<CommentedConfigurationNode> listNode = new ArrayList<>(node.getChildrenMap().values());

            listNode.sort((o1, o2) -> o1.getKey().toString().compareTo(o2.getKey().toString()));

            for (CommentedConfigurationNode childNode : listNode) {
                if (childNode.getComment().isPresent()) {
                    this.toWrite.add(childNode.getKey() + this.fixComments(childNode.getComment().get()));
                }
                this.loopEntries(childNode);
            }
        }
    }

    private String fixComments(String comment) {
        String[] splitComment = comment.split("\\r?\\n");
        if (splitComment.length == 1) {
            return System.lineSeparator() + "   " + comment;
        }
        String fixedComment = "";
        for (String comment2 : splitComment) {
            // This if statement fixes a bug where some comment lines would
            // only be whitespace with no actual content
            if (!comment2.trim().isEmpty()) {
                fixedComment += System.lineSeparator() + "   " + comment2;
            }
        }
        return fixedComment;
    }
}
