package com.senyint.server.handler;

import com.senyint.common.util.HttpRequestUtil;
import com.senyint.server.http.HttpServletRequestBuilder;
import com.senyint.server.http.HttpServletResponseBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.Servlet;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * HttpServlet执行类
 *
 * @author liuhongming
 */
public class HttpServletHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger logger = LoggerFactory.getLogger(HttpServletHandler.class);

    private static final String CHARSET = HttpConstants.DEFAULT_CHARSET.name();
    private static final String TEXT_PLAIN = "text/plain;charset=" + CHARSET;

    private final Servlet servlet;

    public HttpServletHandler(Servlet servlet) {
        this.servlet = servlet;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        if (fullHttpRequest.decoderResult().isFailure()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST, null);
            return;
        }
        Channel channel = ctx.channel();

        MockHttpServletRequest httpServletRequest = HttpServletRequestBuilder.build(fullHttpRequest);

        InetAddress inetAddress = ((InetSocketAddress) channel.remoteAddress()).getAddress();
        String remoteAddress = inetAddress.getHostAddress();
        String xRealIP = httpServletRequest.getHeader("X-Real-IP");
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        if (xRealIP != null) {
            remoteAddress = xRealIP;
        } else if (xForwardedFor != null) {
            remoteAddress = xForwardedFor;
        }
        httpServletRequest.setRemoteAddr(remoteAddress);

        MockHttpServletResponse httpServletResponse = HttpServletResponseBuilder.build();

        long startTime = System.currentTimeMillis();
        servlet.service(httpServletRequest, httpServletResponse);
        long elapsedTime = System.currentTimeMillis() - startTime;
//        if (logger.isInfoEnabled()) {
//            String requestURI = HttpRequestUtil.getUriString(httpServletRequest);
//            logger.info("{} - RequestURI {}: completed in {} ms",
//                    remoteAddress, requestURI, elapsedTime);
//        }

        HttpResponseStatus responseStatus = HttpResponseStatus.valueOf(httpServletResponse.getStatus());
        DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, responseStatus);
        for (String name : httpServletResponse.getHeaderNames()) {
            for (Object value : httpServletResponse.getHeaderValues(name)) {
                response.headers().add(name, value);
            }
        }
        String responseContent = httpServletResponse.getContentAsString();
        if (responseStatus.code() != HttpResponseStatus.OK.code()) {
            responseContent = pretty(responseStatus, httpServletResponse.getErrorMessage());
        }
        ctx.write(response);
        InputStream inputStream = new ByteArrayInputStream(responseContent.getBytes(CHARSET));
        ChannelFuture channelFuture = ctx.writeAndFlush(new ChunkedStream(inputStream));
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR, cause);
        }
    }

    /**
     * 响应错误信息
     */
    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus responseStatus, Throwable cause) {
        String errorMessage = pretty(responseStatus, cause.toString());
        ByteBuf content = Unpooled.copiedBuffer(errorMessage, Charset.forName(CHARSET));
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, responseStatus, content);
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, TEXT_PLAIN);
        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 响应消息格式化
     */
    private String pretty(HttpResponseStatus responseStatus, String errorMessage) {
        StringBuilder builder = new StringBuilder();
        builder.append(responseStatus.code()).append((char) HttpConstants.SP)
                .append(responseStatus.reasonPhrase());
        if (StringUtils.isNotEmpty(errorMessage)) {
            builder.append((char) HttpConstants.LF).append(errorMessage);
        }
        return builder.toString();
    }
}
