package com.github.tiger.pay.test.netty.demo;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class ChatClientHandler extends ChannelInboundHandlerAdapter {

    private String clientName;

    private ChannelHandlerContext ctx;

    public ChatClientHandler(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        ctx.writeAndFlush("Client " + clientName);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Received : " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void say(String msg) {
        this.ctx.writeAndFlush(msg);
    }
}
