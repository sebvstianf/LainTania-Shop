package net.sebastian.shop.model.item;

import lombok.*;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopItem {

    private String itemStackRaw;
    private long inStock;
    private long maxStock;
    private long purchasePrice;
    private long sellPrice;
}
