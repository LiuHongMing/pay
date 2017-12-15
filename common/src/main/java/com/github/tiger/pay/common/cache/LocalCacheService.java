package com.github.tiger.pay.common.cache;

import com.github.tiger.pay.common.cache.redis.RedisCache;
import com.github.tiger.pay.common.core.BaseService;
import com.google.common.cache.Cache;

import java.util.List;
import java.util.Map;

/**
 * 本地缓存服务，可根据业务相关基本信息缓存在本地
 *
 * @author liuhongming
 */
public class LocalCacheService extends BaseService {

    /**
     * Redis缓存
     */
    private RedisCache redisCache;

    @Override
    public void set(String key, Object value, int remoteCacheExpireInSeconds) {
        Cache localCache = getLocalCache(key);
    }

    @Override
    public Map mget(List<String> keys, List<Class> types) {
        return null;
    }

    private Cache getLocalCache(String key) {
        return DEFAULT_CACHES.get(key);
    }
}
