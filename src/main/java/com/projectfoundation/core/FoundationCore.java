package com.projectfoundation.core;

import com.projectfoundation.core.engine.FoundationEngine;
import com.projectfoundation.core.gui.MenuListener;
import com.projectfoundation.core.gui.TestMenu;
import com.projectfoundation.core.player.PlayerListener;
import com.projectfoundation.core.service.ConfigService;
import com.projectfoundation.core.service.GUIService;
import com.projectfoundation.core.service.PlayerService;
import com.projectfoundation.core.storage.PlayerDataStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class FoundationCore extends JavaPlugin {

    private FoundationEngine engine;
    private TestMenu testMenu;
    private GUIService guiService;

    @Override
    public void onEnable() {
        this.engine = new FoundationEngine();

        ConfigService configService = new ConfigService(this);
        PlayerService playerService = new PlayerService(
                new PlayerDataStorage(this)
        );
        this.guiService = new GUIService();

        engine.registerService(ConfigService.class, configService);
        engine.registerService(PlayerService.class, playerService);
        engine.registerService(GUIService.class, guiService);
        engine.start();

        getServer().getPluginManager().registerEvents(
                new PlayerListener(playerService),
                this
        );

        this.testMenu = new TestMenu();

        getServer().getPluginManager().registerEvents(
                new MenuListener(guiService),
                this
        );

        getLogger().info("=================================");
        getLogger().info(" Project Foundation has awakened.");
        getLogger().info(" FoundationCore v0.1.0-alpha");
        getLogger().info("=================================");
    }

    @Override
    public void onDisable() {
        if (engine != null) {
            engine.stop();
        }

        getLogger().info("Project Foundation is shutting down.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("foundation")) {
            return false;
        }

        ConfigService configService = engine.services().get(ConfigService.class);

        if (args.length == 0) {
            sendFoundationMessage(sender, configService);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "help":
                sendHelpMessage(sender);
                return true;

            case "version":
                sendVersionMessage(sender);
                return true;

            case "reload":
                configService.reload();
                sender.sendMessage(ChatColor.GREEN + "FoundationCore config reloaded.");
                return true;

            case "menu":
                if (sender instanceof org.bukkit.entity.Player player) {
                    testMenu.open(player, guiService);
                } else {
                    sender.sendMessage(ChatColor.RED + "Only players can use this command.");
                }
                return true;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown command. Use /foundation help.");
                return true;
        }
    }

    private void sendFoundationMessage(CommandSender sender, ConfigService configService) {
        sender.sendMessage(configService.getMessage(
                "messages.awakened",
                "Project Foundation has awakened."
        ));

        sender.sendMessage(configService.getMessage(
                "messages.subtitle",
                "Welcome to the beginning of something great."
        ));
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "FoundationCore Commands:");
        sender.sendMessage(ChatColor.GRAY + "/foundation" + ChatColor.WHITE + " - Shows the foundation message.");
        sender.sendMessage(ChatColor.GRAY + "/foundation help" + ChatColor.WHITE + " - Shows this help menu.");
        sender.sendMessage(ChatColor.GRAY + "/foundation version" + ChatColor.WHITE + " - Shows plugin version.");
        sender.sendMessage(ChatColor.GRAY + "/foundation reload" + ChatColor.WHITE + " - Reloads config.yml.");
        sender.sendMessage(ChatColor.GRAY + "/foundation menu" + ChatColor.WHITE + " - Opens the test menu.");
    }

    private void sendVersionMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "FoundationCore");
        sender.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.WHITE + getDescription().getVersion());
        sender.sendMessage(ChatColor.GRAY + "Stage: " + ChatColor.WHITE + "SkyBlock Alpha Engine");
    }
}