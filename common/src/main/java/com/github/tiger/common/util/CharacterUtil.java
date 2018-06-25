package com.github.tiger.common.util;

/**
 * 字符工具类
 *
 * @author liuhongming
 */
public class CharacterUtil {

    //字符、数字
    public static boolean isLetterOrDigit(char c) {
        int ascii = (int) c;
        if (ascii >= 0 && ascii <= 255) {
            return true;
        }
        return false;
    }

    // 汉语
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 日语
    public static boolean isJanpanese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.HIRAGANA
                || ub == Character.UnicodeBlock.KATAKANA) {
            return true;
        }
        return false;
    }

    // 韩语
    public static boolean isHangul(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                || ub == Character.UnicodeBlock.HANGUL_JAMO
                || ub == Character.UnicodeBlock.HANGUL_SYLLABLES ) {
            return true;
        }
        return false;
    }

    /**
     * 字符转换成字节数组
     */
    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[1] = (byte) (c & 0xFF);
        b[0] = (byte) (c >> 8 & 0xFF);
        return b;
    }

    /**
     * 字节数组转换成字符
     */
    public static char byteToChar(byte[] b) {
        return (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
    }

}
