package com.github.tiger.pay.common.cache;

import com.google.common.cache.Cache;
import com.google.common.collect.Maps;
import org.springframework.cache.interceptor.CacheOperation;

/**
 * 缓存服务接口
 *
 * @author liuhongming
 */
public interface CacheService extends CacheOperations {

    void addCache(String key, Cache<?, ?> cache);

}
