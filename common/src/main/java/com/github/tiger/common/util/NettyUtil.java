package com.github.tiger.common.util;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.SystemPropertyUtil;

import java.util.concurrent.ThreadFactory;

public class NettyUtil {

    public static int nThread() {
        return Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", SystemUtil.availableProcessors() * 2));
    }

    public static ThreadFactory threadFactory(String poolName) {
        return new DefaultThreadFactory(poolName);
    }

}
