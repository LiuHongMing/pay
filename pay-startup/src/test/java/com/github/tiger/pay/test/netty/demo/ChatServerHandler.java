package com.github.tiger.pay.test.netty.demo;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

@ChannelHandler.Sharable
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final AttributeKey<Boolean> auth = AttributeKey.valueOf("auth");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Channels : " + channels);
        Channel you = ctx.channel();
        Attribute<Boolean> attr = ctx.channel().attr(auth);
        if ("login".equals(msg)) {
            attr.set(true);
            you.writeAndFlush("Welcome!!!\n");
        } else if (Boolean.TRUE.equals(attr.get())) {
            for (Channel ch : channels) {
                if (you != ch) {
                    ch.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg);
                } else {
                    ch.writeAndFlush("[you] " + msg);
                }
            }
        } else {
            you.writeAndFlush(Unpooled.copiedBuffer("Not login!!!".getBytes(CharsetUtil.UTF_8)));
        }
        if ("bye".equals(msg.toString().toLowerCase())) {
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
