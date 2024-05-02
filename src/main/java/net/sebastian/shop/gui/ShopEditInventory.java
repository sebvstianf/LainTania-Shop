package net.sebastian.shop.gui;

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

public class ShopEditInventory {

    private final Player player;
    private final Inventory inventory;

    /**
     * @param player
     * @param shopItem item that can be modified in the inventory
     */
    public ShopEditInventory(final Player player, final ShopItem shopItem) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 5 * 9, Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "Editierung")));

        ShopPlugin.getPlugin().getShopService().getModifyItem().put(player, shopItem);

        Utility.fillInventory(inventory, new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).name(Component.text("§r")));

        final ItemStack purchasePriceScaler = new ItemCreator(Material.PLAYER_HEAD)
                .value("e467a7b9d76ba6d0fed743602533fc98c87af0c60f80f38da774f7a01a2093fa")
                .name(Component.text("§fKaufpreis"))
                .lore(
                        Component.text(""),
                        Component.text("§7Kaufpreis: §a" + shopItem.getPurchasePrice() + "€"),
                        Component.text(""),
                        Component.text("§a<Linksklicke um den Preis zu erhöhen>"),
                        Component.text("§c<Rechtsklicke um den Preis zu niedrigen>"),
                        Component.text("")
                );

        final ItemStack sellPriceScaler = new ItemCreator(Material.PLAYER_HEAD)
                .value("4d11109f4ab03aa6c5b76cad129176ffb1fce8c174e69c9e8ba06b9f8061e5ad")
                .name(Component.text("§fVerkaufspreis"))
                .lore(
                        Component.text(""),
                        Component.text("§7Verkaufspreis: §a" + shopItem.getSellPrice() + "€"),
                        Component.text(""),
                        Component.text("§a<Linksklicke um den Preis zu erhöhen>"),
                        Component.text("§c<Rechtsklicke um den Preis zu niedrigen>"),
                        Component.text("")
                );

        final ItemStack stockScaler = new ItemCreator(Material.PLAYER_HEAD)
                .value("d4f9072d99d13fb6f955fee01b30774967d2a2e0a80a91b889b85c79dc8a5a55")
                .name(Component.text("§fBestand"))
                .lore(
                        Component.text(""),
                        Component.text("§7Bestand: §b" + shopItem.getInStock() + "§7/§9" + shopItem.getMaxStock()),
                        Component.text(""),
                        Component.text("§a<Linksklicke um den Preis zu erhöhen>"),
                        Component.text("§c<Rechtsklicke um den Preis zu niedrigen>"),
                        Component.text("")
                );

        final ItemStack shopItemStack = new ItemCreator(new ItemStack(Utility.decode(shopItem.getItemStackRaw())));

        this.inventory.setItem(13, shopItemStack);

        this.inventory.setItem(29, purchasePriceScaler);
        this.inventory.setItem(31, stockScaler);

        this.inventory.setItem(33, sellPriceScaler);


    }

    public void open() {
        this.player.openInventory(this.inventory);
    }
}
