package com.senyint.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class EchoServer {

    private Selector selector;
    // 创建通道缓存区
    private ByteBuffer readBuffer = ByteBuffer.allocate(8);
    private ConcurrentHashMap<SocketChannel, byte[]> clientMessage = new ConcurrentHashMap<>();

    public void start() throws IOException {
        SelectorProvider provider = SelectorProvider.provider();
        // 创建ServerSocketChannel
        ServerSocketChannel ssChannel = provider.openServerSocketChannel();
        ssChannel.configureBlocking(false); // 非阻塞模式
        ssChannel.bind(new InetSocketAddress("localhost", 12345));
        // 创建Selector
        selector = provider.openSelector();
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int selectKeys = selector.select();
            if (selectKeys > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    }
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel client = ssc.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("A new client connected ...");
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();

        this.readBuffer.clear();

        int numRead;
        try {
            numRead = client.read(readBuffer);
        } catch (Exception e) {
            key.cancel();
            client.close();
            clientMessage.remove(client);
            System.out.println(e.getMessage());
            return;
        }

        byte[] oldBytes = clientMessage.getOrDefault(client, new byte[0]);

        if (numRead > 0) {
            byte[] newBytes = new byte[oldBytes.length + numRead];
            System.arraycopy(oldBytes, 0, newBytes, 0, oldBytes.length);
            System.arraycopy(readBuffer.array(), 0, newBytes, oldBytes.length - 1, numRead);
            clientMessage.put(client, newBytes);
            System.out.println(new String(newBytes));
        } else {
            String message = new String(oldBytes);
            System.out.println(message);
        }

        client.write(ByteBuffer.wrap("12345".getBytes()));
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Server started ...");
        new EchoServer().start();
    }

}
