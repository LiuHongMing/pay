package com.github.tiger.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

/**
 * Jedis模板类
 *
 * @author liuhongming
 */
public class JedisTemplate {

    public static final int DEFAULT_PORT = 6379;

    /**
     * Jedis连接池
     */
    private Pool<Jedis> pool;

    /**
     * 默认JedisPoolConfig配置
     */
    public JedisTemplate(String host) {
        this(new JedisPoolConfig(), host, DEFAULT_PORT);
    }

    /**
     * 默认JedisPoolConfig配置
     */
    public JedisTemplate(String host, int port) {
        this(new JedisPoolConfig(), host, port);
    }

    /**
     * 自定义JedisPoolConfig配置
     */
    public JedisTemplate(JedisPoolConfig jedisPoolConfig, String host, int port) {
        this(jedisPoolConfig, host, port, Protocol.DEFAULT_TIMEOUT);
    }

    /**
     * 自定义JedisPoolConfig配置
     */
    public JedisTemplate(JedisPoolConfig jedisPoolConfig, String host, int port, int timeout) {
        pool = new JedisPool(jedisPoolConfig, host, port, timeout);
    }

    /**
     * 获取Jedis对象
     *
     * @return
     */
    public Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * 选择数据库
     *
     * @param dbIndex
     * @return
     */
    public Jedis getJedis(int dbIndex) {
        Jedis jedis = getJedis();
        jedis.select(dbIndex);
        return jedis;
    }

}
