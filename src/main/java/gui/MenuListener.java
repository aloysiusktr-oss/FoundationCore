package com.projectfoundation.core.gui;

import com.projectfoundation.core.service.GUIService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    private final GUIService guiService;

    public MenuListener(GUIService guiService) {
        this.guiService = guiService;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        FoundationMenu menu = guiService.getOpenMenu(player);

        if (menu == null) {
            return;
        }

        event.setCancelled(true);

        MenuButton button = menu.getButton(event.getSlot());

        if (button != null) {
            button.click();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            guiService.closeMenu(player);
        }
    }
}