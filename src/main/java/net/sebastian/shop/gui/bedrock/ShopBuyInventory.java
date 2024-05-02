package net.sebastian.shop.gui.bedrock;

import net.kyori.adventure.text.Component;
import net.sebastian.ShopPlugin;
import net.sebastian.shop.model.item.ShopItem;
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

public class ShopBuyInventory {

    private final Player player;
    private final Inventory inventory;

    public ShopBuyInventory(final Player player, final ShopItem shopItem) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 3 * 9, Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "Vorgang")));
        ShopPlugin.getPlugin().getShopService().getConfirmBuy().put(player, shopItem);

        Utility.fillInventory(inventory, new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).name(Component.text("§r")));


        final ItemStack buyItem = new ItemCreator(Material.GREEN_DYE)
                .name(Component.text("§fItem erwerben"))
                .lore(
                        Component.text(""),
                        Component.text("§a<Linksklicke zum Kaufen>"),
                        Component.text("")
                );
        final ItemStack sellItem = new ItemCreator(Material.RED_DYE)
                .name(Component.text("§fItem verkaufen"))
                .lore(
                        Component.text(""),
                        Component.text("§a<Linksklicke zum Verkaufen>"),
                        Component.text("")
                );
        final ItemStack back = new ItemCreator(Material.SPRUCE_DOOR)
                .name(Component.text("§cZurück"));

        this.inventory.setItem(13, new ItemCreator(new ItemStack(Utility.decode(shopItem.getItemStackRaw())))
                .lore(
                        Component.text(""),
                        Component.text("§7Kaufpreis: §a" + shopItem.getPurchasePrice() + "€"),
                        Component.text("§7Verkaufspreis: §a" + shopItem.getSellPrice() + "€"),
                        Component.text("§7Auf Lager: §b" + shopItem.getInStock()),
                        Component.text("")
                )
        );

        this.inventory.setItem(10, buyItem);
        this.inventory.setItem(11, buyItem);

        this.inventory.setItem(15, sellItem);
        this.inventory.setItem(16, sellItem);

        this.inventory.setItem(18, back);


    }

    public void open() {
        this.player.openInventory(this.inventory);
    }
}
