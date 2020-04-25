package com.github.tiger.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

/**
 * @author liuhongming
 * @date 2020-04-13
 */
public class IpResolver {

    public static Logger LOGGER = LoggerFactory.getLogger(IpResolver.class);

    private static String LOCAL_IP;

    public static String getIP() {

        if(StringUtils.isNotEmpty(LOCAL_IP)){
            return LOCAL_IP;
        }

        String osName = System.getProperties().getProperty("os.name");
        if (osName != null) {
            osName = osName.toLowerCase();
            if (osName.startsWith("linux")) {
                try {
                    Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                    while (allNetInterfaces.hasMoreElements()) {
                        NetworkInterface netInterface = allNetInterfaces.nextElement();
                        Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                        if (netInterface.getName().startsWith("eth")) {
                            while (addresses.hasMoreElements()) {
                                InetAddress netAddr = addresses.nextElement();
                                if (netAddr != null && netAddr instanceof Inet4Address) {
                                    LOCAL_IP =  netAddr.getHostAddress();
                                    return LOCAL_IP;
                                }
                            }
                        }
                    }
                } catch (SocketException exp) {
                    LOGGER.error("resole local ip fail", exp);
                }
            }
        }
        LOCAL_IP =  getLocalIp();
        return LOCAL_IP;
    }

    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException exp) {
            LOGGER.error("resole local ip fail", exp);
        }
        return "";
    }

}
