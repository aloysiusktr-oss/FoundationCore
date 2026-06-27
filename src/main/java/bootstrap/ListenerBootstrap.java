package bootstrap;

import engine.FoundationEngine;
import gui.MenuListener;
import listener.EquipmentListener;
import org.bukkit.plugin.java.JavaPlugin;
import player.PlayerListener;
import service.GUIService;
import service.ItemService;
import service.PlayerProfileService;
import service.PlayerService;

public class ListenerBootstrap {

    private final JavaPlugin plugin;
    private final FoundationEngine engine;

    public ListenerBootstrap(JavaPlugin plugin, FoundationEngine engine) {
        this.plugin = plugin;
        this.engine = engine;
    }

    public void register() {
        PlayerService playerService = engine.services().get(PlayerService.class);
        PlayerProfileService profileService = engine.services().get(PlayerProfileService.class);
        GUIService guiService = engine.services().get(GUIService.class);
        ItemService itemService = engine.services().get(ItemService.class);

        plugin.getServer().getPluginManager().registerEvents(
                new PlayerListener(playerService, profileService),
                plugin
        );

        plugin.getServer().getPluginManager().registerEvents(
                new MenuListener(guiService),
                plugin
        );

        plugin.getServer().getPluginManager().registerEvents(
                new EquipmentListener(itemService, profileService),
                plugin
        );
    }
}