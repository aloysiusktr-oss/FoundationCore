package com.projectfoundation.core.player;

import com.projectfoundation.core.service.PlayerService;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final PlayerService playerService;

    public PlayerListener(PlayerService playerService) {
        this.playerService = playerService;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FoundationPlayer foundationPlayer = playerService.load(event.getPlayer());

        if (foundationPlayer.isFirstJoin()) {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Welcome to Project Foundation, " + foundationPlayer.getName() + ".");
            event.getPlayer().sendMessage(ChatColor.GRAY + "Your journey begins today.");
            foundationPlayer.setFirstJoin(false);
        } else {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Welcome back, " + foundationPlayer.getName() + ".");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerService.unload(event.getPlayer());
    }
}