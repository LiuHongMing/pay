package com.senyint.server;

import com.senyint.Server;
import org.springframework.util.ClassUtils;

/**
 * 启动服务代理类，用于代理具体服务的创建
 *
 * @author liuhongming
 */
public class ServerProxy {

    /**
     * 创建Server接口的实例化对象，并绑定端口
     *
     * @param name 启动服务类名
     * @param port 服务绑定端口
     *
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Server newProxy(String name, int port) throws ClassNotFoundException
            , IllegalAccessException, InstantiationException {
        Server server = (Server) ClassUtils.forName(name, Thread.currentThread().getContextClassLoader())
                .newInstance();
        server.bind(port);
        return server;
    }

}
