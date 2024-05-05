package net.sebastian;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.kyori.adventure.text.Component;
import net.sebastian.command.BedrockDebugCommand;
import net.sebastian.listener.InventoryClickListener;
import net.sebastian.listener.PlayerJoinListener;
import net.sebastian.listener.PlayerQuitListener;
import net.sebastian.shop.ShopService;
import net.sebastian.shop.gui.ShopAdminInventory;
import net.sebastian.shop.gui.ShopPlainInventory;
import net.sebastian.shop.gui.bedrock.ShopPlainBedrockInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */
@Log
@Getter
public class ShopPlugin extends JavaPlugin {

    @Getter
    private static ShopPlugin plugin;
    private final String inventoryTitle = "§eShop: §7%s";
    private final String permission = "shop.admin";
    private Map<UUID, Long> userBalance;
    private MongoManager mongoManager;
    private ShopService shopService;
    /**
     * temporary boolean to debug bedrock guis
     */
    @Setter
    private boolean debugBedrock = false;

    @Override
    public void onEnable() {
        plugin = this;

        this.userBalance = new HashMap<>();
        this.mongoManager = new MongoManager(Credentials.of("mongodb://localhost:27017", "Shop"));
        this.shopService = new ShopService();

        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);

        this.getCommand("debug").setExecutor(new BedrockDebugCommand());

        NPCLib.getInstance().registerPlugin(this);
        NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(this, "shop01", Bukkit.getWorld("world").getSpawnLocation());
        npc.setNameTag(new NPC.NameTag("§eShop"), true);
        npc.setItemInMainHand(new ItemStack(Material.GRASS_BLOCK));
        npc.addCustomClickAction(NPC.Interact.ClickType.RIGHT_CLICK, (npc1, player) -> {
            if (player.isSneaking() && player.hasPermission(this.getPermission())) {
                new ShopAdminInventory(player).open();
            } else {
                /**
                 * Wiki: https://wiki.geysermc.org/geyser/api/
                 * Use: !GeyserApi#isBedrockPlayer(UUID)
                 */
                if (!isDebugBedrock()) {
                    new ShopPlainInventory(player).open();
                } else {
                    new ShopPlainBedrockInventory(player).open();
                }
            }
        });

        /**
         * DEBUGGING
         */
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                final long balance = this.getUserBalance().get(onlinePlayer.getUniqueId());
                onlinePlayer.sendActionBar(Component.text("§7Dein Geld: §a" + NumberFormat.getInstance().format(balance)));
            }
        }, 0L, 20L);
        /**
         * DEBUGGING
         */


    }

    @Override
    public void onDisable() {
        this.shopService.save();
        log.info("Saving existing shop in database...");
    }
}
