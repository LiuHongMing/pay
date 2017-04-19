package com.senyint.common.kafka;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConsumerUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerUtil.class);

    private static Consumer<String, String> consumer = ConsumerFactory.getInstance();

    private static final List<ConsumerRecord<String, String>> buffer = Lists.newArrayList();

    private static volatile boolean isClosed = false;

    private static int batchSize = 1;

    public static void receive(List<String> topics, RecordOperator future) {
        consumer.subscribe(topics);
        while (!isClosed()) {
            try {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("receive {}", record);
                    buffer.add(record);
                }
            /* 数据达到批量要求，就写入DB，同步确认offset */
                if (buffer.size() >= batchSize) {
                    if (future != null) {
                        try {
                            future.commit(buffer);
                        } catch (Exception e) {
                            logger.error("commit records error", e);
                        }
                    }
                    logger.info("commit offset now");
                    consumer.commitSync();
                    buffer.clear();
                }
            } catch (CommitFailedException e) {
                logger.error("commit sync error", e);
            }
        }
    }

    public static void close() {
        logger.info("close consumer");
        isClosed = true;
        consumer.close();
    }

    public static boolean isClosed() {
        return isClosed;
    }

}
