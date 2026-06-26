package com.projectfoundation.core.profile;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class PlayerProfileStorage {

    private final JavaPlugin plugin;
    private final File profilesFolder;

    public PlayerProfileStorage(JavaPlugin plugin) {
        this.plugin = plugin;
        this.profilesFolder = new File(plugin.getDataFolder(), "profiles");

        if (!profilesFolder.exists()) {
            profilesFolder.mkdirs();
        }
    }

    public PlayerProfile load(UUID uuid, String username) {
        File file = new File(profilesFolder, uuid + ".yml");

        if (!file.exists()) {
            return new PlayerProfile(uuid, username);
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        PlayerProfile profile = new PlayerProfile(uuid, username);
        profile.setUsername(config.getString("username", username));
        profile.setLastSeen(Instant.parse(config.getString("lastSeen", Instant.now().toString())));
        profile.setCoins(config.getInt("coins", 0));
        profile.setLevel(config.getInt("level", 1));
        profile.setExperience(config.getInt("experience", 0));

        return profile;
    }

    public void save(PlayerProfile profile) {
        File file = new File(profilesFolder, profile.getUuid() + ".yml");
        YamlConfiguration config = new YamlConfiguration();

        config.set("uuid", profile.getUuid().toString());
        config.set("username", profile.getUsername());
        config.set("firstJoin", profile.getFirstJoin().toString());
        config.set("lastSeen", profile.getLastSeen().toString());
        config.set("coins", profile.getCoins());
        config.set("level", profile.getLevel());
        config.set("experience", profile.getExperience());

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save profile for " + profile.getUsername());
            e.printStackTrace();
        }
    }
}