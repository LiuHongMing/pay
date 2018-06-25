package com.github.tiger.common.redis;

import redis.clients.jedis.HostAndPort;

/**
 * Redis节点基本信息
 *
 * @author liuhongming
 */
public class RedisNodeInfo {

    private String name;
    private HostAndPort hostAndPort;

    public RedisNodeInfo(String name, HostAndPort hostAndPort) {
        this.name = name;
        this.hostAndPort = hostAndPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }
}
