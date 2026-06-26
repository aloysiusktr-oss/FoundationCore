package com.projectfoundation.core.service;

import com.projectfoundation.core.player.FoundationPlayer;
import com.projectfoundation.core.player.PlayerManager;
import com.projectfoundation.core.storage.PlayerDataStorage;
import org.bukkit.entity.Player;

public class PlayerService implements FoundationService {

    private final PlayerManager manager;

    public PlayerService(PlayerDataStorage storage) {
        this.manager = new PlayerManager(storage);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        manager.saveAll();
    }

    public FoundationPlayer load(Player player) {
        return manager.loadPlayer(player);
    }

    public FoundationPlayer get(Player player) {
        return manager.getPlayer(player);
    }

    public void unload(Player player) {
        manager.unloadPlayer(player);
    }

    public void save(Player player) {
        manager.savePlayer(player);
    }
}