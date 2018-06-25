package com.github.tiger.common.util;

public class StringUtil {

    /**
     * 驼峰格式
     */
    public static String hump(String field, char delimiter) {
        StringBuilder sb = new StringBuilder();
        char[] charArray = field.toCharArray();
        for (int head = 0, pos = 0; head < charArray.length; head++) {
            char c = charArray[head];
            if (c == delimiter) {
                pos = head + 1;
                continue;
            } else if (head > 0 && pos == head) {
                c = Character.toUpperCase(c);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 判断字符串长度
     */
    public static boolean hasLength(String str, int len) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            int val = (int) c;
            if (val < -128 || val > 127) {
                len--;
            }
        }
        return len <= 0;
    }

}
