package com.github.tiger.test.jetty.servlet;

import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class ServerServletTest {

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        // Connector
        ServerConnector connector = new ServerConnector(server,
            new HttpConnectionFactory());
        connector.setPort(8080);

        server.addConnector(connector);

        // ServletHandler 是创建 context handler 的简单方法，
        // 由 Servlet 实例支持。
        // 这个 handler 对象需要被注册给 Server 对象。
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        // 传递的 Servlet 类允许 jetty 实例化并且挂载到给定的上下文路径上。
        // 重要:
        // 这是一个原始的 Servlet, 不是一个通过 web.xml 或者 @WebServlet注解或者其他类似方式配置的servlet。
        handler.addServletWithMapping(HelloServlet.class, "/*");

        server.start();
        server.join();
    }

}
