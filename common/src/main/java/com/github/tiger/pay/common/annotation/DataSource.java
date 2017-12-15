package com.github.tiger.pay.common.annotation;

import java.lang.annotation.*;

/**
 * 数据源注解类
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    String name() default DataSource.SLAVE;

    String MASTER = "master";

    String SLAVE = "slave";

}
