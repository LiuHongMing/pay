package com.github.tiger.pay.test.netty;

import io.netty.util.concurrent.FastThreadLocal;
import org.junit.Test;

/**
 * 测试 FastThreadLocal
 */
public class FastThreadLocalTest {

    @Test
    public void testGetSet() throws Exception {
        FastThreadLocal<String> stringThreadLocal = new FastThreadLocal<>();

        if(stringThreadLocal.get() == null) {
            stringThreadLocal.set("first");
        }

        System.out.println(stringThreadLocal.get());
    }

}
