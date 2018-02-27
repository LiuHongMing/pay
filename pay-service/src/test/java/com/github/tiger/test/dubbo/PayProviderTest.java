package com.github.tiger.test.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PayProviderTest {

    public static void start() {
        String[] configLocations = new String[]{"spring/service-context.xml"};
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
        ctx.start();
        synchronized (PayProviderTest.class) {
            while (true) {
                try {
                    PayProviderTest.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        start();
    }
}
