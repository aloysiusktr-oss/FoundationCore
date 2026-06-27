package listener;

import attribute.Attribute;
import combat.DamageCalculator;
import combat.DamageResult;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import profile.PlayerProfile;
import service.PlayerProfileService;
import entity.FoundationMob;
import service.MobService;

public class CombatListener implements Listener {

    private final PlayerProfileService profileService;
    private final DamageCalculator damageCalculator = new DamageCalculator();
    private final MobService mobService;

    public CombatListener(PlayerProfileService profileService, MobService mobService) {
        this.profileService = profileService;
        this.mobService = mobService;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        PlayerProfile profile = profileService.get(player);

        if (profile == null) {
            return;
        }

        event.setCancelled(true);

        double strength = profile.getAttributes().get(Attribute.STRENGTH);
        double critChance = profile.getAttributes().get(Attribute.CRIT_CHANCE);
        double critDamage = profile.getAttributes().get(Attribute.CRIT_DAMAGE);

        DamageResult result = damageCalculator.calculate(
                10.0,
                strength,
                critChance,
                critDamage
        );
        FoundationMob foundationMob = mobService.getFoundationMob(target);

        double finalDamage = result.getDamage();

        if (foundationMob != null) {
            finalDamage = Math.max(1, finalDamage - foundationMob.getDefense());
        }

        target.damage(finalDamage);

        if (result.isCritical()) {
            player.sendMessage(ChatColor.RED + "CRIT! " + ChatColor.WHITE + result.getDamage() + " damage");
        } else {
            player.sendMessage(ChatColor.YELLOW + "" + result.getDamage() + " damage");
        }
    }

}