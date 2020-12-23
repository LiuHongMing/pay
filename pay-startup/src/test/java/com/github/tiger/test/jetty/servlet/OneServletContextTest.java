package com.github.tiger.test.jetty.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class OneServletContextTest {

    public static void main(String[] args) throws Exception {

        String[] strs = new String[]{};

        System.out.println(String[].class == strs.getClass());

        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(
            ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(context);

        context.addFilter(new FilterHolder(EncodingFilter.class), "/*", EnumSet.of(DispatcherType.REQUEST));
        context.setDisplayName("OneServletContext");

        // 添加 dump servlet
        context.addServlet(DumpServlet.class, "/dump/*");
        // 添加 default servlet
        context.addServlet(HelloServlet.class, "/");

        server.start();
        server.join();
    }

}
