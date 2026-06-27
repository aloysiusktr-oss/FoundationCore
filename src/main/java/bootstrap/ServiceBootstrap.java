package bootstrap;

import engine.FoundationEngine;
import item.ItemDataKeys;
import item.ItemRegistry;
import org.bukkit.plugin.java.JavaPlugin;
import profile.PlayerProfileStorage;
import service.ConfigService;
import service.EconomyService;
import service.GUIService;
import service.ItemService;
import service.PlayerProfileService;
import service.PlayerService;
import storage.PlayerDataStorage;

public class ServiceBootstrap {

    private final JavaPlugin plugin;
    private final FoundationEngine engine;
    private final ItemRegistry itemRegistry;
    private final ItemDataKeys itemDataKeys;

    private ConfigService configService;
    private PlayerService playerService;
    private PlayerProfileService profileService;
    private EconomyService economyService;
    private GUIService guiService;
    private ItemService itemService;

    public ServiceBootstrap(
            JavaPlugin plugin,
            FoundationEngine engine,
            ItemRegistry itemRegistry,
            ItemDataKeys itemDataKeys
    ) {
        this.plugin = plugin;
        this.engine = engine;
        this.itemRegistry = itemRegistry;
        this.itemDataKeys = itemDataKeys;
    }

    public void register() {
        this.configService = new ConfigService(plugin);

        this.playerService = new PlayerService(
                new PlayerDataStorage(plugin)
        );

        this.profileService = new PlayerProfileService(
                new PlayerProfileStorage(plugin)
        );

        this.economyService = new EconomyService(profileService);
        this.guiService = new GUIService();
        this.itemService = new ItemService(itemRegistry, itemDataKeys);

        engine.registerService(ConfigService.class, configService);
        engine.registerService(PlayerService.class, playerService);
        engine.registerService(PlayerProfileService.class, profileService);
        engine.registerService(EconomyService.class, economyService);
        engine.registerService(GUIService.class, guiService);
        engine.registerService(ItemService.class, itemService);
    }

    public ConfigService getConfigService() {
        return configService;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public PlayerProfileService getProfileService() {
        return profileService;
    }

    public EconomyService getEconomyService() {
        return economyService;
    }

    public GUIService getGuiService() {
        return guiService;
    }

    public ItemService getItemService() {
        return itemService;
    }
}
