package com.github.tiger.pay.common.cache.redis;

import com.google.common.cache.Cache;
import com.github.tiger.pay.common.cache.CacheOperations;
import com.github.tiger.pay.common.redis.RedisClient;

import java.util.List;
import java.util.Map;

/**
 * 用Redis做为远程缓存，实现分布式缓存的读写
 */
public class RedisCache implements CacheOperations {

    private RedisClient redisClient;

    public RedisCache(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    @Override
    public void set(String key, Object value, int remoteCacheExpireInSeconds) {

    }

    @Override
    public Map mget(List<String> keys, List<Class> types) {
        return null;
    }

}
