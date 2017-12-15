package com.github.tiger.pay.server.channel;

import com.github.tiger.pay.server.NettyServer;
import com.github.tiger.pay.server.handler.HttpServletHandler;
import com.github.tiger.pay.server.handler.IdleStateAwareHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;

/**
 * Netty ChannelInitializer
 *
 * @author liuhongming
 */
public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final String DEFAULT_SERVLET_NAMESPACE = "CGI";
    private static final String DEFAULT_SERVLET_NAME = "DefaultServlet";

    private final NettyServer server;
    private final DispatcherServlet dispatcherServlet;

    private final int MAX_CONTENT_LENGTH = 8 * 1024;

    /**
     * 初始化上下文
     */
    public DefaultChannelInitializer(NettyServer server, Class<?> annotationConfig) throws ServletException {
        this.server = server;

        MockServletContext servletContext = new MockServletContext();
        MockServletConfig  servletConfig  = new MockServletConfig(servletContext, DEFAULT_SERVLET_NAME);

        AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
        wac.setServletContext(servletContext);
        wac.setServletConfig(servletConfig);
        if (annotationConfig != null) {
            wac.register(annotationConfig);
        }
        wac.refresh();

        this.dispatcherServlet = new DispatcherServlet(wac);
        this.dispatcherServlet.setNamespace(DEFAULT_SERVLET_NAMESPACE);
        this.dispatcherServlet.init(servletConfig);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // -------------------
        // 传输层 outbound
        // -------------------
//        pipeline.addLast("logger", new LoggingHandler(LogLevel.INFO));
        // ssl
        SslHandler sslHandler = server.sslHandler(ch.alloc());
        if (sslHandler != null) {
            pipeline.addLast("ssl", sslHandler);
        }
        // idle, 空闲超时10分钟
        pipeline.addLast("idleState", new IdleStateHandler(60,
                30, 0))
                .addLast("idleStateAware", new IdleStateAwareHandler());
        // http
        pipeline.addLast("decoder",    new HttpRequestDecoder())
                .addLast("aggregator", new HttpObjectAggregator(MAX_CONTENT_LENGTH))
                .addLast("encoder",    new HttpResponseEncoder())
                .addLast("streamer",   new ChunkedWriteHandler())
                .addLast("handler",    new HttpServletHandler(dispatcherServlet));
        // -------------------
        // 应用层 inbound
        // -------------------
    }

}
