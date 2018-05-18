package com.github.tiger.test.net;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketServer {

    public static void main(String[] args) {
        try {
            SocketAddress address = new InetSocketAddress(
                    "localhost", 9001);
            // 启动监听端口 9001
            ServerSocket ss = new ServerSocket();
            ss.bind(address);
            // 接收请求
            Socket s = ss.accept();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
