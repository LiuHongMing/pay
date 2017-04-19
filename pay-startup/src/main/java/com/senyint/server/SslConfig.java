package com.senyint.server;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.internal.SystemPropertyUtil;

import javax.servlet.ServletException;

/**
 * Configure SSL settings
 *
 * @author liuhongming
 */
public abstract class SslConfig {

    public static final boolean SSL = SystemPropertyUtil.getBoolean("ssl", false);

    private SslContext sslCtx;

    public SslConfig() {
        if (SSL) {
            init();
        }
    }

    /**
     * init SslContext by subclass
     */
    public abstract void init();

    public SslHandler sslHandler(ByteBufAllocator alloc) {
        if (sslCtx != null) {
            return sslCtx.newHandler(alloc);
        }
        return null;
    }

    public SslContext sslCtx() {
        return sslCtx;
    }

    public void setSslCtx(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }
}
