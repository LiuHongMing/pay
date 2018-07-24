package com.github.tiger.test.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author liuhongming
 */
public class NetworkInterfaceTest {

    public static void main(String[] args) throws SocketException {

        // 可以得到本机所有的物理网络接口和虚拟机等软件利用本机的物理网络接口创建的逻辑网络接口的信息
        Enumeration<NetworkInterface> localNifs = NetworkInterface
                .getNetworkInterfaces();
        while (localNifs.hasMoreElements()) {
            NetworkInterface netInterface = localNifs.nextElement();
            Enumeration<InetAddress> inetAddr = netInterface.getInetAddresses();
            while (inetAddr.hasMoreElements()) {
                InetAddress ipAddr = inetAddr.nextElement();
                System.out.println(ipAddr.getHostName());
            }
        }
    }

}
