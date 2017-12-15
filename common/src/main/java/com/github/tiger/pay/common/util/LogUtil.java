package com.github.tiger.pay.common.util;

import com.github.tiger.pay.common.kafka.ProducerUtil;

/**
 * 日志输出工具类
 *
 * @author liuhongming
 */
public class LogUtil {

    public static void toKafka(String topic, String key, String value) {
        ProducerUtil.send(topic, key, value);
    }

}
