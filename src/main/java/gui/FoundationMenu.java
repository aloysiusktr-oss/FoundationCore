package com.projectfoundation.core.gui;

import com.projectfoundation.core.service.GUIService;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class FoundationMenu {

    private final String title;
    private final int size;
    private final Map<Integer, MenuButton> buttons = new HashMap<>();

    public FoundationMenu(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public void open(Player player, GUIService guiService) {
        Inventory inventory = Bukkit.createInventory(null, size, title);

        buttons.clear();
        build(player);

        for (Map.Entry<Integer, MenuButton> entry : buttons.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getItem());
        }

        guiService.setOpenMenu(player, this);
        player.openInventory(inventory);
    }

    protected void setButton(int slot, MenuButton button) {
        buttons.put(slot, button);
    }

    public MenuButton getButton(int slot) {
        return buttons.get(slot);
    }

    protected abstract void build(Player player);
}