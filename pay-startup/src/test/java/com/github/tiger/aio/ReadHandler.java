package com.github.tiger.aio;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 异步读事件完成处理类
 */
public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    AsynchronousSocketChannel channel;

    public ReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();

        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);

        String expression = null;
        try {
            expression = new String(body,"utf-8");
            System.out.println("服务器收到的消息：" + expression);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("javascript");
        try {
            Object val = scriptEngine.eval(expression);
            doWrite(String.valueOf(val));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void doWrite(String message) {

        byte[] bytes = message.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();

        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                    channel.write(buffer, buffer, this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
