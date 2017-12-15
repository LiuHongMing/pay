package com.github.tiger.pay;

/**
 * 提供启动/停止的生命周期控制
 *
 * @author liuhongming
 */
public interface Lifecycle {

    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

}
