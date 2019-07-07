package com.github.tiger.test.common;

import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ObjectId 构成：时间戳（4字节）+ 机器唯一标识（5）+ 计数器（3字节）
 *
 * @see <a href=https://github.com/mongodb/mongo-java-driver/blob/master/bson/src/main/org/bson/types/ObjectId.java>ObjectIdTest.java</a>
 *
 * @author liuhongming
 */
public class ObjectId {

    private static final int ID_LENGTH = 12;

    private static final char[] HEX_CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final AtomicInteger NEXT_COUNTER = new AtomicInteger(new SecureRandom().nextInt());

    private static final int RANDOM_VALUE1;
    private static final short RANDOM_VALUE2;

    static {
        SecureRandom secureRandom = new SecureRandom();
        RANDOM_VALUE1 = secureRandom.nextInt(0x10000000);
        RANDOM_VALUE2 = (short) secureRandom.nextInt(0x10000000);
    }

    private final int timestamp;
    private final int counter;
    private final int machineIdentifier;
    private final short processIdentifier;

    public ObjectId(Date date) {
        this(dateToTimestampSeconds(date), NEXT_COUNTER.getAndIncrement() & 0xffffff);
    }

    public ObjectId(int timestamp, int counter) {
        this(timestamp, RANDOM_VALUE1, RANDOM_VALUE2, counter);
    }

    public ObjectId(int timestamp, int machineIdentifier, short processIdentifier, int counter) {
        this.timestamp = timestamp;
        this.counter = counter;
        this.machineIdentifier = machineIdentifier;
        this.processIdentifier = processIdentifier;
    }

    private static byte[] legacyToBytes(final int timestamp,
                                        final int machineAndProcessIdentifier,
                                        final int counter) {
        byte[] bytes = new byte[ID_LENGTH];
        bytes[0] = int3(timestamp);
        bytes[1] = int2(timestamp);
        bytes[2] = int1(timestamp);
        bytes[3] = int0(timestamp);
        bytes[4] = int3(machineAndProcessIdentifier);
        bytes[5] = int2(machineAndProcessIdentifier);
        bytes[6] = int1(machineAndProcessIdentifier);
        bytes[7] = int0(machineAndProcessIdentifier);
        bytes[8] = int3(counter);
        bytes[9] = int2(counter);
        bytes[10] = int1(counter);
        bytes[11] = int0(counter);
        return bytes;
    }

    private static int dateToTimestampSeconds(final Date time) {
        return (int) (time.getTime() / 1000);
    }

    public static byte int3(final int x) {
        return (byte) (x >> 24);
    }

    private static byte int2(final int x) {
        return (byte) (x >> 16);
    }

    private static byte int1(final int x) {
        return (byte) (x >> 8);
    }

    private static byte int0(final int x) {
        return (byte) (x);
    }

    private static byte short1(final short x) {
        return (byte) (x >> 8);
    }

    private static byte short0(final short x) {
        return (byte) (x);
    }

    public static void main(String[] args) {
        ObjectId transactionId = new ObjectId(new Date());
        System.out.println(transactionId.toHexString());
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(ID_LENGTH);
        putToByteBuffer(buffer);
        return buffer.array();
    }

    public String toHexString() {
        char[] chars = new char[ID_LENGTH * 2];
        int i = 0;
        for (byte b : toByteArray()) {
            chars[i++] = HEX_CHARS[b >> 4 & 0xF];
            chars[i++] = HEX_CHARS[b & 0xF];
        }
        return new String(chars);
    }

    private void putToByteBuffer(ByteBuffer buffer) {
        Preconditions.checkNotNull(buffer);
        Preconditions.checkArgument(buffer.remaining() >= ID_LENGTH,
                String.format("buffer.remaining() < %s", ID_LENGTH));

        buffer.put(int3(timestamp));
        buffer.put(int2(timestamp));
        buffer.put(int1(timestamp));
        buffer.put(int0(timestamp));
        buffer.put(int2(machineIdentifier));
        buffer.put(int1(machineIdentifier));
        buffer.put(int1(machineIdentifier));
        buffer.put(short1(processIdentifier));
        buffer.put(short0(processIdentifier));
        buffer.put(int2(counter));
        buffer.put(int1(counter));
        buffer.put(int0(counter));
    }
}
