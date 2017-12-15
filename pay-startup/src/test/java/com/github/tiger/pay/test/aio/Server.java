package com.github.tiger.pay.test.aio;

/**
 * 服务器类
 */
public class Server {

    public static final int DEFAULT_PORT = 12345;

    int port = DEFAULT_PORT;

    static volatile int clientCount = 0;

    public Server(int port) {
        this.port = port;
    }

    public static void start() {
        start(DEFAULT_PORT);
    }

    public static void start(int port) {
        AsyncServerHandler serverHandler = new AsyncServerHandler(port);
        new Thread(serverHandler, "AIO-ServerHandler-001").start();
    }

    public static void main(String[] args) {
        Server.start();
    }
}