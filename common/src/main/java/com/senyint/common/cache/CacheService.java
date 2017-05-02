package com.senyint.common.cache;

import com.google.common.cache.Cache;
import com.google.common.collect.Maps;
import com.senyint.common.core.BaseService;
import org.springframework.cache.interceptor.CacheOperation;

import java.util.List;
import java.util.Map;

/**
 * 缓存服务接口
 *
 * @author liuhongming
 */
public interface CacheService extends CacheOperations {

    void addCache(String key, Cache<?, ?> cache);

}
