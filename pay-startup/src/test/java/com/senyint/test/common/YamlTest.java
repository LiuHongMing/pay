package com.senyint.test.common;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

public class YamlTest {

    String yamlSource = "config/wx.yaml";

    @Test
    public void testYaml2Map() {
        YamlMapFactoryBean yamlMap = new YamlMapFactoryBean();
        yamlMap.setResources(new ClassPathResource(yamlSource));
        Map<String, Object> map = yamlMap.getObject();
        System.out.println(map.get("wxc1ea6808615f50db"));
    }

    @Test
    public void testYaml2Properties() {
        YamlPropertiesFactoryBean yamlProperties = new YamlPropertiesFactoryBean();
        yamlProperties.setResources(new ClassPathResource(yamlSource));
        Properties properties = yamlProperties.getObject();
        System.out.println(properties.get("wxc1ea6808615f50db.partner_key"));
    }

}
