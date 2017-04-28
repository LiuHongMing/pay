package com.senyint.pay.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import(CommonConfig.class)
@ImportResource({"classpath:/applicationContext.xml"})
public class AppConfig {

}
