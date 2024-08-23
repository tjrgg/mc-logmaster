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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.File;

import static gg.tjr.mc.logmaster.util.LogUtil.*;

public class CommandListener implements Listener {

    private final LogMasterPlugin plugin;

    public CommandListener(LogMasterPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String logMode = plugin.getConfig().getString("log.commands");
        if (logMode == null || logMode.isEmpty()) return;

        Player player = event.getPlayer();

        if (!player.hasPermission("logmaster.ignore.commands")) {
            String logMessage = buildLogMessage(plugin, "commands", player, event.getMessage());

            if (logMode.equals("combined") || logMode.equals("both")) {
                File combinedLogFile = getLogFile(plugin, "commands", null);
                writeLogMessage(combinedLogFile, logMessage);
            }

            if (logMode.equals("player") || logMode.equals("both")) {
                File playerLogFile = getLogFile(plugin, "commands", player);
                writeLogMessage(playerLogFile, logMessage);
            }
        }
    }
}
