package service;

import entity.FoundationMob;
import entity.MobDataKeys;
import entity.MobRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

public class MobService implements FoundationService {

    private final MobRegistry mobRegistry;
    private final MobDataKeys mobDataKeys;

    public MobService(MobRegistry mobRegistry, MobDataKeys mobDataKeys) {
        this.mobRegistry = mobRegistry;
        this.mobDataKeys = mobDataKeys;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    public LivingEntity spawn(String mobId, Location location) {
        FoundationMob mob = mobRegistry.get(mobId);

        if (mob == null || location.getWorld() == null) {
            return null;
        }

        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);

        entity.getPersistentDataContainer().set(
                mobDataKeys.getMobIdKey(),
                PersistentDataType.STRING,
                mob.getId()
        );

        entity.setCustomName(ChatColor.RED + "[Lv." + mob.getLevel() + "] " + mob.getName());
        entity.setCustomNameVisible(true);
        entity.setMaxHealth(mob.getMaxHealth());
        entity.setHealth(mob.getMaxHealth());

        return entity;
    }
    public FoundationMob getFoundationMob(org.bukkit.entity.LivingEntity entity) {
        String mobId = entity.getPersistentDataContainer().get(
                mobDataKeys.getMobIdKey(),
                org.bukkit.persistence.PersistentDataType.STRING
        );

        if (mobId == null) {
            return null;
        }

        return mobRegistry.get(mobId);
    }
}