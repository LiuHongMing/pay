package com.github.tiger.common.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机字符串生成工具类
 */
public class RandomStringUtil {

    /**
     * 生成由字母组成的随机字符串
     *
     * @param count
     * @return
     */
    public static String randomAlphabetic(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    /**
     * 生成由数字组成的随机字符串
     *
     * @param count
     * @return
     */
    public static String randomNumeric(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

}
