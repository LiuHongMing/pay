package com.github.tiger.test.springboot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application.system")
public class ApplicationSystemProperties extends ConfigureProperties {

    private String name;

    private String core;

}
