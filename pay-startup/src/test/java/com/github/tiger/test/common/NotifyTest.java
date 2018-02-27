package com.github.tiger.test.common;

import com.github.tiger.pay.common.notify.NotifyManager;
import com.github.tiger.pay.common.util.MapBuilder;
import org.junit.Test;

import java.util.Map;

public class NotifyTest {

    @Test
    public void testMerchantNotify() throws Exception {
        String url = "http://172.16.208.42:8080/weixin/wechat/wap/payNotify";
        Map<String, Object> params = MapBuilder.newMap("out_trade_no", "909809618381664_2017031775706013");
        NotifyManager manager = new NotifyManager();
        manager.notify(url, params);

        while (true) {
            synchronized (NotifyTest.class) {
                try {
                    NotifyTest.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
