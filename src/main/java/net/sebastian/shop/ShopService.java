package net.sebastian.shop;

import lombok.Getter;
import lombok.extern.java.Log;
import net.sebastian.ShopPlugin;
import net.sebastian.shop.model.Shop;
import net.sebastian.shop.model.ShopRepository;
import net.sebastian.shop.model.item.ShopItem;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */
@Log
public class ShopService {

    private final ShopRepository shopRepository;
    private final String shopIdentifier = "Shop-One";
    /**
     * Map that stores the ShopItem for #ShopBuyInventory & InventoryClickListener
     */
    @Getter
    private final HashMap<Player, ShopItem> confirmBuy;
    /**
     * Map that stores the Shopitem for #ShopEditInventory & InventoryClickListener
     */
    @Getter
    private final HashMap<Player, ShopItem> modifyItem;
    @Getter
    private Shop shop;

    public ShopService() {
        this.confirmBuy = new HashMap<>();
        this.modifyItem = new HashMap<>();
        this.shopRepository = ShopPlugin.getPlugin().getMongoService().getMongoManager().create(ShopRepository.class);
        this.setup();
    }


    public void setup() {
        Shop existingShop = this.shopRepository.findFirstById(this.shopIdentifier);
        if (existingShop == null) {
            log.info("Creating new shop with dummy items!");
            this.shop = new Shop(this.shopIdentifier, new HashMap<>());
        } else {
            log.info("Existing shop found. Loading...");
            this.shop = existingShop;
        }
    }

    public void save() {
        this.shopRepository.save(this.shop);
        log.info("Saved Shop with " + this.shop.getShopItemStacks().size() + " Items.");
    }


}
