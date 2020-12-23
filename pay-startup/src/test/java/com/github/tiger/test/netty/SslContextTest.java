package com.github.tiger.test.netty;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.junit.Test;

import javax.net.ssl.SSLException;
import java.io.InputStream;

public class SslContextTest {

    @Test
    public void testClientLite() throws SSLException {
        String sslCa = "etc/ca.pem";
        InputStream certificate = SslContextTest.class.getClassLoader().getResourceAsStream(sslCa);
        String sslCertChain = "etcd/etcd.pem";
        InputStream keyCertChainInputStream = SslContextTest.class.getClassLoader().getResourceAsStream(sslCertChain);
        /**
         * 私钥转换：RSA（PKSC#1）>>> PKSC#8
         *
         * openssl pkey -in rsa_key.pem -out private_key.pem
         */
        String sslKey = "etcd/etcd-key.pem";
        InputStream keyInputStream = SslContextTest.class.getClassLoader().getResourceAsStream(sslKey);

        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(certificate)
                .keyManager(keyCertChainInputStream, keyInputStream)
                .build();
    }

    @Test
    public void testServerLite() throws SSLException {
        String sslCertChain = "etcd/etcd.pem";
        InputStream keyCertChainInputStream = SslContextTest.class.getClassLoader().getResourceAsStream(sslCertChain);
        /**
         * 私钥转换：RSA（PKSC#1）>>> PKSC#8
         *
         * openssl pkey -in rsa_key.pem -out private_key.pem
         */
        String sslKey = "etcd/etcd-key.pem";
        InputStream keyInputStream = SslContextTest.class.getClassLoader().getResourceAsStream(sslKey);
        SslContext sslContext = SslContextBuilder.forServer(
                keyCertChainInputStream, keyInputStream)
                .startTls(true)
                .build();
    }
}
