package com.github.tiger.pay.test.common;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

public class YamlTest {

    String wxYaml = "config/wx.yaml";

    @Test
    public void testWxYaml2Map() {
        YamlMapFactoryBean yamlMap = new YamlMapFactoryBean();
        yamlMap.setResources(new ClassPathResource(wxYaml));
        Map<String, Object> map = yamlMap.getObject();
        System.out.println(map.get("wxc1ea6808615f50db"));
    }

    @Test
    public void testWxYaml2Properties() {
        YamlPropertiesFactoryBean yamlProperties = new YamlPropertiesFactoryBean();
        yamlProperties.setResources(new ClassPathResource(wxYaml));
        Properties properties = yamlProperties.getObject();
        System.out.println(properties.get("wxc1ea6808615f50db.partner_key"));
    }

    String redisYaml = "config/redis.yaml";

    @Test
    public void testRedisYaml2Map() {
        YamlMapFactoryBean yamlMap = new YamlMapFactoryBean();
        yamlMap.setResources(new ClassPathResource(redisYaml));
        Map<String, Object> map = yamlMap.getObject();
        System.out.println(map.get("codis"));
    }

    @Test
    public void testRedisYaml2Properties() {
        YamlPropertiesFactoryBean yamlProperties = new YamlPropertiesFactoryBean();
        yamlProperties.setResources(new ClassPathResource(redisYaml));
        Properties properties = yamlProperties.getObject();
        System.out.println(properties.get("redis.codis"));
    }

}
