package com.github.tiger.common.cache;

import com.github.tiger.common.context.Switches;
import com.github.tiger.common.core.BaseService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class LocalCacheInitService extends BaseService {

    private LocalCacheService localCacheService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Cache<String, Object> globalCache = CacheBuilder.newBuilder()
                .softValues()
                .maximumSize(1000000)
                .expireAfterWrite(Switches.GLOBAL.getExpireInSeconds() / 2, TimeUnit.SECONDS)
                .build();

        addLocalCache(CacheKeyConstants.GLOBAL_KEY, globalCache);
    }

    private void addLocalCache(String key, Cache<?, ?> cache) {
        localCacheService.addCache(key, cache);
    }

}
