package com.github.tiger.pay.web;

import com.github.tiger.pay.server.NettyServer;
import com.github.tiger.pay.server.channel.DefaultChannelInitializer;
import com.github.tiger.pay.web.config.AppConfig;
import io.netty.channel.ChannelInitializer;

import javax.servlet.ServletException;

/**
 * 基于注解配置扩展NettyServer，提供基础Web服务
 *
 * @author liuhongming
 */
public class DefaultServer extends NettyServer {

    @Override
    public ChannelInitializer channelInitializer() throws ServletException {
        return new DefaultChannelInitializer(this, AppConfig.class);
    }
}
