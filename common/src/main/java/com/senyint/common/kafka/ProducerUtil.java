package com.senyint.common.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProducerUtil.class);

    private static Producer<String, String> producer = ProducerFactory.getInstance();

    public static void send(String topic, String key, String value) {
        ProducerRecord record = new ProducerRecord<>(topic, key, value);
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    logger.error("the producer has a exception", exception);
                }
                logger.info("metadata={}", metadata.toString());
            }
        });
    }

    public static void close() {
        producer.close();
    }
}