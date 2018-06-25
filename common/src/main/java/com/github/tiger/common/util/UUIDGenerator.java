package com.github.tiger.common.util;

import java.util.UUID;

/**
 * UUID生成器
 */
public class UUIDGenerator {

    public static String getUUID32() {
        return UUID.randomUUID().toString().trim().replace("-", "");
    }

}
