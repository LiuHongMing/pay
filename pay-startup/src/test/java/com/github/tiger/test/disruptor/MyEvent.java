package com.github.tiger.test.disruptor;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

/**
 * 从生产者传递给消费者的数据单位。
 *
 * 事件没有特定的代码表示，因为它完全由用户定义。
 */
public class MyEvent {

    private List<String> records;

    public MyEvent() {
    }

    public List<String> getRecords() {
        return records;
    }

    public void setRecords(List<String> records) {
        this.records = records;
    }
}
