package com.github.tiger.test.springboot.configure;

import com.github.tiger.test.springboot.ApplicationConfigureProcessor;
import com.github.tiger.test.springboot.annotation.ApplicationComponentScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public class ApplicationConfigureResolver implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 扫描包路径
        final String basePackage = ApplicationConfigureProcessor.class.getPackage().getName();
        new ApplicationComponentScanner((BeanDefinitionRegistry) beanFactory).scan(basePackage);
    }

}
