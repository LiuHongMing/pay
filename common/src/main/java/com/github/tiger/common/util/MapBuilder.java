package com.github.tiger.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Map构造工具类
 *
 * @author liuhongming
 */
public class MapBuilder {

    private Map<Object, Object> map = new HashMap<>();

    private MapBuilder() {
    }

    public static MapBuilder create() {
        return new MapBuilder();
    }

    public MapBuilder put(Object k, Object v) {
        map.put(k, v);
        return this;
    }

    public Map build() {
        return map;
    }

    public static Map newMap(Object k, Object v, Object... kv) {
        MapBuilder builder = MapBuilder.create();
        builder.put(k, v);
        if (kv != null && kv.length > 0)
            for (int i = 0, len = kv.length; i + 1 < len; i += 2)
                builder.put(kv[i], kv[i + 1]);
        return builder.build();
    }

}