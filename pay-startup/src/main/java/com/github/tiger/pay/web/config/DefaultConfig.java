package com.github.tiger.pay.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 默认常用配置
 *
 * @author liuhongming
 */
//@Configuration
public class DefaultConfig {

    @Bean("systemProperties")
    public Properties systemProperties() {
        Properties props = System.getProperties();
        props.setProperty("maxCon", "10");
        return props;
    }

}
