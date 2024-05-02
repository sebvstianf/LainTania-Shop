package net.sebastian.util;

import lombok.experimental.UtilityClass;
import net.sebastian.shop.model.item.ShopItem;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

@UtilityClass
public class Utility {

    public void fillInventory(Inventory inventory, ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, itemStack);
        }
    }
    public int findAvailableSlot(Map<Integer, ShopItem> shopItems) {
        int newSlot = 0;
        while (shopItems.containsKey(newSlot)) {
            newSlot++;
        }
        return newSlot;
    }

    public String encode(ItemStack item) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(item);
            return new String(Base64Coder.encode(outputStream.toByteArray()));
        } catch (Exception ignored) {
            return null;
        }
    }

    public ItemStack decode(String base64) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decode(base64));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            return (ItemStack) dataInput.readObject();
        } catch (Exception ignored) {
            return null;
        }
    }


}
