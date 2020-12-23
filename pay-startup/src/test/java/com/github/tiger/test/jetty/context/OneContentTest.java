package com.github.tiger.test.jetty.context;

import com.github.tiger.test.jetty.HelloHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

public class OneContentTest {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        // 增加一个唯一的 handler 给 context "/hello"
        ContextHandler context = new ContextHandler();
        context.setContextPath("/hello");
        context.setHandler(new HelloHandler());

        // 在 http://localhost:8080/hello 访问服务

        server.setHandler(context);

        // 启动服务
        server.start();
        server.join();
    }

}
