package com.github.tiger.test.redis;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ProtocolTest {

    /**
     * 协议规范
     *
     * @throws IOException
     */
    @Test
    public void testSpecification() throws IOException {
        String setCmd  = "*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n";
        String infoCmd = "*1\r\n$4\r\nINFO\r\n";

        String protocol = setCmd + infoCmd;

        Socket client = new Socket();
        /**
         * 连接超时时间
         */
        int connectTimeout = 1000;
        SocketAddress endpoint = new InetSocketAddress(
                "192.168.20.66", 6379);
        client.connect(endpoint, connectTimeout);
        /**
         * 读取数据超时时间
         */
        int soTimeout = 100;
        client.setSoTimeout(soTimeout);

        PrintStream out = new PrintStream(client.getOutputStream());
        out.print(protocol);

        BufferedReader buf = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
        while (true) {
            try {
                String resp = buf.readLine();
                if (resp != null) {
                    System.out.println(resp);
                }
            } catch (Exception e) {

                break;
            }
        }

        buf.close();
        client.close();
    }

}
