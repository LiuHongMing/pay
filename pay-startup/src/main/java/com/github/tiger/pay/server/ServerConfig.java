package com.github.tiger.pay.server;

import com.github.tiger.common.util.NettyUtil;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configure server settings
 *
 * @author jason
 */
public class ServerConfig extends SslConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    /**
     * bossGroup线程数
     */
    private int bossThreads = NettyUtil.nThread();

    /**
     * workGroup线程数
     */
    private int workThreads = NettyUtil.nThread();

    private boolean useLinuxEpoll;

    public int getBossThreads() {
        return bossThreads;
    }

    public void setBossThreads(int bossThreads) {
        this.bossThreads = bossThreads;
    }

    public int getWorkThreads() {
        return workThreads;
    }

    public void setWorkThreads(int workThreads) {
        this.workThreads = workThreads;
    }

    public boolean isUseLinuxEpoll() {
        return useLinuxEpoll;
    }

    public void setUseLinuxEpoll(boolean useLinuxEpoll) {
        this.useLinuxEpoll = useLinuxEpoll;
    }

    @Override
    public void init() {
        // Configure SSL.
        try {
            if (SSL) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                setSslCtx(SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build());
            }
        } catch (Exception e) {
            logger.error("Configure SSL error", e);
        }
    }
}
