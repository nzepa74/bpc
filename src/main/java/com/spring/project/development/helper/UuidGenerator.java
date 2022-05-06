package com.spring.project.development.helper;

import java.util.UUID;

/**
 * Created By zepaG on 7/5/2021.
 */
public class UuidGenerator {
    public static String generateUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
