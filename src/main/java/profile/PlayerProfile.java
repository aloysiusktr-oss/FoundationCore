package com.projectfoundation.core.profile;

import java.time.Instant;
import java.util.UUID;

public class PlayerProfile {

    private final UUID uuid;
    private String username;

    private final Instant firstJoin;
    private Instant lastSeen;

    private int coins;
    private int level;
    private int experience;

    public PlayerProfile(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
        this.firstJoin = Instant.now();
        this.lastSeen = Instant.now();
        this.coins = 0;
        this.level = 1;
        this.experience = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public Instant getFirstJoin() {
        return firstJoin;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public int getCoins() {
        return coins;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void updateLastSeen() {
        this.lastSeen = Instant.now();
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setCoins(int coins) {
        this.coins = Math.max(0, coins);
    }

    public void addCoins(int amount) {
        this.coins = Math.max(0, this.coins + amount);
    }

    public void removeCoins(int amount) {
        this.coins = Math.max(0, this.coins - amount);
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }

    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }

    public void addExperience(int amount) {
        this.experience = Math.max(0, this.experience + amount);
    }
}