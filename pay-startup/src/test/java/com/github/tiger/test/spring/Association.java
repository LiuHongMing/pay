package com.github.tiger.test.spring;

import java.lang.annotation.*;

/**
 * 模块关联注解
 *
 * @author liuhongming
 * @date 2020-05-21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Association {

    ModuleTypeEnum[] moduleType() default {};
}
