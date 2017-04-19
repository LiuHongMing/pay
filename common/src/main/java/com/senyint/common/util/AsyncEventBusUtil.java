package com.senyint.common.util;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 异步事件监听工具类
 *
 * @author liuhonogming
 */
public final class AsyncEventBusUtil {

    public static final EventBus DEFAULT;

    /** 事件工厂名称格式 */
    private static final String NAME_FORMAT = "eventBus-async-worker";

    static {
        int nThread = Runtime.getRuntime().availableProcessors() * 2;
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setNameFormat(NAME_FORMAT);
        ThreadFactory threadFactory = threadFactoryBuilder.build();
        Executor executor = Executors.newFixedThreadPool(nThread, threadFactory);
        DEFAULT = new AsyncEventBus(executor);
    }

    public static EventBus register(Object object) {
        DEFAULT.register(object);
        return DEFAULT;
    }
}
