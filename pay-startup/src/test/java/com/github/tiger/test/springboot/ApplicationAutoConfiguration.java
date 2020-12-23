package com.github.tiger.test.springboot;

import com.github.tiger.test.springboot.configure.ApplicationPublish;
import com.github.tiger.test.springboot.properties.ApplicationSystemProperties;
import com.github.tiger.test.springboot.register.ApplicationRegister;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ApplicationRegister.class)
@EnableConfigurationProperties(ApplicationSystemProperties.class)
public class ApplicationAutoConfiguration {

    @Bean
    public ApplicationPublish startRegister() {
        return new ApplicationPublish();
    }

}
