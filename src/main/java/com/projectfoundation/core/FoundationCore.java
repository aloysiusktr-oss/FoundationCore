package com.projectfoundation.core;

import attribute.Attribute;
import attribute.AttributeModifier;
import attribute.ModifierOperation;
import bootstrap.CommandBootstrap;
import bootstrap.ItemBootstrap;
import bootstrap.ListenerBootstrap;
import bootstrap.ServiceBootstrap;
import engine.FoundationEngine;
import gui.TestMenu;
import item.FoundationItem;
import item.ItemDataKeys;
import item.ItemStackFactory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import profile.PlayerProfile;
import service.ConfigService;
import service.EconomyService;
import service.GUIService;
import service.PlayerProfileService;
import item.ItemRegistry;
import combat.DamageCalculator;
import combat.DamageResult;
import attribute.Attribute;

public final class FoundationCore extends JavaPlugin {

    private static final String PREFIX = ChatColor.GOLD + "[Foundation] " + ChatColor.RESET;
    private static final String PROJECT_NAME = "Project Foundation";
    private static final String STAGE = "SkyBlock Alpha Engine";

    private FoundationEngine engine;
    private TestMenu testMenu;
    private GUIService guiService;
    private ItemRegistry itemRegistry;
    private ItemDataKeys itemDataKeys;

