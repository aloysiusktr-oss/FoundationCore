package storage;

import player.FoundationPlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerDataStorage {

    private final JavaPlugin plugin;
    private final File playersFolder;

    public PlayerDataStorage(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playersFolder = new File(plugin.getDataFolder(), "players");

        if (!playersFolder.exists()) {
            playersFolder.mkdirs();
        }
    }

    public FoundationPlayer load(UUID uuid, String name) {
        File file = new File(playersFolder, uuid + ".yml");

        if (!file.exists()) {
            return new FoundationPlayer(uuid, name);
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        FoundationPlayer foundationPlayer = new FoundationPlayer(uuid, name);
        foundationPlayer.setName(config.getString("name", name));
        foundationPlayer.setCoins(config.getInt("coins", 0));
        foundationPlayer.setLevel(config.getInt("level", 1));
        foundationPlayer.setFirstJoin(config.getBoolean("firstJoin", false));

        return foundationPlayer;
    }

    public void save(FoundationPlayer foundationPlayer) {
        File file = new File(playersFolder, foundationPlayer.getUuid() + ".yml");
        YamlConfiguration config = new YamlConfiguration();

        config.set("name", foundationPlayer.getName());
        config.set("coins", foundationPlayer.getCoins());
        config.set("level", foundationPlayer.getLevel());
        config.set("firstJoin", foundationPlayer.isFirstJoin());

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save player data for " + foundationPlayer.getName());
            e.printStackTrace();
        }
    }
}