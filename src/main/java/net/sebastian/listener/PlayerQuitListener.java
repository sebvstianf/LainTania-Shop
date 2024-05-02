package net.sebastian.listener;

import net.sebastian.ShopPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ShopPlugin.getPlugin().getShopService().getModifyItem().remove(player);
        ShopPlugin.getPlugin().getShopService().getConfirmBuy().remove(player);
    }
}

