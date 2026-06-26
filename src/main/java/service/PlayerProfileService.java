package com.projectfoundation.core.service;

import com.projectfoundation.core.profile.PlayerProfile;
import com.projectfoundation.core.profile.PlayerProfileStorage;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfileService implements FoundationService {

    private final PlayerProfileStorage storage;
    private final Map<UUID, PlayerProfile> profiles = new HashMap<>();

    public PlayerProfileService(PlayerProfileStorage storage) {
        this.storage = storage;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        saveAll();
        profiles.clear();
    }

    public PlayerProfile load(Player player) {
        PlayerProfile profile = storage.load(player.getUniqueId(), player.getName());
        profile.setUsername(player.getName());
        profiles.put(player.getUniqueId(), profile);
        return profile;
    }

    public PlayerProfile get(Player player) {
        return profiles.get(player.getUniqueId());
    }

    public void save(Player player) {
        PlayerProfile profile = profiles.get(player.getUniqueId());

        if (profile != null) {
            profile.updateLastSeen();
            storage.save(profile);
        }
    }

    public void unload(Player player) {
        save(player);
        profiles.remove(player.getUniqueId());
    }

    public void saveAll() {
        for (PlayerProfile profile : profiles.values()) {
            profile.updateLastSeen();
            storage.save(profile);
        }
    }
}