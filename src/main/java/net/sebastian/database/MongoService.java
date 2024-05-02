package net.sebastian.database;

import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import lombok.Getter;

/**
 * Copyright @ 2024 ~ LainTania-Shop
 */

@Getter
public class MongoService {

    private final MongoManager mongoManager;

    public MongoService() {
        this.mongoManager = new MongoManager(Credentials.of("mongodb://localhost:27017", "Shop"));
    }
}
