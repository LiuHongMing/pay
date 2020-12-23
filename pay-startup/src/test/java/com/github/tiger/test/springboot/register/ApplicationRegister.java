package com.github.tiger.test.springboot.register;

import com.github.tiger.test.springboot.configure.ApplicationConfigureResolver;
import com.github.tiger.test.springboot.properties.ApplicationSystemProperties;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自动注册
 */
public class ApplicationRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private ApplicationSystemProperties properties = new ApplicationSystemProperties();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerApplicationConfigure(registry);
    }

    private void registerApplicationConfigure(BeanDefinitionRegistry registry) {
        String id = ApplicationConfigureResolver.class.getName();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
            .genericBeanDefinition(ApplicationConfigureResolver.class)
            .setLazyInit(false);
        registry.registerBeanDefinition(id, beanDefinitionBuilder.getBeanDefinition());
    }

    @Override
    public void setEnvironment(Environment environment) {
//        String prefix = "application.system";
//        Binder.get(environment).bind(prefix, Bindable.ofInstance(properties));
    }
}
