package com.senyint.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processes timeout when channel idle.
 *
 * @author liuhongming
 * @date   2017/10/30
 */
@ChannelHandler.Sharable
public class IdleStateAwareHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(IdleStateAwareHandler.class);

    public IdleStateAwareHandler() {}

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered evt=" + evt.toString());
        if (evt instanceof IdleStateEvent) {
            Channel channel = ctx.channel();
            IdleState idleState = ((IdleStateEvent) evt).state();
            switch (idleState) {
                case READER_IDLE:
                    // read timeout
                    logger.warn("channel=[{}] read timeout will close.", channel);
                    channel.close();
                    break;
                case WRITER_IDLE:
                    // write timeout
                    logger.warn("channel=[{}] write timeout will close.", channel);
                    channel.close();
                    break;
                case ALL_IDLE:
                    // idle timeout
                    logger.warn("channel=[{}] idle timeout will close.", channel);
                    channel.close();
                    break;
                default:
                    break;
            }
        }
    }
}
