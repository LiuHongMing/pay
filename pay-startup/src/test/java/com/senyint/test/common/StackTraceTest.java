package com.senyint.test.common;

import io.netty.util.concurrent.DefaultPromise;
import org.junit.Test;

public class StackTraceTest {

    @Test
    public void print() throws Exception {
        Throwable cause = new Throwable();
        cause.setStackTrace(new StackTraceElement[]{
                new StackTraceElement(DefaultPromise.class.getName(),
                        "cancal(...)", null, -1)});
        cause.printStackTrace();
    }
}
