package com.projectfoundation.core.gui;

import org.bukkit.inventory.ItemStack;

public class MenuButton {

    private final ItemStack item;
    private final Runnable action;

    public MenuButton(ItemStack item, Runnable action) {
        this.item = item;
        this.action = action;
    }

    public ItemStack getItem() {
        return item;
    }

    public void click() {
        if (action != null) {
            action.run();
        }
    }
}