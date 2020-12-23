package com.github.tiger.test.spring;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * Association 切面。
 *
 * @author liuhongming
 * @date 2020-06-01
 */
public class AssociationAspect extends AbstractPointcutAdvisor implements BeanDefinitionRegistryPostProcessor {

    private ProxyFactory proxyFactory = new ProxyFactory();

    private Pointcut pointcut = new AssociationPointcut();

    private Advice advice = (MethodInterceptor) invocation -> {
        return enhanceInvocation(invocation).proceed();
    };

    public MethodInvocation enhanceInvocation(Object thisTarget) {
        proxyFactory.setTarget(thisTarget);
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
            if ("proceed".equals(method.getName())) {
                System.out.println("Association 加强处理 。。。");
            }
        });
        return (MethodInvocation) proxyFactory.getProxy();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
