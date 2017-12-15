package com.github.tiger.pay.common.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * Factory of KafkaConsumer
 */
public class ConsumerFactory {

    private static class ConsumerHolder {
        private static final Consumer<String, String> instance;

        static {
            Properties props = new Properties();
            props.put("bootstrap.servers", "kafka1:9092,kafka2:9092,kafka3:9092");
            props.put("group.id", "group.default");
            /* 关闭自动提交偏移量 */
            props.put("enable.auto.commit", "false");
            props.put("auto.offset.reset", "earliest");
            props.put("session.timeout.ms", "30000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            instance = new KafkaConsumer<>(props);
        }
    }

    public static Consumer<String, String> getInstance() {
        return ConsumerHolder.instance;
    }

}
