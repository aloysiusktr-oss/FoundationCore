package bootstrap;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandBootstrap {

    private final JavaPlugin plugin;

    public CommandBootstrap(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        // The /foundation command is currently handled by FoundationCore#onCommand.
        // This class exists so future commands have a dedicated bootstrap location.
        if (plugin.getCommand("foundation") == null) {
            plugin.getLogger().warning("The /foundation command is not registered in plugin.yml.");
        }
    }
}
