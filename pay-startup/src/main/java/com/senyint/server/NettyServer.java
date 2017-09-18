package com.senyint.server;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.senyint.Server;
import com.senyint.server.channel.DefaultChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Netty服务类
 *
 * @author jason
 */
public class NettyServer extends ServerConfig implements Server {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private static final Range<Integer> allowingPortRange = Range.closed(8080, 65535);
    private static final int DEFAULT_PORT = 8080;

    private String host = "0.0.0.0";
    private int port = DEFAULT_PORT;
    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Class<? extends ServerChannel> channelClass;
    private ChannelInitializer channelInitializer;

    private ChannelFuture channelFuture;

    public NettyServer() {
        this(DEFAULT_PORT);
    }

    public NettyServer(int port) {
        Preconditions.checkArgument(allowingPortRange.contains(port),
                "Port(%s) is out of range %s", port, allowingPortRange);
        this.port = port;
    }

    @Override
    public void start() {
        serverBootstrap = new ServerBootstrap();
        // init groups and channelClass
        if (isUseLinuxEpoll()) {
            bossGroup   = new EpollEventLoopGroup(getBossThreads(), new DefaultThreadFactory("epollServer-boss"));
            workerGroup = new EpollEventLoopGroup(getWorkThreads(), new DefaultThreadFactory("epollServer-worker"));
            serverBootstrap.group(bossGroup, workerGroup);
            channelClass = EpollServerSocketChannel.class;
            serverBootstrap.channel(channelClass);
        } else {
            bossGroup   = new NioEventLoopGroup(getBossThreads(), new DefaultThreadFactory("nioServer-boss"));
            workerGroup = new NioEventLoopGroup(getWorkThreads(), new DefaultThreadFactory("nioServer-worker"));
            serverBootstrap.group(bossGroup, workerGroup);
            channelClass = NioServerSocketChannel.class;
            serverBootstrap.channel(channelClass);
        }

        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        // parent options
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
        serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        // child options
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        // init channelHandler
        try {
            this.channelInitializer = channelInitializer();
        } catch (Exception e) {
            logger.error("ChannelInitializer failed : " + e.getMessage(), e);
        }
        serverBootstrap.childHandler(channelInitializer);
        try {
            channelFuture = serverBootstrap.bind(port).addListeners(new FutureListener<Void>() {
                @Override
                public void operationComplete(final Future<Void> future) throws Exception {
                    if (future.isSuccess()) {
                        if (logger.isInfoEnabled()) {
                            logger.info("Start succeed in host:port => {}:{}", host, port);
                        }
                    }
                }
            });
        } catch (Exception e) {
            logger.error("Start failed on host:port => {}:{}", host, port, e);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    NettyServer.this.stop();
                }
            });
        }
    }

    @Override
    public void stop() {
        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (logger.isInfoEnabled()) {
            logger.info("Stop completed ...");
        }
    }

    @Override
    public Server getServer() {
        return NettyServer.this;
    }

    public ChannelInitializer channelInitializer() throws ServletException {
        if (this.channelInitializer == null) {
            this.channelInitializer = new DefaultChannelInitializer(this, null);
        }
        return channelInitializer;
    }

    public int port() {
        return port;
    }

    @Override
    public void bind(int port) {
        this.port = port;
    }

}
