package com.senyint.pay.web;

import com.senyint.server.NettyServer;
import com.senyint.server.channel.DefaultChannelInitializer;
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
