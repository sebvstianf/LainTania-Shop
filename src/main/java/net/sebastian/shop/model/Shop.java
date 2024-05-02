package net.sebastian.shop.model;

import eu.koboo.en2do.repository.entity.Id;
import lombok.*;
import net.sebastian.shop.model.item.ShopItem;

import java.util.HashMap;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Shop {

    @Id
    String uniqueId;
    HashMap<Integer, ShopItem> shopItemStacks;
}
