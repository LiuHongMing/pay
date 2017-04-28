package com.senyint.common.cache;

import com.google.common.cache.Cache;

/**
 * 本地缓存服务
 *
 * @author liuhongming
 */
public class LocalCacheService extends CacheService {

    public Cache getLocalCache(String key) {
        return caches.get(key);
    }

}
