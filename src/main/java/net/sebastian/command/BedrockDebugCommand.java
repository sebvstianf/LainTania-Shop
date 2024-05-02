package net.sebastian.command;

import net.kyori.adventure.text.Component;
import net.sebastian.ShopPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

public class BedrockDebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            if (!player.hasPermission(ShopPlugin.getPlugin().getPermission())) {
                return false;
            }
            ShopPlugin.getPlugin().setDebugBedrock(!ShopPlugin.getPlugin().isDebugBedrock());
            Bukkit.broadcast(Component.text("§7Inventar-Typ: " + (ShopPlugin.getPlugin().isDebugBedrock() ? "§aBedrock" : "§cJava")));
        }
        return false;
    }
}
