package com.senyint.common.cache;

import com.google.common.cache.Cache;
import com.google.common.collect.Maps;
import com.senyint.common.core.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 缓存服务接口
 *
 * @author liuhongming
 */
public interface CacheService {

    void addCache(String key, Cache<?, ?> cache);

    void set(final String key, final Object value, int remoteCacheExpireInSeconds);

    Map mget(List<?> keys, List<?> types);
}
