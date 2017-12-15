package com.github.tiger.pay.dubbo.support;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Filter;
import com.github.tiger.pay.dubbo.filter.ProviderResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Set;

/**
 * 扩展点拦截配置
 *
 * @author liuhongming
 */
public class ProviderExtensions implements BeanFactoryPostProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ExtensionLoader extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);
        extensionLoader.addExtension("providerresponse", ProviderResponseFilter.class);
        Set<String> filterSet = extensionLoader.getSupportedExtensions();
        logger.info("Filters:{}", filterSet);
    }

}
