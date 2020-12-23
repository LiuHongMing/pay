package com.github.tiger.test.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 类 URL 代表一个统一资源定位符，它是指向互联网“资源”的指针。
 *
 * https://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/net/URL.html
 */
@RunWith(JUnit4.class)
public class UrlTest {

    @Test
    public void testURLConnection() {
        try {
            URL url = new URL("https://liuhongming.com.cn");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
