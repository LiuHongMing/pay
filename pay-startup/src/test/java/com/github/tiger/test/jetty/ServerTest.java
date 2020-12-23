package com.github.tiger.test.jetty;

import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class ServerTest {

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        // Connector
        ServerConnector connector = new ServerConnector(server,
            new HttpConnectionFactory());
        connector.setPort(8080);

        server.addConnector(connector);

        server.setHandler(new HelloHandler());

        server.start();
        server.join();
    }

}
