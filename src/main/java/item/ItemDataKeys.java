package item;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemDataKeys {

    private final NamespacedKey itemIdKey;

    public ItemDataKeys(JavaPlugin plugin) {
        this.itemIdKey = new NamespacedKey(plugin, "foundation_item_id");
    }

    public NamespacedKey getItemIdKey() {
        return itemIdKey;
    }
}