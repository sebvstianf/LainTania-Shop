package net.sebastian.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

public class ItemCreator extends ItemStack {

    public ItemCreator(Material material) {
        super(material);
    }

    public ItemCreator(ItemStack itemStack) {
        super(itemStack);
    }

    public ItemCreator name(Component component) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.displayName(component);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator lore(Component... content) {
        ItemMeta itemMeta = this.getItemMeta();
        List<Component> loreContent = Arrays.stream(content).toList();
        itemMeta.lore(loreContent);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator head(String playername) {
        ItemMeta itemMeta = this.getItemMeta();
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        PlayerProfile playerProfile = Bukkit.getServer().createProfile(playername);
        skullMeta.setOwnerProfile(playerProfile);
        this.setItemMeta(skullMeta);
        return this;
    }

    /**
     * @param minecraftUrl example: 4d11109f4ab03aa6c5b76cad129176ffb1fce8c174e69c9e8ba06b9f8061e5ad -> (http://textures.minecraft.net/texture/4d11109f4ab03aa6c5b76cad129176ffb1fce8c174e69c9e8ba06b9f8061e5ad)
     * @return playerhead
     */
    public ItemCreator value(String minecraftUrl) {

        ItemMeta meta = this.getItemMeta();
        SkullMeta skullMeta = (SkullMeta) meta;
        org.bukkit.profile.PlayerProfile pp = Bukkit.createPlayerProfile(UUID.fromString("4fbecd49-c7d4-4c18-8410-adf7a7348728"));
        PlayerTextures pt = pp.getTextures();
        try {
            pt.setSkin(new URL("http://textures.minecraft.net/texture/" + minecraftUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        pp.setTextures(pt);
        skullMeta.setOwnerProfile(pp);
        this.setItemMeta(meta);
        return this;
    }

    public ItemCreator amount(int amount) {
        this.setAmount(amount);
        return this;
    }

    public ItemCreator addItemFlag(ItemFlag itemFlag) {
        final ItemMeta itemMeta = this.getItemMeta();
        assert itemMeta != null;
        itemMeta.addItemFlags(itemFlag);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator removeItemFlag(ItemFlag itemFlag) {
        final ItemMeta itemMeta = this.getItemMeta();
        assert itemMeta != null;
        itemMeta.removeItemFlags(itemFlag);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator enchantment(Enchantment enchantment, long level) {
        final ItemMeta itemMeta = this.getItemMeta();
        assert itemMeta != null;
        itemMeta.addEnchant(enchantment, (int) level, true);
        this.setItemMeta(itemMeta);
        return this;
    }
}