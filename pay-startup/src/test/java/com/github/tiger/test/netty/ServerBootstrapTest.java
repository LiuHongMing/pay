package com.github.tiger.test.netty;

import com.github.tiger.pay.Server;
import com.github.tiger.pay.server.NettyServer;

public class ServerBootstrapTest {

    public static void main(String[] args) {
        Server server = new NettyServer(8080);
        server.start();
    }
}
