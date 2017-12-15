package com.github.tiger.pay.test.netty.demo;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.Charset;
import java.util.Scanner;

public class ChatClient {

    private static final Range<Integer> allowingPortRange = Range.closed(80, 65535);
    private static final Integer DEFAULT_LISTENING_PORT = 80;
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    private String host;
    //服务端口
    private int port ;
    //内容字符编码
    private String charset;

    public boolean connected;

    public ChatClient(String host) {
        this(host, DEFAULT_LISTENING_PORT, DEFAULT_CHARSET.toString());
    }

    public ChatClient(String host, int port) {
        this(host, port, DEFAULT_CHARSET.toString());
    }

    public ChatClient(String host, int port, String charset) {
        Preconditions.checkArgument(allowingPortRange.contains(port), "port(%s) is out of range %s", port, allowingPortRange);
        this.host = host;
        this.port = port;
        this.charset = charset;
    }

    public boolean isConnected() {
        return connected;
    }

    private ChatClientHandler chatClientHandler;

    public void connction() throws Exception {
        boolean SSL = System.getProperty("ssl") != null;
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            chatClientHandler = new ChatClientHandler("chat" + RandomStringUtils.randomNumeric(2));
                            ChannelPipeline pipeline = ch.pipeline();
                            if (sslCtx != null) {
                                pipeline.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            pipeline.addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(chatClientHandler);
                        }
                    });
            ChannelFuture f = bootstrap.connect(host, port).sync();
            connected = true;
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("ssl", "true");
        Integer port = 8080;
        final ChatClient client = new ChatClient("127.0.0.1", port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            if (client.connected) {
                client.say(message);
            }
        }
    }

    private void say(String message) {
        chatClientHandler.say(message);
    }

}
