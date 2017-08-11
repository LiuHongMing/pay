package com.senyint.test.common;

import com.senyint.common.util.Dom4jUtil;
import com.senyint.common.util.RandomStringUtil;
import com.senyint.pay.dto.factory.OutTradeNoFactory;
import com.senyint.pay.constant.PayWayEnum;
import io.netty.util.internal.MacAddressUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MainTest {

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
    public void testXml() throws Exception {
        String xml = "<xml>\n" +
                "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "  <attach><![CDATA[支付测试]]></attach>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                "  <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n" +
                "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                "  <total_fee>1</total_fee>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                "</xml>";
        System.out.println(Dom4jUtil.parse2Map(xml));
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
