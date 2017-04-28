package com.senyint.common.cache;

import com.google.common.cache.Cache;
import com.google.common.collect.Maps;
import com.senyint.common.core.BaseService;

import java.util.Map;

public class CacheService extends BaseService {

    public Map<String, Cache> caches = Maps.newHashMap();

    public void addCache(String key, Cache<?, ?> cache) {
        if (cache == null) {
            throw new IllegalArgumentException("cache参数不能为NULL");
        }
        caches.put(key, cache);
    }

    public void set(final String key, final Object value, int remoteCacheExpireInSeconds) {
    }
}
