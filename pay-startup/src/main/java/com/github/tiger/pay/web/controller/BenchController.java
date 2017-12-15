package com.github.tiger.pay.web.controller;

import io.netty.buffer.PooledByteBufAllocator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 空接口，测试接口调用的压力
 */
@RestController
@RequestMapping("/bench")
public class BenchController {

    static class MyConcurrentHashMap extends ConcurrentHashMap {

        public MyConcurrentHashMap(int initialCapacity) {
            super(initialCapacity);
        }
    }

    final PooledByteBufAllocator pooled = new PooledByteBufAllocator(false);

    @Value("#{systemProperties.maxCon}")
    private int maxCon;

    /**
     * 先创建BenchController实例
     *
     * 执行MyConcurrentHashMap myConcurrentHashMap=new MyConcurrentHashMap(maxCon)
     *
     * 再执行@Value("#{systemProperties.maxCon}")
     *
     * 由AbstractAutowireCapableBeanFactory包装后注入属性值
     */
    public MyConcurrentHashMap myConcurrentHashMap = new MyConcurrentHashMap(maxCon);

    public BenchController() {
        System.out.println("Constructor block ...");
    }

    /**
     * OK
     */
    @RequestMapping("/ok")
    public void ok(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        byte[] bigData = new byte[1 * 1024 * 1024];
        int count = 0;
        Random random = new Random(99999);
        for (;;) {
            count++;
            if (count > random.nextInt()) {
                break;
            }
        }
        long elapsed = System.currentTimeMillis() - start;
        String ret = "It's ok, elapsed=" + elapsed + "ms";
        response.setContentType("text/html;charset=UTF-8");
        response.getOutputStream().write(ret.getBytes());
        response.getOutputStream().flush();
    }
}