    @Override
    public void onEnable() {
        this.engine = new FoundationEngine();
        this.itemDataKeys = new ItemDataKeys(this);
        this.itemRegistry = new ItemRegistry();
        this.testMenu = new TestMenu();

        ServiceBootstrap serviceBootstrap = new ServiceBootstrap(
                this,
                engine,
                itemRegistry,
                itemDataKeys
        );
        serviceBootstrap.register();

        this.guiService = serviceBootstrap.getGuiService();

        new ItemBootstrap(itemRegistry).register();

        new ListenerBootstrap(this, engine).register();

        new CommandBootstrap(this).register();

        engine.start();

        logStartup();
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

            case "attributes":
                handleAttributesCommand(sender);
                return true;

            case "attributetest":
                handleAttributeTestCommand(sender);
                return true;

            case "testitem":
                handleTestItemCommand(sender);
                return true;

            case "clearattributes":
                handleClearAttributesCommand(sender);
                return true;

            case "damagetest":
                handleDamageTestCommand(sender);
                return true;

            default:
                sender.sendMessage(PREFIX + ChatColor.RED + "Unknown command. Use /foundation help.");
                return true;
        }
    }

    private void logStartup() {
        getLogger().info("=================================");
        getLogger().info(" " + PROJECT_NAME + " has awakened.");
        getLogger().info(" FoundationCore v" + getDescription().getVersion());
        getLogger().info("=================================");
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

    private void handleAttributesCommand(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only players can use this command.");
            return;
        }

        PlayerProfile profile = getProfile(player);

        if (profile == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Your profile is not loaded.");
            return;
        }

        sender.sendMessage(ChatColor.GOLD + "============== ATTRIBUTES ==============");

        for (Attribute attribute : Attribute.values()) {
            double value = profile.getAttributes().get(attribute);

            sender.sendMessage(
                    ChatColor.YELLOW + formatAttribute(attribute)
                            + ChatColor.GRAY + " : "
                            + ChatColor.WHITE + value
            );
        }

        sender.sendMessage(ChatColor.GOLD + "========================================");
    }

    private void handleAttributeTestCommand(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only players can use this command.");
            return;
        }

        PlayerProfile profile = getProfile(player);

        if (profile == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Your profile is not loaded.");
            return;
        }

        profile.getAttributes().clearModifiers("test_sword");

        profile.getAttributes().addModifier(new AttributeModifier(
                "test_sword",
                Attribute.STRENGTH,
                ModifierOperation.ADD_FLAT,
                50
        ));

        sender.sendMessage(PREFIX + ChatColor.GREEN + "Test sword modifier applied: +50 Strength.");
    }

    private void handleTestItemCommand(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only players can use this command.");
            return;
        }

        FoundationItem testSword = itemRegistry.get("test_sword");

        if (testSword == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Training Sword is not registered.");
            return;
        }

        ItemStackFactory factory = new ItemStackFactory(itemDataKeys);

        player.getInventory().addItem(
                factory.create(Material.IRON_SWORD, testSword)
        );

        player.sendMessage(PREFIX + ChatColor.GREEN + "You received a Training Sword.");
    }

    private PlayerProfile getProfile(Player player) {
        PlayerProfileService profileService = engine.services().get(PlayerProfileService.class);
        return profileService.get(player);
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
        sender.sendMessage(ChatColor.GRAY + "/foundation attributes" + ChatColor.WHITE + " - Shows all player attributes.");
        sender.sendMessage(ChatColor.GRAY + "/foundation attributetest" + ChatColor.WHITE + " - Adds a temporary test Strength modifier.");
        sender.sendMessage(ChatColor.GRAY + "/foundation testitem" + ChatColor.WHITE + " - Gives a test sword.");
        sender.sendMessage(ChatColor.GRAY + "/foundation clearattributes" + ChatColor.WHITE + " - Clears temporary attribute modifiers.");
        sender.sendMessage(ChatColor.GRAY + "/foundation damagetest" + ChatColor.WHITE + " - Calculates your current damage.");
    }

    private void sendVersionMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "FoundationCore");
        sender.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.WHITE + getDescription().getVersion());
        sender.sendMessage(ChatColor.GRAY + "Stage: " + ChatColor.WHITE + STAGE);
    }

    private void sendProfileMessage(CommandSender sender, Player player) {
        PlayerProfile profile = getProfile(player);

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

    private String formatAttribute(Attribute attribute) {
        String name = attribute.name().toLowerCase().replace("_", " ");
        String[] words = name.split(" ");

        StringBuilder builder = new StringBuilder();

        for (String word : words) {
            builder.append(Character.toUpperCase(word.charAt(0)));
            builder.append(word.substring(1));
            builder.append(" ");
        }

        return builder.toString().trim();
    }
    private void handleClearAttributesCommand(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only players can use this command.");
            return;
        }

        PlayerProfileService profileService = engine.services().get(PlayerProfileService.class);
        PlayerProfile profile = profileService.get(player);

        if (profile == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Your profile is not loaded.");
            return;
        }

        profile.getAttributes().clearModifiers("test_sword");
        profile.getAttributes().clearModifiers("held_item");

        profile.getAttributes().set(Attribute.STRENGTH, Attribute.STRENGTH.getDefaultValue());
        profile.getAttributes().set(Attribute.CRIT_DAMAGE, Attribute.CRIT_DAMAGE.getDefaultValue());

        sender.sendMessage(PREFIX + ChatColor.GREEN + "Temporary attribute modifiers cleared.");
    }

    private void handleDamageTestCommand(CommandSender sender) {

        if (!(sender instanceof org.bukkit.entity.Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only players can use this command.");
            return;
        }

        PlayerProfileService profileService = engine.services().get(PlayerProfileService.class);

        PlayerProfile profile = profileService.get(player);

        if (profile == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Profile not loaded.");
            return;
        }

        DamageCalculator calculator = new DamageCalculator();

        double strength = profile.getAttributes().get(Attribute.STRENGTH);
        double critChance = profile.getAttributes().get(Attribute.CRIT_CHANCE);
        double critDamage = profile.getAttributes().get(Attribute.CRIT_DAMAGE);

        DamageResult result = calculator.calculate(
                10.0,
                strength,
                critChance,
                critDamage
        );

        sender.sendMessage(ChatColor.GOLD + "========== DAMAGE TEST ==========");
        sender.sendMessage(ChatColor.GRAY + "Base Damage: " + ChatColor.WHITE + "10");
        sender.sendMessage(ChatColor.GRAY + "Strength: " + ChatColor.WHITE + strength);
        sender.sendMessage(ChatColor.GRAY + "Crit Chance: " + ChatColor.WHITE + critChance + "%");
        sender.sendMessage(ChatColor.GRAY + "Crit Damage: " + ChatColor.WHITE + critDamage + "%");
        sender.sendMessage("");

        if (result.isCritical()) {
            sender.sendMessage(ChatColor.RED + "CRITICAL HIT!");
        }

        sender.sendMessage(ChatColor.GREEN + "Final Damage: " + ChatColor.WHITE + result.getDamage());
        sender.sendMessage(ChatColor.GOLD + "===============================");
    }
}
