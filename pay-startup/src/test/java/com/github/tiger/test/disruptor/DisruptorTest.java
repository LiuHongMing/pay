package com.github.tiger.test.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 高性能队列
 * <p>
 * 并发无锁队列
 */
public class DisruptorTest {

    final int BUFFER_SIZE = 1024;

    final int THREAD_NUMS = Runtime.getRuntime()
            .availableProcessors() * 2;

    @Test
    public void testWizard() throws Exception {
        // 队列中的元素
        class Element {

            private int value;

            public int get() {
                return value;
            }

            public void set(int value) {
                this.value = value;
            }

        }

        // 生产者的线程工厂
        ThreadFactory threadFactory = r -> new Thread(r, "simpleThread");

        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<Element> factory = () -> new Element();

        // 处理Event的handler
        EventHandler<Element> handler =
                (element, sequence, endOfBatch) -> System.out.println("Element:" + element.get());

        // 阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        // 指定RingBuffer的大小
        int bufferSize = BUFFER_SIZE;

        // 创建disruptor，采用单生产者模式
        Disruptor<Element> disruptor = new Disruptor(factory, bufferSize, threadFactory, ProducerType.SINGLE, strategy);

        // 设置EventHandler
        disruptor.handleEventsWith(handler);

        // 启动disruptor的线程
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        for (int l = 0; true; l++) {
            // 获取下一个可用位置的下标
            long sequence = ringBuffer.next();
            try {
                // 返回可用位置的元素
                Element event = ringBuffer.get(sequence);
                // 设置该位置元素的值
                event.set(l);
            } finally {
                ringBuffer.publish(sequence);
            }
            Thread.sleep(10);
        }
    }

    @Test
    public void testSimple() throws ExecutionException, InterruptedException {
        final RingBuffer<MyEvent> ringBuffer =
                RingBuffer.createSingleProducer(new MyEventFactory(),
                        BUFFER_SIZE, new BlockingWaitStrategy());

        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMS);
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        BatchEventProcessor<MyEvent> eventProcessor = new BatchEventProcessor<>(
                ringBuffer, sequenceBarrier, new Consumer());
        ringBuffer.addGatingSequences(eventProcessor.getSequence());

        executors.submit(eventProcessor);

        final Producer producer = new Producer(ringBuffer);

        Future<?> future = executors.submit((Callable<Void>) () -> {
            for (int i = 0; i < 1000; i++) {
                producer.onData();
            }
            return null;
        });
        // 等待生产者结束
        future.get();
        // 等上1秒，等消费都处理完成
        Thread.sleep(1000);
        // 通知事件处理器 可以结束了（并不是马上结束!!!）
        eventProcessor.halt();
        // 终止线程
        executors.shutdown();
    }


}
