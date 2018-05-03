package distributed.crawler.extraction;

import org.junit.Test;
import org.nlpcn.commons.lang.pinyin.Pinyin;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

public class NlpCnTest {

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
    }

}
