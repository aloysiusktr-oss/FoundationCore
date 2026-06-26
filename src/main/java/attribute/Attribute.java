package attribute;

public enum Attribute {

    HEALTH(100),
    DEFENSE(0),
    STRENGTH(0),
    SPEED(100),
    INTELLIGENCE(100),
    CRIT_CHANCE(30),
    CRIT_DAMAGE(50),
    BONUS_ATTACK_SPEED(0),
    FEROCITY(0),

    MINING_FORTUNE(0),
    FARMING_FORTUNE(0),
    FORAGING_FORTUNE(0),
    FISHING_SPEED(0),

    MAGIC_FIND(0);

    private final double defaultValue;

    Attribute(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public double getDefaultValue() {
        return defaultValue;
    }
}