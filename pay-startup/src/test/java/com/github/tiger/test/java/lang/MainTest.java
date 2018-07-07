package com.github.tiger.test.java.lang;

import com.github.tiger.common.util.Dom4jUtil;
import com.github.tiger.common.util.RandomStringUtil;
import com.github.tiger.pay.constant.PayWayEnum;
import com.github.tiger.pay.dto.factory.OutTradeNoFactory;
import io.netty.util.internal.MacAddressUtil;
import org.junit.Test;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class MainTest {

    public static void main(String[] args) {
        // JDK1.7
        int one_million = 1_000_000;

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(10);
    }

    /**
     * 内存泄露
     */
    @Test
    public void testMemoryLeak() throws InterruptedException {
        leakMethod();
        while (true) {
            Object o1 = v1.get(0);
            System.out.println(o1);
            System.out.println(objs[0]);
        }
    }

    Vector v1 = new Vector();
    Object[] objs = new Object[1];

    public void leakMethod() {
        Object o1 = new Object();
        v1.add(o1);
        // 改变o1引用的对象，之前引用的对象仍被v1所引用
        o1 = null;
        objs[0] = o1;
    }

    @Test
    public void testEnums() {
        System.out.println(PayWayEnum.WEIXIN);
    }

    @Test
    public void testRandom() {
        for (int i = 0; i < 100; i++) {
            String merchantOrderNo = RandomStringUtil.randomNumeric(15);
            String tradeNo = String.format("%1$tY%1$tm%1$td%2$s",
                    new Date(), RandomStringUtil.randomNumeric(8));
            System.out.println(OutTradeNoFactory.newInstance(merchantOrderNo, tradeNo));
        }
    }

    @Test
    public void testMacAddress() {
        byte[] bestAvailableMac = MacAddressUtil.bestAvailableMac();
        String macAddress = MacAddressUtil.formatAddress(bestAvailableMac);
        System.out.println(macAddress);
    }

    @Test
    public void testPrimitive() {
        System.out.println(char.class == Character.class);
        System.out.println(char.class.isPrimitive() + "," + char.class.getCanonicalName());
        System.out.println(Character.class.isPrimitive() + "," + Character.class.getCanonicalName());
    }

}
