package player;

import java.util.UUID;

public class FoundationPlayer {

    private final UUID uuid;
    private String name;
    private int coins;
    private int level;
    private boolean firstJoin;

    public FoundationPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.coins = 0;
        this.level = 1;
        this.firstJoin = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public int getLevel() {
        return level;
    }

    public boolean isFirstJoin() {
        return firstJoin;
    }

    public void setFirstJoin(boolean firstJoin) {
        this.firstJoin = firstJoin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void removeCoins(int amount) {
        this.coins = Math.max(0, this.coins - amount);
    }
    public void setCoins(int coins) {
        this.coins = Math.max(0, coins);
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }
}