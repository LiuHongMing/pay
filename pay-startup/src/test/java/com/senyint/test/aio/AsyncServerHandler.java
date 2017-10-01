package com.senyint.test.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 异步服务处理类，执行异步服务配置
 */
public class AsyncServerHandler implements Runnable {

    AsynchronousServerSocketChannel channel;

    CountDownLatch latch;

    public AsyncServerHandler(int port) {
        try {
            // 创建异步服务通道
            channel = AsynchronousServerSocketChannel.open();
            // 绑定端口
            channel.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器已启动，端口：" + port);
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept() {
        channel.accept(this, new AcceptHandler());
    }
}
