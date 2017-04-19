package com.senyint.common.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

/**
 * Factory of KafkaProducer
 */
public class ProducerFactory {

    private static class ProducerHolder {
        private static final Producer<String, String> instance;

        static {
            Properties props = new Properties();
            props.put("bootstrap.servers", "kafka1:9092,kafka2:9092,kafka3:9092");
            props.put("linger.ms", 1);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            instance = new KafkaProducer<>(props);
        }
    }

    public static Producer<String, String> getInstance() {
        return ProducerHolder.instance;
    }
}
