package com.projectfoundation.core.service;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigService implements FoundationService {

    private final JavaPlugin plugin;

    public ConfigService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start() {
        plugin.saveDefaultConfig();
    }

    @Override
    public void stop() {
    }

    public void reload() {
        plugin.reloadConfig();
    }

    public String getMessage(String path, String fallback) {
        return color(plugin.getConfig().getString(path, fallback));
    }

    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}