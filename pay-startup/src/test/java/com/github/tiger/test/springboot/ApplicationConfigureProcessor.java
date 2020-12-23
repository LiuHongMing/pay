package com.github.tiger.test.springboot;

import com.github.tiger.test.springboot.annotation.ApplicationConfigure;
import com.github.tiger.test.springboot.event.ApplicationStartEvent;
import com.github.tiger.test.springboot.properties.ApplicationSystemProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

@ApplicationConfigure
public class ApplicationConfigureProcessor implements ApplicationListener<ApplicationStartEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartEvent event) {
        ApplicationContext context = (ApplicationContext) event.getSource();
        ApplicationSystemProperties applicationSystemProperties = (ApplicationSystemProperties)
            context.getBean(ApplicationSystemProperties.class);
        System.out.println(applicationSystemProperties.getVersion());
    }
}
