/*
 * Copyright © 2024 Tyler Richards (tjrgg). All rights reserved.
 *
 * This software is licensed under the MIT License. See the LICENSE file for more details.
 */

package gg.tjr.mc.logmaster.listeners;

import gg.tjr.mc.logmaster.LogMasterPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;

import static gg.tjr.mc.logmaster.util.LogUtil.*;

public class ChatListener implements Listener {

    private final LogMasterPlugin plugin;

    public ChatListener(LogMasterPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String logMode = plugin.getConfig().getString("log.chat");
        if (logMode == null || logMode.isEmpty()) return;

        Player player = event.getPlayer();

        if (!player.hasPermission("logmaster.ignore.chat")) {
            String logMessage = buildLogMessage(plugin, "chat", player, event.getMessage());

            if (logMode.equals("combined") || logMode.equals("both")) {
                File combinedLogFile = getLogFile(plugin, "chat", null);
                writeLogMessage(combinedLogFile, logMessage);
            }

            if (logMode.equals("player") || logMode.equals("both")) {
                File playerLogFile = getLogFile(plugin, "chat", player);
                writeLogMessage(playerLogFile, logMessage);
            }
        }
    }
}
