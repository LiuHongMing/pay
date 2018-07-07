package com.github.tiger.test.lexicon;

import org.junit.Test;
import org.nlpcn.commons.lang.finger.FingerprintService;
import org.nlpcn.commons.lang.finger.SimHashService;
import org.nlpcn.commons.lang.pinyin.Pinyin;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

public class NlpcnTest {

    @Test
    public void testPinyin() {
        List<String> pinyins = Pinyin.pinyin("刘洪铭");
        for (String pinyi : pinyins) {
            System.out.println(pinyi);
        }
    }

    @Test
    public void testTrie() throws Exception {
        String dic = "中国\t1\tzg\n" +
                "人名\t2\n" +
                "中国人民\t4\n" +
                "人民\t3\n" +
                "孙健\t5\n" +
                "CSDN\t6\n" +
                "java\t7\n" +
                "java学习\t10\n";
        Forest forest = Library.makeForest(new BufferedReader(new StringReader(dic)));

        Library.removeWord(forest, "中国");

        Library.insertWord(forest, "中国人");

        String content = "中国人名识别是中国人民的一个骄傲." +
                "孙健人民在CSDN中学到了很多最早iteye是java学习笔记叫javaeye" +
                "但是java123只是一部分";

        GetWord gw = new GetWord(forest, content);
        for (String param : gw.getParams()) {
            System.out.println(param);
        }
    }

    /**
     * 计算文章的指纹
     */
    @Test
    public void testFinger() {
        String content = "我就想测试下指纹是咋生成的";
        FingerprintService finger = new FingerprintService();
        System.out.println(finger.fingerprint(content));
    }

    /**
     * SimHash指纹
     */
    @Test
    public void testSimHash() {
        String content = "我就想测试下simhash指纹是咋生成的";
        SimHashService simhash = new SimHashService();
        System.out.println(simhash.fingerprint(content));

    }

}
