package com.github.tiger.test.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author liuhongming
 */
public class SocketClientTest {

    public static void main(String[] args) {
        Socket client = new Socket();
        try {
            /**
             * 连接超时时间
             */
            int connectionTimeout = 1000;
            SocketAddress endpoint = new InetSocketAddress(
                    "localhost", 9001);
            client.connect(endpoint, connectionTimeout);
            /**
             * 读取数据超时时间
             */
            int soTimeout = 5 * 100;
            client.setSoTimeout(soTimeout);

            System.out.println("Connected.");
            InputStream in = client.getInputStream();
            System.out.println("Reading...");
            /**
             * 读取不到数据超过soTimeout时间后，抛出 SocketTimeoutException
             */
            in.read();
            System.out.println("Read end.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
