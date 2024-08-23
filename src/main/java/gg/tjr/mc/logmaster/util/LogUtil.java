/*
 * Copyright © 2024 Tyler Richards (tjrgg). All rights reserved.
 *
 * This software is licensed under the MIT License. See the LICENSE file for more details.
 */

package gg.tjr.mc.logmaster.util;

import gg.tjr.mc.logmaster.LogMasterPlugin;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {

    public static String buildLogMessage(LogMasterPlugin plugin, String logType, Player player, String message) {
        String format = plugin.getConfig().getString("format." + logType, "{timestamp} - {player} » {message}");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        format = format.replace("{command}", message);
        format = format.replace("{message}", message);

        format = format.replace("{player}", player.getName());
        format = format.replace("{playerDisplayName}", player.getDisplayName());
        format = format.replace("{playerId}", player.getUniqueId().toString());
        format = format.replace("{playerUsername}", player.getName());
        format = format.replace("{timestamp}", timestamp);

        return format;
    }

    public static File getLogFile(LogMasterPlugin plugin, String logType, Player player) {
        String fileName = plugin.getConfig().getString("files." + logType);

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        fileName = fileName.replace("{date}", date);

        if (player != null) {
            fileName = fileName.replace("{player}", player.getName());
            fileName = fileName.replace("{playerDisplayName}", player.getDisplayName());
            fileName = fileName.replace("{playerId}", player.getUniqueId().toString());
            fileName = fileName.replace("{playerUsername}", player.getName());

            File logDir = new File(plugin.getDataFolder(), "logs/" + player.getUniqueId());
            if (!logDir.exists()) logDir.mkdirs();
            return new File(logDir, fileName);
        } else {
            File logDir = new File(plugin.getDataFolder(), "logs");
            if (!logDir.exists()) logDir.mkdirs();
            return new File(logDir, fileName);
        }
    }

    public static void writeLogMessage(File logFile, String logMessage) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
