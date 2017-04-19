package com.senyint.startup;

import com.senyint.Server;
import com.senyint.server.NettyServer;
import com.senyint.server.ServerProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 服务启动类
 *
 * @author liuhongming
 */
public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    private static final String ENV_SERVER_BOOTSTRAP = "server.bootstrap";
    private static final String ENV_SERVER_PORT = "server.port";

    private static final String DEFAULT_BOOTSTRAP_CLASS_NAME = NettyServer.class.getName();
    private static final String DEFAULT_PORT = "9090";

    public static void main(String[] args) throws Exception {
        // System.setProperty("dubbo.application.logger", "slf4j");

        ClassPathResource evnResource = new ClassPathResource("env.yaml");
        YamlPropertiesFactoryBean yamlProperties = new YamlPropertiesFactoryBean();
        yamlProperties.setResources(evnResource);

        Properties env = yamlProperties.getObject();
        String bootstrap = (String) env.getOrDefault(ENV_SERVER_BOOTSTRAP, DEFAULT_BOOTSTRAP_CLASS_NAME);
        String port = (String) env.getOrDefault(ENV_SERVER_PORT, DEFAULT_PORT);
        try {
            Server server = ServerProxy.newProxy(bootstrap, Integer.valueOf(port));
            server.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
