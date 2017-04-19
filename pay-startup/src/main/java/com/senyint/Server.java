package com.senyint;

/**
 * 该接口代表服务组件
 *
 * @author liuhongming
 */
public interface Server extends Lifecycle {

    /**
     * 获取服务组件
     */
    Server getServer();

    /**
     * 绑定端口
     */
    void bind(int port);

}
