package item;

import attribute.Attribute;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemStackFactory {

    private final ItemDataKeys dataKeys;

    public ItemStackFactory(ItemDataKeys dataKeys) {
        this.dataKeys = dataKeys;
    }

    public ItemStack create(Material material, FoundationItem foundationItem) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return itemStack;
        }

        meta.getPersistentDataContainer().set(
                dataKeys.getItemIdKey(),
                PersistentDataType.STRING,
                foundationItem.getId()
        );

        meta.setDisplayName(
                foundationItem.getRarity().getColor() + foundationItem.getDisplayName()
        );

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.DARK_GRAY + foundationItem.getType().name());
        lore.add("");

        for (Map.Entry<Attribute, Double> entry : foundationItem.getAttributes().entrySet()) {
            lore.add(ChatColor.GRAY + formatAttribute(entry.getKey()) + ": " + ChatColor.GREEN + "+" + entry.getValue());
        }

        lore.add("");
        lore.add(foundationItem.getRarity().getColor() + "" + ChatColor.BOLD + foundationItem.getRarity().name());

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private String formatAttribute(Attribute attribute) {
        String name = attribute.name().toLowerCase().replace("_", " ");
        String[] words = name.split(" ");

        StringBuilder builder = new StringBuilder();

        for (String word : words) {
            builder.append(Character.toUpperCase(word.charAt(0)));
            builder.append(word.substring(1));
            builder.append(" ");
        }

        return builder.toString().trim();
    }
}