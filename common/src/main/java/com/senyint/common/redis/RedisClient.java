package com.senyint.common.redis;

import redis.clients.jedis.Jedis;

/**
 * Redis客户端工具类
 *
 * @author liuhongming
 */
public class RedisClient {

    private JedisTemplate jedisTemplate;

    public RedisClient(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    //------------------------------ Keys ------------------------------//

    /**
     * 检查给定 key 是否存在
     */
    public Boolean exists(String key) {
        Jedis jedis = jedisTemplate.getJedis();
        Boolean ret;
        try {
            ret = jedis.exists(key);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 移除 key 的过期时间
     */
    public Long persist(String key) {
        Jedis jedis = jedisTemplate.getJedis();
        Long ret;
        try {
            ret = jedis.persist(key);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 返回 key 所储存的值的类型
     */
    public String type(String key) {
        Jedis jedis = jedisTemplate.getJedis();
        String ret;
        try {
            ret = jedis.type(key);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 为给定 key 设置生存时间
     */
    public Long expire(String key, int seconds) {
        Jedis jedis = jedisTemplate.getJedis();
        Long ret;
        try {
            ret = jedis.expire(key, seconds);
        } finally {
            jedis.close();
        }
        return ret;
    }

    //------------------------------ Strings ------------------------------//

    /**
     * 将 key 设定为指定的字符串值
     */
    public String set(String key, String value) {
        Jedis jedis = jedisTemplate.getJedis();
        String ret;
        try {
            ret = jedis.set(key, value);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 将 key 设定为指定的字符串值
     * <p>
     * 可根据 key 值是否存在设置为指定的字符串值
     * 可设置过期时长
     */
    public String set(String key, String value, String nxxx, String expx, long time) {
        Jedis jedis = jedisTemplate.getJedis();
        String ret;
        try {
            ret = jedis.set(key, value, nxxx, expx, time);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 将 key 设定为指定的字符串值
     * <p>
     * 可根据 key 是否存在设置为指定的字符串值
     */
    public String set(String key, String value, String nxxx) {
        Jedis jedis = jedisTemplate.getJedis();
        String ret;
        try {
            ret = jedis.set(key, value, nxxx);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 获取 key 的字符串值
     * <p>
     * 如果 key 不存在，返回特殊值 nil
     */
    public String get(String key) {
        Jedis jedis = jedisTemplate.getJedis();
        String ret;
        try {
            ret = jedis.get(key);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位
     */
    public Boolean setbit(String key, long offset, boolean value) {
        Jedis jedis = jedisTemplate.getJedis();
        Boolean ret;
        try {
            ret = jedis.setbit(key, offset, value);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位
     */
    public Boolean setbit(String key, long offset, String value) {
        Jedis jedis = jedisTemplate.getJedis();
        Boolean ret;
        try {
            ret = jedis.setbit(key, offset, value);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位
     */
    public Boolean getbit(String key, long offset) {
        Jedis jedis = jedisTemplate.getJedis();
        Boolean ret;
        try {
            ret = jedis.getbit(key, offset);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 计算给定字符串中，被设置为 1 的比特位的数量
     */
    public Long bitcount(String key) {
        Jedis jedis = jedisTemplate.getJedis();
        Long ret;
        try {
            ret = jedis.bitcount(key);
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 计算给定字符串中，被设置为 1 的比特位的数量, 由 start 和 end 两个偏移量决定
     */
    public Long bitcount(final String key, long start, long end) {
        Jedis jedis = jedisTemplate.getJedis();
        Long ret;
        try {
            ret = jedis.bitcount(key, start, end);
        } finally {
            jedis.close();
        }
        return ret;
    }

}
