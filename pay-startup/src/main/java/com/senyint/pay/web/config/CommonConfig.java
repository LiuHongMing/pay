package com.senyint.pay.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CommonConfig {

    @Bean("systemProperties")
    public Properties systemProperties() {
        return System.getProperties();
    }

}
