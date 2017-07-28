package com.senyint.test.common;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**D
 * 通过Java VisualVM查看 堆 Dump，在OQL控制台输入OQL查询语句
 * select {instance: s, content: s.toString()} from java.lang.String s where s.toString() == 'iByteCode'
 * 可以看到 堆 中存在两个String实例对象，内容为iByteCode
 */
public class StringTest {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        String s = new String("iByteCode");
        System.out.println(s);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        cyclicBarrier.await();
    }

}
