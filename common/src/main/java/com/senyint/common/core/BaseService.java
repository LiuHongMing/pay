package com.senyint.common.core;

import com.google.common.cache.Cache;
import com.google.common.collect.Maps;
import com.senyint.common.cache.CacheOperations;
import com.senyint.common.cache.CacheService;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

/**
 * 服务基类，通过Spring容器实例化的服务接口类可继承此类，以完善在初始化阶段的配置
 *
 * @author liuhongming
 */
public class BaseService implements InitializingBean, CacheService {

    public static final Map<String, Cache> DEFAULT_CACHES = Maps.newConcurrentMap();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void addCache(String key, Cache<?, ?> cache) {
        if (cache == null) {
            throw new IllegalArgumentException("cache参数不能为NULL");
        }
        DEFAULT_CACHES.put(key, cache);
    }

    @Override
    public void set(String key, Object value, int remoteCacheExpireInSeconds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map mget(List<String> keys, List<Class> types) {
        throw new UnsupportedOperationException();
    }
}
