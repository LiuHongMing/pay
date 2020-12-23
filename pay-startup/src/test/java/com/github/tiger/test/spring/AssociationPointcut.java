package com.github.tiger.test.spring;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author liuhongming
 * @date 2020-06-05
 */
public class AssociationPointcut extends StaticMethodMatcherPointcut {

    /**
     * 在注解声明的作用域范围内进行匹配
     *
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {

        if (AssociationService.class.isAssignableFrom(targetClass)) {

            // 查找方法
            Annotation target = AnnotationUtils.findAnnotation(method, Association.class);
            if (target != null) {
                return true;
            }

            // 查找超类
            Class declaringClass = method.getDeclaringClass();
            target = AnnotationUtils.findAnnotation(declaringClass, Association.class);
            if (target != null) {
                return true;
            }

            // 查找接口
            Class[] interfaces = declaringClass.getInterfaces();
            if (interfaces != null && interfaces.length > 0) {
                for (Class declaringInterface : interfaces) {
                    target = AnnotationUtils.findAnnotation(declaringInterface, Association.class);
                    if (target != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
