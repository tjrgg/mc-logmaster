/*
 * Copyright © 2024 Tyler Richards (tjrgg). All rights reserved.
 *
 * This software is licensed under the MIT License. See the LICENSE file for more details.
 */

package gg.tjr.mc.logmaster;

import gg.tjr.mc.logmaster.commands.LogMasterCommand;
import gg.tjr.mc.logmaster.listeners.ChatListener;
import gg.tjr.mc.logmaster.listeners.CommandListener;
import gg.tjr.mc.logmaster.listeners.SessionListener;
import org.bukkit.plugin.java.JavaPlugin;

public class LogMasterPlugin extends JavaPlugin {

    private static LogMasterPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        registerCommands();
        registerListeners();

        getLogger().info("Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        instance = null;

        getLogger().info("Plugin has been disabled.");
    }

    public static LogMasterPlugin getInstance() {
        return instance;
    }

    private void registerCommands() {
        getCommand("logmaster").setExecutor(new LogMasterCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new SessionListener(this), this);
    }
}
