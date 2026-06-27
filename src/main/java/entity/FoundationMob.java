package entity;

import registry.Identifiable;

public class FoundationMob implements Identifiable {

    private final String id;
    private final String name;
    private final int level;
    private final double maxHealth;
    private final double damage;
    private final double defense;

    public FoundationMob(String id, String name, int level, double maxHealth, double damage, double defense) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.defense = defense;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getDamage() {
        return damage;
    }

    public double getDefense() {
        return defense;
    }
}