/*
 * Copyright © 2024 Tyler Richards (tjrgg). All rights reserved.
 *
 * This software is licensed under the MIT License. See the LICENSE file for more details.
 */

package gg.tjr.mc.logmaster.commands;

import gg.tjr.mc.logmaster.LogMasterPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LogMasterCommand implements CommandExecutor {

    private final LogMasterPlugin plugin;

    public LogMasterCommand(LogMasterPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("logmaster.reload")) {
            sender.sendMessage("You do not have permission to do that.");
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        String subcommand = args[0].toLowerCase();
        switch (subcommand) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage("Configuration reloaded.");
                return true;

            default:
                return false;
        }
    }
}
