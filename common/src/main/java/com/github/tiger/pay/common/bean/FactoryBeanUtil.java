package com.github.tiger.pay.common.bean;

import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

public class FactoryBeanUtil {

    /**
     * 复制对象属性
     *
     * @param src
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T copyInstance(Object src, Class<T> clazz) throws Exception {
        if (src == null)
            return null;

        T target;
        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(src, target);
        } catch (InstantiationException | IllegalAccessException e) {
            throw e;
        }

        return target;
    }

    public static <V> Map<String, V> transform(List<? extends BeanKey> from, Class target) throws Exception {
        Map<String, V> result = Maps.newLinkedHashMap();
        for (Object obj : from) {
            V value = (V) FactoryBeanUtil.copyInstance(obj, target);
            result.put(((BeanKey) obj).getKey(), value);
        }
        return result;
    }

}
