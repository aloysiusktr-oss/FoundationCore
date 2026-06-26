package player;

import storage.PlayerDataStorage;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private final Map<UUID, FoundationPlayer> players = new HashMap<>();
    private final PlayerDataStorage storage;

    public PlayerManager(PlayerDataStorage storage) {
        this.storage = storage;
    }

    public FoundationPlayer loadPlayer(Player player) {
        FoundationPlayer foundationPlayer = storage.load(player.getUniqueId(), player.getName());
        players.put(player.getUniqueId(), foundationPlayer);
        return foundationPlayer;
    }

    public FoundationPlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public void savePlayer(Player player) {
        FoundationPlayer foundationPlayer = players.get(player.getUniqueId());

        if (foundationPlayer != null) {
            storage.save(foundationPlayer);
        }
    }

    public void unloadPlayer(Player player) {
        savePlayer(player);
        players.remove(player.getUniqueId());
    }

    public void saveAll() {
        for (FoundationPlayer foundationPlayer : players.values()) {
            storage.save(foundationPlayer);
        }
    }
}