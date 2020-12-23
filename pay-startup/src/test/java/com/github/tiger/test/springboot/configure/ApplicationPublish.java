package com.github.tiger.test.springboot.configure;

import com.github.tiger.test.springboot.event.ApplicationStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

/**
 * 启动注册器
 *
 * 基于 springboot ApplicationReadyEvent 启动
 *
 * @date 2020-05-09
 */
public class ApplicationPublish {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationPublish.class);

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        try {
            ConfigurableApplicationContext context = event.getApplicationContext();
            context.publishEvent(new ApplicationStartEvent(context));
        } catch (Exception e) {
            LOGGER.error("系统异常", e);
            Runtime.getRuntime().exit(1);
        }
    }
}
