package com.senyint.common.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Rpc服务注解类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RpcService {

    String value() default "";

}
