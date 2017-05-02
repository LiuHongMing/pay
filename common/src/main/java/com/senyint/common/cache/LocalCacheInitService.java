package com.senyint.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.senyint.common.context.Switches;
import com.senyint.common.core.BaseService;

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
