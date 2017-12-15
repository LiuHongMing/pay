package com.github.tiger.pay.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import(DefaultConfig.class)
@ImportResource({"classpath:/applicationContext.xml"})
public class AppConfig {

}
