package com.github.tiger.test.net;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author liuhongming
 */
public class HttpClientTest {

    static URL url;

    URLConnection urlConnection;

    HttpURLConnection httpUrlConnection;

    static {
        /**
         * 全局设置
         *
         * 屏蔽自动执行 HTTP 重定向 (3xx)
         */
        HttpURLConnection.setFollowRedirects(false);
    }

    @BeforeTest
    public void setup() throws IOException {

        url = new URL("https://www.baidu.com");

        urlConnection = url.openConnection();

        httpUrlConnection = (HttpURLConnection) urlConnection;

        /**
         * 连接超时时间
         */
        httpUrlConnection.setConnectTimeout(30000);
        /**
         * 读取超时时间
         */
        httpUrlConnection.setReadTimeout(10000);
        /**
         * 设置读入
         *
         * 默认 true
         */
        httpUrlConnection.setDoInput(true);
        /**
         * 手动设置该实例自动执行 HTTP 重定向
         */
        httpUrlConnection.setInstanceFollowRedirects(true);

        // ----- 设置请求头

        /**
         * 传送的内容类型是二进制流
         */
        httpUrlConnection.setRequestProperty("content-type", "text/html;charset=utf-8");
    }

    @Test
    public void testGet() {

        try {
            httpUrlConnection.setRequestMethod("GET");

            // 设置请求头

            /**
             * 连接
             */
            httpUrlConnection.connect();

            String responseContent = receive();

            System.out.println(responseContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost() {

        try {
            httpUrlConnection.setRequestMethod("POST");

            /**
             * 设置写出
             *
             * 默认 false
             */
            httpUrlConnection.setDoOutput(true);
            /**
             * Post 请求不能使用缓存
             */
            httpUrlConnection.setUseCaches(false);

            // 设置请求头

            /**
             * 连接
             */
            httpUrlConnection.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() throws IOException {

        int resCode = httpUrlConnection.getResponseCode();

        if (resCode == HTTP_OK) {

            Map<String, List<String>> headers =
                    httpUrlConnection.getHeaderFields();

            getHeaders(headers);

            return getBody();

        }
        return "";
    }

    public void getHeaders(Map<String, List<String>> headers) {
        System.out.println(headers);
    }

    public String getBody() throws IOException {

        InputStream inStream = httpUrlConnection.getInputStream();

        BufferedInputStream bis = new BufferedInputStream(inStream);

        /**
         * 读取字节数
         */
        byte[] readBytes = new byte[1024];

        String body = "";
        for (int i = bis.read(readBytes, 0, readBytes.length); i != -1; ) {
            body += new String(readBytes);
            i = bis.read(readBytes, 0, readBytes.length);
        }

        return body;
    }

    @AfterTest
    public void close() {
        httpUrlConnection.disconnect();
    }

}
