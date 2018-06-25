package com.github.tiger.common.util;

import org.springframework.beans.BeanUtils;

/**
 * POJO工厂工具类
 *
 * @author liuhongming
 */
public class BeanFactoryUtil {

    /**
     * 复制目标对象属性值
     *
     * @param srcObj
     * @param targetClazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T copyProperties(Object srcObj, Class<T> targetClazz) {
        if (srcObj == null) {
            return null;
        }

        T target = BeanUtils.instantiate(targetClazz);
        BeanUtils.copyProperties(srcObj, target);

        return target;
    }

}
