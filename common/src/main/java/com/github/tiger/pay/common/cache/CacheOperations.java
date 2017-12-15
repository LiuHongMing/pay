package com.github.tiger.pay.common.cache;

import java.util.List;
import java.util.Map;

/**
 * 缓存操作接口，定义用于操作缓存的方法
 */
public interface CacheOperations {

    /**
     * 缓存对应key的value值
     *
     * @param key
     * @param value
     * @param remoteCacheExpireInSeconds
     */
    void set(final String key, final Object value, int remoteCacheExpireInSeconds);

    /**
     * 获取多个key的值
     *
     * @param keys
     * @param types 对应key的value值类型
     * @return
     */
    Map mget(List<String> keys, List<Class> types);

}
