package com.projectfoundation.core;

import engine.FoundationEngine;
import gui.MenuListener;
import gui.TestMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import player.PlayerListener;
import profile.PlayerProfile;
import profile.PlayerProfileStorage;
import service.ConfigService;
import service.EconomyService;
import service.GUIService;
import service.PlayerProfileService;
import service.PlayerService;
import storage.PlayerDataStorage;

public final class FoundationCore extends JavaPlugin {

    private static final String PREFIX = ChatColor.GOLD + "[Foundation] " + ChatColor.RESET;
    private static final String PROJECT_NAME = "Project Foundation";
    private static final String STAGE = "SkyBlock Alpha Engine";

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

        PlayerProfileService profileService = new PlayerProfileService(
                new PlayerProfileStorage(this)
        );

        EconomyService economyService = new EconomyService(profileService);

        this.guiService = new GUIService();

        engine.registerService(ConfigService.class, configService);
        engine.registerService(PlayerService.class, playerService);
        engine.registerService(PlayerProfileService.class, profileService);
        engine.registerService(EconomyService.class, economyService);
        engine.registerService(GUIService.class, guiService);
        engine.start();

        getServer().getPluginManager().registerEvents(
                new PlayerListener(playerService, profileService),
                this
        );

        this.testMenu = new TestMenu();

        getServer().getPluginManager().registerEvents(
                new MenuListener(guiService),
                this
        );

        getLogger().info("=================================");
        getLogger().info(" " + PROJECT_NAME + " has awakened.");
        getLogger().info(" FoundationCore v" + getDescription().getVersion());
        getLogger().info("=================================");
    }

    @Override
    public void onDisable() {
        if (engine != null) {
            engine.stop();
        }

        getLogger().info(PROJECT_NAME + " is shutting down.");
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
                sender.sendMessage(PREFIX + ChatColor.GREEN + "Config reloaded.");
                return true;

            case "menu":
                handleMenuCommand(sender);
                return true;

            case "profile":
                handleProfileCommand(sender);
                return true;

            case "coins":
                handleCoinsCommand(sender, args);
                return true;

            default:
                sender.sendMessage(PREFIX + ChatColor.RED + "Unknown command. Use /foundation help.");
                return true;
        }
    }

    private void handleMenuCommand(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only players can use this command.");
            return;
        }

        testMenu.open(player, guiService);
    }

    private void handleProfileCommand(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only players can use this command.");
            return;
        }

        sendProfileMessage(sender, player);
    }

    private void handleCoinsCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only players can use this command.");
            return;
        }

        EconomyService economyService = engine.services().get(EconomyService.class);

        if (args.length == 1) {
            sender.sendMessage(PREFIX + ChatColor.GOLD + "Coins: " + ChatColor.WHITE + economyService.getCoins(player));
            return;
        }

        if (args.length == 3 && args[1].equalsIgnoreCase("add")) {
            try {
                int amount = Integer.parseInt(args[2]);

                if (amount <= 0) {
                    sender.sendMessage(PREFIX + ChatColor.RED + "Amount must be greater than 0.");
                    return;
                }

                economyService.addCoins(player, amount);
                sender.sendMessage(PREFIX + ChatColor.GREEN + "Added " + amount + " coins.");
            } catch (NumberFormatException exception) {
                sender.sendMessage(PREFIX + ChatColor.RED + "Please enter a valid number.");
            }

            return;
        }

        sender.sendMessage(PREFIX + ChatColor.RED + "Usage: /foundation coins or /foundation coins add <amount>");
    }

    private void sendFoundationMessage(CommandSender sender, ConfigService configService) {
        sender.sendMessage(configService.getMessage(
                "messages.awakened",
                PROJECT_NAME + " has awakened."
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
        sender.sendMessage(ChatColor.GRAY + "/foundation profile" + ChatColor.WHITE + " - Shows your player profile.");
        sender.sendMessage(ChatColor.GRAY + "/foundation coins" + ChatColor.WHITE + " - Shows your coins.");
        sender.sendMessage(ChatColor.GRAY + "/foundation coins add <amount>" + ChatColor.WHITE + " - Adds coins for testing.");
    }

    private void sendVersionMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "FoundationCore");
        sender.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.WHITE + getDescription().getVersion());
        sender.sendMessage(ChatColor.GRAY + "Stage: " + ChatColor.WHITE + STAGE);
    }

    private void sendProfileMessage(CommandSender sender, Player player) {
        PlayerProfileService profileService = engine.services().get(PlayerProfileService.class);
        PlayerProfile profile = profileService.get(player);

        if (profile == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Your profile is not loaded.");
            return;
        }

        sender.sendMessage(ChatColor.GOLD + "================== PROFILE ==================");
        sender.sendMessage(ChatColor.GRAY + "Name: " + ChatColor.WHITE + profile.getUsername());
        sender.sendMessage(ChatColor.GRAY + "Coins: " + ChatColor.WHITE + profile.getCoins());
        sender.sendMessage(ChatColor.GRAY + "Level: " + ChatColor.WHITE + profile.getLevel());
        sender.sendMessage(ChatColor.GRAY + "Experience: " + ChatColor.WHITE + profile.getExperience());
        sender.sendMessage(ChatColor.GRAY + "First Join: " + ChatColor.WHITE + profile.getFirstJoin());
        sender.sendMessage(ChatColor.GRAY + "Last Seen: " + ChatColor.WHITE + profile.getLastSeen());
        sender.sendMessage(ChatColor.GOLD + "=============================================");
    }
}