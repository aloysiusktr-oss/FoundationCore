package com.projectfoundation.core.service;

import com.projectfoundation.core.gui.FoundationMenu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIService implements FoundationService {

    private final Map<UUID, FoundationMenu> openMenus = new HashMap<>();

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        openMenus.clear();
    }

    public void setOpenMenu(Player player, FoundationMenu menu) {
        openMenus.put(player.getUniqueId(), menu);
    }

    public FoundationMenu getOpenMenu(Player player) {
        return openMenus.get(player.getUniqueId());
    }

    public void closeMenu(Player player) {
        openMenus.remove(player.getUniqueId());
    }
}