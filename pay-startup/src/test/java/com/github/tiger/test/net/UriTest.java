package com.github.tiger.test.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * https://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/net/URI.html
 * <p>
 * 参考：
 * https://skyao.io/learning-grpc/basic/name_resolver/URI.html
 */
@RunWith(JUnit4.class)
public class UriTest {

    /**
     * URI 的各个获取方法
     */
    @Test
    public void testUriGetter() {
        try {
            URI uri = new URI("zsx://shixinzhang.top:8080/categories/android?page=3#3");

            System.out.println(uri + " isOpaque : " + uri.isOpaque());

            print("scheme: " + uri.getScheme());
            print("fragment ID is: " + uri.getFragment());
            if (uri.isOpaque()) { // 不透明（不是以 / 开始）
                print("scheme specific part is: " + uri.getSchemeSpecificPart());
            } else { // 不是透明的，就是分层的
                uri = uri.parseServerAuthority();
                print("host is: " + uri.getHost());
                print("user info is: " + uri.getUserInfo());
                print("port is: " + uri.getPort());
                print("path is: " + uri.getPath());
                print("query string is: " + uri.getQuery());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void print(final String s) {
        System.out.println("The " + s);
    }

}
