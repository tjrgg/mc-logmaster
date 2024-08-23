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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static gg.tjr.mc.logmaster.util.LogUtil.*;

public class SessionListener implements Listener {

    private final LogMasterPlugin plugin;
    private final Map<UUID, Long> sessions = new HashMap<>();

    public SessionListener(LogMasterPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String logMode = plugin.getConfig().getString("log.sessions");
        if (logMode == null || logMode.isEmpty()) return;

        Player player = event.getPlayer();

        if (!player.hasPermission("logmaster.ignore.sessions")) {
            sessions.put(player.getUniqueId(), System.currentTimeMillis());

            String logMessage = buildLogMessage(plugin, "sessions", player, "Session started.");
            writeLog(logMode, player, logMessage);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String logMode = plugin.getConfig().getString("log.sessions");
        if (logMode == null || logMode.isEmpty()) return;

        Player player = event.getPlayer();

        Long startTime = sessions.remove(player.getUniqueId());
        if (startTime == null) return;

        long endTime = System.currentTimeMillis();
        long sessionDuration = endTime - startTime;

        String logMessage = buildLogMessage(plugin, "sessions", player, "Session ended.");
        writeLog(logMode, player, logMessage);

        String durationMessage = buildLogMessage(plugin, "sessions", player, "Session lasted " + formatDuration(sessionDuration) + ".\n");
        writeLog(logMode, player, durationMessage);
    }

    private String formatDuration(long durationMillis) {
        long seconds = (durationMillis / 1000) % 60;
        long minutes = (durationMillis / (1000 * 60)) % 60;
        long hours = (durationMillis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void writeLog(String logMode, Player player, String logMessage) {
        if (logMode.equals("combined") || logMode.equals("both")) {
            File combinedLogFile = getLogFile(plugin, "sessions", null);
            writeLogMessage(combinedLogFile, logMessage);
        }

        if (logMode.equals("player") || logMode.equals("both")) {
            File playerLogFile = getLogFile(plugin, "sessions", player);
            writeLogMessage(playerLogFile, logMessage);
        }
    }
}
