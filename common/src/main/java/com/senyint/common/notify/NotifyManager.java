package com.senyint.common.notify;

import com.alibaba.fastjson.JSONObject;
import com.senyint.common.util.HttpClientUtil;
import com.senyint.common.util.NettyUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NotifyManager {

    private static final Logger logger = LoggerFactory.getLogger(NotifyManager.class);

    private static final String SUCCESS = "SUCCESS";

    private static final String FAILURE = "FAILURE";

    private static final int PERIOD = 1;

    private static final TimeUnit MINUTES = TimeUnit.MINUTES;

    private static final int TIMES = 8;

    private static final ScheduledThreadPoolExecutor schedules = new ScheduledThreadPoolExecutor(
            NettyUtil.nThread(), new DefaultThreadFactory("notify-scheduled"));

    public boolean notify(String notifyUrl, Map<String, Object> params) throws Exception {
        for (int i = 1; i <= TIMES; i++) {
            String responseText = HttpClientUtil.post(notifyUrl, params);
            if (StringUtils.isEmpty(responseText)) {
                logger.error("通知未返回结果：notifyUrl：{}, params：{}", notifyUrl, params);
                continue;
            }
            Map<String, Object> kv = (Map) JSONObject.parse(responseText);
            String returnCode = (String) kv.get("return_code");
            if (SUCCESS.equals(returnCode)) {
                return true;
            } else if (FAILURE.equals(returnCode)) {
                logger.warn("通知失败：notifyUrl：{}, params：{}", notifyUrl, params);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (Exception e) {
                    logger.error("通知异常：" + e.getMessage(), e);
                }
            }
        }
        nextNotify(notifyUrl, params);
        return false;
    }

    public void nextNotify(final String notifyUrl, final Map<String, Object> params) {
        schedules.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.warn("延时通知：notifyUrl: {}, params: {}", notifyUrl, params);
                    NotifyManager.this.notify(notifyUrl, params);
                } catch (Exception e) {
                    logger.error("延时通知失败：" + e.getMessage(), e);
                }
            }
        }, PERIOD, MINUTES);
    }

}
