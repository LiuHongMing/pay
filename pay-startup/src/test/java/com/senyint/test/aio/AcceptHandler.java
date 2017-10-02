package com.senyint.test.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 异步接收完成处理类
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AsyncServerHandler attachment) {

        int count = Server.clientCount;
        count++;

        Server.clientCount = count;
        System.out.println("客户端连接数：" + count);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer, buffer, new ReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AsyncServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
