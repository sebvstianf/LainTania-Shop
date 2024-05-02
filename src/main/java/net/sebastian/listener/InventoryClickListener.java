package net.sebastian.listener;

import lombok.extern.java.Log;
import net.kyori.adventure.text.Component;
import net.sebastian.ShopPlugin;
import net.sebastian.shop.gui.ShopEditInventory;
import net.sebastian.shop.gui.bedrock.ShopBuyInventory;
import net.sebastian.shop.gui.bedrock.ShopPlainBedrockInventory;
import net.sebastian.shop.model.Shop;
import net.sebastian.shop.model.item.ShopItem;
import net.sebastian.util.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */
@Log
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            final Component inventoryTitle = event.getView().title();

            if (inventoryTitle.equals(Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "Editierung")))) {

                if (!player.hasPermission(ShopPlugin.getPlugin().getPermission())) return;
                if (event.getClickedInventory() == null) return;
                if (event.getClickedInventory() != event.getView().getTopInventory()) return;
                if (event.getInventory().getItem(event.getSlot()) == null) return;

                event.setCancelled(true);

                final ShopItem shopItem = ShopPlugin.getPlugin().getShopService().getModifyItem().get(player);

                if (shopItem == null) return;

                if (event.getSlot() == 29) {
                    if (event.getClick() == ClickType.LEFT) {
                        final int amountToAdd = 2;
                        shopItem.setPurchasePrice((shopItem.getPurchasePrice() + amountToAdd));

                    } else if (event.getClick() == ClickType.RIGHT) {
                        final int amountToAdd = 2;
                        if (shopItem.getPurchasePrice() == 0) {
                            return;
                        }
                        shopItem.setPurchasePrice((shopItem.getPurchasePrice() - amountToAdd));
                    }

                    final ItemMeta itemMeta = event.getInventory().getItem(event.getSlot()).getItemMeta();
                    itemMeta.lore(new ArrayList<>() {{
                        add(Component.text(""));
                        add(Component.text("§7Kaufpreis: §a" + shopItem.getPurchasePrice() + "€"));
                        add(Component.text(""));
                        add(Component.text("§a<Linksklicke um den Preis zu erhöhen>"));
                        add(Component.text("§c<Rechtsklicke um den Preis zu niedrigen>"));
                        add(Component.text(""));
                    }});
                    event.getInventory().getItem(event.getSlot()).setItemMeta(itemMeta);
                    return;
                }

                if (event.getSlot() == 31) {
                    if (event.getClick() == ClickType.LEFT) {
                        final int amountToAdd = 64;
                        shopItem.setMaxStock((shopItem.getMaxStock() + amountToAdd));

                    } else if (event.getClick() == ClickType.RIGHT) {
                        final int amountToAdd = 64;
                        if (shopItem.getMaxStock() == 0) {
                            return;
                        }
                        shopItem.setMaxStock((shopItem.getMaxStock() - amountToAdd));
                    }

                    final ItemMeta itemMeta = event.getInventory().getItem(event.getSlot()).getItemMeta();
                    itemMeta.lore(new ArrayList<>() {{
                        add(Component.text(""));
                        add(Component.text("§7Bestand: §b" + shopItem.getInStock() + "§7/§9" + shopItem.getMaxStock()));
                        add(Component.text(""));
                        add(Component.text("§a<Linksklicke um den Preis zu erhöhen>"));
                        add(Component.text("§c<Rechtsklicke um den Preis zu niedrigen>"));
                        add(Component.text(""));
                    }});
                    event.getInventory().getItem(event.getSlot()).setItemMeta(itemMeta);
                    return;
                }

                if (event.getSlot() == 33) {
                    if (event.getClick() == ClickType.LEFT) {
                        final int amountToAdd = 2;
                        shopItem.setSellPrice((shopItem.getSellPrice() + amountToAdd));

                    } else if (event.getClick() == ClickType.RIGHT) {
                        final int amountToAdd = 2;
                        if (shopItem.getSellPrice() <= 0) {
                            return;
                        }
                        shopItem.setSellPrice((shopItem.getSellPrice() - amountToAdd));
                    }

                    final ItemMeta itemMeta = event.getInventory().getItem(event.getSlot()).getItemMeta();
                    itemMeta.lore(new ArrayList<>() {{
                        add(Component.text(""));
                        add(Component.text("§7Verkaufspreis: §a" + shopItem.getSellPrice() + "€"));
                        add(Component.text(""));
                        add(Component.text("§a<Linksklicke um den Preis zu erhöhen>"));
                        add(Component.text("§c<Rechtsklicke um den Preis zu niedrigen>"));
                        add(Component.text(""));
                    }});
                    event.getInventory().getItem(event.getSlot()).setItemMeta(itemMeta);
                    return;
                }

                return;
            }

            if (inventoryTitle.equals(Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "B-Startseite")))) {

                if (event.getClickedInventory() == null) return;
                if (event.getClickedInventory() != event.getView().getTopInventory()) return;
                if (event.getInventory().getItem(event.getSlot()) == null) return;


                event.setCancelled(true);
                final ShopItem shopItem = ShopPlugin.getPlugin().getShopService().getShop().getShopItemStacks().get(event.getSlot());
                if (shopItem == null) return;

                new ShopBuyInventory(player, shopItem).open();
                return;
            }

            if (inventoryTitle.equals(Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "Startseite")))) {

                if (event.getClickedInventory() == null) return;
                if (event.getClickedInventory() != event.getView().getTopInventory()) return;

                event.setCancelled(true);
                final ShopItem shopItem = ShopPlugin.getPlugin().getShopService().getShop().getShopItemStacks().get(event.getSlot());
                if (shopItem == null) return;
                final ItemStack itemStackRaw = new ItemStack(Utility.decode(shopItem.getItemStackRaw()));


                if (event.getClick() == ClickType.LEFT) {

                    if (ShopPlugin.getPlugin().getUserBalance().get(player.getUniqueId()) <= shopItem.getPurchasePrice()) {
                        player.sendMessage(Component.text("§cDafür ist dein Kontostand zu niedrig."));
                        return;
                    }

                    if (shopItem.getInStock() <= 1) {
                        player.sendMessage(Component.text("§cDieses Item ist nicht auf Lager."));
                        return;
                    }

                    ShopPlugin.getPlugin().getUserBalance().put(player.getUniqueId(), (ShopPlugin.getPlugin().getUserBalance().get(player.getUniqueId()) - shopItem.getPurchasePrice()));
                    shopItem.setInStock((shopItem.getInStock() - 64));

                    player.getInventory().addItem(itemStackRaw);
                    player.sendMessage(Component.text("§aItem gekauft! (-" + shopItem.getPurchasePrice() + "€)"));

                    final ItemMeta itemMeta = event.getInventory().getItem(event.getSlot()).getItemMeta();
                    itemMeta.lore(new ArrayList<>() {{
                        add(Component.text(""));
                        add(Component.text("§7Bestand: §b" + shopItem.getInStock() + "§7/§9" + shopItem.getMaxStock()));
                        add(Component.text("§7Kaufpreis: §a" + shopItem.getPurchasePrice() + "€"));
                        add(Component.text("§7Verkaufspreis: §a" + shopItem.getSellPrice() + "€"));
                        add(Component.text(""));
                        add(Component.text("§a<Linksklicke zum Kaufen>"));
                        add(Component.text("§a<Rechtsklicke zum Verkaufen>"));
                        add(Component.text(""));
                    }});
                    event.getInventory().getItem(event.getSlot()).setItemMeta(itemMeta);


                } else if (event.getClick() == ClickType.RIGHT) {

                    if (shopItem.getInStock() >= shopItem.getMaxStock()) {
                        player.sendMessage(Component.text("§cDieses Item wird aktuell nicht angekauft."));
                        return;
                    }

                    if (!player.getInventory().containsAtLeast(itemStackRaw, 64)) {
                        player.sendMessage(Component.text("§cDu hast nicht genug von diesem Item im Inventar."));
                        return;
                    }

                    final ItemStack toRemove = itemStackRaw.clone();
                    toRemove.setAmount(64);
                    player.getInventory().removeItem(toRemove);
                    ShopPlugin.getPlugin().getUserBalance().put(player.getUniqueId(), (ShopPlugin.getPlugin().getUserBalance().get(player.getUniqueId()) + shopItem.getSellPrice()));
                    shopItem.setInStock(shopItem.getInStock() + 64);
                    player.sendMessage(Component.text("§aItem verkauft! (+" + shopItem.getSellPrice() + "€)"));


                    final ItemMeta itemMeta = event.getInventory().getItem(event.getSlot()).getItemMeta();
                    itemMeta.lore(new ArrayList<>() {{
                        add(Component.text(""));
                        add(Component.text("§7Bestand: §b" + shopItem.getInStock() + "§7/§9" + shopItem.getMaxStock()));
                        add(Component.text("§7Kaufpreis: §a" + shopItem.getPurchasePrice() + "€"));
                        add(Component.text("§7Verkaufspreis: §a" + shopItem.getSellPrice() + "€"));
                        add(Component.text(""));
                        add(Component.text("§a<Linksklicke zum Kaufen>"));
                        add(Component.text("§a<Rechtsklicke zum Verkaufen>"));
                        add(Component.text(""));
                    }});
                    event.getInventory().getItem(event.getSlot()).setItemMeta(itemMeta);

                }
                return;
            }


            if (inventoryTitle.equals(Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "Admin"))) &&
                    player.hasPermission(ShopPlugin.getPlugin().getPermission()) &&
                    event.getClickedInventory() != null) {

                event.setCancelled(true);

                if (event.getClickedInventory().equals(event.getView().getTopInventory())) {

                    final int slot = event.getSlot();
                    final ShopItem clickedShopItem = ShopPlugin.getPlugin().getShopService().getShop().getShopItemStacks().get(slot);

                    if (event.getClick() == ClickType.RIGHT) {
                        new ShopEditInventory(player, clickedShopItem).open();
                        return;
                    }

                    if (clickedShopItem != null) {
                        ShopPlugin.getPlugin().getShopService().getShop().getShopItemStacks().remove(slot);
                        event.getClickedInventory().setItem(slot, null);
                        player.getInventory().addItem(new ItemStack(Utility.decode(clickedShopItem.getItemStackRaw())));
                    }

                } else if (event.getClickedInventory().equals(event.getView().getBottomInventory()) && event.getClick() == ClickType.LEFT) {

                    final ItemStack itemStack = event.getCurrentItem();

                    if (itemStack != null) {
                        Shop shop = ShopPlugin.getPlugin().getShopService().getShop();

                        int newSlot = Utility.findAvailableSlot(shop.getShopItemStacks());
                        shop.getShopItemStacks().put(newSlot, new ShopItem(Utility.encode(itemStack), 0, 0, 0, 0));
                        event.getView().getTopInventory().setItem(newSlot, itemStack);
                        event.getClickedInventory().setItem(event.getSlot(), null);
                    }
                }
                return;
            }


            if (inventoryTitle.equals(Component.text(String.format(ShopPlugin.getPlugin().getInventoryTitle(), "Vorgang")))) {

                if (event.getClickedInventory() == null) return;
                if (event.getClickedInventory() != event.getView().getTopInventory()) return;
                if (event.getInventory().getItem(event.getSlot()) == null) return;


                event.setCancelled(true);

                final ShopItem shopItem = ShopPlugin.getPlugin().getShopService().getConfirmBuy().get(player);
                if (shopItem == null) return;

                final ItemStack itemStackRaw = new ItemStack(Utility.decode(shopItem.getItemStackRaw()));

                if (event.getSlot() == 10 || event.getSlot() == 11) {

                    if (ShopPlugin.getPlugin().getUserBalance().get(player.getUniqueId()) <= shopItem.getPurchasePrice()) {
                        player.sendMessage(Component.text("§cDafür ist dein Kontostand zu niedrig."));
                        return;
                    }

                    if (shopItem.getInStock() <= 1) {
                        player.sendMessage(Component.text("§cDieses Item ist nicht auf Lager."));
                        return;
                    }

                    ShopPlugin.getPlugin().getUserBalance().put(player.getUniqueId(), (ShopPlugin.getPlugin().getUserBalance().get(player.getUniqueId()) - shopItem.getPurchasePrice()));
                    shopItem.setInStock((shopItem.getInStock() - 64));

                    player.getInventory().addItem(itemStackRaw);
                    player.sendMessage(Component.text("§aItem gekauft! (-" + shopItem.getPurchasePrice() + "€)"));

                    ShopPlugin.getPlugin().getShopService().getConfirmBuy().remove(player);
                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                    return;
                }
                if (event.getSlot() == 15 || event.getSlot() == 16) {

                    if (shopItem.getInStock() >= shopItem.getMaxStock()) {
                        player.sendMessage(Component.text("§cDieses Item wird aktuell nicht angekauft."));
                        return;
                    }

                    if (!player.getInventory().containsAtLeast(itemStackRaw, 64)) {
                        player.sendMessage(Component.text("§cDu hast nicht genug von diesem Item im Inventar."));
                        return;
                    }

                    final ItemStack toRemove = itemStackRaw.clone();
                    toRemove.setAmount(64);
                    player.getInventory().removeItem(toRemove);


                    ShopPlugin.getPlugin().getUserBalance().put(player.getUniqueId(), (ShopPlugin.getPlugin().getUserBalance().get(player.getUniqueId()) + shopItem.getSellPrice()));
                    shopItem.setInStock((shopItem.getInStock() + 64));
                    player.sendMessage(Component.text("§aItem verkauft! (+" + shopItem.getSellPrice() + "€)"));

                    ShopPlugin.getPlugin().getShopService().getConfirmBuy().remove(player);
                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                    return;
                }

                if (event.getSlot() == 18) {
                    ShopPlugin.getPlugin().getShopService().getConfirmBuy().remove(player);
                    new ShopPlainBedrockInventory(player).open();
                }
            }
        }
    }
}

