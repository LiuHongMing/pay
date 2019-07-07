package com.github.tiger.test.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class Consumer implements EventHandler<MyEvent>, WorkHandler<MyEvent> {

    @Override
    public void onEvent(MyEvent event, long sequence, boolean endOfBatch)
            throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(MyEvent event) throws Exception {
        event.getRecords().forEach(s -> System.out.println(s));
    }

}
