package net.sebastian.shop.gui;

import net.kyori.adventure.text.Component;
import net.sebastian.ShopPlugin;
import net.sebastian.util.ItemCreator;
import net.sebastian.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

public class ShopPlainInventory {

    private final Player player;
    private final Inventory inventory;

    public ShopPlainInventory(final Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 54, Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "Startseite")));

        Utility.fillInventory(inventory, new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).name(Component.text("§r")));

        ShopPlugin.getPlugin().getShopService().getShop().getShopItemStacks().forEach((integer, shopItem) -> {
            final ItemStack itemStack = new ItemCreator(new ItemStack(Utility.decode(shopItem.getItemStackRaw())))
                    .lore(
                            Component.text(""),
                            Component.text("§7Bestand: §b" + shopItem.getInStock() + "§7/§9" + shopItem.getMaxStock()),
                            Component.text("§7Kaufpreis: §a" + shopItem.getPurchasePrice() + "€"),
                            Component.text("§7Verkaufspreis: §a" + shopItem.getSellPrice() + "€"),
                            Component.text(""),
                            Component.text("§a<Linksklicke zum Kaufen>"),
                            Component.text("§a<Rechtsklicke zum Verkaufen>"),
                            Component.text("")
                    );
            this.inventory.setItem(integer, itemStack);
        });


    }

    public void open() {
        this.player.openInventory(this.inventory);
    }

}
