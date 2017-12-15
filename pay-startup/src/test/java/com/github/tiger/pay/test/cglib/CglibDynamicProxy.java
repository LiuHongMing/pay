package com.github.tiger.pay.test.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib基于继承类生成委托类的代理对象
 *
 * 对于final类的修改无法使用Cglib来生成代理对象
 */
public class CglibDynamicProxy implements MethodInterceptor {

    private Object target;

    public CglibDynamicProxy() {
    }

    public Object getProxyInstance(Object target) {
        this.target = target;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);

        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("proxy.MethodInterceptor.intercept");
        return proxy.invokeSuper(obj, args);
    }

}
