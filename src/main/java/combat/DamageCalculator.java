package combat;

public class DamageCalculator {

    public DamageResult calculate(double baseDamage, double strength, double critChance, double critDamage) {
        double damage = baseDamage * (1 + strength / 100.0);
        boolean critical = Math.random() * 100 < critChance;

        if (critical) {
            damage *= (1 + critDamage / 100.0);
        }

        return new DamageResult(damage, critical);
    }
}