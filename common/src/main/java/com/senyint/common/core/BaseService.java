package com.senyint.common.core;

import org.springframework.beans.factory.InitializingBean;

/**
 * 服务基类，通过Spring容器实例化的服务接口类可继承此类，以完善在初始化阶段的配置
 *
 * @author liuhongming
 */
public class BaseService implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
