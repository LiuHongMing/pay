package com.github.tiger.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liuhongming
 * @date 2020-04-13
 */
public class SystemUtil {

    /**
     * 获取CPU的核心数
     *
     * @return
     */
    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取操作系统的名称
     *
     * @return
     */
    public static String getOSName() {
        return System.getProperty("os.name");
    }

    /**
     * 判断是否是Linux操作系统
     *
     * @return
     */
    public static boolean isLinux() {
        String osName = getOSName();
        if (!StringUtils.isEmpty(osName) && osName.toLowerCase().startsWith("linux")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 退出进程
     *
     * @param status
     */
    public static void exit(int status) {
        System.exit(status);
    }

    public static final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static final String getLocalIP() {
        return IpResolver.getIP();
    }

}
