package service;

import item.FoundationItem;
import item.ItemDataKeys;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import item.ItemRegistry;

public class ItemService implements FoundationService {

    private final ItemRegistry itemRegistry;
    private final ItemDataKeys itemDataKeys;

    public ItemService(ItemRegistry itemRegistry, ItemDataKeys itemDataKeys) {
        this.itemRegistry = itemRegistry;
        this.itemDataKeys = itemDataKeys;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    public FoundationItem getFoundationItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return null;
        }

        String itemId = meta.getPersistentDataContainer().get(
                itemDataKeys.getItemIdKey(),
                PersistentDataType.STRING
        );

        if (itemId == null) {
            return null;
        }

        return itemRegistry.get(itemId);
    }
}