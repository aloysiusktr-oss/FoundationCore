package gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TestMenu extends FoundationMenu {

    public TestMenu() {
        super(ChatColor.DARK_GREEN + "Foundation Menu", 27);
    }

    @Override
    protected void build(Player player) {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.GREEN + "Project Foundation");
            meta.setLore(List.of(
                    ChatColor.GRAY + "The first reusable GUI.",
                    ChatColor.YELLOW + "Click to test."
            ));

            item.setItemMeta(meta);
        }

        setButton(13, new MenuButton(item, () -> {
            player.sendMessage(ChatColor.GREEN + "GUI click detected.");
            player.closeInventory();
        }));
    }
}
