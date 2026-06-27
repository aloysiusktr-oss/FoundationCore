package entity;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class MobDataKeys {

    private final NamespacedKey mobIdKey;

    public MobDataKeys(JavaPlugin plugin) {
        this.mobIdKey = new NamespacedKey(plugin, "foundation_mob_id");
    }

    public NamespacedKey getMobIdKey() {
        return mobIdKey;
    }
}