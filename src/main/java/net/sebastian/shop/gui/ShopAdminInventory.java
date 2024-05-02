package net.sebastian.shop.gui;

import net.kyori.adventure.text.Component;
import net.sebastian.ShopPlugin;
import net.sebastian.util.ItemCreator;
import net.sebastian.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */
public class ShopAdminInventory {

    private final Player player;
    private final Inventory inventory;

    public ShopAdminInventory(final Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 54, Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "Admin")));

        ShopPlugin.getPlugin().getShopService().getShop().getShopItemStacks().forEach((integer, shopItem) -> {

            // 0, 1, 2, 3. usw...
            final ItemStack itemStack = new ItemCreator(new ItemStack(Utility.decode(shopItem.getItemStackRaw())))
                    .lore(
                            Component.text(""),
                            Component.text("§a<Rechtsklicke zum Editieren>"),
                            Component.text("")
                    );
            this.inventory.setItem(integer, itemStack);
        });
    }

    public void open() {
        this.player.openInventory(this.inventory);
    }
}
