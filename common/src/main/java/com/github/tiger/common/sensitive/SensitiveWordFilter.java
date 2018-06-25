package com.github.tiger.common.sensitive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 简单实现敏感词过滤器
 *
 * DFA(确定性有限自动机)算法
 *
 * @author liuhongming
 */
public class SensitiveWordFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordFilter.class);

    /**
     * 敏感词容器
     */
    private static Map sensitiveWordMap;

    /**
     * 检查敏感词
     *
     * @param text
     * @return
     */
    public boolean checkSensitiveWord(String text) {
        if (sensitiveWordMap == null) {
            logger.warn("未初始化敏感词容器");
            return false;
        }

        Map nowMap = sensitiveWordMap;
        for (int i = 0, length = text.length(); i < length; i++) {
            char at = text.charAt(i);
            nowMap = (Map) nowMap.get(at);
            if (nowMap == null) {
                break;
            } else {
                if ((boolean) nowMap.get("isEnd")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 将敏感词库转换成Map容器
     *
     * @param words
     * @return
     */
    public Map convertSensitiveWordToMap(Set<String> words) {
        if (words == null || words.size() == 0) {
            return new HashMap<>();
        }

        // 初始化敏感词容器
        sensitiveWordMap = new HashMap<>(words.size());

        Map nowMap;
        Map newMap;

        for (String word : words) {
            if (word == null || "".equals(word)) {
                continue;
            }
            nowMap = sensitiveWordMap;
            for (int i = 0, length = word.length(); i < length; i++) {
                char at = word.charAt(i);
                Object temp = nowMap.get(at);
                if (temp == null) {
                    newMap = new HashMap<>();
                    newMap.put("isEnd", false);
                    nowMap.put(at, newMap);
                    nowMap = newMap;
                } else {
                    nowMap = (Map) temp;
                }
                if (i == (length - 1)) {
                    nowMap.put("isEnd", true);
                }
            }
        }

        return sensitiveWordMap;
    }

    public static void main(String[] args) {
        Set<String> sensitiveWords = new HashSet<>();
        sensitiveWords.add("中国");
        sensitiveWords.add("日本");
        sensitiveWords.add("日本人");
        sensitiveWords.add("日本女人");
        SensitiveWordFilter filter = new SensitiveWordFilter();
        sensitiveWordMap = filter.convertSensitiveWordToMap(sensitiveWords);
        System.out.println(sensitiveWordMap);

        String text1 = "日本女人来中国了";
        String text2 = "如何成为一个中等码农";
        System.out.println("“" + text1 + "”" + (filter.checkSensitiveWord(text1) ? "" : "不") + "包含敏感词");
        System.out.println("“" + text2 + "”" + (filter.checkSensitiveWord(text2) ? "" : "不") + "包含敏感词");
    }
}
