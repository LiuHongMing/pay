package com.github.tiger.common.bean;

import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuhongming
 */
public class BeanFunction implements Function {

    private static final Logger logger = LoggerFactory.getLogger(BeanFunction.class);

    private Class output;

    private BeanFunction(Class output) {
        this.output = output;
    }

    @Override
    public Object apply(Object input) {
        return supply(input);
    }

    public Object supply(Object input) {
        try {
            return FactoryBeanUtil.copyInstance(input, output);
        } catch (Exception e) {
            logger.error("BeanFunction：" + e.getMessage(), e);
        }
        return null;
    }

    public static BeanFunction create(Class target) {
        return new BeanFunction(target);
    }

}
