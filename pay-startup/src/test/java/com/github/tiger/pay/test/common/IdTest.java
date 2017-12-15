package com.github.tiger.pay.test.common;

import io.netty.channel.DefaultChannelId;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class IdTest {

    char[] HEX_CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @Test
    public void testId() {
        // ObjectId objectId = new ObjectId();
        // System.out.println(objectId.toHexString());

        DefaultChannelId channelId = DefaultChannelId.newInstance();
        System.out.println(channelId.asLongText());
    }

    public byte getByte(long l, int offset) {
        return (byte) (l >> offset);
    }

    @Test
    public void testCustomId() {
        AtomicInteger atomic = new AtomicInteger(0);
        for (int x = 0; x < 100; x++) {
            long millis = System.currentTimeMillis();
            byte[] bytes = new byte[12];
            bytes[0] = getByte(millis, 56);
            bytes[1] = getByte(millis, 48);
            bytes[2] = getByte(millis, 40);
            bytes[3] = getByte(millis, 32);
            bytes[4] = getByte(millis, 24);
            bytes[5] = getByte(millis, 16);
            bytes[6] = getByte(millis, 8);
            bytes[7] = getByte(millis, 0);

            int incr = atomic.getAndIncrement();
            bytes[8] = getByte(incr, 24);
            bytes[9] = getByte(incr, 16);
            bytes[10] = getByte(incr, 8);
            bytes[11] = getByte(incr, 0);

            char[] chars = new char[24];
            int i = 0;
            for (byte b : bytes) {
                chars[i++] = HEX_CHARS[(b >> 4) & 0xf];
                chars[i++] = HEX_CHARS[b & 0xf];
            }
            System.out.println(new String(chars));
        }
    }

}
