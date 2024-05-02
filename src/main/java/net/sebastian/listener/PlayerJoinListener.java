package net.sebastian.listener;

import net.sebastian.ShopPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ShopPlugin.getPlugin().getUserBalance().put(player.getUniqueId(), 1000L);
    }
}
