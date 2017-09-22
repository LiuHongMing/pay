package com.senyint.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Scanner;

public class EchoClient {

    public void start() throws IOException {
        SelectorProvider provider = SelectorProvider.provider();
        SocketChannel sc = provider.openSocketChannel();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("localhost", 12345));
        Selector selector = provider.openSelector();
        sc.register(selector, SelectionKey.OP_CONNECT);

        Scanner scanner = new Scanner(System.in);
        while(true) {
            int selectKeys = selector.select();
            if (selectKeys > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isConnectable()) {
                        sc.finishConnect();
                        sc.register(selector, SelectionKey.OP_WRITE);
                        System.out.println("Server connected ...");
                        break;
                    } else if (key.isWritable()) {
                        System.out.println("Please input message: ");
                        String message = scanner.nextLine();
                        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
                        sc.write(byteBuffer);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoClient().start();
    }

}
