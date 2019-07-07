package com.github.tiger.test.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class Producer {

    private final RingBuffer<MyEvent> ringBuffer;

    public Producer(RingBuffer<MyEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }


    public void onData() {
        long sequence = ringBuffer.next();
        try {
            // 用上面的索引取出一个空的事件用于填充（获取该序号对应的事件对象）
            MyEvent event = ringBuffer.get(sequence);
            List<String> records = new ArrayList<>(100);
            records.add(RandomStringUtils.randomNumeric(6));
            // 获取要通过事件传递的业务数据
            event.setRecords(records);
        } finally {
            // 发布事件
            // 注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用
            // 如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer
            ringBuffer.publish(sequence);
        }
    }

}
