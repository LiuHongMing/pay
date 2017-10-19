package com.senyint.test.netty;

import com.senyint.Server;
import com.senyint.server.NettyServer;

public class ServerBootstrapTest {

    public static void main(String[] args) {
        Server server = new NettyServer(8080);
        server.start();
    }
}
