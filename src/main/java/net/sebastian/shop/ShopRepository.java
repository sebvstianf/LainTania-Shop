package net.sebastian.shop;

import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;
import net.sebastian.shop.model.Shop;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

@Collection("shops")
public interface ShopRepository extends Repository<Shop, String> {

}
