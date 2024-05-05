package net.sebastian.shop;

import lombok.Getter;
import lombok.extern.java.Log;
import net.sebastian.ShopPlugin;
import net.sebastian.shop.model.Shop;
import net.sebastian.shop.model.item.ShopItem;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */
@Log
@Getter
public class ShopService {

    private final ShopRepository repository;
    private final String shopIdentifier = "Shop-One";
    /**
     * Map that stores the ShopItem for #ShopBuyInventory & InventoryClickListener
     */
    private final HashMap<Player, ShopItem> confirmBuy;
    /**
     * Map that stores the Shopitem for #ShopEditInventory & InventoryClickListener
     */
    private final HashMap<Player, ShopItem> modifyItem;
    private Shop shop;

    public ShopService() {
        this.confirmBuy = new HashMap<>();
        this.modifyItem = new HashMap<>();
        this.repository = ShopPlugin.getPlugin().getMongoManager().create(ShopRepository.class);
        this.setup();
    }


    private void setup() {
        Shop existingShop = this.repository.findFirstById(this.shopIdentifier);
        if (existingShop == null) {
            log.info("Creating new shop!");
            this.shop = new Shop(this.shopIdentifier, new HashMap<>());
        } else {
            log.info("Existing shop found. Loading...");
            this.shop = existingShop;
        }
    }

    public void save() {
        this.repository.save(this.shop);
        log.info("Saved Shop with " + this.shop.getShopItemStacks().size() + " Items.");
    }


}
